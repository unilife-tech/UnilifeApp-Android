package unilife.com.unilife.chat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendsResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_type")
        @Expose
        private String userType;
        @SerializedName("is_online")
        @Expose
        private String isOnline;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("complete_profile")
        @Expose
        private String completeProfile;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("university_school_id")
        @Expose
        private String universitySchoolId;
        @SerializedName("university_school_email")
        @Expose
        private String universitySchoolEmail;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("profile_status")
        @Expose
        private String profileStatus;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("decoded_password")
        @Expose
        private String decodedPassword;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("otp_verify")
        @Expose
        private String otpVerify;
        @SerializedName("reset_password")
        @Expose
        private String resetPassword;
        @SerializedName("remember_token")
        @Expose
        private String rememberToken;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        private boolean selected=false;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(String isOnline) {
            this.isOnline = isOnline;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCompleteProfile() {
            return completeProfile;
        }

        public void setCompleteProfile(String completeProfile) {
            this.completeProfile = completeProfile;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getUniversitySchoolId() {
            return universitySchoolId;
        }

        public void setUniversitySchoolId(String universitySchoolId) {
            this.universitySchoolId = universitySchoolId;
        }

        public String getUniversitySchoolEmail() {
            return universitySchoolEmail;
        }

        public void setUniversitySchoolEmail(String universitySchoolEmail) {
            this.universitySchoolEmail = universitySchoolEmail;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfileStatus() {
            return profileStatus;
        }

        public void setProfileStatus(String profileStatus) {
            this.profileStatus = profileStatus;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDecodedPassword() {
            return decodedPassword;
        }

        public void setDecodedPassword(String decodedPassword) {
            this.decodedPassword = decodedPassword;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getOtpVerify() {
            return otpVerify;
        }

        public void setOtpVerify(String otpVerify) {
            this.otpVerify = otpVerify;
        }

        public String getResetPassword() {
            return resetPassword;
        }

        public void setResetPassword(String resetPassword) {
            this.resetPassword = resetPassword;
        }

        public String getRememberToken() {
            return rememberToken;
        }

        public void setRememberToken(String rememberToken) {
            this.rememberToken = rememberToken;
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

    }

}