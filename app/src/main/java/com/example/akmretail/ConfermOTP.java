package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class ConfermOTP extends AppCompatActivity {

    private String verificationId, Lp;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView Timer_O, Resend_O;
    private EditText editText;
    private int time = 60;
    private boolean doubleclick = false, yy = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferm_otp);

        Lp = getIntent().getStringExtra("Join");

        progressBar = findViewById(R.id.progressbar);
        editText = findViewById(R.id.editTextCode);

        SignIn = findViewById(R.id.buttonSignIn);

        Timer_O = findViewById(R.id.timer_p);
        Resend_O = findViewById(R.id.resendCode_p);

        try {
            mAuth = FirebaseAuth.getInstance();

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    Toast.makeText(ConfermOTP.this, "Code Send", Toast.LENGTH_LONG).show();
                    super.onCodeSent(s, forceResendingToken);
                    verificationId = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    final String phonenumber = getIntent().getStringExtra("Number");
                    final String name = getIntent().getStringExtra("Name");
                    final String passward = getIntent().getStringExtra("Passward");
                    if (code != null) {
                        editText.setText(code);
                        verifyCode(code, name, passward, phonenumber);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(ConfermOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            final String phonenumber = getIntent().getStringExtra("Number");
            final String name = getIntent().getStringExtra("Name");
            final String passward = getIntent().getStringExtra("Passward");

            sendVerificationCode(phonenumber, mCallBack);

            SignIn.setOnClickListener(v -> {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code, name, passward, phonenumber);
            });

        } catch (Exception e) {
            Toast.makeText(ConfermOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void sendVerificationCode(String phonenumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                ConfermOTP.this,
                mCallBack
        );
        Timer();
    }

    private void verifyCode(String code, String Name, String Passward, String PhoneNumber) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(ConfermOTP.this, "verifyCode", Toast.LENGTH_LONG).show();
        signInWithCredential(credential, Name, PhoneNumber, Passward);
    }

    private void Timer(){

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
                Timer_O.setVisibility(View.VISIBLE);
                Timer_O.setText("0:" + checkDegit(time));
                time--;

            }

            @Override
            public void onFinish() {
                time = 60;
                Timer_O.setVisibility(View.GONE);
                Resend_O.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    private String checkDegit(int timer) {

        return timer <= 9? "0" + timer : String.valueOf(timer);

    }

    private void signInWithCredential(PhoneAuthCredential credential, final String NameF, final String PhoneNumberF, final String PasswardF) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if(Lp.equals("Join")){
                            AddAccount(NameF, PhoneNumberF, PasswardF);
                        }else {
                            Login(PhoneNumberF, PasswardF, NameF);
                        }

                    } else {
                        Toast.makeText(ConfermOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void AddAccount(String nameF, final String phoneNumberF, String passwardF) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        String UIDCart = UUID.randomUUID().toString().substring(0, 18);
        String UIDBuy = UUID.randomUUID().toString().substring(0, 18);
        String Pass = UUID.randomUUID().toString().substring(0, 8);

        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("Name", nameF);
        userdata.put("Number", phoneNumberF);
        userdata.put("Shop", passwardF);
        userdata.put("Passward", Pass);
        userdata.put("Approve", "Noooooo");
        userdata.put("Suspend", "A");
        userdata.put("Token", "A");
        userdata.put("Image", "A");
        userdata.put("ImageF", "A");
        userdata.put("ImageB", "A");
        userdata.put("Cart", UIDCart);
        userdata.put("Buy", UIDBuy);

        RootRef.child("ReUser").child(phoneNumberF).updateChildren(userdata).
                addOnCompleteListener(task -> {
                    if (task.isComplete()) {

                        goToAddAA();

                        Paper.book().write(Prevalant.UserNameA, nameF);
                        Paper.book().write(Prevalant.UserImageA, "A");
                        Paper.book().write(Prevalant.UserNumberA, phoneNumberF);
                        Paper.book().write(Prevalant.UserIdA, phoneNumberF);
                        Paper.book().write(Prevalant.ACAdmin, "No");
                        Paper.book().write(Prevalant.FAD, "HomeA");

                        Paper.book().write(Prevalant.userPhoneKey, phoneNumberF);
                        Paper.book().write(Prevalant.userPasswardKey, Pass);
                        Paper.book().write(Prevalant.CheckAdmin, "User");


                        Intent i = new Intent(ConfermOTP.this, HomeActivity.class);
                        Paper.book().write(Prevalant.CheckAdmin, "ReUser");
                        i.putExtra("eeee", "LA");
                        startActivity(i);

                        Toast.makeText(ConfermOTP.this, "Congratulation, Your Account is Created Successfully", Toast.LENGTH_LONG).show();
                        Toast.makeText(ConfermOTP.this, "Please Complete Te KYC to Use Services..", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void Login(String phoneNumberF, String pass, String nam) {

        goToAddAA();

        Paper.book().write(Prevalant.UserNameA, nam);
        Paper.book().write(Prevalant.UserImageA, "A");
        Paper.book().write(Prevalant.UserNumberA, phoneNumberF);
        Paper.book().write(Prevalant.UserIdA, phoneNumberF);
        Paper.book().write(Prevalant.ACAdmin, "No");
        Paper.book().write(Prevalant.FAD, "HomeA");

        Paper.book().write(Prevalant.userPhoneKey, phoneNumberF);
        Paper.book().write(Prevalant.userPasswardKey, pass);
        Paper.book().write(Prevalant.CheckAdmin, "User");


        Intent i = new Intent(ConfermOTP.this, HomeActivity.class);
        Paper.book().write(Prevalant.CheckAdmin, "User");
        i.putExtra("eeee", "LA");
        startActivity(i);

        Toast.makeText(ConfermOTP.this, "Congratulation, Your Account is Created Successfully", Toast.LENGTH_LONG).show();
        Toast.makeText(ConfermOTP.this, "Please Complete Te KYC to Use Services..", Toast.LENGTH_LONG).show();

    }

    private void goToAddAA() {

        DatabaseReference ADref = FirebaseDatabase.getInstance().getReference().child("Ads");

        ADref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String image1 = dataSnapshot.child("Image1").getValue().toString();
                    String image2 = dataSnapshot.child("Image2").getValue().toString();
                    String image3 = dataSnapshot.child("Image3").getValue().toString();
                    String image4 = dataSnapshot.child("Image4").getValue().toString();
                    String image5 = dataSnapshot.child("Image4").getValue().toString();

                    Paper.book().write(Prevalant.AD1, image1);
                    Paper.book().write(Prevalant.AD2, image2);
                    Paper.book().write(Prevalant.AD3, image3);
                    Paper.book().write(Prevalant.AD4, image4);
                    Paper.book().write(Prevalant.AD5, image5);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        if (doubleclick) {
            Intent o = new Intent(ConfermOTP.this, JoinActivity.class);
            o.putExtra("eeee", "CaA");
            startActivity(o);
            return;
        }

        this.doubleclick = true;
        Toast.makeText(ConfermOTP.this, "Press Wait", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(() -> doubleclick = false, 2000);

    }
}

