package com.example.sharp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            editText = findViewById(R.id.edit);
            button = findViewById(R.id.button);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        editText = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            String phone = editText.getText().toString();
            if (phone.isEmpty() || phone.length() < 10) {
                Toast.makeText(this, "Enter Proper phone Number", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intent = new Intent(PhoneActivity.this, OtpActivity.class);
                intent.putExtra("Phone_number", phone);
                startActivity(intent);
            }
        });
    }
}