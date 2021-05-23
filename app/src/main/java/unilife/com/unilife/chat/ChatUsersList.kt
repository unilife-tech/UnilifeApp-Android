package unilife.com.unilife.chat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.update.ChatDetailsActivity
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common


class ChatUsersList : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var friendsList: ArrayList<ChatUserDetailsModel> = ArrayList()
    var friendsListFull: ArrayList<ChatUserDetailsModel> = ArrayList()
    var chatUsersListAdapter: ChatUsersListAdapter? = null
    val TAG = ChatUsersList::class.java.simpleName
    var key: String = ""
    var userStatus: String = ""
    var modeType: String = ""
    var popupwindow_obj: PopupWindow? = null
    var flag: String = "no"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivNotification.background = getDrawable(R.drawable.dot_line)
        ivNotification.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))

        setOnClickListener()
        getIntentdata()
        showFriendsList(true)
//        setSearchFilter()

        applyFilter()

    }

    @SuppressLint("SetTextI18n")
    private fun getIntentdata() {

        key = intent.getStringExtra("key")

        if (key == "view_friends") {
            tvTitle.text = "Friends"

        } else if (key == "view_more_suggestions") {
            tvTitle.text = "Find Friends"
        }

    }

    private fun setOnClickListener() {
        ivBackArrow.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
    }

    private fun setFriendsAdapter(friendsList: ArrayList<ChatUserDetailsModel>, key: String) {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rycFriendlist?.layoutManager = layoutManager

        chatUsersListAdapter = ChatUsersListAdapter(this, friendsList, key)
        rycFriendlist?.adapter = chatUsersListAdapter

        chatUsersListAdapter!!.setOnItemClickListener(object :
            ChatUsersListAdapter.onItemClickListener {
            override fun onItemClick(position: Int, friendsList: ArrayList<ChatUserDetailsModel>?) {
                try {
                    Log.e(
                        "ONCLICK", "friend_id   " + friendsList!![position].friend_id
                                + " " + friendsList[position].friend_username
                                + " " + friendsList[position].friend_profile_image
                    )

                    startActivity(
                        Intent(this@ChatUsersList, ChatDetailsActivity::class.java)
                            .putExtra("receiver_id", friendsList[position].friend_id)
                            .putExtra("receiver_name", friendsList[position].friend_username)
                            .putExtra(
                                "receiver_profile_image",
                                friendsList.get(position).friend_profile_image
                            )
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }


            }

            override fun onPerformAction(
                position: Int,
                status: String,
                friendsList: ArrayList<ChatUserDetailsModel>?
            ) {
                // 0 >> send request, 1 >> cancel Req
                performAction(position, status)
            }
        })

    }


    private fun performAction(pos: Int, status: String) {

        val postParam = JSONObject()

        try {

            if (status == "0") {
                postParam.put("request_id", friendsList[pos].id)
                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )

                Log.e("url", WebUrls.BASE_URL + WebUrls.SEND_FRIEND_REQ)
                RetrofitService(
                    this, this, WebUrls.SEND_FRIEND_REQ,
                    WebUrls.SEND_FRIEND_REQ_CODE, 3, postParam
                ).callService(true)
            } else if (status == "1") {
                postParam.put(
                    "reject_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )

                Log.e("dwfdfd", "" + friendsList[pos].id)
                postParam.put("user_id", friendsList[pos].id)
                RetrofitService(
                    this, this, WebUrls.CANCEL_FRIEND_REQ,
                    WebUrls.CANCEL_FRIEND_REQ_CODE, 3, postParam
                ).callService(true)
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun showFriendsList(flag: Boolean) {

        val postParam = JSONObject()
        Log.e("url", WebUrls.BASE_URL + WebUrls.SHOW_FRIENDS_LISTING)

        try {

            if (key.equals("view_friends")) {
                RetrofitService(
                    this, this, WebUrls.SHOW_FRIENDS_LISTING
                            + PreferenceFile.getInstance().getPreferenceData(
                        this, WebUrls.KEY_USERID
                    ),
                    WebUrls.SHOW_FRIENDS_LISTING_CODE, 1, postParam
                ).callService(flag)


            } else if (key.equals("view_more_suggestions")) {
                Log.e("url", WebUrls.BASE_URL + WebUrls.VIEW_MORE_SUGGESTIONS)

                RetrofitService(
                    this, this, WebUrls.VIEW_MORE_SUGGESTIONS
                            + PreferenceFile.getInstance().getPreferenceData(
                        this, WebUrls.KEY_USERID
                    ),
                    WebUrls.VIEW_MORE_SUGGESTIONS_CODE, 1, postParam
                ).callService(flag)
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun applyFilter() {

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (chatUsersListAdapter != null) {
                    chatUsersListAdapter!!.filterResult(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onResume() {
        super.onResume()
        flag = "no"

    }

    private fun receivedRequestList(flag: Boolean) {

        val postParam = JSONObject()

        try {
            Log.e("url", WebUrls.BASE_URL + WebUrls.RCVD_FRIENDS_REQ)

            RetrofitService(
                this, this, WebUrls.RCVD_FRIENDS_REQ
                        + PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                ),
                WebUrls.RCVD_FRIENDS_REQ_CODE, 1, postParam
            ).callService(flag)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivBackArrow -> {
                onBackPressed()
            }

            R.id.ivNotification -> {

                flag = "yes"
                checkStatusModeWallPaper()

//                 popupwindow_obj = showPopUpMenu()
//                popupwindow_obj!!.showAsDropDown(ivNotification)
//                popupwindow_obj.showAsDropDown(ivNotification, -40, 18)

            }
        }

    }


    fun changeStatus(userStatus: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance()
                        .getPreferenceData(this@ChatUsersList, WebUrls.KEY_USERID)
                )
                postParam.put("status", userStatus)

                RetrofitService(
                    this@ChatUsersList, this@ChatUsersList, WebUrls.HIDE_USER_STATUS,
                    WebUrls.HIDE_USER_STATUS_STATUS_CODE, 3, postParam
                ).callService(true)


            } catch (ex: java.lang.Exception) {
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
                    PreferenceFile.getInstance()
                        .getPreferenceData(this@ChatUsersList, WebUrls.KEY_USERID)
                )
                postParam.put("type", modeType)
                postParam.put("image", "")

                RetrofitService(
                    this@ChatUsersList, this@ChatUsersList, WebUrls.UPLOAD_WALLPAPER,
                    WebUrls.UPLOAD_WALLPAPER_CODE, 3, postParam
                ).callService(true)

                Log.e(TAG, "UPLOAD_WALLPAPER_CODE" + postParam.toString())

            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun checkStatusModeWallPaper() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this,
                    this@ChatUsersList,
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

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun showPopUpMenu(userStatus1: String, modeType1: String): PopupWindow {

        var popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        var inflater =
            this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(unilife.com.unilife.R.layout.chat_popup_option, null)
        val tvCreateGroup = view.findViewById(R.id.tvCreateGroup) as TextView
        val tvChangeWallpaper = view.findViewById(R.id.tvChangeWallpaper) as TextView
        val SwStatus = view.findViewById(R.id.SwStatus) as Switch
        val tvReqRcvd = view.findViewById(R.id.tvReqRcvd) as TextView
        val SwChangeMode = view.findViewById(R.id.SwChangeMode) as Switch

        popupWindow.isFocusable = true

        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.grey_outline, null))
        popupWindow.contentView = view


        if (modeType1.equals("black")) {
            SwChangeMode.isChecked = true
        } else if (modeType1.equals("white")) {
            SwChangeMode.isChecked = false
        } else {
            SwChangeMode.isChecked = false
        }

        if (userStatus1.equals("show")) {
            SwStatus.isChecked = true
        } else SwStatus.isChecked = !userStatus1.equals("hide")

        Log.e("DATA>>", userStatus1 + " modeType : " + modeType1)

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


        tvReqRcvd.setOnClickListener {
            try {
                startActivity(Intent(this@ChatUsersList, RequestReceivedList::class.java))
                popupWindow.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        tvChangeWallpaper.setOnClickListener {
            try {
                startActivity(Intent(this@ChatUsersList, ChangeChatWallpaper::class.java))
                popupWindow.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return popupWindow
    }

    override fun onResponse(requestCode: Int, response: String) {

        try {

            when (requestCode) {

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
                    popupwindow_obj!!.showAsDropDown(ivNotification)
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

                WebUrls.UPLOAD_WALLPAPER_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "UPLOAD_WALLPAPER_CODE:" + obj.toString())

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


                WebUrls.SEND_FRIEND_REQ_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "SEND_FRIEND_REQ_CODE:$obj")

                    if (obj.getBoolean("response")) {
                        customDialog(this, "Unilife", obj.getString("message"))
                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }


                WebUrls.CANCEL_FRIEND_REQ_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "CANCEL_FRIEND_REQ_CODE:$obj")

                    if (obj.getBoolean("response")) {

                        customDialog(this, "Unilife", "Friend request cancelled successfully")

                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))

                    }
                }

                WebUrls.RCVD_FRIENDS_REQ_CODE -> {

                    val obj = JSONObject(response)

                    friendsList.clear()

                    Log.e(TAG, "RCVD_FRIENDS_REQ_CODE:" + obj.toString())

                    if (obj.getBoolean("response")) {

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {

                            for (i in 0 until data.length()) {

                                val dataObj = data.getJSONObject(i)

                                val chatUserDetailsModel = ChatUserDetailsModel()

                                chatUserDetailsModel.id = dataObj.getString("id")
                                chatUserDetailsModel.user_id = dataObj.getString("user_id")
                                chatUserDetailsModel.friend_id = dataObj.getString("friend_id")
                                chatUserDetailsModel.status = dataObj.getString("status")

                                val requestFriendObj = dataObj.getJSONObject("requestfriend")
                                chatUserDetailsModel.friend_username =
                                    requestFriendObj.getString("username")
                                chatUserDetailsModel.friend_profile_image =
                                    requestFriendObj.getString("profile_image")

                                friendsList.add(chatUserDetailsModel)

                            }

                            Log.e(TAG, "friendsList:" + friendsList.size)

                            setFriendsAdapter(friendsList, key)

                        }
                    }
                }


                WebUrls.SHOW_FRIENDS_LISTING_CODE -> {

                    val obj = JSONObject(response)

                    friendsList.clear()

                    Log.e(TAG, "SHOW_FRIENDS_LISTING_CODE:" + obj.toString())

                    if (obj.getBoolean("response")) {

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {

                            for (i in 0 until data.length()) {

                                val dataObj = data.getJSONObject(i)

                                val chatUserDetailsModel = ChatUserDetailsModel()

                                chatUserDetailsModel.id = dataObj.getString("id")
                                chatUserDetailsModel.user_id = dataObj.getString("user_id")
                                chatUserDetailsModel.friend_id = dataObj.getString("friend_id")
                                //                       chatUserDetailsModel.status = dataObj.getString("status")

                                val userfriendObj = dataObj.getJSONObject("userfriend")
                                chatUserDetailsModel.friend_username =
                                    userfriendObj.getString("username")
                                chatUserDetailsModel.friend_profile_image =
                                    userfriendObj.getString("profile_image")

                                friendsList.add(chatUserDetailsModel)
                            }

                            Log.e(TAG, "friendsList:" + friendsList.size)

                            setFriendsAdapter(friendsList, key)

                        }
                    } else {
//                        {"response":false,"message":"no friend is found"}
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }


                WebUrls.VIEW_MORE_SUGGESTIONS_CODE -> {

                    val obj = JSONObject(response)

                    friendsList.clear()
                    friendsListFull.clear()

                    Log.e(TAG, "VIEW_MORE_SUGGESTIONS_CODE:" + obj.toString())

                    if (obj.getBoolean("response")) {

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {

                            for (i in 0 until data.length()) {

                                val dataObj = data.getJSONObject(i)

                                val chatUserDetailsModel = ChatUserDetailsModel()

                                chatUserDetailsModel.id = dataObj.getString("id")
                                chatUserDetailsModel.username = dataObj.getString("username")
                                chatUserDetailsModel.friend_username = dataObj.getString("username")
                                chatUserDetailsModel.friend_profile_image =
                                    dataObj.getString("profile_image")

                                if (dataObj.has("user_friend_request") && !dataObj.isNull("user_friend_request")) {

                                    val userFriendRequestObj =
                                        dataObj.getJSONObject("user_friend_request")

                                    chatUserDetailsModel.user_friend_request_id =
                                        dataObj.getString("id")
                                    chatUserDetailsModel.user_id =
                                        userFriendRequestObj.getString("user_id")
                                    chatUserDetailsModel.friend_id =
                                        userFriendRequestObj.getString("friend_id")
                                    chatUserDetailsModel.status =
                                        userFriendRequestObj.getString("status")

                                    chatUserDetailsModel.check_req_status =
                                        "1"  // show cancel Request

                                } else {

                                    chatUserDetailsModel.friend_id =
                                        dataObj.getString("id")  // friend id

                                    chatUserDetailsModel.check_req_status = "0" // show send request
                                }

                                friendsList.add(chatUserDetailsModel)
                                friendsListFull.add(chatUserDetailsModel)

                            }

                            Log.e(TAG, "friendsList:" + friendsList.size)

                            setFriendsAdapter(friendsList, key)

                        }
                    }
                }
            }

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }


    fun customDialog(context: Context, title: String, msg: String) {

        val dialogBuilder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        val tvTitle = dialogView.tvTitle
        val tvOk = dialogView.tvok
        val tvMsg = dialogView.tvMsg

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        tvTitle.text = title
        tvMsg.text = msg

        tvOk.setOnClickListener(View.OnClickListener {
            showFriendsList(false)
            alertDialog.dismiss()
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }


    private fun setSearchFilter() {


        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                filterData(s.toString())
            }

        })


    }

    private fun filterData(searchText: String) {
        try {


            if (searchText.isNotEmpty()) {
                friendsList.clear()

                Log.e("SearchText", searchText)

                friendsList = friendsListFull.filter {
                    it.username.toLowerCase().trim().startsWith(searchText.toLowerCase().trim())
                } as ArrayList<ChatUserDetailsModel>


            } else {

                Log.e("InsideEmpty", "Yes")

                friendsList.clear()
                friendsList.addAll(friendsListFull)

            }

            Log.e("ListSize", friendsList.size.toString())


            if (chatUsersListAdapter != null) {
                chatUsersListAdapter!!.notifyDataSetChanged()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}



