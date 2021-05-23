package unilife.com.unilife.chat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_blocked_user.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class BlockedUser : AppCompatActivity(), RetrofitResponse {

    var blockUserList: ArrayList<BlockedUserData> = ArrayList()
//    @BindView(R.id.recycvlerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_user)
        ButterKnife.bind(this)



        showBlockedUsers()

        ivNotification.visibility= View.GONE
        tvTitle.text = "Blocked Users"
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {
                WebUrls.SHOW_BLOCKED_USERS_CODE -> {
                    val obj = JSONObject(response)
                        blockUserList.clear()
                    if (obj.getBoolean("response")) {
                        Log.e("responsedffe",""+obj.toString())
                        var data = obj.getJSONArray("data")
                        if (data.length() > 0) {
                            for (i in 0 until data.length()) {
                                val dataobj = data.getJSONObject(i)
                                val blockedUser = BlockedUserData()
                                if (dataobj.has("blockuser")) {
                                    var blockuser = dataobj.getJSONObject("blockuser")
                                    if (blockuser != null) {
                                        blockedUser.blocked_id = blockuser.getString("id")
                                        blockedUser.profile_image = blockuser.getString("profile_image")
                                        blockedUser.username = blockuser.getString("username")


                                        blockUserList.add(blockedUser)
                                    }
                                }
                            }
                            setAdapter(blockUserList)

                        }

                    }
                    else{
                        blockUserList.clear()
                       setAdapter(blockUserList)
                        Log.e("responsedffe",""+obj.toString())
                        Common.customDialog(this,"Unilife","No Users Found")

                    }

                }
                WebUrls.UNBLOCK_USER_CODE->{
                    val obj = JSONObject(response)

                    if (obj.getBoolean("response")) {
                        Log.e("response",""+obj.toString())
                        showBlockedUsers()
                        Common.customDialog(this, "Unilife","User Unblocked")

                    }
                    else{
                        Log.e("response",""+obj.toString())
                        showBlockedUsers()
                        Common.customDialog(this,"Unilife","No Users Found")
                    }
                }
            }
        } catch (e: Exception) {


        }
    }


    private fun showBlockedUsers() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this, WebUrls.SHOW_BLOCKED_USERS +
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            , WebUrls.SHOW_BLOCKED_USERS_CODE,
            1
        ).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }


    fun setAdapter(blockUserList: ArrayList<BlockedUserData>) {
        val linearLayoutManager = LinearLayoutManager(this)
        recycvlerView?.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val blockedUserAdapter = BlockedUserAdapter(this, blockUserList)
        recycvlerView?.adapter = blockedUserAdapter

        blockedUserAdapter.setOnItemClickListener(object : BlockedUserAdapter.onItemClickListener {
            override fun onUnblockClick(i: Int, blocked_id: String) {
                callUnblockService(blocked_id)
            }
        }
        )

    }

    private fun callUnblockService(blocked_id: String) {
        if(Alerts.isNetworkAvailable(this)){
        try {
            var postparam = JSONObject()
            postparam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
            postparam.put("block_id", blocked_id)

            RetrofitService(
                this, this, WebUrls.UNBLOCK_USER, WebUrls.UNBLOCK_USER_CODE,
                3, postparam
            ).callService(true)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    data class BlockedUserData(
        var blocked_id: String = "",
        var profile_image: String = "",
        var username: String = ""
    )

}
