package com.example.tsk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tsk.adaptor.RecentChatRecyclerAdaptor;
import com.example.tsk.adaptor.SearchUserRecyclerAdaptor;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import model.ChateoomModel;
import model.Usermodel;
import utils.Firebaseutil;


public class ChatFragment extends Fragment {
RecyclerView recyclerView;
RecentChatRecyclerAdaptor adaptor;


    public ChatFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        setupRecyclerView();

        return view;
    }
    void setupRecyclerView(){
        Query query = Firebaseutil.allChatroomCollectionReference().whereArrayContains("userids",Firebaseutil.currentUserId())
                .orderBy("lastMessageTimestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ChateoomModel> options=new FirestoreRecyclerOptions.Builder<ChateoomModel>()
                .setQuery(query, ChateoomModel.class).build();

        adaptor=new RecentChatRecyclerAdaptor(options,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }

    @Override
   public void onStop() {
        super.onStop();
        if(adaptor!=null){
            adaptor.stopListening();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }
}