package com.example.whiteshopingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class address extends orderdetails {
    TextView na,ho,pho1,pho2,pins,ro,la;
    ImageView ca1,ca2;
    Activity activity;
    String p1,p2;
    AlertDialog alertDialog;
    private FirebaseFirestore db;

    public address(Activity activity) {
        this.activity = activity;
    }
    void showaddres(String nameget){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =inflater.inflate(R.layout.address,null);
        db = FirebaseFirestore.getInstance();
        builder.setView(view);
        builder.setCancelable(true);
        na = view.findViewById(R.id.name);
        ho = view.findViewById(R.id.house);
        pho1 = view.findViewById(R.id.phonenum1);
        pho2 = view.findViewById(R.id.phonenum2);
        ca1 = view.findViewById(R.id.call1);
        ca2 = view.findViewById(R.id.call2);
        pins = view.findViewById(R.id.pin);
        ro = view.findViewById(R.id.road);
        la = view.findViewById(R.id.landmark);
        DocumentReference doc = db.collection("user_data").document(nameget).collection("Address").document("Address1");
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    na.setText("Name:"+documentSnapshot.getString("name"));
                    ho.setText("House:"+documentSnapshot.getString("House"));
                    pho1.setText("Phone1:"+documentSnapshot.getString("Phone"));
                    p1 = documentSnapshot.getString("Phone");
                    p2 = documentSnapshot.getString("Phone2");
                    pho2.setText("Phone2:"+documentSnapshot.getString("Phone2"));
                    Long p = documentSnapshot.getLong("Pin");
                    String pi = Long.toString(p);
                    pins.setText("Pin:"+pi);
                    la.setText("Landmark:"+documentSnapshot.getString("Landmark"));
                    ro.setText("Road:"+documentSnapshot.getString("Road"));
                }

            }
        });
        ca1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("8129413361"));
                activity.startActivity(call);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }
}
