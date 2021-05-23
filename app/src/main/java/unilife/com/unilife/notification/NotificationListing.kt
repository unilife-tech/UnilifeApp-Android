package unilife.com.unilife.notification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_notification_listing2.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog2.view.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class NotificationListing : AppCompatActivity(), RetrofitResponse {

    var notificationList: ArrayList<NotificationListData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_listing2)

        setData()

        getNotifications()

        setOnClick()

    }

    private fun setData() {
        ivNotification.visibility = View.GONE
        tvTitle.text = "Notifications"
    }

    private fun setOnClick() {
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
        tv_clearNoti.setOnClickListener {
            customDialog(this, "Unilife", "All notifications will be cleared . Are you sure ?")
        }
    }

    private fun deleteNotification() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this,
                    this,
                    WebUrls.DELETE_NOTIFICATION + PreferenceFile.getInstance().getPreferenceData(
                        this,
                        WebUrls.KEY_USERID
                    )
                    ,
                    WebUrls.DELETE_NOTIFICATION_CODE,
                    1
                ).callService(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            unilife.com.unilife.utils.Common.customDialog(
                this,
                "Unilife",
                resources.getString(R.string.internet_offline)
            )
        }
    }

    private fun getNotifications() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this, this, WebUrls.GET_NOTIFICATION + PreferenceFile.getInstance()
                        .getPreferenceData(this, WebUrls.KEY_USERID)
                    , WebUrls.GET_NOTIFICATION_CODE, 1
                ).callService(true)
            } catch (e: Exception) {

                e.printStackTrace()
            }
        } else {
            unilife.com.unilife.utils.Common.customDialog(
                this,
                "Unilife",
                resources.getString(R.string.internet_offline)
            )
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        try {

            when (requestCode) {

                WebUrls.GET_NOTIFICATION_CODE -> {
                    notificationList.clear()
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        val data = obj.getJSONArray("data")
                        if (data.length() > 0) {
                            for (i in 0 until data.length()) {
                                var dataobj = data.getJSONObject(i)
                                val notificationListData = NotificationListData()

                                notificationListData.id = dataobj.getString("id")
                                notificationListData.sender_id = dataobj.getString("sender_id")
                                notificationListData.receiver_id = dataobj.getString("receiver_id")
                                notificationListData.message = dataobj.getString("message")
                                notificationListData.admin_id = dataobj.getString("admin_id")
                                notificationListData.type = dataobj.getString("type")
                                notificationListData.created_at = dataobj.getString("created_at")

                                if (!dataobj.isNull("notification_user")) {
                                    var notification_user =
                                        dataobj.getJSONObject("notification_user")
                                    if (notification_user != null) {
                                        notificationListData.username =
                                            notification_user.getString("username")
                                        notificationListData.profile_image =
                                            notification_user.getString("profile_image")
                                    }
                                }
                                notificationList.add(notificationListData)
                            }
                        }
                        Log.e("LIST", "" + notificationList!!.size)
                        setAdapter(notificationList)
                    }
                }
                WebUrls.DELETE_NOTIFICATION_CODE -> {
                    notificationList.clear()
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        setAdapter(notificationList)
                        unilife.com.unilife.utils.Common.customDialog(
                            this,
                            "Unilife",
                            "Notifications are cleared"
                        )

                    } else {
                        unilife.com.unilife.utils.Common.customDialog(
                            this,
                            "Unilife",
                            "Please try Again"
                        )
                    }

                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAdapter(notificationList: ArrayList<NotificationListData>) {
        var notificationListingAdapter = NotificationListingAdapter(this, notificationList)
        rv_notification?.adapter = notificationListingAdapter
    }

    data class NotificationListData(
        var id: String = "",
        var sender_id: String = "",
        var receiver_id: String = "",
        var admin_id: String = "",
        var message: String = "",
        var type: String = "",
        var created_at: String = "",
        var username: String = "",
        var profile_image: String = ""

    )


    fun customDialog(context: Context, title: String, msg: String) {

        val dialogBuilder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog2, null)

        val tvTitle = dialogView.tvTitle2
        val tvOk = dialogView.tvok2
        val tvMsg = dialogView.tvMsg2
        val tvcancel = dialogView.tvcancel2

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        tvTitle.text = title
        tvMsg.text = msg

        tvOk.setOnClickListener {
            deleteNotification()
            alertDialog.dismiss()
        }

        tvcancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}
