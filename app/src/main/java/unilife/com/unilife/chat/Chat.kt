package unilife.com.unilife.chat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.media.MediaRecorder
import android.media.ThumbnailUtils
import android.media.ToneGenerator
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vincent.filepicker.Constant
import com.vincent.filepicker.Constant.REQUEST_CODE_PICK_FILE
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.vincent.filepicker.filter.entity.NormalFile
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.back_icon_toolbar.ivBackArrow
import kotlinx.android.synthetic.main.back_icon_toolbar.ivProfileImg
import kotlinx.android.synthetic.main.chat_layout.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import unilife.com.unilife.BuildConfig
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.adapter.ChatDetailsAdapter
import unilife.com.unilife.chat.groupdetails.GroupDetail
import unilife.com.unilife.chat.individualChat.IndividualChatDetail
import unilife.com.unilife.chat.update.chatresponse.ChatResponse
import unilife.com.unilife.home.ShowOtherProfile
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.socketConnection.SocketSSL
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class Chat : AppCompatActivity(), View.OnClickListener, RetrofitResponse,
    View.OnTouchListener {

    private var chatArrayList = ArrayList<ChatModel>()
    private var chatAdapter: ChatDetailsAdapter? =null

    private var roomId = ""
    private var wallpaperData = ""
    private var wallpaperType = ""

    private var receiverId: String = ""
    private var receiver_name: String = ""
    private var receiver_profile_image: String = ""

    var groupId: String = ""
    var groupName: String = ""
    var groupImg: String = ""

    var myAudioRecorder: MediaRecorder? = null
    lateinit var toneGen1: ToneGenerator

    val TAG = Chat::class.java.simpleName

    private var socket: Socket? = null
    private var uri: Uri? = null
    private var file: File? = null
    private var file1: File? = null

    var outputFile = ""
    var attachment_type = ""
    var value = 0
    var imagePath = ""
    var bitmapImagePath = ""
    var isGroup = false

    var selectedMediaPaths: ArrayList<String>? = ArrayList()
    private var media_path = HashMap<String, String>()

    private var part: MultipartBody.Part? = null

    private var progressDialog: Dialog? = null

    var popupwindow_obj: PopupWindow? = null

    var modeType: String = ""
    var chatRepliedId: String = ""

    companion object {
        var chatAHInterface: ChatAdapterHandlerInterface? = null
        var bitmapThumb: Bitmap? = null
        var isChatRunning = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)
//        toneGen1 = ToneGenerator(AudioManager.STREAM_ALARM, 100)
//        imgNotification.background = getDrawable(R.drawable.dot_line)
//        imgNotification.backgroundTintList =
//            ColorStateList.valueOf(resources.getColor(R.color.white))
//
//        getIds()
//
//        openEmojiKeyboard()
//
//        getIntentData()
//        setOnClickListener()
//        setChatAdapter()
//
//        edtMessage.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if (s.toString().length > 0) {
//                    imgSend.visibility = VISIBLE
//                    imgAdd.visibility = GONE
//                    imgAudio.visibility = GONE
//                } else {
//                    imgSend.visibility = GONE
//                    imgAdd.visibility = VISIBLE
//                    imgAudio.visibility = VISIBLE
//                }
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
    }

    override fun onStart() {
        isChatRunning = true
        super.onStart()

    }

    override fun onStop() {
        isChatRunning = false
        super.onStop()
    }

    fun setOnClickListener() {
        ivBackArrow.setOnClickListener(this)
        imgNotification.setOnClickListener(this)
        imgAdd.setOnClickListener(this)
        imgCamera.setOnClickListener(this)
//        cancelButton.setOnClickListener(this)
        ivProfileImg.setOnClickListener(this)
        textName.setOnClickListener(this)
    }

    private fun getIntentData() {
        try {
            if (intent != null) {

                Log.e("RECEIVERID:", receiverId)

                if (intent.hasExtra("receiver_id")) {

                    groupId = ""
                    isGroup = false
                    receiverId = intent.getStringExtra("receiver_id")
                    receiver_name = intent.getStringExtra("receiver_name")
                    receiver_profile_image = intent.getStringExtra("receiver_profile_image")

                    ivProfileImg.visibility = VISIBLE

                    if (receiver_profile_image != "") {
                        Picasso.with(this)
                            .load(WebUrls.PROFILE_IMAGE_URL + receiver_profile_image)
                            .placeholder(R.drawable.no_image).into(ivProfileImg)
                    } else {
                        ivProfileImg.setImageResource(R.drawable.no_image)
                    }

                    textName.text = receiver_name


                } else {  // group

                    receiverId = ""
                    isGroup = true
                    groupId = intent.getStringExtra("groupId")
                    groupName = intent.getStringExtra("groupName")
                    groupImg = intent.getStringExtra("groupImg")

                    ivProfileImg.visibility = VISIBLE

                    if (groupImg != "") {
                        Picasso.with(this)
                            .load(WebUrls.PROFILE_IMAGE_URL + groupImg)
                            .placeholder(R.drawable.no_image).into(ivProfileImg)
                    } else {
                        ivProfileImg.setImageResource(R.drawable.no_image)
                    }

                    textName.text = groupName


                }

                createRoom()

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getIds() {

        imgAudio.setOnTouchListener(this)
        imgSend.setOnClickListener(this)
//        llBack.setOnClickListener(this)
        imgCamera.setOnClickListener(this)

//        llChatEmoji.setOnClickListener(this)
    }

    private fun setChatAdapter() {
        var layoutmanager = LinearLayoutManager(this)
        recyclerChat.layoutManager = layoutmanager
        recyclerChat.setHasFixedSize(true)

        val messageSwipeController = MessageSwipeController(this, object : SwipeControllerActions {
            override fun showReplyUI(position: Int) {
                try {
//                    Log.e(TAG, "pos: " + position+" chatArrayList: "+chatArrayList.size)
//                    Log.e(TAG, " MSG : " + chatArrayList.get(position).message)
                    showQuotedMessage(chatArrayList[position], position)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        })

//        val itemTouchHelper = ItemTouchHelper(messageSwipeController)
//        itemTouchHelper.attachToRecyclerView(recyclerChat)


    }

//    private fun getChatAdapterInterface() {
//
//        chatAdapter.getChatInterface(object : ChatDetailsAdapter.ChatInterface {
//            override fun itemClick(pos: Int, chatList: ArrayList<ChatModel>) {
//                try {
//                    startActivity(
//                        Intent(this@Chat, FullMediaScreen::class.java)
//                            .putExtra("file_type", chatList[pos].fileType)
//                            .putExtra("message_type", chatList[pos].messageType)
//                            .putExtra("message", chatList[pos].message)
//                    )
//                } catch (e: java.lang.Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun openEnlargeFragment(chatModel: ChatModel) {
//
//
//            }
//        })
//    }

    private fun connectSocket() {

        socket = IO.socket(WebUrls.SOCKET_BASE_URL, SocketSSL().certificate())

        socket!!.on(Socket.EVENT_CONNECT, onConnect)
        socket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)

        socket!!.on(WebUrls.LISTEN_ROOM_JOIN, onRoomJoinListener)
        socket!!.on(WebUrls.LEAVE_ROOM, onRoomLeaveListener)
        socket!!.on(WebUrls.LISTEN_SEND_MESSAGE, onSendMessageListener)
        socket!!.on(WebUrls.LISTEN_SEEN, onSeenListener)
        socket!!.on(WebUrls.GROUP_SEEN_EMIT_LISTEN, onGroupSeenListener)

        socket!!.connect()

        joinRoom(roomId)

    }

    var onRoomJoinListener = Emitter.Listener { args ->
        Log.e(TAG, "onRoomJoinListener:" + args[0].toString())
    }

    var onRoomLeaveListener = Emitter.Listener { args ->
        Log.e(TAG, "onRoomLeaveListener:" + args[0].toString())
    }

    var onSeenListener = Emitter.Listener { args ->
        Log.e(TAG, "onSeenListener:" + args[0].toString())
    }

    var onGroupSeenListener = Emitter.Listener { args ->
        Log.e(TAG, "onGroupSeenListener:" + args[0].toString())
    }

    var onSendMessageListener = Emitter.Listener { args ->

        Log.e(TAG, "onSendMessageListener" + args[0].toString())

        if (args[0].toString().trim().isNotEmpty()) {

            try {

                var jsonObject = JSONObject(args[0].toString())

                var chatModel = ChatModel()

                chatModel.id = jsonObject.getString("id")
                chatModel.senderId = jsonObject.getString("sender_id")
                chatModel.roomId = jsonObject.getString("room_id")
                chatModel.message = jsonObject.getString("message")

                chatModel.only_date = jsonObject.getString("only_date")
                chatModel.date = jsonObject.getString("date")
                chatModel.seen = jsonObject.getString("seen")


                if (jsonObject.getString("message_type").equals("text")) {
                    chatModel.messageType = "text"
                } else {
                    chatModel.messageType = "media"
                }

                if (jsonObject.has("thumb") && jsonObject.isNull("thumb")) {
                    chatModel.thumb = ""
                } else {
                    chatModel.thumb = jsonObject.getString("thumb")
                }

                chatModel.fileType = jsonObject.getString("message_type")

                chatModel.isDeleted = jsonObject.getString("is_deleted")
                chatModel.createdAt = jsonObject.getString("created_at")
                chatModel.updatedAt = jsonObject.getString("updated_at")


                if (jsonObject.has("group_id") && !jsonObject.isNull("group_id")) {

                    chatModel.receiverId = ""
                    chatModel.groupId = jsonObject.getString("group_id")


                    if (jsonObject.has("sender_user_chat") && !jsonObject.isNull("sender_user_chat")) {

                        var sender_user_chat = jsonObject.getJSONObject("sender_user_chat")

                        chatModel.receiverName =
                            sender_user_chat.getString("username")
                        chatModel.receiverProfileImage =
                            sender_user_chat.getString("profile_image")

                        Log.e(
                            TAG, "onGroupSeenListener:GID:>>" + chatModel.receiverName
                                    + " " + chatModel.receiverProfileImage
                        )
                    }

                    chatModel.senderName = PreferenceFile.getInstance()
                        .getPreferenceData(this, WebUrls.KEY_USERNAME)
                    chatModel.senderProfile = PreferenceFile.getInstance()
                        .getPreferenceData(this, WebUrls.KEY_PROFILE_IMAGE)


                    //emitter emit

                    try {

                        var jsonObject1 = JSONObject()
                        jsonObject1.put("room_id", jsonObject.getString("room_id"))
                        jsonObject1.put("chat_id", jsonObject.getString("id"))
                        jsonObject1.put(
                            "sender_id",
                            PreferenceFile.getInstance().getPreferenceData(
                                this,
                                WebUrls.KEY_USERID
                            )
                        )
//                                            Log.e("GROUP_SEEN_EMIT_LISTEN", jsonObject.toString())
                        socket!!.emit(
                            WebUrls.GROUP_SEEN_EMIT_LISTEN,
                            jsonObject1
                        )

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                } else {

                    chatModel.receiverId = jsonObject.getString("receiver_id")
                    chatModel.groupId = ""


                    if (jsonObject.has("sender_user_chat")) { //login user details

                        var sender_user_chatObject = jsonObject.getJSONObject("sender_user_chat")

                        chatModel.senderName =
                            sender_user_chatObject.getString("username")
                        chatModel.senderProfile =
                            sender_user_chatObject.getString("profile_image")
                    }

                    if (jsonObject.has("receiver_user_chat")) {

                        var receiver_user_chatObject =
                            jsonObject.getJSONObject("receiver_user_chat")

                        chatModel.receiverName =
                            receiver_user_chatObject.getString("username")
                        chatModel.receiverProfileImage =
                            receiver_user_chatObject.getString("profile_image")
                    }


                    //emitter emit

                    try {

                        var jsonObject1 = JSONObject()
                        jsonObject1.put("room_id", jsonObject.getString("room_id"))
                        jsonObject1.put(
                            "receiver_id",
                            PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                        )
                        Log.e("SEEN", jsonObject.toString())
                        socket!!.emit(WebUrls.SEEN, jsonObject1)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }


                // replied messages data

                if (jsonObject.has("chat_slide") && !jsonObject.isNull("chat_slide")) {

                    val chat_slideObj = jsonObject.getJSONObject("chat_slide")

                    chatModel.replied_chat_id = chat_slideObj.getString("id")
                    chatModel.replied_id = chat_slideObj.getString("id")
                    chatModel.replied_text = chat_slideObj.getString("message")
                    chatModel.replied_file_type = chat_slideObj.getString("message_type")

                    if (chat_slideObj.getString("message_type").equals("text")) {
                        chatModel.replied_message_type = "text"
                    } else {
                        chatModel.replied_message_type = "media"
                    }

                    if (chat_slideObj.has("thumb") && chat_slideObj.isNull("thumb")) {
                        chatModel.replied_thumb = ""
                    } else {
                        chatModel.replied_thumb = chat_slideObj.getString("thumb")
                    }

                } else {
                    chatModel.replied_chat_id = ""
                    chatModel.replied_id = ""
                    chatModel.replied_text = ""
                    chatModel.replied_file_type = ""
                    chatModel.replied_message_type = ""
                    chatModel.replied_thumb = ""
                }

                /* runOnUiThread {
                     chatAdapter.addChatModel(chatModel, recyclerChat)
                     edtMessage.setText("")
                     Log.e(TAG,"LIST_SIZE:"+chatArrayList.size)
                 }*/


                runOnUiThread {
                    if (chatArrayList.size > 0) {
                        chatArrayList.add(chatModel)
                        chatAdapter?.notifyDataSetChanged()
                        recyclerChat.smoothScrollToPosition(chatArrayList.size - 1)
                    } else {
                        chatArrayList.add(chatModel)
                        setAdapter()
                    }
                    edtMessage.setText("")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun setAdapter() {
        chatAdapter = ChatDetailsAdapter(this)
        recyclerChat.adapter = chatAdapter
//        getChatAdapterInterface()

    }

    var onConnect = Emitter.Listener { args ->
        Log.e(TAG, "onConnect")
    }

    var onDisconnect = Emitter.Listener { args ->
        Log.e(TAG, "onDisconnect")
        socket!!.connect()
        if (!imagePath.equals("")) {
            sendAndGetBinaryData(imagePath, attachment_type)
        }

    }

    var onConnectError = Emitter.Listener { args ->
        Log.e(TAG, "onConnectError")
        socket!!.connect()
    }

    fun joinRoom(roomId: String) {
        try {

            Log.e(TAG, "JOIN_ROOM:$roomId")
            var jsonObject = JSONObject()
            jsonObject.put(WebUrls.ROOM_ID, roomId)
            socket!!.emit(WebUrls.JOIN_ROOM, jsonObject)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leaveRoom(roomId: String) {
        try {

            Log.e(TAG, "leaveRoom" + roomId)
            var jsonObject = JSONObject()
            jsonObject.put(WebUrls.ROOM_ID, roomId)
            socket!!.emit(WebUrls.LEAVE_ROOM, jsonObject)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectSocket()
        cancelDialog()
    }

    private fun disconnectSocket() {

        try {

            leaveRoom(roomId)

            if (socket != null) {

                socket!!.disconnect()
                socket!!.off(io.socket.client.Socket.EVENT_CONNECT, onConnect)
                socket!!.off(io.socket.client.Socket.EVENT_DISCONNECT, onDisconnect)
                socket!!.off(io.socket.client.Socket.EVENT_CONNECT_ERROR, onConnectError)
                socket!!.off(io.socket.client.Socket.EVENT_CONNECT_TIMEOUT, onConnectError)

                socket!!.off(WebUrls.LISTEN_SEND_MESSAGE, onSendMessageListener)
                socket!!.off(WebUrls.LEAVE_ROOM, onRoomLeaveListener)
                socket!!.off(WebUrls.JOIN_ROOM, onRoomJoinListener)
                socket!!.off(WebUrls.LISTEN_SEEN, onSeenListener)
                socket!!.off(WebUrls.GROUP_SEEN_EMIT_LISTEN, onGroupSeenListener)

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    private fun blockUserService() {
        if (Alerts.isNetworkAvailable(this)) {
            try {

                var jsonObject = JSONObject()

                jsonObject.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                jsonObject.put("block_id", receiverId)

                Log.e("url",WebUrls.BASE_URL+WebUrls.BLOCK_USER);
                RetrofitService(
                    this, this, WebUrls.BLOCK_USER,
                    WebUrls.BLOCK_USER_CODE, 3, jsonObject

                ).callService(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun unjoinGroup(groupId: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("group_id", groupId)

                Log.e(TAG, postParam.toString())

                RetrofitService(
                    this,
                    this,
                    WebUrls.UNJOIN_GROUP,
                    WebUrls.UNJOIN_GROUP_REQ_CODE,
                    3,
                    postParam
                ).callService(true)


            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

/*  fun onBack() {

      if(chatAHInterface!=null)
      {
          Log.e("NullMediaPlayer","NotNull")

          chatAHInterface!!.stopMediaPlayer()
      }
      else
      {
          Log.e("NullMediaPlayer","Null")

      }

      val manager = activity!!.supportFragmentManager
      val trans = manager.beginTransaction()
      trans.remove(this@Chat)
      trans.commit()
      manager.popBackStack()
      if (inboxCallBack != null) {
          inboxCallBack!!.hitApi()
      }

  }*/

    private fun showQuotedMessage(chatModel: Chat.ChatModel, pos: Int) {

//        edtMessage.requestFocus()
//
//        Log.e("TYPE:::", chatModel.messageType + " fileType : " + chatModel.fileType)
//
//        val inputMethodManager =
//            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager?.showSoftInput(edtMessage, InputMethodManager.SHOW_IMPLICIT)
//        txtQuotedMsg.text = chatModel.message
//
//        Picasso.with(this).load(
//            WebUrls.CHAT_MEDIA_URL + PreferenceFile.getInstance()
//                .getPreferenceData(this@Chat, WebUrls.KEY_PROFILE_IMAGE)
//        )
//            .placeholder(R.drawable.no_image)
//            .resize(25, 25)
//            .into(civImageProfile)
//
//        tvName.text =
//            PreferenceFile.getInstance().getPreferenceData(this@Chat, WebUrls.KEY_USERNAME)
//        chatRepliedId = chatModel.id
//
//        tvName.text = chatModel.senderName
//        reply_layout.visibility = View.VISIBLE

    }

    private fun hideReplyLayout() {
        runOnUiThread(Runnable() {
            kotlin.run {
//                reply_layout.visibility = View.GONE
            }
        });
    }

    override fun onClick(v: View?) {

        when (v!!.id) {

//            R.id.cancelButton -> {
//                Common.hideKeyboard(this, v)
//                chatRepliedId = ""
//                hideReplyLayout()
//            }

            R.id.imgSend -> {

                Log.e(TAG, "imgSend")

                if (edtMessage.text.toString().trim().isNotEmpty()) {

                    try {

                        var jsonObject = JSONObject()
                        //          jsonObject.put(WebUrls.ROOM_ID, roomId)
                        jsonObject.put(
                            WebUrls.SENDER_ID,
                            PreferenceFile.getInstance().getPreferenceData(
                                this,
                                WebUrls.KEY_USERID
                            )!!
                        )
                        jsonObject.put(WebUrls.MESSAGE, edtMessage.text.toString().trim())

                        if (receiverId != "") {
                            jsonObject.put(WebUrls.RECEIVER_ID, receiverId)
                            jsonObject.put(WebUrls.GROUP_ID, "")
                        } else {
                            jsonObject.put(WebUrls.GROUP_ID, groupId)
                            jsonObject.put(WebUrls.RECEIVER_ID, "")
                        }

//                        if (reply_layout.isShown) {
//                            jsonObject.put("chat_id", chatRepliedId)
//                        } else {
//                            jsonObject.put("chat_id", "")
//                        }

                        Log.e(TAG, "SEND_MESSAGE$jsonObject")

                        socket!!.emit(WebUrls.SEND_MESSAGE, jsonObject)

                        hideReplyLayout()

                        chatRepliedId = ""


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            R.id.imgNotification -> {
                Log.e(TAG, "imgNotification")
                Common.hideKeyboard(this, v)
                popupwindow_obj = showPopUpMenu()
                popupwindow_obj!!.showAsDropDown(imgNotification)
            }

            R.id.ivBackArrow -> {
                Common.hideKeyboard(this, v)
                onBackPressed()
            }

            R.id.imgCamera -> {
                Common.hideKeyboard(this, v)
                launchCamera()
            }

            R.id.imgAdd -> {
                Common.hideKeyboard(this, v)
                uploadAttachmentsGallery()
            }

//            R.id.llChatEmoji -> {
//                Common.hideKeyboard(this, v)
//
////                if (emojiPopup != null) {
////                    emojiPopup.toggle()
////                }
//            }

            R.id.ivProfileImg -> {
                Log.e("scfsvzsdvd", "" + receiverId)
                Common.hideKeyboard(this, v)
                startActivity(
                    Intent(this, ShowOtherProfile::class.java).putExtra(
                        "post_user_id",
                        receiverId
                    )
                )

            }

            R.id.textName -> {
                try {
                    Common.hideKeyboard(this, v)
                    if (isGroup) {


                        var intent = Intent(this, GroupDetail::class.java)

                        intent.putExtra("groupId", groupId)
                        intent.putExtra("groupName", groupName)
                        intent.putExtra("groupImg", groupImg)
                        intent.putExtra("roomId", roomId)
                        startActivity(intent)
                    } else {
                        var intent = Intent(this, IndividualChatDetail::class.java)
                        intent.putExtra("receiverId", receiverId)
                        intent.putExtra("receiver_name", receiver_name)
                        intent.putExtra("receiver_profile_image", receiver_profile_image)
                        intent.putExtra("roomId", roomId)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun showPopUpMenu(): PopupWindow {

        var popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        var inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.chat_users_popup_option, null)
        val tvBlockUser = view.findViewById(R.id.tvBlockUser) as TextView
        val SwChangeMode1 = view.findViewById(R.id.SwChangeMode1) as Switch

        popupWindow.isFocusable = true

        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT

        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.grey_outline, null))

        popupWindow.contentView = view

        if (modeType == "black") {
            SwChangeMode1.isChecked = true
        } else if (modeType == "white") {
            SwChangeMode1.isChecked = false
        } else {
            SwChangeMode1.isChecked = false
        }

        if (receiverId != "") {
            tvBlockUser.text = "Block User"
            tvBlockUser.visibility = VISIBLE
        } else {
            tvBlockUser.text = "Leave Group"
            tvBlockUser.visibility = VISIBLE
        }

        tvBlockUser.setOnClickListener {
            try {
                if (receiverId != "") {
                    blockUserService()
                    popupWindow.dismiss()
                } else {
                    unjoinGroup(groupId)
                    popupWindow.dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        SwChangeMode1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                modeType = "black"
            } else {
                modeType = "white"
            }
            try {
                changeMode(modeType)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }


        return popupWindow;
    }

    fun changeMode(modeType: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this@Chat, WebUrls.KEY_USERID)
                )
                postParam.put("type", modeType)
                postParam.put("image", "")

                RetrofitService(
                    this@Chat, this@Chat, WebUrls.UPLOAD_WALLPAPER,
                    WebUrls.UPLOAD_WALLPAPER_CODE, 3, postParam
                ).callService(true)

                Log.e(TAG, "UPLOAD_WALLPAPER_CODE$postParam")

            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when (v!!.id) {

            /* R.id.imgAdd -> {
                 launchCamera()

             }
             R.id.imgCamera -> {
                 launchCamera()

             }*/

            R.id.imgAudio -> {

                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {

                        Log.e(TAG, "Action" + "Down")
                        if (checkRecordAudioPermission()) {
                            try {
                                recordAudio()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 101)
                            }
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        Log.e(TAG, "Action" + "Move")
                    }

                    MotionEvent.ACTION_UP -> {

                        Log.e(TAG, "Action" + "Up")

                        if (myAudioRecorder != null) {

//                            cancelDialog()

//                            chronometer.stop()
//                            clSendText.visibility = VISIBLE
//                            climgAudio.visibility = GONE
//                            clCardRecordAudio.visibility = GONE


                            toneGen1.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200)

                            try {
                                if (myAudioRecorder != null) {
                                    myAudioRecorder!!.stop()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }

                        Log.e(TAG, "Action" + outputFile)

                        var file = File(outputFile)

                        if (file.exists()) {
                            Log.e(TAG, "File Exists" + "Yes")
                            sendAndGetBinaryData(outputFile, "audio")
                        } else {
                            Log.e(TAG, "FileNotExists" + "No")
                        }
                    }
                }
            }
        }
        return true
    }

    fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            try {
                val dir = File(Common.makeDirectory(this))
                file = File(dir, System.currentTimeMillis().toString() + "Unilife.png")
                uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider", file!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(cameraIntent, 501)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1).start(this)
    }

    private fun setImageafterCrop(gettingPath: String?) {

        try {
            val bitmap: Bitmap = BitmapFactory.decodeFile(gettingPath)

            attachment_type = "image"

            imagePath = gettingPath.toString()

            file1 = File(gettingPath.toString())

            sendAndGetBinaryData(imagePath, attachment_type)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkRecordAudioPermission(): Boolean {
        val permission = Manifest.permission.RECORD_AUDIO
        val res = this!!.checkCallingOrSelfPermission(permission)
        return res == PackageManager.PERMISSION_GRANTED
    }

    private fun recordAudio() {

        outputFile = this.cacheDir.absolutePath + "/" + UUID.randomUUID().toString() + ".m4a"
//            this!!.cacheDir.absolutePath + "/" + UUID.randomUUID().toString() + ".mp3"

        myAudioRecorder = MediaRecorder()
        myAudioRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        myAudioRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        myAudioRecorder!!.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        myAudioRecorder!!.setAudioSamplingRate(16000)
        myAudioRecorder!!.setOutputFile(outputFile)

        myAudioRecorder!!.prepare()

        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        myAudioRecorder!!.start()

//        showDialog()

//        chronometer.start()
//        chronometer.base = SystemClock.elapsedRealtime()
//
//        clSendText.visibility = GONE
//        clCardRecordAudio.visibility = VISIBLE

//        climgAudio.visibility = VISIBLE

    }

    private fun sendDoc() {

        val types = arrayOf("xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf")

        val intent4 = Intent(this, NormalFilePickActivity::class.java)
        intent4.putExtra(Constant.MAX_NUMBER, 1);
        intent4.putExtra(
            NormalFilePickActivity.SUFFIX, types
        )
//             String [] { "xlsx", "xls", "doc", "docx", "ppt", "pptx", "pdf" });
        startActivityForResult(intent4, REQUEST_CODE_PICK_FILE)


//        val mimeTypes = arrayOf(
//            "application/msword",
//            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
//            // .doc & .docx
//            "text/plain",
//            "application/pdf"
//        )
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
////        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.addCategory(Intent.CATEGORY_OPENABLE)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
//            if (mimeTypes.size > 0) {
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            }
//        } else {
//            var mimeTypesStr = ""
//            for (mimeType in mimeTypes) {
//                mimeTypesStr += "$mimeType|"
//            }
//            intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
//        }
//        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 10)
    }

    private fun callAllChatApi() {
        if (Alerts.isNetworkAvailable(this)) {
            try {

                var jsonObject = JSONObject()

                if (receiverId != "") {

                    jsonObject.put(
                        "sender_id",
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                    )

                    if (!receiverId.equals("")) {
                        jsonObject.put("receiver_id", receiverId)
                    } else {
                        jsonObject.put("group_id", groupId)
                    }
                    Log.e("url",WebUrls.BASE_URL+WebUrls.GET_ALL_CHAT);
                    RetrofitService(
                        this,
                        this,
                        WebUrls.GET_ALL_CHAT,
                        WebUrls.GET_ALL_CHAT_CODE,
                        3,
                        jsonObject
                    ).callService(true)

                } else {

                    jsonObject.put(
                        "sender_id",
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                    )
                    jsonObject.put("group_id", groupId)
                    jsonObject.put("room_id", roomId)

                    RetrofitService(
                        this,
                        this,
                        WebUrls.GROUP_CHAT_DATA,
                        WebUrls.GROUP_CHAT_DATA_REQ_CODE,
                        3,
                        jsonObject
                    ).callService(true)

                }

                Log.e(TAG, "GROUP_CHAT_DATA_REQ_CODE:" + jsonObject.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun createRoom() {

        try {

            var jsonObject = JSONObject()

            if (receiverId != "") {

                jsonObject.put(
                    "sender_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                jsonObject.put("receiver_id", receiverId)
                Log.e("url",WebUrls.BASE_URL+WebUrls.CREATE_ROOM);
                RetrofitService(
                    this,
                    this,
                    WebUrls.CREATE_ROOM,
                    WebUrls.CREATE_ROOM_CODE,
                    3,
                    jsonObject
                ).callService(true)

            } else {

                jsonObject.put(
                    "sender_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                jsonObject.put("group_id", groupId)


                RetrofitService(
                    this,
                    this,
                    WebUrls.CREATE_ROOM_FOR_GROUP,
                    WebUrls.CREATE_ROOM_FOR_GROUP_REQ_CODE,
                    3,
                    jsonObject
                ).callService(true)

            }

            Log.e("createRoom", jsonObject.toString())


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun checkStatusModeWallPaper() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this,
                    this@Chat,
                    WebUrls.SEND_USER_CHAT_WALLPAPER + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    ),
                    WebUrls.SEND_USER_CHAT_WALLPAPER_CODE,
                    1
                ).callService(false)

                Log.e(
                    TAG,
                    "SEND_USER_CHAT_WALLPAPER_CODE" + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    )
                )

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    override fun onResponse(requestCode: Int, response: String) {


        when (requestCode) {

            WebUrls.UPLOAD_WALLPAPER_CODE -> {

                val obj = JSONObject(response)

                Log.e(TAG, "UPLOAD_WALLPAPER_CODE:" + obj.toString())

                val res = obj.getString("response")

                if (obj.getBoolean("response")) {

                    if (obj.has("data") && !obj.isNull("data")) {

                        val statusArr = obj.getJSONArray("data")

                        Log.e(TAG, "statusArr:" + statusArr.get(0))

                        if (statusArr.get(0).toString().equals("1")) {

                            ivMainImage.visibility = GONE

                            if (modeType == "black") {
                                clChatRoot.background = getDrawable(R.color.black)

                            } else if (modeType == "white") {
                                clChatRoot.background = getDrawable(R.color.white)

                            } else {
                                clChatRoot.background = getDrawable(R.color.white)
                            }

//                            Common.customDialog(this, "Unilife", "Mode changed successfully.")

                            if (popupwindow_obj!!.isShowing) {
                                popupwindow_obj!!.dismiss()
                            }
                        }
//                            modeType = statusArr.getString("data")
                    } else {
                        if (ivMainImage.isShown) {
                            ivMainImage.visibility = VISIBLE
                        } else {
                            ivMainImage.visibility = GONE
                        }
                        modeType = ""
                    }

                } else {
                    modeType = ""
                }

            }

            WebUrls.SEND_USER_CHAT_WALLPAPER_CODE -> {

                val obj = JSONObject(response)

                Log.e(TAG, "SEND_USER_CHAT_WALLPAPER_CODE:" + obj.toString())

                val res = obj.getString("response")

                /* if (obj.getBoolean("response")) {

                     if (obj.has("status") && !obj.isNull("status")) {
                         val statusObj = obj.getJSONObject("status")
                         userStatus = statusObj.getString("status")
                     } else {
                         userStatus = ""
                     }

                     if (obj.has("data") && !obj.isNull("data")) {
                         val dataObj = obj.getJSONObject("data")
                         modeType = dataObj.getString("type")
                     } else {
                         modeType = ""
                     }
                 } else {
                     userStatus = ""
                     modeType = ""
                 }

                 popupwindow_obj = showPopUpMenu(userStatus, modeType)
                 popupwindow_obj!!.showAsDropDown(ivNoti)*/
            }

            WebUrls.UNJOIN_GROUP_REQ_CODE -> {

                val obj = JSONObject(response)

                Log.e(TAG, "UNJOIN_GROUP_REQ_CODE:" + obj.toString())

                if (obj.getBoolean("response")) {
                    customDialog(this, "Unilife", obj.getString("message"))

                } else {
                    Common.customDialog(this, "Unilife", obj.getString("message"))
                }

            }

            WebUrls.BLOCK_USER_CODE -> {

                try {

                    var jsonObject = JSONObject(response)

                    Log.e("BLOCK_USER_CODE", jsonObject.toString())

                    if (jsonObject.getBoolean("response")) {

                        customDialog(this, "Unilife", jsonObject.getString("data"))

                    } else {
                        Common.customDialog(this, "Unilife", jsonObject.getString("data"))
                    }


                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.GET_ALL_CHAT_CODE -> {

                try {


                    //======================================================

                    var jsonObject = JSONObject(response)

                    Log.e("GET_ALL_CHAT_CODE", jsonObject.toString())

                    if (jsonObject.getBoolean("response")) {

                        chatArrayList.clear()

                        if (!jsonObject.isNull("data")) {

                            if (jsonObject.has("wallpaper")) {

                                var wallpaperObj = jsonObject.getJSONObject("wallpaper")

                                if (wallpaperObj.has("image") && !wallpaperObj.isNull("image")) {
                                    wallpaperType = "image"
                                    wallpaperData = wallpaperObj.getString("image")
                                    modeType = "white"
                                } else {
                                    wallpaperType = "simple"
                                    modeType = wallpaperObj.getString("type")
                                    wallpaperData = wallpaperObj.getString("type")
                                }
                            } else {
                                wallpaperData = ""
                                wallpaperType = ""
                                modeType = "white"
                            }

                            var jArrayResult = jsonObject.getJSONArray("data")

                            if (jArrayResult.length() > 0) {

                                for (i in 0 until jArrayResult.length()) {

                                    var objResult = jArrayResult.getJSONObject(i)

                                    var chatModel = ChatModel()
                                    chatModel.id = objResult.getString("id")
                                    chatModel.message = objResult.getString("message")
                                    chatModel.senderId = objResult.getString("sender_id")
                                    chatModel.receiverId = objResult.getString("receiver_id")
                                    chatModel.roomId = objResult.getString("room_id")
                                    chatModel.date = objResult.getString("date")
                                    chatModel.seen = objResult.getString("seen")

                                    if (objResult.getString("message_type").equals("text")) {
                                        chatModel.messageType = "text"
                                    } else {
                                        chatModel.messageType = "media"
                                    }

                                    if (objResult.has("thumb") && objResult.isNull("thumb")) {
                                        chatModel.thumb = ""
                                    } else {
                                        chatModel.thumb = objResult.getString("thumb")
                                    }

                                    chatModel.fileType = objResult.getString("message_type")
                                    chatModel.isDeleted = objResult.getString("is_deleted")
                                    chatModel.createdAt = objResult.getString("created_at")
                                    chatModel.updatedAt = objResult.getString("updated_at")

                                    chatModel.only_date = objResult.getString("only_date")

                                    try {

                                        var jsonObject = JSONObject()
                                        jsonObject.put("room_id", roomId)
                                        jsonObject.put(
                                            "receiver_id",
                                            PreferenceFile.getInstance().getPreferenceData(
                                                this,
                                                WebUrls.KEY_USERID
                                            )
                                        )
                                        Log.e("SEEN", jsonObject.toString())
                                        socket!!.emit(WebUrls.SEEN, jsonObject)

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                    if (i == 0) {

                                        chatModel.wallpaperData = wallpaperData
                                        chatModel.wallpaperType = wallpaperType

                                        if (!jsonObject.isNull("user_detail")) {

                                            var userDetailObj =
                                                jsonObject.getJSONArray("user_detail")

                                            for (j in 0 until userDetailObj.length()) {

                                                var userDetailObjData =
                                                    userDetailObj.getJSONObject(j)

                                                if (j == 0) {
                                                    chatModel.receiverName =
                                                        userDetailObjData.getString("username")
                                                    chatModel.receiverProfileImage =
                                                        userDetailObjData.getString("profile_image")
                                                }

                                                if (j == 1) {  //login user details
                                                    chatModel.senderName =
                                                        userDetailObjData.getString("username")
                                                    chatModel.senderProfile =
                                                        userDetailObjData.getString("profile_image")
                                                }
                                            }
                                        }
                                    }

                                    // replied messages data

                                    if (objResult.has("chat_slide") && !objResult.isNull("chat_slide")) {

                                        val chat_slideObj = objResult.getJSONObject("chat_slide")

                                        chatModel.replied_chat_id = chat_slideObj.getString("id")
                                        chatModel.replied_id = chat_slideObj.getString("id")
                                        chatModel.replied_text = chat_slideObj.getString("message")
                                        chatModel.replied_file_type =
                                            chat_slideObj.getString("message_type")

                                        if (chat_slideObj.getString("message_type").equals("text")) {
                                            chatModel.replied_message_type = "text"
                                        } else {
                                            chatModel.replied_message_type = "media"
                                        }

                                        if (chat_slideObj.has("thumb") && chat_slideObj.isNull("thumb")) {
                                            chatModel.replied_thumb = ""
                                        } else {
                                            chatModel.replied_thumb =
                                                chat_slideObj.getString("thumb")
                                        }

                                    } else {
                                        chatModel.replied_chat_id = ""
                                        chatModel.replied_id = ""
                                        chatModel.replied_text = ""
                                        chatModel.replied_file_type = ""
                                        chatModel.replied_message_type = ""
                                        chatModel.replied_thumb = ""
                                    }

                                    chatArrayList.add(chatModel)

                                }

//                                chatAdapter.addData(chatArrayList)
                                setAdapter()

                                Handler().postDelayed({
                                    recyclerChat.smoothScrollToPosition(chatArrayList.size - 1)
//                                    recyclerChat.smoothScrollToPosition(chatAdapter.itemCount - 1)

                                }, 1000)

                            }
                        }

                        Log.e(
                            "ABCD", " wallpaperData11 : "
                                    + wallpaperData + " wallpaperType : " + wallpaperType
                        )

                        Log.e(TAG, "LIST_SIZE_:" + chatArrayList.size)


                        if (wallpaperType.equals("simple")) {

                            ivMainImage.visibility = GONE

                            if (wallpaperData == "black") {
                                clChatRoot.background = getDrawable(R.color.black)

                            } else if (wallpaperData == "white") {
                                clChatRoot.background = getDrawable(R.color.white)

                            } else {
                                clChatRoot.background = getDrawable(R.color.white)
                            }

                        } else if (wallpaperType == "image") {

                            ivMainImage.visibility = VISIBLE

                            Picasso.with(this).load(WebUrls.CHAT_MEDIA_URL + wallpaperData)
                                .into(ivMainImage)

                        } else {

                            ivMainImage.visibility = GONE

                            clChatRoot.background = getDrawable(R.color.white)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.GROUP_CHAT_DATA_REQ_CODE -> {

                try {

                    var jsonObject = JSONObject(response)

                    Log.e("GROUP_DATA_REQ_CODE", jsonObject.toString())

                    if (jsonObject.getBoolean("response")) {

                        chatArrayList.clear()

                        if (!jsonObject.isNull("data")) {

                            if (jsonObject.has("wallpaper")) {

                                var wallpaperObj = jsonObject.getJSONObject("wallpaper")

                                if (wallpaperObj.has("image") && !wallpaperObj.isNull("image")) {

                                    wallpaperType = "image"
                                    wallpaperData = wallpaperObj.getString("image")
                                    modeType = "white"


                                } else {

                                    wallpaperType = "simple"
                                    modeType = wallpaperObj.getString("type")
                                    wallpaperData = wallpaperObj.getString("type")
                                }

                            } else {
                                wallpaperData = ""
                                wallpaperType = ""
                                modeType = "white"
                            }

                            var jArrayResult = jsonObject.getJSONArray("data")

                            if (jArrayResult.length() > 0) {

                                for (i in 0 until jArrayResult.length()) {

                                    var objResult = jArrayResult.getJSONObject(i)

                                    var chatModel = ChatModel()

                                    chatModel.id = objResult.getString("id")
                                    chatModel.senderId = objResult.getString("sender_id")
                                    chatModel.groupId = objResult.getString("group_id")
                                    chatModel.roomId = objResult.getString("room_id")
                                    chatModel.message = objResult.getString("message")
                                    chatModel.date = objResult.getString("date")
                                    chatModel.seen = objResult.getString("seen")


                                    chatModel.receiverId = ""

                                    if (objResult.getString("message_type").equals("text")) {
                                        chatModel.messageType = "text"
                                    } else {
                                        chatModel.messageType = "media"
                                    }

                                    if (objResult.has("thumb") && objResult.isNull("thumb")) {
                                        chatModel.thumb = ""
                                    } else {
                                        chatModel.thumb = objResult.getString("thumb")
                                    }

                                    chatModel.fileType = objResult.getString("message_type")
                                    chatModel.isDeleted = objResult.getString("is_deleted")
                                    chatModel.createdAt = objResult.getString("created_at")
                                    chatModel.updatedAt = objResult.getString("updated_at")

                                    chatModel.only_date = objResult.getString("only_date")


                                    if (objResult.has("sender_user_chat") && !objResult.isNull("sender_user_chat")) {

                                        var sender_user_chat =
                                            objResult.getJSONObject("sender_user_chat")

                                        chatModel.receiverName =
                                            sender_user_chat.getString("username")
                                        chatModel.receiverProfileImage =
                                            sender_user_chat.getString("profile_image")

                                    } else {
                                        chatModel.receiverName = ""
                                        chatModel.receiverProfileImage = ""
                                    }


                                    chatModel.senderName = PreferenceFile.getInstance()
                                        .getPreferenceData(this, WebUrls.KEY_USERNAME)
                                    chatModel.senderProfile = PreferenceFile.getInstance()
                                        .getPreferenceData(this, WebUrls.KEY_PROFILE_IMAGE)

                                    try {

                                        var jsonObject = JSONObject()
                                        jsonObject.put("room_id", roomId)
                                        jsonObject.put("chat_id", chatModel.id)
                                        jsonObject.put(
                                            "sender_id",
                                            PreferenceFile.getInstance().getPreferenceData(
                                                this,
                                                WebUrls.KEY_USERID
                                            )
                                        )
//                                            Log.e("GROUP_SEEN_EMIT_LISTEN", jsonObject.toString())
                                        socket!!.emit(
                                            WebUrls.GROUP_SEEN_EMIT_LISTEN,
                                            jsonObject
                                        )

                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }


                                    if (i == 0) {
                                        chatModel.wallpaperData = wallpaperData
                                        chatModel.wallpaperType = wallpaperType
                                    }


                                    // replied messages data

                                    if (objResult.has("chat_slide") && !objResult.isNull("chat_slide")) {

                                        val chat_slideObj = objResult.getJSONObject("chat_slide")

                                        chatModel.replied_chat_id = chat_slideObj.getString("id")
                                        chatModel.replied_id = chat_slideObj.getString("id")
                                        chatModel.replied_text = chat_slideObj.getString("message")
                                        chatModel.replied_file_type =
                                            chat_slideObj.getString("message_type")

                                        if (chat_slideObj.getString("message_type").equals("text")) {
                                            chatModel.replied_message_type = "text"
                                        } else {
                                            chatModel.replied_message_type = "media"
                                        }

                                        if (chat_slideObj.has("thumb") && chat_slideObj.isNull("thumb")) {
                                            chatModel.replied_thumb = ""
                                        } else {
                                            chatModel.replied_thumb =
                                                chat_slideObj.getString("thumb")
                                        }

                                    } else {
                                        chatModel.replied_chat_id = ""
                                        chatModel.replied_id = ""
                                        chatModel.replied_text = ""
                                        chatModel.replied_file_type = ""
                                        chatModel.replied_message_type = ""
                                        chatModel.replied_thumb = ""
                                    }

                                    chatArrayList.add(chatModel)

                                }

//                                chatAdapter.addData(chatArrayList)
                                setAdapter()

                                Handler().postDelayed({
                                    recyclerChat.smoothScrollToPosition((chatAdapter!!.itemCount) - 1)

                                }, 1000)

                            }
                        }


                        if (wallpaperType == "simple") {

                            ivMainImage.visibility = GONE

                            if (wallpaperData.equals("black")) {
                                clChatRoot.background = getDrawable(R.color.black)
                            } else if (wallpaperData == "white") {
                                clChatRoot.background = getDrawable(R.color.white)
                            } else {
                                clChatRoot.background = getDrawable(R.color.white)
                            }

                        } else if (wallpaperType == "image") {

                            ivMainImage.visibility = VISIBLE

                            Picasso.with(this).load(WebUrls.CHAT_MEDIA_URL + wallpaperData)
                                .into(ivMainImage)

                        } else {

                            ivMainImage.visibility = GONE

                            clChatRoot.background = getDrawable(R.color.white)
                        }


                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.CREATE_ROOM_CODE -> {

                try {

                    val gson = Gson()

                    // Converting json to object
                    // first parameter should be prpreocessed json
                    // and second should be mapping class

                    // Converting json to object
                    // first parameter should be prpreocessed json
                    // and second should be mapping class
                    val chatResponse: ChatResponse = gson.fromJson(
                        response,
                        ChatResponse::class.java
                    )

//                    chatAdapter?.updateData(chatResponse.data.chatData)
                    //=========================================================================

                    var jsonObject = JSONObject(response)

                    if (jsonObject.getString("response").equals("1")) {

                        Log.e("CREATE_ROOM_CODE", jsonObject.toString())

                        var dataObj = jsonObject.getJSONObject("data")

                        roomId = dataObj.getString("room_id")

                        connectSocket()

                        callAllChatApi()

                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.CREATE_ROOM_FOR_GROUP_REQ_CODE -> {

                try {

                    var jsonObject = JSONObject(response)

                    Log.e("CREATE_GROUP_REQ_CODE", jsonObject.toString())

                    if (jsonObject.getString("response").equals("1")) {

                        var dataObj = jsonObject.getJSONObject("data")

                        roomId = dataObj.getString("room_id")

                        connectSocket()

                        callAllChatApi()

                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

    }

//    fun openEmojiKeyboard() {
//        emojiPopup = EmojiPopup.Builder.fromRootView(clChatRoot)
//            .setOnEmojiBackspaceClickListener {
//                //                Log.e("ClickedOn", "BackSpace")
//            }
//            .setOnEmojiClickListener { emoji, imageView ->
//
//                //                Log.e("ClickedOn", "Emoji")
//            }
//            .setOnEmojiPopupShownListener {
//                //                Log.e("ClickedOn", "ShowEmoji")
//
//            }
//            .setOnSoftKeyboardOpenListener {
//                //                Log.e("ClickedOn", "SOFTKeyboard")
//
//            }
//            .setOnEmojiPopupDismissListener {
//                //                Log.e("ClickedOn", "EmojiPopup")
//            }
//            .setOnSoftKeyboardCloseListener {
//                //                Log.e("ClickedOn", "CloseSoftKeyboard")
//
//            }
//            .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
//            .build(edtMessage)
//    }

    data class ChatModel(
        var id: String = "",
        var senderId: String = "",
        var receiverId: String = "",
        var roomId: String = "",
        var message: String = "",
        var date: String = "",
        var seen: String = "",
        var messageType: String = "",
        var fileType: String = "",
        var isDeleted: String = "",
        var createdAt: String = "",
        var updatedAt: String = "",
        var only_date: String = "",
        var senderName: String = "",
        var senderEmail: String = "",
        var senderProfile: String = "",
        var receiverName: String = "",
        var receiverEmail: String = "",
        var receiverProfileImage: String = "",
        var audioFileDuration: String = "",
        var thumb: String = "",
        var last_message_time: String = "",
        var groupId: String = "",
        var groupName: String = "",
        var groupImg: String = "",
        var unseen_msgs_count: String = "",
        var wallpaperData: String = "",
        var wallpaperType: String = "",
        var replied_chat_id: String = "",
        var replied_text: String = "",
        var replied_file_type: String = "",
        var replied_message_type: String = "",
        var replied_id: String = "",
        var replied_thumb: String = ""


    ) {
    }

    private fun chooseVideos() {

        UwMediaPicker
            .with(this)                        // Activity or Fragment
            .setRequestCode(555)                // Give request code, default is 0
            .setGalleryMode(UwMediaPicker.GalleryMode.VideoGallery) // GalleryMode: ImageGallery or VideoGallery, default is ImageGallery
            .setGridColumnCount(2)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is llight status bar enable, default is true
            .enableImageCompression(true)                // Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)                // Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)                // Compressed image's max height px, default is 720
            .setCompressionQuality(85)                // Image compression quality, default is 85
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this@Chat))    // Compressed image file's destination path, default is Pictures Dir
            .open()
    }

    private fun chooseImages() {
        UwMediaPicker
            .with(this)                        // Activity or Fragment
            .setRequestCode(666)                // Give request code, default is 0
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery or VideoGallery, default is ImageGallery
            .setGridColumnCount(2)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is llight status bar enable, default is true
            .enableImageCompression(true)                // Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)                // Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)                // Compressed image's max height px, default is 720
            .setCompressFormat(Bitmap.CompressFormat.JPEG)        // Compressed image's format, default is JPEG
            .setCompressionQuality(85)                // Image compression quality, default is 85
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this@Chat))    // Compressed image file's destination path, default is Pictures Dir
            .open()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            555, 666 -> if (resultCode == Activity.RESULT_OK) {

                Log.e("videouri", data.toString())

                selectedMediaPaths!!.clear()

                selectedMediaPaths!!.addAll(data!!.getStringArrayExtra(UwMediaPicker.UwMediaPickerResultKey))

                if (selectedMediaPaths!!.isNotEmpty()) {

                    Log.e("videouri", "selectedMediaPaths::" + selectedMediaPaths!!.toString() + "")

                    if (selectedMediaPaths.toString().replace("[", "").replace("]", "")
                            .contains(".mp4")
                    ) {
                        attachment_type = "video"
                    } else {
                        attachment_type = "image"
                    }

                    imagePath = selectedMediaPaths.toString().replace("[", "").replace("]", "")

                    file1 = File(imagePath)

                    Log.e("file1!!.path", file1!!.path)

                    if (attachment_type.equals("video")) {

                        bitmapThumb = ThumbnailUtils.createVideoThumbnail(
                            imagePath,
                            MediaStore.Images.Thumbnails.MICRO_KIND
                        );

                        var baos = ByteArrayOutputStream()
                        bitmapThumb!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        var imageBytes = baos.toByteArray()
//                        bitmapImagePath = imageBytes.toString()
                        bitmapImagePath = Base64.encodeToString(imageBytes, Base64.DEFAULT)

//                        ivProfileImg.setImageBitmap(bitmapThumb)

                    }

                    Log.e("bitmapImagePath", bitmapImagePath)

                    // TODO LATER
                    sendAndGetBinaryData(imagePath, attachment_type)

                }


            }

            /* 10 -> {

                 try {

                     val uri = data!!.data

                     Log.e(TAG, "onActivityResult: " + Common.generateImageFromPdf(this, uri))

                     val takeFlags =
                         data!!.flags and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                     contentResolver.takePersistableUriPermission(uri!!, takeFlags)

                     var path: String? = ""

                     path = Common.getPath(this@Chat, uri).toString()

                     sendAndGetBinaryData(path, "document")

                 } catch (e: Exception) {
                     e.printStackTrace()
                 }
             }
 */

            REQUEST_CODE_PICK_FILE -> {

                var list: ArrayList<NormalFile> =
                    data!!.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE)
                if (list.size > 0) {
                    imagePath = list[0].path

                    Log.e("FILE", "FILE:" + list[0].path)

                    file1 = File(imagePath)

                    sendAndGetBinaryData(imagePath, "document")
                }
            }


            501 -> if (resultCode == Activity.RESULT_OK) {
                cropImage(uri)
            }


            111 -> if (resultCode == Activity.RESULT_OK) {
                val uri = data!!.data
                try {
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(uri!!, filePath, null, null, null)
                    if (cursor == null) {
                        val path = uri.path
                        file = File(path)
                    } else {
                        cursor.moveToFirst()
                        val colIndex = cursor.getColumnIndex(filePath[0])
                        val path = cursor.getString(colIndex)
                        file = File(path)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                cropImage(data.data)

            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result.uri
                    setImageafterCrop(resultUri.path)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    error.printStackTrace()
                }
            }

        }
    }

    fun uploadAttachmentsGallery() {

        var alertDialog: AlertDialog? = null

        var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this).inflate(R.layout.upload_attachment_gallery, null)
        var tvUploadImage: TextView = view.findViewById(R.id.tvUploadImage)
        var tvUploadVideo: TextView = view.findViewById(R.id.tvUploadVideo)
        var tvUploadAttach: TextView = view.findViewById(R.id.tvUploadAttach)
        var tvCancel: TextView = view.findViewById(R.id.tvCancel)

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)


        tvUploadImage!!.setOnClickListener(View.OnClickListener {
            chooseImages()
            alertDialog!!.dismiss()
        })

        tvUploadVideo!!.setOnClickListener(View.OnClickListener {
            chooseVideos()
            alertDialog!!.dismiss()
        })

        tvUploadAttach!!.setOnClickListener(View.OnClickListener {
            sendDoc()
            alertDialog!!.dismiss()
        })

        tvCancel!!.setOnClickListener(View.OnClickListener {
            alertDialog!!.dismiss()
        })

        alertDialog!!.window
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.85
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog!!.window.setLayout(width.toInt(), height)
        alertDialog!!.window.setGravity(Gravity.BOTTOM)
        alertDialog!!.show()

    }

    private fun showDialog() {
        try {
            progressDialog = Dialog(this!!)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.setContentView(R.layout.progress_dialog)
            progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

            progressDialog!!.show()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

    private fun cancelDialog() {

        try {

            if (progressDialog != null) {
                progressDialog!!.cancel()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun sendAndGetBinaryData(path: String, type: String) {

        val uni_code = System.currentTimeMillis().toString()

        media_path[uni_code] = path

        Log.e("MediaSize", media_path.size.toString())

        if (media_path.size == 1) {
            uploadFileOnServer(media_path, type)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun uploadFileOnServer(map: HashMap<String, String>, type: String) {

        Log.e(TAG, "uploadFileOnServer: " + map.keys)

        if (map.size > 0) {
            for (entry in map.keys) {
                val value = map[entry]

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    FileUploadTask(
                        value,
                        entry,
                        roomId,
                        receiverId,
                        media_path,
                        type
                    ).executeOnExecutor(
                        AsyncTask.THREAD_POOL_EXECUTOR,
                        ""
                    )
                break
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public inner class FileUploadTask(
        var file_path: String?, var uni_code: String,
        var room_id: String, var receiver_id: String,
        var media_path: HashMap<String, String>,
        var type: String
    ) : AsyncTask<String, Int, String>() {

        private var attachment_type = ""

        var myFlag = "new"

        lateinit var res1: JSONObject

        private var callback: UploadFileMoreDataReqListener? = null
        private var mFileUploadManager: FileUploadManager? = null

        override fun onPreExecute() {

            showDialog()
            mFileUploadManager = FileUploadManager()

        }


        override fun doInBackground(vararg params: String): String? {

            val isSuccess = socket!!.connected()

            Log.e(TAG, "isSuccesssss" + isSuccess.toString())

            if (isSuccess) {

                mFileUploadManager!!.prepare(file_path!!)

                Log.e(TAG, "file_path" + "" + file_path)
                // This function gets callback when server requests more data
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener)
                // This function will get a call back when upload completes
                setUploadFileCompleteListener()
                // Tell server we are ready to start uploading ..
                if (socket!!.connected()) {

                    val res = JSONObject()

                    val jsonArr = JSONArray()

                    try {

                        res.put("Name", mFileUploadManager!!.getFileName())
                        res.put("Size", mFileUploadManager!!.getFileSize())
                        jsonArr.put(res)

                        socket!!.emit("uploadFileStart", jsonArr)

                        Log.e(
                            TAG,
                            "uploadFileStart:" + res.toString() + " jsonArr : " + jsonArr.toString()
                        )


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            return null
        }

        override fun onPostExecute(s: String?) {
            if (s == null) {
                return
            }
            if (s.equals("OK", ignoreCase = true)) {

                Log.e(TAG, "OnPost")

                media_path.remove(uni_code)
                mFileUploadManager!!.close()
                socket!!.off("uploadFileMoreDataReq", uploadFileMoreDataReq)
                socket!!.off("uploadFileCompleteRes", onCompletedddd)
                uploadFileOnServer(media_path)
            }
        }


        @SuppressLint("ObsoleteSdkInt")
        private fun uploadFileOnServer(map: java.util.HashMap<String, String>) {
            Log.e(TAG, "uploadFileOnServer: " + map.keys)
            if (map.size > 0) {
                for (entry in map.keys) {
                    val value = map[entry]

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        FileUploadTask(
                            value,
                            entry, room_id, receiver_id, media_path, type
                        ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "")
                    break
                }
            }
        }


        private val mUploadFileMoreDataReqListener = object : UploadFileMoreDataReqListener {
            override fun err(e: JSONException) {
                e.printStackTrace()
            }

            override fun uploadChunck(offset: Int, percent: Int) {
                // Read the next chunk

                Log.e(
                    TAG, "percent" + percent.toString()
                            + " offset:" + offset.toString()
                )

                mFileUploadManager!!.read(offset)

                if (socket!!.connected()) {

                    var res = JSONObject()
                    var jsonArr = JSONArray()

                    try {

                        Log.e("MyFlag", myFlag + " type:" + type)
                        res.put("Name", mFileUploadManager!!.getFileName())
                        res.put("Size", mFileUploadManager!!.getFileSize())
                        res.put("device_type", "android")
                        res.put(
                            "sender_id",
                            PreferenceFile.getInstance().getPreferenceData(
                                this@Chat,
                                WebUrls.KEY_USERID
                            )
                        )

                        if (!receiverId.equals("")) {
                            res.put("receiver_id", receiverId)
                        } else {
                            res.put("group_id", groupId)
                        }
//                        if (reply_layout.isShown) {
//                            res.put("chat_id", chatRepliedId)
//                        } else {
//                            res.put("chat_id", "")
//                        }
                        res.put("message_type", type)
                        res.put("room_id", room_id)
                        res.put("flag", "send")
//                      res.put("flag", myFlag)
//                      res.put("thumb",PreferenceFile.getInstance().getPreferenceData(this@Chat, WebUrls.KEY_PROFILE_IMAGE))
                        res.put("filepath", file_path)
                        if (type.equals("video")) {
                            res.put("thumb", bitmapImagePath)
                        } else {
                            res.put("thumb", "")
                        }
                        res.put("Data", mFileUploadManager!!.getData())
                        jsonArr.put(res)

                        socket!!.emit("uploadFileChuncks", jsonArr)

                        Log.e(TAG, "uploadChunck:res $res")


                    } catch (e: Exception) {
                        e.message
                    }
                }
            }
        }

        internal var uploadFileMoreDataReq: Emitter.Listener =
            Emitter.Listener { args ->
                Log.e(TAG, "setUploadFileMoreDataReqListener-- " + args[0].toString())
                try {
                    val json_data = args[0] as JSONObject
                    val place = json_data.getInt("Place")
                    val percent = json_data.getInt("Percent")
                    myFlag = "old"
                    publishProgress(json_data.getInt("Percent"))
                    callback!!.uploadChunck(place, percent)
                } catch (e: JSONException) {
                    callback!!.err(e)
                }
            }


        internal var onCompletedddd: Emitter.Listener = object : Emitter.Listener {
            override fun call(vararg args: Any) {
                val json_data = args[0] as JSONObject

                Log.e(TAG, "OnCompleteData" + json_data.toString())
                if (json_data.has("IsSuccess")) {

//                    SocketConnection.socket!!.emit(WebUrls.SEND_MESSAGE, res1)

                    publishProgress(110)

                    hideReplyLayout()

                    chatRepliedId = ""

                    cancelDialog()

                    return
                }
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            if (values[0]!! > 107) {
                if (media_path.containsKey(uni_code)) {
                    onPostExecute("OK")
                }
            }
        }


        private fun setUploadFileMoreDataReqListener(callbackk: UploadFileMoreDataReqListener) {
            Log.e(TAG, "setUploadFileMoreDataReqListener:" + "setUploadFileMoreDataReqListener: ")
            callback = callbackk
            socket!!.on("uploadFileMoreDataReq", uploadFileMoreDataReq)
        }

        private fun setUploadFileCompleteListener() {
            socket!!.on("uploadFileCompleteRes", onCompletedddd)
        }
    }

    fun customDialog(context: Context, title: String, msg: String) {

        val dialogBuilder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        val textName = dialogView.tvTitle
        val tvOk = dialogView.tvok
        val tvMsg = dialogView.tvMsg

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        textName.setText(title)
        tvMsg.setText(msg)

        tvOk.setOnClickListener(View.OnClickListener {
            finish()
            alertDialog.dismiss()
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    override fun onPause() {
        super.onPause()
        cancelDialog()
    }

    fun goBack(view: View) {}
}