package unilife.com.unilife.chat.update.chatresponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstantResponse {

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
    private String thumb;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
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
    @SerializedName("receiver_user_chat")
    @Expose
    private ReceiverUserChat receiverUserChat;
    @SerializedName("chat_slide")
    @Expose
    private ChatSlide chatSlide;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
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

    public ReceiverUserChat getReceiverUserChat() {
        return receiverUserChat;
    }

    public void setReceiverUserChat(ReceiverUserChat receiverUserChat) {
        this.receiverUserChat = receiverUserChat;
    }

    public ChatSlide getChatSlide() {
        return chatSlide;
    }

    public void setChatSlide(ChatSlide chatSlide) {
        this.chatSlide = chatSlide;
    }

    public class ChatSlide {

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
        private String thumb;
        @SerializedName("filepath")
        @Expose
        private String filepath;
        @SerializedName("sender_id")
        @Expose
        private String senderId;
        @SerializedName("receiver_id")
        @Expose
        private String receiverId;
        @SerializedName("group_id")
        @Expose
        private String groupId;
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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
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
    }

    public class ReceiverUserChat {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

    public class SenderUserChat {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

    }

}