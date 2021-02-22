package com.example.sharp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    String authcredential;
    private EditText editText;
    private Button button;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent = getIntent();
        authcredential = intent.getStringExtra("Authcredentials");
        button=findViewById(R.id.login);
        editText=findViewById(R.id.edit);
        button.setOnClickListener(v -> {
            String otp = editText.getText().toString();
            if (otp.isEmpty() || otp.length() < 6) {

                Toast.makeText(this, "Enter Proper Otp", Toast.LENGTH_SHORT).show();
            }else {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(authcredential, otp);
                signInWithPhoneAuthCredential(credential);
            }
            });
        }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(OtpActivity.this, "User Logged in Failed", Toast.LENGTH_SHORT).show();
                            }

                    }
                });
    }

    }