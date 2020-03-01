package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sbb.Model.Token;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth Mauth;
    private DatabaseReference Mdatabase;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabHolder tabHolder;
    private String Currentusers;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private String username, bloodgroup, address;
    private TextView navname, navbloodgroup, navaddress;
    private CircleImageView navimage;
    private TextView totalcard;
    private DatabaseReference malllist;
    private Long list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fcm_token();

        malllist = FirebaseDatabase.getInstance().getReference().child("Users");
        malllist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list = dataSnapshot.getChildrenCount();
                totalcard = findViewById(R.id.TotalCardID);
                totalcard.setText(list.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        drawerLayout = findViewById(R.id.DeawerID);
        navigationView = findViewById(R.id.NavagationID);

        View mview = navigationView.inflateHeaderView(R.layout.headerlayout);
        navname = mview.findViewById(R.id.name);
        navbloodgroup = mview.findViewById(R.id.BloodGroupp);
        navaddress = mview.findViewById(R.id.Addressnav);
        navimage = mview.findViewById(R.id.profileID);


        Mauth = FirebaseAuth.getInstance();
        Currentusers = Mauth.getCurrentUser().getUid();
        Mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Mdatabase.keepSynced(true);

        Mdatabase.child(Currentusers).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("name")){
                        username = dataSnapshot.child("name").getValue().toString();
                        navname.setText(username);
                    }
                    if(dataSnapshot.hasChild("blood")){
                        bloodgroup = dataSnapshot.child("blood").getValue().toString();
                        navbloodgroup.setText(bloodgroup);
                    }
                    if(dataSnapshot.hasChild("profile_imageLink")){
                        String profileimageuri = dataSnapshot.child("profile_imageLink").getValue().toString();
                        Picasso.with(HomeActivity.this).load(profileimageuri).placeholder(R.drawable.defaltimage).into(navimage);
                        Picasso.with(HomeActivity.this).load(profileimageuri).networkPolicy(NetworkPolicy.OFFLINE).into(navimage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                    }
                    if(dataSnapshot.hasChild("idno")){
                        address = dataSnapshot.child("idno").getValue().toString();
                        navaddress.setText(address);
                    }
                }
                else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.StokeID)
                {
                    menuItem.setChecked(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intent = new Intent(getApplicationContext(), StokeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.GlobalChatID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intent = new Intent(getApplicationContext(), GlobalChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.FeedBackID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intent = new Intent(getApplicationContext(), FeedbckAvtivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.LogoutID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);;
                    Intent intent  = new Intent(getApplicationContext(), Login_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Mauth.signOut();
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.ProfileID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);;
                    Intent intent = new Intent(getApplicationContext(), EditProfile_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                if(menuItem.getItemId() == R.id.dashboardID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);;

                    Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.NextPlayingID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    drawerLayout.closeDrawer(Gravity.START);

                    Intent intent = new Intent(getApplicationContext(), NextPlaningActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.ShareID){
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);

                    drawerLayout.closeDrawer(Gravity.START);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");

                    String  shareMessage = "https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID +"\n\n";

                    String sharebody = shareMessage;
                    String sharesubject = "Hey"+"\n\n"+sharebody+"\n";
                    intent.putExtra(Intent.EXTRA_TEXT, sharesubject);
                    //  intent.putExtra(Intent.EXTRA_SUBJECT, sharebody);
                    startActivity(Intent.createChooser(intent, "share out Application"));
                }

                if(menuItem.getItemId() == R.id.BloodListID){
                    drawerLayout.closeDrawer(Gravity.START);
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    Intent intent  = new Intent(getApplicationContext(), MyRquestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.MyRequestID){
                    drawerLayout.closeDrawer(Gravity.START);
                    menuItem.setCheckable(true);
                    menuItem.setCheckable(true);
                    Intent intent = new Intent(getApplicationContext(), Request_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                return true;
            }
        });



        toolbar = findViewById(R.id.HomeToolbarID);
        tabLayout = findViewById(R.id.HometabID);
        viewPager = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SBB");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

         tabHolder = new TabHolder(getSupportFragmentManager());
         viewPager.setAdapter(tabHolder);
         tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.postrequest, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(Gravity.START);
        }

        if(item.getItemId() == R.id.RequestIDID){
            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void fcm_token() {



        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        //  if (refreshedToken!="")





        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference referance=db.getReference("Token");
        Token token=new Token(refreshedToken,true);
        referance.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);


    }

    @Override
    protected void onStart() {

        FirebaseUser Muser = Mauth.getCurrentUser();
        if(Muser == null){
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else {

            Mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild(Currentusers)){
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        super.onStart();
    }

    @Override
    public void onBackPressed() {


        androidx.appcompat.app.AlertDialog.Builder Mbuilder = new androidx.appcompat.app.AlertDialog.Builder(HomeActivity.this);

        Mbuilder.setIcon(R.drawable.help);
        Mbuilder.setTitle("Thank You");
        Mbuilder.setMessage("Thanks using our application Please give us your Suggestions  Feedback and Share our application");

        Mbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Mbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        Mbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        androidx.appcompat.app.AlertDialog alertDialog = Mbuilder.create();
        alertDialog.show();
    }
}
