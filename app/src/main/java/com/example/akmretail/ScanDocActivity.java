package com.example.akmretail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.akmretail.Prevalant.Prevalant;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.UUID;

import io.paperdb.Paper;


public class ScanDocActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1;
    private Button LG, AI;
    private ImageView SI, BB;
    private static final int CAMERA_RE = 1888;
    private Uri ImageUri;
    private Dialog LoadingBar;
    private String downloadImageUri = "A", CheckImage = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_doc);

        LG = findViewById(R.id.LO);
        SI = findViewById(R.id.IM);
        AI = findViewById(R.id.AI);
        BB = findViewById(R.id.back_sett_rev_order);

        Window window = ScanDocActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        LoadingBar = new Dialog(ScanDocActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);


        Paper.book().write(Prevalant.CheckH, "A");
        LG.setOnClickListener(v -> {

            if(!ImageUri.equals(null)){

                downloadImageUri = ImageUri.toString();

                if(downloadImageUri.equals("A")){
                    Toast.makeText(ScanDocActivity.this, "Please Add Image", Toast.LENGTH_SHORT).show();
                }else{
                    AddImage();
                }

            }

        });

        BB.setOnClickListener(v -> {

            Intent o = new Intent(ScanDocActivity.this, HomeActivity.class);
            o.putExtra("eeee", "HA");
            startActivity(o);

        });

        AI.setOnClickListener(v -> {

//            Intent CC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(CC, CAMERA_RE);

            CheckImage = "Yes";
            CropImage.activity(ImageUri)
                    .start(ScanDocActivity.this);

        });

    }

    private void AddImage() {

        try{

            LoadingBar.show();

            StorageReference ProductImageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ssme-5eb32.appspot.com/").child("Doc Image");

            final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0,4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(ImageUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {

                Uri DUri = uri;
                LoadingBar.dismiss();

                Intent o = new Intent(ScanDocActivity.this, ConfermOrderActivity.class);
                o.putExtra("PPID", "A");
                o.putExtra("TotalPrice", "A");
                o.putExtra("BuyF", "Buy");
                o.putExtra("From", "SD");
                o.putExtra("IM", DUri.toString());
                startActivity(o);

            }));



        }catch(Exception e){
            Toast.makeText(ScanDocActivity.this, "jjjj" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RE && resultCode == RESULT_OK) {

            if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                ImageUri = result.getUri();
                SI.setImageURI(ImageUri);
            }

            else{
                Toast.makeText(ScanDocActivity.this, "Error try Again....", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ScanDocActivity.this,ScanDocActivity.class));
                finish();
            }

//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            SI.setImageBitmap(photo);
//
//            ImageUri = getImageUri(ScanDocActivity.this, photo);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return  Uri.parse(path);
    }

}