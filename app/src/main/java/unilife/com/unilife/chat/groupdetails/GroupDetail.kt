package unilife.com.unilife.chat.groupdetails

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.BuildConfig
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_friends_list.*
import kotlinx.android.synthetic.main.activity_group_info.*
import kotlinx.android.synthetic.main.delete_chat_alert.view.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.group_member_action_alert.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.FullMediaScreen
import unilife.com.unilife.chat.adapter.FriendDetailsAdapter
import unilife.com.unilife.chat.adapter.RequestListingAdapter
import unilife.com.unilife.chat.model.ExitGroupRequest
import unilife.com.unilife.chat.model.ItemFriendDetails
import unilife.com.unilife.chat.model.ReceivedRequestResponse
import unilife.com.unilife.chat.update.ChatDetailsActivity
import unilife.com.unilife.home.Home
import unilife.com.unilife.profile.model.responses.CommonResponse
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.utils.RecyclerItemClickListener
import java.io.File
import java.io.Serializable

class GroupDetail : AppDrawer(), View.OnClickListener, GroupParticipantsAdapter.GroupClicks,
    RetrofitResponse {

    private var groupParticipantsAdapter: GroupParticipantsAdapter? = null
    private var posGroupMember = -1
    private var alertDialog: AlertDialog? = null
    private var file1: File? = null
    private var bitmap: Bitmap? = null
    private var part: MultipartBody.Part? = null
    private var file: File? = null
    private var uri: Uri? = null
    private var alertDeleteChat: AlertDialog? = null
    private var alertArchiveChat: AlertDialog? = null
    private var alertRemoveMember: AlertDialog? = null
    private var alertExitGroup: AlertDialog? = null

    private var groupUserList = ArrayList<GroupUserModel>()

    private var groupId = ""
    private var groupName = ""
    private var groupImg = ""
    private var roomId = ""

    companion object {
        var isAdmin: Boolean = false
    }

    var title = arrayOf(
        "Media, Links, & Docs",
        "Mentions",
        "Starred Messages",
        "Apps and Integration"
    )

    var title2 = arrayOf(
        "Do not Disturb",
        "Delete Conversation",
        "Exit Group"
    )

    var image = intArrayOf(
        R.drawable.fdetails_1,
        R.drawable.fdetails_2,
        R.drawable.fdetails_3,
        R.drawable.fdetails_4
    )
    var image2 = intArrayOf(
        R.drawable.fdetails_5,
        R.drawable.fdetails_6,
        R.drawable.ic_exitgroup
    )
    var color = arrayOf(
        "#15be85",
        "#e53030",
        "#e2a731",
        "#006cb5"
    )
    var color2 = arrayOf(
        "#e818c5",
        "#db7b28",
        "#f24545"
    )

    var arrayListTop = java.util.ArrayList<ItemFriendDetails>()
    var arrayListBottom = java.util.ArrayList<ItemFriendDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_info)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        ivBack.visibility = View.VISIBLE
//        ivProfileImg.visibility = View.GONE
//        ivNoti.background = getDrawable(R.drawable.dot_line)
//        ivNoti.backgroundTintList =
//            ColorStateList.valueOf(resources.getColor(R.color.white))
//        ivNoti.visibility = View.GONE

