package com.example.sbb;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

    private SmileRating DonnerCommunatn, DonnerSyste;
    private String DonnerComunaction="", DonnerSysteam="";
    private DatabaseReference MFeedbackDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MFeedbackDatabase = FirebaseDatabase.getInstance().getReference().child("Feedback");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        DonnerCommunatn = view.findViewById(R.id.DonnerCommunationSmail);
        DonnerSyste = view.findViewById(R.id.DonationSysteam);
        DonnerCommunatn.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley){

                    case SmileRating.TERRIBLE:
                        DonnerComunaction = "Terrible";
                        updatingFeedback();
                        break;

                    case SmileRating.BAD:
                        DonnerComunaction="Bad";
                        updatingFeedback();
                        break;

                    case SmileRating.OKAY:
                        DonnerComunaction="Okay";
                        updatingFeedback();
                        break;

                    case SmileRating.GOOD:
                        DonnerComunaction="Good";
                        updatingFeedback();
                        break;

                    case SmileRating.GREAT:
                        DonnerComunaction="Great";
                        updatingFeedback();
                        break;


                }
            }
        });

        DonnerSyste.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley){
                    case SmileRating.TERRIBLE:
                        DonnerSysteam = "Terrible";
                        updatingFeedback();
                        break;

                    case SmileRating.BAD:
                        DonnerSysteam="Bad";
                        updatingFeedback();
                        break;

                    case SmileRating.OKAY:
                        DonnerSysteam="Okay";
                        updatingFeedback();
                        break;

                    case SmileRating.GOOD:
                        DonnerSysteam="Good";
                        updatingFeedback();
                        break;

                    case SmileRating.GREAT:
                        DonnerSysteam="Great";
                        updatingFeedback();
                        break;


                }
            }
        });

        return view;
    }

    private void updatingFeedback(){

        Map feedmap = new HashMap();
        feedmap.put("DonnerComunaction", DonnerComunaction);
        feedmap.put("DonnerSysteam", DonnerSysteam);


       MFeedbackDatabase.child(CurrentUserID).updateChildren(feedmap)
               .addOnCompleteListener(new OnCompleteListener() {
                   @Override
                   public void onComplete(@NonNull Task task) {

                       if(task.isSuccessful()){

                       }
                       else {
                           Toast.makeText(getContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
           }
       });

    }
}
