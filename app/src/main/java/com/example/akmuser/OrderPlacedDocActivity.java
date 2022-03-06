package com.example.akmuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akmuser.Prevalant.Prevalant;

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

        TH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OrderPlacedDocActivity.this, HomeActivity.class);
                Paper.book().write(Prevalant.FAD, "HomeA");
                i.putExtra("eeee", "OPA");
                startActivity(i);

            }
        });

        TTHH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(OrderPlacedDocActivity.this, HomeActivity.class);
                Paper.book().write(Prevalant.FAD, "HomeA");
                i.putExtra("eeee", "OPA");
                startActivity(i);

            }
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