// Created by: Victoria Girkins
// Sources: https://stackoverflow.com/questions/17719634/how-to-exit-an-android-app-using-code#17720065

package com.csci448.vgirkins.hangman;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {

    private int mUserScore;
    private int mComputerScore;
    private int mNumGuesses;
    private boolean mGameOnHard;

    private Button mPlayButton;
    private Button mOptionsButton;
    private Button mQuitButton;
    private static final int REQUEST_CODE_OPTIONS = 0;
    private static final int REQUEST_CODE_GAME = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserScore = 0;
        mComputerScore = 0;
        mNumGuesses = 10;
        mGameOnHard = false;
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
                startActivityForResult(intent, REQUEST_CODE_GAME);
            }
        });

        mOptionsButton = view.findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OptionsActivity.newIntent(getActivity(), mUserScore, mComputerScore, mNumGuesses, mGameOnHard);
                startActivityForResult(intent, REQUEST_CODE_OPTIONS);
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

    private void updateUI() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_OPTIONS) {
        }

        else if (requestCode == REQUEST_CODE_GAME) {

        }
    }
}