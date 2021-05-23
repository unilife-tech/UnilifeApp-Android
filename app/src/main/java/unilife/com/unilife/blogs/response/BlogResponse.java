
package unilife.com.unilife.blogs.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("slider")
    @Expose
    private ArrayList<Slider> slider = null;
    @SerializedName("blogs")
    @Expose
    private ArrayList<Blog> blogs = null;
    @SerializedName("team")
    @Expose
    private ArrayList<Team> team = null;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public ArrayList<Slider> getSlider() {
        return slider;
    }

    public void setSlider(ArrayList<Slider> slider) {
        this.slider = slider;
    }

    public ArrayList<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(ArrayList<Blog> blogs) {
        this.blogs = blogs;
    }

    public ArrayList<Team> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Team> team) {
        this.team = team;
    }

}
