package com.example.whiteshopingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Addproducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Addproducts extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private StorageReference mStorageRef;
    ImageView prdtimsel;
    Button prdtadbt;
    EditText price,offp,delivery,stock,offper;
    AutoCompleteTextView quant,cat;
    String deliveryfee;
    Uri downloadUrl;
    FirebaseFirestore db;
    public  String [] quntity = new String[]{"KG","GM","LTR"};
    public  String [] category = new String[]{"KG","GM","LTR"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Addproducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Addproducts.
     */
    // TODO: Rename and change types and number of parameters
    public static Addproducts newInstance(String param1, String param2) {
        Addproducts fragment = new Addproducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
                RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            Glide.with(this)
                    .load(filepath)
                    .into(prdtimsel);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_addproducts, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        prdtimsel = v.findViewById(R.id.productimselecter);
        price = v.findViewById(R.id.mrp);
        offp = v.findViewById(R.id.offerpr);
        quant = v.findViewById(R.id.qun);
        delivery =v.findViewById(R.id.dvch);
        stock = v.findViewById(R.id.stocks);
        offper = v.findViewById(R.id.offper);
        cat = v.findViewById(R.id.catg);
        prdtadbt = v.findViewById(R.id.upload);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,quntity);
        quant.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,category);
        cat.setAdapter(adapter);
        quant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quant.showDropDown();
            }
        });
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat.showDropDown();
            }
        });
        prdtimsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        prdtadbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String random = UUID.randomUUID().toString();
                final StorageReference riversRef = mStorageRef.child("Productimages/" +random);
                riversRef.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                 riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                     @Override
                                     public void onSuccess(Uri uri) {
                                        downloadUrl = uri;
                                        String downloadUrls = downloadUrl.toString();
                                        String accesstoken = random;
                                        if (delivery.getText().toString().trim().isEmpty()){
                                           deliveryfee = "Free";
                                        }
                                        Modalclass upload = new Modalclass(downloadUrls,price.getText().toString().trim(),offp.getText().toString().trim(),quant.getText().toString().trim(),
                                                stock.getText().toString().trim(),offper.getText().toString().trim(),deliveryfee,accesstoken,cat.getText().toString().trim());
                                        db.collection("products").document(random).set(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Uploading Successfull", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(), "Uploading Failed", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                     }
                                 });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return v;
    }
}