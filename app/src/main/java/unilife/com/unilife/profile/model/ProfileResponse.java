
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse implements Parcelable {

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

    protected ProfileResponse(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        ws = in.readString();
        message = in.readString();
    }

    public static final Creator<ProfileResponse> CREATOR = new Creator<ProfileResponse>() {
        @Override
        public ProfileResponse createFromParcel(Parcel in) {
            return new ProfileResponse(in);
        }

        @Override
        public ProfileResponse[] newArray(int size) {
            return new ProfileResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        parcel.writeString(ws);
        parcel.writeString(message);
    }
}
