package com.example.cep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class AppInfo extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    boolean isPermissionGranted;
    GoogleMap mMap;
    SharedPreferences sp;
    String user_fname, username, user_email, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        /* ------------------------ getting intents ---------------------------- */

        user_fname = getIntent().getStringExtra("user_fname");
        username = getIntent().getStringExtra("username");
        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");

        /* ------------------------------- Hooks ---------------------------------------*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);


        /* ----------------------------- Navigation Drawer --------------------------- */

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        /* -----------------------------   --------------------------- */

        checkPermission();
        if (isPermissionGranted){
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location_map);
            supportMapFragment.getMapAsync(AppInfo.this);
        }



        /* -----------------------------   --------------------------- */

    }
    private void checkPermission(){
        Dexter.withContext(AppInfo.this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.continuePermissionRequest();

            }
        }).check();


    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent intent1 = new Intent(AppInfo.this, UserDashboard.class);

            intent1.putExtra("user_fname", user_fname);
            intent1.putExtra("username", username);
            intent1.putExtra("user_email", user_email);
            intent1.putExtra("user_password", user_password);

            startActivity(intent1);
            finishAffinity();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng company = new LatLng(24.8607, 67.0011);
        mMap.addMarker(new MarkerOptions().position(company).title("HiveFive & Co."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(company));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.home:
                Intent intent = new Intent(AppInfo.this, MainScreen.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
                finishAffinity();
                break;
            case R.id.dashboard:
                Intent intent1 = new Intent(AppInfo.this, UserDashboard.class);

                intent1.putExtra("user_fname", user_fname);
                intent1.putExtra("username", username);
                intent1.putExtra("user_email", user_email);
                intent1.putExtra("user_password", user_password);

                startActivity(intent1);
                finishAffinity();
                break;
            case R.id.logout_but:

                sp = getSharedPreferences("datafile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if(sp.contains("Username")){

                    editor.remove("Username");
                    editor.remove("Full Name");
                    editor.remove("Email");
                    editor.remove("Password");
                    editor.commit();

                }
                Intent intent2 = new Intent(AppInfo.this, LoginScreen.class);
                startActivity(intent2);

                finishAffinity();
                break;
        }drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }

}