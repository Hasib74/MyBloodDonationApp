package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton updatebutton;
    private CircleImageView profileimage;
    private TextView IDNO;
    private DatabaseReference Muserdatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private RadioGroup gendergroup, radioGroupblood;
    private RadioButton radioButtongender, radioButtonblood;
    private String gendertext, bloodtext;
    private TextInputLayout username, phonenumber, address, age, height;
    private ProgressBar mprogress;
    private RadioButton male, femail, others;

    private Button aplus, aminus, bplus, bminus, oplus, ominus, abplus, abminus;
    private String CurrentBloodClikc="";
    private Button malebutton, femailbutton, otherbutton;
    private String GenderString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);

        malebutton = findViewById(R.id.PmailID);
        femailbutton = findViewById(R.id.FemailButtonID);
        otherbutton = findViewById(R.id.OthersButtonID);

        malebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderString = "Male";
                malebutton.setBackgroundResource(R.drawable.blood_buttonclick);
                femailbutton.setBackgroundResource(R.drawable.blood_buttondesign);
                otherbutton.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        femailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderString = "Female";
                malebutton.setBackgroundResource(R.drawable.blood_buttondesign);
                femailbutton.setBackgroundResource(R.drawable.blood_buttonclick);
                otherbutton.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        otherbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderString = "Other";
                malebutton.setBackgroundResource(R.drawable.blood_buttondesign);
                femailbutton.setBackgroundResource(R.drawable.blood_buttondesign);
                otherbutton.setBackgroundResource(R.drawable.blood_buttonclick);
            }
        });

        aplus = findViewById(R.id.Aplus);
        aminus = findViewById(R.id.Aminus);
        bplus = findViewById(R.id.Bplus);
        bminus = findViewById(R.id.Bminus);
        oplus = findViewById(R.id.Oplus);
        ominus = findViewById(R.id.Ominus);
        abplus = findViewById(R.id.ABplus);
        abminus = findViewById(R.id.Abminus);

        aplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="A+";
                aplus.setBackgroundResource(R.drawable.blood_buttonclick);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        aminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="A-";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttonclick);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="B+";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttonclick);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        bminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="B-";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttonclick);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        oplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="O+";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttonclick);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        ominus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="O-";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttonclick);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        abplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="AB+";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttonclick);
                abminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        abminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentBloodClikc="AB-";
                aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                abplus.setBackgroundResource(R.drawable.blood_buttondesign);
                abminus.setBackgroundResource(R.drawable.blood_buttonclick);
            }
        });

        mprogress = findViewById(R.id.MyprogressID);
        mprogress.setVisibility(View.INVISIBLE);
        male = findViewById(R.id.MaleID);
        femail = findViewById(R.id.FemailID);
        others = findViewById(R.id.OthersID);


        username = findViewById(R.id.RUsernameID);
        phonenumber = findViewById(R.id.RPhoneID);
        address = findViewById(R.id.RAddressID);
        age = findViewById(R.id.RageID);
        height = findViewById(R.id.RHightID);




        toolbar = findViewById(R.id.EditProfileID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("My Profile");

        updatebutton = findViewById(R.id.UpdateID);
        profileimage = findViewById(R.id.ProfileImageEditID);
        IDNO = findViewById(R.id.IDNOID);


        Mauth = FirebaseAuth.getInstance();
        Muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Muserdatabase.keepSynced(true);
        CurrentUserID = Mauth.getCurrentUser().getUid();


        gendergroup = findViewById(R.id.RGenderID);
        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int genderposition = gendergroup.getCheckedRadioButtonId();
                radioButtongender = findViewById(genderposition);

                gendertext = radioButtongender.getText().toString();


            }
        });


        Muserdatabase.child(CurrentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("profile_imageLink")){
                        String profileimageget = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(getApplicationContext()).load(profileimageget).placeholder(R.drawable.defaltimage).into(profileimage);
                        Picasso.with(getApplicationContext()).load(profileimageget).networkPolicy(NetworkPolicy.OFFLINE).into(profileimage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                    if(dataSnapshot.hasChild("idno")){
                        String idnoget = dataSnapshot.child("idno").getValue().toString();
                        IDNO.setText(idnoget);
                    }

                    if(dataSnapshot.hasChild("address")){
                        String addressget = dataSnapshot.child("address").getValue().toString();
                        address.getEditText().setText(addressget);
                    }
                    if(dataSnapshot.hasChild("hight")){
                        String hightget = dataSnapshot.child("hight").getValue().toString();
                        height.getEditText().setText(hightget);
                    }
                    if(dataSnapshot.hasChild("name")){
                        String nameget = dataSnapshot.child("name").getValue().toString();
                        username.getEditText().setText(nameget);
                    }
                    if(dataSnapshot.hasChild("phone")){
                        String phoneget = dataSnapshot.child("phone").getValue().toString();
                        phonenumber.getEditText().setText(phoneget);
                    }
                    if(dataSnapshot.hasChild("age")){
                        String ageget = dataSnapshot.child("age").getValue().toString();
                        age.getEditText().setText(ageget);
                    }
                    if(dataSnapshot.hasChild("blood")){

                        String bloodget = dataSnapshot.child("blood").getValue().toString();
                        if(bloodget.equals("B+")){
                            bplus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if(bloodget.equals("AB+")){
                            abplus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if (bloodget.equals("A+")){
                            aplus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if (bloodget.equals("O+")){
                            oplus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if(bloodget.equals("A-")){
                            aminus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if(bloodget.equals("O-")){
                            ominus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if(bloodget.equals("B-")){
                            bminus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                       else if(bloodget.equals("AB-")){
                            abminus.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                    }

                    if(dataSnapshot.hasChild("gender")){
                        String genderget = dataSnapshot.child("gender").getValue().toString();

                        if(genderget.equals("Male")){
                            malebutton.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                        else if(genderget.equals("Female")){
                            femailbutton.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                        else if(genderget.equals("Others")){
                            otherbutton.setBackgroundResource(R.drawable.blood_buttonclick);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updating_profile();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updating_profile(){



        String usernametext = username.getEditText().getText().toString();
        String phonenumbertext = phonenumber.getEditText().getText().toString();
        String addresstext = address.getEditText().getText().toString();
        String agetext = age.getEditText().getText().toString();
        String heighttext = height.getEditText().getText().toString();

        if(usernametext.isEmpty()){
            username.getEditText().setError("Username require");
        }

        else if(CurrentBloodClikc.equals("")){
            Toast.makeText(getApplicationContext(), "Please select your blood group", Toast.LENGTH_LONG).show();
        }

        else if(phonenumbertext.isEmpty()){
            phonenumber.getEditText().setError("Phone number require");
        }
        else if(addresstext.isEmpty()){
            address.getEditText().setError("Address require");
        }
        else if(agetext.isEmpty()){
            age.getEditText().setError("Age require");
        }
        else if(heighttext.isEmpty()){
            height.getEditText().setError("Height require");
        }

        else if(GenderString.equals("")){
            Toast.makeText(getApplicationContext(), "Select your gender", Toast.LENGTH_LONG).show();
        }
        else {

            mprogress.setVisibility(View.VISIBLE);

            Map updatemap = new HashMap();

            updatemap.put("name", usernametext);
            updatemap.put("phone", phonenumbertext);
            updatemap.put("address", addresstext);
            updatemap.put("hight", heighttext);
            updatemap.put("gender", GenderString);
            updatemap.put("blood", CurrentBloodClikc);
            updatemap.put("age", agetext);

            Muserdatabase.child(CurrentUserID).updateChildren(updatemap)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if(task.isSuccessful()){
                                mprogress.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Profile Is updated", Toast.LENGTH_LONG).show();
                            }
                            else {
                                String errormessage = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                mprogress.setVisibility(View.INVISIBLE);
                            }


                        }
                    });
        }

    }

}
