package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView addimage;
    private Uri imageuri;
    private CircleImageView profileimage;
    private TextInputLayout name,phone, address, hight, idno, age;
    private FloatingActionButton updatebutton;
    private RadioGroup genderradiobutton, bloodradiobutton;
    private RadioButton radioButton;
    private DatabaseReference Muserdatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private StorageReference Mprofilestores;
    private ProgressBar Profileprogress;
    private String profileimagelink, gendertext="", bloodgrouptext="";
    private ProgressDialog Mprogress;

    private Button Aplus, Aminus, Bplus, Bminus, Oplus, Ominus, ABplus, ABminus;
    private String CurrentGroup="";
    private Button malebutton, femailbutton, othersbutton;
    private String CurrentGender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        malebutton = findViewById(R.id.MaleButtonID);
        femailbutton = findViewById(R.id.FemailButtonIDID);
        othersbutton = findViewById(R.id.OthersBuID);



        malebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGender="Male";
                malebutton.setBackgroundResource(R.drawable.blood_buttonclick);
                femailbutton.setBackgroundResource(R.drawable.blood_buttondesign);
                othersbutton.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        femailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGender="Female";
                malebutton.setBackgroundResource(R.drawable.blood_buttondesign);
                femailbutton.setBackgroundResource(R.drawable.blood_buttonclick);
                othersbutton.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        othersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGender="Other";
                malebutton.setBackgroundResource(R.drawable.blood_buttondesign);
                femailbutton.setBackgroundResource(R.drawable.blood_buttondesign);
                othersbutton.setBackgroundResource(R.drawable.blood_buttonclick);
            }
        });

        Aplus = findViewById(R.id.APlusID);
        Aminus = findViewById(R.id.AminusID);
        Bplus = findViewById(R.id.BplusID);
        Bminus = findViewById(R.id.BminusID);
        Oplus = findViewById(R.id.OplusID);
        Ominus = findViewById(R.id.OminusID);
        ABplus = findViewById(R.id.ABplusID);
        ABminus = findViewById(R.id.AbminusID);

        Aplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="A+";
                Aplus.setBackgroundResource(R.drawable.blood_buttonclick);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);

            }
        });

        Aminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="A-";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttonclick);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        Bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="B+";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttonclick);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });

        Bminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="B-";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttonclick);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        Oplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="O+";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttonclick);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        Ominus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="O-";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttonclick);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        ABplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="AB+";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttonclick);
                ABminus.setBackgroundResource(R.drawable.blood_buttondesign);
            }
        });
        ABminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentGroup="AB-";
                Aplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Aminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Bminus.setBackgroundResource(R.drawable.blood_buttondesign);
                Oplus.setBackgroundResource(R.drawable.blood_buttondesign);
                Ominus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABplus.setBackgroundResource(R.drawable.blood_buttondesign);
                ABminus.setBackgroundResource(R.drawable.blood_buttonclick);
            }
        });

        Mprogress = new ProgressDialog(ProfileActivity.this);
        Profileprogress = findViewById(R.id.ProfileProgressbarID);
        Profileprogress.setVisibility(View.INVISIBLE);
        Mprofilestores = FirebaseStorage.getInstance().getReference();

        Muserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        genderradiobutton = findViewById(R.id.GenderID);
        bloodradiobutton = findViewById(R.id.BloodID);
        age = findViewById(R.id.AgeIDID);




        addimage = findViewById(R.id.AddImageID);
        profileimage = findViewById(R.id.ProfileImageID);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfileActivity.this);
            }
        });

        name = findViewById(R.id.UsernameID);
        phone = findViewById(R.id.PhoneID);
        address = findViewById(R.id.AddressID);
        hight = findViewById(R.id.HightID);
        idno = findViewById(R.id.IDNO);
        updatebutton = findViewById(R.id.UpdateButtonID);

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nametext = name.getEditText().getText().toString();
                String phonetext = phone.getEditText().getText().toString();
                String addresstext = address.getEditText().getText().toString();
                String highttext = hight.getEditText().getText().toString();
                String idnotext = idno.getEditText().getText().toString();
                String agetext = age.getEditText().getText().toString();


                if(nametext.isEmpty()){
                    name.getEditText().setError("name empty");
                }
                if(agetext.isEmpty()){
                    age.getEditText().setText("age empty");
                }
                else if(phonetext.isEmpty()){
                    phone.getEditText().setError("phone empty");
                }
                else if(addresstext.isEmpty()){
                    address.getEditText().setError("address empty");
                }
                else if(highttext.isEmpty()){
                    hight.getEditText().setError("height empty");
                }
                else if(idnotext.isEmpty()){
                    idno.getEditText().setError("Id empty");
                }
                else if(CurrentGender.equals("")){
                    Toast.makeText(getApplicationContext(), "please select gender", Toast.LENGTH_LONG).show();
                }
                else if(CurrentGroup.equals("")){
                    Toast.makeText(getApplicationContext(), "please select your blood group", Toast.LENGTH_LONG).show();
                }
                else {
                    Mprogress.setTitle("Please wait ...");
                    Mprogress.setMessage("Please wait we are setup your profile");
                    Mprogress.setCanceledOnTouchOutside(false);
                    Mprogress.show();

                    Profileprogress.setVisibility(View.VISIBLE);

                    Map usermap = new HashMap();
                    usermap.put("name", nametext);
                    usermap.put("phone", phonetext);
                    usermap.put("address", addresstext);
                    usermap.put("hight", highttext);
                    usermap.put("idno", idnotext);
                    usermap.put("gender", CurrentGender);
                    usermap.put("blood", CurrentGroup);
                    usermap.put("profile_imageLink", profileimagelink);
                    usermap.put("devices_token", FirebaseInstanceId.getInstance().getToken().toString());
                    usermap.put("age", agetext);


                    Muserdatabase.child(CurrentUserID).updateChildren(usermap)
                           .addOnCompleteListener(new OnCompleteListener() {
                               @Override
                               public void onComplete(@NonNull Task task) {

                                   if(task.isSuccessful()){
                                       Mprogress.dismiss();
                                       Toast.makeText(getApplicationContext(), "setup success", Toast.LENGTH_LONG).show();
                                       Profileprogress.setVisibility(View.VISIBLE);
                                       Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                       startActivity(intent);
                                       finish();
                                   }
                                   else {
                                       Mprogress.dismiss();
                                       String errormessage = task.getException().getMessage().toString();
                                       Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                        Profileprogress.setVisibility(View.INVISIBLE);
                                   }

                               }
                           }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Mprogress.dismiss();
                            String errormessage = e.getMessage().toString();
                            Profileprogress.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                 imageuri = result.getUri();
                 profileimage.setImageURI(imageuri);

                StorageReference filepath = Mprofilestores.child("Profile").child(imageuri.getLastPathSegment());
                filepath.putFile(imageuri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                if(task.isSuccessful()){
                                     profileimagelink = task.getResult().getDownloadUrl().toString();
                                    Toast.makeText(getApplicationContext(), "profile pic is uploaded", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    String errormessage = task.getException().getMessage().toString();
                                    Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errormesage = e.getMessage().toString();
                        Toast.makeText(getApplicationContext(), errormesage, Toast.LENGTH_LONG).show();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
