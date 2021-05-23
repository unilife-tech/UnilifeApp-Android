package unilife.com.unilife.chat.update.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.Acknowledge;
import com.koushikdutta.async.http.socketio.EventCallback;
import com.koushikdutta.async.http.socketio.SocketIOClient;
import com.koushikdutta.async.http.socketio.SocketIORequest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.UploadFileMoreDataReqListener;
import unilife.com.unilife.chat.update.PathUtil;
import unilife.com.unilife.chat.update.uploadfile.FileUploadManager;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.socketConnection.SocketSSL;
import unilife.com.unilife.updated.BaseActivity;

public class UploadFileActivity extends BaseActivity {
    private final String TAG = UploadFileActivity.class.getSimpleName();
    private File file;
    private File file1;
    @NotNull
    private String outputFile = "";
    @NotNull
    private String attachment_type = "";
    private int value;
    @NotNull
    private String imagePath = "";
    @NotNull
    private String bitmapImagePath = "";
    private boolean isGroup;
    @Nullable
    private ArrayList selectedMediaPaths = new ArrayList();
    private HashMap media_path = new HashMap();

    private String roomId = "";
    private String receiverId = "";
    private String senderId = "";
    private int PICK_PHOTO_FOR_AVATAR = 100;


    private SocketIOClient mClient;
    private unilife.com.unilife.chat.update.uploadfile.FileUploadManager mFileUploadManager;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        senderId = PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID);
        receiverId = getIntent().getStringExtra("receiver_id");
        roomId = getIntent().getStringExtra("roomId");
        pickImage();
    }


    public final void joinRoom(@NotNull String roomId) {
        Intrinsics.checkParameterIsNotNull(roomId, "roomId");

        try {
            Log.e(TAG, "JOIN_ROOM:" + roomId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(unilife.com.unilife.retro.WebUrls.getInstance().getROOM_ID(), roomId);
//            Socket var10000 = socket;
//            if (var10000 == null) {
//                Intrinsics.throwNpe();
//            }

            mClient.emit(unilife.com.unilife.retro.WebUrls.getInstance().getJOIN_ROOM(), (Acknowledge) jsonObject);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_uploadfile;
    }

    public void pickImage() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, PICK_PHOTO_FOR_AVATAR);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            Uri selectedImageURI = data.getData();
            String path = null;
            try {
                path = PathUtil.getPath(mContext, selectedImageURI);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.e("path", path);
            File imageFile = new File(path);

            file = imageFile;
            setImageafterCrop(file.getAbsolutePath());
        }
    }


    private final void setImageafterCrop(String gettingPath) {
        try {
            Intrinsics.checkExpressionValueIsNotNull(BitmapFactory.decodeFile(gettingPath), "BitmapFactory.decodeFile(gettingPath)");
            attachment_type = "image";
            imagePath = String.valueOf(gettingPath);
            file1 = new File(String.valueOf(gettingPath));
            sendAndGetBinaryData(imagePath, attachment_type);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public final void sendAndGetBinaryData(@NotNull String path, @NotNull String type) {
//        Intrinsics.checkParameterIsNotNull(path, "path");
//        Intrinsics.checkParameterIsNotNull(type, "type");
//        String uni_code = String.valueOf(System.currentTimeMillis());
//        media_path.put(uni_code, path);
//        Log.e("MediaSize", String.valueOf(media_path.size()));

        mFileUploadManager = new FileUploadManager();
        new FileUploadTask().execute();
    }

    private class FileUploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            boolean isSuccess = connect();

            if(isSuccess) {
                mFileUploadManager.prepare(imagePath, UploadFileActivity.this);

                // This function gets callback when server requests more data
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener);

                // This function will get a call back when upload completes
                setUploadFileCompleteListener();

                // Tell server we are ready to start uploading ..
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) { }

        @Override
        protected void onPreExecute() { }

        @Override
        protected void onProgressUpdate(Void... values) { }
    }

    private UploadFileMoreDataReqListener mUploadFileMoreDataReqListener = new UploadFileMoreDataReqListener() {

        @Override
        public void uploadChunck(int offset, int percent) {
            Log.v(TAG, String.format("Uploading %d completed. offset at: %d", percent,  offset));

            try {

                if(mClient.isConnected()) {
                    JSONArray jsonArr = new JSONArray();
                    JSONObject res = new JSONObject();

                    try {
                        res.put("Name", mFileUploadManager.getFileName());
                        res.put("Size", mFileUploadManager.getFileSize());
                        res.put("device_type", "android");
                        res.put("sender_id", PreferenceFile.getInstance().getPreferenceData(mContext, unilife.com.unilife.retrofit.WebUrls.getKEY_USERID()));
                        if (!receiverId.equals("")) {
                            res.put("receiver_id", receiverId);
                        } else {
                            res.put("group_id", "");
                        }

//                        if (clReplay.getVisibility() == View.VISIBLE) {
//                            res.put("chat_id", chatRepliedId);
//                        } else {
                        res.put("chat_id", "");
//                        }

                        res.put("message_type", attachment_type);
                        res.put("room_id", roomId);
                        res.put("flag", "send");
                        res.put("filepath", file1.getAbsolutePath());

//                        if (attachment_type.equals("video")) {
//                            res.put("thumb", getThumbnail());
//                        } else {
                        res.put("thumb", "");
//                        }

//                        var10002 = mFileUploadManager;
//                        if (var10002 == null) {
//                            Intrinsics.throwNpe();
//                        }

                        res.put("Data", mFileUploadManager.getData());
                        jsonArr.put(res);

                        // This will trigger server 'uploadFileStart' function
                        mClient.emit("uploadFileStart", jsonArr);
                    } catch (JSONException e) {
                        //TODO: Log errors some where..
                    }
                }


            } catch (Exception e) {

            }

        }

        @Override
        public void err(JSONException e) {
            // TODO Auto-generated method stub

        }
    };

    private void setUploadFileMoreDataReqListener(final UploadFileMoreDataReqListener callback ) {
        if(mClient != null) {
            EventCallback eventCallback = new EventCallback() {
                @Override
                public void onEvent(JSONArray argument, Acknowledge acknowledge) {
                    for (int i = 0; i < argument.length(); i++) {
                        try {
                            JSONObject json_data = argument.getJSONObject(i);
                            int place = json_data.getInt("Place");
                            int percent = json_data.getInt("Percent");

                            callback.uploadChunck(place, percent);
                            break;
                        } catch (JSONException e) {
                            callback.err(e);
                        }
                    }
                }
            };

            mClient.addListener("uploadFileMoreDataReq", eventCallback);
        }
    }

    private void setUploadFileCompleteListener() {
        if(mClient != null) {
            EventCallback eventCallback = new EventCallback() {
                @Override
                public void onEvent(JSONArray argument, Acknowledge acknowledge) {
                    mFileUploadManager.close();
                    Toast.makeText(mContext, "File upload success", Toast.LENGTH_SHORT).show();
                }
            };

            mClient.addListener("uploadFileCompleteRes", eventCallback);
        }
    }



    public boolean connect () {
        boolean isSuccess = false;

        try {
            SocketIORequest req = new SocketIORequest(WebUrls.getInstance().getSOCKET_BASE_URL());
            req.setLogging("Socket.IO", Log.VERBOSE);
            mClient = SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), req, null).get();
            isSuccess = true;
        } catch (InterruptedException e) {
            isSuccess = false;
        } catch (ExecutionException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

}