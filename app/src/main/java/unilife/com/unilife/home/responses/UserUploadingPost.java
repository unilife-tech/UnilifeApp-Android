
package unilife.com.unilife.home.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUploadingPost implements Parcelable {

    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    protected UserUploadingPost(Parcel in) {
        profileImage = in.readString();
        username = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<UserUploadingPost> CREATOR = new Creator<UserUploadingPost>() {
        @Override
        public UserUploadingPost createFromParcel(Parcel in) {
            return new UserUploadingPost(in);
        }

        @Override
        public UserUploadingPost[] newArray(int size) {
            return new UserUploadingPost[size];
        }
    };

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        parcel.writeString(profileImage);
        parcel.writeString(username);
        parcel.writeString(createdAt);
    }
}
