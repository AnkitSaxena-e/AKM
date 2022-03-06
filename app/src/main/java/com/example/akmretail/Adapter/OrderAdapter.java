package com.example.akmretail.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akmretail.Modal.OPro;
import com.example.akmretail.Modal.Order;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;
import com.example.akmretail.SeeBuyingActivity;
import com.example.akmretail.View_Holder.catagorySearchVewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VHolder> {

    Context c;
    ArrayList<Order> M;

    public OrderAdapter(Context con, ArrayList<Order> categoryModa) {

        c = con;
        M = categoryModa;

    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_iterm_buying_layout, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder Holder, int position) {

        RecyclerView.LayoutManager layou;

        String PPNNOO = M.get(position).getPNO();
        String BB = M.get(position).getBuy();

        Holder.ctName.setText(M.get(position).getName());
        Holder.ctNumber.setText(M.get(position).getPhoneNumber());
        Holder.ctAddress.setText(M.get(position).getAddress());

        Holder.OORREE.setHasFixedSize(true);
        Holder.OORREE.setItemViewCacheSize(20);
        Holder.OORREE.setDrawingCacheEnabled(true);
        Holder.OORREE.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layou = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        Holder.OORREE.setLayoutManager(layou);

        Holder.ctSeeIt.setOnClickListener(v -> {

            Intent o = new Intent(c, SeeBuyingActivity.class);
            o.putExtra("PPIdd", M.get(position).getPID());
            c.startActivity(o);

        });


        DatabaseReference ORef = FirebaseDatabase.getInstance().getReference().child("OrderList")
                .child(Paper.book().read(Prevalant.UserIdA)).child(BB).child(PPNNOO);

        FirebaseRecyclerOptions<OPro> OrOption =
                new FirebaseRecyclerOptions.Builder<OPro>()
                        .setQuery(ORef, OPro.class)
                        .build();

        FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder> OrAdapter =
                new FirebaseRecyclerAdapter<OPro, catagorySearchVewHolder>(OrOption) {
                    @Override
                    protected void onBindViewHolder(@NonNull catagorySearchVewHolder Holder, int i, @NonNull OPro Layout) {

                        String V = Layout.getPVar();
                        String N = Layout.getPNum();

                        if(!V.equals("AA")){
                            Holder.tltProductColor.setText(Layout.getPVar());
                        }else if(!N.equals("AA")){
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
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.o_re_f, parent, false);
                        return new catagorySearchVewHolder(view);
                    }
                };

        Holder.OORREE.setAdapter(OrAdapter);
        OrAdapter.startListening();


    }

    @Override
    public int getItemCount() {
        return M.size();
    }

    static class VHolder extends RecyclerView.ViewHolder{

        public TextView ctName, ctNumber, ctAddress;
        public TextView ctSeeIt;
        public RecyclerView OORREE;

        public VHolder(@NonNull View itemview) {
            super(itemview);

            ctName = itemView.findViewById(R.id.Product_Name_PP_SS);
            ctAddress = itemView.findViewById(R.id.Address_BB_SS);
            ctNumber = itemView.findViewById(R.id.setDasiReading_PS_PP_SS);

            OORREE = itemView.findViewById(R.id.ORe);
            ctSeeIt = itemView.findViewById(R.id.Edit_PP_SS);

        }
    }
}
