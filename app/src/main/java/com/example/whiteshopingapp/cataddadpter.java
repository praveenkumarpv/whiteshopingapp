package com.example.whiteshopingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;

public class cataddadpter extends Addproducts {
    private Uri filepath;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    EditText addcat;
    Button add;
    ImageView catimage;
    Activity activity;
    AlertDialog alertDialog;
    private static final int PICK_IMAGE_REQUEST = 1;
    public cataddadpter(Activity activity) {
        this.activity = activity;
    }
    void startadding(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view =inflater.inflate(R.layout.addcatogery,null);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        catimage = view.findViewById(R.id.catimage);
        addcat = view.findViewById(R.id.catname);
        add = view.findViewById(R.id.addcat);
        catimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activity.startActivityForResult(intent,PICK_IMAGE_REQUEST);
                filepath = intent.getData();
                Glide.with(activity).load(filepath).into(catimage);



            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "thanks to click", Toast.LENGTH_SHORT).show();
                cataddmodal upload = new cataddmodal(addcat.getText().toString().trim());
               exitadding();
            }
        });

        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }

    void exitadding(){
        alertDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
                RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            Glide.with(activity).load(filepath).into(catimage);

    }
}
}



