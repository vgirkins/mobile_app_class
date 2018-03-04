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

/**
 * Created by Tori on 3/2/2018.
 */

public class OptionsFragment extends Fragment {
    private EditText mNumGuessesField;
    private Button mSetGuessesButton;
    private CheckBox mHardCheckbox;
    private Button mClearScoreButton;

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

        View view = inflater.inflate(R.layout.fragment_options, container, false);

        mNumGuessesField = view.findViewById(R.id.num_guesses_field);
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
