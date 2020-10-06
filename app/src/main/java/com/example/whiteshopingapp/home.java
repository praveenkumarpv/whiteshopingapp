package com.example.whiteshopingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

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
            protected void onBindViewHolder(productviewholder productviewholder, int i, Modalclass modalclass) {
                productviewholder.pri.setText(modalclass.getMrp());
                Glide.with(getActivity()).asBitmap().load(modalclass.getImurl()).into(productviewholder.proimg);

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
        TextView pri;

        public productviewholder(@NonNull View itemView) {
            super(itemView);
            proimg  = itemView.findViewById(R.id.productimage);
            pri = itemView.findViewById(R.id.pricere);
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