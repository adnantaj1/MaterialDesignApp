package com.example.pearl.materialdesignapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.pearl.materialdesignapp.adapter.ChangeThemes;

/**
 * Created by Pearl on 10/13/2015.
 */
public class WebActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setToolBar();
        webView();
    }

    private void webView(){
//        WebView myWebView = (WebView) findViewById(R.id.webinfo);
//        myWebView.loadUrl("http://www.pmd.gov.pk/");
//        //myWebView.setBackgroundResource(R.drawable.lbg);
//        myWebView.setBackgroundColor(Color.TRANSPARENT);
//        myWebView.getSettings().setJavaScriptEnabled(true);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString = extras.getString("main");
        WebView myWebView = (WebView) findViewById(R.id.webinfo);
        myWebView.loadUrl(newString);
        //myWebView.setBackgroundResource(R.drawable.lbg);
        myWebView.setBackgroundColor(Color.TRANSPARENT);
        myWebView.getSettings().setJavaScriptEnabled(true);

    }
    private void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
    }

}
