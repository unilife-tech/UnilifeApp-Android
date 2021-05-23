package unilife.com.unilife.chat.update;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.capybaralabs.swipetoreply.ISwipeControllerActions;
import com.capybaralabs.swipetoreply.SwipeController;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.varunjohn1990.audio_record_view.AudioRecordView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import unilife.com.unilife.PreferenceFile;
import unilife.com.unilife.R;
import unilife.com.unilife.chat.FileUploadManager;
import unilife.com.unilife.chat.UploadFileMoreDataReqListener;
import unilife.com.unilife.chat.adapter.ChatDetailsAdapter;
import unilife.com.unilife.chat.model.ChatDetailRequest;
import unilife.com.unilife.chat.update.chatresponse.ChatResponse;
import unilife.com.unilife.chat.update.chatresponse.Datum;
import unilife.com.unilife.chat.update.chatresponse.InstantResponse;
import unilife.com.unilife.chat.update.groupresponse.GroupRoomResponse;
import unilife.com.unilife.chat.update.ui.ViewProxy;
import unilife.com.unilife.profile.OtherProfileActivity;
import unilife.com.unilife.retro.ApiClient;
import unilife.com.unilife.retro.ApiInterface;
import unilife.com.unilife.retro.WebUrls;
import unilife.com.unilife.socketConnection.SocketSSL;
import unilife.com.unilife.updated.BaseActivity;
import unilife.com.unilife.utils.EmojiUtils;

public class ChatDetailsActivity extends BaseActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    //========================================================
    private static String fileName = null;
    private final String TAG = ChatDetailsActivity.class.getSimpleName();
    private MediaPlayer mMediaPlayer=null;
    private int prevPos=-1;
    public String senderId = "";
    public String senderName = "";
    public String receiver_name = "";
    //=======================================================
    @BindView(R.id.clReplay)
    ConstraintLayout clReplay;
    @BindView(R.id.txtSenderNameReplay)
    TextView txtSenderNameReplay;
    @BindView(R.id.txtMessageReplay)
    TextView txtMessageReplay;
    @BindView(R.id.imgReplay)
    ImageView imgReplay;
    @BindView(R.id.imgCloseReplay)
    ImageView imgCloseReplay;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.ivProfileImg)
    CircleImageView ivProfileImg;
    @BindView(R.id.imgInfo)
    ImageView imgInfo;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgVideoCall)
    ImageView imgVideoCall;
    @BindView(R.id.imgSend)
    ImageView imgSend;
    @BindView(R.id.imgCamera)
    ImageView imgCamera;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    //=====================================================
    @BindView(R.id.imgAudio)
    ImageView imgAudio;
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.edtMessage)
    EditText edtMessage;
    @BindView(R.id.recyclerChat)
    RecyclerView recyclerChat;
    ChatDetailsAdapter chatDetailsAdapter;
    private AudioRecordView audioRecordView;
    private long time;
    private File file1;
    @NotNull
    private String attachment_type = "";
    @NotNull
    private String imagePath = "";
    private HashMap media_path = new HashMap();
    private MediaRecorder recorder = null;
    private TextView recordTimeText;
    private ImageButton audioSendButton;
    private View recordPanel;
    private View slideText;
    private View imgToBlink;
    private float startedDraggingX = -1;
    private float distCanMove = dp(80);
    private long startTime = 0L;
    private Timer timer;
    private String roomId = "";
    private String receiverId = "";
    private String receiver_profile_image = "";
    private com.github.nkzawa.socketio.client.Socket socket;
    private File file;
    private boolean isLock = false;

    @NotNull
    private Emitter.Listener onRoomJoinListener = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onRoomJoinListener:" + args[0].toString());
        }
    };
    @NotNull
    private Emitter.Listener onRoomLeaveListener = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onRoomLeaveListener:" + args[0].toString());
        }
    };
    @NotNull
    private Emitter.Listener onSeenListener = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onSeenListener:" + args[0].toString());
        }
    };
    @NotNull
    private Emitter.Listener onGroupSeenListener = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onGroupSeenListener:" + args[0].toString());
        }
    };
    @NotNull
    private Emitter.Listener onConnect = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onConnect");
        }
    };
    @NotNull
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onDisconnect");
            Socket var10000 = socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.connect();
