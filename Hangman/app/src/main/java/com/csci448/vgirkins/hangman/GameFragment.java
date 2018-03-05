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
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Tori on 3/3/2018.
 * Citations:   https://randomwordgenerator.com/
 *              https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
 */

public class GameFragment extends Fragment {
    private static final String EXTRA_USER_SCORE = "com.csci448.vgirkins.hangman.user_score";
    private static final String EXTRA_COMPUTER_SCORE = "com.csci448.vgirkins.hangman.computer_score";
    private static final String EXTRA_NUM_GUESSES = "com.csci448.vgirkins.hangman.num_guesses";
    private static final String EXTRA_GAME_ON_HARD = "com.csci448.vgirkins.hangman.game_on_hard";

    // For recovering a HangmanGame
    private static final String KEY_WORD = "com.csci448.vgirkins.hangman.word";
    private static final String KEY_USER_WORD = "com.csci448.vgirkins.hangman.user_word";
    private static final String KEY_GUESSED_LETTERS = "com.csci448.vgirkins.hangman.guessed_letters";

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Recover values and recreate game
            mUserScore = savedInstanceState.getInt(EXTRA_USER_SCORE, 0);
            mComputerScore = savedInstanceState.getInt(EXTRA_COMPUTER_SCORE, 0);
            mNumGuesses = savedInstanceState.getInt(EXTRA_NUM_GUESSES, 10);
            mGameOnHard = savedInstanceState.getBoolean(EXTRA_GAME_ON_HARD, false);
            String word = savedInstanceState.getString(KEY_WORD);
            String userWord = savedInstanceState.getString(KEY_USER_WORD);
            String guessedLetters = savedInstanceState.getString(KEY_GUESSED_LETTERS);

            game = new HangmanGame(word, userWord, mNumGuesses, guessedLetters);
        }
        else {
            // Load in values from calling activity
            mUserScore = getArguments().getInt(EXTRA_USER_SCORE);
            mComputerScore = getActivity().getIntent().getIntExtra(EXTRA_COMPUTER_SCORE, 0);
            mNumGuesses = getActivity().getIntent().getIntExtra(EXTRA_NUM_GUESSES, 10);
            mGameOnHard = getActivity().getIntent().getBooleanExtra(EXTRA_GAME_ON_HARD, false);

            game = new HangmanGame(pickWord(), mNumGuesses);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(EXTRA_USER_SCORE, mUserScore);
        savedInstanceState.putInt(EXTRA_COMPUTER_SCORE, mComputerScore);
        savedInstanceState.putInt(EXTRA_NUM_GUESSES, mNumGuesses);
        savedInstanceState.putBoolean(EXTRA_GAME_ON_HARD, mGameOnHard);

        savedInstanceState.putString(KEY_WORD, game.getWord());
        savedInstanceState.putString(KEY_USER_WORD, game.getUserWord());
        savedInstanceState.putString(KEY_GUESSED_LETTERS, game.getGuessedLetters());
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
                getActivity().onBackPressed();
            }
        });

        mNewGameButton = view.findViewById(R.id.new_game_button);
        mNewGameButton.setVisibility(View.INVISIBLE);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game = new HangmanGame(pickWord(), mNumGuesses);

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

    // Selects the appropriate file and pulls a random word from it
    private String pickWord() {
        String fileName = mGameOnHard ? mHardWordsFile : mEasyWordsFile;
        String word = "";

        // Read file
        try {
            InputStream inputStream = getContext().getAssets().open(fileName);
            Scanner scanner = new Scanner(inputStream);
            int targetIndex = (int)(Math.random() * mNumWords);
            int i = 0;
            word = scanner.nextLine();
            while (scanner.hasNextLine() && i < targetIndex) {
                word = scanner.nextLine();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Remove space from word because I'm too lazy to write a script to remove spaces from file
        word = word.replace(" ", "");
        return word;
    }

    // Update the result with changed values
    public void setReturnResult() {
        Intent resultIntent = new Intent();

        // The only two values which may get changed in this activity
        resultIntent.putExtra(EXTRA_USER_SCORE, mUserScore);
        resultIntent.putExtra(EXTRA_COMPUTER_SCORE, mComputerScore);

        getActivity().setResult(Activity.RESULT_OK, resultIntent);
    }
}
