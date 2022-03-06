package com.example.akm_admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akm_admin.AdminUserInfoDetailActivity;
import com.example.akm_admin.Modal.Users;
import com.example.akm_admin.R;

import java.util.ArrayList;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.Viewholder> {

    private ArrayList<Users> aUserModal;
    private Context context;

    public AdminUserAdapter(Context context, ArrayList<Users> aUserModal) {
        this.aUserModal = aUserModal;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testt, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.AName.setText(aUserModal.get(position).getName());
        holder.AUserJ.setText(aUserModal.get(position).getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdminUserInfoDetailActivity.class);
                i.putExtra("Info", aUserModal.get(position).getNumber());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return aUserModal.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public TextView AUserJ, AName, ASName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            AUserJ = itemView.findViewById(R.id.num_user_OC);
            AName = itemView.findViewById(R.id.name_user_OC);

        }
    }


}
