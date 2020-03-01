package com.example.sbb;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Card extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference MuserDatabase;
    private FloatingActionButton requestbutton;
    private Animation animation;

    public Card() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);
        recyclerView = view.findViewById(R.id.CardViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestbutton = view.findViewById(R.id.RrquestButtonID);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.frombuttondonner);
        requestbutton.setAnimation(animation);
        requestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RequestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onStart() {

        FirebaseRecyclerAdapter<card_modal, CardHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<card_modal, CardHolder>(
                card_modal.class,
                R.layout.homebanner_layout,
                CardHolder.class,
                MuserDatabase

        ) {
            @Override
            protected void populateViewHolder(final CardHolder cardHolder, card_modal card_modal, int i) {

                final String UID = getRef(i).getKey();

                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("address")){
                                String addressget = dataSnapshot.child("address").getValue().toString();
                                cardHolder.setaddresss(addressget);
                            }
                            if(dataSnapshot.hasChild("blood")){
                                String bloodget = dataSnapshot.child("blood").getValue().toString();
                                cardHolder.setbloodgroup(bloodget);
                            }
                            if(dataSnapshot.hasChild("gender")){
                                String genderget = dataSnapshot.child("gender").getValue().toString();
                                cardHolder.setgetbder(genderget);
                            }
                            if(dataSnapshot.hasChild("hight")){
                                String hightget = dataSnapshot.child("hight").getValue().toString();
                                cardHolder.sethihgt(hightget);
                            }
                            if(dataSnapshot.hasChild("name")){
                                String namege = dataSnapshot.child("name").getValue().toString();
                                cardHolder.setname(namege);
                            }
                            if(dataSnapshot.hasChild("phone")){
                                String phonege = dataSnapshot.child("phone").getValue().toString();
                                cardHolder.setphonenumbers(phonege);
                            }
                            if(dataSnapshot.hasChild("profile_imageLink")){
                                String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                cardHolder.setprofileiamegst(profile_imageLinkget);
                            }

                            animation = AnimationUtils.loadAnimation(cardHolder.context, R.anim.from_button);
                            cardHolder.Mview.setAnimation(animation);

                        }
                        else {

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    public static class CardHolder extends RecyclerView.ViewHolder{

        private View Mview;
        private TextView username, bloodgroup, address, phonenumber, age, gender, hight;
        private CircleImageView profileimage;
        private Context context;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            username = Mview.findViewById(R.id.UserNameIDIDID);
            bloodgroup = Mview.findViewById(R.id.BloodGroupID);
            address = Mview.findViewById(R.id.AddressIDID);
            phonenumber = Mview.findViewById(R.id.PhoneNumberID);
            age = Mview.findViewById(R.id.AgeID);
            gender  =Mview.findViewById(R.id.GenderIDID);
            hight = Mview.findViewById(R.id.HigehtIDId);
            profileimage = Mview.findViewById(R.id.ProfileImageIDIDID);
            context = Mview.getContext();
        }

        public void setname(String name){
            username.setText(name);
        }
        public void setbloodgroup(String blood){
            bloodgroup.setText(blood);
        }
        public void setaddresss(String add){
            address.setText(add);
        }
        public void setphonenumbers(String phn){
            phonenumber.setText(phn);
        }
        public void setagest(String ag){
            age.setText(ag);
        }
        public void setgetbder(String gen){
            gender.setText(gen);
        }
        public void sethihgt(String hig){
            hight.setText(hig);
        }
        public void setprofileiamegst(String image){
            Picasso.with(context).load(image).placeholder(R.drawable.defaltimage).into(profileimage);
            Picasso.with(context).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(profileimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
