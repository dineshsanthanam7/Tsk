package com.example.tsk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import model.Usermodel;
import utils.Firebaseutil;
public class login_user extends AppCompatActivity {
EditText usernameInput;
ProgressBar progressBar;
Button letmein;
String phonenumber;
Usermodel usermodel;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        usernameInput=findViewById(R.id.username);
        progressBar=findViewById(R.id.probar);
        letmein=findViewById(R.id.let_user_in);
        phonenumber=getIntent().getExtras().getString("phone");
        getUsername();

letmein.setOnClickListener(v -> {
    setUsername();

});



    }
    void setUsername(){
        setInProgress(true);
        String username=usernameInput.getText().toString();
        if(username.isEmpty() || username.length()<3){
            usernameInput.setError("username should be atleast 3 char");
            return;
        }
        setInProgress(true);
        if(usermodel!=null){
            usermodel.setUsername(username);
             }
        else{
            usermodel= new Usermodel(username,phonenumber, Timestamp.now(),Firebaseutil.currentUserId());

        }
        Firebaseutil.currentUserDetails().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful()){
    Intent intent = new Intent(login_user.this,MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);

}
            }
        });
    }
    void getUsername(){
setInProgress(true);
        Firebaseutil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                     usermodel=task.getResult().toObject(Usermodel.class);
                    if(usermodel!=null){
                        usernameInput.setText(usermodel.getUsername());

                    }
                }
            }
        });
    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            letmein.setVisibility(View.GONE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            letmein.setVisibility(View.VISIBLE);
        }
    }
}