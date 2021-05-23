package unilife.com.unilife.chat.model;

public class ChatDetailRequest {

    String sender_id;
    String receiver_id;
    String group_id;
    String room_id;

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }
}
