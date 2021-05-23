package unilife.com.unilife.chat.savedMultimedia

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.delete_chat_alert.view.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.images_list_layout.*
import org.json.JSONObject
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.FullMediaScreen
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
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

class ImagesList : AppDrawer(), ImagesListAdapter.OnClickChange, View.OnClickListener,
    RetrofitResponse {
    var imagesList = ArrayList<ViewSavedMultimedia.ChatMultiMedia>()
    var imagesListAdapter: ImagesListAdapter? = null
    private var selectedMedia = ArrayList<String>()
    private var alertDeleteChat: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.images_list_layout)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivBack.visibility = View.VISIBLE
        ivBack.setOnClickListener {
            super.onBackPressed()
        }
        ivProfileImg.visibility = View.GONE
        ivNoti.visibility = View.GONE
        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
        tvMainTitle.text = getString(R.string.Images)
        selectedMedia.clear()
        getIntentData()
        setClickCallbacks()
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("imagesList")) {
                var auList =
                    intent.getSerializableExtra("imagesList") as ArrayList<ViewSavedMultimedia.ChatMultiMedia>
                imagesList.addAll(auList)
            }

            if (imagesList.size==0)
            {
                showToast("Images not shared yet")
            }

            setAudioListAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llDelete -> {
                if (selectedMedia.size == 0) {
                    Common.customDialog(this, "Unilife", "Please select atleast one item to delete")
                } else {
                    showDeleteChatAlert()
                }
            }

            R.id.llMove -> {
                try {
                    if (Alerts.isNetworkAvailable(this)) {
                        for (i in 0 until selectedMedia.size) {
                            val names: List<String> =
                                object : AbstractList<String>() {
                                    override fun get(location: Int): String? {
                                        return imagesList[location].id
                                    }

                                    override val size: Int
                                        get() = imagesList.size
                                }


                            for (i in 0 until selectedMedia.size) {
                                if (names.contains(selectedMedia[i])) {
                                    var indexIs = names.indexOf(selectedMedia[i])

                                    if (i == selectedMedia.size - 1) {
                                        DownloadTask(
                                            this,
                                            WebUrls.CHAT_MEDIA_URL + imagesList[indexIs].message,
                                            true
                                        )
                                    } else {
                                        DownloadTask(
                                            this,
                                            WebUrls.CHAT_MEDIA_URL + imagesList[indexIs].message,
                                            false
                                        )
                                    }

                                }
                            }
                        }
                    } else {
                        Common.customDialog(
                            this,
                            "Unilife",
                            resources.getString(R.string.internet_offline)
                        )
                    }
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
            val tvTitleIs = view.tvTitleIs
            val tvWarning = view.tvWarning
            tvTitleIs.text = "Delete Media"
            tvWarning.text = "There be no backup of media available once deleted"

            tvYes.setOnClickListener {
                alertDeleteChat!!.dismiss()
                callDeleteItemService()
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

    private fun setClickCallbacks() {
        try {
            rlChat.setOnClickListener(this)
            rlBlogs.setOnClickListener(this)
            rlBrands.setOnClickListener(this)
            rlEvent.setOnClickListener(this)
            llDelete.setOnClickListener(this)
            llMove.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setAudioListAdapter() {
        try {
            imagesListAdapter = ImagesListAdapter(this, imagesList)
            rvImages.adapter = imagesListAdapter
            imagesListAdapter!!.initOnClickChange(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun checkChange(position: Int, isChecked: Boolean) {
        try {
            imagesList[position].isSelected = isChecked
            if (isChecked) {
                if (!selectedMedia.contains(imagesList[position].id)) {
                    selectedMedia.add(imagesList[position].id)
                }
            } else {
                if (selectedMedia.contains(imagesList[position].id)) {
                    var indexIs = selectedMedia.indexOf(imagesList[position].id)
                    selectedMedia.removeAt(indexIs)
                }
//                cbSelectAll.isChecked = false
            }
            if (selectedMedia.size == 0) {
                llDelete.visibility = View.GONE
                llMove.visibility = View.GONE
            } else {
                llDelete.visibility = View.VISIBLE
                llMove.visibility = View.VISIBLE
            }
            imagesListAdapter!!.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClickItem(position: Int) {
        try {
            startActivity(
                Intent(this, FullMediaScreen::class.java)
                    .putExtra("file_type", imagesList[position].message_type)
                    .putExtra("message_type", "media")
                    .putExtra("message", imagesList[position].message)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun callDeleteItemService() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                var postParam = JSONObject()
                postParam.put(
                    "sender_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("media", selectedMedia.toString())

                RetrofitService(
                    this, this, WebUrls.DELETE_MULTIMEDIA, WebUrls.DELETE_MULTIMEDIA_CODE,
                    3, postParam
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
            WebUrls.DELETE_MULTIMEDIA_CODE -> {
                try {
                    Log.e("resDeleteMediaIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        Common.customDialog(this, "Unilife", "Media Deleted Successfully")
                        val names: List<String> =
                            object : AbstractList<String>() {
                                override fun get(location: Int): String? {
                                    return imagesList[location].id
                                }

                                override val size: Int
                                    get() = imagesList.size
                            }


                        for (i in 0 until selectedMedia.size) {
                            if (names.contains(selectedMedia[i])) {
                                var indexIs = names.indexOf(selectedMedia[i])
                                imagesList.removeAt(indexIs)
                                imagesListAdapter!!.notifyItemRemoved(indexIs)
                                imagesListAdapter!!.notifyItemRangeChanged(indexIs, imagesList.size)
                            }
                        }
                        selectedMedia.clear()

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}
