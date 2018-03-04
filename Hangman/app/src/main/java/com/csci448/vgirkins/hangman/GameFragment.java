package com.csci448.vgirkins.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private String hardWordsFile = "hard.txt";
    private String easyWordsFile = "easy.txt";
    private int numWords = 500;

    public static GameFragment newInstance(int userScore, int computerScore, int numGuesses, boolean gameOnHard) {
        Log.d("icecream", "New instance");
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

        // The only two values which may get changed in this activity
        resultIntent.putExtra(EXTRA_USER_SCORE, mUserScore);
        resultIntent.putExtra(EXTRA_COMPUTER_SCORE, mComputerScore);

        getActivity().setResult(Activity.RESULT_OK, resultIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load in extras
        mUserScore = getArguments().getInt(EXTRA_USER_SCORE);
        mComputerScore = getActivity().getIntent().getIntExtra(EXTRA_COMPUTER_SCORE, 0);
        mNumGuesses = getActivity().getIntent().getIntExtra(EXTRA_NUM_GUESSES, 10);
        mGameOnHard = getActivity().getIntent().getBooleanExtra(EXTRA_GAME_ON_HARD, false);

        // FIXME actually load in words
        // Create a game
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
        mGuessesLeft.setText(String.format(getString(R.string.guesses_left), mNumGuesses));

        mDisplayWord = view.findViewById(R.id.display_word_field);
        mDisplayWord.setText(game.getWordToDisplay());

        mEnterGuess = view.findViewById(R.id.enter_guess_field);

        mSubmitGuessButton = view.findViewById(R.id.submit_button);
        mSubmitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle a user guess
                String guess = mEnterGuess.getText().toString();
                boolean userWon = game.checkGuess(guess);
                mNumGuesses = game.getNumGuessesLeft();
                if (userWon) {
                    mUserScore++;
                    setReturnResult();
                    String toastText = "Great job! You won.";
                    makeToast(toastText);
                }
                else {
                    if (game.isGameOver()) {
                        mComputerScore++;
                        setReturnResult();
                        String toastText = "Sorry, I won this time.";
                        makeToast(toastText);
                    }
                }
                updateUI();
            }
        });

        mBackButton = view.findViewById(R.id.back_button);
        mBackButton.setVisibility(View.INVISIBLE);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReturnResult();
                // TODO: Go back (duh)
            }
        });

        mNewGameButton = view.findViewById(R.id.new_game_button);
        mNewGameButton.setVisibility(View.INVISIBLE);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME actually load in words
                // Create a new game
                if (mGameOnHard) {
                    game = new HangmanGame("antidisestablishmentarianism", mNumGuesses);
                }
                else {
                    game = new HangmanGame("create", mNumGuesses);
                }

                // Re-enable everything
                mGuessesLeft.setVisibility(View.VISIBLE);
                mNumGuesses = getActivity().getIntent().getIntExtra(EXTRA_NUM_GUESSES, 10);
                mEnterGuess.setEnabled(true);
                mSubmitGuessButton.setEnabled(true);
                mBackButton.setVisibility(View.INVISIBLE);
                mNewGameButton.setVisibility(View.INVISIBLE);
                updateUI();
            }
        });

        return view;
    }

    private void updateUI() {
        if (game.isGameOver()) {
            // Disable everything
            mGuessesLeft.setVisibility(View.INVISIBLE);
            mEnterGuess.setEnabled(false);
            mSubmitGuessButton.setEnabled(false);
            mBackButton.setVisibility(View.VISIBLE);
            mNewGameButton.setVisibility(View.VISIBLE);
        }

        // Update fields with new data
        mScoreDisplay.setText(String.format(getString(R.string.score), mUserScore, mComputerScore));
        mGuessesLeft.setText(String.format(getString(R.string.guesses_left), mNumGuesses));
        mDisplayWord.setText(game.getWordToDisplay());
        mEnterGuess.setText("");
    }

    private void makeToast(String text) {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.TOP, 0, 0);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.background_toast);
        toast.show();
    }
}
