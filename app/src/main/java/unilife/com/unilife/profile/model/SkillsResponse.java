
package unilife.com.unilife.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkillsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("respoonse")
    @Expose
    private Respoonse respoonse;
    @SerializedName("self_intoduction")
    @Expose
    private SelfIntroduction selfIntroduction;
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

    public Respoonse getRespoonse() {
        return respoonse;
    }

    public void setRespoonse(Respoonse respoonse) {
        this.respoonse = respoonse;
    }

    public SelfIntroduction getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(SelfIntroduction selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
