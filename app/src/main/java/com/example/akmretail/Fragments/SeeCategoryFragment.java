package com.example.akmretail.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmretail.Adapter.SearchAdapter;
import com.example.akmretail.KYCActivity;
import com.example.akmretail.Modal.Product;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;
import com.example.akmretail.ScanDocActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class SeeCategoryFragment extends Fragment {

    private RecyclerView FiPhonb;
    private RecyclerView.LayoutManager layoutManagerFiPhonb;
    private String type, CCCC, Vari;
    private int h;
    private ImageView back;
    private ArrayList<Product> PList, QList, RList, FList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_see_category, container, false);

        type = Paper.book().read(Prevalant.CatType);

        FiPhonb = root.findViewById(R.id.reCattttLL_frag);
        FiPhonb.setHasFixedSize(true);
        FiPhonb.setItemViewCacheSize(20);
        FiPhonb.setDrawingCacheEnabled(true);
        FiPhonb.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhonb = new LinearLayoutManager(getActivity());
        FiPhonb.setLayoutManager(layoutManagerFiPhonb);

        String LL = Paper.book().read(Prevalant.CheckH);
        String oo = Paper.book().read(Prevalant.FAD);


        Toast.makeText(getActivity(), oo + " " + LL , Toast.LENGTH_LONG).show();


        if(oo.equals("HomeA")){

            Paper.book().write(Prevalant.FAD, "A");
            Paper.book().write(Prevalant.CheckH, "A");

        }

        if(oo.equals("A") && LL.equals("SCA")){

            Paper.book().write(Prevalant.FAD, "A");
            Paper.book().write(Prevalant.CheckH, "A");

        }

        if(oo.equals("CategoryA")){

            Paper.book().write(Prevalant.CheckH, "CA");

        }

        TakeUser();



        return root;

    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("ReUser").child(Paper.book().read(Prevalant.UserIdA));

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    Vari = snapshot.child("Approve").getValue().toString();
                    Toast.makeText(getActivity(), Vari, Toast.LENGTH_SHORT).show();
                    GetListfi(type, Vari);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void GetListfi(String j, String vari) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("ReProduct");

        if (!ProductRef.equals(null))

            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        QList = new ArrayList<>();
                        RList = new ArrayList<>();
                        FList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getCategory();

                            if (h.equals(j)) {
                                QList.add(PList.get(g));
                            }
                        }

                        SearchAdapter adapter = new SearchAdapter(getActivity(), QList, "SCA", vari);

                        FiPhonb.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

}
