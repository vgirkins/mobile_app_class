package com.csci448.vgirkins.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private static final String EXTRA_USER_SCORE = "com.csci448.vgirkins.hangman.user_score";
    private static final String EXTRA_COMPUTER_SCORE = "com.csci448.vgirkins.hangman.computer_score";
    private static final String EXTRA_NUM_GUESSES = "com.csci448.vgirkins.hangman.num_guesses";
    private static final String EXTRA_GAME_ON_HARD = "com.csci448.vgirkins.hangman.game_on_hard";

    private int mUserScore;
    private  int mComputerScore;
    private int mNumGuesses;
    private boolean mGameOnHard;

    private HangmanGame game;

    private TextView mScoreDisplay;
    private TextView mGuessesLeft;
    private TextView mDisplayWord;
    private EditText mEnterGuess;
    private Button mSubmitGuessButton;
    private Button mBackButton;
    private Button mNewGameButton;

    public static GameFragment newInstance(int userScore, int computerScore, int numGuesses, boolean gameOnHard) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_USER_SCORE, userScore);
        args.putInt(EXTRA_COMPUTER_SCORE, computerScore);
        args.putInt(EXTRA_NUM_GUESSES, numGuesses);
        args.putBoolean(EXTRA_GAME_ON_HARD, gameOnHard);

        GameFragment frag = new GameFragment();
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

        // FIXME actually load in words
        if (mGameOnHard) {
            game = new HangmanGame("antidisestablishmentarianism", mNumGuesses);
        }
        else {
            game = new HangmanGame("create", mNumGuesses);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_game, container, false);

        mScoreDisplay = view.findViewById(R.id.score_display_field);
        mScoreDisplay.setText(String.format(getString(R.string.score), mUserScore, mComputerScore));

        mGuessesLeft = view.findViewById(R.id.guesses_left_field);
        mGuessesLeft.setText(String.format("%1$d", mNumGuesses));

        mDisplayWord = view.findViewById(R.id.display_word_field);
        mDisplayWord.setText(game.getWordToDisplay());

        mEnterGuess = view.findViewById(R.id.enter_guess_field);

        mSubmitGuessButton = view.findViewById(R.id.submit_button);
        mSubmitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = mEnterGuess.getText().toString();
                boolean userWon = game.checkGuess(guess);
                if (userWon) {
                    mUserScore++;
                }
            }
        });

        mBackButton = view.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReturnResult();
                // TODO: Go back (duh)
            }
        });

        mNewGameButton = view.findViewById(R.id.new_game_button);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: start a new game (duh)
            }
        });

        return view;
    }
}
