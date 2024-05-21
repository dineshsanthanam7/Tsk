package com.example.tsk.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tsk.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import model.ChatMessageModel;
import utils.Firebaseutil;

public class ChatRecyclerAdaptor extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdaptor.ChatmodelViewHolder> {

    Context context;

    public ChatRecyclerAdaptor(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {

        super(options);
      //  Toast.makeText(context.getApplicationContext(), "layout",Toast.LENGTH_SHORT).show();
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatmodelViewHolder holder, int position, @NonNull ChatMessageModel model) {
       // Toast.makeText(context.getApplicationContext(), "layout___",Toast.LENGTH_SHORT).show();

        if (model.getSenderId().equals(Firebaseutil.currentUserId())) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatTextView.setText(model.getMessage());
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.leftChatTextView.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatmodelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Toast.makeText(context.getApplicationContext(), "layout",Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new ChatmodelViewHolder(view);
    }

    class ChatmodelViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatTextView, rightChatTextView;

        public ChatmodelViewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextView = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextView = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}
