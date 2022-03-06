package com.example.akm_admin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.akm_admin.ADActivity;
import com.example.akm_admin.AdminAddCatActivity;
import com.example.akm_admin.AdminFHShowActivity;
import com.example.akm_admin.AdminSeeProductPhoneActivity;
import com.example.akm_admin.CreateNewBillActivity;
import com.example.akm_admin.LoginActivity;
import com.example.akm_admin.Prevalant.Prevalant;
import com.example.akm_admin.R;
import com.example.akm_admin.RetailActivity;

import io.paperdb.Paper;

public class AdminHomeFragment extends Fragment {

    private Button CreateBill, AddNPhone, ManagePhones, ManageBanners, ManageFPhones, LogoutA, Retail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        Paper.init(getActivity());

        AddNPhone = root.findViewById(R.id.AdminAddNewPhone);
        ManagePhones = root.findViewById(R.id.AdminManagePhones);
        ManageBanners = root.findViewById(R.id.AdminManageBanner);
        ManageFPhones = root.findViewById(R.id.AdminManageFPhones);
        CreateBill = root.findViewById(R.id.AdminNewB);
        Retail = root.findViewById(R.id.AdminRetailB);
        LogoutA = root.findViewById(R.id.AdminManageLogout);

        AddNPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminAddCatActivity.class);
                startActivity(i);
            }
        });

        ManagePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminSeeProductPhoneActivity.class);
                Paper.book().write(Prevalant.CheckAdmin, "Admin");
                startActivity(i);
            }
        });

        ManageBanners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ADActivity.class);
                startActivity(i);
            }
        });

        ManageFPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminFHShowActivity.class);
                startActivity(i);
            }
        });

        CreateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateNewBillActivity.class);
                startActivity(i);
            }
        });

        Retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RetailActivity.class);
                startActivity(i);
            }
        });

        LogoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

}
