package com.example.tsk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

import model.ChateoomModel;
import model.Usermodel;
import utils.Androidutil;
import utils.Firebaseutil;

public class ChatActivity extends AppCompatActivity {
Usermodel otheruser;
String chatroomID;
ChateoomModel chateoomModel;
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
        chatroomID= Firebaseutil.getChatroomID(Firebaseutil.currentUserId(),otheruser.getUserID());
messageInput=findViewById(R.id.chat_msg_input);
sendmessage=findViewById(R.id.msg_send_button);
backbtn=findViewById(R.id.back_button);
otherUsername=findViewById(R.id.otherusername);
recyclerView=findViewById(R.id.chat_recycler_view);
backbtn.setOnClickListener(v -> {
    onBackPressed();
});
otherUsername.setText(otheruser.getUsername());
getOrCreateChatRoomModel();



    }
   void getOrCreateChatRoomModel(){
       Firebaseutil.getChatroomReference(chatroomID).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               chateoomModel=task.getResult().toObject(ChateoomModel.class);
if(chateoomModel==null){
    chateoomModel=new ChateoomModel(chatroomID, Arrays.asList(Firebaseutil.currentUserId(),otheruser.getUserID()),
            Timestamp.now(),"");
    Firebaseutil.getChatroomReference(chatroomID).set(chateoomModel);
}
           }
       });


   }
}