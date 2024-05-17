package com.example.tsk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
ChatFragment chatFragment;
ProfileFragment profileFragment;
BottomNavigationView bottomNavigationView;
ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatFragment=new ChatFragment();
        profileFragment=new ProfileFragment();
        search= findViewById(R.id.searchbutton);
        bottomNavigationView=findViewById(R.id.bottom_navigator);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });
bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_chat){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,chatFragment).commit();
        }
        if(item.getItemId()==R.id.menu_profile){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).commit();
        }

        return true;
    }
});
bottomNavigationView.setSelectedItemId(R.id.menu_chat);


    }
}