//            if (!getImagePath().equals("")) {
//                sendAndGetBinaryData(unilife.com.unilife.chat.ChatTest.this.getImagePath(), unilife.com.unilife.chat.ChatTest.this.getAttachment_type());
//            }

        }
    };
    @NotNull
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        public final void call(Object[] args) {
            Log.e(TAG, "onConnectError");
            Socket var10000 = socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.connect();
        }
    };
    @NotNull
    private Emitter.Listener onSendMessageListener = new Emitter.Listener() {
        public final void call(Object[] args) {
//            findViewById(R.id.layoutEmpty).setVisibility(View.GONE);
            Log.e(TAG, "onSendMessageListener" + args[0].toString());
            String response = args[0].toString();

            Gson gson = new Gson();
            InstantResponse instantResponse = gson.fromJson(response, InstantResponse.class);

            Datum datum = new Datum();
            datum.setId(instantResponse.getId());
            datum.setRoomId(instantResponse.getRoomId());
            datum.setMessage(instantResponse.getMessage());
            datum.setThumb(instantResponse.getThumb());
            datum.setFilepath(instantResponse.getFilepath());
            datum.setSenderId(instantResponse.getSenderId());
            datum.setReceiverId(instantResponse.getReceiverId());
            datum.setGroupId(instantResponse.getGroupId());
            datum.setChatId(instantResponse.getChatId());
            datum.setDate(instantResponse.getDate());
            datum.setSeen(instantResponse.getSeen());
            datum.setIsDeleted(instantResponse.getIsDeleted());
            datum.setDeleteChatId(instantResponse.getDeleteChatId());
            datum.setMessageType(instantResponse.getMessageType());
            datum.setOnlyDate(instantResponse.getOnlyDate());
            datum.setCreatedAt(instantResponse.getCreatedAt());
            datum.setUpdatedAt(instantResponse.getUpdatedAt());

            chatDetailsAdapter.addNewData(datum, recyclerChat);
        }
    };
    private int PICK_IMAGE_CODE = 101;
    private int PICK_VIDEO_CODE = 201;
    private int PICK_DOCUMENT_CODE = 301;
    private int CAMERA_REQUEST = 401;
    private int REQUEST_ID_MULTIPLE_PERMISSIONS = 15;
    private String chatRepliedId = "";

    public static int dp(float value) {
        return (int) Math.ceil(1 * value);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        checkAndRequestPermissions();
        initRecord();
        init();
//        getChatData();
        crateChatRoom();
//        connectSocket();
    }

    public void crateChatRoom() {
        if (!isNetworkConnected())
            return;

        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
        chatDetailRequest.setReceiver_id(receiverId);
        chatDetailRequest.setSender_id(senderId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<GroupRoomResponse> call = apiInterface.createRoom(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), chatDetailRequest);
        call.enqueue(new Callback<GroupRoomResponse>() {
            @Override
            public void onResponse(Call<GroupRoomResponse> call, Response<GroupRoomResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {
                    roomId = response.body().getData().getRoomId();
                    connectSocket();
                    getChatData();
//                    groupChatDetailsAdapter.updateData(response.body().getData().getChatData());
//                    recyclerChat.scrollToPosition(groupChatDetailsAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void onFailure(Call<GroupRoomResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }


    private void init() {

        senderId = PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID);
        senderName = PreferenceFile.getInstance().getPreferenceData(this, KEY_USERNAME);
        receiverId = getIntent().getStringExtra("receiver_id");
//        roomId = getIntent().getStringExtra("roomId");
        receiver_name = getIntent().getStringExtra("receiver_name");
        receiver_profile_image = getIntent().getStringExtra("receiver_profile_image");

        textName.setText(receiver_name);
        Glide.with(mContext)
                .load(WebUrls.getInstance().getPROFILE_IMAGE_URL() + receiver_profile_image)
                .apply(defultImg)
                .into(ivProfileImg);

        chatDetailsAdapter = new ChatDetailsAdapter(this);
        recyclerChat.setHasFixedSize(true);
        recyclerChat.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerChat.setAdapter(chatDetailsAdapter);

        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    imgSend.setVisibility(View.VISIBLE);
                    imgAdd.setVisibility(View.GONE);
//                    imgCamera.setVisibility(View.GONE);
                    imgAudio.setVisibility(View.GONE);
                } else {
                    imgSend.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.VISIBLE);
//                    imgCamera.setVisibility(View.VISIBLE);
                    imgAudio.setVisibility(View.VISIBLE);
                }
            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edtMessage.getText().toString().trim();
                String encode = "";

                if (EmojiUtils.containsEmoji(msg)) {
                    try {
                        encode = URLEncoder.encode(msg, "UTF-8");
                        Log.e("Enocode", encode);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(WebUrls.SENDER_ID, senderId);
                        jsonObject.put(WebUrls.MESSAGE, encode);

                        if (receiverId != "") {
                            jsonObject.put(WebUrls.RECEIVER_ID, receiverId);
                            jsonObject.put(WebUrls.GROUP_ID, "");
                        } else {
//                        jsonObject.put(WebUrls.GROUP_ID, groupId)
//                        jsonObject.put(WebUrls.RECEIVER_ID, "")
                        }

                        Log.e("params", jsonObject.toString());
                        edtMessage.setText("");

                        if (clReplay.getVisibility() == View.VISIBLE) {
                            jsonObject.put("chat_id", chatRepliedId);
                        } else {
                            jsonObject.put("chat_id", "");
                        }
                        Socket skt = socket;
                        if (socket == null) {
                            Intrinsics.throwNpe();
                        }
                        clReplay.setVisibility(View.GONE);
                        skt.emit(WebUrls.getInstance().getSEND_MESSAGE(), jsonObject);
//                    this.hideReplyLayout();
//                    this.chatRepliedId = "";
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }


                } else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        //          jsonObject.put(WebUrls.ROOM_ID, roomId)
                        jsonObject.put(WebUrls.SENDER_ID, senderId);
                        jsonObject.put(WebUrls.MESSAGE, msg);

                        if (receiverId != "") {
                            jsonObject.put(WebUrls.RECEIVER_ID, receiverId);
                            jsonObject.put(WebUrls.GROUP_ID, "");
                        } else {
//                        jsonObject.put(WebUrls.GROUP_ID, groupId)
//                        jsonObject.put(WebUrls.RECEIVER_ID, "")
                        }

                        Log.e("params", jsonObject.toString());
                        edtMessage.setText("");

                        if (clReplay.getVisibility() == View.VISIBLE) {
                            jsonObject.put("chat_id", chatRepliedId);
                        } else {
                            jsonObject.put("chat_id", "");
                        }
                        Socket skt = socket;
                        if (socket == null) {
                            Intrinsics.throwNpe();
                        }
                        clReplay.setVisibility(View.GONE);
                        skt.emit(WebUrls.getInstance().getSEND_MESSAGE(), jsonObject);
//                    this.hideReplyLayout();
//                    this.chatRepliedId = "";
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }
                }
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadGalleryDialog();
            }
        });
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        SwipeController controller = new SwipeController(mContext, new ISwipeControllerActions() {
            @Override
            public void onSwipePerformed(int position) {
//                showToast(position+"");
                // Here you can handle the swipe-to-reply event
                setUpReplay(chatDetailsAdapter.arrayList.get(position));

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        itemTouchHelper.attachToRecyclerView(recyclerChat);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat_details;
    }

    public void openFriendDetails(View view) {
        intent = new Intent(mContext, FriendDetailActivity.class);
        intent.putExtra("receiver_id", receiverId);
        intent.putExtra("receiver_name", receiver_name);
        intent.putExtra("receiver_profile_image", receiver_profile_image);
        intent.putExtra("roomId", roomId);
        startActivity(intent);
    }

    public void goBack(View view) {
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        disconnectSocket();
//        cancelDialog();
    }

    public final void leaveRoom(@NotNull String roomId) {
        Intrinsics.checkParameterIsNotNull(roomId, "roomId");

        try {
            Log.e(this.TAG, "leaveRoom" + roomId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebUrls.getInstance().getROOM_ID(), roomId);
            Socket var10000 = this.socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.emit(WebUrls.getInstance().getLEAVE_ROOM(), jsonObject);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    private final void disconnectSocket() {
        try {
            leaveRoom(this.roomId);
            if (this.socket != null) {
                Socket var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.disconnect();
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off("connect", this.onConnect);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off("disconnect", this.onDisconnect);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off("connect_error", this.onConnectError);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off("connect_timeout", this.onConnectError);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off(WebUrls.getInstance().getLISTEN_SEND_MESSAGE(), this.onSendMessageListener);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off(WebUrls.getInstance().getLEAVE_ROOM(), this.onRoomLeaveListener);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off(WebUrls.getInstance().getJOIN_ROOM(), this.onRoomJoinListener);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off(WebUrls.getInstance().getLISTEN_SEEN(), this.onSeenListener);
                var10000 = this.socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                var10000.off(WebUrls.getInstance().getGROUP_SEEN_EMIT_LISTEN(), this.onGroupSeenListener);
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void getChatData() {
        if (!isNetworkConnected())
            return;

        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
        chatDetailRequest.setReceiver_id(receiverId);
        chatDetailRequest.setSender_id(senderId);

        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClientOld().create(ApiInterface.class);
        Call<ChatResponse> call = apiInterface.getChatDetails(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), chatDetailRequest);

        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                hideProgressDialog();
                Log.e("response code -->", "" + response.code());

                if (response.isSuccessful()) {

                    Glide.with(mContext)
                            .load(CHAT_MEDIA_URL + response.body().getWallpaper().getImage())
                            .apply(defChatback)
                            .into(imgBackground);

                    if (response.body().getUserDetail().size() > 0) {
//                        findViewById(R.id.layoutEmpty).setVisibility(View.GONE);
                        chatDetailsAdapter.updateData(response.body().getData(), response.body().getUserDetail());
                        recyclerChat.scrollToPosition(chatDetailsAdapter.getItemCount() - 1);
                    } else {
//                        findViewById(R.id.layoutEmpty).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                hideProgressDialog();
                showToast(t.getMessage());
            }
        });
    }

    private void connectSocket() {
        try {
            this.socket = IO.socket(WebUrls.getInstance().getSOCKET_BASE_URL(), (new SocketSSL()).certificate());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Socket skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on("connect", this.onConnect);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on("disconnect", this.onDisconnect);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on("connect_error", this.onConnectError);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on("connect_timeout", this.onConnectError);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on(WebUrls.getInstance().getLISTEN_ROOM_JOIN(), onRoomJoinListener);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on(WebUrls.getInstance().getLEAVE_ROOM(), this.onRoomLeaveListener);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on(WebUrls.getInstance().getLISTEN_SEND_MESSAGE(), this.onSendMessageListener);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on(WebUrls.getInstance().getLISTEN_SEEN(), this.onSeenListener);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.on(WebUrls.getInstance().getGROUP_SEEN_EMIT_LISTEN(), this.onGroupSeenListener);
        skt = this.socket;
        if (skt == null) {
            Intrinsics.throwNpe();
        }

        skt.connect();
        this.joinRoom(roomId);
    }

    public final void joinRoom(@NotNull String roomId) {
        Intrinsics.checkParameterIsNotNull(roomId, "roomId");

        try {
            Log.e(this.TAG, "JOIN_ROOM:" + roomId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebUrls.getInstance().getROOM_ID(), roomId);
            Socket var10000 = this.socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.emit(WebUrls.getInstance().getJOIN_ROOM(), jsonObject);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    //============================================================================================
    public final void uploadGalleryDialog() {
        final AlertDialog alertDialog;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.custom_alert_dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.upload_attachment_gallery, null);

        TextView tvUploadImage = view.findViewById(R.id.tvUploadImage);
        TextView tvUploadVideo = view.findViewById(R.id.tvUploadVideo);
        TextView tvUploadAttach = view.findViewById(R.id.tvUploadAttach);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        alertDialogBuilder.setView(view);

        alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        tvUploadImage.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_CODE);

            }
        });

        tvUploadVideo.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent, PICK_VIDEO_CODE);
            }
        });

        tvUploadAttach.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
                String[] mimeTypes =
                        {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                                "text/plain",
                                "application/pdf",
                                "application/zip", "application/vnd.android.package-archive"};

                intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, PICK_DOCUMENT_CODE);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View it) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.85);
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        alertDialog.getWindow().setLayout(width, height);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();
    }

    public void comingSoon(View view) {
        showToast("Coming soon...");
    }

    //=========================================== Record audio ====================================

    private void initRecord() {
        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "/AUDIO_" + System.currentTimeMillis() + ".mp3";

        recordPanel = findViewById(R.id.record_panel);
        recordTimeText = findViewById(R.id.recording_time_text);
        slideText = findViewById(R.id.slideText);
        imgToBlink = findViewById(R.id.imgToBlink);
        audioSendButton = findViewById(R.id.chat_audio_send_button);
        TextView textView = findViewById(R.id.slideToCancelTextView);
        textView.setText("Slide to cancel");
        imgAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!isLock) {
                    findViewById(R.id.chat_audio_send_button).setVisibility(View.GONE);
                    findViewById(R.id.txtCancel).setVisibility(View.GONE);
                    findViewById(R.id.layoutLock).setVisibility(View.VISIBLE);
                    findViewById(R.id.slideText).setVisibility(View.VISIBLE);
                    findViewById(R.id.imgAudioSend).setVisibility(View.VISIBLE);
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) slideText
                            .getLayoutParams();
                    params.leftMargin = dp(30);
                    slideText.setLayoutParams(params);
                    ViewProxy.setAlpha(slideText, 1);
                    startedDraggingX = -1;
                    // startRecording();
                    startrecord();
                    audioSendButton.getParent()
                            .requestDisallowInterceptTouchEvent(true);
                    recordPanel.setVisibility(View.VISIBLE);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP
                        || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    startedDraggingX = -1;

                    if (!isLock)
                        stoprecord();
                    // stopRecording(true);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (x < -distCanMove) {
                        cancelRecord();
                        // stopRecording(false);
                        x = x + ViewProxy.getX(audioSendButton);
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) slideText
                                .getLayoutParams();
                        if (startedDraggingX != -1) {
                            float dist = (x - startedDraggingX);
                            params.leftMargin = dp(30) + (int) dist;
                            slideText.setLayoutParams(params);
                            float alpha = 1.0f + dist / distCanMove;
                            if (alpha > 1) {
                                alpha = 1;
                            } else if (alpha < 0) {
                                alpha = 0;
                            }
                            ViewProxy.setAlpha(slideText, alpha);
                        }
                        if (x <= ViewProxy.getX(slideText) + slideText.getWidth()
                                + dp(30)) {
                            if (startedDraggingX == -1) {
                                startedDraggingX = x;
                                distCanMove = (recordPanel.getMeasuredWidth()
                                        - slideText.getMeasuredWidth() - dp(48)) / 2.0f;
                                if (distCanMove <= 0) {
                                    distCanMove = dp(80);
                                } else if (distCanMove > dp(80)) {
                                    distCanMove = dp(80);
                                }
                            }
                        }
                        if (params.leftMargin > dp(30)) {
                            params.leftMargin = dp(30);
                            slideText.setLayoutParams(params);
                            ViewProxy.setAlpha(slideText, 1);
                            startedDraggingX = -1;
                        }
                    } else if (y < -distCanMove) {
                        isLock = true;
//                        Toast.makeText(mContext, "Locked", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.chat_audio_send_button).setVisibility(View.VISIBLE);
                        findViewById(R.id.txtCancel).setVisibility(View.VISIBLE);
                        findViewById(R.id.layoutLock).setVisibility(View.GONE);
                        findViewById(R.id.slideText).setVisibility(View.GONE);
                        findViewById(R.id.imgAudioSend).setVisibility(View.GONE);

                        findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isLock = false;
                                cancelRecord();

                            }
                        });

                        findViewById(R.id.chat_audio_send_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isLock = false;
                                stoprecord();
                            }
                        });
