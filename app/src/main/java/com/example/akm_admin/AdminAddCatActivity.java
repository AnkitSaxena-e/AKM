package com.example.akm_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminAddCatActivity extends AppCompatActivity {

    private Button Ven, IVS, FoC, OxM, Cot, RyT, UrB, FeT, BTS, MdS, Nki, Vtr, CBg, Vol, Otr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cat);

        Ven = findViewById(R.id.Admin_Venflon);
        IVS = findViewById(R.id.Admin_Iv_Set);
        FoC = findViewById(R.id.Admin_Foley_Catheter);
        OxM = findViewById(R.id.Admin_Oxygen_Mask);
        Cot = findViewById(R.id.Admin_Cotton);
        RyT = findViewById(R.id.Admin_Ryles_Tube);
        UrB = findViewById(R.id.Admin_Uro_Bag);
        FeT = findViewById(R.id.Admin_Feeding_Tube);
        Otr = findViewById(R.id.Admin_Other);
        BTS = findViewById(R.id.Admin_BT_Set);
        MdS = findViewById(R.id.Admin_Micro_Drip_Set);
        Nki = findViewById(R.id.Admin_Nebulizer_Kit);
        Vtr = findViewById(R.id.Admin_Ventilator);
        CBg = findViewById(R.id.Admin_Crabe_Bandage);
        Vol = findViewById(R.id.Admin_Veion_o_line);

        Ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Venflon");
                startActivity(i);

            }
        });

        IVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "IV Set");
                startActivity(i);

            }
        });

        FoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Foley Catheter");
                startActivity(i);

            }
        });

        OxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Oxygen Face Mask");
                startActivity(i);

            }
        });

        Cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Cotton");
                startActivity(i);

            }
        });

        RyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Ryles Tube");
                startActivity(i);

            }
        });

        UrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Uro Bag");
                startActivity(i);

            }
        });

        FeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Feeding Tube");
                startActivity(i);

            }
        });

        BTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "BT Set");
                startActivity(i);

            }
        });

        MdS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Micro Drip Set");
                startActivity(i);

            }
        });

        Nki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Nebulization Kit");
                startActivity(i);

            }
        });

        Vtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Ventilator Tube");
                startActivity(i);

            }
        });

        CBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Crabe Bandage");
                startActivity(i);

            }
        });

        Vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Veion-o-line");
                startActivity(i);

            }
        });

        Otr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Other");
                startActivity(i);

            }
        });

    }
}