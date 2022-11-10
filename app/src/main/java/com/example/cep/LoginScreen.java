package com.example.cep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    TextInputLayout ent_user, ent_pass;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ent_user = findViewById(R.id.username);
        ent_pass = findViewById(R.id.password);

        // New User Button Listener
        Button but = findViewById(R.id.new_user_but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, SignUpClass.class);
                startActivity(intent);
            }
        });


    }

    private Boolean validateUsername() {
        String val = ent_user.getEditText().getText().toString();
        if (val.isEmpty()) {
            ent_user.setError("Field cannot be empty");
            return false;
        } else {
            ent_user.setError(null);
            ent_user.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = ent_pass.getEditText().getText().toString();
        if (val.isEmpty()) {
            ent_pass.setError("Field cannot be empty");
            return false;
        } else {
            ent_pass.setError(null);
            ent_pass.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            checkUser();
        }
    }

    private void checkUser() {


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Checking User");
        dialog.show();
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final String userEnteredUsername = ent_user.getEditText().getText().toString();
        final String userEnteredPassword = ent_pass.getEditText().getText().toString();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Accounts");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ent_user.setError(null);
                    ent_user.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        ent_user.setError(null);
                        ent_user.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);

//

                        // Taking out data from database
                        Intent intent  = new Intent(LoginScreen.this, MainScreen.class);
                        intent.putExtra("user_fname", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("user_email", emailFromDB);
                        intent.putExtra("user_password", passwordFromDB);
                        add_data(nameFromDB, usernameFromDB, emailFromDB, passwordFromDB);
                        dialog.dismiss();
                        startActivity(intent);


                        finish();


                    } else {
                        dialog.dismiss();
                        ent_pass.setError("Wrong Password");
                        ent_pass.requestFocus();
                    }
                } else {
                    dialog.dismiss();
                    ent_user.setError("No such User exist");
                    ent_user.requestFocus();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            return;
            }
            })
    ;}

    public void add_data(String f_name, String username, String email, String pass){

        sp = getSharedPreferences("datafile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("Username", username);
        editor.putString("Full Name", f_name);
        editor.putString("Email", email);
        editor.putString("Password", pass);

        editor.commit();


    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finishAffinity();
    }

}
