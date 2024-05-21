package com.example.tsk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsk.adaptor.ChatRecyclerAdaptor;
import com.example.tsk.adaptor.SearchUserRecyclerAdaptor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

import model.ChatMessageModel;
import model.ChateoomModel;
import model.Usermodel;
import utils.Androidutil;
import utils.Firebaseutil;

public class ChatActivity extends AppCompatActivity {
Usermodel otheruser;
String chatroomID;
ChateoomModel chateoomModel;
EditText messageInput;
ChatRecyclerAdaptor adaptor;
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

sendmessage.setOnClickListener(v -> {
    String message = messageInput.getText().toString().trim();
    if(message.isEmpty()){
        return ;
    }
    sendMessageToUser(message);

});

getOrCreateChatRoomModel();
setupChatRecyclerView();




    }
    void setupChatRecyclerView(){

        Query query = Firebaseutil.getChatRoomMessageReference(chatroomID).orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChatMessageModel> options=new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();

        adaptor=new ChatRecyclerAdaptor(options,getApplicationContext());
        LinearLayoutManager manager= new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();
        adaptor.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });


        }

    void sendMessageToUser(String message){
        chateoomModel.setLastMessageSenderId(Firebaseutil.currentUserId());
        chateoomModel.setLastMessageTimestamp(Timestamp.now());
        Firebaseutil.getChatroomReference(chatroomID).set(chateoomModel);

        ChatMessageModel chatMessageModel=new ChatMessageModel(message,Firebaseutil.currentUserId(),Timestamp.now());
Firebaseutil.getChatRoomMessageReference(chatroomID).add(chatMessageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
    @Override
    public void onComplete(@NonNull Task<DocumentReference> task) {
        if(task.isSuccessful()){
            messageInput.setText("");
        }
    }
});
    }


   void getOrCreateChatRoomModel(){
       // Toast.makeText(getApplicationContext(), "getup",Toast.LENGTH_SHORT).show();
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