package com.example.akmuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmuser.Fragments.ProductDataFragment;
import com.example.akmuser.Modal.OPro;
import com.example.akmuser.Modal.Order;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.View_Holder.catagorySearchVewHolder;
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

import io.paperdb.Paper;

public class SeeBuyingActivity extends AppCompatActivity {

    private TextView OD_Name, OD_Number, OD_Address, OD_City, OD_Pin, OD_TotalAmount, OD_Date, OD_Time, OD_DD;
    private Button ReturnBB;
    private String ProductPID, T;
    private ImageView back;
    private RelativeLayout llll;
    private RecyclerView RePro;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_buying);

        ProductPID = getIntent().getStringExtra("PPIdd");

        back = findViewById(R.id.back_sett_seeeeee);

        OD_Name = findViewById(R.id.see_order_name_BB);
        OD_Number = findViewById(R.id.see_order_PID_BB);
        OD_Address = findViewById(R.id.see_order_order_number_BB);
        OD_City = findViewById(R.id.see_order_login_number_BB);
        OD_Pin = findViewById(R.id.see_order_address_BB);
        OD_TotalAmount = findViewById(R.id.see_order_amount_BB);
        OD_Date = findViewById(R.id.see_order_type_BB);
        OD_Time = findViewById(R.id.see_order_orderNo_BB);
        OD_DD = findViewById(R.id.see_order_status_BB);

        RePro = findViewById(R.id.UserROSeeOrderUU);
        RePro.setHasFixedSize(true);
        RePro.setItemViewCacheSize(20);
        RePro.setDrawingCacheEnabled(true);
        RePro.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManager = new LinearLayoutManager(SeeBuyingActivity.this);
        RePro.setLayoutManager(layoutManager);

        ReturnBB = findViewById(R.id.Return_BB);

        Window window = SeeBuyingActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        getOrr(ProductPID);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
                i.putExtra("eeee", "BA");
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        getOrr(ProductPID);

    }

    private void getOrr(String orderPID) {

        DatabaseReference reffreref = FirebaseDatabase.getInstance().getReference().child("Order").child(orderPID);

        reffreref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String Name = dataSnapshot.child("Name").getValue().toString();
                    String NNO = dataSnapshot.child("PhoneNumber").getValue().toString();
                    String ONO = dataSnapshot.child("ONO").getValue().toString();
                    String Address = dataSnapshot.child("Address").getValue().toString();
                    String PID = dataSnapshot.child("PID").getValue().toString();
                    String Amount = dataSnapshot.child("TotalAmount").getValue().toString();
                    String City = dataSnapshot.child("City").getValue().toString();
                    String Pin = dataSnapshot.child("Pin").getValue().toString();
                    String Return = dataSnapshot.child("Return").getValue().toString();
                    String Date = dataSnapshot.child("Date").getValue().toString();
                    String Time = dataSnapshot.child("Time").getValue().toString();
                    String PNO = dataSnapshot.child("PNO").getValue().toString();

                    String Buy = dataSnapshot.child("Buy").getValue().toString();

                    OD_Name.setText("Name: " + Name);
                    OD_Number.setText("Number: " + NNO);
                    OD_Address.setText("Address: " + Address);
                    OD_City.setText("City:" + City);
                    OD_Pin.setText("Pin: " + Pin);
                    OD_TotalAmount.setText("Total Amount: ₹" + Amount);
                    OD_Date.setText("Date: " + Date);
                    OD_Time.setText("Time: " + Time);

                    getProd(Buy, PNO);

                    if(!Return.equals("A")){

                        ReturnBB.setText("Your Return is Accepted...Please Wait For Our Response");
                        ReturnBB.setClickable(false);

                    }

                    ReturnBB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(!Return.equals("A")){
                                AlertDialog.Builder aD = new AlertDialog.Builder(SeeBuyingActivity.this);
                                aD.setMessage("Note : Return Will Not Applicable if The Packaging was Not Damaged." + "\n" + "Are You Sure, Your Wanna Return this Order..");
                                aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        HashMap<String, Object> ll = new HashMap<>();

                                        ll.put("Return", "Return");

                                        reffreref.updateChildren(ll).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(SeeBuyingActivity.this, "Your Return is Accepted...Please Wait For Our Response", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

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
                            }else {

                                Toast.makeText(SeeBuyingActivity.this, "Please Wait For Our Response", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getProd(String buy, String NT) {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child(buy).child(NT);

        FirebaseRecyclerOptions<OPro> OrOption =
                new FirebaseRecyclerOptions.Builder<OPro>()
                        .setQuery(OrREF, OPro.class)
                        .build();

        FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                    @Override
                    protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                        Holder.tltProductName.setText(Layout.getPName());
                        Holder.tltProductPrice.setText(Layout.getPPri());
                        Holder.tltProductCompany.setText(Layout.getPCom());
                        Holder.tltProductColor.setText(Layout.getPVar());
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SeeBuyingActivity.this, HomeActivity.class);
        i.putExtra("eeee", "BA");
        startActivity(i);
    }
}