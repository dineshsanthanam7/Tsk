package com.example.tsk.adaptor;

import static androidx.appcompat.widget.ResourceManagerInternal.get;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tsk.ChatActivity;
import com.example.tsk.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import model.ChateoomModel;
import model.Usermodel;
import utils.Androidutil;
import utils.Firebaseutil;

public class RecentChatRecyclerAdaptor extends FirestoreRecyclerAdapter<ChateoomModel, RecentChatRecyclerAdaptor.ChateoomModelViewHolder> {
    Context context;
    public RecentChatRecyclerAdaptor(@NonNull FirestoreRecyclerOptions<ChateoomModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChateoomModelViewHolder holder, int position, @NonNull ChateoomModel model) {
Firebaseutil.otherUserFromChatroom(model.getUserids()).get().addOnCompleteListener(task -> {
    if(task.isSuccessful()){
        boolean lastmessagesentbyme=model.getLastMessageSenderId().equals(Firebaseutil.currentUserId());

       Usermodel otherusermodel=task.getResult().toObject(Usermodel.class);
       holder.username.setText(otherusermodel.getUsername());
       if(lastmessagesentbyme) {
           holder.lastMessageText.setText("You : "+model.getLastMessage());
       }
       else{
           holder.lastMessageText.setText(model.getLastMessage());
       }
       holder.lastMessageTime.setText(Firebaseutil.timeSatmptoString(model.getLastMessageTimestamp()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, ChatActivity.class );
            Androidutil.passUserModelAsIntent(intent,otherusermodel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

});




    }

    @NonNull
    @Override
    public ChateoomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row,parent,false);


        return new ChateoomModelViewHolder(view);
    }

    class ChateoomModelViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView lastMessageText;
        TextView lastMessageTime;
        ImageView propic;

        public ChateoomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username_text);
            lastMessageText=itemView.findViewById(R.id.last_message_text);
            lastMessageTime=itemView.findViewById(R.id.last_message_time_text);
            propic=itemView.findViewById(R.id.profile_pic_view);
        }
    }
}
