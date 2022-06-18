package com.example.monitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class gauge_fragment extends DialogFragment  {

    ProgressBar progressBar;
    TextView textView;
    TextView pengukuran;
    String mystr,mystr1,a;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.gauge_fragment,container,false);



        progressBar = view.findViewById(R.id.proggres);
        textView = view.findViewById(R.id.nilai);
        pengukuran = view.findViewById(R.id.pengukuran);
        Bundle data = getArguments();

        if (data != null){
            mystr = data.getString("mydata");
            mystr1 = data.getString("mydata1");
        }
        if(mystr1=="Suhu"){
            a="°C";
        }else {
            a="%";
        }
        pengukuran.setText(mystr+""+a);
        textView.setText(mystr1);
        int A = Integer.parseInt(mystr);
        progressBar.setProgress(A);
        progressBar.setMax(100);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button oke = view.findViewById(R.id.oke);

        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}








