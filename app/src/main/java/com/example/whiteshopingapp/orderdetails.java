package com.example.whiteshopingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

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
    TextView proname,orderid,addrview,tot;
    RecyclerView consumedproduct;
    private FirestoreRecyclerAdapter adapter;
    String nameget,orderidget,imgurlget,statusget,date,time,total;
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
        final address address = new address(getActivity());
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
        addrview = v.findViewById(R.id.adressview);
        proimage = v.findViewById(R.id.userproimg);
        proname = v.findViewById(R.id.userproname);
        orderid = v.findViewById(R.id.orderid);
        tot = v.findViewById(R.id.totalamount);
        nameget = getArguments().getString("name");
        imgurlget = getArguments().getString("img");
        orderidget = getArguments().getString("orderid");
        statusget = getArguments().getString("status");
        date = getArguments().getString("date");
        time = getArguments().getString("time");
        total = getArguments().getString("total");
        tot.setText(total);
        final DocumentReference doc = db.collection("user_data").document(nameget);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                 proname.setText(documentSnapshot.getString("name"));
                 Glide.with(getActivity()).load(documentSnapshot.getString("img")).into(proimage);
                }
            }
        });

//        proname.setText(nameget);
        orderid.setText(orderidget);
        addrview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address.showaddres(nameget);

            }
        });
        if (statusget.equals("Accepted")){
            acc.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("Packed")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("Out for delivery")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
            out.setVisibility(View.VISIBLE);
        }
        else if (statusget.equals("Delivered")){
            acc.setVisibility(View.VISIBLE);
            pac.setVisibility(View.VISIBLE);
            out.setVisibility(View.VISIBLE);
            dev.setVisibility(View.VISIBLE);
        }
       accl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //ordermodalclass upload = new ordermodalclass(imgurlget,orderidget,"accepted",nameget,date,time);
               Map<String,Object>upload = new HashMap<>();
               upload.put("Status","Accepted");
               db.collection("user_data").document(nameget).collection("Orders").document(orderidget).set(upload,SetOptions.merge());
               db.collection("Orders").document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
               //ordermodalclass upload = new ordermodalclass(imgurlget,orderidget,"packed",nameget,date,time);
               Map<String,Object>upload = new HashMap<>();
               upload.put("Status","Packed");
               db.collection("user_data").document(nameget).collection("Orders").document(orderidget).set(upload,SetOptions.merge());
               db.collection("Orders").document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
               //ordermodalclass upload = new ordermodalclass(imgurlget,orderidget,"out for delivery",nameget,date,time);
               Map<String,Object>upload = new HashMap<>();
               upload.put("Status","Out for delivery");
               db.collection("user_data").document(nameget).collection("Orders").document(orderidget).set(upload,SetOptions.merge());
               db.collection("Orders").document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
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
               //ordermodalclass upload = new ordermodalclass(imgurlget,orderidget,"delivered",nameget,date,time);
               Map<String,Object>upload = new HashMap<>();
               upload.put("Status","Delivered");
               db.collection("user_data").document(nameget).collection("Orders").document(orderidget).set(upload,SetOptions.merge());
               db.collection("Orders").document(orderidget).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       dev.setVisibility(View.VISIBLE);
                   }
               });

           }
       });
        Query query = db.collection("Orders").document(orderidget).collection("Items");
        FirestoreRecyclerOptions<itemsmodelclass> op = new FirestoreRecyclerOptions.Builder<itemsmodelclass>().setQuery(query,itemsmodelclass.class).build();
        adapter = new FirestoreRecyclerAdapter<itemsmodelclass, itemsviewholder>(op) {
            @NonNull
            @Override
            public itemsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
                return new itemsviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull itemsviewholder holder, int position, @NonNull itemsmodelclass model) {
                holder.product.setText(model.getProduct());
                String c = Long.toString(model.getCount());
                holder.quant.setText(c);

            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        consumedproduct.setHasFixedSize(true);
        consumedproduct.setLayoutManager(layoutManager);
        consumedproduct.setAdapter(adapter);

        return v;
    }

    private class itemsviewholder extends RecyclerView.ViewHolder {
        TextView product,quant;
        public itemsviewholder(@NonNull View itemView) {
            super(itemView);
            product = itemView.findViewById(R.id.itemsname);
            quant = itemView.findViewById(R.id.itemqunt);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }
}