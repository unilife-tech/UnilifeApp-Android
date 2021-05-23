package unilife.com.unilife.brands.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryWiseBrandResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Category {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("offers")
        @Expose
        private ArrayList<Offer> offers = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public ArrayList<Offer> getOffers() {
            return offers;
        }

        public void setOffers(ArrayList<Offer> offers) {
            this.offers = offers;
        }

    }

    public class Offer {

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
    }
}