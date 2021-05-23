package unilife.com.unilife.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {

    String imageChatBackground;
    String about;
    String domain;
    String universityId;
    String universityName;
    String universityEmail;

    String username;
    String gender;
    String birthday;
    String password;
    String profileImage;
    String userId;

    String countryName;
    String countryCode;

    String branchName;
    String ReceiptNumber;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences("Unilife", Context.MODE_PRIVATE);
    }

    public String getBranchName() {
        return sharedPreferences.getString("branchName", "");
    }

    public void setBranchName(String branchName) {
        sharedPreferences.edit().putString("branchName", branchName).apply();
    }

    public String getReceiptNumber() {
        return sharedPreferences.getString("receiptNumber", "");
    }

    public void setReceiptNumber(String receiptNumber) {
        sharedPreferences.edit().putString("receiptNumber", receiptNumber).apply();
    }

    public String getCountryName() {
        return sharedPreferences.getString("countryName", "United Arab Emirates (UAE)");
    }

    public void setCountryName(String countryName) {
        sharedPreferences.edit().putString("countryName", countryName).apply();
    }

    public String getCountryCode() {
        return sharedPreferences.getString("countryCode", "+971");
    }

    public void setCountryCode(String countryCode) {
        sharedPreferences.edit().putString("countryCode", countryCode).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString("userId", "");
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString("userId", userId).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString("username", "");
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString("username", username).apply();
    }

    public String getGender() {
        return sharedPreferences.getString("gender", "");
    }

    public void setGender(String gender) {
        sharedPreferences.edit().putString("gender", gender).apply();
    }

    public String getBirthday() {
        return sharedPreferences.getString("birthday", "");
    }

    public void setBirthday(String birthday) {
        sharedPreferences.edit().putString("birthday", birthday).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString("password", "");
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString("password", password).apply();
    }

    public String getProfileImage() {
        return sharedPreferences.getString("profileImage", "");
    }

    public void setProfileImage(String profileImage) {
        sharedPreferences.edit().putString("profileImage", profileImage).apply();
    }

    public String getDomain() {
        return sharedPreferences.getString("domain", "");
    }

    public void setDomain(String domain) {
        sharedPreferences.edit().putString("domain", domain).apply();
    }

    public String getUniversityId() {
        return sharedPreferences.getString("universityId", "");
    }

    public void setUniversityId(String universityId) {
        sharedPreferences.edit().putString("universityId", universityId).apply();
    }

    public String getUniversityName() {
        return sharedPreferences.getString("universityName", "");
    }

    public void setUniversityName(String universityName) {
        sharedPreferences.edit().putString("universityName", universityName).apply();
    }

    public String getUniversityEmail() {
        return sharedPreferences.getString("universityEmail", "");
    }

    public void setUniversityEmail(String universityEmail) {
        sharedPreferences.edit().putString("universityEmail", universityEmail).apply();
    }

    public String getAbout() {
        return sharedPreferences.getString("about", "");
    }

    public void setAbout(String about) {
        sharedPreferences.edit().putString("about", about).apply();
    }

    public String getImageChatBackground() {
        return sharedPreferences.getString("imageChatBackground", "");
    }

    public void setImageChatBackground(String imageChatBackground) {
        sharedPreferences.edit().putString("imageChatBackground", imageChatBackground).apply();
    }

    public void clearPref() {
        sharedPreferences.edit().clear().apply();
    }

}
