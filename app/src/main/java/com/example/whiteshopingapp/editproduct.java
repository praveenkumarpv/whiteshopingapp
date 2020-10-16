package com.example.whiteshopingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editproduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editproduct extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private StorageReference mStorageRef;
    private ImageView prdtimseled;
    private Button updates,delets;
    private EditText produed, priceed,offped,deliveryed,stocked,offpered;
    private Spinner quanted,cated;
    TextView qunt,catet;
    private String deliveryfee,imagename,product,mrp,off,quanty,stock,offerpercent,categ,delivery,imageurl;
    private Uri downloadUrl;
    private FirebaseFirestore db;
    private FragmentTransaction fragmentTransaction;
    public  String [] quntity = new String[]{"KG","GM","LTR"};
    public  String [] category = new String[]{"KG","GM","LTR"};

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editproduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editproduct.
     */
    // TODO: Rename and change types and number of parameters
    public static editproduct newInstance(String param1, String param2) {
        editproduct fragment = new editproduct();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
                RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            Glide.with(this)
                    .load(filepath)
                    .into(prdtimseled);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_editproduct, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final Loadingadapter loadingadapter = new Loadingadapter(getActivity());
        db = FirebaseFirestore.getInstance();
        produed = v.findViewById(R.id.productnamesed);
        prdtimseled = v.findViewById(R.id.productimselectered);
        priceed = v.findViewById(R.id.mrped);
        offped = v.findViewById(R.id.offerpred);
        quanted = v.findViewById(R.id.qun);
        deliveryed =v.findViewById(R.id.dvched);
        stocked = v.findViewById(R.id.stocksed);
        offpered = v.findViewById(R.id.offpered);
        cated = v.findViewById(R.id.catg);
        updates = v.findViewById(R.id.upload);
        delets = v.findViewById(R.id.delet);
        updates = v.findViewById(R.id.update);
        qunt = v.findViewById(R.id.quntext);
        catet = v.findViewById(R.id.catgtext);
        product = getArguments().getString("productname");
        mrp = getArguments().getString("price");
        off = getArguments().getString("offerprice");
        quanty = getArguments().getString("quanty");
        stock = getArguments().getString("stock");
        offerpercent = getArguments().getString("offerpercentage");
        categ = getArguments().getString("category");
        delivery = getArguments().getString("delivery");
        imageurl = getArguments().getString("image");
        imagename = getArguments().getString("imagename");
        produed.setText(product);
        priceed.setText(mrp);
        offped.setText(off);
        qunt.setText(quanty);
        deliveryed.setText(delivery);
        stocked.setText(stock);
        offpered.setText(offerpercent);
        catet.setText(categ);
        Glide.with(getActivity()).asBitmap().load(imageurl).into(prdtimseled);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,quntity);
        quanted.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,category);
        cated.setAdapter(adapter1);
        quanted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                qunt.setText(quanted.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                qunt.setText(quanty);
            }
        });
        cated.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                catet.setText(cated.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                catet.setText(categ);
            }
        });

        prdtimseled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        delets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingadapter.startloading();
                StorageReference desertRef = mStorageRef.child("Productimages/" +imagename);
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                      db.collection("products").document(imagename).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Toast.makeText(getActivity(), "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                              loadingadapter.finishloading();
                              fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                              fragmentTransaction.replace(R.id.fragment,new Addproducts());
                              fragmentTransaction.commit();
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(getActivity(), "Unsuccesfull", Toast.LENGTH_SHORT).show();
                          }
                      });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingadapter.startloading();
                if (filepath == null){
                    if (deliveryed.getText().toString().trim().isEmpty()){
                        deliveryfee = "Free";
                    }
                    Modalclass upload = new Modalclass(produed.getText().toString().trim(),imageurl,priceed.getText().toString().trim(),offped.getText().toString().trim(),quanted.getSelectedItem().toString(),
                            stocked.getText().toString().trim(),offpered.getText().toString().trim(),deliveryfee,imagename,cated.getSelectedItem().toString().trim());
                    db.collection("products").document(imagename).set(upload, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loadingadapter.finishloading();
                            Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingadapter.finishloading();
                            Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {

                    final String random = UUID.randomUUID().toString();
                    final StorageReference riversRef = mStorageRef.child("Productimages/" +random);
                    riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri;
                                    String downloadUrls = downloadUrl.toString();
                                    String accesstoken = random;
                                    if (deliveryed.getText().toString().trim().isEmpty()){
                                        deliveryfee = "Free";
                                    }
                                    Modalclass upload = new Modalclass(produed.getText().toString().trim(),downloadUrls,priceed.getText().toString().trim(),offped.getText().toString().trim(),quanted.getSelectedItem().toString().trim(),
                                            stocked.getText().toString().trim(),offpered.getText().toString().trim(),deliveryfee,accesstoken,cated.getSelectedItem().toString().trim());
                                    db.collection("products").document(imagename).set(upload, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    loadingadapter.finishloading();
                                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingadapter.finishloading();
                                            Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                            loadingadapter.finishloading();

                        }
                    });
                }

            }
        });
        return  v;
    }
}