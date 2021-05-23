
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEducation implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("college_name")
    @Expose
    private String collegeName;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("concentration")
    @Expose
    private String concentration;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("club_society")
    @Expose
    private String clubSociety;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    protected UserEducation(Parcel in) {
        id = in.readString();
        userId = in.readString();
        collegeName = in.readString();
        concentration = in.readString();
        degree = in.readString();
        clubSociety = in.readString();
        grade = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(collegeName);
        dest.writeString(concentration);
        dest.writeString(degree);
        dest.writeString(clubSociety);
        dest.writeString(grade);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserEducation> CREATOR = new Creator<UserEducation>() {
        @Override
        public UserEducation createFromParcel(Parcel in) {
            return new UserEducation(in);
        }

        @Override
        public UserEducation[] newArray(int size) {
            return new UserEducation[size];
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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getClubSociety() {
        return clubSociety;
    }

    public void setClubSociety(String clubSociety) {
        this.clubSociety = clubSociety;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
