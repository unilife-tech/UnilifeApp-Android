package unilife.com.unilife.chat.groupdetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.view_saved_multimedia_layout.*
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.savedMultimedia.AudioList
import unilife.com.unilife.chat.savedMultimedia.DocsList
import unilife.com.unilife.chat.savedMultimedia.ImagesList
import unilife.com.unilife.chat.savedMultimedia.VideosList
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.io.Serializable

class ViewSavedMultimedia : AppDrawer(), RetrofitResponse, View.OnClickListener {
    private var groupId = ""
    private var receiverId = ""

    private var imagesList = ArrayList<ChatMultiMedia>()
    private var videosList = ArrayList<ChatMultiMedia>()
    private var audiosList = ArrayList<ChatMultiMedia>()
    private var docsList = ArrayList<ChatMultiMedia>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_saved_multimedia_layout)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivBack.visibility = View.VISIBLE
        ivBack.setOnClickListener {
            super.onBackPressed()
        }
        ivProfileImg.visibility = View.GONE
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = getString(R.string.ViewSavedMultiMediia)
        getIntentData()
        setClickCallbacks()
    }

    override fun onResume() {
        super.onResume()
        if (groupId.isNotEmpty()) {
            callViewMultiMediaApi(groupId, "group")
        } else {
            callViewMultiMediaApi(PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID), "single")
        }
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("groupId")) {
                groupId = intent.getStringExtra("groupId")

                callViewMultiMediaApi(groupId, "group")
            } else {
                receiverId = intent.getStringExtra("receiverId")
                callViewMultiMediaApi(PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID), "single")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llAudio -> {
                try {
                    var intent = Intent(this, AudioList::class.java)
                    intent.putExtra("audioList", audiosList)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.llVideo -> {
                try {
                    var intent = Intent(this, VideosList::class.java)
                    intent.putExtra("videosList", videosList)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.llImage -> {
                try {
                    var intent = Intent(this, ImagesList::class.java)
                    intent.putExtra("imagesList", imagesList)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            R.id.llDocuments -> {
                try {
                    var intent = Intent(this, DocsList::class.java)
                    intent.putExtra("docsList", docsList)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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

        }
    }

    private fun setClickCallbacks() {
        try {
            rlChat.setOnClickListener(this)
            rlBlogs.setOnClickListener(this)
            rlBrands.setOnClickListener(this)
            rlEvent.setOnClickListener(this)
            llAudio.setOnClickListener(this)
            llVideo.setOnClickListener(this)
            llImage.setOnClickListener(this)
            llDocuments.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callViewMultiMediaApi(userId: String, type: String) {
        try {
            var jsonObject = JSONObject()
            jsonObject.put("user_id", userId)
            jsonObject.put("type", type)

            RetrofitService(
                this,
                this,
                WebUrls.VIEW_MULTIMEDIA_CHAT,
                WebUrls.VIEW_MULTIMEDIA_CHAT_CODE,
                3,
                jsonObject
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        when (requestCode) {
            WebUrls.VIEW_MULTIMEDIA_CHAT_CODE -> {
                try {
                    Log.e("resMultiMediaIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        imagesList.clear()
                        videosList.clear()
                        audiosList.clear()
                        docsList.clear()
                        if (!jsonObject.isNull("data")) {
                            var dataArray = jsonObject.getJSONArray("data")
                            if (dataArray.length() > 0) {
                                for (i in 0 until dataArray.length()) {
                                    var data = dataArray.getJSONObject(i)
                                    var chatMultiMedia = ChatMultiMedia()
                                    chatMultiMedia.id = data.getString("id")
                                    chatMultiMedia.room_id = data.getString("room_id")
                                    chatMultiMedia.message = data.getString("message")
                                    chatMultiMedia.thumb = data.getString("thumb")
                                    chatMultiMedia.filepath = data.getString("filepath")
                                    chatMultiMedia.sender_id = data.getString("sender_id")
                                    chatMultiMedia.receiver_id = data.getString("receiver_id")
                                    chatMultiMedia.group_id = data.getString("group_id")
                                    chatMultiMedia.chat_id = data.getString("chat_id")
                                    chatMultiMedia.date = data.getString("date")
                                    chatMultiMedia.seen = data.getString("seen")
                                    chatMultiMedia.is_deleted = data.getString("is_deleted")
                                    chatMultiMedia.delete_chat_id = data.getString("delete_chat_id")
                                    chatMultiMedia.message_type = data.getString("message_type")
                                    chatMultiMedia.only_date = data.getString("only_date")
                                    chatMultiMedia.created_at = data.getString("created_at")
                                    chatMultiMedia.updated_at = data.getString("updated_at")

                                    when (data.getString("message_type")) {
                                        "image" -> {
                                            imagesList.add(chatMultiMedia)
                                        }
                                        "video" -> {
                                            videosList.add(chatMultiMedia)
                                        }
                                        "audio" -> {
                                            audiosList.add(chatMultiMedia)
                                        }
                                        "document" -> {
                                            docsList.add(chatMultiMedia)
                                        }
                                    }

                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    data class ChatMultiMedia(
        var id: String = "",
        var room_id: String = "",
        var message: String = "",
        var thumb: String = "",
        var filepath: String = "",
        var sender_id: String = "",
        var receiver_id: String = "",
        var group_id: String = "",
        var chat_id: String = "",
        var date: String = "",
        var seen: String = "",
        var is_deleted: String = "",
        var delete_chat_id: String = "",
        var message_type: String = "",
        var only_date: String = "",
        var created_at: String = "",
        var updated_at: String = "",
        var isSelected: Boolean = false
    ) : Serializable
}
