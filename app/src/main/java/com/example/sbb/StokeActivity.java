package com.example.sbb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StokeActivity extends AppCompatActivity {

    private TextView abpositive;
    private DatabaseReference Muserdatabase;
    private TextView opositive, abnegative, bposttive, apositive, anegative, onegative, bnegative;
    private int Counter, abpostivecounter, abnegativecountr, bposttivecounter, bnegativecounter, onegativeCounter;
    private int anegativeCounter, opositiveCounter, apositiveCounter, opositiveCounterc;
    private TextView opostivetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoke);

        opostivetext = findViewById(R.id.opositiveId);
        abpositive = findViewById(R.id.abpositiveID);
        onegative = findViewById(R.id.onegativeID);
        opositive = findViewById(R.id.opositiveId);
        anegative = findViewById(R.id.anegativeID);
        apositive = findViewById(R.id.apositiveID);
        bnegative = findViewById(R.id.bnegativeID);
        bposttive = findViewById(R.id.bpostiveID);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Muserdatabase.keepSynced(true);

        Muserdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for(DataSnapshot data : dataSnapshot.getChildren())
                {

                  String val =  data.getValue().toString();

                  if(val.equals("O+")){

                      opositiveCounterc++;
                      opositive.setText("Total "+opositiveCounterc);
                  }

                  if(val.equals("AB+")){

                      abpostivecounter++;
                      abpositive.setText("Total "+abpostivecounter);


                  }
                  if(val.equals("A+")){
                      apositiveCounter++;
                    apositive.setText("Total "+apositiveCounter);
                  }
                  if(val.equals("O+")){
                      opositiveCounter++;
                      opositive.setText("Total "+opositiveCounter);
                  }
                  if(val.equals("B+")){
                      bposttivecounter++;
                        bposttive.setText("Total "+bposttivecounter);
                  }
                  if(val.equals("A-")){
                      anegativeCounter++;
                      anegative.setText("Total "+anegativeCounter);

                  }
                  if(val.equals("O-")){
                      onegativeCounter++;
                    onegative.setText("Total "+onegativeCounter);
                  }
                  if(val.equals("B-")){
                      bnegativecounter++;
                      bnegative.setText("Total "+bnegativecounter);

                  }
                  if(val.equals("AB-")){
                      abnegativecountr++;
                      abnegative.setText("Total "+abnegativecountr);
                  }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        abpositive = findViewById(R.id.abpositiveID);

    }
}
