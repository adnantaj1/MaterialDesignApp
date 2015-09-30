package com.example.pearl.materialdesignapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.pearl.materialdesignapp.R;
import com.example.pearl.materialdesignapp.adapter.NavigationDrawerAdapter;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private boolean isOpened;
    public  static MainActivity activity;

    private String home[] = {
      "Home", "Email" , "Themes"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        setToolBar();
        //setTheme();
        setDrawer();
    }

    private void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void closeDrawer() {

        this.drawerLayout.closeDrawers();
    }
    public void homeScreen(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setDrawer(){
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.adapter = new NavigationDrawerAdapter(home);
        this.recyclerView.setAdapter(adapter);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                isOpened = true;

            }
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                isOpened = false;

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    private void setTheme(){
        toolbar.setBackgroundColor(Color.parseColor("#1e90ff"));
    }
    public void launchThemeActivity (){
        startActivity(new Intent(this,ThemesActivity.class));
    }
}
