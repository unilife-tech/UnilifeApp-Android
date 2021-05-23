
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAchievement implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("certificate_name")
    @Expose
    private String certificateName;
    @SerializedName("offered_by")
    @Expose
    private String offeredBy;
    @SerializedName("offered_date")
    @Expose
    private String offeredDate;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    protected UserAchievement(Parcel in) {
        id = in.readString();
        userId = in.readString();
        certificateName = in.readString();
        offeredBy = in.readString();
        offeredDate = in.readString();
        duration = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<UserAchievement> CREATOR = new Creator<UserAchievement>() {
        @Override
        public UserAchievement createFromParcel(Parcel in) {
            return new UserAchievement(in);
        }

        @Override
        public UserAchievement[] newArray(int size) {
            return new UserAchievement[size];
        }
    };

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

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getOfferedBy() {
        return offeredBy;
    }

    public void setOfferedBy(String offeredBy) {
        this.offeredBy = offeredBy;
    }

    public String getOfferedDate() {
        return offeredDate;
    }

    public void setOfferedDate(String offeredDate) {
        this.offeredDate = offeredDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(certificateName);
        parcel.writeString(offeredBy);
        parcel.writeString(offeredDate);
        parcel.writeString(duration);
        parcel.writeString(createdAt);
    }
}
