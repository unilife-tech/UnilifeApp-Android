package unilife.com.unilife.profile.model.requests;

public class AchievementRequest {

    String certificate_name;
    String offered_by;
    String offered_date;
    String duration;
    String type;

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public void setCertificate_name(String certificate_name) {
        this.certificate_name = certificate_name;
    }

    public void setOffered_by(String offered_by) {
        this.offered_by = offered_by;
    }

    public void setOffered_date(String offered_date) {
        this.offered_date = offered_date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }
}
