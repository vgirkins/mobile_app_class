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

public class OptionsActivity extends AppCompatActivity {
    // private static final String MY_EXTRA = "com.csci448.vgirkins.hangman.myExtra";

    protected OptionsFragment createFragment() {
        return new OptionsFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_options);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container_options, fragment)
                    .commit();
        }

    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, OptionsActivity.class);
        // put extras
        return intent;
    }
}
