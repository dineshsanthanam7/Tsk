package com.example.tsk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class login_otp extends AppCompatActivity {
String phonenumber;
Long timeoutsec=60L;
String verificationcode;
PhoneAuthProvider.ForceResendingToken ResendingToken;
EditText otpinput;
TextView resendotp;
ProgressBar progressBar;
Button nxtbutn;
FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
    phonenumber=getIntent().getExtras().getString("phone");
    otpinput=findViewById(R.id.otpnumber);
    nxtbutn=findViewById(R.id.next);
    progressBar=findViewById(R.id.pro);
    resendotp=findViewById(R.id.resendotptxt);
    sendOtp(phonenumber,false);
    nxtbutn.setOnClickListener(v -> {
        String enteredotp=otpinput.getText().toString();
      PhoneAuthCredential credential=  PhoneAuthProvider.getCredential(verificationcode,enteredotp);
signIn(credential);
setInProgress(true);


    });
    resendotp.setOnClickListener(v -> {
        sendOtp(phonenumber,true);

    });





        
        
    }
void sendOtp(String phonenumber,boolean isResend){
        setresendtimer();
        setInProgress(true);
    PhoneAuthOptions.Builder builder=PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phonenumber)
            .setTimeout(timeoutsec, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    signIn(phoneAuthCredential);
                    setInProgress(false);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(getApplicationContext(),"OTP Verification failed",Toast.LENGTH_SHORT).show();
                    setInProgress(false);
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verificationcode =s;
                    ResendingToken=forceResendingToken;
                    Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_SHORT).show();
                    setInProgress(false);
                }
            });
    if(isResend){
        PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(ResendingToken).build());
    }
    else{
        PhoneAuthProvider.verifyPhoneNumber(builder.build());
    }
}
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            nxtbutn.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            nxtbutn.setVisibility(View.VISIBLE);
        }
    }
    void signIn(PhoneAuthCredential phoneAuthCredential){
setInProgress(true);
mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        setInProgress(false);
        if(task.isSuccessful()){
Intent intent = new Intent(login_otp.this, login_user.class);
intent.putExtra("phone",phonenumber);
startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Otp verification failed",Toast.LENGTH_SHORT).show();

        }
    }
});
    }
    void setresendtimer(){
        resendotp.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutsec--;
                resendotp.setText("resend OTP in "+ timeoutsec+"sec");
if(timeoutsec<=0){
    timeoutsec=60L;
    timer.cancel();
    runOnUiThread(() -> {
        resendotp.setEnabled(true);
    });
}
            }
        },0,1000);

    }
}