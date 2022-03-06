package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akm_admin.Modal.SearchCategoryModal;
import com.example.akm_admin.Prevalant.Prevalant;
import com.example.akm_admin.View_Holder.CategoryViewHolder;
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

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class RetSeeUserDetailActivity extends AppCompatActivity {

    private TextView profileNameA, profileNumberA, profileShopA, profileApproveA;
    private RecyclerView RecyclerViewAPA;
    private RecyclerView.LayoutManager layoutManagerAPA;
    private Button DeleteAcc, Susp_User, Approve_Ret;
    private FirebaseRecyclerOptions<SearchCategoryModal> optionsAd;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapterAd;
    private DatabaseReference UserAddressref;
    private ImageView PhotoD1, PhotoD2;
    String EInfo, NUMA = "0", UUUU, CC = "TT";
    CircleImageView profile_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ret_see_user_detail);

        EInfo = Paper.book().read(Prevalant.ACAdmin);

        RecyclerViewAPA = findViewById(R.id.addressSeeAdmin_retail);
        RecyclerViewAPA.setDrawingCacheEnabled(true);
        RecyclerViewAPA.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAPA = new LinearLayoutManager(RetSeeUserDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewAPA.setLayoutManager(layoutManagerAPA);

        profileNameA = findViewById(R.id.profile_nameA_retail);
        profileNumberA = findViewById(R.id.profile_numberA_retail);
        profileShopA = findViewById(R.id.ShopeNameProfileA_retail);
        profileApproveA = findViewById(R.id.ApproveA_retail);
        profile_Image = findViewById(R.id.user_profile_image_profileA_retail);

        PhotoD1 = findViewById(R.id.PicPhoto1_User_ret);
        PhotoD2 = findViewById(R.id.PicPhoto2_User_ret);

        DeleteAcc = findViewById(R.id.Delete_Account_retail);
        Susp_User = findViewById(R.id.Sus_User_retail);
        Approve_Ret = findViewById(R.id.Approve_User_Ret);

        TakeInfo(EInfo);

        CheckSusp(EInfo);

        DeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMA = EInfo;

                if (TextUtils.isEmpty(NUMA)) {
                    Toast.makeText(RetSeeUserDetailActivity.this, "Please Enter Number", Toast.LENGTH_LONG).show();
                } else {
                    delInfo(NUMA);
                }
            }
        });

        Susp_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CC.equals("FF")){
                    UnSus(EInfo);
                }else{
                    SusUser(EInfo);
                }

            }
        });

        Approve_Ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApproveUser(EInfo);

            }
        });

    }

    private void ApproveUser(String eInfo) {

        final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("ReUser").child(eInfo);

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("Approve", "Approved");

        AlertDialog.Builder aD = new AlertDialog.Builder(RetSeeUserDetailActivity.this);
        aD.setMessage("Did You Check All Document Carefully..");
        aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProfileRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RetSeeUserDetailActivity.this, "Approve Successfully", Toast.LENGTH_SHORT).show();
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

    private void UnSus(String eInfo) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("ReUser").child(eInfo);

        HashMap<String, Object> hh = new HashMap<>();

        hh.put("Suspend", "A");

        rty.updateChildren(hh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(RetSeeUserDetailActivity.this, "UnSuspended", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void CheckSusp(String Info) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Info);

        rty.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String ii = snapshot.child("Suspend").getValue().toString();

                    if(ii.equals("Suspended")){

                        Susp_User.setText("UnSuspend");

                        CC = "FF";

                    }else{

                        Susp_User.setText("Suspend");

                        CC = "WW";

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SusUser(String nInfo) {

        DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("ReUser").child(nInfo);

        HashMap<String, Object> hh = new HashMap<>();

        hh.put("Suspend", "Suspended");

        rty.updateChildren(hh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(RetSeeUserDetailActivity.this, "Suspended", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void WarUser(String wt, String EInfo) {

        DatabaseReference reffff = FirebaseDatabase.getInstance().getReference().child("ReUser");

        HashMap<String, Object> hshs = new HashMap<>();

        hshs.put("Warn", wt);

        reffff.child(EInfo).updateChildren(hshs).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(RetSeeUserDetailActivity.this, "Warn Successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        UserAddressref = FirebaseDatabase.getInstance().getReference()
                .child("UReAddressI").child(EInfo);

        optionsAd =
                new  FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UserAddressref, SearchCategoryModal.class)
                        .build();

        adapterAd =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(optionsAd) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder , final int i , @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress() + "," + modal.getCity() + "," + modal.getState());
                        holder.CatPin.setText(modal.getPin());
                        holder.CatRemoveButton.setVisibility(View.INVISIBLE);
                        holder.CatEditButton.setVisibility(View.INVISIBLE);

                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_resource_file, parent, false);
                        CategoryViewHolder holder = new CategoryViewHolder(view);
                        return holder;
                    }
                };
        RecyclerViewAPA.setAdapter(adapterAd);
        adapterAd.startListening();
    }

    private void delInfo(String num) {

        final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("ReUser").child(num);

        AlertDialog.Builder aD = new AlertDialog.Builder(RetSeeUserDetailActivity.this);
        aD.setMessage("Are You Sure, Your Wanna Delete this User..");
        aD.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ProfileRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RetSeeUserDetailActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();
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

    private void TakeInfo(final String num) {

        final DatabaseReference ProfileRef = FirebaseDatabase.getInstance().getReference().child("ReUser").child(num);

        ProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showInform(num);
                } else {
                    Toast.makeText(RetSeeUserDetailActivity.this, "Account with" + num + "does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showInform(String num) {

        DatabaseReference refref = FirebaseDatabase.getInstance().getReference().child("ReUser").child(num);

        refref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String NNNN = dataSnapshot.child("Name").getValue().toString();
                    String Nuuu = dataSnapshot.child("Number").getValue().toString();
                    String Nppp = dataSnapshot.child("Shop").getValue().toString();
                    String Npplp = dataSnapshot.child("Approve").getValue().toString();
                    String I1 = dataSnapshot.child("ImageF").getValue().toString();
                    String I2 = dataSnapshot.child("ImageB").getValue().toString();

                    try{
                        String Niii = dataSnapshot.child("Image").getValue().toString();

                        Picasso.get().load(Niii).into(profile_Image);

                    }catch (Exception n){
                        Toast.makeText(RetSeeUserDetailActivity.this, "Pic is not available", Toast.LENGTH_SHORT).show();
                    }

                    if(!I1.equals("A") && !I2.equals("A")){
                        Picasso.get().load(I1).into(PhotoD1);
                        Picasso.get().load(I2).into(PhotoD2);
                    }

                    profileNameA.setText("Name - " + NNNN);
                    profileNumberA.setText("Number - " + Nuuu);
                    profileShopA.setText("Shop - " + Nppp);
                    profileApproveA.setText("Approve - " + Npplp);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RetSeeUserDetailActivity.this, RetailActivity.class);
        startActivity(i);
    }

}