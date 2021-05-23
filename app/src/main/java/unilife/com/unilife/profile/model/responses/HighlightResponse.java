package unilife.com.unilife.profile.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HighlightResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("currently_working")
        @Expose
        private String currentlyWorking;
        @SerializedName("currently_studying")
        @Expose
        private String currentlyStudying;
        @SerializedName("graduated_from")
        @Expose
        private String graduatedFrom;
        @SerializedName("complete_highschool_at")
        @Expose
        private String completeHighschoolAt;
        @SerializedName("lives_in")
        @Expose
        private String livesIn;
        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("personal_information")
        @Expose
        private String personalInformation;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCurrentlyWorking() {
            return currentlyWorking;
        }

        public void setCurrentlyWorking(String currentlyWorking) {
            this.currentlyWorking = currentlyWorking;
        }

        public String getCurrentlyStudying() {
            return currentlyStudying;
        }

        public void setCurrentlyStudying(String currentlyStudying) {
            this.currentlyStudying = currentlyStudying;
        }

        public String getGraduatedFrom() {
            return graduatedFrom;
        }

        public void setGraduatedFrom(String graduatedFrom) {
            this.graduatedFrom = graduatedFrom;
        }

        public String getCompleteHighschoolAt() {
            return completeHighschoolAt;
        }

        public void setCompleteHighschoolAt(String completeHighschoolAt) {
            this.completeHighschoolAt = completeHighschoolAt;
        }

        public String getLivesIn() {
            return livesIn;
        }

        public void setLivesIn(String livesIn) {
            this.livesIn = livesIn;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getPersonalInformation() {
            return personalInformation;
        }

        public void setPersonalInformation(String personalInformation) {
            this.personalInformation = personalInformation;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

}
