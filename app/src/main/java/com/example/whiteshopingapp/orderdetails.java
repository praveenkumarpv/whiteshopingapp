package com.example.whiteshopingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderdetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderdetails extends Fragment {
    LinearLayout accl,pacl,outl,devl;
    ImageView acc,pac,out,dev;
    CircleImageView proimage;
    TextView proname,orderid;
    RecyclerView consumedproduct;
    String nameget,orderidget,imgurlget,statusget,date;
    private FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderdetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderdetails.
     */
    // TODO: Rename and change types and number of parameters
    public static orderdetails newInstance(String param1, String param2) {
        orderdetails fragment = new orderdetails();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_orderdetails, container, false);
        consumedproduct = v.findViewById(R.id.consumedproduct);
        db = FirebaseFirestore.getInstance();
        acc = v.findViewById(R.id.accept);
        pac = v.findViewById(R.id.packed);
        out = v.findViewById(R.id.outfordelivery);
        dev = v.findViewById(R.id.delivered);
        accl = v.findViewById(R.id.acceptlayout);
        pacl = v.findViewById(R.id.packlayout);
        outl = v.findViewById(R.id.outfordeliverylayout);
        devl = v.findViewById(R.id.deliveredlayout);
        proimage = v.findViewById(R.id.userproimg);
        proname = v.findViewById(R.id.userproname);
        orderid = v.findViewById(R.id.orderid);
        nameget = getArguments().getString("name");
        imgurlget = getArguments().getString("img");
        orderidget = getArguments().getString("orderid");
        statusget = getArguments().getString("status");
        date = getArguments().getString("date");
        Glide.with(getActivity()).load(imgurlget).into(proimage);
        proname.setText(nameget);
        orderid.setText(orderidget);
        if (statusget.equals("accepted")){
            acc.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("packed")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("out for delivery")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
            out.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("delivered")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
            out.setVisibility(View.VISIBLE);
            dev.setVisibility(View.VISIBLE);
        }
       accl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ordermodalclass upload = new ordermodalclass(imgurlget,nameget,null,orderidget,"accepted");
               db.collection(date).document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       acc.setVisibility(View.VISIBLE);
                   }
               });

           }
       });
        pacl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ordermodalclass upload = new ordermodalclass(imgurlget,nameget,null,orderidget,"packed");
               db.collection(date).document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       pac.setVisibility(View.VISIBLE);
                   }
               });

           }
       });
        outl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ordermodalclass upload = new ordermodalclass(imgurlget,nameget,null,orderidget,"out for delivery");
               db.collection(date).document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       out.setVisibility(View.VISIBLE);
                   }
               });

           }
       });
        devl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ordermodalclass upload = new ordermodalclass(imgurlget,nameget,null,orderidget,"delivered");
               db.collection(date).document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       dev.setVisibility(View.VISIBLE);
                   }
               });

           }
       });

        return v;
    }
}