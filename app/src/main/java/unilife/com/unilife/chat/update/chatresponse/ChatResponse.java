
package unilife.com.unilife.chat.update.chatresponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("user_detail")
    @Expose
    private ArrayList<UserDetail> userDetail = null;
    @SerializedName("wallpaper")
    @Expose
    private Wallpaper wallpaper;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public ArrayList<UserDetail> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(ArrayList<UserDetail> userDetail) {
        this.userDetail = userDetail;
    }

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

}
