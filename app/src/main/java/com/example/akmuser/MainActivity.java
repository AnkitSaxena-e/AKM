package com.example.akmuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.akmuser.Modal.Users;
import com.example.akmuser.Prevalant.Prevalant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button Join, Login;
    private String Image1 = "", Image2 = "",Image3 = "", Image4 = "", Image5 = "";
    private int i = 0;
    private int llll = 0;
    private Dialog LoadingBar;
    private String parantName = "User"; private String IIMM = "AS";
    private boolean doubleclick = false;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Paper.init(MainActivity.this);

        Join = findViewById(R.id.join);
        Login = findViewById(R.id.login);
        LoadingBar = new Dialog(MainActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        Paper.init(MainActivity.this);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
            + ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW)
            != PackageManager.PERMISSION_GRANTED){

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECEIVE_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW))
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Grant Those Permissions");
            builder.setMessage("Internet, Read and Write Storage and Send SMS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.INTERNET,
                                    Manifest.permission.ACCESS_NETWORK_STATE,
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.SYSTEM_ALERT_WINDOW
                            },ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                }
            });

            builder.setNegativeButton("Cancel", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.SEND_SMS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.SYSTEM_ALERT_WINDOW
                    },ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }else{
        Toast.makeText(getApplicationContext(), "Permission is already Granted", Toast.LENGTH_SHORT).show();
    }

        Join.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(i);

//                Intent i = new Intent(MainActivity.this, ADSeeUserActivity.class);
//                startActivity(i);
        }
    });

        Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    });

    String UserPhoneKey = Paper.book().read(Prevalant.userPhoneKey);
    String UserPassward = Paper.book().read(Prevalant.userPasswardKey);
    String UserType = Paper.book().read(Prevalant.CheckAdmin);

        if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassward)){

            LoadingBar.show();

            parantName = UserType;

            goToAdd(UserPhoneKey, UserPassward);
        }

}



    private void CheckFireBase(final String Number, final String passward, final String image1, final String image2, final String image3,
                               final String image4, final String image5){

        try{

            Paper.book().write(Prevalant.AD1, image1);
            Paper.book().write(Prevalant.AD2, image2);
            Paper.book().write(Prevalant.AD3, image3);
            Paper.book().write(Prevalant.AD4, image4);
            Paper.book().write(Prevalant.AD5, image5);

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(parantName).child(Number).exists()) {

                        Users userdata = dataSnapshot.child(parantName).child(Number).getValue(Users.class);

                        String NNAA = dataSnapshot.child(parantName).child(Number).child("Name").getValue().toString();
                        String NNUU = dataSnapshot.child(parantName).child(Number).child("Number").getValue().toString();

                        if(dataSnapshot.child(parantName).child(Number).child("Image").exists()){
                            IIMM = dataSnapshot.child(parantName).child(Number).child("Image").getValue().toString();
                        }

                        if (userdata.getNumber().equals(Number)) {

                            if (userdata.getPassward().equals(passward)) {

                                if (parantName.equals("User")) {
                                    try {

                                        if(userdata.getSuspend().equals("A")){
                                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                            LoadingBar.dismiss();
                                            Paper.book().write(Prevalant.UserNameA, NNAA);
                                            Paper.book().write(Prevalant.UserImageA, IIMM);
                                            Paper.book().write(Prevalant.UserNumberA, NNUU);
                                            Paper.book().read(Prevalant.ACAdmin, "No");
                                            Paper.book().write(Prevalant.FAD, "HomeA");

                                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                            Prevalant.currentOnlineUser = userdata;
                                            Paper.book().write(Prevalant.userName, userdata);
                                            Paper.book().write(Prevalant.FAD, "HomeA");
                                            Paper.book().write(Prevalant.CheckAdmin, "User");
                                            i.putExtra("eeee", "LA");
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(MainActivity.this, "!Your Account is Suspended..Please Contact to the Admin!", Toast.LENGTH_SHORT).show();
                                            LoadingBar.dismiss();
                                        }


                                    } catch (Exception e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                LoadingBar.dismiss();
                                Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Account with this " + Number + " id does not exsit", Toast.LENGTH_LONG).show();
                            LoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Account with this " + Number + " id does not exsit", Toast.LENGTH_LONG).show();
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Please Create a new Account", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void goToAdd(final String phoneNumber, final String passward) {

        DatabaseReference ADref = FirebaseDatabase.getInstance().getReference().child("Ads");

        ADref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Image1 = dataSnapshot.child("Image1").getValue().toString();
                    Image2 = dataSnapshot.child("Image2").getValue().toString();
                    Image3 = dataSnapshot.child("Image3").getValue().toString();
                    Image4 = dataSnapshot.child("Image4").getValue().toString();
                    Image5 = dataSnapshot.child("Image4").getValue().toString();

                    CheckFireBase(phoneNumber, passward, Image1, Image2, Image3, Image4, Image5);

                }
                else {
                    CheckFireBase(phoneNumber,passward, Image1, Image2, Image3, Image4, Image5);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

}
