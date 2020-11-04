package com.example.whiteshopingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {
    TextView date;
    ImageView drop;
    DatePickerDialog datePickerDialog;
    int year ,month,day;
    FirebaseFirestore db;
    RecyclerView order;
    int ordercount;
    FragmentTransaction fragmentTransaction;
    private String orderselecter,years,months,dates;
    private FirestoreRecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseFirestore.getInstance();
        date = v.findViewById(R.id.datepicer);
        drop = v.findViewById(R.id.datepickerimage);
        order = v.findViewById(R.id.orderview);
       Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        years = Integer.toString(year);
        month = calendar.get(Calendar.MONTH)+1;
        months = Integer.toString(month);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dates = Integer.toString(day);
        orderselecter = 00+dates+"-"+months+"-"+years ;
        getdata(orderselecter);
        date.setText(00+dates+"/"+months+"/"+years);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                year = calendar.get(Calendar.YEAR);
                years = Integer.toString(year);
                month = calendar.get(Calendar.MONTH)+1;
                months = Integer.toString(month);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                dates = Integer.toString(day);
                datePickerDialog  = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        date.setText(00+day + "/"+ month +"/"+ year);
                        orderselecter = 00+day + "-"+month+"-"+year ;
                        orderSorted lv = new orderSorted();
                        Bundle arg = new Bundle();
                        arg.putString("date",orderselecter);
                        lv.setArguments(arg);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment,lv);
                        fragmentTransaction.commit();

                    }
                },year,month,day);
                datePickerDialog.show();
            }

        });

        return v;
    }

    private void getdata(final String orderselecter) {
        String wow = orderselecter;
        Query query = db.collection("Orders").orderBy("date").startAt(wow);
       // Toast.makeText(getActivity(), wow, Toast.LENGTH_SHORT).show();
        FirestoreRecyclerOptions<ordermodalclass> op = new FirestoreRecyclerOptions.Builder<ordermodalclass>().setQuery(query,ordermodalclass.class).build();
        adapter = new FirestoreRecyclerAdapter<ordermodalclass, orderviewholder>(op) {
            @NonNull
            @Override
            public orderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderviewrecycler,parent,false);
                return new orderviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final orderviewholder holder, int position, @NonNull final ordermodalclass model) {
               // Glide.with(getActivity()).asBitmap().load(model.getImurl()).into(holder.proimg);
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
               // holder.name.setText(model.getUser_id());
                holder.time.setText(model.getTime());
                holder.status.setText(model.getStatus());
                holder.orderid.setText(model.getOrder_id());
                holder.ordercard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderdetails ord = new orderdetails();
                        Bundle args = new Bundle();
                        args.putString("img",model.getImurl());
                        args.putString("name",model.getUser_id());
                        args.putString("orderid",model.getOrder_id());
                        args.putString("status",model.getStatus());
                        args.putString("date",orderselecter);
                        args.putString("time",model.getTime());
                        ord.setArguments(args);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment,ord);
                        fragmentTransaction.commit();
                    }
                });

            }

            @Override
            public int getItemCount() {

                ordercount = super.getItemCount();
                if (ordercount == 0){
                  //  Toast.makeText(getActivity(), "No orders yet", Toast.LENGTH_SHORT).show();
                }
                return super.getItemCount();
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        order.setHasFixedSize(true);
        order.setLayoutManager(layoutManager);
        order.setAdapter(adapter);

    }


    private class orderviewholder extends RecyclerView.ViewHolder {
        CircleImageView proimg;
        TextView name,time,orderid,status;
        CardView ordercard;
        public orderviewholder(@NonNull View itemView) {
            super(itemView);
            proimg = itemView.findViewById(R.id.proimgs);
            name = itemView.findViewById(R.id.proname);
            time = itemView.findViewById(R.id.protime);
            orderid = itemView.findViewById(R.id.orderid);
            status = itemView.findViewById(R.id.status);
            ordercard = itemView.findViewById(R.id.ordercardview);
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