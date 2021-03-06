package com.example.whiteshopingapp;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.Distribution;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

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
    ProgressDialog progressDialog;
    TextView Head , SearchTagText;
    LinearLayout SearchTag;
    EditText SearchKey;
    ImageView SearchButton, TagClose;
    boolean Clicked = false;
    Query query;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Override
    public void onResume() {
        super.onResume();
        ShowProduct(query);

    }

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
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.loadingscreen);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        addp = v.findViewById(R.id.addproducts);
        addedp = v.findViewById(R.id.productre);
        SearchButton = v.findViewById(R.id.search);
        SearchKey = v.findViewById(R.id.search_key);
        Head = v.findViewById(R.id.head);
        TagClose = v.findViewById(R.id.close_tag);
        SearchTag = v.findViewById(R.id.tag_view);
        SearchTagText= v.findViewById(R.id.tag);

        TagClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchTag.setVisibility(View.GONE);
                Head.setVisibility(View.VISIBLE);
                ShowProduct( db.collection("products"));
            }
        });

        query= db.collection("products").orderBy("productname");
        ShowProduct(query);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Clicked) {
                    String key = SearchKey.getText().toString();
                    if (key.isEmpty())
                        SearchKey.setError("Enter Some Text to search");
                    else {
                        SearchKey.setVisibility(View.GONE);
                        SearchTag.setVisibility(View.VISIBLE);
                        SearchTagText.setText(""+key);

                        query = db.collection("products").whereArrayContains("tags", key.toLowerCase());
                        ShowProduct(query);
                        Clicked = false;
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                else{
                    SearchKey.setText("");

                    Head.setVisibility(View.INVISIBLE);
                    SearchKey.setVisibility(View.VISIBLE);
                    SearchKey.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                    Clicked=true;
                }


            }
        });




        addp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_home2_to_addproducts2);

                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new Addproducts());
                fragmentTransaction.commit();
            }
        });


        return v;
    }

    private void ShowProduct(Query query) {
        FirestoreRecyclerOptions<Modalclass> op = new FirestoreRecyclerOptions.Builder<Modalclass>().setQuery(query, Modalclass.class).build();
        adapter = new FirestoreRecyclerAdapter<Modalclass, productviewholder>(op) {
            @NonNull
            @Override
            public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addproductcardview, parent, false);
                return new productviewholder(view);
            }

            @Override
            protected void onBindViewHolder(final productviewholder productviewholder, int i, final Modalclass modalclass) {
                productviewholder.pri.setText("₹" + modalclass.getOffprice());
                productviewholder.mr.setText("₹" + modalclass.getMrp());
                productviewholder.productname.setText(modalclass.getProductname());
                Glide.with(getActivity()).asBitmap().load(modalclass.getImurl()).into(productviewholder.proimg);
                productviewholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editproduct lb = new editproduct();
                        Bundle args = new Bundle();
                        args.putString("productname", modalclass.getProductname());
                        args.putString("price", modalclass.getMrp());
                        args.putString("offerprice", modalclass.getOffprice());
                        args.putString("quanty", modalclass.getQuantity());
                        args.putString("stock", modalclass.getStock());
                        args.putString("offerpercentage", modalclass.getOffpers());
                        args.putString("delivery", modalclass.getDeliverycharge());
                        args.putString("image", modalclass.getImurl());
                        args.putString("imagename", modalclass.getImagename());
                        args.putString("description", modalclass.getDescription());
                        lb.setArguments(args);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, lb);
                        fragmentTransaction.commit();
                    }
                });

                progressDialog.dismiss();
            }
        };
        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        addedp.setHasFixedSize(true);
        addedp.setLayoutManager(gridLayout);
        addedp.setAdapter(adapter);
        adapter.startListening();
    }

    private class productviewholder extends RecyclerView.ViewHolder {
        ImageView proimg;
        TextView pri, mr, productname;
        Button edit;

        public productviewholder(@NonNull View itemView) {
            super(itemView);
            proimg = itemView.findViewById(R.id.productimage);
            pri = itemView.findViewById(R.id.offerprice);
            mr = itemView.findViewById(R.id.mrps);
            //edit = itemView.findViewById(R.id.editbutton);
            productname = itemView.findViewById(R.id.productnames);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}