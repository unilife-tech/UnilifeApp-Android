
package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserExperience implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("emp_type")
    @Expose
    private String empType;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    protected UserExperience(Parcel in) {
        id = in.readString();
        userId = in.readString();
        companyName = in.readString();
        empType = in.readString();
        industry = in.readString();
        designation = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        location = in.readString();
        createdAt = in.readString();
    }

    public static final Creator<UserExperience> CREATOR = new Creator<UserExperience>() {
        @Override
        public UserExperience createFromParcel(Parcel in) {
            return new UserExperience(in);
        }

        @Override
        public UserExperience[] newArray(int size) {
            return new UserExperience[size];
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

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        parcel.writeString(companyName);
        parcel.writeString(empType);
        parcel.writeString(industry);
        parcel.writeString(designation);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(location);
        parcel.writeString(createdAt);
    }
}
