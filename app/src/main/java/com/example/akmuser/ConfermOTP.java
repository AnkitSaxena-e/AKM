package com.example.akmuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.akmuser.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConfermOTP extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    Button SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferm_otp);

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

            progressBar = findViewById(R.id.progressbar);
            editText = findViewById(R.id.editTextCode);

            SignIn = findViewById(R.id.buttonSignIn);

            sendVerificationCode(phonenumber, mCallBack);

            SignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code = editText.getText().toString().trim();

                    if (code.isEmpty() || code.length() < 6) {

                        editText.setError("Enter code...");
                        editText.requestFocus();
                        return;
                    }
                    verifyCode(code, name, passward, phonenumber);
                }
            });
        } catch (Exception e) {
            Toast.makeText(ConfermOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void sendVerificationCode(String phonenumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack) {
        Toast.makeText(ConfermOTP.this, phonenumber, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,
                60,
                TimeUnit.SECONDS,
                ConfermOTP.this,
                mCallBack
        );
        //TaskExecutors.MAIN_THREAD
        Toast.makeText(ConfermOTP.this, "Send Verification Code", Toast.LENGTH_LONG).show();
    }

    private void verifyCode(String code, String Name, String Passward, String PhoneNumber) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(ConfermOTP.this, "verifyCode", Toast.LENGTH_LONG).show();
        signInWithCredential(credential, Name, PhoneNumber, Passward);
    }

    private void signInWithCredential(PhoneAuthCredential credential, final String NameF, final String PhoneNumberF, final String PasswardF) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AddAccount(NameF, PhoneNumberF, PasswardF);
                        } else {
                            Toast.makeText(ConfermOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void AddAccount(String nameF, final String phoneNumberF, String passwardF) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        String UIDCart = UUID.randomUUID().toString().substring(0, 18);
        String UIDBuy = UUID.randomUUID().toString().substring(0, 18);

        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("Name", nameF);
        userdata.put("Number", phoneNumberF);
        userdata.put("Passward", passwardF);
        userdata.put("Suspend", "A");
        userdata.put("Token", "A");
        userdata.put("Image", "A");
        userdata.put("Cart", UIDCart);
        userdata.put("Buy", UIDBuy);

        RootRef.child("User").child(phoneNumberF).updateChildren(userdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {

                            Toast.makeText(ConfermOTP.this, "Congratulation, Your Account is Created Successfully", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ConfermOTP.this, LoginActivity.class);
                            startActivity(i);
                            Toast.makeText(ConfermOTP.this, "Please Login", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

}

