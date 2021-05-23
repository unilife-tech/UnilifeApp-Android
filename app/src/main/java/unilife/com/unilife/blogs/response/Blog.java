
package unilife.com.unilife.blogs.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Blog implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("categories_image")
    @Expose
    private String categoriesImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("categories_blog")
    @Expose
    private ArrayList<CategoriesBlog> categoriesBlog = null;

    protected Blog(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        categoriesName = in.readString();
        categoriesImage = in.readString();
        status = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        categoriesBlog=in.createTypedArrayList(CategoriesBlog.CREATOR);
    }

    public static final Creator<Blog> CREATOR = new Creator<Blog>() {
        @Override
        public Blog createFromParcel(Parcel in) {
            return new Blog(in);
        }

        @Override
        public Blog[] newArray(int size) {
            return new Blog[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getCategoriesImage() {
        return categoriesImage;
    }

    public void setCategoriesImage(String categoriesImage) {
        this.categoriesImage = categoriesImage;
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

    public ArrayList<CategoriesBlog> getCategoriesBlog() {
        return categoriesBlog;
    }

    public void setCategoriesBlog(ArrayList<CategoriesBlog> categoriesBlog) {
        this.categoriesBlog = categoriesBlog;
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
        parcel.writeString(categoriesName);
        parcel.writeString(categoriesImage);
        parcel.writeString(status);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeTypedList(categoriesBlog);
    }
}
