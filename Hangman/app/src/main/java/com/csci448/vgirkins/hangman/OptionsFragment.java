// Author: Victoria Girkins
// Citations: https://stackoverflow.com/questions/48925221/how-to-use-string-arguments-in-layout-xml

package com.csci448.vgirkins.hangman;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Tori on 3/2/2018.
 */

public class OptionsFragment extends Fragment {
    private static final String EXTRA_USER_SCORE = "com.csci448.vgirkins.hangman.user_score";
    private static final String EXTRA_COMPUTER_SCORE = "com.csci448.vgirkins.hangman.computer_score";
    private static final String EXTRA_NUM_GUESSES = "com.csci448.vgirkins.hangman.num_guesses";
    private static final String EXTRA_GAME_ON_HARD = "com.csci448.vgirkins.hangman.game_on_hard";

    private EditText mNumGuessesField;
    private Button mSetGuessesButton;
    private CheckBox mHardCheckbox;
    private TextView mScoreDisplay;
    private Button mClearScoreButton;

    private int mUserScore;
    private  int mComputerScore;
    private int mNumGuesses;
    private boolean mGameOnHard;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserScore = getActivity().getIntent().getIntExtra(EXTRA_USER_SCORE, 0);
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
        mNumGuessesField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // TODO
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // TODO
            }
        });

        mSetGuessesButton = view.findViewById(R.id.set_guesses_button);

        mHardCheckbox = view.findViewById(R.id.hard_checkbox);
        mHardCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // TODO
            }
        });

        mSetGuessesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        mScoreDisplay = view.findViewById(R.id.score_display_options);
        mScoreDisplay.setText(String.format(getString(R.string.score), mUserScore, mComputerScore));

        mClearScoreButton = view.findViewById(R.id.clear_score_button);
        mClearScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putBoolean(value);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mCallbacks = null;
    }
}
