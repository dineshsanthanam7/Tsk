package com.example.tsk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tsk.adaptor.SearchUserRecyclerAdaptor;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import model.Usermodel;
import utils.Firebaseutil;

public class SearchActivity extends AppCompatActivity {
EditText usernamesearch;
SearchUserRecyclerAdaptor adaptor;
ImageButton searchbtn;
ImageButton backbtn;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        usernamesearch=findViewById(R.id.search_username_input);
        searchbtn=findViewById(R.id.search_usr_btn);
        backbtn=findViewById(R.id.back_button);
        recyclerView=findViewById(R.id.search_usr_recyclerview);
        usernamesearch.requestFocus();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchname=usernamesearch.getText().toString();
                if(searchname==null || searchname.length()<3){
                    usernamesearch.setError("invalid username");
                    return;
                }
                setupRecyclerView(searchname);
            }
        });
    }
    void setupRecyclerView(String searchname){
        Query query = Firebaseutil.allCollectionReference().whereGreaterThanOrEqualTo("username",searchname);
       FirestoreRecyclerOptions<Usermodel> options=new FirestoreRecyclerOptions.Builder<Usermodel>()
               .setQuery(query, Usermodel.class).build();

        adaptor=new SearchUserRecyclerAdaptor(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptor);
        adaptor.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adaptor!=null){
            adaptor.stopListening();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }
}