package unilife.com.unilife.brands.request;

public class RedeemRequest {

    String type;
    String code;
    String brand_id;
    String receipt_number;
    String branch_name_location;

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public void setReceipt_number(String receipt_number) {
        this.receipt_number = receipt_number;
    }

    public void setBranch_name_location(String branch_name_location) {
        this.branch_name_location = branch_name_location;
    }
}
