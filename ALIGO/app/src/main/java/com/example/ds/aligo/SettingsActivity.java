package com.example.ds.aligo;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SettingsActivity extends AppCompatActivity {
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        settingsFragment = new SettingsFragment();
        settingsFragment.setContext(this);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();

    }
}

