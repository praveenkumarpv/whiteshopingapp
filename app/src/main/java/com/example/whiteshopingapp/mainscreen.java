package com.example.whiteshopingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainscreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        fragment = new home();
        fragmenttransation(fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case R.id.home:
                        Toast.makeText(mainscreen.this, "home", Toast.LENGTH_SHORT).show();
                        fragment = new home();
                        fragmenttransation(fragment);
                        break;
                    case R.id.offer:
                        Toast.makeText(mainscreen.this, "offer", Toast.LENGTH_SHORT).show();
                        fragment = new offer();
                        fragmenttransation(fragment);
                        break;
                    case R.id.profile:
                        Toast.makeText(mainscreen.this, "profile", Toast.LENGTH_SHORT).show();
                        fragment = new profile();
                        fragmenttransation(fragment);
                        break;
                }

                return true;
            }



        });
    }
    private void fragmenttransation(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce =true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}