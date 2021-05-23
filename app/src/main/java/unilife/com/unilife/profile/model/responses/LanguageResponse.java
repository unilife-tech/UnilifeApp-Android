package unilife.com.unilife.profile.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import unilife.com.unilife.profile.model.UserLanguage;

public class LanguageResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("data")
    @Expose
    private ArrayList<UserLanguage> data = null;
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

    public ArrayList<UserLanguage> getData() {
        return data;
    }

    public void setData(ArrayList<UserLanguage> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public class Datum {
//
//        @SerializedName("id")
//        @Expose
//        private String id;
//        @SerializedName("language_name")
//        @Expose
//        private String languageName;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getLanguageName() {
//            return languageName;
//        }
//
//        public void setLanguageName(String languageName) {
//            this.languageName = languageName;
//        }
//    }
}