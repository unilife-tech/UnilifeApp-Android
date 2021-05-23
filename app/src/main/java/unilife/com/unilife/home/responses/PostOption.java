
package unilife.com.unilife.home.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostOption implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("selected")
    @Expose
    private String selected;
    @SerializedName("selected_count")
    @Expose
    private Integer selectedCount;
    @SerializedName("post_id")
    @Expose
    private String postId;

    private boolean isSelect=false;

    protected PostOption(Parcel in) {
        id = in.readString();
        options = in.readString();
        selected = in.readString();
        if (in.readByte() == 0) {
            selectedCount = null;
        } else {
            selectedCount = in.readInt();
        }
        postId = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<PostOption> CREATOR = new Creator<PostOption>() {
        @Override
        public PostOption createFromParcel(Parcel in) {
            return new PostOption(in);
        }

        @Override
        public PostOption[] newArray(int size) {
            return new PostOption[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public Integer getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Integer selectedCount) {
        this.selectedCount = selectedCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(options);
        parcel.writeString(selected);
        if (selectedCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(selectedCount);
        }
        parcel.writeString(postId);
        parcel.writeByte((byte) (isSelect ? 1 : 0));
    }
}
