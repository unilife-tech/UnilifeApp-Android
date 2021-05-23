package unilife.com.unilife.chat.update.model;

public class SendMsgRequest {
    String sender_id;
    String message;
    String receiver_id;

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }
}
