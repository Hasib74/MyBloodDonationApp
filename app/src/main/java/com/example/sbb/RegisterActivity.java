package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private Button haveaccount;
    private EditText REmail, RPassword, RCPassword;
    private Button RegisterButton;
    private FirebaseAuth mauth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        mauth = FirebaseAuth.getInstance();
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        haveaccount = findViewById(R.id.AlreAdyHaveAccountButtonID);
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        REmail = findViewById(R.id.RegisteEmailID);
        RPassword = findViewById(R.id.RegisterPassword);
        RCPassword = findViewById(R.id.RegisterCPassword);
        RegisterButton = findViewById(R.id.RegisterButtonID);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailtext = REmail.getText().toString();
                String passworstext = RPassword.getText().toString();
                String cpasword = RCPassword.getText().toString();

                if(emailtext.isEmpty()){
               //     progressDialog.dismiss();
                    REmail.setError("Email is empty");
                }
               else if(passworstext.isEmpty()){
                //   progressDialog.dismiss();
                    RPassword.setError("Password is empty");
                }
               else if(cpasword.isEmpty()){
               //    progressDialog.dismiss();
                    RCPassword.setError("Password is empty");
                }

               else if(!passworstext.equals(cpasword)){
               //    progressDialog.dismiss();
                    RPassword.setError("Password didn't match");
                    RCPassword.setError("Password didn't match");
                }

               else if(passworstext.length() >= 8){
               //    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Password minimum need 8 char", Toast.LENGTH_LONG).show();
                }
                else {

                    progressDialog.setTitle("Waiting for Registration");
                    progressDialog.setMessage("Please wait we are Registration your account thanks");
                    progressDialog.setCancelable(false);
                   progressDialog.show();


                    mauth.createUserWithEmailAndPassword(emailtext, passworstext)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();

                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        progressDialog.dismiss();
                                        String errormessage = task.getException().getMessage().toString();
                                        Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            String errormessage = e.getMessage().toString();
                            Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });


    }

    @Override
    protected void onStart() {

        FirebaseUser Muser = mauth.getCurrentUser();
        if(Muser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onStart();
    }
}
