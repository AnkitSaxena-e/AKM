package com.example.akmretail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akmretail.Prevalant.Prevalant;

import io.paperdb.Paper;

public class OrderPlacedDocActivity extends AppCompatActivity {

    private String ON;
    private TextView or, TH;
    private ImageView TTHH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed_doc);

        TH = findViewById(R.id.ssssssssAA);
        TTHH = findViewById(R.id.contiImageAA);

        Paper.book().write(Prevalant.CheckH, "A");
        TH.setOnClickListener(v -> {

            Intent i = new Intent(OrderPlacedDocActivity.this, HomeActivity.class);
            Paper.book().write(Prevalant.FAD, "HomeA");
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });

        TTHH.setOnClickListener(v -> {

            Intent i = new Intent(OrderPlacedDocActivity.this, HomeActivity.class);
            Paper.book().write(Prevalant.FAD, "HomeA");
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OrderPlacedDocActivity.this, HomeActivity.class);
        Paper.book().write(Prevalant.FAD, "HomeA");
        i.putExtra("eeee", "HA");
        startActivity(i);
    }
}