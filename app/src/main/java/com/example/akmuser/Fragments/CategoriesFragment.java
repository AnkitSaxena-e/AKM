package com.example.akmuser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.akmuser.CategoryActivity;
import com.example.akmuser.Prevalant.Prevalant;
import com.example.akmuser.R;

import io.paperdb.Paper;

public class CategoriesFragment extends Fragment {

    private Button Ven, IVS, FoC, NeM, OxM, TWC, Cot, RyT, ETT, UrB, FeT, SuT, Otr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        Ven = root.findViewById(R.id.Admin_Venflon_AAAA);
        IVS = root.findViewById(R.id.Admin_Iv_Set_AAAA);
        FoC = root.findViewById(R.id.Admin_Foley_Catheter_AAAA);
        NeM = root.findViewById(R.id.Admin_Nebulization_Mask_AAAA);
        OxM = root.findViewById(R.id.Admin_Oxygen_Mask_AAAA);
        TWC = root.findViewById(R.id.Admin_Way_Connector_AAAA);
        Cot = root.findViewById(R.id.Admin_Cotton_AAAA);
        RyT = root.findViewById(R.id.Admin_Ryles_Tube_AAAA);
        ETT = root.findViewById(R.id.Admin_ET_Tube_AAAA);
        UrB = root.findViewById(R.id.Admin_Uro_Bag_AAAA);
        FeT = root.findViewById(R.id.Admin_Feeding_Tube_AAAA);
        SuT = root.findViewById(R.id.Admin_Suction_Tube_AAAA);
        Otr = root.findViewById(R.id.Admin_Other_AAAA);

        Paper.book().write(Prevalant.FAD, "CategoryA");

        Ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "Venflon");
                startActivity(i);

            }
        });

        IVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "IVSet");
                startActivity(i);

            }
        });

        FoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "FoleyCatheter");
                startActivity(i);

            }
        });

        NeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "NebulizationMask");
                startActivity(i);

            }
        });

        OxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "OxygenMask");
                startActivity(i);

            }
        });

        TWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "3WayConnector");
                startActivity(i);

            }
        });

        Cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("", "Cotton");
                startActivity(i);

            }
        });

        RyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "RylesTube");
                startActivity(i);

            }
        });

        ETT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "ETTube");
                startActivity(i);

            }
        });

        UrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "UroBag");
                startActivity(i);

            }
        });

        FeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "FeedingTube");
                startActivity(i);

            }
        });

        SuT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "SuctionTube");
                startActivity(i);

            }
        });

        Otr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("loik", "Other");
                startActivity(i);

            }
        });

        return root;
    }
}
