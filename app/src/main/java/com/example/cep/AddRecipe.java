package com.example.cep;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class AddRecipe extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String user_fname, username, user_email, user_password;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button browse_pic, submit_recipe;
    Uri filepath;
    ActivityResultLauncher<String> get_img_cont;
    ImageView img;
    EditText recipe_name, ingredients, procedure;
    SharedPreferences sp;

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        /* ------------------------ getting intents ---------------------------- */

        user_fname = getIntent().getStringExtra("user_fname");
        username = getIntent().getStringExtra("username");
        user_email = getIntent().getStringExtra("user_email");
        user_password = getIntent().getStringExtra("user_password");

        /* ------------------------------- Hooks ---------------------------------------*/

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        browse_pic = findViewById(R.id.browse_but);
        submit_recipe = findViewById(R.id.submit_rec_but);
        img = findViewById(R.id.recipe_image);

        get_img_cont = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    img.setImageURI(result);
                    filepath = result;
                }
            }
        });

        /* ----------------------------- Navigation Drawer --------------------------- */

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        /* ----------------------------- pic but --------------------------------------*/

        browse_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_img_cont.launch("image/*");


            }


        });

        submit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtofirebase();
            }
        });

    }

    /* ----------------------------- back press method --------------------------------------*/

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            Intent intent1 = new Intent(AddRecipe.this, UserDashboard.class);

            intent1.putExtra("user_fname", user_fname);
            intent1.putExtra("username", username);
            intent1.putExtra("user_email", user_email);
            intent1.putExtra("user_password", user_password);

            startActivity(intent1);
            finishAffinity();
        }
    }
    /* ----------------------------- navigation selector method --------------------------------------*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.home:
                Intent intent = new Intent(AddRecipe.this, MainScreen.class);

                intent.putExtra("user_fname", user_fname);
                intent.putExtra("username", username);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);

                startActivity(intent);
                finishAffinity();
                break;

            case R.id.dashboard:
                Intent intent1 = new Intent(AddRecipe.this, UserDashboard.class);

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
                Intent intent2 = new Intent(AddRecipe.this, LoginScreen.class);
                startActivity(intent2);

                finishAffinity();
                break;
        }drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /* ----------------------------- Upload data to FireBase --------------------------------------*/

    private void uploadtofirebase() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Uploading Recipe");
        dialog.show();

        recipe_name = findViewById(R.id.recipe_name);
        ingredients = findViewById(R.id.ingrdients);
        procedure = findViewById(R.id.how_to_cook);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference uploader = storage.getReference(username + "_image_" + new Random().nextInt(999999999));
        if (filepath != null) {
            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    dialog.dismiss();
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference root = db.getReference("Chefs").child(username);

                                    AddRecipeDataHolder obj = new AddRecipeDataHolder(user_fname, recipe_name.getText().toString(), ingredients.getText().toString(), procedure.getText().toString(), uri.toString());
                                    root.child(username+"rec_serial_"+new Random().nextInt(999999999)).setValue(obj);


                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference root_rec = database.getReference("Recipe");
                                    AddRecipeDataHolder object = new AddRecipeDataHolder(user_fname, recipe_name.getText().toString(), ingredients.getText().toString(), procedure.getText().toString(), uri.toString());
                                    root_rec.child(String.valueOf(new Random().nextInt(999999999))).setValue(object);

                                    recipe_name.setText("");
                                    ingredients.setText("");
                                    procedure.setText("");
                                    img.setImageResource(R.drawable.res_pic);
                                    filepath = null;
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded :" + (int) percent + " %");
                        }
                    });

        }
        else{
            filepath = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.res_pic)
                    + '/' + getResources().getResourceTypeName(R.drawable.res_pic) + '/' + getResources().getResourceEntryName(R.drawable.res_pic) );
            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    dialog.dismiss();
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference root = db.getReference("Chefs").child(username);

                                    AddRecipeDataHolder obj = new AddRecipeDataHolder(user_fname, recipe_name.getText().toString(), ingredients.getText().toString(), procedure.getText().toString(), uri.toString());
                                    root.child(username+"rec_serial_"+new Random().nextInt(999999999)).setValue(obj);


                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference root_rec = database.getReference("Recipe");
                                    AddRecipeDataHolder object = new AddRecipeDataHolder(user_fname, recipe_name.getText().toString(), ingredients.getText().toString(), procedure.getText().toString(), uri.toString());
                                    root_rec.child(String.valueOf(new Random().nextInt(999999999))).setValue(object);

                                    recipe_name.setText("");
                                    ingredients.setText("");
                                    procedure.setText("");
                                    img.setImageResource(R.drawable.res_pic);
                                    filepath = null;
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded :" + (int) percent + " %");
                        }
                    });


        }
    }

}