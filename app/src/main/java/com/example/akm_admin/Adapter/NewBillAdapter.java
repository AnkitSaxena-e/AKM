package com.example.akm_admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.CreateNewBillActivity;
import com.example.akm_admin.R;

import java.util.ArrayList;

public class NewBillAdapter extends RecyclerView.Adapter<NewBillAdapter.Viewholder>{

    Context context;
    ArrayList<ArrayList<String>> New;

    public NewBillAdapter(CreateNewBillActivity newBillActivity, ArrayList<ArrayList<String>> appPro) {

        context = newBillActivity;
        New = appPro;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_recycler, parent, false);
        return new Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        String N = New.get(position).get(0);
        String Q = New.get(position).get(1);
        String P = New.get(position).get(2);

        int QQ = Integer.parseInt(Q);
        int PP = Integer.parseInt(P);

        int Price = QQ * PP;

        holder.No.setText(String.valueOf(position + 1) + ".");
        holder.PName.setText(N);
        holder.Qut.setText(Q);
        holder.MRP.setText(String.valueOf(Price));

    }

    @Override
    public int getItemCount() {
        return New.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView No, PName, Qut, MRP;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            No = itemView.findViewById(R.id.TCusNo);
            PName = itemView.findViewById(R.id.TCusPNa);
            Qut = itemView.findViewById(R.id.TCusPQu);
            MRP = itemView.findViewById(R.id.TCusPMRP);

        }
    }
}
