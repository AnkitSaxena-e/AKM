package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Modal.OPro;
import com.example.akm_admin.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class RetailSeeOrderDetailActivity extends AppCompatActivity {


    private TextView O_Name, O_Number, O_Address, O_ONO, O_DT, O_DetailDate, O_Return, O_ShopName;
    private ImageView PP_Image;
    private ImageView O_Name_Copy, O_Number_Copy, O_Address_Copy, O_ONO_Copy, O_DT_Copy, O_DetailDate_Copy, O_Return_Copy, O_Shop_Copy;
    private String PID, Type, From, To, Name, NNO, ONO, Address, Amount, City, Pin, Return, DD, PPPP, Buy, Shop;
    private ClipboardManager clipboardManager;
    private Button OrDele, GeneBill, CompOrder;
    private RecyclerView RePro;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_see_order_detail);

        O_Name = findViewById(R.id.see_order_name_retail);
        O_Number = findViewById(R.id.see_order_num_retail);
        O_Address = findViewById(R.id.see_order_address_retail);
        O_ONO = findViewById(R.id.see_order_Onum_retail);
        O_DT = findViewById(R.id.see_order_pid_retail);
        O_DetailDate = findViewById(R.id.see_order_detaildate_retail);
        O_Return = findViewById(R.id.see_order_return_retail);
        O_ShopName = findViewById(R.id.see_Shop_retail);

        OrDele = findViewById(R.id.Dele_Order_retail);
        GeneBill = findViewById(R.id.PDF_Order_retail);
        CompOrder = findViewById(R.id.Com_Order_retail);

        O_Name_Copy = findViewById(R.id.Name_C_retail);
        O_Number_Copy = findViewById(R.id.num_C_retail);
        O_Address_Copy = findViewById(R.id.address_C_retail);
        O_ONO_Copy = findViewById(R.id.Onum_C_retail);
        O_DT_Copy = findViewById(R.id.pid_C_retail);
        O_DetailDate_Copy = findViewById(R.id.detaildate_C_retail);
        O_Return_Copy = findViewById(R.id.return_C_retail);
        O_Shop_Copy = findViewById(R.id.shop_C_retail);

        RePro = findViewById(R.id.AdminROSeeOrderUU_retail);
        RePro.setHasFixedSize(true);
        RePro.setItemViewCacheSize(20);
        RePro.setDrawingCacheEnabled(true);
        RePro.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManager = new LinearLayoutManager(RetailSeeOrderDetailActivity.this);
        RePro.setLayoutManager(layoutManager);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        PID = getIntent().getStringExtra("uid");

        SetOrderDetail(PID);

        GeneBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RetailSeeOrderDetailActivity.this, CreatePDFActivity.class);
                i.putExtra("PPP", PID);
                startActivity(i);
            }
        });

        CompOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("ReOrder").child(PID);

                HashMap<String, Object> productMap = new HashMap<>();

                productMap.put("Return", "Complete");

                AlertDialog.Builder aD = new AlertDialog.Builder(RetailSeeOrderDetailActivity.this);
                aD.setMessage("Your Complete this Order..");
                aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ProfileRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RetailSeeOrderDetailActivity.this, "Complete Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                aD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = aD.create();
                alertDialog.show();

            }
        });

        OrDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("ReOrder").child(PID);

                AlertDialog.Builder aD = new AlertDialog.Builder(RetailSeeOrderDetailActivity.this);
                aD.setMessage("Are You Sure, Your Wanna Delete this Order..");
                aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ProfileRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RetailSeeOrderDetailActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                aD.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = aD.create();
                alertDialog.show();

            }
        });
    }

    private void SetOrderDetail(String pid) {

        final DatabaseReference oAdminA = FirebaseDatabase.getInstance().getReference()
                .child("ReOrder").child(pid);

        oAdminA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    try {

                        Name = dataSnapshot.child("Name").getValue().toString();
                        NNO = dataSnapshot.child("PhoneNumber").getValue().toString();
                        ONO = dataSnapshot.child("ONO").getValue().toString();
                        Address = dataSnapshot.child("Address").getValue().toString();
                        PID = dataSnapshot.child("PID").getValue().toString();
                        Amount = dataSnapshot.child("TotalAmount").getValue().toString();
                        City = dataSnapshot.child("City").getValue().toString();
                        Pin = dataSnapshot.child("Pin").getValue().toString();
                        Return = dataSnapshot.child("Return").getValue().toString();
                        DD = dataSnapshot.child("DeliveryDetail").getValue().toString();
                        Shop = dataSnapshot.child("Shop").getValue().toString();

                        Buy = dataSnapshot.child("Buy").getValue().toString();
                        PPPP = dataSnapshot.child("PNO").getValue().toString();

                        O_Name.setText("Name: " + Name);
                        O_Number.setText("Orignal No: " + NNO);
                        O_Address.setText("Address: " + Address + ", " + City + ", " + Pin);
                        O_ONO.setText("Order No: " + ONO);
                        O_DT.setText("PID: " + PID);
                        O_DetailDate.setText("Delivery Date: " + DD);
                        O_Return.setText("Return: " + Return);
                        O_ShopName.setText("Shop: " + Shop);

                        ProductPPID(Buy, ONO, PPPP);
                        CopyT();

                    }catch (Exception e){
                        Toast.makeText(RetailSeeOrderDetailActivity.this, "aaaaa " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ProductPPID(String buy, String PPPP, String pppp) {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("ReOrderList").child(PPPP).child(buy).child(pppp);

        FirebaseRecyclerOptions<OPro> OrOption =
                new FirebaseRecyclerOptions.Builder<OPro>()
                        .setQuery(OrREF, OPro.class)
                        .build();

        FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                    @Override
                    protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                        String V = Layout.getPVar();
                        String N = Layout.getPNum();

                        if(!V.equals("AA")){

                            Holder.tltProductColor.setText(Layout.getPVar());

                        } else if(!N.equals("AA")){

                            Holder.tltProductColor.setText(Layout.getPNum());

                        }else{

                            Holder.tltProductColor.setVisibility(View.INVISIBLE);

                        }

                        Holder.tltProductName.setText(Layout.getPName());
                        Holder.tltProductPrice.setText(Layout.getPPri());
                        Holder.tltProductCompany.setText(Layout.getPCom());
                        Holder.tltProductQuantity.setText(Layout.getPQut());
                        Picasso.get().load(Layout.getPImage()).fit().centerCrop().into(Holder.tltProductImage);

                    }

                    @NonNull
                    @Override
                    public catagorySearchVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_resource_file, parent, false);
                        catagorySearchVewHolder holder = new catagorySearchVewHolder(view);
                        return holder;
                    }
                };

        RePro.setAdapter(OrAdapter);
        OrAdapter.startListening();

    }

    private void CopyT() {

        O_Name_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Name;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Number_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = NNO;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Address_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Address;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_ONO_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = ONO;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_DT_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = PID;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_DetailDate_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = DD;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Return_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Return;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        O_Shop_Copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Copy = Shop;

                ClipData clipData = ClipData.newPlainText("text", Copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(RetailSeeOrderDetailActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });


    }

}