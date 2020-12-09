package com.example.whiteshopingapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Use the {@link Ordersview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ordersview extends Fragment {

    TextView Date;
    ImageView Date_Picker;
    RecyclerView OrdersRc;
    private FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db;
    LinearLayout OrdersEmpty, OrdersSorted;
    ProgressDialog progressDialog;
    FragmentTransaction fragmentTransaction;
    String year, month, day, date;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Ordersview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ordersview.
     */
    // TODO: Rename and change types and number of parameters
    public static Ordersview newInstance(String param1, String param2) {
        Ordersview fragment = new Ordersview();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ordersview, container, false);

        //Initialization of elements
        Date = v.findViewById(R.id.Orders_head);
        Date_Picker = v.findViewById(R.id.date_picker);
        OrdersEmpty = v.findViewById(R.id.empty_orders);
        OrdersSorted = v.findViewById(R.id.orders_items);
        OrdersRc = v.findViewById(R.id.orders_recycler);
        db = FirebaseFirestore.getInstance();
        //Loading Started
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.loadingscreen);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        date = day + "/" + month + "/" + year;
        Date.setText("Today Orders ( " +date+" )");
        //Check If Orders Are empty
        CheckOrderEmpty();
        //If Empty Show Empty orders view
        //Else Load Orders To RC
        LoadOrdersToRc(date);

        //Date Picker implimentation
        Date_Picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.loadingscreen);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        String yy=String.valueOf(i);
                        String mm=String.valueOf(i1+1);
                        String dd=String.valueOf(i2);
                        String yymmdd= dd + "/" + mm + "/" + yy;
                        Date.setText("Showing Orders On " +yymmdd);
                        LoadOrdersToRc(yymmdd);
                    }
                },Integer.parseInt(year),Integer.parseInt(month)-1 ,Integer.parseInt(day));
                datePickerDialog.show();
            }
        });


        return v;
    }

    //Load ORders Function
    private void LoadOrdersToRc(final String date) {

        Query query = db.collection("Orders").whereEqualTo("date", date).orderBy("time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ordermodalclass> op = new FirestoreRecyclerOptions.Builder<ordermodalclass>().setQuery(query, ordermodalclass.class).build();
        adapter = new FirestoreRecyclerAdapter<ordermodalclass, Ordersview.orderviewholder>(op) {
            @NonNull
            @Override
            public Ordersview.orderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderviewrecycler, parent, false);
                return new Ordersview.orderviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final Ordersview.orderviewholder holder, int position, @NonNull final ordermodalclass model) {
                // Glide.with(getActivity()).asBitmap().load(model.getImurl()).into(holder.proimg);
                final DocumentReference doc = db.collection("user_data").document(model.getUser_id());
                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            holder.name.setText(documentSnapshot.getString("name"));
                            Glide.with(getActivity()).load(documentSnapshot.getString("img")).into(holder.proimg);

                        }
                    }
                });
                // holder.name.setText(model.getUser_id());
                holder.totalPrice.setText(""+model.getTotalAmount()+" /-");
                holder.time.setText(model.getTime2());
                holder.status.setText(model.getStatus());
                holder.orderid.setText(model.getOrder_id());
                holder.ordercard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderdetails ord = new orderdetails();
                        Bundle args = new Bundle();
                        args.putString("img", model.getImurl());
                        args.putString("name", model.getUser_id());
                        args.putString("orderid", model.getOrder_id());
                        args.putString("status", model.getStatus());
                        args.putString("date", date);
                        args.putString("time", model.getTime2());
                        args.putString("total", model.getTotalAmount());
                        ord.setArguments(args);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, ord);
                        fragmentTransaction.commit();
                    }
                });
                progressDialog.dismiss();
            }

        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        OrdersRc.setHasFixedSize(true);
        OrdersRc.setLayoutManager(layoutManager);
        OrdersRc.setAdapter(adapter);
        adapter.startListening();

    }

    //Check Orders Empty
    private void CheckOrderEmpty() {

    }

    private class orderviewholder extends RecyclerView.ViewHolder {
        CircleImageView proimg;
        TextView name, time, orderid, status, totalPrice;
        CardView ordercard;

        public orderviewholder(@NonNull View itemView) {
            super(itemView);
            proimg = itemView.findViewById(R.id.proimgs);
            name = itemView.findViewById(R.id.proname);
            time = itemView.findViewById(R.id.protime);
            orderid = itemView.findViewById(R.id.orderid);
            status = itemView.findViewById(R.id.status);
            ordercard = itemView.findViewById(R.id.ordercardview);
            totalPrice = itemView.findViewById(R.id.total_price);
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