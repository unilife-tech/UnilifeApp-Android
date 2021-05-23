
package unilife.com.unilife.chat.update.groupresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("group_user_detail")
    @Expose
    private GroupUserDetail groupUserDetail;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GroupUserDetail getGroupUserDetail() {
        return groupUserDetail;
    }

    public void setGroupUserDetail(GroupUserDetail groupUserDetail) {
        this.groupUserDetail = groupUserDetail;
    }

}
