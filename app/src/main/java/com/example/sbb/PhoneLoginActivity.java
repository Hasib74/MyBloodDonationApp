package com.example.sbb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class PhoneLoginActivity extends AppCompatActivity {

    private EditText phonecode;
    private EditText phonenumber;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){

            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        phonecode = findViewById(R.id.CountryCodeTextIDID);
        phonenumber = findViewById(R.id.NumberID);

        loginbutton = findViewById(R.id.PhoneLoginID);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                String phonecodetext = phonecode.getText().toString().trim();
                String phonenumbertext = phonenumber.getText().toString().trim();

                if(phonecodetext.isEmpty()){
                    phonecode.setError("Phone Number Code Require");
                }
                else if(phonenumbertext.isEmpty()){
                    phonenumber.setError("Phone Number Require");
                }

                else {
                    String NUMBER = phonecodetext + phonenumbertext;
                    Intent intent = new Intent(getApplicationContext(), PhoneCodeActivity.class);
                    intent.putExtra("NUMBER", NUMBER);
                    startActivity(intent);
                }

            }
        });

    }
}
