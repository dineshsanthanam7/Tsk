package com.example.tsk.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tsk.ChatActivity;
import com.example.tsk.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import model.Usermodel;
import utils.Androidutil;
import utils.Firebaseutil;

public class SearchUserRecyclerAdaptor extends FirestoreRecyclerAdapter<Usermodel, SearchUserRecyclerAdaptor.UsermodelViewHolder> {
   Context context;
    public SearchUserRecyclerAdaptor(@NonNull FirestoreRecyclerOptions<Usermodel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsermodelViewHolder holder, int position, @NonNull Usermodel model) {
        holder.username.setText(model.getUsername());
        holder.phonenumber.setText(model.getPhonenumber());
        if(model.getUserID().equals(Firebaseutil.currentUserId())){
            holder.username.setText(model.getUsername()+"(me)");
        }
holder.itemView.setOnClickListener(v -> {
    Intent intent=new Intent(context, ChatActivity.class );
    Androidutil.passUserModelAsIntent(intent,model);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
});
    }

    @NonNull
    @Override
    public UsermodelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.search_user_recycler_row,parent,false);


        return new UsermodelViewHolder(view);
    }

    class UsermodelViewHolder extends RecyclerView.ViewHolder{
TextView username;
TextView phonenumber;
ImageView propic;

        public UsermodelViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username_text);
            phonenumber=itemView.findViewById(R.id.userphone_number);
            propic=itemView.findViewById(R.id.profile_pic_view);
        }
    }
}
