package com.example.sbb;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sbb.mail.Post;
import com.example.sbb.mail.Postt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class DonarFragment extends Fragment {

    private EditText issuinput;
    private EditText commentsinput;
    private EditText email;
    private FloatingActionButton sendbutton;
    private DatabaseReference MFeedbackDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;
    private String issutext, commentstext, emailtext;
    private ProgressDialog Mprogress;

    private String Cheakingprocessget, Communactionget, DonnerComunactionget, DonnerSysteamget, OverllServicesget;

    public DonarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        Mprogress = new ProgressDialog(getContext());

        MFeedbackDatabase = FirebaseDatabase.getInstance().getReference().child("Feedback");
        MFeedbackDatabase.child(CurrentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    if(dataSnapshot.hasChild("Cheakingprocess")){
                         Cheakingprocessget = dataSnapshot.child("Cheakingprocess").getValue().toString();
                    }
                    if(dataSnapshot.hasChild("Communaction")){
                         Communactionget = dataSnapshot.child("Communaction").getValue().toString();
                    }
                    if(dataSnapshot.hasChild("DonnerComunaction")){
                         DonnerComunactionget = dataSnapshot.child("DonnerComunaction").getValue().toString();
                    }
                    if(dataSnapshot.hasChild("DonnerSysteam")){
                         DonnerSysteamget = dataSnapshot.child("DonnerSysteam").getValue().toString();
                    }
                    if(dataSnapshot.hasChild("OverllServices")){
                         OverllServicesget = dataSnapshot.child("OverllServices").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_donar, container, false);
        issuinput = view.findViewById(R.id.IssuInputID);
        commentsinput = view.findViewById(R.id.CommentsInputID);
        email = view.findViewById(R.id.EmailAddressID);
        sendbutton = view.findViewById(R.id.SendButtonID);


        /////send email
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 issutext = issuinput.getText().toString();
                 commentstext = commentsinput.getText().toString();
                 emailtext = email.getText().toString();



                if(issutext.isEmpty()){
                    issuinput.setError("setup your in info");
                }
                else if(commentstext.isEmpty()){
                    commentsinput.setError("setup yor commends");
                }
                else if(emailtext.isEmpty()){
                    email.setError("email must require");
                }
                else {
                    sendingemail();
                }


            }
        });


        return view;
    }

    private void sendingemail() {

        Mprogress.setTitle("Thanks For Feedback");
        Mprogress.setMessage("If you found any issue of our app the developer solve problems soon thanks for your information");
        Mprogress.setCanceledOnTouchOutside(false);
        Mprogress.show();

        final String Message = "Email :"+"\n\n"+emailtext+"\n\n"+"ISSUE/ERROR :"+issutext+"\n\n"+"COMMENTS :"+commentstext+"\n\n"+"Checkingprocess :"+Cheakingprocessget+"\n\n"+"Communication :"+Communactionget+"\n\n"+"Donner Communication :"+DonnerComunactionget+"\n\n"+"Donner System :"+DonnerSysteamget+"\n\n"+"OverallServices :"+OverllServicesget+"\n\n";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hsmailapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /////send email

        Post sendMailAPIClint = retrofit.create(Post.class);


        Postt postt = new Postt("mangrovehotel4@gmail.com", "Mangrovehotel2019", "sajibroy206@gmail.com", "FeedBack From SBB", Message);

        Call<Post> call = sendMailAPIClint.createPost(postt);




        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "server not responds", Toast.LENGTH_LONG).show();
                    Mprogress.dismiss();
                }
                else {
                    Toast.makeText(getContext(), "send success", Toast.LENGTH_LONG).show();
                    Mprogress.dismiss();
                    Mprogress.dismiss();
                    email.setText("");
                    issuinput.setText("");
                    commentsinput.setText("");


                    MFeedbackDatabase.child(CurrentUserID)
                            .removeValue();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Mprogress.dismiss();

                email.setText("");
                issuinput.setText("");
                commentsinput.setText("");

                Toast.makeText(getContext(), "send success", Toast.LENGTH_LONG).show();

                MFeedbackDatabase.child(CurrentUserID)
                        .removeValue();
            }
        });


    }
}
