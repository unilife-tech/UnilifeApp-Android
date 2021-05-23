package unilife.com.unilife.chat.model;

public class ExitGroupRequest {

    String group_id;
    String remove_user_id;

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public void setRemove_user_id(String remove_user_id) {
        this.remove_user_id = remove_user_id;
    }
}
