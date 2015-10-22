package com.example.pearl.materialdesignapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pearl.materialdesignapp.adapter.ChangeThemes;
import com.example.pearl.materialdesignapp.adapter.ExpandableListAdapter;
import com.example.pearl.materialdesignapp.adapter.NavigationDrawerAdapter;
import com.example.pearl.materialdesignapp.controller.Singleton;
import com.example.pearl.materialdesignapp.model.DBUsers;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private boolean isOpened;
    public  static MainActivity activity;
    Button imgButton;
    ImageView img;
    private String home[] = {
      "Home", "Email" , "Themes", "Profile", "Location"
    };
    ExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    Bitmap bitmap;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        Singleton.getInstance().setActivityName(this.getClass().getName());
        setToolBar();
        setDrawer();
        if (Singleton.getInstance().getSelectedValue() == Singleton.VALUE0) {
            if (ifExists()) {
                showEntryDialog();
                Singleton.getInstance().setSelectedValue(Singleton.VALUE1);
            }
        }
        img = (ImageView) findViewById(R.id.web_image);
        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);
        onCollapse();
        setButton();
        setTheme();
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
    public void launchMapActivity(){
        startActivity(new Intent(this,MapActivity.class));
        finish();
    }
    public void launchEmailActivity (){

        startActivity(new Intent(this,EmailActivity.class));
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

    public void collectImage(View view) {
        LoadImage task = new LoadImage();
        task.execute(new String[]{"http://aviationweather.gov/data/obs/sat/intl/ir_ICAO-E.jpg"});
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void prepareListData(){
        TextView text = (TextView) findViewById(R.id.lblListItem);
        listDataHeader  = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Animation");
        listDataHeader.add("Satellite Image");
        listDataHeader.add("Temperatures");

        List<String> Animation = new ArrayList<String>();
        Animation.add("Animation Page");

        final List<String> Upload_Photo  = new ArrayList<String>();
        Upload_Photo.add("Download Satellite Image");
        //Upload_Photo.add(getImage(getBitmap()));
//        animate android.os.Handler().postDelayed(animate Runnable() {
//            @Override
//            public void run() {
//                Upload_Photo.add(getImage(getBitmap()));
//            }
//        },15000);


        List<String> Temperatures = new ArrayList<String>();
        Temperatures.add("Satellite Images");
        Temperatures.add("Daily Weather Report");

        listDataChild.put(listDataHeader.get(0), Animation);
        listDataChild.put(listDataHeader.get(1), Upload_Photo);
        listDataChild.put(listDataHeader.get(2), Temperatures);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0 && childPosition == 0){
                    Intent intent = new Intent(getApplication(),AnimationActivity.class);
                    startActivity(intent);
                }
                if (groupPosition == 1 && childPosition == 0){
                    collectImage(img);
                    if (img != null)
                        img.setVisibility(View.VISIBLE);
                }
                if (groupPosition==2 && childPosition==0){
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    String str = "main";
                    intent.putExtra(str,"http://www.pmd.gov.pk/");
                    startActivity(intent);
                } else if (groupPosition==2 && childPosition==1){
                    Intent intent = new Intent(getApplicationContext(), WebActivity.class);
                    String str = "main";
                    intent.putExtra(str, "http://nwfc.pmd.gov.pk/media/index.php");
                    startActivity(intent);
                }

                return false;
            }
        });

    }

    private void onCollapse(){
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup){
                    expandableListView.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            }
        });
    }

    public String getImage(Bitmap bitmap){
        SpannableStringBuilder sb = new SpannableStringBuilder("");
        Drawable d = new BitmapDrawable(getResources(),bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d,ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb.toString();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void removeImg(){
        img.setVisibility(View.GONE);
    }
    public void setButton(){
        imgButton = (Button) findViewById(R.id.image_button);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              removeImg();

            }
        });
    }
}
