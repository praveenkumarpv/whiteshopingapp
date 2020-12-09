package com.example.whiteshopingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.whiteshopingapp.MainActivity.log;

public class mainscreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExitPressedOnce = false;
    int count = 0;
    private static final int REQUEST_PHONE_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        getSupportActionBar().hide();
        if (ContextCompat.checkSelfPermission(mainscreen.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainscreen.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            //startActivity(intent);
        }

        fragment = new home();
        fragmenttransation(fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case R.id.home:
                        fragment = new home();
                        fragmenttransation(fragment);
                        break;
                    case R.id.offer:
                        fragment = new offer();
                        fragmenttransation(fragment);
                        break;
                    case R.id.profile:
                        fragment = new Ordersview();
                        fragmenttransation(fragment);
                        break;
                    case R.id.logout:
                        count ++;
                        if (count == 1){
                            Toast.makeText(mainscreen.this, "Press again to Logout", Toast.LENGTH_SHORT).show();
                        }
                        else if (count == 2){
                            SharedPreferences settings = getSharedPreferences(log, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("not", false);
                            editor.commit();
                            mainscreen.this.finish();
                        }
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                count--;
                            }
                        }, 2000);
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
        Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}