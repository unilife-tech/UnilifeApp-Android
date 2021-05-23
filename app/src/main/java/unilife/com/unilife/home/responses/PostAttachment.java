
package unilife.com.unilife.home.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAttachment implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("attachment_type")
    @Expose
    private String attachmentType;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("thumbnail")
    @Expose
    private Object thumbnail;

    protected PostAttachment(Parcel in) {
        id = in.readString();
        attachmentType = in.readString();
        attachment = in.readString();
    }

    public static final Creator<PostAttachment> CREATOR = new Creator<PostAttachment>() {
        @Override
        public PostAttachment createFromParcel(Parcel in) {
            return new PostAttachment(in);
        }

        @Override
        public PostAttachment[] newArray(int size) {
            return new PostAttachment[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(attachmentType);
        parcel.writeString(attachment);
    }
}
