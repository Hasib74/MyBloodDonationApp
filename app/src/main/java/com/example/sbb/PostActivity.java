package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseReference MuserDatabase, MpostDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUser;

    private CircleImageView imageView;
    private TextView name;

    private EditText postdetails, hospitalname, locationname;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String bloodselected = "";
    private ProgressDialog Mprogress;
    private String nameget;
    private String profile_imageLinkget;

    private Button apositive, anegative, bpositive, bnegative, opositive, onegative, abpositive, abnegative;
    private String ClickBlood = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        hospitalname = findViewById(R.id.HospitalName);
        locationname = findViewById(R.id.LocationName);
        Mprogress = new ProgressDialog(PostActivity.this);
        toolbar = findViewById(R.id.PostToolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("Add A Post");
        MpostDatabase = FirebaseDatabase.getInstance().getReference().child("Post");

        apositive = findViewById(R.id.AposiyiveID);
        anegative = findViewById(R.id.AnegativeID);
        bpositive = findViewById(R.id.BPositiveID);
        bnegative = findViewById(R.id.BNegativeID);
        opositive = findViewById(R.id.OPositiveID);
        onegative = findViewById(R.id.ONegativeID);
        abpositive = findViewById(R.id.ABPositiveID);
        abnegative = findViewById(R.id.ABNegativeID);

        apositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="A+";
                apositive.setBackgroundResource(R.drawable.blood_buttonclick);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        anegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="A-";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttonclick);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        bpositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="B+";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttonclick);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        bnegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="B-";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttonclick);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        opositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="O+";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttonclick);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        onegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="O-";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttonclick);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        abpositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="AB+";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttonclick);
                abnegative.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        abnegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickBlood="AB-";
                apositive.setBackgroundResource(R.drawable.blood_buttondesign);
                anegative.setBackgroundResource(R.drawable.blood_buttondesign);
                bpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                bnegative.setBackgroundResource(R.drawable.blood_buttondesign);
                opositive.setBackgroundResource(R.drawable.blood_buttondesign);
                onegative.setBackgroundResource(R.drawable.blood_buttondesign);
                abpositive.setBackgroundResource(R.drawable.blood_buttondesign);
                abnegative.setBackgroundResource(R.drawable.blood_buttonclick);
            }
        });


        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Mauth = FirebaseAuth.getInstance();
        CurrentUser = Mauth.getCurrentUser().getUid();

        imageView = findViewById(R.id.postprofileimage);
        name = findViewById(R.id.nameID);



        MuserDatabase.child(CurrentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("profile_imageLink")){
                        String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(getApplicationContext()).load(profile_imageLinkget).placeholder(R.drawable.defaltimage).into(imageView);
                    }
                    if(dataSnapshot.hasChild("name")){
                         nameget = dataSnapshot.child("name").getValue().toString();
                        name.setText("Hey I am "+nameget);
                    }
                    if(dataSnapshot.hasChild("profile_imageLink")){
                        profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        postdetails = findViewById(R.id.PostMessageID);
        radioGroup = findViewById(R.id.groupone);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);

                 bloodselected = radioButton.getText().toString();


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        if(item.getItemId() == R.id.UpdatePostID){
            SetupUpdatePost();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.postupdate, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void SetupUpdatePost(){

        Mprogress.setTitle("Please wait ...");
        Mprogress.setMessage("Your Post is sending");
        Mprogress.setCanceledOnTouchOutside(false);
        Mprogress.show();

        String postApplication = postdetails.getText().toString();
        String hospitalnametext = hospitalname.getText().toString();
        String locationmame = locationname.getText().toString();

        if(postApplication.isEmpty()){
            Mprogress.dismiss();
            postdetails.setError("Application Require");
        }
        else if(ClickBlood==""){
            Mprogress.dismiss();
            Toast.makeText(getApplicationContext(), "Blood Group Not Selected", Toast.LENGTH_LONG).show();
        }
        else {

            Calendar calendartime = Calendar.getInstance();
            SimpleDateFormat simpleDateFormattime = new SimpleDateFormat("HH:mm:ss");

            String time = simpleDateFormattime.format(calendartime.getTime());

            Calendar calendardate = Calendar.getInstance();
            SimpleDateFormat simpleDateFormatdate = new SimpleDateFormat("dd-MM-yyyy");
            String date = simpleDateFormatdate.format(calendardate.getTime());

            Map postmap = new HashMap();
            postmap.put("time", time);
            postmap.put("date", date);
            postmap.put("application", postApplication);
            postmap.put("bloodgroup", ClickBlood);
            postmap.put("name", nameget);
            postmap.put("profileimage", profile_imageLinkget);
            postmap.put("hospitalname", hospitalnametext);
            postmap.put("locationname", locationmame);


            String UID = date+time;

            MpostDatabase.child(UID).updateChildren(postmap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){
                        Mprogress.dismiss();
                        Toast.makeText(getApplicationContext(), "Post is Send", Toast.LENGTH_LONG).show();
                        postdetails.setText("");
                    }
                    else {
                        Mprogress.dismiss();
                        String errormessage = task.getException().getMessage().toString();
                        Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
            });
        }


    }
}
