package com.example.akmuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.akmuser.Adapter.FiRecyAdapter;
import com.example.akmuser.Adapter.SearchAdapter;
import com.example.akmuser.Fragments.ProductDataFragment;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.View_Holder.productGridLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import io.paperdb.Paper;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView FiPhonb;
    private RecyclerView.LayoutManager layoutManagerFiPhonb;
    private String type, CCCC;
    private int h;
    private ImageView back;
    private ArrayList<Product> PList, QList, RList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        type = Paper.book().read(Prevalant.CheckAdmin);
        CCCC = getIntent().getStringExtra("loik");

        back = findViewById(R.id.back_sett_cat);

        FiPhonb = findViewById(R.id.reCattttLL);
        FiPhonb.setHasFixedSize(true);
        FiPhonb.setItemViewCacheSize(20);
        FiPhonb.setDrawingCacheEnabled(true);
        FiPhonb.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhonb = new LinearLayoutManager(CategoryActivity.this);
        FiPhonb.setLayoutManager(layoutManagerFiPhonb);

        Window window = CategoryActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        TakePID(CCCC);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
                i.putExtra("eeee", "CA");
                startActivity(i);

            }
        });

    }

    public void TakePID(String UU) {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("Products");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        CPrice((Map<String, Object>) snapshot.getValue(), UU);

                    } catch (Exception e) {

                        Toast.makeText(CategoryActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CPrice(Map<String, Object> snapshot, String uu) {

        ArrayList<Integer> Pric = new ArrayList<>();
        ArrayList<Integer> InPric = new ArrayList<>();

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Category"));

            if (oo.equals(uu)) {

                String sss = String.valueOf(SPro.get("Price"));

                Pric.add(Integer.parseInt(sss));
            }
        }

        LowHigh(Pric, uu);

    }

    private void LowHigh(ArrayList<Integer> priceFi, String gg) {

        Collections.sort(priceFi);

        GetListfi(priceFi, gg);

    }

    private void GetListfi(ArrayList<Integer> fPriceFi, String j) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        if (!ProductRef.equals(null))

            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        QList = new ArrayList<>();
                        RList = new ArrayList<>();
                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getCategory().toString();

                            if (h.equals(j)) {
                                QList.add(PList.get(g));
                            }

                        }

                        for (int t = 0; t < fPriceFi.size(); t++) {

                            for (int s = 0; s < QList.size(); s++) {

                                String ll = QList.get(s).getPrice();
                                String ff = String.valueOf(fPriceFi.get(t));

                                if (ll.equals(ff)) {

                                    RList.add(QList.get(s));

                                }

                            }

                        }


                        SearchAdapter adapter = new SearchAdapter(CategoryActivity.this, RList, type);

                        FiPhonb.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
        i.putExtra("eeee", "CA");
        startActivity(i);
    }

}