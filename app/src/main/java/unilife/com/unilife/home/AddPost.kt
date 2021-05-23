package unilife.com.unilife.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner
import io.apptik.widget.multiselectspinner.MultiSelectSpinner
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*

import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.BuildConfig
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.model.InfoModel
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.utils.CustomSpinnerAdapter
import unilife.com.unilife.location.PlacesAutoCompleteAdapter
import unilife.com.unilife.model.CustomMediaModel
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddPost : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var MY_PERMISSIONS_REQUEST_CAMERA = 1
    var value = 0
    var caption = ""
    var addPostThrugrp = "no"
    var attachment_type = ""
    private var uri: Uri? = null
    private var file: File? = null
    private var file1: File? = null
    private var part: MultipartBody.Part? = null
    var bitmapThumb: Bitmap? = null
    var lattitude: Double = 0.0
    var longitude: Double = 0.0
    var bitmapImagePath = ""

    lateinit var placesAutoCompleteAdapter: PlacesAutoCompleteAdapter

    lateinit var etLocation: AutoCompleteTextView
    internal var tvUploadFile: TextView? = null
    internal var tvCancel: TextView? = null
    internal var tvGalleryImage: TextView? = null
    internal var tvCameraImage: TextView? = null
    internal var tvUploadVideo: TextView? = null
    internal var tvVideoCamera: TextView? = null
    private val VIDEO_CAPTURE = 101

    internal var spTagPeople: MultiSelectSpinner? = null
    internal var spSelectGroup: MultiSelectSpinner? = null
    internal var rView: RecyclerView? = null
    internal var ivSwitchButton: Switch? = null
    internal var rltag: RelativeLayout? = null
    internal var rlGrpInVis: RelativeLayout? = null
    internal var rlGrpVis: RelativeLayout? = null

    private var customAdapter: CustomSpinnerAdapter? = null

    var postAttachmentIds = ArrayList<Int>()
    var tagPeopleList: ArrayList<InfoModel> = ArrayList()
    var tagPeopleListString: ArrayList<String> = ArrayList()
    var tagPeopleResult = ArrayList<String>()

    var groupList: ArrayList<InfoModel> = ArrayList()
    var groupListString: ArrayList<String> = ArrayList()
    var groupListResult = ArrayList<String>()

    val TAG = AddPost::class.java.simpleName
    private var alertDialog: AlertDialog? = null


    private lateinit var addPostMediaAdapter: AddPostMediaAdapter
    var multipartList: ArrayList<MultipartBody.Part>? = ArrayList()
    var customMediaList: ArrayList<CustomMediaModel>? = ArrayList()
    var fileList: ArrayList<File>? = ArrayList()
    var selectedMediaPaths: ArrayList<String>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        findIds()

        tvPost.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        setAdapter()

        setMediaAdapter()

        callTagPeopleService()

        ivSwitchButton!!.setOnCheckedChangeListener { buttonView, isChecked ->

        if(isChecked){
            addPostThrugrp = "yes"
        }else{
            addPostThrugrp ="no"
        }

        }

        setOnClickListner()

        ivNotification.visibility = View.GONE
    }

    private fun findIds() {
        etLocation = findViewById(R.id.etLocation)
        spTagPeople = findViewById(R.id.spTagPeople)
        spSelectGroup = findViewById(R.id.spSelectGroup)
        rView = findViewById(R.id.rView)
        ivSwitchButton = findViewById(R.id.ivSwitchButton)
        rltag = findViewById(R.id.rltag)
        rlGrpInVis = findViewById(R.id.rlGrpInVis)
        rlGrpVis = findViewById(R.id.rlGrpVis)

        tvTitle.text="Add Post"

    }

    fun setOnClickListner() {
        rlChat.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)
        tvPostFinal.setOnClickListener(this)
        rlGrpInVis!!.setOnClickListener(this)
        rlGrpInVis!!.setOnClickListener(this)
        rltag!!.setOnClickListener(this)
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

    @SuppressLint("ByteOrderMark")
    private fun launchVideoCamera() {
        val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        try {
            val dir = File(Common.makeDirectory(this))
            file = File(dir, System.currentTimeMillis().toString() + "Unilife.mp4")
            uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider", file!!
            )
            videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(videoIntent, VIDEO_CAPTURE)

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(2, 1).start(this)
    }

    private fun setImageafterCrop(gettingPath: String?) {
        try {
            val bmpOptions = BitmapFactory.Options()
            bmpOptions.inSampleSize = 1
            bmpOptions.inJustDecodeBounds = false
            var bitmap: Bitmap = BitmapFactory.decodeFile(gettingPath)

            attachment_type = "image"

            val customMediaModel = CustomMediaModel()
            customMediaModel.type = "image"
            customMediaModel.status = "1"
            customMediaModel.bitmap = bitmap
            customMediaModel.path = gettingPath.toString()

            customMediaList!![value] = customMediaModel

            Log.e(TAG, gettingPath + " setImageAfterCrop:" + customMediaList!!.size + " gettingPath:" + gettingPath)

//         file1 = Common.makeFile(bitmap, gettingPath.toString())
            file1 = File(gettingPath.toString())

            if (file1 != null) {
                val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
                part = MultipartBody.Part.createFormData("attachment", file1!!.name, reqFile)
                callAddPostAttachmentService()
                addPostMediaAdapter.notifyDataSetChanged()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {


            555,666 -> if (resultCode == Activity.RESULT_OK) {

                Log.e("videouri", data.toString())

                selectedMediaPaths!!.clear()

                selectedMediaPaths!!.addAll(data!!.getStringArrayExtra(UwMediaPicker.UwMediaPickerResultKey))

                if (selectedMediaPaths!!.isNotEmpty()) {

                    Log.e("videouri", "selectedMediaPaths::"+selectedMediaPaths!!.toString()+"")

                    if(selectedMediaPaths.toString().replace(
                            "[",
                            ""
                        ).replace("]", "").contains(".mp4")){
                        attachment_type = "video"
                    }else{
                        attachment_type = "image"
                    }

                    file1 = File(selectedMediaPaths.toString().replace(
                        "[",
                        ""
                    ).replace("]", ""))



                    val customMediaModel = CustomMediaModel()
                    customMediaModel.type = "gallery"
                    customMediaModel.status = "1"
                    customMediaModel.bitmap = null
                    customMediaModel.path = selectedMediaPaths.toString().replace("[", "").replace("]", "")

                    customMediaList!![value] = customMediaModel

                    if (attachment_type == "video") {

                        bitmapThumb = ThumbnailUtils.createVideoThumbnail(
                            selectedMediaPaths.toString().replace(
                                "[",
                                ""
                            ).replace("]", ""),
                            MediaStore.Images.Thumbnails.MICRO_KIND
                        )

                        var baos = ByteArrayOutputStream()
                        bitmapThumb!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        var imageBytes = baos.toByteArray()
//                        bitmapImagePath = imageBytes.toString()
                        bitmapImagePath = Base64.encodeToString(imageBytes, Base64.DEFAULT)

//                        ivProfileImg.setImageBitmap(bitmapThumb)

                    }

                    Log.e("bitmapImagePath", bitmapImagePath)

                    if (file1 != null) {
                        val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
                        part = MultipartBody.Part.createFormData("attachment", file1!!.name, reqFile)
                        callAddPostAttachmentService()
                        addPostMediaAdapter.notifyDataSetChanged()

                        Log.e("videouri", "file1::"+file1!!.length()+"")

                    }
                }


            }

            501 -> if (resultCode == Activity.RESULT_OK) {
                cropImage(uri)
            }

            VIDEO_CAPTURE ->{
                val videoUri = data!!.data

                    if (resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "Video saved to:\n"
                                + videoUri, Toast.LENGTH_LONG).show()

                        try {
                            val filePath = arrayOf(MediaStore.Images.Media.DATA)
                            val cursor = contentResolver.query(videoUri!!, filePath, null, null, null)
                            if (cursor == null) {
                                val path = videoUri.path
                                file = File(path)
                            } else {
                                cursor.moveToFirst()
                                val colIndex = cursor.getColumnIndex(filePath[0])
                                val path = cursor.getString(colIndex)
                                file = File(path)
                            }


                            bitmapThumb = ThumbnailUtils.createVideoThumbnail(
                                selectedMediaPaths.toString().replace(
                                    "[",
                                    ""
                                ).replace("]", ""),
                                MediaStore.Images.Thumbnails.MICRO_KIND
                            )

                            var baos = ByteArrayOutputStream()
                            bitmapThumb!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                            var imageBytes = baos.toByteArray()
//                        bitmapImagePath = imageBytes.toString()
                            bitmapImagePath = Base64.encodeToString(imageBytes, Base64.DEFAULT)
                        }
                        catch (e: Exception) {
                            e.printStackTrace()
                        }

                        val customMediaModel = CustomMediaModel()
                        customMediaModel.type = "gallery"
                        customMediaModel.status = "1"
                        customMediaModel.bitmap = null
                        customMediaModel.path = file!!.path



                        customMediaList!![value] = customMediaModel


                        Log.e("customMedia","ghfgfgjgjjhk"+customMediaList!!.size)


                        if (file != null) {
                            val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
                            part = MultipartBody.Part.createFormData("attachment", file!!.name, reqFile)
                            attachment_type = "video"
                            callAddPostAttachmentService()
                            addPostMediaAdapter.notifyDataSetChanged()

                            Log.e("videouri", "file1::"+file!!.length()+"")

                        }

                    } else {
                        Toast.makeText(this, "Failed to record video",
                            Toast.LENGTH_LONG).show()
                    }



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



    private fun setAdapter() {
        try {
            placesAutoCompleteAdapter =
                PlacesAutoCompleteAdapter(application, R.layout.place_adapter_list)

            etLocation.setAdapter(placesAutoCompleteAdapter)

            etLocation.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->

                    var address = adapterView.getItemAtPosition(i) as String

                    Common.hideKeyboard(this@AddPost, etLocation)

                    try {
                        val geocoder = Geocoder(this@AddPost, Locale.getDefault())
                        var addressList: java.util.ArrayList<Address>?
                        addressList = geocoder.getFromLocationName(
                            address,
                            1
                        ) as java.util.ArrayList<Address>?
                        Log.e("address", addressList.toString())

                        if (addressList != null && addressList.size > 0) {
                            //todo for hide keyboard

                            val returnedAddress = addressList[0].getAddressLine(0)
                            lattitude = addressList[0].latitude
                            longitude = addressList[0].longitude
                            etLocation.text =
                                Editable.Factory.getInstance().newEditable(returnedAddress)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callAddPostAttachmentService() {
    if(Alerts.isNetworkAvailable(this)){
        var mmap = HashMap<String, RequestBody>()

        try {

            Log.e("attachment_type",attachment_type)

            mmap["attachment_type"] =
                RequestBody.create(MediaType.parse("multipart/form-data"), attachment_type)
            mmap["device_type"] =
                RequestBody.create(MediaType.parse("multipart/form-data"), "android")


            if (attachment_type == "video") {
                mmap["thumbnail"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), bitmapImagePath)
            } else {
                mmap["thumbnail"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "")
            }



            RetrofitService(
                this, this, WebUrls.ADD_POST_ATT, WebUrls.ADD_POST_ATT_CODE,
                2, mmap, part!!
            ).callService(true)

            Log.e("service request", "bdjhfvejfvbkbdsd" + part.toString())
            Log.e("mMAp", "bdjhfvejfvbkbdsd   $mmap")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
    else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
    }

    private fun addPostService() {

        if(Alerts.isNetworkAvailable(this)){

        val postparam = JSONObject()

        caption = etWriteCaption.text.toString()

        var idArrayList = JSONArray()

        for (i in 0 until postAttachmentIds.size) {
            idArrayList!!.put(postAttachmentIds[i])
        }

        postparam.put(
            "user_id",
            PreferenceFile.getInstance().getPreferenceData(this@AddPost, WebUrls.KEY_USERID)
        )

        postparam.put("caption", caption)
        postparam.put("post_through_group", addPostThrugrp)
        postparam.put("tag_user", tagPeopleResult.toString())
      //  postparam.put("tag_group", groupListResult)
        val jsonArray = JSONArray()
            for(i in 0 until groupListResult.size) {
                jsonArray.put(groupListResult[i])
            }
            postparam.put("tag_group", jsonArray)

            val jsonArray1 = JSONArray()
            for(i in 0 until tagPeopleResult.size) {
                jsonArray1.put(tagPeopleResult[i])
            }
            postparam.put("tag_user", jsonArray1)


        postparam.put("location_name", "location_name")

        postparam.put("post_attachment_ids", idArrayList)

        Log.e(TAG, "Params: $postparam")


        RetrofitService(this, this, WebUrls.ADD_POST, WebUrls.ADD_POST_CODE, 3, postparam).callService(true)

    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun callTagPeopleService() {
        if(Alerts.isNetworkAvailable(this)){
        val postParam = JSONObject()
        try {
            RetrofitService(
                this, this, WebUrls.VIEW_FRIEND_LIST
                        + "/" + PreferenceFile.getInstance().getPreferenceData(
                    this@AddPost, WebUrls.KEY_USERID
                ),
                WebUrls.VIEW_FREIND_LIST_CODE, 1, postParam
            ).callService(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun callSelectGroupService() {
        if(Alerts.isNetworkAvailable(this)){

        val postParam = JSONObject()
        try {
            RetrofitService(
                this, this,
                WebUrls.SEND_GROUP_LIST + "/" + PreferenceFile.getInstance().getPreferenceData(
                    this@AddPost, WebUrls.KEY_USERID
                ),
                WebUrls.SEND_GROUP_LIST_CODE, 1, postParam
            ).callService(false)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

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

            R.id.ivBackArrow -> {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }

            R.id.rltag -> {
                Toast.makeText(applicationContext,"No data",Toast.LENGTH_SHORT).show()
            }

            R.id.rlGrpInVis -> {
                Toast.makeText(applicationContext,"No groups",Toast.LENGTH_SHORT).show()
            }

            R.id.tvPostFinal -> {

                Log.e("POST",""+postAttachmentIds!!.size)

                if(postAttachmentIds!!.size ==0){
                    Common.customDialog(this@AddPost,"Alert!","Please select the media.")
                }else {
                    addPostService()
                }
//                var i = Intent(this, Home::class.java)
//                startActivity(i)
            }

        }

    }

    override fun onResponse(requestCode: Int, response: String) {

        when (requestCode) {

            WebUrls.ADD_POST_ATT_CODE -> {
                try {
                    val jsonObject = JSONObject(response)

                    val res = jsonObject.getString("response")

                    if ((res == "true")) {

                        val data = jsonObject.getJSONObject("data")

                        if (data != null) {

                            val id = data.getInt("id")

                            Log.e("jsondata", "vjv $id")

                            postAttachmentIds.add(id)

                            Log.e("listsize", postAttachmentIds.size.toString())
                        }

                    }
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }

            }
            WebUrls.ADD_POST_CODE -> {

                try {
                    val jsonObject = JSONObject(response)

                    val res = jsonObject.getString("response")

                    customDialog(this, "Unilife",jsonObject.getString("message"))

//                    if ((res == "true")) {
//                        val data = jsonObject.getJSONObject("data")
//                        if (data != null) {
//                            val id = data.getInt("id")
//                            Log.e("jsondata", "vjv $id")
//                        }
//                    }
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }

            }

            WebUrls.VIEW_FREIND_LIST_CODE -> {

                val jsonObject = JSONObject(response)

                val res = jsonObject.getString("response")

                tagPeopleListString.clear()
                tagPeopleList.clear()

                if (res.equals("true", true)) {

                    var data = jsonObject.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val infoModel = InfoModel()
                        var datObj = data.getJSONObject(i)
                        if (!datObj.isNull("userfriend")) {
                            var userFriend = datObj.getJSONObject("userfriend")
                            infoModel.id = userFriend.getString("id")
                            infoModel.name = userFriend.getString("username")
                            infoModel.image = userFriend.getString("profile_image")
                            tagPeopleList.add(infoModel)
                            tagPeopleListString.add(infoModel.name)
                        }
                    }

                    Log.e(TAG, "tagPeopleList:" + tagPeopleList.size.toString())
                    rltag!!.visibility=View.GONE
                    spTagPeople!!.visibility=View.VISIBLE

                    setTagAdapter()

                } else {

                    rltag!!.visibility=View.VISIBLE
                    spTagPeople!!.visibility=View.GONE
//                    Common.alertDialog(this, jsonObject.getString("message"))


                }

                callSelectGroupService()

            }
            WebUrls.SEND_GROUP_LIST_CODE -> {

                val jsonObject = JSONObject(response)

                val res = jsonObject.getString("response")

                groupListString.clear()
                groupList.clear()

                if (res.equals("true", true)) {

                    var data = jsonObject.getJSONArray("data")

                    for (i in 0 until data.length()) {
                        val infoModel = InfoModel()
                        var datObj = data.getJSONObject(i)
                        var userGroupObj = datObj.getJSONObject("usergroup")
                        infoModel.id = userGroupObj.getString("id")
                        infoModel.name = userGroupObj.getString("group_name")
                        infoModel.image = userGroupObj.getString("group_image")
                        groupList.add(infoModel)
                        groupListString.add(infoModel.name)
                    }

                    Log.e(TAG, "groupListString:" + groupListString.size.toString())
                    rlGrpInVis!!.visibility=View.GONE
                    rlGrpVis!!.visibility=View.VISIBLE
                    setGroupAdapter()

                } else {

                    rlGrpInVis!!.visibility=View.VISIBLE
                    rlGrpVis!!.visibility=View.GONE

//                    Common.alertDialog(this, jsonObject.getString("message"))
                }
            }
        }
    }

    fun setTagAdapter() {

        val adapterP = ArrayAdapter(
            this@AddPost,
            android.R.layout.simple_list_item_multiple_choice, tagPeopleListString
        )

        spTagPeople?.setListAdapter(adapterP)
            ?.setListener<BaseMultiSelectSpinner> { selected ->
                tagPeopleResult.clear()
                for (k in 0 until selected!!.size) {
                    Log.e(TAG, "ABCD:" + selected[k].toString())
                    if (selected[k]) {
                        tagPeopleResult.add(tagPeopleList[k].id)
                        Log.e(
                            TAG,
                            "selected:" + selected[k] + "tagPeopleResult:" + tagPeopleResult
                        )
                    }
                }
            }

            ?.setTitle<BaseMultiSelectSpinner>("Tag People")
            ?.setAllUncheckedText<BaseMultiSelectSpinner>("Tag People")
            ?.setAllCheckedText<BaseMultiSelectSpinner>(
                tagPeopleListString.toString().replace(
                    "[",
                    ""
                ).replace("]", "")
            )
    }

    fun setGroupAdapter() {

        val adapterP = ArrayAdapter(
            this@AddPost,
            android.R.layout.simple_list_item_multiple_choice, groupListString
        )

        if(groupListString!!.size > 0) {

            spSelectGroup?.setListAdapter(adapterP)
                ?.setListener<BaseMultiSelectSpinner> { selected ->
                    groupListResult.clear()
                    for (k in 0 until selected!!.size) {
                        Log.e(TAG, "ABCD:" + selected[k].toString())
                        if (selected[k]) {
                            groupListResult.add(groupList[k].id)
                            Log.e(TAG, "selected:" + selected[k] + "tagPeopleResult:" + groupListResult)

                        }
                    }
                    ivSwitchButton!!.isEnabled = groupListResult.size>0
                    Log.e(TAG, "groupListString:" + groupListString.toString())
                }

                ?.setTitle<BaseMultiSelectSpinner>("Select Group")
                ?.setAllUncheckedText<BaseMultiSelectSpinner>("Joined group list")
                ?.setAllCheckedText<BaseMultiSelectSpinner>(
                    groupListString.toString().replace(
                        "[",
                        ""
                    ).replace("]", "")
                )
        }else{
            spSelectGroup?.setAllUncheckedText<BaseMultiSelectSpinner>("Joined group list")

        }

//        customAdapter = CustomSpinnerAdapter(this, tagPeopleList!!)
//        spTagPeople!!.adapter = customAdapter

    }

    fun uploadAttachment() {

        var alertDialogBuilder = AlertDialog.Builder(this@AddPost, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this@AddPost).inflate(R.layout.upload_attachment, null)
        tvUploadVideo = view.findViewById(R.id.tvUploadVideo)
        tvCameraImage = view.findViewById(R.id.tvCameraImage)
        tvGalleryImage = view.findViewById(R.id.tvGalleryImage)
        tvVideoCamera = view.findViewById(R.id.tvVideoCamera)
        tvCancel = view.findViewById(R.id.tvCancel)

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)

        this.tvCameraImage!!.setOnClickListener(View.OnClickListener {
            launchCamera()
            alertDialog!!.dismiss()

        })

        this.tvVideoCamera!!.setOnClickListener(View.OnClickListener {
            launchVideoCamera()
            alertDialog!!.dismiss()

        })

        this.tvGalleryImage!!.setOnClickListener(View.OnClickListener {
            //            val galIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(
//                Intent.createChooser(galIntent, "Select Image from Gallery"),
//                111
//            )
            chooseImages()

            alertDialog!!.dismiss()
        })

        this.tvUploadVideo!!.setOnClickListener(View.OnClickListener {
            chooseVideos()
            alertDialog!!.dismiss()

        })

        this.tvCancel!!.setOnClickListener(View.OnClickListener {
            alertDialog!!.dismiss()
        })


        alertDialog!!.window
            .setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        val width = resources.displayMetrics.widthPixels * 0.85
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog!!.window.setLayout(width.toInt(), height)
        alertDialog!!.window.setGravity(Gravity.BOTTOM)
        alertDialog!!.show()

    }



    private fun setMediaAdapter() {
        rView!!.layoutManager = LinearLayoutManager(
            this@AddPost,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        for(i in 0 until 6){
            val customMediaModel = CustomMediaModel()

            customMediaModel.type = "image"
            customMediaModel.status = "1"
            customMediaModel.bitmap = null

            customMediaList!!.add(customMediaModel)

        }


        addPostMediaAdapter = AddPostMediaAdapter(this, customMediaList)

        rView!!.adapter = addPostMediaAdapter

        addPostMediaAdapter.getAddPostImagesVideos(object :
            AddPostMediaAdapter.AddPostMediaInterface {
            override fun AddPostMedia(position: Int) {

                value = position
                uploadAttachment()
            }
        })
    }

    private fun chooseVideos(){

        UwMediaPicker
            .with(this)						// Activity or Fragment
            .setRequestCode(555)				// Give request code, default is 0
            .setGalleryMode(UwMediaPicker.GalleryMode.VideoGallery) // GalleryMode: ImageGallery or VideoGallery, default is ImageGallery
            .setGridColumnCount(2)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is llight status bar enable, default is true
            .enableImageCompression(true)				// Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)				// Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)				// Compressed image's max height px, default is 720
            .setCompressFormat(Bitmap.CompressFormat.JPEG)		// Compressed image's format, default is JPEG
            .setCompressionQuality(85)				// Image compression quality, default is 85
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this@AddPost))	// Compressed image file's destination path, default is Pictures Dir
            .open()
    }

    private fun chooseImages(){
        UwMediaPicker
            .with(this)						// Activity or Fragment
            .setRequestCode(666)				// Give request code, default is 0
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery) // GalleryMode: ImageGallery or VideoGallery, default is ImageGallery
            .setGridColumnCount(2)                                  // Grid column count, default is 3
            .setMaxSelectableMediaCount(1)                         // Maximum selectable media count, default is null which means infinite
            .setLightStatusBar(true)                                // Is llight status bar enable, default is true
            .enableImageCompression(true)				// Is image compression enable, default is false
            .setCompressionMaxWidth(1280F)				// Compressed image's max width px, default is 1280
            .setCompressionMaxHeight(720F)				// Compressed image's max height px, default is 720
            .setCompressFormat(Bitmap.CompressFormat.JPEG)		// Compressed image's format, default is JPEG
            .setCompressionQuality(85)				// Image compression quality, default is 85
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this@AddPost))	// Compressed image file's destination path, default is Pictures Dir
            .open()
    }


    override fun onBackPressed() {
        startActivity(Intent(this@AddPost, Home::class.java))

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
            intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }

        return Bitmap.createScaledBitmap(image, width, height, true)
    }


}

