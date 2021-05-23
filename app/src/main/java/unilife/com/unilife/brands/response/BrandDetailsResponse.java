package unilife.com.unilife.brands.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.ArrayList;

public class BrandDetailsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("categories_id")
        @Expose
        private String categoriesId;
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
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("online")
        @Expose
        private ArrayList<Online> online = null;
        @SerializedName("store")
        @Expose
        private ArrayList<Store> store = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoriesId() {
            return categoriesId;
        }

        public void setCategoriesId(String categoriesId) {
            this.categoriesId = categoriesId;
        }

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

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public ArrayList<Online> getOnline() {
            return online;
        }

        public void setOnline(ArrayList<Online> online) {
            this.online = online;
        }

        public ArrayList<Store> getStore() {
            return store;
        }

        public void setStore(ArrayList<Store> store) {
            this.store = store;
        }

    }

    public static class Online implements Parcelable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("terms_condition")
        @Expose
        private String termsCondition;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("discount_message")
        @Expose
        private String discountMessage;
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("online_redeem_link")
        @Expose
        private String onlineRedeemLink;
        @SerializedName("used_voucher")
        @Expose
        private String usedVoucher;

        protected Online(Parcel in) {
            id = in.readString();
            brandId = in.readString();
            heading = in.readString();
            description = in.readString();
            termsCondition = in.readString();
            code = in.readString();
            type = in.readString();
            discountMessage = in.readString();
            facebook = in.readString();
            instagram = in.readString();
            twitter = in.readString();
            onlineRedeemLink = in.readString();
            usedVoucher = in.readString();
        }

        public static final Creator<Online> CREATOR = new Creator<Online>() {
            @Override
            public Online createFromParcel(Parcel in) {
                return new Online(in);
            }

            @Override
            public Online[] newArray(int size) {
                return new Online[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsCondition() {
            return termsCondition;
        }

        public void setTermsCondition(String termsCondition) {
            this.termsCondition = termsCondition;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDiscountMessage() {
            return discountMessage;
        }

        public void setDiscountMessage(String discountMessage) {
            this.discountMessage = discountMessage;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getOnlineRedeemLink() {
            return onlineRedeemLink;
        }

        public void setOnlineRedeemLink(String onlineRedeemLink) {
            this.onlineRedeemLink = onlineRedeemLink;
        }

        public String getUsedVoucher() {
            return usedVoucher;
        }

        public void setUsedVoucher(String usedVoucher) {
            this.usedVoucher = usedVoucher;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(brandId);
            parcel.writeString(heading);
            parcel.writeString(description);
            parcel.writeString(termsCondition);
            parcel.writeString(code);
            parcel.writeString(type);
            parcel.writeString(discountMessage);
            parcel.writeString(facebook);
            parcel.writeString(instagram);
            parcel.writeString(twitter);
            parcel.writeString(onlineRedeemLink);
            parcel.writeString(usedVoucher);
        }
    }

    public static class Store implements Parcelable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("terms_condition")
        @Expose
        private String termsCondition;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("discount_message")
        @Expose
        private String discountMessage;
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("instagram")
        @Expose
        private String instagram;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("online_redeem_link")
        @Expose
        private String onlineRedeemLink;
        @SerializedName("used_voucher")
        @Expose
        private String usedVoucher;

        protected Store(Parcel in) {
            id = in.readString();
            brandId = in.readString();
            heading = in.readString();
            description = in.readString();
            termsCondition = in.readString();
            code = in.readString();
            type = in.readString();
            discountMessage = in.readString();
            facebook = in.readString();
            instagram = in.readString();
            twitter = in.readString();
            onlineRedeemLink = in.readString();
            usedVoucher = in.readString();
        }

        public static final Creator<Store> CREATOR = new Creator<Store>() {
            @Override
            public Store createFromParcel(Parcel in) {
                return new Store(in);
            }

            @Override
            public Store[] newArray(int size) {
                return new Store[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTermsCondition() {
            return termsCondition;
        }

        public void setTermsCondition(String termsCondition) {
            this.termsCondition = termsCondition;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDiscountMessage() {
            return discountMessage;
        }

        public void setDiscountMessage(String discountMessage) {
            this.discountMessage = discountMessage;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getOnlineRedeemLink() {
            return onlineRedeemLink;
        }

        public void setOnlineRedeemLink(String onlineRedeemLink) {
            this.onlineRedeemLink = onlineRedeemLink;
        }

        public String getUsedVoucher() {
            return usedVoucher;
        }

        public void setUsedVoucher(String usedVoucher) {
            this.usedVoucher = usedVoucher;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(brandId);
            parcel.writeString(heading);
            parcel.writeString(description);
            parcel.writeString(termsCondition);
            parcel.writeString(code);
            parcel.writeString(type);
            parcel.writeString(discountMessage);
            parcel.writeString(facebook);
            parcel.writeString(instagram);
            parcel.writeString(twitter);
            parcel.writeString(onlineRedeemLink);
            parcel.writeString(usedVoucher);
        }
    }
}