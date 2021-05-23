package unilife.com.unilife.login.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserPhonesResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ws")
    @Expose
    private String ws;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("university_school_id")
        @Expose
        private String universitySchoolId;
        @SerializedName("email_domain")
        @Expose
        private String emailDomain;
        @SerializedName("school")
        @Expose
        private String school;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUniversitySchoolId() {
            return universitySchoolId;
        }

        public void setUniversitySchoolId(String universitySchoolId) {
            this.universitySchoolId = universitySchoolId;
        }

        public String getEmailDomain() {
            return emailDomain;
        }

        public void setEmailDomain(String emailDomain) {
            this.emailDomain = emailDomain;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

    }
}