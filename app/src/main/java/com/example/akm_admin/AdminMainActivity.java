package com.example.akm_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.akm_admin.Fragments.AdminHomeFragment;
import com.example.akm_admin.Fragments.AdminSeeUsersFragment;
import com.example.akm_admin.Fragments.AdminSettingFragment;
import com.example.akm_admin.Prevalant.Prevalant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import io.paperdb.Paper;

public class AdminMainActivity extends AppCompatActivity {

    private boolean doubleclickk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Paper.init(AdminMainActivity.this);

        BottomNavigationView navView = findViewById(R.id.nav_vieww);

        navView.setOnNavigationItemSelectedListener(onNavi);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, new AdminHomeFragment()).commit();

        Window window = AdminMainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#303F9F"));

        updateToken();

    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavi = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;

            if (menuItem.getItemId() == R.id.navigation_home_admin) {
                fragment = new AdminHomeFragment();
            } else if (menuItem.getItemId() == R.id.navigation_dashboard_admin) {
                fragment = new AdminSeeUsersFragment();
            } else if (menuItem.getItemId() == R.id.navigation_notifications_admin) {
                fragment = new AdminSettingFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment, fragment).commit();

            return true;
        }
    };

    private void updateToken() {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();

        DatabaseReference rtgv = FirebaseDatabase.getInstance().getReference().child("AdminTokens").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Token", refreshToken);
        rtgv.updateChildren(hashMap);

    }

    @Override
    public void onBackPressed() {

        if (doubleclickk) {
            Intent hi = new Intent(Intent.ACTION_MAIN);
            hi.addCategory(Intent.CATEGORY_HOME);
            hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(hi);
            return;
        }

        this.doubleclickk = true;
        Toast.makeText(AdminMainActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleclickk = false;
            }
        }, 2000);
    }
}