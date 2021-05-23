package unilife.com.unilife.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_create_group.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import unilife.com.unilife.BuildConfig
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.chat.groupdetails.AddParticipant
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.io.File

class CreateGroup : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    val TAG = CreateGroup::class.java.simpleName

    var friendsList: ArrayList<AddParticipant.ShowFriendListModel> = ArrayList()
    var membersList: ArrayList<String> = ArrayList()

    var groupUsersListAdapter: GroupUsersListAdapter? = null

    internal var tvCancel: TextView? = null
    internal var tvGalleryImage: TextView? = null
    internal var tvCameraImage: TextView? = null
    internal var tvVideoCamera: TextView? = null
    internal var tvUploadVideo: TextView? = null

    private var alertDialog: AlertDialog? = null

    private var uri: Uri? = null
    private var file: File? = null
    private var file1: File? = null
    var selectedMediaPaths: ArrayList<String>? = ArrayList()
    private var part: MultipartBody.Part? = null

    var groupId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        tvTitle.text = "Create Group"

        ivNotification.visibility = GONE

        ivNotification.background = getDrawable(R.drawable.dot_line)

        ivNotification.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white))

//        showFriendsList(true)
        callFriendListApi(true)

        setOnclikListner()

        applyFilter()

    }


    private fun applyFilter() {

        etSearch1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (groupUsersListAdapter != null) {
                    groupUsersListAdapter!!.filterResult(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    fun setOnclikListner() {
        tvFriend_list.setOnClickListener(this)
        tvCreateGrp.setOnClickListener(this)
        ivGroupImg.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)
    }

    private fun createGroup() {
        if (Alerts.isNetworkAvailable(this)) {
            try {

                if (file1 != null) {

                    var mmap = HashMap<String, RequestBody>()

                    mmap["user_id"] = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                    )
                    mmap["group_name"] = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        etGroup.text.toString().trim()
                    )
                    mmap["request_id"] = RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        membersList.toString()
                    )


                    RetrofitService(
                        this,
                        this,
                        WebUrls.SEND_MULTI_USER_FRIEND_REQUEST,
                        WebUrls.SEND_MULTI_USER_FRIEND_REQUEST_CODE,
                        2,
                        mmap,
                        part!!
                    ).callService(true)

                } else {

                    /*        val postParam = JSONObject()
                            postParam.put(
                                "user_id",
                                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                            )
                            postParam.put("group_name", etGroup.text.toString().trim())
                            postParam.put(
                                "group_member",
                                membersList.toString().replace("[", "").replace("]", "")
                            )

                            Log.e(TAG, postParam.toString())

                            RetrofitService(
                                this,
                                this,
                                WebUrls.CREATE_GROUP,
                                WebUrls.CREATE_GROUP_REQ_CODE,
                                3,
                                postParam
                            ).callService(true)*/


                    var jsonObject = JSONObject()
                    jsonObject.put(
                        "user_id", PreferenceFile.getInstance().getPreferenceData(
                            this, WebUrls.KEY_USERID
                        )
                    )

                    jsonObject.put("group_id", "")
                    jsonObject.put("group_name", etGroup.text.toString().trim())
                    jsonObject.put("request_id", membersList.toString())

                    Log.e("create group request",jsonObject.toString())

                    RetrofitService(
                        this,
                        this,
                        WebUrls.SEND_MULTI_USER_FRIEND_REQUEST,
                        WebUrls.SEND_MULTI_USER_FRIEND_REQUEST_CODE,
                        3,
                        jsonObject
                    ).callService(true)


                }

//new code
                try {


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    fun createRoom(groupId: String) {
        if (Alerts.isNetworkAvailable(this)) {
            try {

                var jsonObject = JSONObject()



                jsonObject.put(
                    "sender_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                jsonObject.put("group_id", groupId)


                RetrofitService(
                    this,
                    this,
                    WebUrls.CREATE_ROOM_FOR_GROUP,
                    WebUrls.CREATE_ROOM_FOR_GROUP_REQ_CODE,
                    3,
                    jsonObject
                ).callService(true)


                Log.e("createRoom", jsonObject.toString())


            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun showFriendsList(flag: Boolean) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this, this, WebUrls.SHOW_FRIENDS_LISTING
                            + PreferenceFile.getInstance().getPreferenceData(
                        this, WebUrls.KEY_USERID
                    ),
                    WebUrls.SHOW_FRIENDS_LISTING_CODE, 1, postParam
                ).callService(flag)


            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
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


    private fun setFriendsAdapter(
        friendsList: ArrayList<AddParticipant.ShowFriendListModel>,
        key: String
    ) {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvCreateGrouplist?.layoutManager = layoutManager

        groupUsersListAdapter = GroupUsersListAdapter(this, friendsList, "group")
        rvCreateGrouplist?.adapter = groupUsersListAdapter

        groupUsersListAdapter!!.setOnItemClickListener(object :
            GroupUsersListAdapter.onItemClickListener {
            override fun onItemClick(
                position: Int,
                friendsList: ArrayList<AddParticipant.ShowFriendListModel>?
            ) {

            }

            override fun onPerformAction(
                position: Int,
                status: String,
                friendsList: ArrayList<AddParticipant.ShowFriendListModel>?
            ) {
                // 0 >> send request, 1 >> cancel Req
                if (friendsList!!.get(position).check_req_status.equals("0")) { //adding
                    friendsList!!.get(position).check_req_status = "1" // becomes selected
                    membersList.add(friendsList!!.get(position).id)
                } else if (friendsList!!.get(position).check_req_status.equals("1")) { //selected one
                    friendsList!!.get(position).check_req_status = "0" // becomes unselected
                    membersList.remove(friendsList!!.get(position).id)
                }

                groupUsersListAdapter!!.notifyDataSetChanged()
                Log.e(TAG, "MEMEBERS_LIST:$membersList")
            }
        })

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivBackArrow -> {
                onBackPressed()
            }

            R.id.tvFriend_list -> {
                val intent = Intent(this, ChatUsersList::class.java)
                intent.putExtra("key", "view_friends")
                startActivity(intent)
            }

            R.id.tvCreateGrp -> {
                if (etGroup.text.toString().trim().isEmpty()) {
                    Common.customDialog(this, "Unilife", "Please enter group name.")
                } else {
                    createGroup()
                }
            }

            R.id.ivGroupImg -> {
                uploadAttachment()
            }

            R.id.ivNotification -> {
//                val popupwindow_obj = showPopUpMenu()
//                popupwindow_obj.showAsDropDown(ivNoti)
            }
        }
    }

    fun uploadAttachment() {

        var alertDialogBuilder = AlertDialog.Builder(this@CreateGroup, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this@CreateGroup).inflate(R.layout.upload_attachment, null)
        tvUploadVideo = view.findViewById(R.id.tvUploadVideo)
        tvCameraImage = view.findViewById(R.id.tvCameraImage)
        tvGalleryImage = view.findViewById(R.id.tvGalleryImage)
        tvVideoCamera = view.findViewById(R.id.tvVideoCamera)
        tvCancel = view.findViewById(R.id.tvCancel)

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)

        tvUploadVideo!!.visibility = GONE
        tvVideoCamera!!.visibility = GONE

        this.tvCameraImage!!.setOnClickListener(View.OnClickListener {
            launchCamera()
            alertDialog!!.dismiss()

        })

        this.tvGalleryImage!!.setOnClickListener(View.OnClickListener {

            chooseImages()

            alertDialog!!.dismiss()
        })

        this.tvUploadVideo!!.setOnClickListener(View.OnClickListener {
            alertDialog!!.dismiss()

        })

        this.tvCancel!!.setOnClickListener(View.OnClickListener {
            alertDialog!!.dismiss()
        })


        alertDialog!!.window
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.85
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog!!.window.setLayout(width.toInt(), height)
        alertDialog!!.window.setGravity(Gravity.BOTTOM)
        alertDialog!!.show()

    }

    fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            try {
                val dir = File(Common.makeDirectory(this))
                file = File(dir, System.currentTimeMillis().toString() + "Unilife.png")
                uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider", file!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(cameraIntent, 501)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun chooseImages() {
        UwMediaPicker
            .with(this)                        // Activity or Fragment
            .setRequestCode(666)                // Give request code, default is 0
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery or VideoGallery, default is ImageGallery
            .setGridColumnCount(2)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is llight status bar enable, default is true
            .enableImageCompression(true)                // Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)                // Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)                // Compressed image's max height px, default is 720
            .setCompressFormat(Bitmap.CompressFormat.JPEG)        // Compressed image's format, default is JPEG
            .setCompressionQuality(85)                // Image compression quality, default is 85
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this@CreateGroup))    // Compressed image file's destination path, default is Pictures Dir
            .open()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {

            555, 666 -> if (resultCode == Activity.RESULT_OK) {

                selectedMediaPaths!!.clear()
                selectedMediaPaths!!.addAll(data!!.getStringArrayExtra(UwMediaPicker.UwMediaPickerResultKey))

                if (selectedMediaPaths!!.isNotEmpty()) {
                    file1 = File(selectedMediaPaths.toString().replace("[", "").replace("]", ""))
                    if (file1 != null) {
                        val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
                        part =
                            MultipartBody.Part.createFormData("group_image", file1!!.name, reqFile)
                        Log.e("videouri", "file1::" + file1!!.length() + "")
                    }

                    var bmOptions = BitmapFactory.Options()
                    var bitmap = BitmapFactory.decodeFile(file1!!.path, bmOptions);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                    ivGroupImg.setImageBitmap(bitmap)
                }
            }

            501 -> if (resultCode == Activity.RESULT_OK) {
                cropImage(uri)
            }


            111 -> if (resultCode == Activity.RESULT_OK) {
                val uri = data!!.data
                try {
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(uri!!, filePath, null, null, null)
                    if (cursor == null) {
                        val path = uri.path
                        file = File(path)
                    } else {
                        cursor.moveToFirst()
                        val colIndex = cursor.getColumnIndex(filePath[0])
                        val path = cursor.getString(colIndex)
                        file = File(path)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                cropImage(data.data)

            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result.uri
                    setImageafterCrop(resultUri.path)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    error.printStackTrace()
                }
            }

        }
    }

    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1).start(this)
    }

    private fun setImageafterCrop(gettingPath: String?) {

        try {

            val bitmap: Bitmap = BitmapFactory.decodeFile(gettingPath)

            ivGroupImg.setImageBitmap(bitmap)

            Log.e(TAG, " gettingPath:" + gettingPath)

            file1 = File(gettingPath.toString())

            if (file1 != null) {
                val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
                part = MultipartBody.Part.createFormData("group_image", file1!!.name, reqFile)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {

        when (requestCode) {

            WebUrls.SHOW_FRIEND_USER_CODE -> {

                val obj = JSONObject(response)
                Log.e("SHOW_FRIEND_USER_CODE", response)
                friendsList.clear()

                if (obj.getBoolean("response")) {

                    val data = obj.getJSONArray("data")

                    if (data.length() > 0) {

                        for (i in 0 until data.length()) {

                            val dataObj = data.getJSONObject(i)

                            val showFriendListModel = AddParticipant.ShowFriendListModel()

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
                        setFriendsAdapter(friendsList, "group")
                    }
                } else {
                    Common.customDialog(this, "Unilife", obj.getString("message"))
                }
            }

            WebUrls.CREATE_GROUP_REQ_CODE -> {

                val obj = JSONObject(response)

                Log.e(TAG, "CREATE_GROUP_REQ_CODE:" + obj.toString())

                if (obj.getBoolean("response")) {

                    var data = obj.getJSONObject("data")

                    groupId = data.getString("id")

                    createRoom(groupId)


                } else {
                    Common.customDialog(this, "Unilife", obj.getString("message"))
                }

            }

            WebUrls.CREATE_ROOM_FOR_GROUP_REQ_CODE -> {

                try {

                    var jsonObject = JSONObject(response)

                    Log.e("CREATE_ROOM_FOR_GROUP", jsonObject.toString())

                    if (jsonObject.getString("response").equals("1")) {

                        etGroup.setText("")
                        customDialog(this, "Unilife", "Group created successfully")

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            WebUrls.SHOW_FRIENDS_LISTING_CODE -> {

                val obj = JSONObject(response)

//                friendsList.clear()

                Log.e(TAG, "SHOW_FRIENDS_LISTING_CODE:" + obj.toString())

                if (obj.getBoolean("response")) {

                    val data = obj.getJSONArray("data")

                    if (data.length() > 0) {

                        for (i in 0 until data.length()) {

                            val dataObj = data.getJSONObject(i)

                            val chatUserDetailsModel = ChatUserDetailsModel()

                            chatUserDetailsModel.id = dataObj.getString("id")
                            chatUserDetailsModel.user_id = dataObj.getString("user_id")
                            chatUserDetailsModel.friend_id = dataObj.getString("friend_id")

                            chatUserDetailsModel.check_req_status = "0"

                            if (dataObj.has("status")) {
                                chatUserDetailsModel.status = dataObj.getString("status")
                            } else {
                                chatUserDetailsModel.status = ""
                            }

                            val userfriendObj = dataObj.getJSONObject("userfriend")

                            chatUserDetailsModel.friend_username =
                                userfriendObj.getString("username")
                            chatUserDetailsModel.friend_profile_image =
                                userfriendObj.getString("profile_image")

//                            friendsList.add(chatUserDetailsModel)
                        }

                        Log.e(TAG, "friendsList:" + friendsList.size)

                        setFriendsAdapter(friendsList, "group")

                    }
                } else {
//                        {"response":false,"message":"no friend is found"}
                    Common.customDialog(this, "Unilife", obj.getString("message"))
                }
            }

            WebUrls.SEND_MULTI_USER_FRIEND_REQUEST_CODE -> {
                try {
                    Log.e("resCreateGroupIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        if (jsonObject.getString("message") == "Friend requests send successfully") {
                            super.onBackPressed()
                            Toast.makeText(
                                this,
                                "Group Created and invitations sent successfully.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }

    }

    fun customDialog(context: Context, title: String, msg: String) {

        val dialogBuilder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        val tvTitle = dialogView.tvTitle
        val tvOk = dialogView.tvok
        val tvMsg = dialogView.tvMsg

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        tvTitle.text = title
        tvMsg.text = msg

        tvOk.setOnClickListener(View.OnClickListener {

            onBackPressed()
            finish()
            alertDialog.dismiss()
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

}
