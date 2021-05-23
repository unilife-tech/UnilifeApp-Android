
package unilife.com.unilife.home.responses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import unilife.com.unilife.blogs.response.CategoriesBlog;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("university_post_id")
    @Expose
    private String universityPostId;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("location_name")
    @Expose
    private Object locationName;
    @SerializedName("post_through_group")
    @Expose
    private String postThroughGroup;
    @SerializedName("group_id")
    @Expose
    private Object groupId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("event_title")
    @Expose
    private String eventTitle;
    @SerializedName("event_link")
    @Expose
    private String eventLink;
    @SerializedName("event_description")
    @Expose
    private String eventDescription;

    public String getAlready_hit_button() {
        return already_hit_button;
    }

    public void setAlready_hit_button(String already_hit_button) {
        this.already_hit_button = already_hit_button;
    }

    @SerializedName("event_register_count")
    @Expose
    private String event_register_count;
    @SerializedName("already_hit_button")
    @Expose
    private String already_hit_button;

    public String getEvent_register_count() {
        return event_register_count;
    }

    public void setEvent_register_count(String event_register_count) {
        this.event_register_count = event_register_count;
    }

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_uploading_post")
    @Expose
    private ArrayList<UserUploadingPost> userUploadingPost = null;
    @SerializedName("is_like")
    @Expose
    private Boolean isLike;
    @SerializedName("post_options")
    @Expose
    private ArrayList<PostOption> postOptions = null;
    @SerializedName("post_attachments")
    @Expose
    private ArrayList<PostAttachment> postAttachments = null;
    @SerializedName("post_comments_count")
    @Expose
    private Integer postCommentsCount;
    @SerializedName("post_like_count")
    @Expose
    private Integer postLikeCount;

    protected Datum(Parcel in) {
        id = in.readString();
        userId = in.readString();
        universityPostId = in.readString();
        caption = in.readString();
        postThroughGroup = in.readString();
        status = in.readString();
        type = in.readString();
        question = in.readString();
        eventTitle = in.readString();
        eventLink = in.readString();
        eventDescription = in.readString();
        createdAt = in.readString();
        event_register_count = in.readString();
        already_hit_button = in.readString();
        byte tmpIsLike = in.readByte();
        isLike = tmpIsLike == 0 ? null : tmpIsLike == 1;
        if (in.readByte() == 0) {
            postCommentsCount = null;
        } else {
            postCommentsCount = in.readInt();
        }
        if (in.readByte() == 0) {
            postLikeCount = null;
        } else {
            postLikeCount = in.readInt();
        }
        postAttachments=in.createTypedArrayList(PostAttachment.CREATOR);
        userUploadingPost=in.createTypedArrayList(UserUploadingPost.CREATOR);
        postOptions=in.createTypedArrayList(PostOption.CREATOR);
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUniversityPostId() {
        return universityPostId;
    }

    public void setUniversityPostId(String universityPostId) {
        this.universityPostId = universityPostId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Object getLocationName() {
        return locationName;
    }

    public void setLocationName(Object locationName) {
        this.locationName = locationName;
    }

    public String getPostThroughGroup() {
        return postThroughGroup;
    }

    public void setPostThroughGroup(String postThroughGroup) {
        this.postThroughGroup = postThroughGroup;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<UserUploadingPost> getUserUploadingPost() {
        return userUploadingPost;
    }

    public void setUserUploadingPost(ArrayList<UserUploadingPost> userUploadingPost) {
        this.userUploadingPost = userUploadingPost;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public ArrayList<PostOption> getPostOptions() {
        return postOptions;
    }

    public void setPostOptions(ArrayList<PostOption> postOptions) {
        this.postOptions = postOptions;
    }

    public ArrayList<PostAttachment> getPostAttachments() {
        return postAttachments;
    }

    public void setPostAttachments(ArrayList<PostAttachment> postAttachments) {
        this.postAttachments = postAttachments;
    }

    public Integer getPostCommentsCount() {
        return postCommentsCount;
    }

    public void setPostCommentsCount(Integer postCommentsCount) {
        this.postCommentsCount = postCommentsCount;
    }

    public Integer getPostLikeCount() {
        return postLikeCount;
    }

    public void setPostLikeCount(Integer postLikeCount) {
        this.postLikeCount = postLikeCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(universityPostId);
        parcel.writeString(caption);
        parcel.writeString(postThroughGroup);
        parcel.writeString(status);
        parcel.writeString(type);
        parcel.writeString(question);
        parcel.writeString(eventTitle);
        parcel.writeString(eventLink);
        parcel.writeString(eventDescription);
        parcel.writeString(createdAt);
        parcel.writeString(event_register_count);
        parcel.writeString(already_hit_button);
        parcel.writeByte((byte) (isLike == null ? 0 : isLike ? 1 : 2));
        if (postCommentsCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(postCommentsCount);
        }
        if (postLikeCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(postLikeCount);
        }
        parcel.writeTypedList(postAttachments);
        parcel.writeTypedList(userUploadingPost);
        parcel.writeTypedList(postOptions);
    }
}
