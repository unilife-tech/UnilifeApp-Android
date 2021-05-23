package unilife.com.unilife.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_change_chat_wallpaper.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import unilife.com.unilife.BuildConfig
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import java.io.File

class ChangeChatWallpaper : AppCompatActivity(), RetrofitResponse {


    internal var tvCancel: TextView? = null
    internal var tvGalleryImage: TextView? = null
    internal var tvCameraImage: TextView? = null
    internal var tvUploadVideo: TextView? = null
    private var uri: Uri? = null
    private var file: File? = null
    private var file1: File? = null
    var selectedMediaPaths: ArrayList<String>? = ArrayList()
    private var alertDialog: AlertDialog? = null
    private var part: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_chat_wallpaper)
        ivNotification.visibility = View.GONE
        tvTitle.text = "Change Wallpaper"
        setClick()
    }

    private fun setClick() {
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
        iv_image.setOnClickListener {
            uploadAttachment()
        }

        textView.setOnClickListener {
            uploadimage()
        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {
                WebUrls.UPLOAD_WALLPAPER_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        customDialog(this, "Unilife", "Chat Wallpaper Changed")

                    } else {
                        customDialog(this, "Unilife", "Please try again")
                    }

                }

            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    private fun uploadimage() {
        if (Alerts.isNetworkAvailable(this)) {
            if (file1 != null) {

                var mmap = HashMap<String, RequestBody>()

                mmap["user_id"] = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                mmap["type"] = RequestBody.create(MediaType.parse("multipart/form-data"), "image")


                RetrofitService(
                    this, this, WebUrls.UPLOAD_WALLPAPER, WebUrls.UPLOAD_WALLPAPER_CODE,
                    2, mmap, part!!
                ).callService(true)

            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    fun uploadAttachment() {

        var alertDialogBuilder = AlertDialog.Builder(this, R.style.custom_alert_dialog)
        val view =
            LayoutInflater.from(this).inflate(R.layout.upload_attachment, null)
        tvUploadVideo = view.findViewById(R.id.tvUploadVideo)
        tvCameraImage = view.findViewById(R.id.tvCameraImage)
        tvGalleryImage = view.findViewById(R.id.tvGalleryImage)
        tvCancel = view.findViewById(R.id.tvCancel)

        alertDialogBuilder.setView(view)

        alertDialog = alertDialogBuilder.create()
        alertDialog!!.setCancelable(false)
        alertDialog!!.setCanceledOnTouchOutside(false)

        tvUploadVideo!!.visibility = View.GONE

        this.tvCameraImage!!.setOnClickListener(View.OnClickListener {
            launchCamera()
            alertDialog!!.dismiss()

        })

        this.tvGalleryImage!!.setOnClickListener(View.OnClickListener {

            chooseImages()

            alertDialog!!.dismiss()
        })

        this.tvUploadVideo!!.visibility = View.GONE


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
            .setCompressedFileDestinationPath(Alerts.makeDirectory(this))    // Compressed image file's destination path, default is Pictures Dir
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
                        part = MultipartBody.Part.createFormData("image", file1!!.name, reqFile)
                        Log.e("videouri", "file1::" + file1!!.length() + "")
                    }

                    var bmOptions = BitmapFactory.Options()
                    var bitmap = BitmapFactory.decodeFile(file1!!.path, bmOptions)
                    bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true)
                    iv_image.setImageBitmap(bitmap)
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

            iv_image.setImageBitmap(bitmap)


            file1 = File(gettingPath.toString())

            if (file1 != null) {
                val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
                part = MultipartBody.Part.createFormData("image", file1!!.name, reqFile)
            }

        } catch (e: Exception) {
            e.printStackTrace()
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

            alertDialog.dismiss()
            startActivity(Intent(this@ChangeChatWallpaper, ChatListing::class.java))
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}
