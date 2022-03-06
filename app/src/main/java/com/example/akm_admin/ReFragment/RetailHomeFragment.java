package com.example.akm_admin.ReFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.akm_admin.CreateNewBillActivity;
import com.example.akm_admin.LoginActivity;
import com.example.akm_admin.Prevalant.Prevalant;
import com.example.akm_admin.R;
import com.example.akm_admin.ReFHShowActivity;
import com.example.akm_admin.RetailAddPhoneActivity;
import com.example.akm_admin.RetailBannerActivity;
import com.example.akm_admin.RetailSeeMaintProActivity;

import io.paperdb.Paper;

public class RetailHomeFragment extends Fragment {

    private Button CreateBill, AddNPhone, ManagePhones, ManageBanners, ManageFPhones, LogoutA;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_retail, container, false);

        Paper.init(getActivity());

        AddNPhone = root.findViewById(R.id.AdminAddNewPhone_Retail);
        ManagePhones = root.findViewById(R.id.AdminManagePhones_Retail);
        ManageBanners = root.findViewById(R.id.AdminManageBanner_Retail);
        ManageFPhones = root.findViewById(R.id.AdminManageFPhones_Retail);
        CreateBill = root.findViewById(R.id.AdminNewB_Retail);
        LogoutA = root.findViewById(R.id.AdminManageLogout_Retail);

        AddNPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RetailAddPhoneActivity.class);
                startActivity(i);
            }
        });

        ManagePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RetailSeeMaintProActivity.class);
                Paper.book().write(Prevalant.CheckAdmin, "Admin");
                startActivity(i);
            }
        });

        ManageBanners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RetailBannerActivity.class);
                startActivity(i);
            }
        });

        ManageFPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReFHShowActivity.class);
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
