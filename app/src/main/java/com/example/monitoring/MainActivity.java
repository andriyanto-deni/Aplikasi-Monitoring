package com.example.monitoring;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity   {

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    SwitchCompat switchCompat;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference Data1;
    DatabaseReference Data2;
    DatabaseReference Data3;
    DatabaseReference Data4;


    TextView data1;
    TextView data2;
    TextView dataswitch;
    View refresh;
    View detailsuhu;
    View detailkelembapan;
    View detailled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        //loading loading = new loading(MainActivity.this);

        Data1 = firebaseDatabase.getReference("Data/Suhu");
        Data2 = firebaseDatabase.getReference("Data/Kelembapan");
        Data3 = firebaseDatabase.getReference("Data/Led");
        Data4 = firebaseDatabase.getReference("Data/Led");

        data1 = findViewById(R.id.nilaisuhu);
        data2 = findViewById(R.id.nilaikelembapan);
        switchCompat = findViewById(R.id.tombol);
        dataswitch = findViewById(R.id.dataswitch);



        refresh = findViewById(R.id.refresh);
        progressBar = findViewById(R.id.proggres);
        detailsuhu = findViewById(R.id.viewsuhu);
        detailkelembapan = findViewById(R.id.viewkelembapan);
        detailled = findViewById(R.id.viewdetailled);



        detailsuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView suhu = (TextView) findViewById(R.id.nilaisuhu);
                String nilaisuhu = suhu.getText().toString();
                gauge_fragment dialogFragment=new gauge_fragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();

                Bundle data = new Bundle();
                data.putString("mydata",nilaisuhu);
                data.putString("mydata1","Suhu");
                dialogFragment.setArguments(data);
                dialogFragment.show(getSupportFragmentManager(),"My  Fragment");

            }
        });
        detailkelembapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView kelembapan = (TextView) findViewById(R.id.nilaikelembapan);
                String nilaikelembapan = kelembapan.getText().toString();
                gauge_fragment dialogFragment=new gauge_fragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();

                Bundle data = new Bundle();
                data.putString("mydata",nilaikelembapan);
                data.putString("mydata1","Kelembapan");
                dialogFragment.setArguments(data);
                dialogFragment.show(getSupportFragmentManager(),"My  Fragment");

            }
        });

        sakelar();

        getdata();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.proggress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                //loading.startloading();
                getdata();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        //loading.dissmisdialog();
                    }
                }, 800);
            }

        });
    }

    private void getdata() {

        Data1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                data1.setText(value);
                String a = data1.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data1.", Toast.LENGTH_SHORT).show();
            }
        });

        Data2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                data2.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data2.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sakelar(){
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    Data3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Data3.setValue(true);

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Fail to set data3.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    Data3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Data3.setValue(false);

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Fail to set data3.", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
