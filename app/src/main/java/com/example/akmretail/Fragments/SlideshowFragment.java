package com.example.akmretail.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.akmretail.AboutActivity;
import com.example.akmretail.ChangePasswardUserActivity;
import com.example.akmretail.HomeActivity;
import com.example.akmretail.LoginActivity;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;
import com.example.akmretail.UserCostomerSupportActivity;

import io.paperdb.Paper;

public class SlideshowFragment extends Fragment {


    private String Name, type;
    private TextView yourOrder, logOut, seeAccount, chngePassward, aboutS, costomerSupport;
    private int shortAnimationDuration;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Paper.init(getActivity());
        logOut = root.findViewById(R.id.Logout_Setting_first);
        seeAccount = root.findViewById(R.id.yourAccount);
        chngePassward = root.findViewById(R.id.changePasswardL);
        aboutS = root.findViewById(R.id.aboutL);
        costomerSupport = root.findViewById(R.id.costomerSupport);

        type = Paper.book().read(Prevalant.CheckAdmin).toString();
        Paper.book().write(Prevalant.CheckH, "A");

        Paper.book().write(Prevalant.FAD, "SettingA");

        seeAccount.setOnClickListener(v -> {
            Paper.book().write(Prevalant.CheckH, "SS");
            Paper.book().write(Prevalant.FAD, "ProfileA");
            Intent i = new Intent(getActivity(), HomeActivity.class);
            i.putExtra("eeee", "PA");
            startActivity(i);
        });

        chngePassward.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ChangePasswardUserActivity.class);
            startActivity(i);
        });

        costomerSupport.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserCostomerSupportActivity.class);
            startActivity(i);
        });

        aboutS.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AboutActivity.class);
            startActivity(i);
        });

        logOut.setOnClickListener(v -> {
            Paper.book().destroy();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        return root;
    }
}