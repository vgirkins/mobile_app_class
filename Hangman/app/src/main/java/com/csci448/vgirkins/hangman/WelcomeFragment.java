package com.csci448.vgirkins.hangman;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class WelcomeFragment extends Fragment {
    private Button mPlayButton;
    private Button mOptionsButton;
    private Button mQuitButton;


    public interface willIEvenNeedThis {

    }

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

        View view = inflater.inflate(R.layout.activity_welcome, container, false);

        mPlayButton = view.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO start the game activity
            }
        });

        mOptionsButton = view.findViewById(R.id.options_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO start the options activity
            }
        });

        mQuitButton = view.findViewById(R.id.quit_button);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO close the app
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