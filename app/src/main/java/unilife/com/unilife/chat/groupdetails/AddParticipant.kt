package unilife.com.unilife.chat.groupdetails

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.add_participant_layout.*
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.json.JSONArray
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.util.*
import kotlin.collections.ArrayList

class AddParticipant : AppDrawer(), View.OnClickListener, RetrofitResponse,
    AddParticipantAdapter.onItemClickListener, SelectedParticipantAdapter.ClickSelected {
    private var addParticipantAdapter: AddParticipantAdapter? = null
    private var selectedParticipantAdapter: SelectedParticipantAdapter? = null
    private var friendsList: ArrayList<ShowFriendListModel> = ArrayList()
    private var selectedFriendsList: ArrayList<ShowFriendListModel> = ArrayList()
    private var userIdsGroupMember = ArrayList<String>()
    private var groupId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_participant_layout)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        ivBack.visibility = View.VISIBLE
        ivProfileImg.visibility = View.GONE
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = getString(R.string.Add_Participant)
        userIdsGroupMember.clear()
        getIntentData()
        setClickCallbacks()
        setSelectedMembersAdapter()
        callFriendListApi(true)
        applyFilter()
    }

    private fun applyFilter() {

        etSearch1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (addParticipantAdapter != null) {
                    addParticipantAdapter!!.filterResult(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("groupId")) {

                groupId = intent.getStringExtra("groupId")
            }
            if (intent.hasExtra("groupMemberList")) {
                var list =
                    intent!!.getSerializableExtra("groupMemberList") as ArrayList<GroupDetail.GroupUserModel>

                val names: List<String> =
                    object : AbstractList<String>() {
                        override fun get(location: Int): String? {
                            return list[location].user_id
                        }

                        override val size: Int
                            get() = list.size
                    }

                userIdsGroupMember.clear()
                userIdsGroupMember.addAll(names)
                Log.e("namesAre", names.toString())
                Log.e("userIds", userIdsGroupMember.toString())
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        Common.hideKeyboard(this, v)
        when (v!!.id) {
            R.id.ivBack -> {
                super.onBackPressed()
            }
            R.id.rlChat -> {
                val intent = Intent(this, ChatListing::class.java)
                startActivity(intent)
            }
            R.id.rlBlogs -> {
                val intent = Intent(this, Blog::class.java)
                startActivity(intent)
            }
            R.id.rlBrands -> {
                val intent = Intent(this, BrandsHome::class.java)
                startActivity(intent)
            }
            R.id.rlEvent -> {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            R.id.llAddMembers -> {
                if (selectedFriendsList.size == 0) {
                    Alerts.alertDialog(this, "Please select atleast one member to add!")
                } else {
                    callAddParticipantsApi()
                }
            }
        }
    }

    private fun setClickCallbacks() {
        try {
            ivBack.setOnClickListener(this)
            rlChat.setOnClickListener(this)
            rlBlogs.setOnClickListener(this)
            rlBrands.setOnClickListener(this)
            rlEvent.setOnClickListener(this)
            llAddMembers.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setFriendListAdapter() {
        try {
            addParticipantAdapter = AddParticipantAdapter(this, friendsList)
            rvParticipantsList.adapter = addParticipantAdapter
            addParticipantAdapter!!.setOnItemClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPerformAction(
        position: Int,
        status: String,
        friendsList: ArrayList<ShowFriendListModel>?
    ) {
        /* if (this.friendsList!![position].check_req_status == "1") {
             //Deselect
             this.friendsList!![position].check_req_status = "0"
         }*/
        if (addParticipantAdapter!!.friendsList!![position].check_req_status == "0") {
            //Select
            addParticipantAdapter!!.friendsList!![position].check_req_status = "1"
            selectedFriendsList.add(addParticipantAdapter!!.friendsList[position])
        }
        selectedParticipantAdapter!!.notifyDataSetChanged()
        addParticipantAdapter!!.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int, friendsList: ArrayList<ShowFriendListModel>?) {

    }

    private fun setSelectedMembersAdapter() {
        try {
            selectedParticipantAdapter = SelectedParticipantAdapter(this, selectedFriendsList)
            rvSelectedParticipants.adapter = selectedParticipantAdapter
            selectedParticipantAdapter!!.initClickSelected(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callFriendListApi(flag: Boolean) {
        if (Alerts.isNetworkAvailable(this)) {

            try {
                val postParam = JSONObject()

                postParam.put(
                    "user_id", PreferenceFile.getInstance().getPreferenceData(
                        this, WebUrls.KEY_USERID
                    )
                )
                postParam.put("group_id", groupId)

                RetrofitService(
                    this, this, WebUrls.SHOW_FRIEND_USER
                    ,
                    WebUrls.SHOW_FRIEND_USER_CODE, 3, postParam
                ).callService(flag)


            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun callAddParticipantsApi() {
        try {

            var jsonObject = JSONObject()
            jsonObject.put(
                "user_id", PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                )
            )
            jsonObject.put("group_id", groupId)

            var jsonArray = JSONArray()

            if (selectedFriendsList.size > 0) {
                for (i in 0 until selectedFriendsList.size) {
                    jsonArray.put(selectedFriendsList[i].id)
                }
            }

            jsonObject.put("request_id", jsonArray.toString())

            RetrofitService(
                this,
                this,
                WebUrls.SEND_MULTI_USER_FRIEND_REQUEST,
                WebUrls.SEND_MULTI_USER_FRIEND_REQUEST_CODE,
                3,
                jsonObject
            ).callService(true)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        when (requestCode) {
            WebUrls.SHOW_FRIEND_USER_CODE -> {

                val obj = JSONObject(response)
                Log.e("listFriend", response)
                friendsList.clear()

                if (obj.getBoolean("response")) {

                    val data = obj.getJSONArray("data")

                    if (data.length() > 0) {

                        for (i in 0 until data.length()) {

                            val dataObj = data.getJSONObject(i)

                            val showFriendListModel = ShowFriendListModel()

                            showFriendListModel.id = dataObj.getString("id")
                            showFriendListModel.user_type = dataObj.getString("user_type")
                            showFriendListModel.is_online = dataObj.getString("is_online")
                            showFriendListModel.username = dataObj.getString("username")
                            showFriendListModel.complete_profile =
                                dataObj.getString("complete_profile")
                            showFriendListModel.profile_image = dataObj.getString("profile_image")
                            showFriendListModel.university_school_id =
                                dataObj.getString("university_school_id")
                            showFriendListModel.university_school_email =
                                dataObj.getString("university_school_email")
                            showFriendListModel.profile_status = dataObj.getString("profile_status")

                            showFriendListModel.check_req_status = "0"

                            if (dataObj.has("status")) {
                                showFriendListModel.status = dataObj.getString("status")
                            } else {
                                showFriendListModel.status = ""
                            }
                            friendsList.add(showFriendListModel)
                        }
                        setFriendListAdapter()
                    }
                } else {
                    Common.customDialog(this, "Unilife", obj.getString("message"))
                }
            }
            WebUrls.SEND_MULTI_USER_FRIEND_REQUEST_CODE -> {
                try {
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        super.onBackPressed()
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_LONG)
                            .show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun removeSelected(position: Int) {
        try {
            if (addParticipantAdapter!!.friendsList.contains(selectedFriendsList[position])) {
                var indexIs =
                    addParticipantAdapter!!.friendsList.indexOf(selectedFriendsList[position])
                addParticipantAdapter!!.friendsList[indexIs].check_req_status = "0"
                addParticipantAdapter!!.notifyItemChanged(indexIs)
            }
            selectedFriendsList.removeAt(position)
            selectedParticipantAdapter!!.notifyItemRemoved(position)
            selectedParticipantAdapter!!.notifyItemRangeChanged(position, selectedFriendsList.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    data class ShowFriendListModel(

        var id: String = "",
        var user_type: String = "",
        var is_online: String = "",
        var username: String = "",
        var complete_profile: String = "",
        var profile_image: String = "",
        var university_school_id: String = "",
        var university_school_email: String = "",
        var status: String = "",
        var profile_status: String = "",
        var check_req_status: String = ""


    )
}
