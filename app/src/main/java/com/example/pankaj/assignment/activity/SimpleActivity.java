package com.example.pankaj.assignment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pankaj.assignment.R;
import com.example.pankaj.assignment.application.ApplicationData;
import com.example.pankaj.assignment.preferences.PreferenceManager;

/**
 * Created by Pankaj on 21-06-2017.
 */

public class SimpleActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        preferenceManager = new PreferenceManager(this);
        preferenceManager.putPreferenceIntValues(ApplicationData.STATE,1);
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            *//*case R.id.action_save:
                updateEducation();
                return true;*//*
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}
