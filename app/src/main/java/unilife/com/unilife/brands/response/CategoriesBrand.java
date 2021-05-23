//
//package unilife.com.unilife.brands.response;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.ArrayList;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class CategoriesBrand implements Parcelable {
//
//    @SerializedName("id")
//    @Expose
//    private String id;
//    @SerializedName("categories_id")
//    @Expose
//    private Integer categoriesId;
//    @SerializedName("brand_name")
//    @Expose
//    private String brandName;
//    @SerializedName("image")
//    @Expose
//    private String image;
//    @SerializedName("type")
//    @Expose
//    private String type;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("brand_offer")
//    @Expose
//    private ArrayList<BrandOffer> brandOffer = null;
//
//    protected CategoriesBrand(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readString();
//        }
//        if (in.readByte() == 0) {
//            categoriesId = null;
//        } else {
//            categoriesId = in.readInt();
//        }
//        brandName = in.readString();
//        image = in.readString();
//        type = in.readString();
//        description = in.readString();
//        status = in.readString();
//        createdAt = in.readString();
//        updatedAt = in.readString();
//        brandOffer=in.createTypedArrayList(BrandOffer.CREATOR);
//    }
//
//    public static final Creator<CategoriesBrand> CREATOR = new Creator<CategoriesBrand>() {
//        @Override
//        public CategoriesBrand createFromParcel(Parcel in) {
//            return new CategoriesBrand(in);
//        }
//
//        @Override
//        public CategoriesBrand[] newArray(int size) {
//            return new CategoriesBrand[size];
//        }
//    };
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Integer getCategoriesId() {
//        return categoriesId;
//    }
//
//    public void setCategoriesId(Integer categoriesId) {
//        this.categoriesId = categoriesId;
//    }
//
//    public String getBrandName() {
//        return brandName;
//    }
//
//    public void setBrandName(String brandName) {
//        this.brandName = brandName;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public ArrayList<BrandOffer> getBrandOffer() {
//        return brandOffer;
//    }
//
//    public void setBrandOffer(ArrayList<BrandOffer> brandOffer) {
//        this.brandOffer = brandOffer;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        if (id == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeString(id);
//        }
//        if (categoriesId == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeInt(categoriesId);
//        }
//        parcel.writeString(brandName);
//        parcel.writeString(image);
//        parcel.writeString(type);
//        parcel.writeString(description);
//        parcel.writeString(status);
//        parcel.writeString(createdAt);
//        parcel.writeString(updatedAt);
//        parcel.writeTypedList(brandOffer);
//    }
//}
