package com.example.pearl.materialdesignapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pearl.materialdesignapp.adapter.ChangeThemes;
import com.example.pearl.materialdesignapp.model.DBUsers;

/**
 * Created by Pearl on 10/2/2015.
 */
public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    FloatingActionButton btn;
    TextView textView;
    Button btnSubmit;
    Button btnReset;
    Button btnEdit;
    EditText textName;
    EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView = (TextView) findViewById(R.id.textView);
        btnSubmit = (Button) findViewById(R.id.button);
        btnEdit = (Button) findViewById(R.id.button_edit);
        btnReset = (Button) findViewById(R.id.button_reset);
        textName = (EditText) findViewById(R.id.editTextName);
        textPassword = (EditText) findViewById(R.id.editTextPassword);
        setToolbar();
        goBack();
        setTheme();
        check();
        saveUser();
        setData();
    }

    private void goBack(){
        btn = (FloatingActionButton) findViewById(R.id.back_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void setToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
    }

    public void saveUser(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"clicked", Toast.LENGTH_SHORT).show();
                DBUsers db = new DBUsers(getApplication());
                db.open();
                long insert = db.insertUsers(textName.getText().toString(),textPassword.getText().toString());
                db.close();
                Toast.makeText(getApplicationContext(),"Saved "+ insert, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplication(), MainActivity.class));
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUsers db = new DBUsers(getApplication());
                db.open();
                //db.updateUsers()
                db.close();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textName.setText("");
                textPassword.setText("");
            }
        });
    }

    private boolean ifExists(){
        DBUsers db = new DBUsers(this);
        db.open();
        Cursor c = db.getAllRecords();
        c.moveToFirst();
        Toast.makeText(getApplicationContext(), "" + c.getCount() + "", Toast.LENGTH_LONG).show();
        if (c.getCount() > 0){
            //btnSubmit.setEnabled(false);
            db.close();
            return true;
        }
//        else {
//           // btnSubmit.setEnabled(true);
//        }
        db.close();
        return false;
    }

    public void setTheme(){
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        textView.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        btnSubmit.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        btnReset.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        btnEdit.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
    }

    private void check() {
        if (ifExists()) {
            btnSubmit.setEnabled(false);
            btnEdit.setEnabled(true);
        }else {

        }
    }

    private void setData(){
        if (ifExists()){
            DBUsers db = new DBUsers(this);
            db.open();
            Cursor c = db.getAllRecords();
            if (c.moveToFirst()){
                textName.setText(c.getString(0));
                textPassword.setText(c.getString(1));
            }
        }else {}
    }

    private boolean ifTextChange(){
        textName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnEdit.setEnabled(true);
            }
        });
        return false;
    }
}
