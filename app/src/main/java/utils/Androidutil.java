package utils;

import android.content.Intent;

import model.Usermodel;

public class Androidutil {
    public static void passUserModelAsIntent(Intent intent, Usermodel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phonenumber",model.getPhonenumber());
        intent.putExtra("userId",model.getUserID());
    }
    public static Usermodel getUserModelFromIntent(Intent intent){
        Usermodel usermodel=new Usermodel();
        usermodel.setUsername(intent.getStringExtra("username"));
        usermodel.setPhonenumber(intent.getStringExtra("phonenumber"));
        usermodel.setUserID(intent.getStringExtra("userId"));
        return usermodel;
    }
}
