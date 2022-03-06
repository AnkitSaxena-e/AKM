package com.example.akm_admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.Modal.OPro;
import com.example.akm_admin.R;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.Viewholder>{

    private Context context;
    private ArrayList<OPro> Bill;

    public BillAdapter(Context c, ArrayList<OPro> B) {

        context = c;
        Bill = B;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_recycler, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        int P = position;

        int QQ = Integer.parseInt(Bill.get(position).getPQut().toString());
        int SS = Integer.parseInt(Bill.get(position).getPPri().toString());

        int TP = QQ * SS;
        holder.No.setText(String.valueOf(P + 1) + ".");
        holder.PName.setText(Bill.get(position).getPName().toString());
        holder.Qut.setText(Bill.get(position).getPQut().toString());
        holder.MRP.setText("₹" + String.valueOf(TP));

    }

    @Override
    public int getItemCount() {
        return Bill.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

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
