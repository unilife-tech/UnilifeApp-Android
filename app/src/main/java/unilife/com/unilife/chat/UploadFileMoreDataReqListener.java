package unilife.com.unilife.chat;

import org.json.JSONException;

public interface UploadFileMoreDataReqListener
{
    void uploadChunck(int place, int percent);
    void err(JSONException e);
}
