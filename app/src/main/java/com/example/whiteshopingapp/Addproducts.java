package com.example.whiteshopingapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Addproducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Addproducts extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filepath,im;
    FragmentTransaction fragmentTransaction;
    private StorageReference mStorageRef;
    private ImageView prdtimsel;
    private Button prdtadbt;
    private EditText produ, price,offp,delivery,stock,ta;
    private String deliveryfee,a, offerpersent;
    private Uri downloadUrl;
    private Spinner quant;
    private FirebaseFirestore db;
    private RecyclerView catre;
    private FragmentManager fragmentManager;
    private Integer i=0,curr=9,j,k,sellection=0,l;
    private FirestoreRecyclerAdapter adapter1;
    public  String [] quntity = new String[]{"Quantity","Kilogram","Gram","Liter"};
    public  String [] category = new String[curr];
    public  String [] tagsa = new String[60];

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
    private void startcroping(Uri im) {
        CropImage.activity(im)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(),this);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getActivity(), "in", Toast.LENGTH_SHORT).show();
       if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
                RESULT_OK
                && data != null && data.getData() != null) {
           im = data.getData();
//            Glide.with(this)
//                    .load(filepath)
//                    .into(prdtimsel);
           startcroping(im);
       }
           if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
              // Toast.makeText(getActivity(), "cp", Toast.LENGTH_SHORT).show();
               CropImage.ActivityResult result = CropImage.getActivityResult(data);
               if (resultCode == RESULT_OK) {
                   filepath = result.getUri();
                   Glide.with(this).load(filepath).into(prdtimsel);
                  // Toast.makeText(getActivity(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
               } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                   Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
               }
           }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_addproducts, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final Loadingadapter loadingadapter = new Loadingadapter(getActivity());
        db = FirebaseFirestore.getInstance();
        produ = v.findViewById(R.id.productnames);
        prdtimsel = v.findViewById(R.id.productimselecter);
        price = v.findViewById(R.id.mrp);
        offp = v.findViewById(R.id.offerpr);
        quant = v.findViewById(R.id.qun);
        delivery =v.findViewById(R.id.dvch);
        stock = v.findViewById(R.id.stocks);
        prdtadbt = v.findViewById(R.id.upload);
        catre = v.findViewById(R.id.catselection);
        ta = v.findViewById(R.id.tags);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,quntity);
        quant.setAdapter(adapter);
        Query query = db.collection("categories");
        FirestoreRecyclerOptions<cataddmodal> op = new FirestoreRecyclerOptions.Builder<cataddmodal>().setQuery(query,cataddmodal.class).build();
        adapter1 = new FirestoreRecyclerAdapter<cataddmodal, catholder>(op) {
            @NonNull
            @Override
            public catholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catselection,parent,false);
                return new catholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final catholder holder, int position, @NonNull final cataddmodal model) {
                Glide.with(getContext()).load(model.getImageurl()).into(holder.cati);
                holder.catn.setText(model.getCatName());

                holder.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      int b = category.length;
                      for (int c = 0;c<b;c++) {
                          if (category[c] == model.getCatName()) {
                              holder.c.setBackgroundColor(Color.WHITE);
                              category[c]=null;
                              i--;
                              break;
                          }
                          else if(category[c] != model.getCatName() && c==b-1){
                              holder.c.setBackgroundColor(Color.LTGRAY);
                              category[i]= model.getCatName();
                              i++;
                              curr++;
                          }
                      }

                     // String j = String.valueOf(i);
                       // Toast.makeText(getActivity(), category[--i], Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        //GridLayoutManager gridLayout = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager li = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        catre.setHasFixedSize(true);
        catre.setLayoutManager(li);
        catre.setAdapter(adapter1);
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
                String tag =  produ.getText().toString().trim();
                int sl = tag.length();
                final String [] temp = tag.split(" ");
                l =  temp.length;
                int p =0;
                String hint;
                if (sellection == 0){
                for (k=0;k<l;k++){
                   String tem = temp[k];
                   int teml = tem.length();
                   if (p == 0){
                       tagsa[p] =tem;
                       p++;
                   }
                   else {
                       tagsa[p] = temp[k-1]+" "+tem;
                       p++;
                   }
                   for (j=0;j<teml;j++){
                       hint=String.valueOf(tem.charAt(j));
                       if (j == 0){
                           tagsa[p] = hint;
                           p++;
                       }
                       else {
                           tagsa[p] = tagsa[p-1]+hint;
                           p++;
                       }
                   }
                   if (k==l-1){
                       for (j=0;j<sl;j++){
                           hint=String.valueOf(tag.charAt(j));
                           if (j==0){
                               tagsa[p] = hint;
                               p++;
                           }
                           else {
                               tagsa[p]=tagsa[p-1]+hint;
                               p++;
                           }
                       }
                   }
                }
                int q = tagsa.length;
                for (int f = 0 ;f<q;f++){
                    if (tagsa[f] != null) {
                        if (f == 0) {
                            a = tagsa[f];
                        } else {
                            a = a + "," + tagsa[f];
                        }
                    }
                }
                    ta.setText(a);
                    sellection++;
                }
                else {

                try {
                    loadingadapter.startloading();
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
                                            final List<String> catogary = Arrays.asList(category);
                                            final List<String> tags = Arrays.asList(tagsa);
                                            String downloadUrls = downloadUrl.toString();
                                            String accesstoken = random;
                                            try {
                                                float p  = Float.valueOf(price.getText().toString()).floatValue();
                                                float o  = Float.valueOf(offp.getText().toString()).floatValue();
                                                float total = ((p-o)/p)*100;
                                                int tto = (int) total;
                                                offerpersent = Integer.toString(tto);
                                            }
                                            catch (Exception e){

                                            }

                                            if (delivery.getText().toString().trim().isEmpty()){
                                                deliveryfee = "Free";
                                            }
                                            Modalclass upload = new Modalclass(produ.getText().toString().trim(),downloadUrls,price.getText().toString().trim(),
                                                    offp.getText().toString().trim(),quant.getSelectedItem().toString(),
                                                    stock.getText().toString().trim(),offerpersent,deliveryfee,accesstoken,catogary,tags);
                                            db.collection("products").document(random).set(upload).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getActivity(), "Uploading Successfull", Toast.LENGTH_SHORT).show();
                                                    produ.setText("");
                                                    price.setText("");
                                                    offp.setText("");
                                                    stock.setText("");
                                                    refesh();
                                                    sellection=0;
                                                    i=0;
                                                    j=0;
                                                    k=0;
                                                    l=0;
                                                    a=null;
                                                    loadingadapter.finishloading();

//                                                    tags.clear();
//                                                    catogary.clear();



                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getActivity(), "Uploading Failed", Toast.LENGTH_SHORT).show();
                                                    loadingadapter.finishloading();
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
                                    loadingadapter.finishloading();
                                }
                            });

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_SHORT).show();
                    loadingadapter.finishloading();
                }

            }
            }
        });
        return v;
    }

    private void refesh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private class catholder extends RecyclerView.ViewHolder {
        ImageView cati;
        TextView catn;
        LinearLayout c;
        public catholder(@NonNull View itemView) {
            super(itemView);
            cati = itemView.findViewById(R.id.catimg);
            catn = itemView.findViewById(R.id.catname);
            c = itemView.findViewById(R.id.catfull);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter1.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter1.stopListening();
    }
}