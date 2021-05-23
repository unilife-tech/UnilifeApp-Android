
package unilife.com.unilife.home.responses.comment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_data")
    @Expose
    private List<UserDatum> userData = null;
    @SerializedName("like_users")
    @Expose
    private List<Object> likeUsers = null;
    @SerializedName("like_users_count")
    @Expose
    private Integer likeUsersCount;
    @SerializedName("is_like")
    @Expose
    private Boolean isLike;
    @SerializedName("reply_count")
    @Expose
    private Integer replyCount;
    @SerializedName("reply_comment")
    @Expose
    private List<Object> replyComment = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserDatum> getUserData() {
        return userData;
    }

    public void setUserData(List<UserDatum> userData) {
        this.userData = userData;
    }

    public List<Object> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<Object> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public Integer getLikeUsersCount() {
        return likeUsersCount;
    }

    public void setLikeUsersCount(Integer likeUsersCount) {
        this.likeUsersCount = likeUsersCount;
    }

    public Boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public List<Object> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<Object> replyComment) {
        this.replyComment = replyComment;
    }

}
