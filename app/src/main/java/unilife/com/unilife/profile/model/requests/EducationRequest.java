package unilife.com.unilife.profile.model.requests;

public class EducationRequest {

    String college_name;
    String concentration;
    String degree;
    String club_society;
    String grade;
    String start_date;
    String end_date;
    String type;
    String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setClub_society(String club_society) {
        this.club_society = club_society;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setType(String type) {
        this.type = type;
    }
}
