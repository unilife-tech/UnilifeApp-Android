
package unilife.com.unilife.blogs.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slider {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categories_id")
    @Expose
    private Integer categoriesId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("shared_by")
    @Expose
    private String sharedBy;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("slider")
    @Expose
    private String slider;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("user_blog_like")
    @Expose
    private List<Object> userBlogLike = null;
    @SerializedName("user_blog_saved")
    @Expose
    private List<Object> userBlogSaved = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(Integer categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(String sharedBy) {
        this.sharedBy = sharedBy;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Object> getUserBlogLike() {
        return userBlogLike;
    }

    public void setUserBlogLike(List<Object> userBlogLike) {
        this.userBlogLike = userBlogLike;
    }

    public List<Object> getUserBlogSaved() {
        return userBlogSaved;
    }

    public void setUserBlogSaved(List<Object> userBlogSaved) {
        this.userBlogSaved = userBlogSaved;
    }

}
