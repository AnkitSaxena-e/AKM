package com.example.akmuser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmuser.Adapter.OrderAdapter;
import com.example.akmuser.Modal.OPro;
import com.example.akmuser.Modal.Order;
import com.example.akmuser.Modal.Product;
import com.example.akmuser.Prevalant.OrderPrevalent;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.R;
import com.example.akmuser.SeeBuyingActivity;
import com.example.akmuser.View_Holder.OrderViewHolder;
import com.example.akmuser.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class OrderFragment extends Fragment {

    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;

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

        Paper.book().write(Prevalant.FAD, "OrderA");

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("Order");

        FirebaseRecyclerOptions<Order> OrOption =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(OrREF, Order.class)
                        .build();

        FirebaseRecyclerAdapter<Order, OrderViewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<Order, OrderViewHolder>(OrOption) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder Holder, int i, @NonNull Order Layout) {

                        Holder.ctName.setText(Layout.getName());
                        Holder.ctNumber.setText(Layout.getPhoneNumber());
                        Holder.ctAddress.setText(Layout.getAddress());

                        Holder.ctSeeIt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent o = new Intent(getActivity(), SeeBuyingActivity.class);
                                o.putExtra("PPIdd", Layout.getPID());
                                startActivity(o);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_buying_layout, parent, false);
                        OrderViewHolder holder = new OrderViewHolder(view);
                        return holder;
                    }
                };

        R_PhoneS.setAdapter(OrAdapter);
        OrAdapter.startListening();

    }

}
