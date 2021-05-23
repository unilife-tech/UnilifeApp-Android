package unilife.com.unilife.profile.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Respoonse {

    @SerializedName("user_highlights")
    @Expose
    private ArrayList<UserHighlight> userHighlights = null;
    @SerializedName("user_experience")
    @Expose
    private ArrayList<UserExperience> userExperience = null;
    @SerializedName("user_education")
    @Expose
    private ArrayList<UserEducation> userEducation = null;
    @SerializedName("user_skills")
    @Expose
    private ArrayList<UserSkill> userSkills = null;
    @SerializedName("user_languages")
    @Expose
    private ArrayList<UserLanguage> userLanguages = null;
    @SerializedName("user_achievements")
    @Expose
    private ArrayList<UserAchievement> userAchievements = null;
    @SerializedName("user_interest")
    @Expose
    private ArrayList<UserInterest> userInterest = null;

    @SerializedName("user_social_profile")
    @Expose
    private ArrayList<UserSocialProfile> userSocialProfile = null;
    @SerializedName("user_course")
    @Expose
    private ArrayList<UserCourse> userCourses = null;

    protected Respoonse(Parcel in) {
    }

    public ArrayList<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(ArrayList<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }

    public ArrayList<UserHighlight> getUserHighlights() {
        return userHighlights;
    }

    public void setUserHighlights(ArrayList<UserHighlight> userHighlights) {
        this.userHighlights = userHighlights;
    }

    public ArrayList<UserExperience> getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(ArrayList<UserExperience> userExperience) {
        this.userExperience = userExperience;
    }

    public ArrayList<UserEducation> getUserEducation() {
        return userEducation;
    }

    public void setUserEducation(ArrayList<UserEducation> userEducation) {
        this.userEducation = userEducation;
    }

    public ArrayList<UserSkill> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(ArrayList<UserSkill> userSkills) {
        this.userSkills = userSkills;
    }

    public ArrayList<UserLanguage> getUserLanguages() {
        return userLanguages;
    }

    public void setUserLanguages(ArrayList<UserLanguage> userLanguages) {
        this.userLanguages = userLanguages;
    }

    public ArrayList<UserAchievement> getUserAchievements() {
        return userAchievements;
    }

    public void setUserAchievements(ArrayList<UserAchievement> userAchievements) {
        this.userAchievements = userAchievements;
    }

    public ArrayList<UserInterest> getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(ArrayList<UserInterest> userInterest) {
        this.userInterest = userInterest;
    }

    public ArrayList<UserSocialProfile> getUserSocialProfile() {
        return userSocialProfile;
    }

    public void setUserSocialProfile(ArrayList<UserSocialProfile> userSocialProfile) {
        this.userSocialProfile = userSocialProfile;
    }
}
