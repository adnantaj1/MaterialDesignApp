package com.example.pearl.materialdesignapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pearl.materialdesignapp.adapter.ChangeThemes;

/**
 * Created by Pearl on 10/14/2015.
 */
public class EmailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText subjectText;
    private EditText msgText;
    private Button sendButt;
    private Button resetButt;
    private Button cancelButt;
    private TextView titleView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_right_push_in, R.anim.anim_right_push_in_2);
        setContentView(R.layout.activity_email);
        setToolbar();
        this.linearLayout = (LinearLayout) findViewById(R.id.title_layout);
        this.subjectText = (EditText) findViewById(R.id.email_subject_et);
        this.msgText = (EditText) findViewById(R.id.email_message_et);
        this.sendButt = (Button) findViewById(R.id.email_send_button);
        this.sendButt.setOnClickListener(this);
        this.resetButt = (Button) findViewById(R.id.email_reset_button);
        this.resetButt.setOnClickListener(this);
        this.cancelButt = (Button) findViewById(R.id.email_cancel_button);
        this.cancelButt.setOnClickListener(this);
        setTheme();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == this.sendButt){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adnansulehri1@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,this.subjectText.getText().toString());
            intent.putExtra(Intent.EXTRA_TEXT,this.msgText.getText().toString());
            try {
                startActivity(intent);
            }catch (android.content.ActivityNotFoundException e){
                Toast.makeText(this,"There are no email clients installed on your ",Toast.LENGTH_SHORT).show();
            }finally {
                finish();
            }
        } else if (view == this.resetButt){
            this.msgText.setText("");
            this.subjectText.setText("");
        } else if (view == this.cancelButt){
            onBackPressed();
        }
    }

    private void setTheme(){
        sendButt.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        resetButt.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        cancelButt.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        linearLayout.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));

    }
}
