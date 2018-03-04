package com.csci448.vgirkins.hangman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Tori on 3/3/2018.
 */

public class GameActivity extends AppCompatActivity {
    private static final String EXTRA_USER_SCORE = "com.csci448.vgirkins.hangman.user_score";
    private static final String EXTRA_COMPUTER_SCORE = "com.csci448.vgirkins.hangman.computer_score";
    private static final String EXTRA_NUM_GUESSES = "com.csci448.vgirkins.hangman.num_guesses";
    private static final String EXTRA_GAME_ON_HARD = "com.csci448.vgirkins.hangman.game_on_hard";

    private int mUserScore;
    private int mComputerScore;
    private int mNumGuesses;
    private boolean mGameOnHard;

    public static Intent newIntent(Context packageContext, int userScore, int computerScore, int numGuesses, boolean isOnHard) {
        Intent intent = new Intent(packageContext, GameActivity.class);
        intent.putExtra(EXTRA_USER_SCORE, userScore);
        intent.putExtra(EXTRA_COMPUTER_SCORE, computerScore);
        intent.putExtra(EXTRA_NUM_GUESSES, numGuesses);
        intent.putExtra(EXTRA_GAME_ON_HARD, isOnHard);
        return intent;
    }

    protected Fragment createFragment() {
        int userScore = getIntent().getIntExtra(EXTRA_USER_SCORE, 0);
        int computerScore = getIntent().getIntExtra(EXTRA_COMPUTER_SCORE, 0);
        int numGuesses = getIntent().getIntExtra(EXTRA_NUM_GUESSES, 10);
        boolean gameOnHard = getIntent().getBooleanExtra(EXTRA_GAME_ON_HARD, false);
        return GameFragment.newInstance(userScore, computerScore, numGuesses, gameOnHard);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_game);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_game, fragment)
                    .commit();
        }

    }
}
