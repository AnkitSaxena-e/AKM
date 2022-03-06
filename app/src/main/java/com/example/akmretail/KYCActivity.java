package com.example.akmretail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;
import java.util.UUID;

import io.paperdb.Paper;

public class KYCActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private RelativeLayout Re_K_A, Re_K_B;
    private ImageView Im_K_A, Im_K_B;
    private Button SendK;
    private TextView noteK;
    private StorageTask uploadTask;
    private String Know = "A";
    private Uri ImageUriA, ImageUriB;

    private StorageReference profilePictureRef;
    private Dialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c);

        Re_K_A = findViewById(R.id.Re_a_kyc);
        Re_K_B = findViewById(R.id.Re_b_kyc);

        Im_K_A = findViewById(R.id.img_a_kyc);
        Im_K_B = findViewById(R.id.img_b_kyc);

        SendK = findViewById(R.id.AI_KYC);
        noteK = findViewById(R.id.text_Kyc);

        profilePictureRef = FirebaseStorage.getInstance().getReference().child("KYC Picture");

        LoadingBar = new Dialog(KYCActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Window window = KYCActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#22BFC1"));

        Re_K_A.setOnClickListener(v -> {

            Know = "A";

            if (ActivityCompat.checkSelfPermission(KYCActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(KYCActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }

            try {

                Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(k, RESULT_LOAD_IMAGE);

            } catch (Exception e) {
                Toast.makeText(KYCActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Re_K_B.setOnClickListener(v -> {

            Know = "B";

            if (ActivityCompat.checkSelfPermission(KYCActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(KYCActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }

            try {

                Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(k, RESULT_LOAD_IMAGE);

            } catch (Exception e) {
                Toast.makeText(KYCActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        SendK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddImage();

            }
        });

    }

    private void AddImage() {

        try {

            LoadingBar.show();

            if (ImageUriA != null) {
                final StorageReference fileRef = profilePictureRef.child(Paper.book().read(Prevalant.UserIdA) + UUID.randomUUID().toString().substring(0, 8) + ".jpg");
                uploadTask = fileRef.putFile(ImageUriA);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                    if (task.isSuccessful()) {

                        try {
                            Uri doewnloadUri = task.getResult();
                            String myUri = doewnloadUri.toString();

                            AddImageB(myUri);

                        } catch (Exception b) {
                            Toast.makeText(KYCActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LoadingBar.dismiss();
                        Toast.makeText(KYCActivity.this, "Error...Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(KYCActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void AddImageB(String myUri) {

        try {

            LoadingBar.show();

            if (ImageUriB != null) {
                final StorageReference fileRef = profilePictureRef.child(Paper.book().read(Prevalant.UserIdA) + UUID.randomUUID().toString().substring(0, 8) + ".jpg");
                uploadTask = fileRef.putFile(ImageUriB);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                    if (task.isSuccessful()) {

                        try {
                            Uri doewnloadUri = task.getResult();
                            String myUriB = doewnloadUri.toString();

                            Uploadto(myUri, myUriB);

                        } catch (Exception b) {
                            Toast.makeText(KYCActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LoadingBar.dismiss();
                        Toast.makeText(KYCActivity.this, "Error...Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(KYCActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void Uploadto(String myUri, String myUriB) {

        DatabaseReference rreeff = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> userdata = new HashMap<>();
        userdata.put("ImageF", myUri);
        userdata.put("ImageB", myUriB);

        rreeff.updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                LoadingBar.dismiss();
                Toast.makeText(KYCActivity.this, "Application Send Successfully.. Please Wait for Our Response..", Toast.LENGTH_SHORT).show();
                Intent p = new Intent(KYCActivity.this, HomeActivity.class);
                p.putExtra("eeee", "LA");
                startActivity(p);

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if(Know.equals("A")){
                ImageUriA = data.getData();
                Im_K_A.setImageURI(ImageUriA);
            }

            if(Know.equals("B")){
                ImageUriB = data.getData();
                Im_K_B.setImageURI(ImageUriB);
            }

        } else {
            Toast.makeText(KYCActivity.this, "Error try Again....", Toast.LENGTH_SHORT).show();
        }

    }

}