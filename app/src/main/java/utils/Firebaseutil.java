package utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class Firebaseutil {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();

    }
    public static boolean isloggedin(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }

    public static DocumentReference currentUserDetails(){

        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference allCollectionReference(){
return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getChatroomReference(String chatroomID){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomID);

    }
    public static  CollectionReference getChatRoomMessageReference(String ChatroomID){
        return getChatroomReference(ChatroomID).collection("chats");
    }
    public static String getChatroomID(String userId1, String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }
        else{
            return userId2+"_"+userId1;
        }
    }
    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public static DocumentReference otherUserFromChatroom(List<String> userids){
        if(userids.get(0).equals(currentUserId())){
          return   allCollectionReference().document(userids.get(1));
        }
        else{
            return   allCollectionReference().document(userids.get(0));
        }


    }
    public static String timeSatmptoString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
}
