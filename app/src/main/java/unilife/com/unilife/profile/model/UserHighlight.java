
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserHighlight implements Parcelable {

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

    protected UserHighlight(Parcel in) {
        id = in.readString();
        userId = in.readString();
        currentlyWorking = in.readString();
        currentlyStudying = in.readString();
        graduatedFrom = in.readString();
        completeHighschoolAt = in.readString();
        livesIn = in.readString();
        from = in.readString();
        personalInformation = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<UserHighlight> CREATOR = new Creator<UserHighlight>() {
        @Override
        public UserHighlight createFromParcel(Parcel in) {
            return new UserHighlight(in);
        }

        @Override
        public UserHighlight[] newArray(int size) {
            return new UserHighlight[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(currentlyWorking);
        parcel.writeString(currentlyStudying);
        parcel.writeString(graduatedFrom);
        parcel.writeString(completeHighschoolAt);
        parcel.writeString(livesIn);
        parcel.writeString(from);
        parcel.writeString(personalInformation);
        parcel.writeString(createdAt);
    }
}
