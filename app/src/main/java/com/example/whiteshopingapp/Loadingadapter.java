package com.example.whiteshopingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingadapter {


    Activity activity;
    AlertDialog alertDialog;
    public Loadingadapter(Activity activity) {
        this.activity = activity;
    }
    void startloading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingscreen,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();
    }
    void finishloading(){
        alertDialog.dismiss();
    }

}
