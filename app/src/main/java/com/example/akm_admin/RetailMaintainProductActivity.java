package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class RetailMaintainProductActivity extends AppCompatActivity {

    private String Image1, Image2;

    private String ProductName, Description, Price, ProductKeyward, ProductCompany, ProductEDate, ProductMDate, ProductCategory, ProductQuantity;
    private String ColorT1 = "A", ColorT2 = "A", ColorT3 = "A", ColorT4 = "A", ColorT5 = "A", ColorT6 = "A",
            ColorT7 = "A", ColorT8 = "A", ColorT9 = "A", ColorT10 = "A";
    private String ColorT1Pri = "A", ColorT2Pri = "A", ColorT3Pri = "A", ColorT4Pri = "A", ColorT5Pri = "A", ColorT6Pri = "A", ColorT7Pri = "A",
            ColorT8Pri = "A", ColorT9Pri = "A", ColorT10Pri = "A";
    private String Table_Name, Table_Detail, Table_Brand;
    private EditText productName, productDescription, productKeyeard, productQuantity, productPrice, productCompany, productEDate, productMDate,
            productCategory;
    private EditText Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9, Color10;
    private EditText Color1Pri, Color2Pri, Color3Pri, Color4Pri, Color5Pri, Color6Pri, Color7Pri, Color8Pri, Color9Pri, Color10Pri;
    private TextView Adult, Child;
    private EditText AdultPri, ChildPri;
    private EditText Table_N, Table_D, Table_B;
    private ImageView picPhoto1, picPhoto2;
    private String PPPIIIDDD = "";
    private Button addProduct, deletePro;
    private DatabaseReference ProductRef;
    private String productRandomKey;
    int dd = 0, ii = 0;
    private Dialog LoadingBar;
    private String AdultT, ChildT, AdultTPri = "A", ChildTPri = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_maintain_product);

        PPPIIIDDD = getIntent().getStringExtra("pid");

        Toast.makeText(this, PPPIIIDDD, Toast.LENGTH_SHORT).show();
        Paper.init(RetailMaintainProductActivity.this);

        productName = findViewById(R.id.ProductName_MMMM_R);
        productDescription = findViewById(R.id.ProductDescription_MMMM_R);
        productKeyeard = findViewById(R.id.ProductKeyward_MMMM_R);
        productCategory = findViewById(R.id.ProductCategory_MMMM_R);
        productCompany = findViewById(R.id.ProductCompany_MMMM_R);
        productQuantity = findViewById(R.id.ProductQuantity_MMMM_R);
        productEDate = findViewById(R.id.ProductEDate_MMMM_R);
        productMDate = findViewById(R.id.ProductMDate_MMMM_R);

        productPrice = findViewById(R.id.ProductPrice_MMMM_R);

        picPhoto1 = findViewById(R.id.PicPhoto1_MMMM_R);
        picPhoto2 = findViewById(R.id.PicPhoto2_MMMM_R);

        Table_N = findViewById(R.id.name_T_A_MMMM_R);
        Table_D = findViewById(R.id.Detail_T_A_MMMM_R);
        Table_B = findViewById(R.id.brand_T_A_MMMM_R);

        Color1 = findViewById(R.id.ProductColor1_MMMM_R);
        Color2 = findViewById(R.id.ProductColor2_MMMM_R);
        Color3 = findViewById(R.id.ProductColor3_MMMM_R);
        Color4 = findViewById(R.id.ProductColor4_MMMM_R);
        Color5 = findViewById(R.id.ProductColor5_MMMM_R);
        Color6 = findViewById(R.id.ProductColor6_MMMM_R);
        Color7 = findViewById(R.id.ProductColor7_MMMM_R);
        Color8 = findViewById(R.id.ProductColor8_MMMM_R);
        Color9 = findViewById(R.id.ProductColor9_MMMM_R);
        Color10 = findViewById(R.id.ProductColor10_MMMM_R);

        Color1Pri = findViewById(R.id.ProductColor1Pri_MMMM_R);
        Color2Pri = findViewById(R.id.ProductColor2Pri_MMMM_R);
        Color3Pri = findViewById(R.id.ProductColor3Pri_MMMM_R);
        Color4Pri = findViewById(R.id.ProductColor4Pri_MMMM_R);
        Color5Pri = findViewById(R.id.ProductColor5Pri_MMMM_R);
        Color6Pri = findViewById(R.id.ProductColor6Pri_MMMM_R);
        Color7Pri = findViewById(R.id.ProductColor7Pri_MMMM_R);
        Color8Pri = findViewById(R.id.ProductColor8Pri_MMMM_R);
        Color9Pri = findViewById(R.id.ProductColor9Pri_MMMM_R);
        Color10Pri = findViewById(R.id.ProductColor10Pri_MMMM_R);

        Adult = findViewById(R.id.ProductSR1_MMMM_R);
        Child = findViewById(R.id.ProductSR2_MMMM_R);

        AdultPri = findViewById(R.id.AdultPri_MMMM_R);
        ChildPri = findViewById(R.id.ChildPri_MMMM_R);

        LoadingBar = new Dialog(RetailMaintainProductActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        addProduct = findViewById(R.id.AddProduct_MMMM_R);
        deletePro = findViewById(R.id.DeliProduct_MMMM_R);

        addProduct.setVisibility(View.VISIBLE);

        ProductRef = FirebaseDatabase.getInstance().getReference().child("ReProduct");

        SetDataEE(PPPIIIDDD);

        Adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AdultT.equals("Yes")) {
                    Adult.setBackgroundResource(R.drawable.loginbutton);
                    AdultT = "No";
                }

                else {
                    Adult.setBackgroundResource(R.drawable.joinbutton);
                    AdultT = "Yes";
                }

            }
        });

        Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ChildT.equals("Yes")) {
                    Child.setBackgroundResource(R.drawable.loginbutton);
                    ChildT = "No";
                }

                else {
                    Child.setBackgroundResource(R.drawable.joinbutton);
                    ChildT = "Yes";
                }

            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });

        deletePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductRef.child(PPPIIIDDD).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(RetailMaintainProductActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }

    private void ValidateProduct() {

        try {

            ProductName = productName.getText().toString();
            Description = productDescription.getText().toString();
            ProductKeyward = productKeyeard.getText().toString();
            ProductCategory = productCategory.getText().toString();
            ProductCompany = productCompany.getText().toString();
            ProductEDate = productEDate.getText().toString();
            ProductMDate = productMDate.getText().toString();
            ProductQuantity = productQuantity.getText().toString();
            Table_Name = Table_N.getText().toString();
            Table_Detail = Table_D.getText().toString();
            Table_Brand = Table_B.getText().toString();

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
            ColorT10Pri= Color10Pri.getText().toString();

            AdultTPri = AdultPri.getText().toString();
            ChildTPri = ChildPri.getText().toString();

            Price = productPrice.getText().toString();

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
            } else if(TextUtils.isEmpty(ProductCategory)){
                productCategory.setError("Please Enter");
            } else if(TextUtils.isEmpty(ProductCompany)){
                productCompany.setError("Please Enter");
            } else if(TextUtils.isEmpty(ProductEDate)){
                productEDate.setError("Please Enter");
            } else if(TextUtils.isEmpty(ProductMDate)){
                productMDate.setError("Please Enter");
            } else if(TextUtils.isEmpty(ProductQuantity)){
                productQuantity.setError("Please Enter");
            } else {

                if(Price.isEmpty()){
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
                }

                else {
                    LoadingBar.show();
                    SaveProductInfo(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10, AdultT, ChildT);
                }

            }

        }
        catch (Exception e){
            Toast.makeText(RetailMaintainProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void SaveProductInfo(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                                 String colorT8, String colorT9, String colorT10, String snRT1, String snRT2) {

        try {

            productRandomKey = PPPIIIDDD;

            HashMap<String, Object> productMap = new HashMap<>();

            productMap.put("Pid", productRandomKey);
            productMap.put("Description", Description);
            productMap.put("TName", Table_Name);
            productMap.put("TDetail", Table_Detail);
            productMap.put("TBrand", Table_Brand);
            productMap.put("Keyward", ProductKeyward);
            productMap.put("Company", ProductCompany);
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
            productMap.put("Category", ProductCategory);
            productMap.put("Quantity", ProductQuantity);
            productMap.put("S1", "10");
            productMap.put("S2", "2");
            productMap.put("S3", "4");
            productMap.put("S4", "3");
            productMap.put("S5", "9");
            productMap.put("ProductName", ProductName);

            ProductRef.child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(RetailMaintainProductActivity.this, AdminMainActivity.class);
                                startActivity(i);
                                LoadingBar.dismiss();
                                Toast.makeText(RetailMaintainProductActivity.this, "Product is updated successfully.......", Toast.LENGTH_LONG).show();
                            } else {
                                String massage = task.getException().toString();
                                Toast.makeText(RetailMaintainProductActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        catch (Exception e){
            Toast.makeText(RetailMaintainProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void SetDataEE(String pppiiiddd) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("ReProduct");

        productRef.child(pppiiiddd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                    String Namemm = dataSnapshot.child("ProductName").getValue().toString();
                    String Description = dataSnapshot.child("Description").getValue().toString();

                    String TN = dataSnapshot.child("TName").getValue().toString();
                    String TD = dataSnapshot.child("TDetail").getValue().toString();
                    String TB = dataSnapshot.child("TBrand").getValue().toString();

                    Image1 = dataSnapshot.child("Image1").getValue().toString();
                    Image2 = dataSnapshot.child("Image2").getValue().toString();

                    String P = dataSnapshot.child("Price").getValue().toString();

                    String N1 = dataSnapshot.child("No1").getValue().toString();
                    String N2 = dataSnapshot.child("No2").getValue().toString();
                    String N3 = dataSnapshot.child("No3").getValue().toString();
                    String N4 = dataSnapshot.child("No4").getValue().toString();
                    String N5 = dataSnapshot.child("No5").getValue().toString();
                    String N6 = dataSnapshot.child("No6").getValue().toString();
                    String N7 = dataSnapshot.child("No7").getValue().toString();
                    String N8 = dataSnapshot.child("No8").getValue().toString();
                    String N9 = dataSnapshot.child("No9").getValue().toString();
                    String N10 = dataSnapshot.child("No10").getValue().toString();

                    String PN1 = dataSnapshot.child("No1Price").getValue().toString();
                    String PN2 = dataSnapshot.child("No2Price").getValue().toString();
                    String PN3 = dataSnapshot.child("No3Price").getValue().toString();
                    String PN4 = dataSnapshot.child("No4Price").getValue().toString();
                    String PN5 = dataSnapshot.child("No5Price").getValue().toString();
                    String PN6 = dataSnapshot.child("No6Price").getValue().toString();
                    String PN7 = dataSnapshot.child("No7Price").getValue().toString();
                    String PN8 = dataSnapshot.child("No8Price").getValue().toString();
                    String PN9 = dataSnapshot.child("No9Price").getValue().toString();
                    String PN10 = dataSnapshot.child("No10Price").getValue().toString();


                    String Ad = dataSnapshot.child("Adult").getValue().toString();
                    String Ch = dataSnapshot.child("Child").getValue().toString();

                    String AdP = dataSnapshot.child("AdultPrice").getValue().toString();
                    String ChP = dataSnapshot.child("ChildPrice").getValue().toString();

                    String Quan = dataSnapshot.child("Quantity").getValue().toString();

                    String Com = dataSnapshot.child("Company").getValue().toString();
                    String Key = dataSnapshot.child("Keyward").getValue().toString();
                    String Cate = dataSnapshot.child("Category").getValue().toString();

                    String ED = dataSnapshot.child("EDate").getValue().toString();
                    String MD = dataSnapshot.child("MDate").getValue().toString();

                    productPrice.setText(P, TextView.BufferType.EDITABLE);
                    Adult.setText(Ad);
                    Child.setText(Ch);

                    AdultPri.setText(AdP,TextView.BufferType.EDITABLE);
                    ChildPri.setText(AdP,TextView.BufferType.EDITABLE);

                    Picasso.get().load(Image1).fit().centerCrop().into(picPhoto1);
                    Picasso.get().load(Image2).fit().centerCrop().into(picPhoto2);

                    AdultT = Ad;
                    ChildT = Ch;

                    productName.setText(Namemm, TextView.BufferType.EDITABLE);
                    productDescription.setText(Description, TextView.BufferType.EDITABLE);
                    productKeyeard.setText(Key, TextView.BufferType.EDITABLE);
                    productCategory.setText(Cate, TextView.BufferType.EDITABLE);
                    productCompany.setText(Com, TextView.BufferType.EDITABLE);
                    productEDate.setText(ED, TextView.BufferType.EDITABLE);
                    productMDate.setText(MD, TextView.BufferType.EDITABLE);
                    productQuantity.setText(Quan, TextView.BufferType.EDITABLE);

                    Table_N.setText(TN, TextView.BufferType.EDITABLE);
                    Table_D.setText(TD, TextView.BufferType.EDITABLE);
                    Table_B.setText(TB, TextView.BufferType.EDITABLE);

                    Color1.setText(N1, TextView.BufferType.EDITABLE);
                    Color2.setText(N2, TextView.BufferType.EDITABLE);
                    Color3.setText(N3, TextView.BufferType.EDITABLE);
                    Color4.setText(N4, TextView.BufferType.EDITABLE);
                    Color5.setText(N5, TextView.BufferType.EDITABLE);
                    Color6.setText(N6, TextView.BufferType.EDITABLE);
                    Color7.setText(N7, TextView.BufferType.EDITABLE);
                    Color8.setText(N8, TextView.BufferType.EDITABLE);
                    Color9.setText(N9, TextView.BufferType.EDITABLE);
                    Color10.setText(N10, TextView.BufferType.EDITABLE);

                    Color1Pri.setText(PN1, TextView.BufferType.EDITABLE);
                    Color2Pri.setText(PN2, TextView.BufferType.EDITABLE);
                    Color3Pri.setText(PN3, TextView.BufferType.EDITABLE);
                    Color4Pri.setText(PN4, TextView.BufferType.EDITABLE);
                    Color5Pri.setText(PN5, TextView.BufferType.EDITABLE);
                    Color6Pri.setText(PN6, TextView.BufferType.EDITABLE);
                    Color7Pri.setText(PN7, TextView.BufferType.EDITABLE);
                    Color8Pri.setText(PN8, TextView.BufferType.EDITABLE);
                    Color9Pri.setText(PN9, TextView.BufferType.EDITABLE);
                    Color10Pri.setText(PN10, TextView.BufferType.EDITABLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}