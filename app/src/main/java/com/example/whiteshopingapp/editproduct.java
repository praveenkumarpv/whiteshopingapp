package com.example.whiteshopingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    ProgressDialog progressDialog;
    private StorageReference mStorageRef;
    private ImageView prdtimseled, BannerImage;
    private Button updates, delets;
    Switch BannerSwitch;
    LinearLayout SelectBannerImage;
    private EditText produed, priceed, offped, deliveryed, stocked, des;
    private Spinner quanted;
    private TextView qunt;
    private String deliveryfee, imagename, product, mrp, off, quanty, stock, offerpercent, delivery, imageurl, descrip;
    private Uri downloadUrl, im;
    private FirebaseFirestore db;
    private Integer i = 0, curr = 9, j, k, y = 0;
    private FirestoreRecyclerAdapter adapter1;
    private RecyclerView catreed;
    boolean banner = false, flag = false;
    private FragmentTransaction fragmentTransaction;
    public String[] quntity = new String[]{"Quantity", "Kilogram", "Gram", "Liter"};
    public String[] category = new String[9];
    private String[] tagsa = new String[100];
    private List<String> group = new ArrayList<>();

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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setContentView(R.layout.loadingscreen);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            im = data.getData();
            if (banner) {
                final StorageReference riversRef = mStorageRef.child("homescreenbanner/" + imagename);
                riversRef.putFile(im).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri;
                                final String downloadUrls = downloadUrl.toString();
                                String accesstoken = imagename;
                                Bannermodalclass upload = new Bannermodalclass(downloadUrls, accesstoken);
                                db.collection("homescreenbanner").document(accesstoken).set(upload)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Glide.with(getActivity()).load(downloadUrls).into(BannerImage);
                                                SelectBannerImage.setVisibility(View.VISIBLE);
                                                banner = false;
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Failed to add", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                startcroping(im);
//            Glide.with(this)
//                    .load(filepath)
//                    .into(prdtimseled);

            }

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //Toast.makeText(getActivity(), "cp", Toast.LENGTH_SHORT).show();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                filepath = result.getUri();
                Glide.with(this).load(filepath).into(prdtimseled);
                progressDialog.dismiss();
                //Toast.makeText(getActivity(), "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getActivity(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void startcroping(Uri im) {
        CropImage.activity(im)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(), this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editproduct, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final Loadingadapter loadingadapter = new Loadingadapter(getActivity());
        db = FirebaseFirestore.getInstance();
        produed = v.findViewById(R.id.productnamesed);
        prdtimseled = v.findViewById(R.id.productimselectered);
        priceed = v.findViewById(R.id.mrped);
        offped = v.findViewById(R.id.offerpred);
        quanted = v.findViewById(R.id.qun);
        deliveryed = v.findViewById(R.id.dvched);
        stocked = v.findViewById(R.id.stocksed);
        updates = v.findViewById(R.id.upload);
        delets = v.findViewById(R.id.delet);
        updates = v.findViewById(R.id.update);
        qunt = v.findViewById(R.id.quntext);
        des = v.findViewById(R.id.description);
        catreed = v.findViewById(R.id.catselectioned);
        product = getArguments().getString("productname");
        mrp = getArguments().getString("price");
        off = getArguments().getString("offerprice");
        quanty = getArguments().getString("quanty");
        stock = getArguments().getString("stock");
        offerpercent = getArguments().getString("offerpercentage");
        delivery = getArguments().getString("delivery");
        imageurl = getArguments().getString("image");
        imagename = getArguments().getString("imagename");
        descrip = getArguments().getString("description");
        produed.setText(product);
        priceed.setText(mrp);
        offped.setText(off);
        qunt.setText(quanty);
        deliveryed.setText(delivery);
        stocked.setText(stock);
        des.setText(descrip);


        //Add Banner Image Section

        BannerSwitch = v.findViewById(R.id.banner_switch);
        SelectBannerImage = v.findViewById(R.id.select_image);
        BannerImage = v.findViewById(R.id.banner_image);

        //checks if banner already added
        CheckIfAdded();


        BannerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!flag) {
                        banner = true;
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PICK_IMAGE_REQUEST);

                    }

                } else {
                    SelectBannerImage.setVisibility(View.GONE);
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.loadingscreen);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    db.collection("homescreenbanner").document(imagename).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    mStorageRef.child("homescreenbanner/" + imagename).delete();
                                    db.collection("homescreenbanner").document(imagename).delete();
                                    flag = false;
                                    BannerSwitch.setChecked(false);
                                    progressDialog.dismiss();

                                }else
                                    progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        BannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Add Banner Image Section Ends Here


        Glide.with(getActivity()).asBitmap().load(imageurl).into(prdtimseled);
        db.collection("products")
                .document(imagename).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        group = (List<String>) document.get("catogary");
                        Log.d("myTag", String.valueOf(group));
                        category = new String[group.size()];
                        group.toArray(category);
                        Log.d("myarray", String.valueOf(category.length));
                    }
                });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, quntity);
        quanted.setAdapter(adapter);
        quanted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //quanted.setText(quanted.getSelectedItem().toString());
                quanty = quanted.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                qunt.setText(quanty);
            }
        });
        qunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanted.setVisibility(View.VISIBLE);
                qunt.setVisibility(View.GONE);
                y++;
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
        Query query = db.collection("categories").orderBy("No");
        FirestoreRecyclerOptions<cataddmodal> op = new FirestoreRecyclerOptions.Builder<cataddmodal>().setQuery(query, cataddmodal.class).build();
        adapter1 = new FirestoreRecyclerAdapter<cataddmodal, catedholder>(op) {
            @NonNull
            @Override
            public catedholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.catselection, parent, false);
                return new catedholder(views);
            }

            @Override
            protected void onBindViewHolder(@NonNull final catedholder holder, int position, @NonNull final cataddmodal model) {
                Glide.with(getContext()).load(model.getImageurl()).into(holder.cati);
                holder.catn.setText(model.getCatName());
                int v = category.length;
                String f = model.getCatName();
                boolean g = group.contains(f);
                if (g == true) {
                    holder.c.setBackgroundColor(Color.LTGRAY);
                }
//                for (int d = 0;d<v;v++){
//                    if (category[d] == f){
//                        holder.c.setBackgroundColor(Color.LTGRAY);
//                        break;
//                    }
//               }
                holder.c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int b = category.length;
                        Log.d("myarray", String.valueOf(category.length));
                        for (int c = 0; c < b; c++) {
                            if (category[c] == model.getCatName()) {
                                holder.c.setBackgroundColor(Color.WHITE);
                                category[c] = null;
                                i--;
                                break;
                            } else if (category[c] != model.getCatName() && c == b - 1) {
                                try {
                                    holder.c.setBackgroundColor(Color.LTGRAY);
                                    category[i] = model.getCatName();
                                    i++;

                                } catch (Exception e) {
                                    for (int w = 0; c < b; c++) {
                                        if (category[w] == null) {
                                            holder.c.setBackgroundColor(Color.LTGRAY);
                                            category[c] = model.getCatName();
                                            i++;
                                        }
                                    }
                                }

                                curr++;
                            }
                        }
                    }
                });
            }
        };
        LinearLayoutManager li = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        catreed.setHasFixedSize(true);
        catreed.setLayoutManager(li);
        catreed.setAdapter(adapter1);
        delets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingadapter.startloading();
                StorageReference desertRef = mStorageRef.child("Productimages/" + imagename);
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.collection("products").document(imagename).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Deleted Succesfully", Toast.LENGTH_SHORT).show();
                                loadingadapter.finishloading();
                                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, new Addproducts());
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
                        loadingadapter.finishloading();
                        Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sellection = 0;
                String f = produed.getText().toString().trim().toLowerCase();
                String s = des.getText().toString().trim().toLowerCase();
                String tag = f;
                int sl = tag.length();
                final String[] temp = tag.split(" ");
                int l = temp.length;
                int pp = 0;
                String hint;
                while (true) {
                    if (sellection == 0) {
                        for (k = 0; k < l; k++) {
                            String tem = temp[k];
                            int teml = tem.length();
                            if (pp == 0) {
                                tagsa[pp] = tem;
                                pp++;
                            } else {
                                tagsa[pp] = temp[k - 1] + " " + tem;
                                pp++;
                            }
                            for (j = 0; j < teml; j++) {
                                hint = String.valueOf(tem.charAt(j));
                                if (j == 0) {
                                    tagsa[pp] = hint;
                                    pp++;
                                } else {
                                    tagsa[pp] = tagsa[pp - 1] + hint;
                                    pp++;
                                }
                            }
                            if (k == l - 1) {
                                for (j = 0; j < sl; j++) {
                                    hint = String.valueOf(tag.charAt(j));
                                    if (j == 0) {
                                        tagsa[pp] = hint;
                                        pp++;
                                    } else {
                                        tagsa[pp] = tagsa[pp - 1] + hint;
                                        pp++;
                                    }
                                }
                            }
                        }
                        String[] gm = s.trim().split("\\.");
                        int sld = gm.length;

                        for (int gf = 0; gf < sld; gf++) {

                            String[] gm2 = gm[gf].trim().split(" ");
                            int sld2 = gm2.length;
                            for (int gf2 = 0; gf2 < sld2; gf2++) {
                                tagsa[pp] = gm2[gf2].trim();
                                pp++;
                            }

                        }
                        sellection++;
                    } else {
                        loadingadapter.startloading();
//                        if (y!=0){
//                           quanty =
//                        }

                        final List<String> catogary = Arrays.asList(category);
                        final List<String> tags = Arrays.asList(tagsa);
                        if (filepath == null) {
                            float p = Float.valueOf(priceed.getText().toString()).floatValue();
                            float o = Float.valueOf(offped.getText().toString()).floatValue();
                            float total = ((p - o) / p) * 100;
                            int tto = (int) total;
                            String offerpersents = Integer.toString(tto);
                            if (deliveryed.getText().toString().trim().isEmpty()) {
                                deliveryfee = "Free";
                            }
                            Modalclass upload = new Modalclass(produed.getText().toString().trim(), imageurl, priceed.getText().toString().trim(), offped.getText().toString().trim(), quanty,
                                    stocked.getText().toString().trim(), offerpersents, deliveryfee, imagename, catogary, tags, des.getText().toString().trim());
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
                        } else {
                            StorageReference desertRef = mStorageRef.child("Productimages/" + imagename);
                            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    final StorageReference riversRef = mStorageRef.child("Productimages/" + imagename);
                                    riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    downloadUrl = uri;
                                                    String downloadUrls = downloadUrl.toString();
                                                    String accesstoken = imagename;
                                                    float p = Float.valueOf(priceed.getText().toString()).floatValue();
                                                    float o = Float.valueOf(offped.getText().toString()).floatValue();
                                                    float total = ((p - o) / p) * 100;
                                                    int tto = (int) total;
                                                    String offerpersented = Integer.toString(tto);
                                                    if (deliveryed.getText().toString().trim().isEmpty()) {
                                                        deliveryfee = "Free";
                                                    }
                                                    Modalclass upload = new Modalclass(produed.getText().toString().trim(), downloadUrls, priceed.getText().toString().trim(),
                                                            offped.getText().toString().trim(), quanty,
                                                            stocked.getText().toString().trim(), offerpersented, deliveryfee, accesstoken, catogary, tags, des.getText().toString().trim());
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
                                            Toast.makeText(getActivity(), "Error Occured in update", Toast.LENGTH_SHORT).show();
                                            loadingadapter.finishloading();

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Error in img delete", Toast.LENGTH_SHORT).show();
                                    loadingadapter.finishloading();
                                }
                            });

                        }
                        break;
                    }
                }
            }
        });
        return v;
    }

    private void CheckIfAdded() {
        db.collection("homescreenbanner").document(imagename).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        flag = true;
                        BannerSwitch.setChecked(true);
                        SelectBannerImage.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(document.getString("imageurl")).into(BannerImage);

                    }
                }
            }
        });

    }

    private class catedholder extends RecyclerView.ViewHolder {
        ImageView cati;
        TextView catn;
        LinearLayout c;

        public catedholder(@NonNull View itemView) {
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