package com.example.sbb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button submitbutton;
    private ImageView imageView;
    private TextView errortext;
    private FirebaseAuth Mauth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        imageView = findViewById(R.id.SendMessageImageID);
        errortext = findViewById(R.id.ErrorTextID);
        Mauth = FirebaseAuth.getInstance();
        imageView.setVisibility(View.INVISIBLE);
        errortext.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.ProgressID);
        progressBar.setVisibility(View.INVISIBLE);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        email = findViewById(R.id.FEmailID);
        submitbutton = findViewById(R.id.SubmitButtonID);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtext = email.getText().toString();
                if(emailtext.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Please enter your email address", Toast.LENGTH_LONG).show();
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                    Mauth.sendPasswordResetEmail(emailtext)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        imageView.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        email.setText("");
                                        progressBar.setVisibility(View.INVISIBLE);
                                        imageView.setVisibility(View.INVISIBLE);
                                        String errormessage = task.getException().getMessage().toString();
                                        Toast.makeText(getApplicationContext(), errormessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.INVISIBLE);
                            email.setText("");
                            String  error = e.getMessage().toString();
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
