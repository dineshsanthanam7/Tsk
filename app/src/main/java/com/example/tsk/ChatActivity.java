package com.example.tsk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import model.Usermodel;
import utils.Androidutil;

public class ChatActivity extends AppCompatActivity {
Usermodel otheruser;
EditText messageInput;
ImageButton sendmessage;
ImageButton backbtn;
TextView otherUsername;
RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        otheruser= Androidutil.getUserModelFromIntent(getIntent());
messageInput=findViewById(R.id.chat_msg_input);
sendmessage=findViewById(R.id.msg_send_button);
backbtn=findViewById(R.id.back_button);
otherUsername=findViewById(R.id.otherusername);
recyclerView=findViewById(R.id.chat_recycler_view);
backbtn.setOnClickListener(v -> {
    onBackPressed();
});
otherUsername.setText(otheruser.getUsername());
    }
}