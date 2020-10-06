package com.example.whiteshopingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private TextView white;
    private EditText email,password;
    private String  emails,passwords, e ="whiteadminlog", pass= "adminpass123";
    private FirebaseAuth mAuth;
    public static final String log = "not" ;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.loginbutton);
        white = findViewById(R.id.white);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emails = email.getText().toString().trim();
                passwords = password.getText().toString().trim();
                if (emails.isEmpty() || !emails.equals(e)) {
                    email.setError("Valid Emailid Please");
                    email.requestFocus();
                } else if (passwords.isEmpty() || !passwords.equals(pass)) {
                    password.setError("Valid Password Please");
                    password.requestFocus();
                } else if (emails.isEmpty() && passwords.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Valid Admin details Please", Toast.LENGTH_SHORT).show();
                } else if (emails.equals(e) && passwords.equals(pass)) {
                    Intent i = new Intent(MainActivity.this, mainscreen.class);
                    startActivity(i);
                    SharedPreferences settings = getSharedPreferences(log, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("not", true);
                    editor.commit();
                    MainActivity.this.finish();

                }
            }
        });
        SharedPreferences settins = getSharedPreferences(log, 0);
        boolean haslogin = settins.getBoolean("not", false);
        if (haslogin) {
            Intent i = new Intent(MainActivity.this, mainscreen.class);
            startActivity(i);
            MainActivity.this.finish();
        }
    }

}