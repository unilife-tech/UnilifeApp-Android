package unilife.com.unilife.profile.model.requests;

public class IntroRequest {

    String full_name;
    String email;
    String designation;
    String organisation;
    String phone;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    String profile_image;

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }
}
