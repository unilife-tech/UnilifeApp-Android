
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSocialProfile implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("snapchat")
    @Expose
    private String snapchat;

    public String getLinkedin() {
        return linkedIn;
    }

    public void setLinkedin(String linkedin) {
        this.linkedIn = linkedin;
    }

    @SerializedName("linkedIn")
    @Expose
    private String linkedIn;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    protected UserSocialProfile(Parcel in) {
        id = in.readString();
        userId = in.readString();
        facebook = in.readString();
        instagram = in.readString();
        snapchat = in.readString();
        twitter = in.readString();
        linkedIn = in.readString();
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(facebook);
        dest.writeString(instagram);
        dest.writeString(snapchat);
        dest.writeString(twitter);
        dest.writeString(linkedIn);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserSocialProfile> CREATOR = new Creator<UserSocialProfile>() {
        @Override
        public UserSocialProfile createFromParcel(Parcel in) {
            return new UserSocialProfile(in);
        }

        @Override
        public UserSocialProfile[] newArray(int size) {
            return new UserSocialProfile[size];
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}

