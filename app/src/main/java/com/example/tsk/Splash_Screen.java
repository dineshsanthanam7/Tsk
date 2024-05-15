package com.example.tsk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;

import utils.Firebaseutil;

public class Splash_Screen extends AppCompatActivity {
TextView appname;
LottieAnimationView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    appname=findViewById(R.id.appname);
    logo=findViewById(R.id.animation_view);
    appname.animate().translationY(-1400).setDuration(2700).setStartDelay(0);
    logo.animate().translationX(2000).setDuration(2000).setStartDelay(2100);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            if(Firebaseutil.isloggedin()){
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);

            }
            else {
                Intent i = new Intent(getApplicationContext(), login.class);
                startActivity(i);
            }
            finish();
        }
    },5000);


    }
}