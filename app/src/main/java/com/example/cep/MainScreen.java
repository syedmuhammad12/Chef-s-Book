package com.example.cep;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String user_fname, username, user_email, user_password;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView drawer_head_text;

    SharedPreferences sp;

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        user_fname = getIntent().getStringExtra("user_fname");
        username = getIntent().getStringExtra("username");
        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");



        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new RecFragment()).commit();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        drawer_head_text = findViewById(R.id.drawer_head_text);
        user_fname = user_fname;



        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);




    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.home:
                break;
            case R.id.dashboard:
                Intent intent = new Intent(MainScreen.this, UserDashboard.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
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
                Intent intent1 = new Intent(MainScreen.this, LoginScreen.class);
                startActivity(intent1);

                finish();
                break;
            }drawerLayout.closeDrawer(GravityCompat.START);

            return true;
    }
}