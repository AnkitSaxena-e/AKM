package com.example.akmretail.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmretail.Adapter.OrderAdapter;
import com.example.akmretail.Modal.Order;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class OrderFragment extends Fragment {

    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private ArrayList<Order> PList, QList, RList, FFList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        Paper.init(getActivity());

        R_PhoneS = root.findViewById(R.id.Order_Searchoo);
        R_PhoneS.setHasFixedSize(true);
        R_PhoneS.setItemViewCacheSize(20);
        R_PhoneS.setDrawingCacheEnabled(true);
        R_PhoneS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(getActivity());
        R_PhoneS.setLayoutManager(layoutManagerSS);

        OrderLe();

        Paper.book().write(Prevalant.FAD, "OrderA");

        Paper.book().write(Prevalant.CheckH, "A");
        return root;
    }

    private void OrderLe() {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("ReOrder");

        OrREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    PList = new ArrayList<>();
                    QList = new ArrayList<>();
                    RList = new ArrayList<>();
                    FFList = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        PList.add(ss.getValue(Order.class));
                    }

                    for (int g = 0; g < PList.size(); g++) {

                        String t = Paper.book().read(Prevalant.UserIdA);
                        String h = PList.get(g).getByUser();

                        if (h.equals(t)) {
                            QList.add(PList.get(g));
                        }

                    }

                    for(int o = QList.size() - 1; o >= 0; o--){

                        RList.add(QList.get(o));

                    }

                    OrderAdapter orderAdapter = new OrderAdapter(getActivity(), RList);
                    R_PhoneS.setAdapter(orderAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
