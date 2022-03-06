package com.example.akmretail.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

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

public class GalleryFragment extends Fragment {

    private SearchView searchViewA;
    private RecyclerView ReSear;
    private ArrayList<Product> PList, FList;
    private String Check, Vari;
    private int j = 0;
    private RecyclerView.LayoutManager layoutManagerSer;
    private DatabaseReference refgty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        searchViewA = root.findViewById(R.id.search_view_search);

        ReSear = root.findViewById(R.id.Search_Search);
        ReSear.setHasFixedSize(true);
        ReSear.setItemViewCacheSize(20);
        ReSear.setDrawingCacheEnabled(true);
        ReSear.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSer = new LinearLayoutManager(getActivity());
        ReSear.setLayoutManager(layoutManagerSer);

        TakeUser();

        Prevalant.SuspendUser = new ArrayList<>();

        Paper.book().write(Prevalant.FAD, "GalleryA");
        Paper.book().write(Prevalant.CheckH, "A");

        Check = Paper.book().read(Prevalant.CheckAdmin);

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
                    ShowPP(Vari);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ShowPP(String vari) {

        refgty = FirebaseDatabase.getInstance().getReference().child("ReProduct");

        if(!refgty.equals(null)) {

            refgty.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        FList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        SearchAdapter adapter = new SearchAdapter(getActivity(), PList, "S", vari);
                        ReSear.setAdapter(adapter);

                        searchViewA.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchViewA.setIconified(false);
                            }
                        });

                        if (!searchViewA.equals(null)) {

                            searchViewA.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    searchIt(newText, vari);
                                    return false;
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        TakeUser();

    }

    private void searchIt(String newText, String vari) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : PList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        SearchAdapter adapter = new SearchAdapter(getActivity(), Newlist, "S", vari);
        ReSear.setAdapter(adapter);
    }

}