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
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class OTPJoinActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private TextView Timer_O, Resend_O;
    private ProgressBar progressBar;
    private int timer = 60;
    private EditText editText;
    private boolean doubleclick = false, yy = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_join);

        progressBar = findViewById(R.id.progressbar_j);
        editText = findViewById(R.id.editTextCode_j);
        Timer_O = findViewById(R.id.timer);
        Resend_O = findViewById(R.id.resendCode);

        SignIn = findViewById(R.id.buttonSignIn_j);

        try {
            mAuth = FirebaseAuth.getInstance();

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    Toast.makeText(OTPJoinActivity.this, "Code Send", Toast.LENGTH_LONG).show();
                    super.onCodeSent(s, forceResendingToken);
                    verificationId = s;
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    final String phonenumber = getIntent().getStringExtra("NNOO");
                    final String pass = getIntent().getStringExtra("Pass");
                    final String Nam = getIntent().getStringExtra("Name");
                    if (code != null) {
                        editText.setText(code);
                        verifyCode(code, phonenumber, pass, Nam);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(OTPJoinActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            final String phonenumber = getIntent().getStringExtra("NNOO");
            final String pass = getIntent().getStringExtra("Pass");
            final String Nam = getIntent().getStringExtra("Name");

            sendVerificationCode(phonenumber, mCallBack);

            Resend_O.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendVerificationCode(phonenumber, mCallBack);
                }
            });

            SignIn.setOnClickListener(v -> {

                String code = editText.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code, phonenumber, pass, Nam);
            });
        } catch (Exception e) {
            Toast.makeText(OTPJoinActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void Timer(){

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
                Timer_O.setVisibility(View.VISIBLE);
                Timer_O.setText("0:" + checkDegit(timer));
                timer--;

            }

            @Override
            public void onFinish() {
                timer = 60;
                Timer_O.setVisibility(View.GONE);
                Resend_O.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    private String checkDegit(int timer) {

        return timer <= 9? "0" + timer : String.valueOf(timer);

    }

    private void sendVerificationCode(String phonenumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                OTPJoinActivity.this,
                mCallBack
        );

        Timer();

    }

    private void verifyCode(String code, String PhoneNumber, String pass, String nam) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(OTPJoinActivity.this, "verifyCode", Toast.LENGTH_LONG).show();
        signInWithCredential(credential, PhoneNumber, pass, nam);
    }

    private void signInWithCredential(PhoneAuthCredential credential, final String PhoneNumberF, String pass, String nam) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Login(PhoneNumberF, pass, nam);
                    } else {
                        Toast.makeText(OTPJoinActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Login(String phoneNumberF, String pass, String nam) {

        goToAdd();

        Paper.book().write(Prevalant.UserNameA, nam);
        Paper.book().write(Prevalant.UserImageA, "A");
        Paper.book().write(Prevalant.UserNumberA, phoneNumberF);
        Paper.book().write(Prevalant.UserIdA, phoneNumberF);
        Paper.book().write(Prevalant.ACAdmin, "No");
        Paper.book().write(Prevalant.FAD, "HomeA");

        Paper.book().write(Prevalant.userPhoneKey, phoneNumberF);
        Paper.book().write(Prevalant.userPasswardKey, pass);
        Paper.book().write(Prevalant.CheckAdmin, "User");


        Intent i = new Intent(OTPJoinActivity.this, HomeActivity.class);
        Paper.book().write(Prevalant.CheckAdmin, "User");
        i.putExtra("eeee", "LA");
        startActivity(i);

        Toast.makeText(OTPJoinActivity.this, "Congratulation, Your Account is Created Successfully", Toast.LENGTH_LONG).show();
        Toast.makeText(OTPJoinActivity.this, "Please Complete Te KYC to Use Services..", Toast.LENGTH_LONG).show();

    }

    private void goToAdd() {

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
            Intent o = new Intent(OTPJoinActivity.this, JoinActivity.class);
            o.putExtra("eeee", "CaA");
            startActivity(o);
            return;
        }

        this.doubleclick = true;
        Toast.makeText(OTPJoinActivity.this, "Press Wait", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(() -> doubleclick = false, 2000);

    }

}