//        llToolBar.background = resources.getDrawable(R.color.colorPrimary)
//        tvMainTitle.text = "Group Info"
        llToolBar.visibility = View.GONE
        initRecycler()
        getIntentData()
        setClickCallbacks()
        setGroupParticipantsAdapter()
    }

    fun initRecycler() {
//        recyclerViewTop.nested
//        recyclerViewBottom
//        rvGroupParticipants

        for (i in title.indices) {
            val itemFriendDetails = ItemFriendDetails()
            itemFriendDetails.title = title[i]
            itemFriendDetails.color = color[i]
            itemFriendDetails.image = image[i]
            arrayListTop.add(itemFriendDetails)
        }

        recyclerViewTop.setHasFixedSize(true)
        recyclerViewTop.layoutManager = LinearLayoutManager(this)
        recyclerViewTop.adapter = FriendDetailsAdapter(this, arrayListTop)

        for (i in title2.indices) {
            val itemFriendDetails = ItemFriendDetails()
            itemFriendDetails.title = title2[i]
            itemFriendDetails.color = color2[i]
            itemFriendDetails.image = image2[i]
            arrayListBottom.add(itemFriendDetails)
        }

        recyclerViewBottom.setHasFixedSize(true)
        recyclerViewBottom.layoutManager = LinearLayoutManager(this)
        recyclerViewBottom.adapter = FriendDetailsAdapter(this, arrayListBottom)

        recyclerViewTop.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerViewTop,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(
                        view: View?,
                        position: Int
                    ) {
                    }

                    override fun onItemClick(view: View, position: Int) {
                        when (position) {
                            0 -> {
                                var intent =
                                    Intent(this@GroupDetail, ViewSavedMultimedia::class.java)
                                intent.putExtra("groupId", groupId)
                                startActivity(intent)
                            }
                            2 -> {
                                showToast("Coming soon...")
                            }
                        }
                    }
                })
        )

        recyclerViewBottom.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerViewBottom,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onLongItemClick(
                        view: View?,
                        position: Int
                    ) {
                    }

                    override fun onItemClick(view: View, position: Int) {
                        when (position) {
                            0 -> {

                            }
                            1 -> {
                                showDeleteChatAlert()
                            }
                            2 -> {
                                showExitGroupAlert()
                            }
                        }
                    }
                })
        )
    }

    override fun onResume() {
        super.onResume()
        callGroupDetailsApi()
    }

    private fun getIntentData() {
        try {
            if (intent.hasExtra("groupId")) {
                groupId = intent.getStringExtra("groupId")
            }
            if (intent.hasExtra("groupName")) {
                groupName = intent.getStringExtra("groupName")
            }
            if (intent.hasExtra("roomId")) {
                roomId = intent.getStringExtra("roomId")
            }
            if (intent.hasExtra("groupImg")) {
                groupImg = intent.getStringExtra("groupImg")


                textGroupName.text = groupName
                if (groupImg != "") {
                    Picasso.with(this)
                        .load(WebUrls.PROFILE_IMAGE_URL + groupImg)
                        .placeholder(R.drawable.no_image).into(imgProfile)
                } else {
                    imgProfile.setImageResource(R.drawable.no_image)
                }
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
            R.id.llCamera -> {
                checkPermission()
            }
            R.id.llDeleteChat -> {
                showDeleteChatAlert()
            }
            R.id.llArchiveChat -> {
                showArchiveChatAlert()
            }
//            R.id.llViewSavedMedia -> {
//                var intent = Intent(this, ViewSavedMultimedia::class.java)
//                intent.putExtra("groupId", groupId)
//                startActivity(intent)
//            }
//            R.id.llViewBlockedUsers -> {
//                var intent = Intent(this, BlockedGroupUsers::class.java)
//                intent.putExtra("groupId", groupId)
//                startActivity(intent)
//            }
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
            R.id.txtAddParticipant -> {
                val intent = Intent(this, AddParticipant::class.java)
                intent.putExtra("groupMemberList", groupUserList)
                intent.putExtra("groupId", groupId)
                startActivity(intent)
            }
//            R.id.llRemoveParticipants -> {
//                val intent = Intent(this, RemoveParticipants::class.java)
//                intent.putExtra("groupMemberList", groupUserList)
//                intent.putExtra("groupId", groupId)
//                startActivity(intent)
//            }
//            R.id.cvExitGroup -> {
//                showExitGroupAlert()
//            }
            R.id.imgProfile -> {
                startActivity(
                    Intent(this, FullMediaScreen::class.java)
                        .putExtra("file_type", "profile_image")
                        .putExtra("message_type", "profile_image")
                        .putExtra("message", groupImg)
                )
            }
        }
    }

    private fun setClickCallbacks() {
        try {
//            ivProfileImg.setOnClickListener(this)
//            ivBack.setOnClickListener(this)
            llCamera.setOnClickListener(this)
//            llDeleteChat.setOnClickListener(this)
//            llArchiveChat.setOnClickListener(this)
//            llViewSavedMedia.setOnClickListener(this)
//            llViewBlockedUsers.setOnClickListener(this)
//            rlChat.setOnClickListener(this)
//            rlBlogs.setOnClickListener(this)
//            rlBrands.setOnClickListener(this)
//            rlEvent.setOnClickListener(this)
            txtAddParticipant.setOnClickListener(this)
//            llRemoveParticipants.setOnClickListener(this)
//            cvExitGroup.setOnClickListener(this)
//            imgProfile.setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setGroupParticipantsAdapter() {
        try {
            rvGroupParticipants.layoutManager = LinearLayoutManager(this)
            rvGroupParticipants.setHasFixedSize(true)
            groupParticipantsAdapter = GroupParticipantsAdapter(this)
            rvGroupParticipants.adapter = groupParticipantsAdapter
            groupParticipantsAdapter!!.initGroupClicks(this)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun clickGroupMember(position: Int) {
        try {
            posGroupMember = position
            showGroupMemberActionAlert()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun removeGroupMember(position: Int) {
        posGroupMember = position
        showRemoveMemberAlert()
    }

//    override fun removeGroupMember(position: Int) {
//        posGroupMember = position
//        removeGroupMember()
//    }

    @SuppressLint("SetTextI18n")
    private fun showGroupMemberActionAlert() {
        try {
            var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
            val view =
                LayoutInflater.from(this).inflate(R.layout.group_member_action_alert, null)

            alertDialogBuilder.setView(view)

            alertDialog = alertDialogBuilder.create()
            alertDialog!!.setCancelable(true)
            alertDialog!!.setCanceledOnTouchOutside(true)

            val llMessage = view.llMessage
            val llRemove = view.llRemove
            val tvMessage = view.tvMessage
            val tvRemove = view.tvRemove
            llRemove.visibility = View.GONE

            tvRemove.text = "Remove " + groupUserList[posGroupMember].username
            tvMessage.text = "Message " + groupUserList[posGroupMember].username

            if (!isAdmin) {
                llRemove.visibility = View.GONE
            }

            alertDialog!!.window
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.MATCH_PARENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertDialog!!.window.setLayout(width, height)
            alertDialog!!.window.setGravity(Gravity.CENTER)
            alertDialog!!.show()

            llMessage.setOnClickListener {
                alertDialog!!.dismiss()
                var intent = Intent(this, ChatDetailsActivity::class.java)
                intent.putExtra("receiver_id", groupUserList[posGroupMember].user_id)
                intent.putExtra("receiver_name", groupUserList[posGroupMember].username)
                intent.putExtra(
                    "receiver_profile_image",
                    groupUserList[posGroupMember].profile_image
                )
                startActivity(intent)
                finish()
            }
            llRemove.setOnClickListener {
                alertDialog!!.dismiss()
                showRemoveMemberAlert()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showRemoveMemberAlert() {
        try {
            var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
            val view =
                LayoutInflater.from(this).inflate(R.layout.remove_member_alert, null)

            alertDialogBuilder.setView(view)

            alertRemoveMember = alertDialogBuilder.create()
            alertRemoveMember!!.setCancelable(false)
            alertRemoveMember!!.setCanceledOnTouchOutside(false)

            val tvYes = view.tvYes
            val tvNo = view.tvNo

            tvYes.setOnClickListener {
                callRemoveParticipantApi()
            }
            tvNo.setOnClickListener {
                alertRemoveMember!!.dismiss()
            }

            alertRemoveMember!!.window
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.WRAP_CONTENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertRemoveMember!!.window.setLayout(width, height)
            alertRemoveMember!!.window.setGravity(Gravity.CENTER)
            alertRemoveMember!!.show()

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

    private fun showExitGroupAlert() {
        try {
            var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
            val view =
                LayoutInflater.from(this).inflate(R.layout.exit_group_alert, null)

            alertDialogBuilder.setView(view)

            alertExitGroup = alertDialogBuilder.create()
            alertExitGroup!!.setCancelable(false)
            alertExitGroup!!.setCanceledOnTouchOutside(false)

            val tvYes = view.tvYes
            val tvNo = view.tvNo

            tvYes.setOnClickListener {
                alertExitGroup!!.dismiss()
                callExitGroupApi()
            }
            tvNo.setOnClickListener {
                alertExitGroup!!.dismiss()
            }

            alertExitGroup!!.window
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.WRAP_CONTENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertExitGroup!!.window.setLayout(width, height)
            alertExitGroup!!.window.setGravity(Gravity.CENTER)
            alertExitGroup!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            val hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA)
            val hasReadExtPermission =
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasWriteExtPermission =
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            val permissionList = java.util.ArrayList<String>()

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA)
            }

            if (hasReadExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (hasWriteExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (permissionList.isNotEmpty()) {
                requestPermissions(permissionList.toTypedArray(), 2)
            } else {
                selectImg()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            2 ->
                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        selectImg()
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    }
                }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun selectImg() {

        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

        val builder = android.support.v7.app.AlertDialog.Builder(this)
        builder.setTitle("Choose Group Photo!")

        builder.setItems(options) { dialogInterface, i ->
            if (options[i] == "Take Photo") {
                launchCamera()

            }

            if (options[i] == "Choose from Gallery") {

                val galleryIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(galleryIntent, "Select Image from Gallery"),
                    111
                )

            } else if (options[i] == "Cancel") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        if (cameraIntent.resolveActivity(packageManager) != null) {
            try {
                val dir = File(Alerts.makeDirectory(this))
                file = File(dir, System.currentTimeMillis().toString() + "unilife.png")
                uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider", file!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(cameraIntent, 501)
                Log.e("sbsjcb", "$uri dnifnfoefe")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            501 -> if (resultCode == Activity.RESULT_OK) { //camera
                cropImage(uri)
            }

            111 -> if (resultCode == Activity.RESULT_OK) {      // gallery
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
                    setImageAfterCrop(resultUri.path)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    error.printStackTrace()
                }
            }
        }
    }

    /*crop image square*/
    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1).start(this)
    }

    private fun setImageAfterCrop(path: String?) {
        file1 = File(path)
        bitmap = BitmapFactory.decodeFile(file1!!.path)
        val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
        part = MultipartBody.Part.createFormData("group_image", file1!!.name, reqFile)
        callChangeGroupIconApi()
    }

    private fun callGroupDetailsApi() {
        try {
            RetrofitService(
                this,
                this,
                WebUrls.GROUP_DETAIL + groupId,
                WebUrls.GROUP_DETAIL_CODE,
                1
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callChangeGroupIconApi() {
        try {

            var hashMap: HashMap<String, RequestBody> = HashMap()
            hashMap["group_id"] =
                RequestBody.create(MediaType.parse("multipart/form-data"), groupId)

            RetrofitService(
                this,
                this,
                WebUrls.CHANGE_GROUP_ICON,
                WebUrls.CHANGE_GROUP_ICON_CODE,
                5,
                hashMap, part!!
            ).callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callRemoveParticipantApi() {
        try {
            var jsonObject = JSONObject()
            jsonObject.put("group_id", groupId)

            var jsonArray = JSONArray()
            jsonArray.put(groupUserList[posGroupMember].user_id)
            jsonObject.put("user_id", jsonArray)

            RetrofitService(
                this,
                this,
                WebUrls.REMOVE_PARTCIPANT,
                WebUrls.REMOVE_PARTCIPANT_CODE,
                3,
                jsonObject
            ).callService(true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    private fun callExitGroupApi() {
//        if (Alerts.isNetworkAvailable(this)) {
//            val postParam = JSONObject()
//
//            try {
//
//                postParam.put(
//                    "user_id",
//                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
//                )
//                postParam.put("group_id", groupId)
//
//
//                RetrofitService(
//                    this,
//                    this,
//                    WebUrls.UNJOIN_GROUP,
//                    WebUrls.UNJOIN_GROUP_REQ_CODE,
//                    3,
//                    postParam
//                ).callService(true)
//
//
//            } catch (ex: java.lang.Exception) {
//                ex.printStackTrace()
//            }
//
//        } else {
//            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
//        }
//    }

    fun callExitGroupApi() {
//        if (!isNetworkConnected()) return
//        ChatDetailRequest chatDetailRequest = new ChatDetailRequest();
//        chatDetailRequest.setReceiver_id(receiverId);
//        chatDetailRequest.setSender_id(senderId);
        val exitGroupRequest=ExitGroupRequest();
        exitGroupRequest.setGroup_id(groupId);
        exitGroupRequest.setRemove_user_id(PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID));

        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.exitGroup(
            PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID),exitGroupRequest)
        call.enqueue(object : Callback<CommonResponse?> {
            override fun onResponse(
                call: Call<CommonResponse?>,
                response: Response<CommonResponse?>
            ) {
//                hideProgressDialog()
                Log.e("response code -->", "" + response.code())
                if (response.isSuccessful) {
                    showToast(response.body()!!.message)
                    finish()
                }
            }

            override fun onFailure(
                call: Call<CommonResponse?>,
                t: Throwable
            ) {
//                hideProgressDialog()
                showToast(t.message.toString())
            }
        })
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
            WebUrls.CHANGE_GROUP_ICON_CODE -> {
                try {
                    Log.e("resChangeGroupIconIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        imgProfile.setImageBitmap(bitmap)
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_LONG)
                            .show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.GROUP_DETAIL_CODE -> {
                try {
                    Log.e("resGroupDetailsIs", response)
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        groupUserList.clear()
                        if (!jsonObject.isNull("data")) {
                            var data = jsonObject.getJSONObject("data")
                            if (!data.isNull("users_in_group")) {
                                var users_in_group = data.getJSONArray("users_in_group")
                                if (users_in_group.length() > 0) {
                                    groupUserList.clear()
                                    for (i in 0 until users_in_group.length()) {
                                        var user = users_in_group.getJSONObject(i)
                                        var groupUserModel = GroupUserModel()
                                        groupUserModel.id = user.getString("id")
                                        groupUserModel.user_id = user.getString("user_id")

                                        groupUserModel.group_id = user.getString("group_id")
                                        groupUserModel.group_admin = user.getString("group_admin")

                                        if (user.getString("user_id") == PreferenceFile.getInstance()
                                                .getPreferenceData(
                                                    this,
                                                    WebUrls.KEY_USERID
                                                ) && user.getString("group_admin") == "yes"
                                        ) {
                                                isAdmin = true
//                                                llRemoveParticipants.visibility = View.VISIBLE
                                                txtAddParticipant.visibility = View.VISIBLE

                                        }
                                        /* if (!isAdmin) {
                                             llRemoveParticipants.visibility = View.GONE
                                             txtAddParticipant.visibility = View.GONE
                                         } else {
                                             llRemoveParticipants.visibility = View.VISIBLE
                                             txtAddParticipant.visibility = View.VISIBLE
                                         }*/
                                        groupUserModel.created_at = user.getString("created_at")
                                        groupUserModel.updated_at = user.getString("updated_at")
                                        if (!user.isNull("group_user_detail")) {


                                            var group_user_detail =
                                                user.getJSONObject("group_user_detail")
                                            groupUserModel.user_type =
                                                group_user_detail.getString("user_type")
                                            groupUserModel.is_online =
                                                group_user_detail.getString("is_online")
                                            groupUserModel.username =
                                                group_user_detail.getString("username")
                                            groupUserModel.profile_image =
                                                group_user_detail.getString("profile_image")
                                            groupUserModel.profile_status =
                                                group_user_detail.getString("profile_status")
                                            groupUserModel.status =
                                                group_user_detail.getString("status")
                                        }
                                        groupUserList.add(groupUserModel)
                                    }
                                    groupParticipantsAdapter!!.upDateData(groupUserList)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.REMOVE_PARTCIPANT_CODE -> {
                try {
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        Alerts.alertDialog(this, jsonObject.getString("message"))
                        alertRemoveMember!!.dismiss()
                        groupUserList.removeAt(posGroupMember)
                        groupParticipantsAdapter!!.notifyItemRemoved(posGroupMember)
                        groupParticipantsAdapter!!.notifyItemRangeChanged(
                            posGroupMember,
                            groupUserList.size
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.UNJOIN_GROUP_REQ_CODE -> {
                try {
                    var jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("response")) {
                        Alerts.alertDialog(this, jsonObject.getString("message"))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.DELETE_CHAT_CODE -> {
                try {
                    Log.e("resDelGroupChat", response)

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
                    Log.e("resArchiveGroupChat", response)

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

    data class GroupUserModel(
        var id: String = "",
        var user_id: String = "",
        var group_id: String = "",
        var group_admin: String = "",
        var created_at: String = "",
        var updated_at: String = "",
        var user_type: String = "",
        var is_online: String = "",
        var username: String = "",
        var profile_image: String = "",
        var profile_status: String = "",
        var status: String = "",
        var check_req_status: String = "0"
    ) : Serializable

    fun goBack(view: View) {
        finish()
    }
}
