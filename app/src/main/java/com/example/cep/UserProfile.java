package com.example.cep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String user_fname, username, user_email, user_password;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextInputLayout f_name, phone, email, password;
    Button update_prof;
    SharedPreferences sp;
//    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /* ------------------------ getting intents ---------------------------- */

        user_fname = getIntent().getStringExtra("user_fname");
        username = getIntent().getStringExtra("username");
        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");

        /* ------------------------------- Hooks ---------------------------------------*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        f_name = findViewById(R.id.full_name_profile);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        update_prof = findViewById(R.id.update_prof);


        /* ----------------------------- Navigation Drawer --------------------------- */

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /* -----------------------------  --------------------------- */

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        if (ActivityCompat.checkSelfPermission(UserProfile.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            getlocation();
//
//        } else {
//            ActivityCompat.requestPermissions(UserProfile.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//        }
//
//
//    }
//      ----------------------------   -------------------------------------

        f_name.getEditText().setText(user_fname);
        email.getEditText().setText(user_email);
        password.getEditText().setText(user_password);



//    ----------------------------------  --------------------------------------

        update_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = f_name.getEditText().getText().toString();
                String number = phone.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();



                UpdateProfileData updateProfileData = new UpdateProfileData(name, username, mail, number, pass);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Accounts");
                myRef.child(username).setValue(updateProfileData);
            }
        });



    }
//    private void getlocation() {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location = task.getResult();
//
//                if (location != null) {
//                    Geocoder geocoder = new Geocoder(UserProfile.this, Locale.getDefault());
//
//                    try {
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
//                                location.getLongitude(), 1);
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//            }
//        });
//
//    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent intent1 = new Intent(UserProfile.this, UserDashboard.class);

            intent1.putExtra("user_fname", user_fname);
            intent1.putExtra("username", username);
            intent1.putExtra("user_email", user_email);
            intent1.putExtra("user_password", user_password);

            startActivity(intent1);
            finishAffinity();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.home:
                Intent intent = new Intent(UserProfile.this, MainScreen.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
                finishAffinity();
                break;
            case R.id.dashboard:
                Intent intent1 = new Intent(UserProfile.this, UserDashboard.class);

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
                Intent intent2 = new Intent(UserProfile.this, LoginScreen.class);
                startActivity(intent2);

                finishAffinity();
                break;
        }drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
}