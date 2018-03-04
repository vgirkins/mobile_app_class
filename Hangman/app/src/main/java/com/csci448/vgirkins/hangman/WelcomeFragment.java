// Created by: Victoria Girkins
// Sources: https://stackoverflow.com/questions/17719634/how-to-exit-an-android-app-using-code#17720065

package com.csci448.vgirkins.hangman;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {

    private int userScore;
    private int computerScore;
    private int numGuesses;
    private boolean gameOnHard;

    private Button mPlayButton;
    private Button mOptionsButton;
    private Button mQuitButton;
    private static final int REQUEST_CODE_SCORE = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        userScore = 0;
        computerScore = 0;
        numGuesses = 10;
        gameOnHard = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        mPlayButton = view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = GameActivity.newIntent(getActivity());
                // TODO put extras
                startActivityForResult(intent, REQUEST_CODE_SCORE);
            }
        });

        mOptionsButton = view.findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OptionsActivity.newIntent(getActivity(), userScore, computerScore, numGuesses, gameOnHard);
                startActivityForResult(intent, REQUEST_CODE_SCORE);
            }
        });

        mQuitButton = view.findViewById(R.id.quit_button);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
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