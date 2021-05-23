package unilife.com.unilife.login.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UniversityListingResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ws")
    @Expose
    private String ws;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datum {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("domains")
        @Expose
        private String domains;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDomains() {
            return domains;
        }

        public void setDomains(String domains) {
            this.domains = domains;
        }
    }
}