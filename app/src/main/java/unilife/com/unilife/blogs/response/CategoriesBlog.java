
package unilife.com.unilife.blogs.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesBlog implements Parcelable {

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

    protected CategoriesBlog(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            categoriesId = null;
        } else {
            categoriesId = in.readInt();
        }
        title = in.readString();
        description = in.readString();
        image = in.readString();
        sharedBy = in.readString();
        videoLink = in.readString();
        slider = in.readString();
        status = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<CategoriesBlog> CREATOR = new Creator<CategoriesBlog>() {
        @Override
        public CategoriesBlog createFromParcel(Parcel in) {
            return new CategoriesBlog(in);
        }

        @Override
        public CategoriesBlog[] newArray(int size) {
            return new CategoriesBlog[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        if (categoriesId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(categoriesId);
        }
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeString(sharedBy);
        parcel.writeString(videoLink);
        parcel.writeString(slider);
        parcel.writeString(status);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}
