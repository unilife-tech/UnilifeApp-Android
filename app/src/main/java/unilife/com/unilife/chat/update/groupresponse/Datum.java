
package unilife.com.unilife.chat.update.groupresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("thumb")
    @Expose
    private Object thumb;
    @SerializedName("filepath")
    @Expose
    private Object filepath;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private Object receiverId;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("seen")
    @Expose
    private String seen;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("delete_chat_id")
    @Expose
    private String deleteChatId;
    @SerializedName("message_type")
    @Expose
    private String messageType;
    @SerializedName("only_date")
    @Expose
    private String onlyDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("sender_user_chat")
    @Expose
    private SenderUserChat senderUserChat;
    @SerializedName("chat_slide")
    @Expose
    private Object chatSlide;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getThumb() {
        return thumb;
    }

    public void setThumb(Object thumb) {
        this.thumb = thumb;
    }

    public Object getFilepath() {
        return filepath;
    }

    public void setFilepath(Object filepath) {
        this.filepath = filepath;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Object getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Object receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDeleteChatId() {
        return deleteChatId;
    }

    public void setDeleteChatId(String deleteChatId) {
        this.deleteChatId = deleteChatId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(String onlyDate) {
        this.onlyDate = onlyDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SenderUserChat getSenderUserChat() {
        return senderUserChat;
    }

    public void setSenderUserChat(SenderUserChat senderUserChat) {
        this.senderUserChat = senderUserChat;
    }

    public Object getChatSlide() {
        return chatSlide;
    }

    public void setChatSlide(Object chatSlide) {
        this.chatSlide = chatSlide;
    }

}
