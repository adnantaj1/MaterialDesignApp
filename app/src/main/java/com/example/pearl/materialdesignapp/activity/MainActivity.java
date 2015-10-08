package com.example.pearl.materialdesignapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pearl.materialdesignapp.adapter.ChangeThemes;
import com.example.pearl.materialdesignapp.adapter.NavigationDrawerAdapter;
import com.example.pearl.materialdesignapp.controller.FileHandler;
import com.example.pearl.materialdesignapp.controller.Singleton;
import com.example.pearl.materialdesignapp.model.DBUsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private boolean isOpened;
    public  static MainActivity activity;

    private String home[] = {
      "Home", "Email" , "Themes", "Profile"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        Singleton.getInstance().setActivityName(this.getClass().getName());
        setToolBar();
        //setTheme();
        setDrawer();
        setTheme();
        if (Singleton.getInstance().getSelectedValue() == Singleton.VALUE0) {
            if (ifExists()) {
                showEntryDialog();
                Singleton.getInstance().setSelectedValue(Singleton.VALUE1);
            }
        }
        FileHandler.getsInstance().writeCache(this);
        FileHandler.getsInstance().readCache(this);
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



    private void setTheme(){
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
    }
    public void launchThemeActivity (){

        startActivity(new Intent(this,ThemesActivity.class));
        finish();
    }
    public void launchProfileActivity(){
        startActivity(new Intent(this,ProfileActivity.class));
        finish();
    }

    private void showEntryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle("User Information");
        String name = "";
        DBUsers dbs = new DBUsers(this);
        dbs.open();
        Cursor cursor = dbs.getAllRecords();
        if (cursor.moveToFirst()) {
            name = cursor.getString(1);
        }
        dbs.close();
        builder.setMessage(name);
        final EditText u_password = new EditText(this);
        u_password.setTransformationMethod(new PasswordTransformationMethod());

        builder.setView(u_password);

        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = u_password.getText().toString();
                DBUsers db = new DBUsers(getApplicationContext());
                db.open();
                Cursor c = db.getAllRecords();
                if (c.moveToFirst()) {
                    if (c.getString(2).equalsIgnoreCase(password)) {
                        dialog.dismiss();
                    } else {
                        System.exit(0);
                        //finish();
                    }
                    db.close();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
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

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
}
