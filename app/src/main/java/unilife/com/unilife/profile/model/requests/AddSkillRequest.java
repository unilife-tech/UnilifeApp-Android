package unilife.com.unilife.profile.model.requests;

public class AddSkillRequest {

    private String skill_name;
    private String language_name;
    private String interest_name;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public void setUser_interest(String user_interest) {
        this.interest_name = user_interest;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }
}
