package model;


import com.google.firebase.Timestamp;

import java.util.List;

public class ChateoomModel {
    String chatroomId;
    List<String> userids;
    Timestamp lastMessageTimestamp;
    String lastMessageSenderId;
    String lastMessage;

    public ChateoomModel() {

    }

    public ChateoomModel(String chatroomId, List<String> userids, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.userids = userids;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public Timestamp getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
