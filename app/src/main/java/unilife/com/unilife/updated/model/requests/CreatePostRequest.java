package unilife.com.unilife.updated.model.requests;

public class CreatePostRequest {
    private String groupId;
    private String caption;
    private String event_images;
    private EventVideos event_video;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setEventImages(String eventImages) {
        this.event_images = eventImages;
    }

    public void setEventVideos(EventVideos eventVideo) {
        this.event_video = eventVideo;
    }

    public static class EventVideos {

        private String video_1;
        private String video_2;
        private String video_3;
        private String video_4;

        public void setVideo_1(String video_1) {
            this.video_1 = video_1;
        }

        public void setVideo_2(String video_2) {
            this.video_2 = video_2;
        }

        public void setVideo_3(String video_3) {
            this.video_3 = video_3;
        }

        public void setVideo_4(String video_4) {
            this.video_4 = video_4;
        }
    }

    public static class EventImages {

        private String image_1="";
        private String image_2;
        private String image_3;
        private String image_4;

        public void setImage_1(String image_1) {
            this.image_1 = image_1;
        }

        public void setImage_2(String image_2) {
            this.image_2 = image_2;
        }

        public void setImage_3(String image_3) {
            this.image_3 = image_3;
        }

        public void setImage_4(String image_4) {
            this.image_4 = image_4;
        }
    }
}
