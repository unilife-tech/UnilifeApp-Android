
package unilife.com.unilife.home.responses.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum {

    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

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

}
