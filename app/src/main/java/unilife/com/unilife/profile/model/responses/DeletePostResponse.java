
package unilife.com.unilife.profile.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePostResponse {

    @SerializedName("response")
    @Expose
    private Boolean response;
    @SerializedName("data")
    @Expose
    private String data;

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}