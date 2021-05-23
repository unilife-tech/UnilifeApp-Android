package unilife.com.unilife.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendListingResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ws")
    @Expose
    private String ws;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("friend_id")
        @Expose
        private String friendId;
        @SerializedName("group_id")
        @Expose
        private Object groupId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("user_university_school_email")
        @Expose
        private String userUniversitySchoolEmail;
        @SerializedName("user_username")
        @Expose
        private String userUsername;
        @SerializedName("user_profile_image")
        @Expose
        private Object userProfileImage;
        @SerializedName("group_name")
        @Expose
        private String groupName;
        @SerializedName("group_image")
        @Expose
        private String groupImage;

        private boolean selected=false;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFriendId() {
            return friendId;
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }

        public Object getGroupId() {
            return groupId;
        }

        public void setGroupId(Object groupId) {
            this.groupId = groupId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserUniversitySchoolEmail() {
            return userUniversitySchoolEmail;
        }

        public void setUserUniversitySchoolEmail(String userUniversitySchoolEmail) {
            this.userUniversitySchoolEmail = userUniversitySchoolEmail;
        }

        public String getUserUsername() {
            return userUsername;
        }

        public void setUserUsername(String userUsername) {
            this.userUsername = userUsername;
        }

        public Object getUserProfileImage() {
            return userProfileImage;
        }

        public void setUserProfileImage(Object userProfileImage) {
            this.userProfileImage = userProfileImage;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
            this.groupImage = groupImage;
        }
    }
}