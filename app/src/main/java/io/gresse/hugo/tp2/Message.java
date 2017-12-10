package io.gresse.hugo.tp2;

/**
 * Represent a single message sended by an user
 *
 * Created by Hugo Gresse on 26/11/2017.
 */

public class Message {

    public String content;
    public String userName;
    public String userEmail;
    public Long timestamp;

    public Message() {
        // Empty constructor for Firebase
    }

    public Message(String content, String userName,String email, Long timestamp) {
        this.content = content;
        this.userName = userName;
        this.userEmail=email;
        this.timestamp = timestamp;
    }
}
