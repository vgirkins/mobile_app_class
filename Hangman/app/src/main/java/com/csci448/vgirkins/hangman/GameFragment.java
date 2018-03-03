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
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Tori on 3/3/2018.
 */

public class GameFragment extends Fragment {
    private TextView mScoreDisplay;
    private TextView mGuessesLeft;
    private TextView mDisplayWord;
    private EditText mEnterGuess;
    private Button mSubmitGuessButton;
    private Button mBackButton;
    private Button mNewGameButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_game, container, false);

        mScoreDisplay = view.findViewById(R.id.score_display_field);
        mGuessesLeft = view.findViewById(R.id.guesses_left_field);
        mDisplayWord = view.findViewById(R.id.display_word_field);

        mEnterGuess = view.findViewById(R.id.enter_guess_field);
        mEnterGuess.addTextChangedListener(new TextWatcher() {
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

        mSubmitGuessButton = view.findViewById(R.id.submit_button);
        mSubmitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        mBackButton = view.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  TODO
            }
        });

        mNewGameButton = view.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
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
