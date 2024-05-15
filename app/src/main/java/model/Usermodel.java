package model;

import com.google.firebase.Timestamp;

public class Usermodel {

    private String username;
    private String phonenumber;
    private Timestamp createdTimestamp;

    public Usermodel() {
    }

    public Usermodel(String username, String phonenumber, Timestamp createdTimestamp) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.createdTimestamp = createdTimestamp;
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
}
