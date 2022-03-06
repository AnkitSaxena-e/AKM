package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Adapter.SliderLayoutAdapter;
import com.example.akm_admin.Adapter.UploadListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ADActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri ImageUri;
    private String Image1, Image2, Image3, Image4, Image5;
    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private ArrayList<String> ImageList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;
    private StorageTask uploadTask;
    private StorageReference profilePictureRef;
    private String myUri = "";
    private RecyclerView recyclerView8;
    private ImageView imageView;
    private Dialog LoadingBar;
    private TextView AddImage, UploadAd;
    private Uri ImageUri1, ImageUri2, ImageUri3, ImageUri4, ImageUri5;
    private Bitmap BImageUri1, BImageUri2, BImageUri3, BImageUri4, BImageUri5;
    private ImageView picPhoto18, picPhoto28, picPhoto38 ,picPhoto48, picPhoto58;
    private EditText categoryName;
    private Button EditAdd;
    private String Add_Link_Text;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private String[] urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        try {

            AddImage = findViewById(R.id.addtheAdd);
            UploadAd = findViewById(R.id.uploadAdd);

            EditAdd = findViewById(R.id.editAd);

            picPhoto18 = findViewById(R.id.PicPhoto18);
            picPhoto28 = findViewById(R.id.PicPhoto28);
            picPhoto38 = findViewById(R.id.PicPhoto38);
            picPhoto48 = findViewById(R.id.PicPhoto48);
            picPhoto58 = findViewById(R.id.PicPhoto58);

            recyclerView8 = findViewById(R.id.upload_listll);

            fileNameList = new ArrayList<>();
            fileDoneList = new ArrayList<>();

            ImageList = new ArrayList<String>();

            uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);
            recyclerView8.setLayoutManager(new LinearLayoutManager(this));
            recyclerView8.setHasFixedSize(true);
            recyclerView8.setAdapter(uploadListAdapter);

            LoadingBar = new Dialog(ADActivity.this);
            LoadingBar.setContentView(R.layout.loading_dialog);
            LoadingBar.setCancelable(false);

            mStorage = FirebaseStorage.getInstance().getReference();

            profilePictureRef = FirebaseStorage.getInstance().getReference().child("ADs");

            AddBanner();

            AddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ActivityCompat.checkSelfPermission(ADActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(ADActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                        return;
                    }

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

                }
            });

            UploadAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadImage();
                }
            });

            EditAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent o = new Intent(ADActivity.this, EditBannerActivity.class);
                    o.putExtra("Ad", "Ads");
                    startActivity(o);

                }
            });
        }
        catch (Exception e){
            Toast.makeText(ADActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void AddBanner() {

        DatabaseReference Arefkk = FirebaseDatabase.getInstance().getReference().child("Ads");

        Arefkk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Link1 = snapshot.child("Image1").getValue().toString();
                    String Link2 = snapshot.child("Image2").getValue().toString();
                    String Link3 = snapshot.child("Image3").getValue().toString();
                    String Link4 = snapshot.child("Image4").getValue().toString();
                    String Link5 = snapshot.child("Image5").getValue().toString();

                    urls = new String[] {
                            Link1,
                            Link2,
                            Link3,
                            Link4,
                            Link5
                    };

                    Flip();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Flip() {

        mPager = (ViewPager) findViewById(R.id.viewPagerShow);
        mPager.setAdapter(new SliderLayoutAdapter(ADActivity.this,urls));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    private void uploadImage() {

        try {

            LoadingBar.show();

            DatabaseReference refkk = FirebaseDatabase.getInstance().getReference().child("Ads");

            HashMap<String, Object> map = new HashMap<>();

            map.put("Image1", Image1);
            map.put("Image2", Image2);
            map.put("Image3", Image3);
            map.put("Image4", Image4);
            map.put("Image5", Image5);

            refkk.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ADActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                });
            LoadingBar.dismiss();
            Toast.makeText(ADActivity.this, "Image uploaded Successfully", Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            Toast.makeText(ADActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            if(data.getClipData() != null){

                int totalItemsSelected = data.getClipData().getItemCount();

                List<Bitmap> bitmaps = new ArrayList<>();
                ClipData clipData = data.getClipData();

                if(clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();

                        try {
                            InputStream is = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            bitmaps.add(bitmap);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(ADActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{

                    Uri imageUri = data.getData();

                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(ADActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


                int o = 0;

                for (Bitmap bm : bitmaps) {
                    Toast.makeText(ADActivity.this, Integer.toString(o), Toast.LENGTH_SHORT).show();
                    o++;

                    switch(o){
                        case 1:
                            picPhoto18.setImageBitmap(bm);
                            Image1 = getImageString(bm);
                            break;

                        case 2:
                            picPhoto28.setImageBitmap(bm);
                            Image2 = getImageString(bm);
                            break;

                        case 3:
                            picPhoto38.setImageBitmap(bm);
                            Image3 = getImageString(bm);
                            break;

                        case 4:
                            picPhoto48.setImageBitmap(bm);
                            Image4 = getImageString(bm);
                            break;

                        case 5:
                            picPhoto58.setImageBitmap(bm);
                            Image5 = getImageString(bm);
                            break;
                    }
                }


                for (int i = 0; i < totalItemsSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();

                    String fileName = getFileName(fileUri).substring(0, 3) + UUID.randomUUID().toString().substring(0, 8);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    StorageReference fileToUpload = mStorage.child("Images").child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");

                            uploadListAdapter.notifyDataSetChanged();
                            fileToUpload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    try{

                                        String DownloadUriString = uri.toString();

                                        ImageList.add(DownloadUriString);
                                        Toast.makeText(ADActivity.this, "for" + finalI, Toast.LENGTH_SHORT).show();

                                        switch(finalI){

                                            case 0:
                                                Image1 = ImageList.get(0);
                                                break;

                                            case 1:
                                                Image2 = ImageList.get(1);
                                                break;

                                            case 2:
                                                Image3 = ImageList.get(2);
                                                break;

                                            case 3:
                                                Image4 = ImageList.get(3);
                                                break;

                                            case 4:
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                UploadAd.setVisibility(View.VISIBLE);
                                                Image5 = ImageList.get(4);
                                                break;

                                        }

                                    }
                                    catch (Exception e){
                                        Toast.makeText(ADActivity.this, "if" +  e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    });
                }
            } else if (data.getData() != null){
                Toast.makeText(ADActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally{
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return  Uri.parse(path);
    }

    public String getImageString(Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String path = Base64.encodeToString(b, Base64.DEFAULT);
        return path;
    }

}
