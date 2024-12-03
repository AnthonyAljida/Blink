package edu.oakland.blink.models;

import java.security.Timestamp;
import java.util.List;

public class ChatroomModel {

    String chatroomId;
    List<String> userIds;
    Timestamp lastMessageTimestamp;
    String lastMessageSenderId;

    public ChatroomModel() {

    }

    public ChatroomModel(String chatroomId, List<String> userIds, Timestamp lastMessageTimestamp, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public void setLastMessageTimestamp(Timestamp lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
