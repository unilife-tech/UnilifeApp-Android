//
//package unilife.com.unilife.brands.response;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class BrandOffer implements Parcelable {
//
//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("brand_id")
//    @Expose
//    private Integer brandId;
//    @SerializedName("title")
//    @Expose
//    private Object title;
//    @SerializedName("start_date")
//    @Expose
//    private String startDate;
//    @SerializedName("exp_date")
//    @Expose
//    private String expDate;
//    @SerializedName("type")
//    @Expose
//    private String type;
//    @SerializedName("discount_type")
//    @Expose
//    private String discountType;
//    @SerializedName("link")
//    @Expose
//    private String link;
//    @SerializedName("discount_percent")
//    @Expose
//    private Integer discountPercent;
//    @SerializedName("discount_code")
//    @Expose
//    private String discountCode;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("term_condition")
//    @Expose
//    private String termCondition;
//    @SerializedName("image")
//    @Expose
//    private String image;
//    @SerializedName("slider")
//    @Expose
//    private String slider;
//    @SerializedName("status")
//    @Expose
//    private String status;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("brand_name_data")
//    @Expose
//    private BrandNameData brandNameData;
//
//    protected BrandOffer(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readInt();
//        }
//        if (in.readByte() == 0) {
//            brandId = null;
//        } else {
//            brandId = in.readInt();
//        }
//        startDate = in.readString();
//        expDate = in.readString();
//        type = in.readString();
//        discountType = in.readString();
//        link = in.readString();
//        if (in.readByte() == 0) {
//            discountPercent = null;
//        } else {
//            discountPercent = in.readInt();
//        }
//        discountCode = in.readString();
//        description = in.readString();
//        termCondition = in.readString();
//        image = in.readString();
//        slider = in.readString();
//        status = in.readString();
//        createdAt = in.readString();
//        updatedAt = in.readString();
//    }
//
//    public static final Creator<BrandOffer> CREATOR = new Creator<BrandOffer>() {
//        @Override
//        public BrandOffer createFromParcel(Parcel in) {
//            return new BrandOffer(in);
//        }
//
//        @Override
//        public BrandOffer[] newArray(int size) {
//            return new BrandOffer[size];
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
//    public Integer getBrandId() {
//        return brandId;
//    }
//
//    public void setBrandId(Integer brandId) {
//        this.brandId = brandId;
//    }
//
//    public Object getTitle() {
//        return title;
//    }
//
//    public void setTitle(Object title) {
//        this.title = title;
//    }
//
//    public String getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    public String getExpDate() {
//        return expDate;
//    }
//
//    public void setExpDate(String expDate) {
//        this.expDate = expDate;
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
//    public String getDiscountType() {
//        return discountType;
//    }
//
//    public void setDiscountType(String discountType) {
//        this.discountType = discountType;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public Integer getDiscountPercent() {
//        return discountPercent;
//    }
//
//    public void setDiscountPercent(Integer discountPercent) {
//        this.discountPercent = discountPercent;
//    }
//
//    public String getDiscountCode() {
//        return discountCode;
//    }
//
//    public void setDiscountCode(String discountCode) {
//        this.discountCode = discountCode;
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
//    public String getTermCondition() {
//        return termCondition;
//    }
//
//    public void setTermCondition(String termCondition) {
//        this.termCondition = termCondition;
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
//    public String getSlider() {
//        return slider;
//    }
//
//    public void setSlider(String slider) {
//        this.slider = slider;
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
//    public BrandNameData getBrandNameData() {
//        return brandNameData;
//    }
//
//    public void setBrandNameData(BrandNameData brandNameData) {
//        this.brandNameData = brandNameData;
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
//        if (brandId == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeInt(brandId);
//        }
//        parcel.writeString(startDate);
//        parcel.writeString(expDate);
//        parcel.writeString(type);
//        parcel.writeString(discountType);
//        parcel.writeString(link);
//        if (discountPercent == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeInt(discountPercent);
//        }
//        parcel.writeString(discountCode);
//        parcel.writeString(description);
//        parcel.writeString(termCondition);
//        parcel.writeString(image);
//        parcel.writeString(slider);
//        parcel.writeString(status);
//        parcel.writeString(createdAt);
//        parcel.writeString(updatedAt);
//    }
//}
