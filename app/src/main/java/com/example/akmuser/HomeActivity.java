package com.example.akmuser;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akmuser.Fragments.CartFragment;
import com.example.akmuser.Fragments.CategoriesFragment;
import com.example.akmuser.Fragments.GalleryFragment;
import com.example.akmuser.Fragments.HomeFragment;
import com.example.akmuser.Fragments.OrderFragment;
import com.example.akmuser.Fragments.ProductDataFragment;
import com.example.akmuser.Fragments.SlideshowFragment;
import com.example.akmuser.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private String ee;
    private NavigationView navigationView;
    private boolean doubleclick = false, yy = false;
    private Fragment fragment;
    private ImageView Cart;
    private Toolbar toolbar;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ee = getIntent().getStringExtra("eeee");

        Paper.init(HomeActivity.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cart = findViewById(R.id.See_C);
        toolbar.setTitle("Home");

        Window window = HomeActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#22BFC1"));

        fragment = new HomeFragment();

        Paper.book().write(Prevalant.FAD, "HomeA");

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        try {
            getSupportActionBar().setElevation(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {

            View headerview = navigationView.getHeaderView(0);
            TextView usernameTextView = headerview.findViewById(R.id.textview_navnav);
            CircleImageView profileImageView = headerview.findViewById(R.id.imageView_navnav);
            String Name = Paper.book().read(Prevalant.UserNameA);
            Picasso.get().load(Paper.book().read(Prevalant.UserImageA).toString()).placeholder(R.drawable.profile).into(profileImageView);
            usernameTextView.setText(Name);

            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(i);

                }
            });

            usernameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(i);

                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle("Cart");
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CartFragment()).commit();
            }
        });
        if (ee.equals("SeA")) {
            toolbar.setTitle("Search");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new GalleryFragment()).commit();
        } else if (ee.equals("SA")) {
            toolbar.setTitle("Setting");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new SlideshowFragment()).commit();
        } else if (ee.equals("OA")) {
            toolbar.setTitle("Order");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new OrderFragment()).commit();
        } else if (ee.equals("CA")) {
            toolbar.setTitle("Category");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CategoriesFragment()).commit();
        } else if (ee.equals("PDA")) {
            toolbar.setTitle("Product");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProductDataFragment()).commit();
        } else if (ee.equals("CaA")) {
            toolbar.setTitle("Cart");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CartFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new HomeFragment()).commit();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        updateToken();

    }

    private void updateToken() {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();

        DatabaseReference rtgv = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Token", refreshToken);
        rtgv.updateChildren(hashMap);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        fragment = null;

        if (menuItem.getItemId() == R.id.nav_home) {
            fragment = new HomeFragment();
            Paper.book().write(Prevalant.FAD, "HomeA");
            toolbar.setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_gallery) {
            fragment = new GalleryFragment();
            Paper.book().write(Prevalant.FAD, "SearchA");
            toolbar.setTitle("Search");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_order) {
            fragment = new OrderFragment();
            toolbar.setTitle("Order");
            Paper.book().write(Prevalant.FAD, "OrderA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_categories) {
            fragment = new CategoriesFragment();
            toolbar.setTitle("Category");
            Paper.book().write(Prevalant.FAD, "CategoryA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_cart) {
            fragment = new CartFragment();
            toolbar.setTitle("Cart");
            Paper.book().write(Prevalant.FAD, "CartA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_slideshow) {
            fragment = new SlideshowFragment();
            toolbar.setTitle("Setting");
            Paper.book().write(Prevalant.FAD, "SettingA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_logout) {
            Paper.book().destroy();
            yy = true;
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            fragment = new HomeFragment();
            toolbar.setTitle("Home");
            Paper.book().write(Prevalant.FAD, "HomeA");
            drawer.closeDrawer(GravityCompat.START);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            String FAA = Paper.book().read(Prevalant.FAD).toString();

            if (FAA.equals("HomeA")) {
                if (doubleclick) {
                    Intent hi = new Intent(Intent.ACTION_MAIN);
                    hi.addCategory(Intent.CATEGORY_HOME);
                    hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(hi);
                    return;
                }

                this.doubleclick = true;
                Toast.makeText(HomeActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleclick = false;
                    }
                }, 2000);
            } else {
                Intent o = new Intent(HomeActivity.this, HomeActivity.class);
                o.putExtra("eeee", "HA");
                startActivity(o);
            }
        }
    }

    @Override
    protected void onPause() {
        if (drawer.isDrawerOpen(GravityCompat.START) && !yy) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onPause();
        }
    }
}