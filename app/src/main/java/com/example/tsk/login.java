package com.example.tsk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

public class login extends AppCompatActivity {
CountryCodePicker countryCodePicker;
EditText phoneinput;
Button sendotp;
ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        countryCodePicker = findViewById(R.id.countryCodeHolder);
        phoneinput=findViewById(R.id.mobilenumber);
progressBar=findViewById(R.id.progress_bar);
sendotp=findViewById(R.id.sendotp);
progressBar.setVisibility(View.GONE);
countryCodePicker.registerCarrierNumberEditText(phoneinput);
sendotp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!countryCodePicker.isValidFullNumber()){
            phoneinput.setError("invalid Phone number");
            return;
        }
        else{
            Intent intent=new Intent(login.this,login_otp.class);
            intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);

        }
    }
});


    }
}