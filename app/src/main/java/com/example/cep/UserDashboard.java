package com.example.cep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    String user_fname, username, user_email, user_password;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView card_user_profile, card_user_recipes, card_add_recipe, card_user_help;
    SharedPreferences sp;



    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        /* ------------------------ getting intents ---------------------------- */

        user_fname = getIntent().getStringExtra("user_fname");
        username = getIntent().getStringExtra("username");
        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");


        /* ------------------------------- Hooks ---------------------------------------*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        card_user_profile = findViewById(R.id.card_profile_info);
        card_user_recipes = findViewById(R.id.card_your_recipes);
        card_add_recipe = findViewById(R.id.card_add_recipes);
        card_user_help = findViewById(R.id.card_user_help);

        /* ----------------------------- Navigation Drawer --------------------------- */

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.dashboard);

        /* ----------------------------- intenting user profile screen --------------------------- */

        card_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDashboard.this, UserProfile.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);

            }
        });


        /* ----------------------------- intenting add recipe screen --------------------------- */


        card_add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDashboard.this, AddRecipe.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
            }
        });



        /* ----------------------------- intenting your recipe screen --------------------------- */




        /* ----------------------------- intenting user help screen --------------------------- */

        card_user_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UserDashboard.this, AppInfo.class);
                intent1.putExtra("user_fname", user_fname);
                intent1.putExtra("username", username);
                intent1.putExtra("user_email", user_email);
                intent1.putExtra("user_password", user_password);
                startActivity(intent1);

            }
        });





    }

    /* --------------------------------------------------   ---------------------------------------*/

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent intent = new Intent(UserDashboard.this, MainScreen.class);

            intent.putExtra("user_fname", user_fname);
            intent.putExtra("username", username);
            intent.putExtra("user_email", user_email);
            intent.putExtra("user_password", user_password);

            startActivity(intent);
            finishAffinity();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.home:
                Intent intent = new Intent(UserDashboard.this, MainScreen.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
                finishAffinity();
                break;
            case R.id.dashboard:
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
                Intent intent1 = new Intent(UserDashboard.this, LoginScreen.class);
                startActivity(intent1);

                finishAffinity();
                break;
        }drawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }
}