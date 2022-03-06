package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AboutActivity extends AppCompatActivity {

     private TextView About, AnkitInsta, AnandInsta, TAC;
     private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        About = findViewById(R.id.AboutA);
        AnkitInsta = findViewById(R.id.instaMe);
        AnandInsta = findViewById(R.id.instaAnand);
        backB = findViewById(R.id.back_sett_about);
        TAC = findViewById(R.id.abdf);
        Paper.book().write(Prevalant.CheckH, "A");

        Window window = AboutActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        DatabaseReference UserRefA = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        AnkitInsta.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/ankits1432/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/ankits1432/")));
            }
        });

        AnandInsta.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.instagram.com/ankits1432/");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/ankits1432/")));
            }
        });

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AboutActivity.this, HomeActivity.class);
                i.putExtra("eeee", "SA");
                startActivity(i);
            }
        });

        UserRefA.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("Name").exists()) {
                        String name = dataSnapshot.child("Name").getValue().toString();

                        About.setText("Hello " + name + "! as you know this is an e-commerce app where you can buy Surgical items at your door " +
                                "Steps as soon as possible from our side at lowest Price possible with only Rs.15 delivery charge." +
                                "We have all the top company surgical items so don't worry about the quality. " +
                                "We Only Need Your Support so that we Can Add More things at your Service.");

                        TAC.setText("1. Please do not use OTP Services(Like Join & Forgot Password) more than 10 times in a day otherwise your OTP Services" +
                                "will be stopped Temporarily." + "\n" +
                                "2. If You Cancel more then 8 Orders Constantly So Your Account Will be Suspended." + "\n" +
                                "3. If the Packaging is broken or if the product is broken so We will not return the Product." + "\n" +
                                "4. If the Product will broke after the Delivery, So we are not Responsible for that.");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AboutActivity.this, HomeActivity.class);
        i.putExtra("eeee", "SA");
        startActivity(i);
    }

}