package unilife.com.unilife.brands.newbrandresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.ArrayList;

public class BrandResponse2 {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("offer")
    @Expose
    private ArrayList<Offer> offer = null;
    @SerializedName("banner")
    @Expose
    private ArrayList<Banner> banner = null;
    @SerializedName("categories")
    @Expose
    private ArrayList<Category> categories = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Offer> getOffer() {
        return offer;
    }

    public void setOffer(ArrayList<Offer> offer) {
        this.offer = offer;
    }

    public ArrayList<Banner> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<Banner> banner) {
        this.banner = banner;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public class Banner {

        @SerializedName("image")
        @Expose
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    public static class CategoriesBrand implements Parcelable{

        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("id")
        @Expose
        private String id;

        protected CategoriesBrand(Parcel in) {
            brandName = in.readString();
            image = in.readString();
            type = in.readString();
            description = in.readString();
            id = in.readString();
        }

        public static final Creator<CategoriesBrand> CREATOR = new Creator<CategoriesBrand>() {
            @Override
            public CategoriesBrand createFromParcel(Parcel in) {
                return new CategoriesBrand(in);
            }

            @Override
            public CategoriesBrand[] newArray(int size) {
                return new CategoriesBrand[size];
            }
        };

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(brandName);
            parcel.writeString(image);
            parcel.writeString(type);
            parcel.writeString(description);
            parcel.writeString(id);
        }
    }

    public class Category {

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private String id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public static class Offer implements Parcelable {

        @SerializedName("categories_id")
        @Expose
        private String categoriesId;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("categories_brand")
        @Expose
        private ArrayList<CategoriesBrand> categoriesBrand = null;

        protected Offer(Parcel in) {
            categoriesId = in.readString();
            category = in.readString();
            type = in.readString();
            categoriesBrand=in.createTypedArrayList(CategoriesBrand.CREATOR);
        }

        public static final Creator<Offer> CREATOR = new Creator<Offer>() {
            @Override
            public Offer createFromParcel(Parcel in) {
                return new Offer(in);
            }

            @Override
            public Offer[] newArray(int size) {
                return new Offer[size];
            }
        };

        public String getCategoriesId() {
            return categoriesId;
        }

        public void setCategoriesId(String categoriesId) {
            this.categoriesId = categoriesId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<CategoriesBrand> getCategoriesBrand() {
            return categoriesBrand;
        }

        public void setCategoriesBrand(ArrayList<CategoriesBrand> categoriesBrand) {
            this.categoriesBrand = categoriesBrand;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(categoriesId);
            parcel.writeString(category);
            parcel.writeString(type);
            parcel.writeTypedList(categoriesBrand);
        }
    }
}