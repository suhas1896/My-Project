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
    String phone_number;
    private EditText editText;
    public  String code_sent;
    private Button button;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent = getIntent();
        phone_number = intent.getStringExtra("Phone_number");
        button=findViewById(R.id.login);
        editText=findViewById(R.id.edit);
        String extphone = "+91" + phone_number;
        send_code (extphone);
        button.setOnClickListener(v -> {
            check_code();
        });

    }

    private void check_code() {
        String entered_otp = editText.getText().toString();
        if (entered_otp.isEmpty() || entered_otp.length() <6 ){
            Toast.makeText(OtpActivity.this, "Enter Proper Otp", Toast.LENGTH_SHORT).show();
            return;
        }
        finisheverything(entered_otp);
    }

    private void send_code(String extphone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(extphone,60, TimeUnit.SECONDS,this,mcallback);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                finisheverything(code);
            }
        }
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            code_sent= s;
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpActivity.this, "User Logged in Failed", Toast.LENGTH_SHORT).show();
        }
    };

    private void finisheverything(String code) {
        editText.setText(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(code_sent,code);
        sign_in(credential);
    }

    private void sign_in(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential).addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(OtpActivity.this,MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(OtpActivity.this, "User Logged in Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}