package com.example.pearl.materialdesignapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.pearl.materialdesignapp.model.GameView;

/**
 * Created by Pearl on 10/14/2015.
 */
public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}