//                        cancelRecord();
                        // stopRecording(false);
                    }
                }

                view.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    private void animation() {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        imgToBlink.startAnimation(animation); //to start animation
    }

    private void startrecord() {
        vibrate();
        startRecording();
        findViewById(R.id.layoutAudio).setVisibility(View.VISIBLE);
        findViewById(R.id.constraintBottom).setVisibility(View.INVISIBLE);
        animation();
        // TODO Auto-generated method stub
        startTime = SystemClock.uptimeMillis();
        timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);

    }

    private void cancelRecord() {
        stopRecording();
        findViewById(R.id.layoutAudio).setVisibility(View.GONE);
        findViewById(R.id.constraintBottom).setVisibility(View.VISIBLE);
        imgToBlink.clearAnimation();
        if (timer != null) {
            timer.cancel();
        }
        recordTimeText.setText("00:00");
    }

    private void stoprecord() {
        vibrate();
        stopRecording();
        findViewById(R.id.layoutAudio).setVisibility(View.GONE);
        findViewById(R.id.constraintBottom).setVisibility(View.VISIBLE);
        imgToBlink.clearAnimation();
        // TODO Auto-generated method stub
        if (timer != null) {
            timer.cancel();
        }
        if (recordTimeText.getText().toString().equals("00:00")) {
            return;
        } else {
            recordTimeText.setText("00:00");
            setFileToSend(new File(fileName), "audio");
        }
//        startPlaying();
    }

    private void startPlaying() {

//        MediaPlayer player = new MediaPlayer();
//        try {
//            player.setDataSource(fileName);
//            player.prepare();
//            player.start();
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "prepare() failed");
//        }
    }

    private void vibrate() {
        // TODO Auto-generated method stub
        try {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

//        recorder.prepare();
//        recorder.start();

//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setOutputFile(fileName);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }


    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    //==================================== Upload file to server ======================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PICK_IMAGE_CODE || requestCode == PICK_VIDEO_CODE || requestCode == PICK_DOCUMENT_CODE) && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                showToast("File selection error, try again.");
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

            file = new File(path);

            if (requestCode == PICK_IMAGE_CODE)
                setFileToSend(file, "image");
            else if (requestCode == PICK_VIDEO_CODE)
                setFileToSend(file, "video");
            else if (requestCode == PICK_DOCUMENT_CODE)
                setFileToSend(file, "document");
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String fileCapture = getExternalCacheDir().getAbsolutePath();
            fileCapture += "/IMG" + System.currentTimeMillis() + ".png";
            try (FileOutputStream out = new FileOutputStream(fileCapture)) {
                photo.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
                setFileToSend(new File(fileCapture), "image");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setFileToSend(File file, String type) {
        try {
            imagePath = String.valueOf(file.getAbsolutePath());
            attachment_type = type;
            file1 = file;
            sendAndGetBinaryData(imagePath, type);
        } catch (Exception var3) {
            var3.printStackTrace();
            Log.e("second uploading erroe", var3.getMessage());
        }
    }

    public final void sendAndGetBinaryData(@NotNull String path, @NotNull String type) {
        Intrinsics.checkParameterIsNotNull(path, "path");
        Intrinsics.checkParameterIsNotNull(type, "type");
        String uni_code = String.valueOf(System.currentTimeMillis());
        media_path.put(uni_code, path);
        Log.e("MediaSize", String.valueOf(media_path.size()));
        if (media_path.size() > 0) {
//            if (!socket.connected())
//                socket.connect();

            uploadFileOnServer(media_path, type);
        }
    }

    @SuppressLint({"ObsoleteSdkInt"})
    private final void uploadFileOnServer(HashMap map, String type) {
        Log.e(TAG, "uploadFileOnServer: " + map.keySet());
        if (map.size() > 0) {
            Iterator var4 = map.keySet().iterator();
            if (var4.hasNext()) {
                String entry = (String) var4.next();
                String value = (String) map.get(entry);
                if (Build.VERSION.SDK_INT >= 11) {
                    Intrinsics.checkExpressionValueIsNotNull(entry, "entry");
                    new FileUploadTask(value, entry, roomId, receiverId, media_path, type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
                }
            }
        }
    }

    private String getThumbnail() {
        String mData = "";
        try {
            Bitmap bmp = ThumbnailUtils.createVideoThumbnail(file1.getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bmp.recycle();
            mData = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mData;
    }

    public void openOtherProfile(View view) {
        intent = new Intent(mContext, OtherProfileActivity.class);
        intent.putExtra("userId", receiverId);
        startActivity(intent);
    }

    //===============================================================================================

    private void checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    public void setUpReplay(Datum chatDatum) {
        clReplay.setVisibility(View.VISIBLE);
        chatRepliedId = chatDatum.getId();
        if (((ChatDetailsActivity) mContext).senderId.equals(chatDatum.getSenderId())) {
            txtSenderNameReplay.setText(senderName);
        } else {
            txtSenderNameReplay.setText(receiver_name);
        }

        if (chatDatum.getMessageType().equals("text")) {
            txtMessageReplay.setText(chatDatum.getMessage());
            imgReplay.setVisibility(View.GONE);
        } else {
            txtMessageReplay.setText(chatDatum.getMessageType());
            imgReplay.setVisibility(View.VISIBLE);

            if (chatDatum.getMessageType().equals("image") || chatDatum.getMessageType().equals("gif")) {
                Glide.with(mContext)
                        .load(chatDetailsAdapter.CHAT_MEDIA_URL + chatDatum.getMessage())
                        .apply(defultImg)
                        .into(imgReplay);

            } else if (chatDatum.getMessageType().equals("document")) {
                Glide.with(mContext)
                        .load(R.drawable.doc_sender)
                        .apply(defultImg)
                        .into(imgReplay);

            } else if (chatDatum.getMessageType().equals("video")) {
                Glide.with(mContext)
                        .load(chatDetailsAdapter.CHAT_MEDIA_URL + chatDatum.getThumb())
                        .apply(defultImg)
                        .into(imgReplay);

            } else if (chatDatum.getMessageType().equals("audio")) {
                Glide.with(mContext)
                        .load(R.drawable.ic_baseline_mic_24)
                        .apply(defultImg)
                        .into(imgReplay);


            }
        }

        imgCloseReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clReplay.setVisibility(View.GONE);
            }
        });
    }

//    public void sendMsgWithEmoji(String msg) {
//        if (!isNetworkConnected())
//            return;
//
//        SendEmojiRequest sendEmojiRequest = new SendEmojiRequest();
//        sendEmojiRequest.setRoom_id(roomId);
//        sendEmojiRequest.setReceiver_id(receiverId);
//        sendEmojiRequest.setMessage(msg);
//
//        showProgressDialog();
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<CommonResponse> call = apiInterface.sendEmoji(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID), sendEmojiRequest);
//        call.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                hideProgressDialog();
//                Log.e("response code -->", "" + response.code());
//
//                if (response.isSuccessful()) {
//                    edtMessage.setText("");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse> call, Throwable t) {
//                hideProgressDialog();
//                showToast(t.getMessage());
//            }
//        });
//    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            final String hms = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(updatedTime)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(updatedTime)),
                    TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(updatedTime)));
            long lastsec = TimeUnit.MILLISECONDS.toSeconds(updatedTime)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                    .toMinutes(updatedTime));
            System.out.println(lastsec + " hms " + hms);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (recordTimeText != null)
                            recordTimeText.setText(hms);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            });
        }
    }

    @SuppressLint({"StaticFieldLeak"})
    public final class FileUploadTask extends AsyncTask {
        private final UploadFileMoreDataReqListener mUploadFileMoreDataReqListener;
        //        @NotNull
//        public JSONObject res1;
//        private String attachment_type;
        @NotNull
        private String myFlag;
        private UploadFileMoreDataReqListener callback;
        private FileUploadManager mFileUploadManager;
        @NotNull
        private Emitter.Listener uploadFileMoreDataReq;
        @NotNull
        private Emitter.Listener onCompletedddd;
        @Nullable
        private String file_path;
        @NotNull
        private String uni_code;
        @NotNull
        private String room_id;
        @NotNull
        private String receiver_id;
        @NotNull
        private HashMap media_path;
        @NotNull
        private String type;

        public FileUploadTask(@Nullable String file_path, @NotNull String uni_code, @NotNull String room_id, @NotNull String receiver_id, @NotNull HashMap media_path, @NotNull String type) {
            myFlag = "new";
            mUploadFileMoreDataReqListener = new UploadFileMoreDataReqListener() {
                public void err(@NotNull JSONException e) {
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    e.printStackTrace();
                }

                public void uploadChunck(int offset, int percent) {
                    Log.e(TAG, "percent" + percent + " offset:" + offset);
                    FileUploadManager var10000 = mFileUploadManager;
                    if (var10000 == null) {
                        Intrinsics.throwNpe();
                    }

                    var10000.read(offset);
                    Socket var7 = socket;
                    if (var7 == null) {
                        Intrinsics.throwNpe();
                    }

                    if (var7.connected()) {
                        JSONObject res = new JSONObject();
                        JSONArray jsonArr = new JSONArray();

                        try {
                            Log.e("MyFlag", myFlag + " type:" + attachment_type);
                            FileUploadManager var10002 = mFileUploadManager;
                            if (var10002 == null) {
                                Intrinsics.throwNpe();
                            }

                            res.put("Name", var10002.getFileName());
                            var10002 = mFileUploadManager;
                            if (var10002 == null) {
                                Intrinsics.throwNpe();
                            }

                            res.put("Size", var10002.getFileSize());
                            res.put("device_type", "android");
                            res.put("sender_id", PreferenceFile.getInstance().getPreferenceData(mContext, unilife.com.unilife.retrofit.WebUrls.getKEY_USERID()));
                            if (!receiverId.equals("")) {
                                res.put("receiver_id", receiverId);
                            } else {
                                res.put("group_id", "");
                            }

                            if (clReplay.getVisibility() == View.VISIBLE) {
                                res.put("chat_id", chatRepliedId);
                            } else {
                                res.put("chat_id", "");
                            }

                            res.put("message_type", attachment_type);
                            res.put("room_id", roomId);
                            res.put("flag", "send");
                            res.put("filepath", "");
//                            res.put("filepath", file1.getAbsolutePath());

                            if (attachment_type.equals("video")) {
                                res.put("thumb", getThumbnail());
                            } else {
                                res.put("thumb", "");
                            }

                            var10002 = mFileUploadManager;
                            if (var10002 == null) {
                                Intrinsics.throwNpe();
                            }

                            res.put("Data", var10002.getData());
                            jsonArr.put(res);
                            var7 = socket;
                            if (var7 == null) {
                                Intrinsics.throwNpe();
                            }

                            clReplay.setVisibility(View.GONE);
                            var7.emit("uploadFileChuncks", jsonArr);
                            Log.e(TAG, "uploadChunck:res " + res);
                        } catch (Exception var6) {
                            var6.getMessage();
                        }
                    }

                }
            };
            uploadFileMoreDataReq = new Emitter.Listener() {
                public final void call(Object[] args) {
                    Log.e(TAG, "setUploadFileMoreDataReqListener-- " + args[0].toString());

                    UploadFileMoreDataReqListener var10000;
                    try {
                        Object var6 = args[0];
                        if (args[0] == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.json.JSONObject");
                        }

                        JSONObject json_data = (JSONObject) var6;
                        int place = json_data.getInt("Place");
                        int percent = json_data.getInt("Percent");
//                        setMyFlag("old");
                        publishProgress(new Integer[]{json_data.getInt("Percent")});
                        var10000 = callback;
                        if (var10000 == null) {
                            Intrinsics.throwNpe();
                        }

                        var10000.uploadChunck(place, percent);
                    } catch (JSONException var5) {
                        var10000 = callback;
                        if (var10000 == null) {
                            Intrinsics.throwNpe();
                        }

                        var10000.err(var5);
                    }

                }
            };
            onCompletedddd = new Emitter.Listener() {
                public void call(@NotNull Object... args) {
                    hideProgressDialog();
                }
            };
        }

        protected void onPreExecute() {
            showProgressDialog();
            mFileUploadManager = new FileUploadManager();
        }

        @Nullable
        protected String doInBackground(@NotNull String... params) {
            Intrinsics.checkParameterIsNotNull(params, "params");
            Socket var10000 = socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            boolean isSuccess = var10000.connected();
            Log.e(TAG, "isSuccesssss" + isSuccess);
            if (isSuccess) {
                FileUploadManager var7 = mFileUploadManager;
                if (var7 == null) {
                    Intrinsics.throwNpe();
                }

                String var10001 = file1.getAbsolutePath();
                if (var10001 == null) {
                    Intrinsics.throwNpe();
                }

                var7.prepare(var10001);
                Log.e(TAG, "file_path" + file_path);
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener);
                setUploadFileCompleteListener();
                var10000 = socket;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                if (var10000.connected()) {
                    JSONObject res = new JSONObject();
                    JSONArray jsonArr = new JSONArray();

                    try {
                        FileUploadManager var10002 = mFileUploadManager;
                        if (var10002 == null) {
                            Intrinsics.throwNpe();
                        }

                        res.put("Name", var10002.getFileName());
                        var10002 = mFileUploadManager;
                        if (var10002 == null) {
                            Intrinsics.throwNpe();
                        }

                        res.put("Size", var10002.getFileSize());
                        jsonArr.put(res);
                        var10000 = socket;
                        if (var10000 == null) {
                            Intrinsics.throwNpe();
                        }

                        var10000.emit("uploadFileStart", jsonArr);
                        Log.e(TAG, "uploadFileStart:" + res.toString() + " jsonArr : " + jsonArr.toString());
                    } catch (Exception var6) {
                        var6.printStackTrace();
                    }
                }
            }

            return null;
        }

        // $FF: synthetic method
        // $FF: bridge method
        public Object doInBackground(Object[] var1) {
            return doInBackground((String[]) var1);
        }

        protected void onPostExecute(@Nullable String s) {
            if (s != null) {
                if (StringsKt.equals(s, "OK", true)) {
                    Log.e(TAG, "OnPost");
                    media_path.remove(uni_code);
                    FileUploadManager var10000 = mFileUploadManager;
                    if (var10000 == null) {
                        Intrinsics.throwNpe();
                    }

                    var10000.close();
                    Socket var2 = socket;
                    if (var2 == null) {
                        Intrinsics.throwNpe();
                    }

                    var2.off("uploadFileMoreDataReq", uploadFileMoreDataReq);
                    var2 = socket;
                    if (var2 == null) {
                        Intrinsics.throwNpe();
                    }

                    var2.off("uploadFileCompleteRes", onCompletedddd);
                    uploadFileOnServer(media_path);
                }

            }
        }

        // $FF: synthetic method
        // $FF: bridge method
        public void onPostExecute(Object var1) {
            onPostExecute((String) var1);
        }

        @SuppressLint({"ObsoleteSdkInt"})
        private final void uploadFileOnServer(HashMap map) {
            Log.e(TAG, "uploadFileOnServer: " + map.keySet());
            if (map.size() > 0) {
                Iterator var3 = map.keySet().iterator();
                if (var3.hasNext()) {
                    String entry = (String) var3.next();
                    String value = (String) map.get(entry);
                    if (Build.VERSION.SDK_INT >= 11) {
                        Intrinsics.checkExpressionValueIsNotNull(entry, "entry");
                        new FileUploadTask(value, entry, room_id, receiver_id, media_path, type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{""});
                    }
                }
            }
        }

        private final void setUploadFileMoreDataReqListener(UploadFileMoreDataReqListener callbackk) {
            Log.e(TAG, "setUploadFileMoreDataReqListener:setUploadFileMoreDataReqListener: ");
            callback = callbackk;
            Socket var10000 = socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.on("uploadFileMoreDataReq", uploadFileMoreDataReq);
        }

        private final void setUploadFileCompleteListener() {
            Socket var10000 = socket;
            if (var10000 == null) {
                Intrinsics.throwNpe();
            }

            var10000.on("uploadFileCompleteRes", onCompletedddd);
        }
    }

    public class SendEmojiRequest {
        String room_id;
        String message;
        String receiver_id;

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }
    }

}