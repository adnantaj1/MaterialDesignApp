package com.example.pearl.materialdesignapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.pearl.materialdesignapp.R;

/**
 * Created by Pearl on 9/30/2015.
 */
public class ThemesActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.theme_toolbar);
        setSupportActionBar(toolbar);
    }
}
