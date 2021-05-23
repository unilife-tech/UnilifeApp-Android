package unilife.com.unilife.profile.model.requests;

public class SocialRequest {

    String facebook;
    String instagram;
    String snapchat;
    String twitter;

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    String linkedIn;

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
