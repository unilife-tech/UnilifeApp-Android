package unilife.com.unilife.chat.individualChat

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_individual_chat_detail.*
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.delete_chat_alert.view.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.FullMediaScreen
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Common
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class IndividualChatDetail : AppDrawer(), View.OnClickListener, RetrofitResponse {
    private var receiverId: String = ""
    private var receiver_name: String = ""
    private var receiver_profile_image: String = ""
    private var roomId = ""
    private var alertDeleteChat: AlertDialog? = null
    private var alertArchiveChat: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_chat_detail)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivBack.visibility = View.VISIBLE
        ivProfileImg.visibility = View.GONE
        ivNoti.background = getDrawable(R.drawable.dot_line)
        ivNoti.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = "Chat Info"
        getIntentData()
        setClickCallbacks()
    }

    private fun getIntentData() {
        try {
            receiverId = intent.getStringExtra("receiverId")
            receiver_name = intent.getStringExtra("receiver_name")
            receiver_profile_image = intent.getStringExtra("receiver_profile_image")
            roomId = intent.getStringExtra("roomId")
            if (receiver_profile_image != "") {
                Picasso.with(this)
                    .load(WebUrls.PROFILE_IMAGE_URL + receiver_profile_image)
                    .placeholder(R.drawable.no_image).into(ivChatImage)
            } else {
                ivChatImage.setImageResource(R.drawable.no_image)
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
            R.id.llSingleViewSavedMedia -> {
                var intent = Intent(this, ViewSavedMultimedia::class.java)
                intent.putExtra("receiverId", receiverId)
                startActivity(intent)
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
            R.id.llDeleteSingleChat -> {
                showDeleteChatAlert()
            }
            R.id.llArchiveSingleChat -> {
                showArchiveChatAlert()
            }
            R.id.ivChatImage -> {
                startActivity(
                    Intent(this, FullMediaScreen::class.java)
                        .putExtra("file_type", "profile_image")
                        .putExtra("message_type", "profile_image")
                        .putExtra("message", receiver_profile_image)
                )
            }
        }
    }

    private fun setClickCallbacks() {
        try {
            ivBack.setOnClickListener(this)
            llSingleViewSavedMedia.setOnClickListener(this)
            rlChat.setOnClickListener(this)
            rlBlogs.setOnClickListener(this)
            rlBrands.setOnClickListener(this)
            rlEvent.setOnClickListener(this)
            llDeleteSingleChat.setOnClickListener(this)
            llArchiveSingleChat.setOnClickListener(this)
            ivChatImage.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showDeleteChatAlert() {
        try {
            var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
            val view =
                LayoutInflater.from(this).inflate(R.layout.delete_chat_alert, null)

            alertDialogBuilder.setView(view)

            alertDeleteChat = alertDialogBuilder.create()
            alertDeleteChat!!.setCancelable(false)
            alertDeleteChat!!.setCanceledOnTouchOutside(false)

            val tvYes = view.tvYes
            val tvNo = view.tvNo

            tvYes.setOnClickListener {
                alertDeleteChat!!.dismiss()
                callDeleteChatApi()
            }
            tvNo.setOnClickListener {
                alertDeleteChat!!.dismiss()
            }

            alertDeleteChat!!.window
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.WRAP_CONTENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertDeleteChat!!.window.setLayout(width, height)
            alertDeleteChat!!.window.setGravity(Gravity.CENTER)
            alertDeleteChat!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showArchiveChatAlert() {
        try {
            var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
            val view =
                LayoutInflater.from(this).inflate(R.layout.delete_chat_alert, null)

            alertDialogBuilder.setView(view)

            alertArchiveChat = alertDialogBuilder.create()
            alertArchiveChat!!.setCancelable(false)
            alertArchiveChat!!.setCanceledOnTouchOutside(false)

            val tvYes = view.tvYes
            val tvNo = view.tvNo
            val tvTitleIs = view.tvTitleIs
            val tvWarning = view.tvWarning

            tvTitleIs.text = getString(R.string.chat_archive_title)
            tvWarning.text = getString(R.string.chat_archive)

            tvYes.setOnClickListener {
                alertArchiveChat!!.dismiss()
                callArchiveChatApi()
            }
            tvNo.setOnClickListener {
                alertArchiveChat!!.dismiss()
            }

            alertArchiveChat!!.window
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.WRAP_CONTENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertArchiveChat!!.window.setLayout(width, height)
            alertArchiveChat!!.window.setGravity(Gravity.CENTER)
            alertArchiveChat!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callDeleteChatApi() {
        try {
            var jsonObject = JSONObject()
            jsonObject.put(
                "user_id",
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            )
            jsonObject.put("chat_room_id", roomId)

            RetrofitService(
                this,
                this,
                WebUrls.DELETE_CHAT,
                WebUrls.DELETE_CHAT_CODE,
                3,
                jsonObject
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callArchiveChatApi() {
        try {
            var jsonObject = JSONObject()
            jsonObject.put(
                "user_id",
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            )
            jsonObject.put("chat_room_id", roomId)
            RetrofitService(
                this,
                this,
                WebUrls.ARCHIVE_CHAT,
                WebUrls.ARCHIVE_CHAT_CODE,
                3,
                jsonObject
            ).callService(true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        super.onResponse(requestCode, response)

        when (requestCode) {
            WebUrls.DELETE_CHAT_CODE -> {
                try {
                    Log.e("resDelIndChat", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_LONG)
                            .show()
                        var intent = Intent(this, ChatListing::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.ARCHIVE_CHAT_CODE -> {
                try {
                    Log.e("resArchiveIndChat", response)

                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_LONG)
                            .show()
                        var intent = Intent(this, ChatListing::class.java)
                        startActivity(intent)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
