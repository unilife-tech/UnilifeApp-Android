
package unilife.com.unilife.chat.update.groupresponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupChatResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("detail")
    @Expose
    private ArrayList<Detail> detail = null;
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

    public ArrayList<Detail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<Detail> detail) {
        this.detail = detail;
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
