package unilife.com.unilife.chat.update.groupresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupRoomResponse {

    @SerializedName("response")
    @Expose
    private Integer response;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("body")
    @Expose
    private Body body;

    public Integer getResponse() {
        return response;
    }

    public void setResponse(Integer response) {
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public class Body {

        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("sender_id")
        @Expose
        private String senderId;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getSenderId() {
            return senderId;
        }

        public void setSenderId(String senderId) {
            this.senderId = senderId;
        }

    }

    public class Data {

        @SerializedName("room_id")
        @Expose
        private String roomId;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

    }
}