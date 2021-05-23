package unilife.com.unilife.chat

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.json.JSONArray
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.update.ChatDetailsActivity
import unilife.com.unilife.chat.update.GroupChatDetailsActivity
import unilife.com.unilife.chat.update.SearchActivity
import unilife.com.unilife.home.Home
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common

class ChatListing : AppDrawer(), View.OnClickListener, RetrofitResponse {

    var friendsList: ArrayList<ChatUserDetailsModel> = ArrayList()
    val TAG = ChatListing::class.java.simpleName
    private var chatArrayList = ArrayList<Chat.ChatModel>()

    var groupId: String = ""
    var groupName: String = ""
    var groupImg: String = ""
    var receiverId: String = ""
    var receiverName: String = ""
    var receiverImg: String = ""
    var userStatus: String = ""
    var modeType: String = ""
    var flag: String = "no"
    var search: String = ""

    var chatAdapter: ChatAdapter? = null
    var popupwindow_obj: PopupWindow? = null

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivChat.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        tvChat.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )

        tvMainTitle.text = "Chat"
        tvMainTitle.setTextColor(resources.getColor(R.color.colorAccent, null))
        llToolBar.setBackgroundColor(resources.getColor(R.color.white, null))
        ivNoti.setImageResource(R.drawable.ic_baseline_more_vert_24)
        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))


        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callSearchChats()
            }
            true
        }

        setOnClickListener()

        LocalBroadcastManager.getInstance(this@ChatListing)
            .registerReceiver(newMessage, IntentFilter("New message"))
    }


    internal var newMessage: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showOnlineFriends(false)
            lastMsgsList(false)
            flag = "no"
        }
    }

    override fun onResume() {
        super.onResume()
        showOnlineFriends(true)
        lastMsgsList(false)
        flag = "no"
//        checkStatusModeWallPaper()
    }


    fun showOnlineFriends(flag: Boolean) {
        if (Alerts.isNetworkAvailable(this)) {
            try {

                Log.e("url", WebUrls.BASE_URL + WebUrls.SHOW_ONLINE_FRIENDS)
                RetrofitService(
                    this,
                    this@ChatListing,
                    WebUrls.SHOW_ONLINE_FRIENDS + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    ),
                    WebUrls.SHOW_ONLINE_FRIENDS_CODE,
                    1
                ).callService(flag)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun lastMsgsList(flag: Boolean) {

        if (Alerts.isNetworkAvailable(this)) {

            try {
                Log.e("url", WebUrls.BASE_URL + WebUrls.CREATE_ROOM)
                RetrofitService(
                    this,
                    this@ChatListing,
                    WebUrls.LAST_MSG_LIST + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    ),
                    WebUrls.LAST_MSG_LIST_CODE,
                    1
                ).callService(flag)

                Log.e(
                    TAG, "LAST_MSG_LIST" + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    )
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun setOnlineFriendsAdapter(friendsList: ArrayList<ChatUserDetailsModel>) {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvOnlineFriends?.layoutManager = layoutManager
        val onlineFriendsAdapter = OnlineFriendsAdapter(this, friendsList)
        rvOnlineFriends?.adapter = onlineFriendsAdapter

        onlineFriendsAdapter.setOnItemClickListener(object :
            OnlineFriendsAdapter.onItemClickListener {
            override fun onItemClick(position: Int, friendsList: ArrayList<ChatUserDetailsModel>?) {


                try {

                    Log.e("friend_id", friendsList!!.get(position).friend_id)
                    Log.e("friend_username", friendsList.get(position).friend_username)


//                    if(chatArrayList!!.get(position).receiverId.equals("")){
//                        startActivity(
//                            Intent(this@ChatListing, Chat::class.java)
//                                .putExtra("groupId", chatArrayList!!.get(position).groupId)
//                                .putExtra("groupName", chatArrayList!!.get(position).groupName)
//                                .putExtra("groupImg",
//                                    chatArrayList!!.get(position).groupImg
//                                )
//                        )
//                    }else{


                    startActivity(
                        Intent(this@ChatListing, ChatDetailsActivity::class.java)
                            .putExtra("receiver_id", friendsList[position].friend_id)
                            .putExtra("receiver_name", friendsList[position].friend_username)
                            .putExtra(
                                "receiver_profile_image",
                                friendsList.get(position).friend_profile_image
                            )
                    )
//                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

    }

    private fun setChatAdapter(chatArrayList: ArrayList<Chat.ChatModel>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rycChatlist?.layoutManager = layoutManager

        if (chatArrayList.size > 0) {
            rycChatlist.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE
        } else {
            rycChatlist.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        }

        chatAdapter = ChatAdapter(this, chatArrayList)
        rycChatlist?.adapter = chatAdapter

        Log.e("HELLO", "inside : " + chatArrayList.size)

        chatAdapter!!.setOnItemClickListener(object : ChatAdapter.onItemClickListener {
            override fun onItemClick(position: Int, chatArrayList: ArrayList<Chat.ChatModel>?) {
//                Log.e("receiverId", chatArrayList!!.get(position).receiverId)
//                Log.e("senderId", chatArrayList!!.get(position).senderId)
                try {
                    if (chatArrayList!!.get(position).receiverId.equals("")) {
                        startActivity(
                            Intent(this@ChatListing, GroupChatDetailsActivity::class.java)
                                .putExtra("groupId", chatArrayList.get(position).groupId)
                                .putExtra("groupName", chatArrayList.get(position).groupName)
                                .putExtra(
                                    "groupImg",
                                    chatArrayList.get(position).groupImg
                                )
                        )
                    } else {
                        Log.e("groupId", chatArrayList.get(position).groupId)
                        startActivity(
                            Intent(this@ChatListing, ChatDetailsActivity::class.java)
                                .putExtra("receiver_id", chatArrayList.get(position).receiverId)
                                .putExtra("receiver_name", chatArrayList.get(position).receiverName)
                                .putExtra(
                                    "receiver_profile_image",
                                    chatArrayList.get(position).receiverProfileImage
                                )
                                .putExtra("roomId", chatArrayList.get(position).roomId)
                        )
//
//                        startActivity(Intent(this@ChatListing, UploadFileActivity::class.java)
//                                .putExtra("receiver_id", chatArrayList.get(position).receiverId)
//                                .putExtra("receiver_name",chatArrayList.get(position).receiverName)
//                                .putExtra("receiver_profile_image", chatArrayList.get(position).receiverProfileImage)
//                                .putExtra("roomId", chatArrayList.get(position).roomId))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun setOnClickListener() {
//        rlEvent.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
        tvFriend_list.setOnClickListener(this)
        tvViewMoreSuggestions.setOnClickListener(this)
        ivNoti.setOnClickListener(this)
        tvGroupChats.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)


        when (v?.id) {

//            R.id.rlEvent -> {
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBlogs -> {
//                val intent = Intent(this, Blog::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBrands -> {
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//                finish()
//            }

            R.id.tvFriend_list -> {
                val intent = Intent(this, ChatUsersList::class.java)
                intent.putExtra("key", "view_friends")
                startActivity(intent)
            }

            R.id.tvViewMoreSuggestions -> {
                val intent = Intent(this, ChatUsersList::class.java)
                intent.putExtra("key", "view_more_suggestions")
                startActivity(intent)
            }

            R.id.ivNoti -> {
                flag = "yes"
                checkStatusModeWallPaper()
            }

            R.id.tvGroupChats -> {

                try {
                    startActivity(Intent(this@ChatListing, GroupListing::class.java))
//                    popupWindow.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }


    @SuppressLint("SetTextI18n")
    private fun showPopUpMenu(userStatus1: String, modeType1: String): PopupWindow {

        var popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        var inflater =
            this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.chat_popup_option, null)
        val tvCreateGroup = view.findViewById(R.id.tvCreateGroup) as TextView
        val tvChangeWallpaper = view.findViewById(R.id.tvChangeWallpaper) as TextView
        val SwStatus = view.findViewById(R.id.SwStatus) as Switch
        val tvReqRcvd = view.findViewById(R.id.tvReqRcvd) as TextView
        val tvStatus = view.findViewById(R.id.tvStatus) as TextView
        val tvChangeMode = view.findViewById(R.id.tvChangeMode) as TextView
        val SwChangeMode = view.findViewById(R.id.SwChangeMode) as Switch

        popupWindow.isFocusable = true

        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.grey_outline, null))
        popupWindow.contentView = view

        if (modeType1 == "black") {
            SwChangeMode.isChecked = true
            tvChangeMode.text = "Dark Mode"
        } else if (modeType1 == "white") {
            SwChangeMode.isChecked = false
            tvChangeMode.text = "Light Mode"
        } else {
            SwChangeMode.isChecked = false
            tvChangeMode.text = "Light Mode"
        }

        when (userStatus1) {
            "show" -> {
                SwStatus.isChecked = true
                tvStatus.text = "Online"
            }
            "hide" -> {
                SwStatus.isChecked = false
                tvStatus.text = "Offline"
            }
            else -> {
                SwStatus.isChecked = true
                tvStatus.text = "Online"
            }
        }

        Log.e("DATA>>", "$userStatus1 modeType : $modeType1")

        SwStatus.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                userStatus = "show"
            } else {
                userStatus = "hide"
            }
            try {
                changeStatus(userStatus)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        SwChangeMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                modeType = "black"
            } else {
                modeType = "white"
            }
            try {
                changeMode(modeType)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        tvCreateGroup.setOnClickListener {

            try {
                startActivity(Intent(this@ChatListing, GroupListing::class.java))
                popupWindow.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        tvChangeWallpaper.setOnClickListener {

            try {
                startActivity(Intent(this@ChatListing, ChangeChatWallpaper::class.java))
                popupWindow.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        tvReqRcvd.setOnClickListener {
            try {
                startActivity(Intent(this@ChatListing, ReceivedRequestActivity::class.java))
                popupWindow.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return popupWindow
    }


    fun checkStatusModeWallPaper() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this,
                    this@ChatListing,
                    WebUrls.SEND_USER_CHAT_WALLPAPER + PreferenceFile.getInstance()
                        .getPreferenceData(
                            this,
                            WebUrls.KEY_USERID
                        ),
                    WebUrls.SEND_USER_CHAT_WALLPAPER_CODE,
                    1
                ).callService(false)

                Log.e(
                    TAG,
                    "SEND_USER_CHAT_WALLPAPER_CODE" + PreferenceFile.getInstance()
                        .getPreferenceData(
                            this,
                            WebUrls.KEY_USERID
                        )
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun changeStatus(userStatus: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(
                        this@ChatListing,
                        WebUrls.KEY_USERID
                    )
                )
                postParam.put("status", userStatus)

                RetrofitService(
                    this@ChatListing, this@ChatListing, WebUrls.HIDE_USER_STATUS,
                    WebUrls.HIDE_USER_STATUS_STATUS_CODE, 3, postParam
                ).callService(true)


            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun changeMode(modeType: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(
                        this@ChatListing,
                        WebUrls.KEY_USERID
                    )
                )
                postParam.put("type", modeType)
                postParam.put("image", "")

                RetrofitService(
                    this@ChatListing, this@ChatListing, WebUrls.UPLOAD_WALLPAPER,
                    WebUrls.UPLOAD_WALLPAPER_CODE, 3, postParam
                ).callService(true)

                Log.e(TAG, "UPLOAD_WALLPAPER_CODE" + postParam.toString())

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callSearchChats() {
        Log.e("Called", "searchChat")

        search = etSearch.text.toString().trim()
        if (Alerts.isNetworkAvailable(this)) {
            try {
                var postParam = JSONObject()
                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("search", search)

                RetrofitService(
                    this, this, WebUrls.CHAT_SEARCH, WebUrls.CHAT_SEARCH_CODE,
                    3, postParam
                ).callService(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    override fun onResponse(requestCode: Int, response: String) {
        super.onResponse(requestCode, response)

        try {

            when (requestCode) {


                WebUrls.UPLOAD_WALLPAPER_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "UPLOAD_WALLPAPER_CODE:$obj")

                    val res = obj.getString("response")

                    if (obj.getBoolean("response")) {

                        if (obj.has("data") && !obj.isNull("data")) {
                            val statusArr = obj.getJSONArray("data")
                            Log.e(TAG, "statusArr:" + statusArr.get(0))
                            if (statusArr.get(0).toString().equals("1")) {
                                Common.customDialog(this, "Unilife", "Mode changed successfully.")
                                if (popupwindow_obj!!.isShowing) {
                                    popupwindow_obj!!.dismiss()
                                }
                            }
//                            modeType = statusArr.getString("data")
                        } else {
                            modeType = ""
                        }

                    } else {
                        modeType = ""
                    }

                }

                WebUrls.HIDE_USER_STATUS_STATUS_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "HIDE_USER_STATUS_STATUS_CODE:" + obj.toString())

                    val res = obj.getString("response")

                    if (obj.getBoolean("response")) {

                        if (obj.has("data") && !obj.isNull("data")) {
                            val statusArr = obj.getJSONArray("data")
                            Log.e(TAG, "statusArr:" + statusArr.get(0))
                            if (statusArr.get(0).toString().equals("1")) {
                                Common.customDialog(this, "Unilife", "Status changed successfully.")
                                if (popupwindow_obj!!.isShowing) {
                                    popupwindow_obj!!.dismiss()
                                }
                            }
//                            userStatus = statusObj.getString("data")
                        } else {
                            userStatus = ""
                        }

                    } else {
                        userStatus = ""
                    }

                }

                WebUrls.SEND_USER_CHAT_WALLPAPER_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "SEND_USER_CHAT_WALLPAPER_CODE:" + obj.toString())

                    val res = obj.getString("response")

                    if (obj.getBoolean("response")) {

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
                    popupwindow_obj!!.showAsDropDown(ivNoti)
                }


                WebUrls.LAST_MSG_LIST_CODE, WebUrls.CHAT_SEARCH_CODE -> {

                    try {

                        var jsonObject = JSONObject(response)

                        Log.e("LAST_MSG_LIST_CODE", jsonObject.toString())

                        etSearch.setText("")

                        if (jsonObject.getBoolean("response")) {


                            chatArrayList.clear()

                            if (!jsonObject.isNull("data")) {

                                val dataOpt = jsonObject.opt("data")

                                if (dataOpt is JSONArray) {

                                    var jArrayResult = jsonObject.getJSONArray("data")

                                    if (jArrayResult.length() > 0) {

                                        for (i in 0 until jArrayResult.length()) {

                                            var objResult = jArrayResult.getJSONObject(i)

                                            var chatModel = Chat.ChatModel()

                                            chatModel.id = objResult.getString("id")

                                            chatModel.roomId = objResult.getString("room_id")
                                            if (!objResult.isNull("last_message")) {
                                                chatModel.message =
                                                    objResult.getString("last_message")
                                            } else {
                                                chatModel.message = ""

                                            }
                                            chatModel.last_message_time =
                                                objResult.getString("last_message_time")

//                                        chatModel.date = objResult.getString("date")
//                                        chatModel.seen = objResult.getString("seen")

                                            if (objResult.getString("message_type")
                                                    .equals("text")
                                            ) {
                                                chatModel.messageType = "text"
                                            } else {
                                                chatModel.messageType = "media"
                                            }


                                            chatModel.fileType = objResult.getString("message_type")

//                                        chatModel.isDeleted = objResult.getString("is_deleted")
                                            chatModel.createdAt = objResult.getString("created_at")
                                            chatModel.updatedAt = objResult.getString("updated_at")

//                                        chatModel.only_date = objResult.getString("only_date")


                                            if (objResult.has("chat_group") && !objResult.isNull("chat_group")) {

                                                var objChatGroup =
                                                    objResult.getJSONObject("chat_group")

                                                groupId = objChatGroup.getString("id")
                                                groupName = objChatGroup.getString("group_name")
                                                groupImg = objChatGroup.getString("group_image")

                                                chatModel.groupId = groupId
                                                chatModel.groupName = groupName
                                                chatModel.groupImg = groupImg


                                                if (objChatGroup.has("group_user_seen") && !objChatGroup.isNull(
                                                        "group_user_seen"
                                                    )
                                                ) {

                                                    var groupChatArr =
                                                        objChatGroup.getJSONArray("group_user_seen")

                                                    if (groupChatArr.length() > 0) {
                                                        chatModel.unseen_msgs_count =
                                                            groupChatArr.length().toString()
                                                    }

                                                }

                                            } else {

                                                if (PreferenceFile.getInstance().getPreferenceData(
                                                        this, WebUrls.KEY_USERID
                                                    ).equals(objResult.getString("sender_id"))
                                                ) {

                                                    if (objResult.has("receiver_user") && !objResult.isNull(
                                                            "receiver_user"
                                                        )
                                                    ) {

                                                        var objReceiverUser =
                                                            objResult.getJSONObject("receiver_user")

                                                        receiverId = objReceiverUser.getString("id")
                                                        receiverName =
                                                            objReceiverUser.getString("username")
                                                        receiverImg =
                                                            objReceiverUser.getString("profile_image")

                                                    }

                                                } else {

                                                    if (objResult.has("sender_user") && !objResult.isNull(
                                                            "sender_user"
                                                        )
                                                    ) {

                                                        var objSenderUser =
                                                            objResult.getJSONObject("sender_user")

                                                        receiverId = objSenderUser.getString("id")
                                                        receiverName =
                                                            objSenderUser.getString("username")
                                                        receiverImg =
                                                            objSenderUser.getString("profile_image")
                                                    }
                                                }

                                                chatModel.receiverId = receiverId
                                                chatModel.receiverName = receiverName
                                                chatModel.receiverProfileImage = receiverImg


                                                if (objResult.has("room_chat") && !objResult.isNull(
                                                        "room_chat"
                                                    )
                                                ) {

                                                    var roomChatArr =
                                                        objResult.getJSONArray("room_chat")

                                                    if (roomChatArr.length() > 0) {
                                                        chatModel.unseen_msgs_count =
                                                            roomChatArr.length().toString()
                                                    }

                                                }

                                            }


//                                        if (!objResult.isNull("last_message")) {
                                            //       if(chatModel.groupId.equals("49")) {
                                            chatArrayList.add(chatModel)
                                            //        }
//                                        }

                                        }

                                    }

                                    if (chatArrayList.size > 0) {
                                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility =
                                            View.GONE
                                        rycChatlist.visibility = View.VISIBLE
                                        setChatAdapter(chatArrayList)
                                    } else {
                                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility =
                                            View.VISIBLE
                                    }


                                } else {
//                                    Log.e("HELLO", "HELLO "+chatArrayList.size)
////                                    chatArrayList.clear()
//                                    setChatAdapter(chatArrayList)
////                                    chatAdapter!!.notifyItemRemoved(0)

                                }
                            }
                        } else {
//                            Log.e("HELLO", "HELLO 111 "+chatArrayList.size)

//                            chatArrayList.clear()
//                            setChatAdapter(chatArrayList)

                        }


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }


                WebUrls.SHOW_ONLINE_FRIENDS_CODE -> {

                    val obj = JSONObject(response)

                    friendsList.clear()

                    Log.e(TAG, "SHOW_ONLINE_FRIENDS_CODE:$obj")

//                    val res = obj.getString("response")

                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

//                            val dataOpt = obj.opt("data")

//                            if (dataOpt is JSONArray) {

                            val data = obj.getJSONArray("data")

                            if (data.length() > 0) {

                                cardView12.visibility = View.VISIBLE

                                for (i in 0 until data.length()) {

                                    val dataObj = data.getJSONObject(i)

                                    val chatUserDetailsModel = ChatUserDetailsModel()

                                    chatUserDetailsModel.id = dataObj.getString("id")
                                    chatUserDetailsModel.user_id = dataObj.getString("user_id")
                                    chatUserDetailsModel.friend_id =
                                        dataObj.getString("friend_id")

                                    if (dataObj.has("status")) {
                                        chatUserDetailsModel.status =
                                            dataObj.getString("status")
                                    } else {
                                        chatUserDetailsModel.status = ""
                                    }

                                    val userfriendObj = dataObj.getJSONObject("userfriend")
                                    chatUserDetailsModel.friend_username =
                                        userfriendObj.getString("username")
                                    chatUserDetailsModel.friend_profile_image =
                                        userfriendObj.getString("profile_image")

                                    friendsList.add(chatUserDetailsModel)
                                }

                                Log.e(TAG, "friendsList:" + friendsList.size)

                                setOnlineFriendsAdapter(friendsList)

                            }
                        } else {
                            setOnlineFriendsAdapter(friendsList)
                        }
                    }else{
//                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility =
//                            View.VISIBLE
                    }

//                    } else {
//                        setOnlineFriendsAdapter(friendsList)

//                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
//                    }

                    if (friendsList.size == 0) {
                        cardView12.visibility = View.GONE
                    }
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        try {
            val intent = Intent(this, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finishAffinity()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openSearch(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}
