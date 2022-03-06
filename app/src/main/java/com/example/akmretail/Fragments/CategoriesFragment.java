package com.example.akmretail.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.akmretail.CategoryActivity;
import com.example.akmretail.Prevalant.Prevalant;
import com.example.akmretail.R;

import io.paperdb.Paper;

public class CategoriesFragment extends Fragment {

    private Button Ven, IVS, FoC, OxM, Cot, RyT, UrB, FeT, BTS, MdS, Nki, Vtr, CBg, Vol, Otr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        Ven = root.findViewById(R.id.Admin_Venflon_AAAA);
        IVS = root.findViewById(R.id.Admin_Iv_Set_AAAA);
        FoC = root.findViewById(R.id.Admin_Foley_Catheter_AAAA);
        OxM = root.findViewById(R.id.Admin_Oxygen_Mask_AAAA);
        Cot = root.findViewById(R.id.Admin_Cotton_AAAA);
        RyT = root.findViewById(R.id.Admin_Ryles_Tube_AAAA);
        UrB = root.findViewById(R.id.Admin_Uro_Bag_AAAA);
        FeT = root.findViewById(R.id.Admin_Feeding_Tube_AAAA);
        Otr = root.findViewById(R.id.Admin_Other_AAAA);
        BTS = root.findViewById(R.id.Admin_BT_Set_AAAA);
        MdS = root.findViewById(R.id.Admin_Micro_Drip_Set_AAAA);
        Nki = root.findViewById(R.id.Admin_Nebulizer_Kit_AAAA);
        Vtr = root.findViewById(R.id.Admin_Ventilator_AAAA);
        CBg = root.findViewById(R.id.Admin_Crabe_Bandage_AAAA);
        Vol = root.findViewById(R.id.Admin_Veion_o_line_AAAA);

        Paper.book().write(Prevalant.FAD, "CategoryA");
        Paper.book().write(Prevalant.CheckH, "A");

        Ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Venflon");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        IVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "IV Set");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        FoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Foley Catheter");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        OxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Oxygen Face Mask");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        Cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Cotton");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        RyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Ryles Tube");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        UrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Uro Bag");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        FeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Feeding Tube");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        BTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "BT Set");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        MdS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Micro Drip Set");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        Nki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Nebulization Kit");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        Vtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Ventilator Tube");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        CBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Crabe Bandage");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        Vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Veion-o-line");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        Otr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Other");
                Paper.book().write(Prevalant.CheckH, "CA");

            }
        });

        return root;
    }
}
