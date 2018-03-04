package com.csci448.vgirkins.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Tori on 3/3/2018.
 * Citations:   https://randomwordgenerator.com/
 *              https://stackoverflow.com/questions/2788080/java-how-to-read-a-text-file
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

    private String mHardWordsFile = "hard.txt";
    private String mEasyWordsFile = "easy.txt";
    private int mNumWords = 500;

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

        String fileName = mGameOnHard ? mHardWordsFile : mEasyWordsFile;
        String word;
        // Read file

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            word = br.readLine();
            int targetIndex = (int)Math.random() * mNumWords;
            int i = 0;
            while (word != null && i < targetIndex) {
                word = br.readLine();
                i++;
            }
            br.close();
        } catch (Exception e) {
            Log.d("icecream", e.getMessage());
            e.printStackTrace();
            return;
        }


        // Remove space from word because I'm too lazy to write a script to remove spaces from file
        word = word.replace(" ", "");
        Log.d("icecream", word);
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
