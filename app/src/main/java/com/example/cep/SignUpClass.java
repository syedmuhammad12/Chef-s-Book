package com.example.cep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpClass extends AppCompatActivity {

    TextInputLayout reg_name, reg_username, reg_email, reg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_class);

        reg_name = findViewById(R.id.full_name);
        reg_username = findViewById(R.id.username);
        reg_email = findViewById(R.id.email);
        reg_password = findViewById(R.id.password);

        Button signin_ask_but = findViewById(R.id.exist_user_but);
        signin_ask_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpClass.this, LoginScreen.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        Button sign_up_but = findViewById(R.id.signup_but);
        sign_up_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateName() | !validateUsername() | !validateEmail() | !validatePassword()){

                    return;
                }
                final ProgressDialog dialog = new ProgressDialog(SignUpClass.this);
                dialog.setTitle("Checking User");
                dialog.show();
                dialog.setContentView(R.layout.progress_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                String name = reg_name.getEditText().getText().toString();
                String username = reg_username.getEditText().getText().toString();
                String email = reg_email.getEditText().getText().toString();
                String password = reg_password.getEditText().getText().toString();

                AccountCreateClass create_account = new AccountCreateClass(name, username, email, password);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Accounts");
                myRef.child(username).setValue(create_account);

                reg_name.getEditText().setText("");
                reg_username.getEditText().setText("");
                reg_password.getEditText().setText("");
                reg_email.getEditText().setText("");
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "User Saved", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(SignUpClass.this, LoginScreen.class);
                startActivity(intent1);
                finishAffinity();


            }
        });
    }
    private Boolean validateName() {
        String val = reg_name.getEditText().getText().toString();

        if (val.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        }
        else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = reg_username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            reg_username.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            reg_username.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            reg_username.setError("White Spaces are not allowed");
            return false;
        } else {
            reg_username.setError(null);
            reg_username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            reg_email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            reg_email.setError("Invalid email address");
            return false;
        } else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = reg_password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            reg_password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            reg_password.setError("Password is too weak");
            return false;
        } else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpClass.this, LoginScreen.class);
        startActivity(intent);
        finish();
//        super.onBackPressed();
    }
}