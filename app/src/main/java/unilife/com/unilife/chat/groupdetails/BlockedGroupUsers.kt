package unilife.com.unilife.chat.groupdetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.blocked_group_users_layout.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class BlockedGroupUsers : AppDrawer(), RetrofitResponse, BlockedGroupUsersAdapter.ClickGroupBlock {
    private var blockedGroupUsersAdapter: BlockedGroupUsersAdapter? = null
    private var groupId = ""
    private var groupBlockUserList = ArrayList<GroupBlockUser>()
    private var posUnblock = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blocked_group_users_layout)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivBack.visibility = View.VISIBLE
        ivBack.setOnClickListener {
            super.onBackPressed()
        }
        ivProfileImg.visibility = View.GONE
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = getString(R.string.BlockedGroupUsers)
        getIntentData()
        setAdapter()
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("groupId")) {
                groupId = intent.getStringExtra("groupId")
                callGroupBlockListApi()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAdapter() {
        try {
            blockedGroupUsersAdapter = BlockedGroupUsersAdapter(this, groupBlockUserList)

            rvBlockedGroupUsers?.adapter = blockedGroupUsersAdapter
            blockedGroupUsersAdapter!!.initClickGroupBlock(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun clickUnblock(position: Int) {
        try {
            posUnblock = position
            callUnblockService()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callGroupBlockListApi() {
        try {
            var jsonObject = JSONObject()
            jsonObject.put(
                "user_id",
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            )
            jsonObject.put("group_id", groupId)
            RetrofitService(
                this,
                this,
                WebUrls.GROUP_BLOCK_LIST,
                WebUrls.GROUP_BLOCK_LIST_CODE,
                3,
                jsonObject
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callUnblockService() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                var postparam = JSONObject()
                postparam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postparam.put("block_id", groupBlockUserList[posUnblock].id)

                RetrofitService(
                    this, this, WebUrls.UNBLOCK_USER, WebUrls.UNBLOCK_USER_CODE,
                    3, postparam
                ).callService(true)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        when (requestCode) {
            WebUrls.GROUP_BLOCK_LIST_CODE -> {
                try {
                    Log.e("resBlockGroupIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        groupBlockUserList.clear()
                        if (!jsonObject.isNull("data")) {
                            var dataArray = jsonObject.getJSONArray("data")
                            if (dataArray.length() > 0) {
                                for (i in 0 until dataArray.length()) {
                                    var data = dataArray.getJSONObject(i)
                                    var groupBlockUser = GroupBlockUser()
                                    groupBlockUser.id = data.getString("id")
                                    groupBlockUser.user_type = data.getString("user_type")
                                    groupBlockUser.is_online = data.getString("is_online")
                                    groupBlockUser.username = data.getString("username")
                                    groupBlockUser.profile_image = data.getString("profile_image")
                                    groupBlockUserList.add(groupBlockUser)
                                }
                            }
                        }
                        blockedGroupUsersAdapter!!.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.UNBLOCK_USER_CODE -> {
                try {
                    val obj = JSONObject(response)

                    if (obj.getBoolean("response")) {
                        Log.e("responseUnBlock", "" + obj.toString())
                        groupBlockUserList.removeAt(posUnblock)
                        blockedGroupUsersAdapter!!.notifyItemRemoved(posUnblock)
                        blockedGroupUsersAdapter!!.notifyItemRangeChanged(
                            posUnblock,
                            groupBlockUserList.size
                        )

                        Common.customDialog(this, "Unilife", "User Unblocked")

                    } else {

                        Common.customDialog(
                            this, "Unilife", "No Users Found"
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    data class GroupBlockUser(
        var id: String = "",
        var user_type: String = "",
        var is_online: String = "",
        var username: String = "",
        var profile_image: String = ""
    )

}
