package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuestHomePage extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView donorlist;
    private DatabaseReference MuserDatabase;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home_page);

        MuserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        MuserDatabase.keepSynced(true);
        toolbar = findViewById(R.id.GuestToolbarID);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.backicon);
        getSupportActionBar().setTitle("Donor SBB");


        donorlist = findViewById(R.id.DonorListListID);
        donorlist.setHasFixedSize(true);
        donorlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        final FirebaseRecyclerAdapter<card_modal, GuserHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<card_modal, GuserHolder>(
                card_modal.class,
                R.layout.homebanner_layout,
                GuserHolder.class,
                MuserDatabase
        ) {
            @Override
            protected void populateViewHolder(final GuserHolder guserHolder, final card_modal card_modal, int i) {

                final String UID = getRef(i).getKey();
                MuserDatabase.child(UID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){

                            if(dataSnapshot.hasChild("address")){
                                String addressget = dataSnapshot.child("address").getValue().toString();
                                guserHolder.locationset(addressget);
                            }

                            if(dataSnapshot.hasChild("age")){
                                String ageget = dataSnapshot.child("age").getValue().toString();
                                guserHolder.ageset(ageget);
                            }

                            if(dataSnapshot.hasChild("blood")){
                                String bloodget = dataSnapshot.child("blood").getValue().toString();
                                guserHolder.setbloodgroupset(bloodget);
                            }

                            if(dataSnapshot.hasChild("gender")){
                                String genderget = dataSnapshot.child("gender").getValue().toString();
                                guserHolder.genderset(genderget);
                            }

                            if(dataSnapshot.hasChild("hight")){
                                String hightget = dataSnapshot.child("hight").getValue().toString();
                                guserHolder.heightset(hightget);
                            }

                            if(dataSnapshot.hasChild("name")){
                                String nameget = dataSnapshot.child("name").getValue().toString();
                                guserHolder.numberset(nameget);
                            }

                            if(dataSnapshot.hasChild("phone")){
                                String phoneget = dataSnapshot.child("phone").getValue().toString();
                                guserHolder.numberset(phoneget);
                            }

                            if(dataSnapshot.hasChild("profile_imageLink")){
                                String profile_imageLinkget = dataSnapshot.child("profile_imageLink").getValue().toString();
                                guserHolder.setprofileimageset(profile_imageLinkget);
                            }

                            animation = AnimationUtils.loadAnimation(guserHolder.context, R.anim.from_button);
                            guserHolder.Mview.setAnimation(animation);

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        donorlist.setAdapter(firebaseRecyclerAdapter);
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static class GuserHolder extends RecyclerView.ViewHolder{

        private TextView username;
        private TextView bloodgroup;
        private TextView location;
        private TextView number;
        private TextView age;
        private TextView gender;
        private TextView height;
        private Context context;
        private View Mview;
        private CircleImageView profileimage;

        public GuserHolder(@NonNull View itemView) {
            super(itemView);

            Mview = itemView;
            username = Mview.findViewById(R.id.UserNameIDIDID);
            bloodgroup = Mview.findViewById(R.id.BloodGroupID);
            location = Mview.findViewById(R.id.AddressIDID);
            number = Mview.findViewById(R.id.PhoneNumberID);
            age = Mview.findViewById(R.id.AgeID);
            gender = Mview.findViewById(R.id.GenderIDID);
            height = Mview.findViewById(R.id.HigehtIDId);
            context = Mview.getContext();
            profileimage = Mview.findViewById(R.id.ProfileImageIDIDID);
        }

        public void setprofileimageset(String img){
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).into(profileimage);
            Picasso.with(context).load(img).placeholder(R.drawable.defaltimage).networkPolicy(NetworkPolicy.OFFLINE).into(profileimage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }

        public void setusernameset(String nam){
            username.setText(nam);
        }

        public void setbloodgroupset(String blood){
            bloodgroup.setText(blood);
        }

        public void locationset(String loc){
            location.setText(loc);
        }

        public void numberset(String num){
            number.setText(num);
        }

        public void ageset(String a){
            age.setText(a);
        }

        public void genderset(String gen){
            gender.setText(gen);
        }

        public void heightset(String hei){
            height.setText(hei);
        }
    }
}


