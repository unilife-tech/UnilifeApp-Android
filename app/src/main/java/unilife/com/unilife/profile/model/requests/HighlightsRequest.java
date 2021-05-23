package unilife.com.unilife.profile.model.requests;

public class HighlightsRequest {

    String currently_working;
    String currently_studying;
    String graduated_from;
    String complete_highschool_at;
    String lives_in;
    String from;
    String personal_information;

    public void setCurrently_working(String currently_working) {
        this.currently_working = currently_working;
    }

    public void setCurrently_studying(String currently_studying) {
        this.currently_studying = currently_studying;
    }

    public void setGraduated_from(String graduated_from) {
        this.graduated_from = graduated_from;
    }

    public void setComplete_highschool_at(String complete_highschool_at) {
        this.complete_highschool_at = complete_highschool_at;
    }

    public void setLives_in(String lives_in) {
        this.lives_in = lives_in;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPersonal_information(String personal_information) {
        this.personal_information = personal_information;
    }
}
