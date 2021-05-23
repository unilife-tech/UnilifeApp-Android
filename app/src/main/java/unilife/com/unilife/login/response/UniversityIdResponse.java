package unilife.com.unilife.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversityIdResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("university_id")
    @Expose
    private String universityId;
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

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
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

}