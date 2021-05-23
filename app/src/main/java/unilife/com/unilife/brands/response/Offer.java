//
//package unilife.com.unilife.brands.response;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Offer implements Parcelable {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("image")
//    @Expose
//    private String image;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("categories_brand")
//    @Expose
//    private ArrayList<CategoriesBrand> categoriesBrand = null;
//
//    protected Offer(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readInt();
//        }
//        name = in.readString();
//        image = in.readString();
//        status = in.readString();
//        createdAt = in.readString();
//        updatedAt = in.readString();
//        categoriesBrand=in.createTypedArrayList(CategoriesBrand.CREATOR);
//    }
//
//    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
//        @Override
//        public Offer createFromParcel(Parcel in) {
//            return new Offer(in);
//        }
//
//        @Override
//        public Offer[] newArray(int size) {
//            return new Offer[size];
//        }
//    };
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
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
//    public ArrayList<CategoriesBrand> getCategoriesBrand() {
//        return categoriesBrand;
//    }
//
//    public void setCategoriesBrand(ArrayList<CategoriesBrand> categoriesBrand) {
//        this.categoriesBrand = categoriesBrand;
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
//            parcel.writeInt(id);
//        }
//        parcel.writeString(name);
//        parcel.writeString(image);
//        parcel.writeString(status);
//        parcel.writeString(createdAt);
//        parcel.writeString(updatedAt);
//        parcel.writeTypedList(categoriesBrand);
//    }
//}
