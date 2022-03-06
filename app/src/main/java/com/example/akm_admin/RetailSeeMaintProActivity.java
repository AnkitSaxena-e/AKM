package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.akm_admin.Adapter.CategoryAdapter;
import com.example.akm_admin.Modal.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RetailSeeMaintProActivity extends AppCompatActivity {

    private SearchView S_PhoneS;
    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private DatabaseReference SearchRef;

    private ArrayList<Product> PList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_see_maint_pro);

        S_PhoneS = findViewById(R.id.search_view_EPP_R);

        R_PhoneS = findViewById(R.id.EPP_ReLay_R);
        R_PhoneS.setHasFixedSize(true);
        R_PhoneS.setItemViewCacheSize(20);
        R_PhoneS.setDrawingCacheEnabled(true);
        R_PhoneS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(RetailSeeMaintProActivity.this);
        R_PhoneS.setLayoutManager(layoutManagerSS);

        SearchRef = FirebaseDatabase.getInstance().getReference().child("ReProduct");

    }

    @Override
    public void onStart() {
        super.onStart();

        if(!SearchRef.equals(null)) {

            SearchRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        CategoryAdapter adapter = new CategoryAdapter(RetailSeeMaintProActivity.this, PList, "Re");
                        R_PhoneS.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(!S_PhoneS.equals(null)){

            S_PhoneS.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    searchIt(newText);
                    return false;
                }
            });
        }
    }

    private void searchIt(String newText) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : PList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        CategoryAdapter adapter = new CategoryAdapter(RetailSeeMaintProActivity.this, Newlist, "Admin");
        R_PhoneS.setAdapter(adapter);
    }

}