package com.example.whiteshopingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderdetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderdetails extends Fragment {
    ImageView acc,pac,out,dev;
    CircleImageView proimage;
    TextView proname,orderid;

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
        acc = v.findViewById(R.id.accept);
        pac = v.findViewById(R.id.packed);
        out = v.findViewById(R.id.outfordelivery);
        dev = v.findViewById(R.id.delivered);
        proimage = v.findViewById(R.id.userproimg);
        proname = v.findViewById(R.id.userproname);
        orderid = v.findViewById(R.id.orderid);

        return v;
    }
}