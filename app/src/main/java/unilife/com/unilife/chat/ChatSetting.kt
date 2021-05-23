package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_chat_setting.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog2.view.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class ChatSetting : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var archive_status = ""
    var type = ""
    var userStatus = ""
    var received_backup_status =""
    var get_backup_status =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_setting)

        getArchiveStatus()
        getBackUpStatus()
        tvTitle.text = "Chat Settings"
        ivNotification.visibility = View.GONE
        setOnClickListner()

/*
        SwChangeMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                userStatus = "yes"
            } else {
                userStatus = "no"
            }
            try {
                backupStatus(userStatus)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } */
        SwChangeMode.setOnClickListener {
            if (SwChangeMode.isChecked) {
                userStatus = "yes"
            } else {
                userStatus = "no"
            }
            try {
                backupStatus(userStatus)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getBackUpStatus() {
        if(Alerts.isNetworkAvailable(this)){
        try {
            RetrofitService(
                this, this, WebUrls.GET_BACKUP+PreferenceFile.getInstance()
                    .getPreferenceData(this,WebUrls.KEY_USERID), WebUrls.GET_BACKUP_CODE, 1).callService(true)
        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun backupStatus(userStatus: String) {
        if(Alerts.isNetworkAvailable(this)){
        try {
            var postParam = JSONObject()

            postParam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
            postParam.put("status", userStatus)

            RetrofitService(
                this, this, WebUrls.SEND_BACKUP_STATUS, WebUrls.SEND_BACKUP_STATUS_CODE,
                3, postParam
            ).callService(true)

        } catch (e: Exception) {

            e.printStackTrace()
        }

    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun getArchiveStatus() {
        if(Alerts.isNetworkAvailable(this)){
        try {
            RetrofitService(
                this, this, WebUrls.ARCHIVE_STATUS +
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                , WebUrls.ARCHIVE_STATUS_CODE, 1
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {

                WebUrls.ARCHIVE_STATUS_CODE -> {

                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {

                        archive_status = obj.getString("message")

                        if (archive_status == "Archive") {
                            llArchiveChat.visibility = View.GONE
                            llUnArchiveChat.visibility = View.VISIBLE
                        } else {
                            llUnArchiveChat.visibility = View.GONE
                            llArchiveChat.visibility = View.VISIBLE
                        }

                        llchatSettings2.visibility = View.VISIBLE
                    } else {
                        llUnArchiveChat.visibility = View.GONE
                    }
                }

                WebUrls.UNARCHIVE_ALL_CHAT_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {

                        llUnArchiveChat.visibility = View.GONE
                        llArchiveChat.visibility = View.VISIBLE
                    }
                }


                WebUrls.ARCHIVE_ALL_CHAT_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {

                        llArchiveChat.visibility = View.GONE
                        llUnArchiveChat.visibility = View.VISIBLE
                    }
                }
                WebUrls.SEND_BACKUP_STATUS_CODE->{
                    val obj = JSONObject(response)
                    if(obj.getBoolean("response")){
                        if(obj.has("message")){
                         var message = obj.getJSONObject("message")
                            received_backup_status = message.getString("status")
                            if(received_backup_status == "yes"){
                                Common.customDialog(this,"Unilife","Chat backup turned on.")
                            }
                            else{
                                Common.customDialog(this,"Unilife","Chat backup turned off.")
                            }

                        }
                    }
                    else{
                        Common.customDialog(this,"Unilife","Please try again")
                    }

                }
                WebUrls.GET_BACKUP_CODE ->{
                    val obj = JSONObject(response)
                    if(obj.getBoolean("response")){
                        if(obj.has("message")){
                            var message = obj.getJSONObject("message")
                            get_backup_status = message.getString("status")
                            Log.e("sdfkldfnf",""+get_backup_status)
                            SwChangeMode.isChecked = get_backup_status == "yes"

                        }
                    }

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun setOnClickListner() {
        ivBackArrow.setOnClickListener(this)
        llblockeduser.setOnClickListener(this)
        llsavedMultimedia.setOnClickListener(this)
        llArchiveChat.setOnClickListener(this)
        llUnArchiveChat.setOnClickListener(this)
        llDeleteChat.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackArrow -> {
                onBackPressed()
            }

            R.id.llblockeduser -> {
                val intent = Intent(this, BlockedUser::class.java)
                startActivity(intent)
            }
            R.id.llsavedMultimedia -> {
                val intent = Intent(this, ViewChatMultimedia::class.java)
                startActivity(intent)

            }
            R.id.llArchiveChat -> {
                type = "archive"
                customDialog(this, "Unilife", "Last saved backup of chat will be available once archived.", type)

            }
            R.id.llUnArchiveChat -> {
                type = "unarchive"
                customDialog(this, "Unilife", "Last saved backup of chat will be available when once Unarchived.", type)

            }
            R.id.llDeleteChat -> {
                type = "delete"
                customDialog(this, "Unilife", "There will be no backup of chat available once deleted.", type)
            }

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun customDialog(context: Context, title: String, msg: String, type: String) {

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
            if (type == "archive") {
                archiveAllChat()

            } else if (type == "unarchive") {
                unarchiveAllChat()
            } else {
                deleteAllChat()
            }


            alertDialog.dismiss()
        }

        tvcancel.setOnClickListener {

            alertDialog.dismiss()
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    private fun unarchiveAllChat() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this,
            WebUrls.UNARCHIVE_ALL_CHAT + PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            , WebUrls.UNARCHIVE_ALL_CHAT_CODE, 1
        ).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun deleteAllChat() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this,
            WebUrls.DELETE_ALL_CHAT + PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            , WebUrls.DELETE_ALL_CHAT_CODE, 1
        ).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun archiveAllChat() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this,
            WebUrls.ARCHIVE_ALL_CHAT + PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            , WebUrls.ARCHIVE_ALL_CHAT_CODE, 1
        ).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }
}

