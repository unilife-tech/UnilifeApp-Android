package unilife.com.unilife.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.adapter.RequestListingAdapter
import unilife.com.unilife.chat.model.ReceivedRequestResponse
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.retrofit.WebUrls.KEY_USERID
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common


class RequestReceivedList : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var friendsList: ArrayList<ChatUserDetailsModel> = ArrayList()
    var chatUsersListAdapter: ChatUsersListAdapter? = null
    val TAG = RequestReceivedList::class.java.simpleName
    var key: String = "Request Received"
    private var alertDialog: AlertDialog? = null
    var posAcceptGroup = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        ivNotification.background = getDrawable(R.drawable.dot_line)
        ivNotification.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))
        tvTitle.text = "Request Received"
        llSearch.visibility = GONE
        ivNotification.visibility = GONE
        setOnClickListener()
        LocalBroadcastManager.getInstance(this@RequestReceivedList)
            .registerReceiver(friendRequest, IntentFilter("Friend Request"))
//        receivedRequestList(true)
        getRequest()
    }


    fun getRequest() {
//        if (!isNetworkConnected()) return

//        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
//        chatDetailRequest.setReceiver_id(receiverId);
//        chatDetailRequest.setSender_id(senderId);
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getRequest(
            PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID)
        )
        call.enqueue(object : Callback<ReceivedRequestResponse?> {
            override fun onResponse(
                call: Call<ReceivedRequestResponse?>,
                response: Response<ReceivedRequestResponse?>
            ) {
//                hideProgressDialog()
                Log.e("response code -->", "" + response.code())
                if (response.isSuccessful) {
                    rycFriendlist.setHasFixedSize(true)
                    rycFriendlist.layoutManager = LinearLayoutManager(this@RequestReceivedList)
                    rycFriendlist.adapter = RequestListingAdapter(
                        this@RequestReceivedList,
                        response.body()?.data
                    )
                }
            }

            override fun onFailure(
                call: Call<ReceivedRequestResponse?>,
                t: Throwable
            ) {
//                hideProgressDialog()
//                showToast(t.message)
            }
        })
    }

    fun callAcceptReject() {
//        if (!isNetworkConnected()) return

//        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
//        chatDetailRequest.setReceiver_id(receiverId);
//        chatDetailRequest.setSender_id(senderId);
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getRequest(
            PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID)
        )
        call.enqueue(object : Callback<ReceivedRequestResponse?> {
            override fun onResponse(
                call: Call<ReceivedRequestResponse?>,
                response: Response<ReceivedRequestResponse?>
            ) {
//                hideProgressDialog()
                Log.e("response code -->", "" + response.code())
                if (response.isSuccessful) {
                    rycFriendlist.setHasFixedSize(true)
                    rycFriendlist.layoutManager = LinearLayoutManager(this@RequestReceivedList)
                    rycFriendlist.adapter = RequestListingAdapter(
                        this@RequestReceivedList,
                        response.body()?.data
                    )
                }
            }

            override fun onFailure(
                call: Call<ReceivedRequestResponse?>,
                t: Throwable
            ) {
//                hideProgressDialog()
//                showToast(t.message)
            }
        })
    }


    internal var friendRequest: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            receivedRequestList(false)

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

            }

            override fun onProfileImageClick(position: Int) {
                if (friendsList[position].type == "group") {
                    startActivity(
                        Intent(this@RequestReceivedList, GroupDetails::class.java)
                            .putExtra("group_id", friendsList[position].groupId)
                    )
                } else {
                    /* startActivity(
                         Intent(this@RequestReceivedList, ShowOtherProfile::class.java)
                             .putExtra("post_user_id", friendsList[position].friend_id)
                     )*/
                }
            }

            override fun onPerformAction(
                position: Int, status: String, friendsList: ArrayList<ChatUserDetailsModel>?
            ) {
                // 0 >> send request, 1 >> cancel Req
                Log.e("ONCLICK", "onPerformAction")
                performActionDialog(position)
            }
        })

    }

    private fun performAction(pos: Int, status: String, userId: String, acceptId: String) {
        val postParam = JSONObject()
        if (Alerts.isNetworkAvailable(this)) {
            try {

                postParam.put(
                    "user_id",
                    userId
                )

                if (status == "0") {
                    posAcceptGroup = pos
                    postParam.put(
                        "accept_id",
                        acceptId
                    )  // friend id for accepting request
                    Log.e("url", WebUrls.BASE_URL + WebUrls.ACCEPT_FRIEND_REQ)
                    RetrofitService(
                        this, this, WebUrls.ACCEPT_FRIEND_REQ,
                        WebUrls.ACCEPT_FRIEND_REQ_CODE, 3, postParam
                    ).callService(true)
                } else if (status == "1") {
                    postParam.put("reject_id", acceptId)
                    postParam.put("group_id", friendsList[pos].groupId)

                    Log.e("url", WebUrls.BASE_URL + WebUrls.REJECT_FRIEND_REQ)
                    RetrofitService(
                        this, this, WebUrls.REJECT_FRIEND_REQ,
                        WebUrls.REJECT_FRIEND_REQ_CODE, 3, postParam
                    ).callService(true)
                }

                Log.e(TAG, "performAction:$postParam")

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun callAddParticipantsApi(groupId: String) {
        try {

            var jsonObject = JSONObject()
            jsonObject.put("group_id", groupId)

            var jsonArray = JSONArray()
            jsonArray.put(
                PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                )
            )

            jsonObject.put("user_id", jsonArray)

            RetrofitService(
                this,
                this,
                WebUrls.ADD_PARTCIPANT,
                WebUrls.ADD_PARTCIPANT_CODE,
                3,
                jsonObject
            ).callService(true)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun receivedRequestList(flag: Boolean) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

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
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivBackArrow -> {
                onBackPressed()
            }

            R.id.ivNotification -> {
                Log.e(TAG, "ivNotification")
                val popupwindow_obj = showPopUpMenu()
                popupwindow_obj.showAsDropDown(ivNotification)
//                popupwindow_obj.showAsDropDown(ivNotification, -40, 18)
            }
        }

    }

    private fun showPopUpMenu(): PopupWindow {

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

        tvReqRcvd.setOnClickListener {

            Log.e(TAG, "clicked")
            receivedRequestList(true)
            popupWindow.dismiss()

        }


        return popupWindow
    }

    override fun onResponse(requestCode: Int, response: String) {
        try {

            when (requestCode) {

                WebUrls.ACCEPT_FRIEND_REQ_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "SEND_FRIEND_REQ_CODE:" + obj.toString())

                    if (obj.getBoolean("response")) {

                        customDialog(this, "Unilife", obj.getString("message"))

                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }

                WebUrls.REJECT_FRIEND_REQ_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "REJECT_FRIEND_REQ_CODE:$obj")

                    if (obj.getBoolean("response")) {

                        customDialog(this, "Unilife", "Request cancelled successfully")

                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))

                    }
                }

                WebUrls.RCVD_FRIENDS_REQ_CODE -> {

                    val obj = JSONObject(response)

                    friendsList.clear()

                    Log.e(TAG, "RCVD_FRIENDS_REQ_CODE:$obj")

                    if (obj.getBoolean("response")) {

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {

                            Log.e(TAG, "RCVD_FRIENDS_REQ_CODE:data.length()::" + data.length())


                            for (i in 0 until data.length()) {

                                val dataObj = data.getJSONObject(i)

                                val chatUserDetailsModel = ChatUserDetailsModel()

                                chatUserDetailsModel.id = dataObj.getString("id")
                                chatUserDetailsModel.friend_id =
                                    dataObj.getString("user_id")  // friend id
                                chatUserDetailsModel.user_id =
                                    dataObj.getString("friend_id") // login user id
                                chatUserDetailsModel.status = dataObj.getString("status")
                                chatUserDetailsModel.type = dataObj.getString("type")

                                if (dataObj.getString("type") == "group") {
                                    if (!dataObj.isNull("groupDtl")) {
                                        val groupDtl = dataObj.getJSONObject("groupDtl")

                                        if (!groupDtl.isNull("usergroup")) {
                                            var userGroup = groupDtl.getJSONObject("usergroup")
                                            chatUserDetailsModel.friend_username =
                                                userGroup.getString("group_name")
                                            chatUserDetailsModel.friend_profile_image =
                                                userGroup.getString("group_image")
                                        }
                                        chatUserDetailsModel.groupId =
                                            groupDtl.getString("group_id")
                                        chatUserDetailsModel.groupUserId =
                                            groupDtl.getString("user_id")
                                    }

                                } else {
                                    if (!dataObj.isNull("requestfriend")) {
                                        val requestFriendObj =
                                            dataObj.getJSONObject("requestfriend")
                                        chatUserDetailsModel.friend_username =
                                            requestFriendObj.getString("username")
                                        chatUserDetailsModel.friend_profile_image =
                                            requestFriendObj.getString("profile_image")
                                    }

                                }

                                friendsList.add(chatUserDetailsModel)

                            }

                            Log.e(TAG, "friendsList:" + friendsList.size)

                            setFriendsAdapter(friendsList, key)

                        }
                    } else {
                        friendsList.clear()
                        if (chatUsersListAdapter != null) {
                            chatUsersListAdapter!!.notifyDataSetChanged()
                        }
                        Common.customDialog(this, "Unilife", obj.getString("message"))

                    }
                }

                WebUrls.ADD_PARTCIPANT_CODE -> {
                    try {
                        val obj = JSONObject(response)
                        Log.e("AcceptGroup", "$obj")

                        if (obj.getBoolean("response")) {
                            if (obj.getString("message") == "Participant added successfully") {
                                friendsList.removeAt(posAcceptGroup)
                                chatUsersListAdapter!!.notifyItemRemoved(posAcceptGroup)
                                chatUsersListAdapter!!.notifyItemRangeChanged(
                                    posAcceptGroup,
                                    friendsList.size
                                )
                                Common.customDialog(
                                    this,
                                    "Unilife",
                                    "Group invitation accepted successfully!"
                                )
                            }

                        } else {

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }

    fun performActionDialog(position: Int) {

        var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this).inflate(R.layout.perform_action_options, null)
        var tvAccept = view.findViewById(R.id.tvAccept) as TextView
        var tvReject = view.findViewById(R.id.tvReject) as TextView
        var tvCancel = view.findViewById(R.id.tvCancel) as TextView

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)

        tvAccept.setOnClickListener {
            try {
                if (friendsList[position].type == "group") {
                    callAddParticipantsApi(friendsList[position].groupId)
                } else {
                    performAction(
                        position,
                        "0",
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID),
                        friendsList[position].friend_id
                    )
                }
                alertDialog!!.dismiss()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        tvReject.setOnClickListener {
            try {
                if (friendsList[position].type == "group") {
                    performAction(
                        position,
                        "1",
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID),
                        friendsList[position].groupUserId
                    )
                } else {
                    performAction(
                        position,
                        "1",
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID),
                        friendsList[position].friend_id
                    )
                }
                alertDialog!!.dismiss()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        tvCancel.setOnClickListener {
            alertDialog!!.dismiss()
        }

        alertDialog!!.window
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.85
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog!!.window.setLayout(width.toInt(), height)
        alertDialog!!.window.setGravity(Gravity.BOTTOM)
        alertDialog!!.show()

    }


    fun acceptRejectRequest(requestId: String) {

        var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this).inflate(R.layout.perform_action_options, null)
        var tvAccept = view.findViewById(R.id.tvAccept) as TextView
        var tvReject = view.findViewById(R.id.tvReject) as TextView
        var tvCancel = view.findViewById(R.id.tvCancel) as TextView

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)

        tvAccept.setOnClickListener {
            alertDialog!!.dismiss()

        }

        tvReject.setOnClickListener {
            alertDialog!!.dismiss()
        }

        tvCancel.setOnClickListener {
            alertDialog!!.dismiss()
        }

        alertDialog!!.window
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.85
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog!!.window.setLayout(width.toInt(), height)
        alertDialog!!.window.setGravity(Gravity.BOTTOM)
        alertDialog!!.show()

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

        tvOk.setOnClickListener {
            receivedRequestList(false)
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}



