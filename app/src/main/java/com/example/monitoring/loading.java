package com.example.monitoring;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

class loading {

    private Activity activity;
    private AlertDialog dialog;
    private ProgressBar proggress;

    loading(Activity myActivity){

        activity = myActivity;
    }
    void startloading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.proggress_dialog,null));


        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    void  dissmisdialog(){
        dialog.dismiss();;
    }
}
