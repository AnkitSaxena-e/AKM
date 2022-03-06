package com.example.akm_admin;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.akm_admin.Prevalant.Prevalant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

import io.paperdb.Paper;

public class EditBannerActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView P1, P2, P3, P4, P5;
    private String Child, CImage;
    private Dialog ImageSeeDialog;
    private ImageView Im_Con;
    Uri ImageUri;
    private String myUri = "";
    StorageTask uploadTask;
    StorageReference profilePictureRef;
    private String checker = "";
    private Dialog LoadingBar;
    private Button Yes_Con, No_Con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_banner);

        Child = getIntent().getStringExtra("Ad");

        P1 = findViewById(R.id.PicPhoto18_P);
        P2 = findViewById(R.id.PicPhoto28_P);
        P3 = findViewById(R.id.PicPhoto38_P);
        P4 = findViewById(R.id.PicPhoto48_P);
        P5 = findViewById(R.id.PicPhoto58_P);

        LoadingBar = new Dialog(EditBannerActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        ImageSeeDialog = new Dialog(EditBannerActivity.this);
        ImageSeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageSeeDialog.setCancelable(false);
        ImageSeeDialog.setContentView(R.layout.image_conferm_dialog);

        Yes_Con = ImageSeeDialog.findViewById(R.id.con_Yes_POPO);
        No_Con = ImageSeeDialog.findViewById(R.id.con_No_POPO);
        Im_Con = ImageSeeDialog.findViewById(R.id.con_Image_POPO);

        AddPic(Child);

    }

    private void AddPic(String child) {

        DatabaseReference Arefkk = FirebaseDatabase.getInstance().getReference().child(child);

        Arefkk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Link1 = snapshot.child("Image1").getValue().toString();
                    String Link2 = snapshot.child("Image2").getValue().toString();
                    String Link3 = snapshot.child("Image3").getValue().toString();
                    String Link4 = snapshot.child("Image4").getValue().toString();
                    String Link5 = snapshot.child("Image5").getValue().toString();

                    Picasso.get().load(Link1).into(P1);
                    Picasso.get().load(Link2).into(P2);
                    Picasso.get().load(Link3).into(P3);
                    Picasso.get().load(Link4).into(P4);
                    Picasso.get().load(Link5).into(P5);

                    P1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddP("Image1");

                        }
                    });

                    P2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddP("Image2");

                        }
                    });

                    P3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddP("Image3");

                        }
                    });

                    P4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddP("Image4");

                        }
                    });

                    P5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddP("Image5");

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AddP(String image) {

        CImage = image;

        if (ActivityCompat.checkSelfPermission(EditBannerActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(EditBannerActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }

        try {

            Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(k, RESULT_LOAD_IMAGE);

        } catch (Exception e) {
            Toast.makeText(EditBannerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            ImageUri = data.getData();

            ImageSeeDialog.show();
            Im_Con.setImageURI(ImageUri);

            Yes_Con.setOnClickListener(v -> {

                LoadingBar.show();
                ImageSeeDialog.dismiss();

                uploadImage(ImageUri);

            });

            No_Con.setOnClickListener(v -> ImageSeeDialog.dismiss());

        } else {
            Toast.makeText(EditBannerActivity.this, "Error try Again....", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadImage(Uri imageUri) {

        try {

            LoadingBar.show();

            if (imageUri != null) {
                final StorageReference fileRef = profilePictureRef.child(UUID.randomUUID().toString().substring(0, 10) + ".jpg");
                uploadTask = fileRef.putFile(imageUri);

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
                            myUri = doewnloadUri.toString();

                            DatabaseReference Arfkk = FirebaseDatabase.getInstance().getReference().child(Child);

                            HashMap<String, Object> map = new HashMap<>();

                            map.put(CImage, myUri);
                            Arfkk.child(Paper.book().read(Prevalant.UserIdA)).updateChildren(map);

                            LoadingBar.dismiss();

                        } catch (Exception b) {
                            Toast.makeText(EditBannerActivity.this, b.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LoadingBar.dismiss();
                        Toast.makeText(EditBannerActivity.this, "Error...Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(EditBannerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}