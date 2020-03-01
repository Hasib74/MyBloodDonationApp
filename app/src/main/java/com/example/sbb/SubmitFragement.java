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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragement extends Fragment {

    private SmileRating cheackingprocess, communactionservices, OverAll;
    private String SmaillCheackinprocess = "", Communaction="", OverllServices;
    private DatabaseReference MFeedbackDatabase;
    private FirebaseAuth Mauth;
    private String CurrentUserID;

    public SubmitFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit_fragement, container, false);
        MFeedbackDatabase = FirebaseDatabase.getInstance().getReference().child("Feedback");
        Mauth = FirebaseAuth.getInstance();
        CurrentUserID = Mauth.getCurrentUser().getUid();

        cheackingprocess = view.findViewById(R.id.CheakingprocessIDSmile);
        cheackingprocess.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {

                switch (smiley){

                    case SmileRating.TERRIBLE:
                        SmaillCheackinprocess = "Terrible";
                        updatingFeedback();
                        break;

                    case SmileRating.BAD:
                        SmaillCheackinprocess="Bad";
                        updatingFeedback();
                        break;

                    case SmileRating.OKAY:
                        SmaillCheackinprocess="Okay";
                        updatingFeedback();
                        break;

                    case SmileRating.GOOD:
                        SmaillCheackinprocess="Good";
                        updatingFeedback();
                        break;

                    case SmileRating.GREAT:
                        SmaillCheackinprocess="Great";
                        updatingFeedback();
                        break;


                }
            }
        });

        communactionservices = view.findViewById(R.id.CommunactionServicesSmill);
        communactionservices.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley){
                    case SmileRating.TERRIBLE:
                        Communaction = "Terrible";
                        updatingFeedback();
                        break;

                    case SmileRating.BAD:
                        Communaction="Bad";
                        updatingFeedback();
                        break;

                    case SmileRating.OKAY:
                        Communaction="Okay";
                        updatingFeedback();
                        break;

                    case SmileRating.GOOD:
                        Communaction="Good";
                        updatingFeedback();
                        break;

                    case SmileRating.GREAT:
                        Communaction="Great";
                        updatingFeedback();
                        break;


                }
            }
        });

        OverAll = view.findViewById(R.id.OverAllSmill);
        OverAll.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                switch (smiley){
                    case SmileRating.TERRIBLE:
                        OverllServices = "Terrible";
                        updatingFeedback();
                        break;

                    case SmileRating.BAD:
                        OverllServices="Bad";
                        updatingFeedback();
                        break;

                    case SmileRating.OKAY:
                        OverllServices="Okay";
                        updatingFeedback();
                        break;

                    case SmileRating.GOOD:
                        OverllServices="Good";
                        updatingFeedback();
                        break;

                    case SmileRating.GREAT:
                        OverllServices="Great";
                        updatingFeedback();
                        break;


                }
            }
        });

        return view;




    }


   private void updatingFeedback(){

       Map feedbackmap = new HashMap();
       feedbackmap.put("Cheakingprocess", SmaillCheackinprocess);
       feedbackmap.put("Communaction", Communaction);
       feedbackmap.put("OverllServices", OverllServices);

       MFeedbackDatabase.child(CurrentUserID).updateChildren(feedbackmap)
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
