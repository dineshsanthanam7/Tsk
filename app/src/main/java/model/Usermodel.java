package model;

import com.google.firebase.Timestamp;

public class Usermodel {

    private String username;
    private String phonenumber;
    private Timestamp createdTimestamp;
    private String userID;

    public Usermodel() {
    }

    public Usermodel(String username, String phonenumber, Timestamp createdTimestamp,String userID) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.createdTimestamp = createdTimestamp;
        this.userID=userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
