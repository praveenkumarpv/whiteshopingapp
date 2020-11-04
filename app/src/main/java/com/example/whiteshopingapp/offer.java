package com.example.whiteshopingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link offer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class offer extends Fragment {
    private RecyclerView homeoffer,offersre;
    private FirestoreRecyclerAdapter adapter,adapter1;
    private FirebaseFirestore db;
    private ImageView homeadd,offeradd;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filepath,downloadUrl;
    private String imagename;
    private String activity,susses ="not";
    private int limitoffers,limithome;
    Loadingadapter loadingadapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public offer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment offer.
     */
    // TODO: Rename and change types and number of parameters
    public static offer newInstance(String param1, String param2) {
        offer fragment = new offer();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        loadingadapter = new Loadingadapter(getActivity());
        db = FirebaseFirestore.getInstance();
        homeadd = view.findViewById(R.id.homeadd);
        homeoffer = view.findViewById(R.id.homeoffer);
        offeradd = view.findViewById(R.id.offeradd);
        offersre = view.findViewById(R.id.offeraddrecy);

        Query query = db.collection("homescreenbanner");
        Query query1 = db.collection("offerscreenbanner");
        FirestoreRecyclerOptions<Bannermodalclass> op = new FirestoreRecyclerOptions.Builder<Bannermodalclass>().setQuery(query,Bannermodalclass.class).build();
        FirestoreRecyclerOptions<Bannermodalclass> op1 = new FirestoreRecyclerOptions.Builder<Bannermodalclass>().setQuery(query1,Bannermodalclass.class).build();
        adapter1 = new FirestoreRecyclerAdapter<Bannermodalclass, offerviewholder>(op1) {
            @NonNull
            @Override
            public offerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offerbanners,parent,false);
                return new offerviewholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull offerviewholder kholder, int position, @NonNull final Bannermodalclass models) {
                Glide.with(getActivity()).asBitmap().load(models.getImageurl()).into(kholder.homebanner);
                kholder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingadapter.startloading();
                        imagename = models.getImagename();
                        StorageReference desertRef = mStorageRef.child("offerscreenbanner/" +imagename);
                        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.collection("offerscreenbanner").document(imagename).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingadapter.finishloading();
                                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

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

            }

            @Override
            public int getItemCount() {
                limitoffers = super.getItemCount();
                return super.getItemCount();
            }
        };
        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(),8,GridLayoutManager.VERTICAL,false);
        offersre.setHasFixedSize(true);
        offersre.setLayoutManager(gridLayout);
        offersre.setAdapter(adapter1);


        adapter = new FirestoreRecyclerAdapter<Bannermodalclass, homebannerviewholder>(op) {
            @NonNull
            @Override
            public homebannerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeofferbanners,parent,false);
                return new homebannerviewholder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull homebannerviewholder holder, int position, @NonNull final Bannermodalclass model) {
                Glide.with(getActivity()).asBitmap().load(model.getImageurl()).into(holder.homebanner);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingadapter.startloading();
                        imagename = model.getImagename();
                        StorageReference desertRef = mStorageRef.child("homescreenbanner/" +imagename);
                        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                db.collection("homescreenbanner").document(imagename).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loadingadapter.finishloading();
                                        Toast.makeText(getActivity(), "Succesfully deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                    }
                });

            }

            @Override
            public int getItemCount() {
                limithome = super.getItemCount();
                return super.getItemCount();
            }
        };

        GridLayoutManager gridLayout1 = new GridLayoutManager(getActivity(),4,GridLayoutManager.VERTICAL,false);
        homeoffer.setHasFixedSize(true);
        homeoffer.setLayoutManager(gridLayout1);
        homeoffer.setAdapter(adapter);
        offeradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (limitoffers == 8){
                    Toast.makeText(getActivity(), "Banners are full", Toast.LENGTH_SHORT).show();
                }
                else {
                    getimage();
                    activity = "offer";
                }
            }
        });

        homeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (limithome == 4){
                    Toast.makeText(getActivity(), "Banners are full", Toast.LENGTH_SHORT).show();
                }
                else {
                    getimage();
                    activity = "home";

                }
            }
        });

        return view;
    }

    private void getimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==
                RESULT_OK
                && data != null && data.getData() != null) {
            loadingadapter.startloading();
            filepath = data.getData();
            final String random = UUID.randomUUID().toString();
            if (activity.equals("home")){
                final StorageReference riversRef = mStorageRef.child("homescreenbanner/" +random);
                riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri;
                                String downloadUrls = downloadUrl.toString();
                                String accesstoken = random;
                                Bannermodalclass upload = new  Bannermodalclass(downloadUrls,accesstoken);
                                db.collection("homescreenbanner").document(accesstoken).set(upload)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                loadingadapter.finishloading();
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

            }
            if (activity.equals("offer")){

                final StorageReference riversRef = mStorageRef.child("offerscreenbanner/" +random);
                riversRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadUrl = uri;
                                String downloadUrls = downloadUrl.toString();
                                String accesstoken = random;
                                Bannermodalclass upload = new  Bannermodalclass(downloadUrls,accesstoken);
                                db.collection("offerscreenbanner").document(accesstoken).set(upload)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                loadingadapter.finishloading();
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
            }



        }
    }


    private class homebannerviewholder extends RecyclerView.ViewHolder {
        ImageView homebanner;
        Button delete;
        public homebannerviewholder(@NonNull View itemView) {
            super(itemView);
            homebanner = itemView.findViewById(R.id.offerimagead);
            delete =itemView.findViewById(R.id.homeofferdelet);
        }
    }
    private class offerviewholder extends RecyclerView.ViewHolder {
        ImageView homebanner;
        Button delete;
        public offerviewholder(@NonNull View itemView) {
            super(itemView);
            homebanner = itemView.findViewById(R.id.offerimagead);
            delete =itemView.findViewById(R.id.homeofferdelet);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter1.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
        adapter1.startListening();
    }


}