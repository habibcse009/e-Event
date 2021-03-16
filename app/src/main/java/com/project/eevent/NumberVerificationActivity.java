package com.project.eevent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NumberVerificationActivity extends AppCompatActivity {
    EditText extOTP;
    Button btnSubmitOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_verification);

        getSupportActionBar().setTitle("OTP Verification");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        extOTP = findViewById(R.id.etx_otp_write);
        btnSubmitOTP = findViewById(R.id.txt_submit_OTP);

    }
}