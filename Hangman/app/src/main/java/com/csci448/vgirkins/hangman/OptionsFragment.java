// Author: Victoria Girkins
// Citations: https://stackoverflow.com/questions/48925221/how-to-use-string-arguments-in-layout-xml

package com.csci448.vgirkins.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// TODO IF SOMETHING STOPS WORKING YOU MAY NEED TO IMPORT ACTIVITY.RESULT_OK

/**
 * Created by Tori on 3/2/2018.
 */

public class OptionsFragment extends Fragment {
    private static final String EXTRA_USER_SCORE = "com.csci448.vgirkins.hangman.user_score";
    private static final String EXTRA_COMPUTER_SCORE = "com.csci448.vgirkins.hangman.computer_score";
    private static final String EXTRA_NUM_GUESSES = "com.csci448.vgirkins.hangman.num_guesses";
    private static final String EXTRA_GAME_ON_HARD = "com.csci448.vgirkins.hangman.game_on_hard";

    private int mUserScore;
    private  int mComputerScore;
    private int mNumGuesses;
    private boolean mGameOnHard;

    private EditText mNumGuessesField;
    private Button mSetGuessesButton;
    private CheckBox mHardCheckbox;
    private TextView mScoreDisplay;
    private Button mClearScoreButton;


    public static OptionsFragment newInstance(int userScore, int computerScore, int numGuesses, boolean gameOnHard) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_USER_SCORE, userScore);
        args.putInt(EXTRA_COMPUTER_SCORE, computerScore);
        args.putInt(EXTRA_NUM_GUESSES, numGuesses);
        args.putBoolean(EXTRA_GAME_ON_HARD, gameOnHard);

        OptionsFragment frag = new OptionsFragment();
        frag.setArguments(args);
        return frag;
    }

    public void setReturnResult() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra(EXTRA_USER_SCORE, mUserScore);
        resultIntent.putExtra(EXTRA_COMPUTER_SCORE, mComputerScore);
        resultIntent.putExtra(EXTRA_NUM_GUESSES, mNumGuesses);
        resultIntent.putExtra(EXTRA_GAME_ON_HARD, mGameOnHard);

        getActivity().setResult(Activity.RESULT_OK, resultIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserScore = getArguments().getInt(EXTRA_USER_SCORE);
        mComputerScore = getActivity().getIntent().getIntExtra(EXTRA_COMPUTER_SCORE, 0);
        mNumGuesses = getActivity().getIntent().getIntExtra(EXTRA_NUM_GUESSES, 10);
        mGameOnHard = getActivity().getIntent().getBooleanExtra(EXTRA_GAME_ON_HARD, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_options, container, false);

        mNumGuessesField = view.findViewById(R.id.num_guesses_field);
        mNumGuessesField.setText(String.format(getString(R.string.num_guesses_hint), mNumGuesses));

        mSetGuessesButton = view.findViewById(R.id.set_guesses_button);
        mSetGuessesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNumGuesses = Integer.parseInt(mNumGuessesField.getText().toString());
                setReturnResult();
                String text = "Number of guesses has been set to " + String.format("%1$d", mNumGuesses);
                makeToast(text);
            }
        });

        mHardCheckbox = view.findViewById(R.id.hard_checkbox);
        mHardCheckbox.setChecked(mGameOnHard);
        mHardCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mGameOnHard = mHardCheckbox.isChecked();
                setReturnResult();
                String text = "Game is now on " + (mGameOnHard ? "hard" : "easy");
                makeToast(text);
            }
        });

        mScoreDisplay = view.findViewById(R.id.score_display_options);
        mScoreDisplay.setText(String.format(getString(R.string.score), mUserScore, mComputerScore));

        mClearScoreButton = view.findViewById(R.id.clear_score_button);
        mClearScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserScore = 0;
                mComputerScore = 0;
                mScoreDisplay.setText(String.format(getString(R.string.score), mUserScore, mComputerScore));
                setReturnResult();
                String text = "Score record was reset";
                makeToast(text);
            }
        });

        return view;
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.TOP, 0, 0);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.background_toast);
        toast.show();
    }
}
