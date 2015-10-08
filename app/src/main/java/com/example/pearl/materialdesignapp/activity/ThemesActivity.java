package com.example.pearl.materialdesignapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pearl.materialdesignapp.adapter.ChangeThemes;
import com.example.pearl.materialdesignapp.adapter.ThemesAdapter;
import com.example.pearl.materialdesignapp.controller.FileHandler;
import com.example.pearl.materialdesignapp.controller.Singleton;

/**
 * Created by Pearl on 9/30/2015.
 */
public class ThemesActivity extends AppCompatActivity {
    private TextView textView;
    private Toolbar toolbar;
    //private ArrayAdapter<String> adapter;
    ListView listView;
    ThemesAdapter adapter;
    public static ThemesActivity activity;

    private String themes[]={"Blue", "Red", "Pink", "Yellow", "Orange","DarkBrown", "Mixed", "Voilet"};
    private String colors[]={"#3366FF", "#800000", "#FF0066", "#FFCC00", "#FF6600", "#663300","#00CCCC", "#CC00CC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        activity = ThemesActivity.this;
        textView = (TextView) findViewById(R.id.textView);
        Singleton.getInstance().setActivityName(this.getClass().getName());
        FileHandler.getsInstance().writeCache(this);
        FileHandler.getsInstance().readCache(this);
        setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //selectTheme();
        //setComponents();
        setRow();
        setTheme();
    }

//    private void setComponents() {
//        LinearLayout  mainLayout = (LinearLayout) findViewById(R.id.main_layout);
//        for (int i = 0; i < themes.length; i++) {
//            mainLayout.addView(new MyView(getApplicationContext(), themes[i], colors[i]));
//        }
//    }

    private void setRow(){
        listView = (ListView) findViewById(R.id.theme_listView);
        adapter = new ThemesAdapter(getApplicationContext(), R.layout.theme_listitem, themes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),"this",Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_BLUE);
                        break;
                    case 1:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_RED);
                        break;
                    case 2:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_PNK);
                        break;
                    case 3:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_YELLOW);
                        break;
                    case 4:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_ORANGE);
                        break;
                    case 5:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_DARKBROWB);
                        break;
                    case 6:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_MIXED);
                        break;
                    case 7:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_VIOLET);
                        break;
                    default:
                        ChangeThemes.getsInstance().setSelectedTheme(ChangeThemes.SelectedTheme.THEME_PNK);
                        break;

                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    private void setToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.theme_toolbar);
        setSupportActionBar(toolbar);
    }

//    private void selectTheme(){
//        View v = getLayoutInflater().inflate(R.layout.theme_listitem, null);
//        TextView tv = (TextView) v.findViewById(R.id.textView2);
//        adapter = new ArrayAdapter<String>(this, R.layout.theme_listitem, themes);
//       // ListView listView = (ListView) findViewById(R.id.theme_listView);
//       // listView.setAdapter(adapter);
//    }

//    private class MyView extends TextView {
//
//        private String title;
//        private String color;
//        /**
//         *
//         * @param context
//         * @param title
//         * @param color
//         */
//        public MyView(Context context, final String title, final String color) {
//            super(context);
//            this.title = title;
//            this.color = color;
//            make();
//        }
//
//        private void make() {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(0,5,0,0);
//            setText(title);
//            setTextSize(32.0f);
//            setBackgroundColor(Color.parseColor(this.color));
//            setLayoutParams(params);
//            setGravity(Gravity.CENTER);
//            setPadding(5, 5, 5, 5);
//        }
//    }

    /**
     *
     * @return
     */
    public String getColors(int i) {
        return colors[i];
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


    public void setTheme(){
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        textView.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
    }
}/** end class. */
