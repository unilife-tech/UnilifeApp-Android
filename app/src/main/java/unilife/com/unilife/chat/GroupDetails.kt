package unilife.com.unilife.chat

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.group_details.*
import org.json.JSONObject
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class GroupDetails : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    val TAG = GroupListing::class.java.simpleName
    var groupId: String = ""
    var memId: String = ""
    var memName: String = ""
    var memImg: String = ""

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_details)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        tvTitle.text = "Group Details"

        ivNotification.visibility = GONE

        getIntentData()

        showGroupDetails()

        setOnClickListener()

    }


    private fun getIntentData() {
        try {
            groupId = if (intent.hasExtra("group_id")) {
                intent.getStringExtra("group_id")
            } else {
                var groupModel = intent!!.getSerializableExtra("data") as? GroupModel
                groupModel!!.group_id
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showGroupDetails() {
        if (Alerts.isNetworkAvailable(this)) {
            RetrofitService(
                this,
                this,
                WebUrls.GROUP_DETAILS + "/" + groupId,
                WebUrls.GROUP_DETAILS_REQ_CODE,
                1
            ).callService(true)
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun setGroupsAdapter(memberList: ArrayList<GroupModel.MembersModel>) {

        val layoutManager = LinearLayoutManager(this)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvMembers?.layoutManager = layoutManager

        val groupAdapter = GroupDetailsAdapter(this, memberList)
        rvMembers?.adapter = groupAdapter

        groupAdapter.setOnItemClickListener(object : GroupDetailsAdapter.onItemClickListener {
            override fun onUnJoinClick(position: Int, groupList: ArrayList<GroupModel>?) {
            }

            override fun onItemClick(position: Int, groupList: ArrayList<GroupModel>?) {
            }
        })

    }


    fun setOnClickListener() {
        ivBackArrow.setOnClickListener(this)
        ivNotification.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivBackArrow -> {
                onBackPressed()
            }
        }
    }


    override fun onResponse(requestCode: Int, response: String) {


        when (requestCode) {

            WebUrls.GROUP_DETAILS_REQ_CODE -> {

                try {

                    clMain.visibility = VISIBLE

                    var jsonObject = JSONObject(response)

                    Log.e(TAG, "GROUP_DETAILS_REQ_CODE:" + jsonObject.toString())


                    if ((jsonObject.getBoolean("response"))) {


                        val data = jsonObject.getJSONObject("data")

                        tvGrpName.text = data.getString("group_name")

                        if (!data.isNull("group_image")) {
                            Picasso.with(this)
                                .load(WebUrls.PROFILE_IMAGE_URL + data.getString("group_image"))
                                .placeholder(R.drawable.no_image).into(ivGroupImg)

                        } else {
                            ivGroupImg.setImageResource(R.drawable.no_image)
                        }


                        var membersList = ArrayList<GroupModel.MembersModel>()

                        membersList.clear()

                        if (data.has("users_in_group") && !data.isNull("users_in_group")) {

                            var users_in_group = data.getJSONArray("users_in_group")

                            if (users_in_group.length() > 0) {

                                for (i in 0 until users_in_group.length()) {

                                    var users_in_groupObj = users_in_group.getJSONObject(i)

                                    if (users_in_groupObj.has("group_user_detail")
                                        && !users_in_groupObj.isNull("group_user_detail")
                                    ) {

                                        var group_user_detail =
                                            users_in_groupObj.getJSONObject("group_user_detail")

                                        memId = group_user_detail.getString("id")
                                        memName = group_user_detail.getString("username")
                                        if (group_user_detail.has("profile_image") && !group_user_detail.isNull(
                                                "profile_image"
                                            )
                                        ) {
                                            memImg = group_user_detail.getString("profile_image")
                                        } else {
                                            memImg = ""
                                        }

                                        var memModel =
                                            GroupModel.MembersModel(memId, memName, memImg)

                                        membersList.add(memModel)

                                    }
                                }
                            }

                            setGroupsAdapter(membersList)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }

    }


}
