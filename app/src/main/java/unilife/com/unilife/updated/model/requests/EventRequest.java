package unilife.com.unilife.updated.model.requests;

public class EventRequest {

   String event_title;
   String event_link;
   String event_description;
   String event_images;
   String group_id;

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public void setEvent_link(String event_link) {
        this.event_link = event_link;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public void setEvent_images(String event_images) {
        this.event_images = event_images;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
