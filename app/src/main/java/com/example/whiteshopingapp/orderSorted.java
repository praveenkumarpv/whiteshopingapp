package com.example.whiteshopingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderSorted#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderSorted extends Fragment {
    RecyclerView sorted;
    FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public orderSorted() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderSorted.
     */
    // TODO: Rename and change types and number of parameters
    public static orderSorted newInstance(String param1, String param2) {
        orderSorted fragment = new orderSorted();
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
        View view = inflater.inflate(R.layout.fragment_order_sorted, container, false);
        sorted = view.findViewById(R.id.ordersorted);
        db = FirebaseFirestore.getInstance();
        int day = getArguments().getInt("day");
        int month = getArguments().getInt("month");
        int year = getArguments().getInt("year");
        String orderkey = day+"/"+month+"/"+year;
        Toast.makeText(getActivity(), orderkey, Toast.LENGTH_SHORT).show();
        Query query = db.collection("Orders").whereEqualTo("date",orderkey);
        Toast.makeText(getActivity(), orderkey, Toast.LENGTH_SHORT).show();
        FirestoreRecyclerOptions<ordermodalclass> op = new FirestoreRecyclerOptions.Builder<ordermodalclass>().setQuery(query,ordermodalclass.class).build();
        adapter = new FirestoreRecyclerAdapter<ordermodalclass, sortviewholder>(op) {
            @NonNull
            @Override
            public sortviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderviewrecycler,parent,false);
                return new sortviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final sortviewholder holder, int position, @NonNull ordermodalclass model) {
                final DocumentReference doc = db.collection("user_data").document(model.getUser_id());
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            holder.name.setText(documentSnapshot.getString("name"));
                            Glide.with(getActivity()).load(documentSnapshot.getString("img")).into(holder.proimg);

                        }
                    }
                });
               // Glide.with(getActivity()).asBitmap().load(model.getImurl()).into(holder.proimg);
               // holder.name.setText(model.getUser_id());
                holder.time.setText(model.getDate());
                holder.status.setText(model.getStatus());
                holder.orderid.setText(model.getOrder_id());
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        sorted.setHasFixedSize(true);
        sorted.setLayoutManager(layoutManager);
        sorted.setAdapter(adapter);
        return view;
    }

    private class sortviewholder extends RecyclerView.ViewHolder {
        CircleImageView proimg;
        TextView name,time,orderid,status;
        public sortviewholder(@NonNull View itemView) {
            super(itemView);
            proimg = itemView.findViewById(R.id.proimgs);
            name = itemView.findViewById(R.id.proname);
            time = itemView.findViewById(R.id.protime);
            orderid = itemView.findViewById(R.id.orderid);
            status = itemView.findViewById(R.id.status);
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