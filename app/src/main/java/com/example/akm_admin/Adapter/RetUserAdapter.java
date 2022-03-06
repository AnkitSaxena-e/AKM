package com.example.akm_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.Modal.Users;
import com.example.akm_admin.Prevalant.Prevalant;
import com.example.akm_admin.R;
import com.example.akm_admin.RetSeeUserDetailActivity;

import java.util.ArrayList;

import io.paperdb.Paper;

public class RetUserAdapter extends RecyclerView.Adapter<RetUserAdapter.ViewHol> {

    private ArrayList<Users> aa;
    private Context context;

    public RetUserAdapter(FragmentActivity activity, ArrayList<Users> PListAA) {

        context = activity;
        aa = PListAA;

    }

    @NonNull
    @Override
    public RetUserAdapter.ViewHol onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.testt, parent, false);
        RetUserAdapter.ViewHol viewHol = new RetUserAdapter.ViewHol(v);
        return viewHol;
    }

    @Override
    public void onBindViewHolder(@NonNull RetUserAdapter.ViewHol holder, int position) {

        holder.AUName.setText(aa.get(position).getName());
        holder.AUNumber.setText(aa.get(position).getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, RetSeeUserDetailActivity.class);
                Paper.book().write(Prevalant.ACAdmin, aa.get(position).getNumber());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return aa.size();
    }

    public class ViewHol extends RecyclerView.ViewHolder {

        public TextView AUName, AUNumber;

        public ViewHol(@NonNull View itemView) {
            super(itemView);

            AUName = itemView.findViewById(R.id.name_user_OC);
            AUNumber = itemView.findViewById(R.id.num_user_OC);

        }

    }

}