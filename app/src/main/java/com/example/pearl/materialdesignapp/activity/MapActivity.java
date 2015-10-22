package com.example.pearl.materialdesignapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pearl.materialdesignapp.adapter.ChangeThemes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

/**
 * Created by Pearl on 10/16/2015.
 */
public class MapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Toolbar toolbar;
    private LinearLayout layout;
    private static final String TAG = MapActivity.class.getSimpleName();
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private boolean mRequestingLocationUpdate = false;
    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL = 10000;
    private static int FASTEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;
    private TextView lblLocation;
    private Button btnShowLocation, btnStartLocationUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        layout = (LinearLayout) findViewById(R.id.map_layout);
        lblLocation = (TextView) findViewById(R.id.lblLocation);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
        btnStartLocationUpdate = (Button) findViewById(R.id.btnLocationUpdates);
        if (checkPlayService()){
            buildGoogleApiClient();
        }
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLocation();
                Toast.makeText(getApplication(),"test",Toast.LENGTH_SHORT).show();
            }
        });
        setToolbar();
        setTheme();
    }

    private boolean checkPlayService(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICE_RESOLUTION_REQUEST).show();
            }else {
                Toast.makeText(getApplicationContext(),"This Device is not Supported",Toast.LENGTH_LONG).show();
                finish();
            }return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private void displayLocation(){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null){
            double latitude = 0.0;
            double longitude = 0.0;
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            //Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            lblLocation.setText(latitude+", "+longitude);

        }else {
            lblLocation.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayService();
    }

    @Override
    public void onConnected(Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG,"Connection failed: ConnectionResult.getErrorCode()"+ connectionResult.getErrorCode());
        Toast.makeText(this, "CONNECTION FAILED", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setTheme(){
        toolbar.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
        layout.setBackgroundColor(Color.parseColor(ChangeThemes.getsInstance().loadTheme()));
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();
        displayLocation();
    }
}
