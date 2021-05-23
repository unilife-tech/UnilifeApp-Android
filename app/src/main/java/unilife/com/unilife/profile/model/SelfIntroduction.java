package unilife.com.unilife.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelfIntroduction implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("university_school_email")
    @Expose
    private String universitySchoolEmail;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("university_school_id")
    @Expose
    private String universitySchoolId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("organisation")
    @Expose
    private String organisation;
    @SerializedName("personal_mission")
    @Expose
    private String personalMission;
    @SerializedName("personal_description")
    @Expose
    private String personalDescription;
    @SerializedName("profile_banner_image")
    @Expose
    private String profileBannerImage;
    @SerializedName("unilife_user_name")
    @Expose
    private String unilifeUserName;
    @SerializedName("university_schools_name")
    @Expose
    private String universitySchoolsName;
    @SerializedName("profile_logo")
    @Expose
    private String profileLogo;

    protected SelfIntroduction(Parcel in) {
        id = in.readString();
        universitySchoolEmail = in.readString();
        username = in.readString();
        profileImage = in.readString();
        userType = in.readString();
        universitySchoolId = in.readString();
        designation = in.readString();
        organisation = in.readString();
        personalMission = in.readString();
        personalDescription = in.readString();
        profileBannerImage = in.readString();
        unilifeUserName = in.readString();
        universitySchoolsName = in.readString();
        profileLogo = in.readString();
    }

    public static final Creator<SelfIntroduction> CREATOR = new Creator<SelfIntroduction>() {
        @Override
        public SelfIntroduction createFromParcel(Parcel in) {
            return new SelfIntroduction(in);
        }

        @Override
        public SelfIntroduction[] newArray(int size) {
            return new SelfIntroduction[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniversitySchoolEmail() {
        return universitySchoolEmail;
    }

    public void setUniversitySchoolEmail(String universitySchoolEmail) {
        this.universitySchoolEmail = universitySchoolEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUniversitySchoolId() {
        return universitySchoolId;
    }

    public void setUniversitySchoolId(String universitySchoolId) {
        this.universitySchoolId = universitySchoolId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPersonalMission() {
        return personalMission;
    }

    public void setPersonalMission(String personalMission) {
        this.personalMission = personalMission;
    }

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personalDescription) {
        this.personalDescription = personalDescription;
    }

    public String getProfileBannerImage() {
        return profileBannerImage;
    }

    public void setProfileBannerImage(String profileBannerImage) {
        this.profileBannerImage = profileBannerImage;
    }

    public String getUnilifeUserName() {
        return unilifeUserName;
    }

    public void setUnilifeUserName(String unilifeUserName) {
        this.unilifeUserName = unilifeUserName;
    }

    public String getUniversitySchoolsName() {
        return universitySchoolsName;
    }

    public void setUniversitySchoolsName(String universitySchoolsName) {
        this.universitySchoolsName = universitySchoolsName;
    }

    public String getProfileLogo() {
        return profileLogo;
    }

    public void setProfileLogo(String profileLogo) {
        this.profileLogo = profileLogo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(universitySchoolEmail);
        parcel.writeString(username);
        parcel.writeString(profileImage);
        parcel.writeString(userType);
        parcel.writeString(universitySchoolId);
        parcel.writeString(designation);
        parcel.writeString(organisation);
        parcel.writeString(personalMission);
        parcel.writeString(personalDescription);
        parcel.writeString(profileBannerImage);
        parcel.writeString(unilifeUserName);
        parcel.writeString(universitySchoolsName);
        parcel.writeString(profileLogo);
    }
}