package unilife.com.unilife.home.requests;

public class ReportPostRequest {
    String report_post_id;
    String type;
    String reason;
    String report_user_id;

    public void setReport_user_id(String report_user_id) {
        this.report_user_id = report_user_id;
    }

    public void setReport_post_id(String report_post_id) {
        this.report_post_id = report_post_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
