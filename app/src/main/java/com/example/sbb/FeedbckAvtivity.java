package com.example.sbb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class FeedbckAvtivity extends AppCompatActivity {

    private ViewPager viewPager;
    private FeedbackPager_Adapter feedbackPager_adapter;
    private TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbck_avtivity);


        viewPager = findViewById(R.id.ViewPagerIDID);
        tableLayout = findViewById(R.id.TabLayoutUDUDU);
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        feedbackPager_adapter = new FeedbackPager_Adapter(getSupportFragmentManager());


        viewPager.setAdapter(feedbackPager_adapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}
