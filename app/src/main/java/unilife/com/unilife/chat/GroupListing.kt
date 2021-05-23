package unilife.com.unilife.chat

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.group_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.model.ExitGroupRequest
import unilife.com.unilife.chat.update.GroupChatDetailsActivity
import unilife.com.unilife.profile.model.responses.CommonResponse
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import java.io.Serializable

class GroupListing : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var groupList: ArrayList<GroupModel> = ArrayList()

    val TAG = GroupListing::class.java.simpleName

    var groupAdapter: GroupAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.group_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        tvTitle.text = "Groups"

        ivNotification.visibility = GONE

        ivNotification.background = getDrawable(R.drawable.dot_line)

        ivNotification.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))

        setOnClickListener()


    }

    override fun onResume() {
        super.onResume()
        showGroups()
        applyFilter()
    }

    fun applyFilter() {

        etSearchGrpName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (groupAdapter != null) {
                    groupAdapter!!.filterResult(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }


    fun showGroups() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this,
                    this@GroupListing,
                    WebUrls.SEND_GROUP_LIST + "/" + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    ),
                    WebUrls.SEND_GROUP_LIST_CODE,
                    1
                ).callService(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun setGroupsAdapter(groupList: ArrayList<GroupModel>) {

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvList?.layoutManager = layoutManager

        groupAdapter = GroupAdapter(this, groupList)
        rvList?.adapter = groupAdapter

        groupAdapter!!.setOnItemClickListener(object : GroupAdapter.onItemClickListener {
            override fun onItemClick(position: Int, groupList: ArrayList<GroupModel>?) {

                try {
                    var intent = Intent(this@GroupListing, GroupDetails::class.java)
                    var groupModel = groupList!![position]
                    intent.putExtra("data", groupModel as Serializable)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onUnJoinClick(position: Int, groupList: ArrayList<GroupModel>?) {

                try {
//                    unjoinGroup(groupList!![position].group_id)
                    callExitGroupApi(groupList!![position].group_id)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            fun callExitGroupApi(groupId: String) {
//        if (!isNetworkConnected()) return
//        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
//        chatDetailRequest.setReceiver_id(receiverId);
//        chatDetailRequest.setSender_id(senderId);
                val exitGroupRequest = ExitGroupRequest()
                exitGroupRequest.setGroup_id(groupId)
                exitGroupRequest.setRemove_user_id(
                    PreferenceFile.getInstance()
                        .getPreferenceData(this@GroupListing, WebUrls.KEY_USERID)
                )

                val apiInterface =
                    ApiClient.getClient().create(ApiInterface::class.java)
                val call = apiInterface.exitGroup(
                    PreferenceFile.getInstance().getPreferenceData(this@GroupListing, WebUrls.KEY_USERID),
                    exitGroupRequest
                )
                call.enqueue(object : Callback<CommonResponse?> {
                    override fun onResponse(
                        call: Call<CommonResponse?>,
                        response: Response<CommonResponse?>
                    ) {
//                hideProgressDialog()
                        Log.e("response code -->", "" + response.code())
                        if (response.isSuccessful) {
                            showGroups()
                        }
                    }

                    override fun onFailure(
                        call: Call<CommonResponse?>,
                        t: Throwable
                    ) {
//                hideProgressDialog()
                    }
                })
            }


            override fun onGoToGroupChat(position: Int, groupList: ArrayList<GroupModel>?) {
                try {
                    var intent = Intent(this@GroupListing, GroupChatDetailsActivity::class.java)
                    var groupModel = groupList!![position]
                    intent.putExtra("groupId", groupModel.group_id)
                    intent.putExtra("groupName", groupModel.group_name)
                    intent.putExtra("groupImg", groupModel.group_image)
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

    }

    fun setOnClickListener() {
        tvCreateNew.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
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

            R.id.tvCreateNew -> {
                val intent = Intent(this, CreateGroupActivity::class.java)
                startActivity(intent)
            }

            R.id.ivNotification -> {
                val popupwindow_obj = showPopUpMenu()
                popupwindow_obj.showAsDropDown(ivNotification)
            }
        }
    }

    private fun showPopUpMenu(): PopupWindow {

        var popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        var inflater =
            this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.chat_popup_option, null)
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
            try {
                startActivity(Intent(this@GroupListing, RequestReceivedList::class.java))
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

                WebUrls.UNJOIN_GROUP_REQ_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, "UNJOIN_GROUP_REQ_CODE:" + obj.toString())

                    if (obj.getBoolean("response")) {
                        Common.customDialog(this, "Unilife", obj.getString("message"))

                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }

                    showGroups()


                }


                WebUrls.SEND_GROUP_LIST_CODE -> {

                    val obj = JSONObject(response)

                    groupList.clear()

                    Log.e(TAG, "SEND_GROUP_LIST_CODE:" + obj.toString())

                    val res = obj.getString("response")

                    if (obj.getBoolean("response")) {

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {
                            rvList.visibility=View.VISIBLE

                            for (i in 0 until data.length()) {
                                val dataObj = data.getJSONObject(i)
                                val groupModel = GroupModel()
                                groupModel.id = dataObj.getString("id")
                                groupModel.user_id = dataObj.getString("user_id")
                                groupModel.group_id = dataObj.getString("group_id")
                                groupModel.group_admin = dataObj.getString("group_admin")

                                val usergroupObj = dataObj.getJSONObject("usergroup")

                                groupModel.group_name = usergroupObj.getString("group_name")
                                groupModel.group_image = usergroupObj.getString("group_image")

                                if (usergroupObj.has("users_in_group") && !usergroupObj.isNull("users_in_group")) {

                                    val users_in_group = usergroupObj.getJSONArray("users_in_group")

                                    if (users_in_group.length() > 0) {
                                        groupModel.group_members_count =
                                            users_in_group.length().toString()
                                    } else {
                                        groupModel.group_members_count = "0"
                                    }

                                } else {
                                    groupModel.group_members_count = "0"
                                }

                                groupList.add(groupModel)

                            }

                            Log.e(TAG, "groupList:" + groupList.size)

                            setGroupsAdapter(groupList)

                        }else{
                            rvList.visibility=View.GONE
                        }
                    } else {
                        rvList.visibility=View.GONE
                        Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        try {
//            val intent = Intent(this, Home::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finishAffinity()
            finish()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
