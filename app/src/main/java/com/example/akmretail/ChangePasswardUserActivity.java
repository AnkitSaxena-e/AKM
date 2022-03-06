package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class ChangePasswardUserActivity extends AppCompatActivity {

    private EditText oldPassward, newPassward, confermNewPassward;
    private Button confermPasswardChange;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private String Name, type;
    ImageButton Show_Pass_O, Show_Pass_N;
    private ImageView back;
    private Dialog LoadingBar;
    int y = 0;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passward_user);
        try {
            Paper.init(ChangePasswardUserActivity.this);
            oldPassward = findViewById(R.id.Old_Passward_cp);
            newPassward = findViewById(R.id.New_Passward_cp);
            confermNewPassward = findViewById(R.id.Conferm_New_Passward_cp);
            confermPasswardChange = findViewById(R.id.Change_Button_cp);

            back = findViewById(R.id.back_sett_change_pass);

            Show_Pass_O = findViewById(R.id.Show_O_Pass);
            Show_Pass_N = findViewById(R.id.Show_N_Pass);

            Paper.book().write(Prevalant.CheckH, "A");
            type = Paper.book().read(Prevalant.CheckAdmin).toString();

            LoadingBar = new Dialog(ChangePasswardUserActivity.this);
            LoadingBar.setContentView(R.layout.loading_dialog);
            LoadingBar.setCancelable(false);

            oldPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confermNewPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());

            Show_Pass_O.setOnClickListener(v -> {
                if(i == 0){
                    oldPassward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Show_Pass_O.setBackground(ContextCompat.getDrawable(ChangePasswardUserActivity.this, R.drawable.ic_visibility));
                    i++;
                }

                else{
                    oldPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Show_Pass_O.setBackground(ContextCompat.getDrawable(ChangePasswardUserActivity.this,R.drawable.ic_visibility_off));
                    i = 0;
                }
            });


            Show_Pass_N.setOnClickListener(v -> {
                if(i == 0){
                    newPassward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confermNewPassward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Show_Pass_N.setBackground(ContextCompat.getDrawable(ChangePasswardUserActivity.this, R.drawable.ic_visibility));
                    i++;
                }

                else{
                    newPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confermNewPassward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Show_Pass_N.setBackground(ContextCompat.getDrawable(ChangePasswardUserActivity.this,R.drawable.ic_visibility_off));
                    i = 0;
                }
            });

            Window window = ChangePasswardUserActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

            confermPasswardChange.setOnClickListener(v -> InCheck());

            back.setOnClickListener(v -> {

                Intent i = new Intent(ChangePasswardUserActivity.this, HomeActivity.class);
                i.putExtra("eeee", "SA");
                startActivity(i);

            });

        }
        catch (Exception e){
            Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void InCheck() {

        try {
            String OldPass = oldPassward.getText().toString();
            String NewPass = newPassward.getText().toString();
            String ConNewPass = confermNewPassward.getText().toString();

            if (OldPass.isEmpty()) {
                oldPassward.setError("Please Enter your Old Passward");
            } else if (NewPass.isEmpty()) {
                newPassward.setError("Please Enter your New Passward");
            } else if(NewPass.length() < 8){
                newPassward.setError("Minimum 8 Characters Required");
            } else if (ConNewPass.isEmpty()) {
                confermNewPassward.setError("Please Conferm your New Password");
            } else if (NewPass.equals(ConNewPass)) {
                ChangePassward(OldPass, NewPass);
            } else {
                confermNewPassward.setError("Please Match your New Password");
            }
        }
        catch (Exception e){
            Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void ChangePassward(final String oldPass, final String newPass) {

        try {
            DatabaseReference PasswardRef = FirebaseDatabase.getInstance().getReference().child("User")
                    .child(Paper.book().read(Prevalant.UserIdA));

            PasswardRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    try {
                        if (dataSnapshot.exists()) {
                            String takePassward = dataSnapshot.child("Passward").getValue().toString();

                            if (takePassward.equals(oldPass)) {
                                ChangePass(newPass);
                            } else {
                                oldPassward.setError("Password didn't Match");
                            }
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void ChangePass(String newPass) {

        try {
            DatabaseReference PasswardRef = FirebaseDatabase.getInstance().getReference().child("User")
                    .child(Paper.book().read(Prevalant.UserIdA));

            HashMap<String, Object> PassUpdate = new HashMap<>();

            PassUpdate.put("Passward", newPass);

            PasswardRef.updateChildren(PassUpdate).addOnCompleteListener(task -> {

                try {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePasswardUserActivity.this, "Password Update Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChangePasswardUserActivity.this, "Network Error.....", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(ChangePasswardUserActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChangePasswardUserActivity.this, HomeActivity.class);
        i.putExtra("eeee", "SA");
        startActivity(i);
    }

}