package unilife.com.unilife.profile.model.requests;

public class UpdateProfileRequest {
    String personal_mission;
    String personal_description;
    String profile_banner_image;

    public void setPersonal_mission(String personal_mission) {
        this.personal_mission = personal_mission;
    }

    public void setPersonal_description(String personal_description) {
        this.personal_description = personal_description;
    }

    public void setProfile_banner_image(String profile_banner_image) {
        this.profile_banner_image = profile_banner_image;
    }
}
