package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Adapter.UploadListAdapter;
import com.example.akm_admin.Modal.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.paperdb.Paper;

public class RetailAddPhoneActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private ArrayList<String> ImageList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;

    private String Image1 = "A", Image2 = "A";
    private Uri UImage1, UImage2;

    String CategoryName, ProductName, Description, Company, Price, Quantity, ProductKeyward, ProductMDate, ProductEDate, storeCurruntDate, storeCurruntTime;
    String ColorT1 = "A", ColorT2 = "A", ColorT3 = "A", ColorT4 = "A", ColorT5 = "A", ColorT6 = "A", ColorT7 = "A", ColorT8 = "A", ColorT9 = "A", ColorT10 = "A";
    String ColorT1Pri = "A", ColorT2Pri = "A", ColorT3Pri = "A", ColorT4Pri = "A", ColorT5Pri = "A", ColorT6Pri = "A", ColorT7Pri = "A",
            ColorT8Pri = "A", ColorT9Pri = "A", ColorT10Pri = "A";
    String Table_Name, Table_Detail, Table_Brand;
    EditText productName, productDescription, productKeyeard, productPrice, productCatagory, productQuantity, productCompany,
            productMDate, productEDate;
    EditText Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9, Color10;
    EditText Color1Pri, Color2Pri, Color3Pri, Color4Pri, Color5Pri, Color6Pri, Color7Pri, Color8Pri, Color9Pri, Color10Pri;
    TextView Adult, Child;
    EditText AdultPri, ChildPri;
    EditText Table_N, Table_D, Table_B;
    ImageView picPhoto1, picPhoto2;
    Button addProduct, AddPic;
    DatabaseReference ProductRef;
    String productRandomKey, downloadImageUri1, downloadImageUri2;
    StorageReference ProductImageRef;
    int dd = 0, ii = 0;
    private Dialog LoadingBar;
    private String Chh, AdultT = "A", ChildT = "A", AdultTPri = "A", ChildTPri = "A", CatChh;
    private int A = 0, C = 0;
    private static final int GallaryPic = 1;

    private RecyclerView ReRetailPro;
    private RecyclerView.LayoutManager ReRetailLayout;

    private DatabaseReference SearchProRe;
    private ArrayList<Product> PList, DList;

    private SearchView SearchRetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_add_phone);

        mStorage = FirebaseStorage.getInstance().getReference();

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        ImageList = new ArrayList<String>();

        Paper.init(RetailAddPhoneActivity.this);

        productName = findViewById(R.id.ProductName_retail);
        productDescription = findViewById(R.id.ProductDescription_retail);
        productKeyeard = findViewById(R.id.ProductKeyward_retail);
        productCatagory = findViewById(R.id.ProductCategory_retail);
        productCompany = findViewById(R.id.ProductCompany_retail);
        productQuantity = findViewById(R.id.ProductQuantity_retail);
        productEDate = findViewById(R.id.ProductEDate_retail);
        productMDate = findViewById(R.id.ProductMDate_retail);

        productPrice = findViewById(R.id.ProductPrice_retail);

        AddPic = findViewById(R.id.ButtonIm_retail);

        picPhoto1 = findViewById(R.id.PicPhoto1_retail);
        picPhoto2 = findViewById(R.id.PicPhoto2_retail);

        Table_N = findViewById(R.id.name_T_A_retail);
        Table_D = findViewById(R.id.Detail_T_A_retail);
        Table_B = findViewById(R.id.brand_T_A_retail);

        Color1 = findViewById(R.id.ProductColor1_retail);
        Color2 = findViewById(R.id.ProductColor2_retail);
        Color3 = findViewById(R.id.ProductColor3_retail);
        Color4 = findViewById(R.id.ProductColor4_retail);
        Color5 = findViewById(R.id.ProductColor5_retail);
        Color6 = findViewById(R.id.ProductColor6_retail);
        Color7 = findViewById(R.id.ProductColor7_retail);
        Color8 = findViewById(R.id.ProductColor8_retail);
        Color9 = findViewById(R.id.ProductColor9_retail);
        Color10 = findViewById(R.id.ProductColor10_retail);

        Color1Pri = findViewById(R.id.ProductColor1Pri_retail);
        Color2Pri = findViewById(R.id.ProductColor2Pri_retail);
        Color3Pri = findViewById(R.id.ProductColor3Pri_retail);
        Color4Pri = findViewById(R.id.ProductColor4Pri_retail);
        Color5Pri = findViewById(R.id.ProductColor5Pri_retail);
        Color6Pri = findViewById(R.id.ProductColor6Pri_retail);
        Color7Pri = findViewById(R.id.ProductColor7Pri_retail);
        Color8Pri = findViewById(R.id.ProductColor8Pri_retail);
        Color9Pri = findViewById(R.id.ProductColor9Pri_retail);
        Color10Pri = findViewById(R.id.ProductColor10Pri_retail);

        Adult = findViewById(R.id.ProductSR1_retail);
        Child = findViewById(R.id.ProductSR2_retail);

        AdultPri = findViewById(R.id.AdultPri_retail);
        ChildPri = findViewById(R.id.ChildPri_retail);

        addProduct = findViewById(R.id.AddProduct_retail);

        SearchRetail = findViewById(R.id.search_view_search_retail);
        ReRetailPro = findViewById(R.id.Search_Search_retail);
        ReRetailPro.setHasFixedSize(true);
        ReRetailPro.setItemViewCacheSize(20);
        ReRetailPro.setDrawingCacheEnabled(true);
        ReRetailPro.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        ReRetailLayout = new LinearLayoutManager(RetailAddPhoneActivity.this, LinearLayoutManager.HORIZONTAL, false);
        ReRetailPro.setLayoutManager(ReRetailLayout);

        SearchProRe = FirebaseDatabase.getInstance().getReference().child("Products");

        Adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AdultT.equals("Yes")) {
                    Adult.setBackgroundResource(R.drawable.loginbutton);
                    AdultT = "No";
                } else {
                    Adult.setBackgroundResource(R.drawable.joinbutton);
                    AdultT = "Yes";
                }
            }
        });

        Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ChildT.equals("Yes")) {
                    Child.setBackgroundResource(R.drawable.loginbutton);
                    ChildT = "No";
                } else {
                    Child.setBackgroundResource(R.drawable.joinbutton);
                    ChildT = "Yes";
                }

            }
        });

        LoadingBar = new Dialog(RetailAddPhoneActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        CategoryName = getIntent().getStringExtra("category");
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Re Product Image");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("ReProduct");

        AddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(RetailAddPhoneActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(RetailAddPhoneActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show();

        SearchProRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    PList = new ArrayList<>();

                    for (DataSnapshot h : snapshot.getChildren()) {

                        PList.add(h.getValue(Product.class));

                    }

                    Toast.makeText(RetailAddPhoneActivity.this, PList.get(1).getProductName().toString(), Toast.LENGTH_SHORT).show();

                    RetailProAdapter adapter = new RetailProAdapter(PList);
                    ReRetailPro.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (!SearchRetail.equals(null)) {

            SearchRetail.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    SearchItNow(newText);
                    return false;
                }
            });

        }

    }

    private void SearchItNow(String newText) {

        DList = new ArrayList<>();

        for (Product p : PList) {
            if (p.getKeyward().toLowerCase().contains(newText.toLowerCase())) {
                DList.add(p);
            }
        }

        RetailProAdapter adapter = new RetailProAdapter(DList);
        ReRetailPro.setAdapter(adapter);

    }

    private void ValidateProduct() {

        try {

            ProductName = productName.getText().toString();
            Description = productDescription.getText().toString();
            ProductKeyward = productKeyeard.getText().toString();
            Quantity = productQuantity.getText().toString();
            Table_Name = Table_N.getText().toString();
            Table_Detail = Table_D.getText().toString();
            Table_Brand = Table_B.getText().toString();
            CatChh = productCatagory.getText().toString();
            Company = productCompany.getText().toString();
            ProductEDate = productEDate.getText().toString();
            ProductMDate = productMDate.getText().toString();

            ColorT1 = Color1.getText().toString();
            ColorT2 = Color2.getText().toString();
            ColorT3 = Color3.getText().toString();
            ColorT4 = Color4.getText().toString();
            ColorT5 = Color5.getText().toString();
            ColorT6 = Color6.getText().toString();
            ColorT7 = Color7.getText().toString();
            ColorT8 = Color8.getText().toString();
            ColorT9 = Color9.getText().toString();
            ColorT10 = Color10.getText().toString();

            ColorT1Pri = Color1Pri.getText().toString();
            ColorT2Pri = Color2Pri.getText().toString();
            ColorT3Pri = Color3Pri.getText().toString();
            ColorT4Pri = Color4Pri.getText().toString();
            ColorT5Pri = Color5Pri.getText().toString();
            ColorT6Pri = Color6Pri.getText().toString();
            ColorT7Pri = Color7Pri.getText().toString();
            ColorT8Pri = Color8Pri.getText().toString();
            ColorT9Pri = Color9Pri.getText().toString();
            ColorT10Pri = Color10Pri.getText().toString();

            AdultTPri = AdultPri.getText().toString();
            ChildTPri = ChildPri.getText().toString();

            Price = productPrice.getText().toString();

            if (CatChh.isEmpty()) {
                productCatagory.setError("Please Enter");
            } else {
                Chh = CatChh;
            }

            if (TextUtils.isEmpty(ProductName)) {
                productName.setError("Please Enter");
            } else if (TextUtils.isEmpty(Description)) {
                productDescription.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductKeyward)) {
                productKeyeard.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Name)) {
                Table_N.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Brand)) {
                Table_B.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Detail)) {
                Table_D.setError("Please Enter");
            } else if (TextUtils.isEmpty(Company)) {
                productCompany.setError("Please Enter");
            } else if (TextUtils.isEmpty(Quantity)) {
                productQuantity.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductEDate)) {
                productEDate.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductMDate)) {
                productMDate.setError("Please Enter");
            } else {

                if (Price.isEmpty()) {
                    Price = "A";
                }

                if (AdultT.equals("No")) {
                    AdultT = "A";
                    AdultTPri = "A";
                }
                if (ChildT.equals("No")) {
                    ChildT = "A";
                    ChildTPri = "A";
                }

                if (AdultT.equals("Yes") && TextUtils.isEmpty(AdultTPri)) {
                    AdultPri.setError("Please Enter");
                }
                if (ChildT.equals("Yes") && TextUtils.isEmpty(ColorT1Pri)) {
                    ChildPri.setError("Please Enter");
                }

                if (ColorT1.isEmpty()) {
                    ColorT1 = "A";
                    ColorT1Pri = "A";
                }
                if (ColorT2.isEmpty()) {
                    ColorT2 = "A";
                    ColorT2Pri = "A";
                }
                if (ColorT3.isEmpty()) {
                    ColorT3 = "A";
                    ColorT3Pri = "A";

                }
                if (ColorT4.isEmpty()) {
                    ColorT4 = "A";
                    ColorT4Pri = "A";

                }
                if (ColorT5.isEmpty()) {
                    ColorT5 = "A";
                    ColorT5Pri = "A";

                }
                if (ColorT6.isEmpty()) {
                    ColorT6 = "A";
                    ColorT6Pri = "A";

                }
                if (ColorT7.isEmpty()) {
                    ColorT7 = "A";
                    ColorT7Pri = "A";
                }
                if (ColorT8.isEmpty()) {
                    ColorT8 = "A";
                    ColorT8Pri = "A";
                }
                if (ColorT9.isEmpty()) {
                    ColorT9 = "A";
                    ColorT9Pri = "A";
                }
                if (ColorT10.isEmpty()) {
                    ColorT10 = "A";
                    ColorT10Pri = "A";
                }

                if (!ColorT1.isEmpty() && TextUtils.isEmpty(ColorT1Pri)) {
                    Color1Pri.setError("Please Enter");
                }
                if (!ColorT2.isEmpty() && TextUtils.isEmpty(ColorT2Pri)) {
                    Color2Pri.setError("Please Enter");
                }
                if (!ColorT3.isEmpty() && TextUtils.isEmpty(ColorT3Pri)) {
                    Color3Pri.setError("Please Enter");
                }
                if (!ColorT4.isEmpty() && TextUtils.isEmpty(ColorT4Pri)) {
                    Color4Pri.setError("Please Enter");
                }
                if (!ColorT5.isEmpty() && TextUtils.isEmpty(ColorT5Pri)) {
                    Color5Pri.setError("Please Enter");
                }
                if (!ColorT6.isEmpty() && TextUtils.isEmpty(ColorT6Pri)) {
                    Color6Pri.setError("Please Enter");
                }
                if (!ColorT7.isEmpty() && TextUtils.isEmpty(ColorT7Pri)) {
                    Color7Pri.setError("Please Enter");
                }
                if (!ColorT8.isEmpty() && TextUtils.isEmpty(ColorT8Pri)) {
                    Color8Pri.setError("Please Enter");
                }
                if (!ColorT9.isEmpty() && TextUtils.isEmpty(ColorT9Pri)) {
                    Color9Pri.setError("Please Enter");
                }
                if (!ColorT10.isEmpty() && TextUtils.isEmpty(ColorT10Pri)) {
                    Color10Pri.setError("Please Enter");
                } else {

                    LoadingBar.show();

                    if(!Image1.equals("A") && !Image2.equals("A")){
                        SaveProductInfo(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                                AdultT, ChildT, Image1, Image2);
                    } else {
                        AddImage1(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10,
                                AdultT, ChildT);
                    }
                }
            }

        } catch (Exception e) {
            Toast.makeText(RetailAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void AddImage1(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                           String colorT8, String colorT9, String colorT10, String snRT1, String snRT2) {

        try {

            final StorageReference filePath = ProductImageRef.child(UImage1.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0, 4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage1);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(RetailAddPhoneActivity.this, "Error! " + massage, Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RetailAddPhoneActivity.this, "Product Image Upload Successfully", Toast.LENGTH_LONG).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                LoadingBar.dismiss();
                                throw task.getException();
                            }

                            downloadImageUri1 = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                String i1 = task.getResult().toString();
                                Toast.makeText(RetailAddPhoneActivity.this, "Getting Product Image 1 Uri Successfully" + i1, Toast.LENGTH_LONG).show();

                                AddImage2(colorT1, colorT2, colorT3, colorT4, colorT5, colorT6, colorT7, colorT8, colorT9, colorT10, snRT1,
                                        snRT2, i1);
                            }
                        }
                    });
                }
            });

        } catch (Exception e) {
            Toast.makeText(RetailAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void AddImage2(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                           String colorT8, String colorT9, String colorT10, String snRT1, String snRT2, String downloadImageUri1) {

        try {

            final StorageReference filePath = ProductImageRef.child(UImage2.getLastPathSegment().substring(0, 4) + UUID.randomUUID().toString().substring(0, 4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage2);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(RetailAddPhoneActivity.this, "Error! " + massage, Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RetailAddPhoneActivity.this, "Product Image Upload Successfully", Toast.LENGTH_LONG).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                LoadingBar.dismiss();
                                throw task.getException();
                            }

                            downloadImageUri2 = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                String i2 = task.getResult().toString();
                                Toast.makeText(RetailAddPhoneActivity.this, "Getting Product Image 2 Uri Successfully" + i2, Toast.LENGTH_LONG).show();

                                SaveProductInfo(colorT1, colorT2, colorT3, colorT4, colorT5, colorT6, colorT7, colorT8, colorT9, colorT10, snRT1,
                                        snRT2, downloadImageUri1, i2);
                            }
                        }
                    });
                }
            });

        } catch (Exception e) {
            Toast.makeText(RetailAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void SaveProductInfo(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                                 String colorT8, String colorT9, String colorT10, String snRT1, String snRT2,
                                 String image1, String image2) {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        storeCurruntDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        storeCurruntTime = currentTime.format(calendar.getTime());

        productRandomKey = storeCurruntDate + storeCurruntTime;

        String UID = UUID.randomUUID().toString().substring(0, 35);

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("Pid", productRandomKey);
        productMap.put("Date", storeCurruntDate);
        productMap.put("Time", storeCurruntTime);
        productMap.put("Description", Description);
        productMap.put("TName", Table_Name);
        productMap.put("TDetail", Table_Detail);
        productMap.put("TBrand", Table_Brand);
        productMap.put("Keyward", ProductKeyward);
        productMap.put("Company", Company);
        productMap.put("No1", colorT1);
        productMap.put("No2", colorT2);
        productMap.put("No3", colorT3);
        productMap.put("No4", colorT4);
        productMap.put("No5", colorT5);
        productMap.put("No6", colorT6);
        productMap.put("No7", colorT7);
        productMap.put("No8", colorT8);
        productMap.put("No9", colorT9);
        productMap.put("No10", colorT10);
        productMap.put("Adult", snRT1);
        productMap.put("Child", snRT2);
        productMap.put("Price", Price);
        productMap.put("No1Price", ColorT1Pri);
        productMap.put("No2Price", ColorT2Pri);
        productMap.put("No3Price", ColorT3Pri);
        productMap.put("No4Price", ColorT4Pri);
        productMap.put("No5Price", ColorT5Pri);
        productMap.put("No6Price", ColorT6Pri);
        productMap.put("No7Price", ColorT7Pri);
        productMap.put("No8Price", ColorT8Pri);
        productMap.put("No9Price", ColorT9Pri);
        productMap.put("No10Price", ColorT10Pri);
        productMap.put("AdultPrice", AdultTPri);
        productMap.put("ChildPrice", ChildTPri);
        productMap.put("MDate", ProductMDate);
        productMap.put("EDate", ProductEDate);
        productMap.put("Category", Chh);
        productMap.put("Quantity", Quantity);
        productMap.put("S1", "10");
        productMap.put("S2", "2");
        productMap.put("S3", "4");
        productMap.put("S4", "3");
        productMap.put("S5", "9");
        productMap.put("Image1", image1);
        productMap.put("Image2", image2);
        productMap.put("ProductName", ProductName);

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(RetailAddPhoneActivity.this, AdminMainActivity.class);

                            startActivity(i);
                            LoadingBar.dismiss();
                            Toast.makeText(RetailAddPhoneActivity.this, "Product is added successfully.......", Toast.LENGTH_LONG).show();
                        } else {
                            String massage = task.getException().toString();
                            Toast.makeText(RetailAddPhoneActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void OpenGallary() {
        dd++;
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, GallaryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                List<Bitmap> bitmaps = new ArrayList<>();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();

                        try {
                            InputStream is = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            bitmaps.add(bitmap);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(RetailAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {

                    Uri imageUri = data.getData();

                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(RetailAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


                int o = 0;

                for (Bitmap bm : bitmaps) {
                    o++;
                    addProduct.setVisibility(View.VISIBLE);

                    if (o == 1) {
                        UImage1 = getImageUri(RetailAddPhoneActivity.this, bm);
                        Toast.makeText(this, UImage1.toString(), Toast.LENGTH_SHORT).show();
                        picPhoto1.setImageBitmap(bm);
                    } else if (o == 2) {
                        UImage2 = getImageUri(RetailAddPhoneActivity.this, bm);
                        Toast.makeText(this, UImage2.toString(), Toast.LENGTH_SHORT).show();
                        picPhoto2.setImageBitmap(bm);
                    }

                }

            } else if (data.getData() != null) {
                Toast.makeText(RetailAddPhoneActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
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
            } finally {
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getImageString(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String path = Base64.encodeToString(b, Base64.DEFAULT);
        return path;
    }

    public class RetailProAdapter extends RecyclerView.Adapter<RetailProAdapter.Viewholder> {

        private ArrayList<Product> categoryModals;

        public RetailProAdapter(ArrayList<Product> categoryModals) {
            this.categoryModals = categoryModals;
        }

        @NonNull
        @Override
        public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_layout, parent, false);
            return new RetailProAdapter.Viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Viewholder holder, int position) {
            String S1 = categoryModals.get(position).getS1();
            String S2 = categoryModals.get(position).getS2();
            String S3 = categoryModals.get(position).getS3();
            String S4 = categoryModals.get(position).getS4();
            String S5 = categoryModals.get(position).getS5();

            int s1 = Integer.parseInt(S1);
            int s2 = Integer.parseInt(S2);
            int s3 = Integer.parseInt(S3);
            int s4 = Integer.parseInt(S4);
            int s5 = Integer.parseInt(S5);

            int Upper = (s1) + (s2 * 2) + (s3 * 3) + (s4 * 4) + (s5 * 5);

            int Lower = s1 + s2 + s3 + s4 + s5;

            BigInteger U = new BigInteger(String.valueOf(Upper));
            BigInteger L = new BigInteger(String.valueOf(Lower));

            float Ul = U.floatValue();
            float Ll = L.floatValue();

            float Total_S = Ul / Ll;
            String Total_Star = new DecimalFormat("#.#").format(Total_S);

            Picasso.get().load(categoryModals.get(position).getImage1()).fit().centerCrop().into(holder.imageView);
            holder.txtPRating.setText(Total_Star);
            holder.txtProductName.setText(categoryModals.get(position).getProductName());
            holder.txtSnR.setVisibility(View.INVISIBLE);
            holder.txtColor.setVisibility(View.INVISIBLE);
            holder.txtPricee.setVisibility(View.INVISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    productName.setText(categoryModals.get(position).getProductName());
                    productDescription.setText(categoryModals.get(position).getDescription());
                    productKeyeard.setText(categoryModals.get(position).getKeyward());
                    productCatagory.setText(categoryModals.get(position).getCategory());
                    productCompany.setText(categoryModals.get(position).getCompany());
                    productQuantity.setText(categoryModals.get(position).getQuantity());
                    productEDate.setText(categoryModals.get(position).getEDate());
                    productMDate.setText(categoryModals.get(position).getMDate());
                    productPrice.setText(categoryModals.get(position).getPrice());
                    Table_N.setText(categoryModals.get(position).getTName());
                    Table_D.setText(categoryModals.get(position).getTDetail());
                    Table_B.setText(categoryModals.get(position).getTBrand());
                    Color1.setText(categoryModals.get(position).getNo1());
                    Color2.setText(categoryModals.get(position).getNo2());
                    Color3.setText(categoryModals.get(position).getNo3());
                    Color4.setText(categoryModals.get(position).getNo4());
                    Color5.setText(categoryModals.get(position).getNo5());
                    Color6.setText(categoryModals.get(position).getNo6());
                    Color7.setText(categoryModals.get(position).getNo7());
                    Color8.setText(categoryModals.get(position).getNo8());
                    Color9.setText(categoryModals.get(position).getNo9());
                    Color10.setText(categoryModals.get(position).getNo10());
                    Color1Pri.setText(categoryModals.get(position).getNo1Price());
                    Color2Pri.setText(categoryModals.get(position).getNo2Price());
                    Color3Pri.setText(categoryModals.get(position).getNo3Price());
                    Color4Pri.setText(categoryModals.get(position).getNo4Price());
                    Color5Pri.setText(categoryModals.get(position).getNo5Price());
                    Color6Pri.setText(categoryModals.get(position).getNo6Price());
                    Color7Pri.setText(categoryModals.get(position).getNo7Price());
                    Color8Pri.setText(categoryModals.get(position).getNo8Price());
                    Color9Pri.setText(categoryModals.get(position).getNo9Price());
                    Color10Pri.setText(categoryModals.get(position).getNo10Price());
                    AdultPri.setText(categoryModals.get(position).getAdultPrice());
                    ChildPri.setText(categoryModals.get(position).getChildPrice());

                    Picasso.get().load(categoryModals.get(position).getImage1()).fit().centerCrop().into(picPhoto1);
                    Picasso.get().load(categoryModals.get(position).getImage2()).fit().centerCrop().into(picPhoto2);

                    Image1 = categoryModals.get(position).getImage1();
                    Image2 = categoryModals.get(position).getImage2();

                    addProduct.setVisibility(View.VISIBLE);

                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryModals.size();
        }

        class Viewholder extends RecyclerView.ViewHolder {

            public TextView txtProductName, txtPRating, txtSnR, txtColor, txtPricee;
            public ImageView imageView;

            public Viewholder(View itemview) {
                super(itemview);
                imageView = itemview.findViewById(R.id.Product_Image_II_PPPP);
                txtPricee = itemview.findViewById(R.id.Product_Price_II_PPPP);
                txtProductName = itemview.findViewById(R.id.Product_Name_II_PPPP);
                txtColor = itemview.findViewById(R.id.Color_PP_BBBB_II_PPPP);
                txtSnR = itemview.findViewById(R.id.Snr_PP_BBBB_II_PPPP);
                txtPRating = itemview.findViewById(R.id.setDasiReading_PS_II_PPPP);
            }
        }

    }

}