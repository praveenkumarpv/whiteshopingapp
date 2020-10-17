package com.example.whiteshopingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    FloatingActionButton addp;
    FragmentTransaction fragmentTransaction;
    RecyclerView addedp;
    private FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        db = FirebaseFirestore.getInstance();
        final Loadingadapter loadingadapter = new Loadingadapter(getActivity());
        loadingadapter.startloading();
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingadapter.finishloading();

            }
        },2000);

        addp =v.findViewById(R.id.addproducts);
        addedp = v.findViewById(R.id.productre);
        addp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_home2_to_addproducts2);

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment,new Addproducts());
                fragmentTransaction.commit();
            }
        });
        Query query = db.collection("products");
        FirestoreRecyclerOptions<Modalclass> op = new FirestoreRecyclerOptions.Builder<Modalclass>().setQuery(query,Modalclass.class).build();
        adapter = new FirestoreRecyclerAdapter<Modalclass, productviewholder>(op){
            @NonNull
            @Override
            public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addproductcardview,parent,false);
                return new productviewholder(view);
            }

            @Override
            protected void onBindViewHolder(final productviewholder productviewholder, int i, final Modalclass modalclass) {
                productviewholder.pri.setText(modalclass.getOffprice());
                productviewholder.mr.setText("₹"+modalclass.getMrp());
                productviewholder.productname.setText(modalclass.getProductname());
                Glide.with(getActivity()).asBitmap().load(modalclass.getImurl()).into(productviewholder.proimg);
                productviewholder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editproduct lb = new editproduct();
                        Bundle args = new Bundle();
                        args.putString("productname",modalclass.getProductname());
                        args.putString("price",modalclass.getMrp());
                        args.putString("offerprice",modalclass.getOffprice());
                        args.putString("quanty",modalclass.getQuantity());
                        args.putString("stock",modalclass.getStock());
                        args.putString("offerpercentage",modalclass.getOffpers());
                        args.putString("category",modalclass.getCategory());
                        args.putString("delivery",modalclass.getDeliverycharge());
                        args.putString("image",modalclass.getImurl());
                        args.putString("imagename",modalclass.getImagename());
                        lb.setArguments(args);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment,lb);
                        fragmentTransaction.commit();
                    }
                });

            }
        };
        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        addedp.setHasFixedSize(true);
        addedp.setLayoutManager(gridLayout);
        addedp.setAdapter(adapter);
        return v;
    }

    private class productviewholder extends RecyclerView.ViewHolder {
        ImageView proimg;
        TextView pri,mr,productname;
        Button edit;

        public productviewholder(@NonNull View itemView) {
            super(itemView);
            proimg  = itemView.findViewById(R.id.productimage);
            pri = itemView.findViewById(R.id.offerprice);
            mr = itemView.findViewById(R.id.mrps);
            edit = itemView.findViewById(R.id.editbutton);
            productname =itemView.findViewById(R.id.productnames);
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