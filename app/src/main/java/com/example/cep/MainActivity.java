package com.example.cep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static int splash_scr = 4000;

    Animation top, bottom;
    ImageView main_ico;
    TextView app_nam, app_slogan;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        main_ico = findViewById(R.id.main_screen_icon);
        app_nam = findViewById(R.id.app_name);
        app_slogan = findViewById(R.id.app_slogan);

        main_ico.setAnimation(top);
        app_nam.setAnimation(bottom);
        app_slogan.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sp = getSharedPreferences("datafile", MODE_PRIVATE);
                if(sp.contains("Username")){

                    Intent intent = new Intent(MainActivity.this, MainScreen.class);
                    intent.putExtra("user_fname", sp.getString("Full Name", ""));
                    intent.putExtra("username", sp.getString("Username", ""));
                    intent.putExtra("user_email", sp.getString("Email", ""));
                    intent.putExtra("user_password", sp.getString("Password", ""));
                    startActivity(intent);
                    finish();
                }

                else {
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, splash_scr);
    }
}