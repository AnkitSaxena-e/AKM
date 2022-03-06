package com.example.akmuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.akmuser.Prevalant.Prevalant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.Predicate;


public class ScanDocActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1;
    private Button LG, AI;
    private ImageView SI;
    private static final int CAMERA_RE = 1888;
    private Uri ImageUri;
    private Dialog LoadingBar;
    private String downloadImageUri = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_doc);

        LG = findViewById(R.id.LO);
        SI = findViewById(R.id.IM);
        AI = findViewById(R.id.AI);

        LoadingBar = new Dialog(ScanDocActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);


        LG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ImageUri.equals(null)){

                    downloadImageUri = ImageUri.toString();

                    if(downloadImageUri.equals("A")){
                        Toast.makeText(ScanDocActivity.this, "Please Add Image", Toast.LENGTH_SHORT).show();
                    }else{
                        AddImage();
                    }

                }

            }
        });

        AI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent CC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(CC, CAMERA_RE);

            }
        });

    }

    private void AddImage() {

        try{

            LoadingBar.show();

            StorageReference ProductImageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ssme-5eb32.appspot.com/").child("Doc Image");

            final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0,4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(ImageUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Uri DUri = uri;
                            LoadingBar.dismiss();

                            Intent o = new Intent(ScanDocActivity.this, ConfermOrderActivity.class);
                            o.putExtra("PPID", "A");
                            o.putExtra("TotalPrice", "A");
                            o.putExtra("BuyF", "Buy");
                            o.putExtra("From", "SD");
                            o.putExtra("IM", DUri.toString());
                            startActivity(o);

                        }
                    });

                }
            });



        }catch(Exception e){
            Toast.makeText(ScanDocActivity.this, "jjjj" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RE && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            SI.setImageBitmap(photo);

            ImageUri = getImageUri(ScanDocActivity.this, photo);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return  Uri.parse(path);
    }

}