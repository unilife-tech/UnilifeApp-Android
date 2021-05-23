package unilife.com.unilife.home

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.navigation_new.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.Startup
import unilife.com.unilife.home.ReportReasonActivity.strReportReason
import unilife.com.unilife.home.adapter.HomeAdapter2
import unilife.com.unilife.home.requests.DeletePostRequest
import unilife.com.unilife.home.requests.ReportPostRequest
import unilife.com.unilife.home.requests.SelectPollRequest
import unilife.com.unilife.home.responses.HomeResponse
import unilife.com.unilife.home.responses.LikeUnlikeResponse
import unilife.com.unilife.notification.NotificationListing
import unilife.com.unilife.profile.model.ProfileResponse
import unilife.com.unilife.profile.model.responses.CommonResponse
import unilife.com.unilife.profile.model.responses.DeletePostResponse
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retro.WebUrls.KEY_USERID
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.updated.EventActivity
import unilife.com.unilife.updated.MediaActivity
import unilife.com.unilife.updated.OpinionActivity
import unilife.com.unilife.updated.PollActivity
import unilife.com.unilife.utils.SharePref


class Home : AppDrawer(), View.OnClickListener {

    var count = 0
    val TAG = Home::class.java.simpleName
    var username: String = ""
    var profile_image: String = ""
    var group_image: String = ""
    var group_name: String = ""
    var strUserId: String = ""
    var strPostId: String = ""
    var strReportType: String = ""

    private var progressDialog: Dialog? = null
    private var recyclerView: RecyclerView? = null
    private var homeadapter: HomeAdapter2? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.word_grey))

        ivNoti.setOnClickListener {
            val intent = Intent(this, NotificationListing::class.java)
            startActivity(intent)
        }

        tvPost.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        tvMainTitle.text = "Unilife"
        llToolBar.setBackgroundColor(resources.getColor(R.color.white))
        checkPermission()
        val intent: Intent = intent

        if (intent != null) {
            if (PreferenceFile.getInstance()
                    .getPreferenceData(this@Home, WebUrls.KEY_USERID) == null
            ) {
                val intent = Intent(this@Home, Startup::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                val action: String? = intent.action
                val data: Uri? = intent.data
                Log.e("DATAAAA", action.toString())
                Log.e("DATAAAA", data.toString())
            }
        }

        findViewById<TextView>(R.id.txtMedia).setOnClickListener {
            val i = Intent(this, MediaActivity::class.java)
            startActivity(i)
        }
        findViewById<TextView>(R.id.txtEvent).setOnClickListener {
            val i = Intent(this, EventActivity::class.java)
            startActivity(i)
        }
        findViewById<TextView>(R.id.txtPoll).setOnClickListener {
            val i = Intent(this, PollActivity::class.java)
            startActivity(i)
        }
        findViewById<TextView>(R.id.edtOpinion).setOnClickListener {
            val i = Intent(this, OpinionActivity::class.java)
            startActivity(i)
        }

        initHeader()

    }


    fun initHeader() {
        homeadapter = HomeAdapter2(this)
        recyclerView = findViewById(R.id.rcyMain)
        recyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            this?.layoutManager = LinearLayoutManager(this@Home)
            // set the custom adapter to the RecyclerView
            this?.adapter = homeadapter

            this?.setHasFixedSize(true)

            this?.isNestedScrollingEnabled = false
        }

//        getHomepageData(1)
    }

    override fun onResume() {
        super.onResume()
        if (strReportReason.equals("")) {
            getHomepageData(1)
        } else {
            if (strReportType.equals("user")) {
                reportUser(strReportReason)
            } else if (strReportType.equals("post")) {
                reportPost(strReportReason)
            }
            strReportReason = ""
        }

        if (SharePref(this).about.equals("")) {
            getProfile()
        } else {
            txtNavAbout.text = SharePref(this).about
        }
    }

    fun showDialog() {
        if (progressDialog == null) {
            progressDialog = Dialog(this)
            progressDialog?.setCancelable(false)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setContentView(R.layout.progress_dialog)
            progressDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            progressDialog!!.show()
        } else if (!progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    fun hideDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog?.dismiss()
        }
    }

    //todo av
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            val hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA)
            val hasReadExtPermission =
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasWriteExtPermission =
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val permissionList = java.util.ArrayList<String>()
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.CAMERA)
            }

            if (hasReadExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (hasWriteExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (permissionList.isNotEmpty()) {
                requestPermissions(permissionList.toTypedArray(), 2)
            }
        }
    }

    class HomeRequest {
        private var pagination: String? = null
        private var source: String? = null
        private var version: String? = null

        fun setPagination(pagination: String?) {
            this.pagination = pagination
        }
        fun setSource(source: String?) {
            this.source = source
        }
        fun setVersion(version: String?) {
            this.version = version
        }
    }

    fun getHomepageData(page: Int) {

        val manager = this.packageManager
        val info = manager?.getPackageInfo(
            this.packageName, 0
        )

        val versionNumber = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info?.longVersionCode
        } else {
            info?.versionCode
        }

        val homeRequest = HomeRequest()
        homeRequest.setPagination(page.toString() + "")
        homeRequest.setSource("android")
        homeRequest.setVersion(versionNumber.toString())

        Log.e("version", versionNumber.toString())

//        showDialog()
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getHomeData(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), homeRequest
        )
        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
//                hideDialog()
                if (response.body()!!.status) {
                    if (response.body()!!.data != null && response.body()!!.data.size > 0) {
                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility = View.GONE
                        recyclerView!!.visibility = View.VISIBLE
                        homeadapter!!.updateData(response.body()!!.data)
                    } else {
                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility = View.VISIBLE
                        recyclerView!!.visibility = View.GONE
                    }
                }else if (response.body()!!.message.contains("New version is available")){
                    val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@Home)
                    alertDialogBuilder.setTitle("Update Your App")
                    alertDialogBuilder.setMessage("Looks like we need to put you on the latest version Unilife.")
                    alertDialogBuilder.setCancelable(false)

                    alertDialogBuilder.setPositiveButton("Update Now"
                    ) { dialog, _ ->
                        dialog.cancel()
                        val appPackageName =
                            packageName // package name of the app

                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=$appPackageName")
                                )
                            )
                        } catch (anfe: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                )
                            )
                        }

                    }

                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            }

            override fun onFailure(
                call: Call<HomeResponse>,
                t: Throwable
            ) {
                t.printStackTrace()
//                hideDialog()
            }
        })
    }

    class EventCountRegister {
        private var event_id: String? = null
        fun setEventId(event_id: String?) {
            this.event_id = event_id
        }
    }

    class LikeRequest {
        private var user_id: String? = null
        private var post_id: String? = null
        fun setUser_id(user_id: String?) {
            this.user_id = user_id
        }

        fun setPost_id(post_id: String?) {
            this.post_id = post_id
        }
    }

    fun likeUnlikePost(id: String?) {
//        if (!isNetworkConnected()
//            return;
        val likeRequest = LikeRequest()
        likeRequest.setUser_id(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            )
        )
        likeRequest.setPost_id(id)
//        showDialog()
        val apiInterface =
            ApiClient.getClientOld().create(ApiInterface::class.java)
        val call = apiInterface.likePost(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), likeRequest
        )
        call.enqueue(object : Callback<LikeUnlikeResponse> {
            override fun onResponse(
                call: Call<LikeUnlikeResponse>,
                response: Response<LikeUnlikeResponse>
            ) {
//                hideDialog()
                if (response.isSuccessful) {
                    getHomepageData(1)
                    Toast.makeText(this@Home, response.body()!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<LikeUnlikeResponse>,
                t: Throwable
            ) {
                hideDialog()
            }
        })
    }


    fun eventCounter(id: String?) {
//        if (!isNetworkConnected()
//            return;
        val eventCountRegister = EventCountRegister()
        eventCountRegister.setEventId(id)
//        showDialog()
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.registerEvent(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), eventCountRegister
        )
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,

                response: Response<CommonResponse>
            ) {
//                hideDialog()
                if (response.isSuccessful) {
                    getHomepageData(1)
//                    Toast.makeText(this@Home, response.body()!!.message, Toast.LENGTH_SHORT)
//                        .show()
                }
            }

            override fun onFailure(
                call: Call<CommonResponse>,
                t: Throwable
            ) {
                hideDialog()
            }
        })
    }

    fun showPopup(post_id: String?, user_id: String?, view: View) {
        //Creating the instance of PopupMenu
        var popup = PopupMenu(this, view)
        //Inflating the Popup using xml file
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->

            if (item.itemId == R.id.one) {
                val alertDialog: AlertDialog = AlertDialog.Builder(this) //set icon
//                    .setIcon(android.R.drawable.ic_dialog_alert) //set title
//                    .setTitle("Are you sure to Exit") //set message
                    .setMessage("Are you sure to report this user?") //set positive button
                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what would happen when positive button is clicked
//                            reportUser(user_id)
                            strReportType = "user"
                            strUserId = user_id.toString()
                            val intent = Intent(this@Home, ReportReasonActivity::class.java)
                            startActivity(intent)
                        }
                    }) //set negative button
                    .setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what should happen when negative button is clicked
                            dialogInterface?.dismiss()
                        }
                    })
                    .show()


            } else if (item.itemId == R.id.two) {
                val alertDialog: AlertDialog = AlertDialog.Builder(this) //set icon
//                    .setIcon(android.R.drawable.ic_dialog_alert) //set title
//                    .setTitle("Are you sure to Exit") //set message
                    .setMessage("Are you sure to report this post?") //set positive button
                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what would happen when positive button is clicked
//                            reportPost(post_id)
                            strReportType = "post"
                            strUserId = user_id.toString()
                            strPostId = post_id.toString()
                            val intent = Intent(this@Home, ReportReasonActivity::class.java)
                            startActivity(intent)
                        }
                    }) //set negative button
                    .setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what should happen when negative button is clicked
                            dialogInterface?.dismiss()
                        }
                    })
                    .show()

            } else if (item.itemId == R.id.three) {
                val alertDialog: AlertDialog = AlertDialog.Builder(this) //set icon
//                    .setIcon(android.R.drawable.ic_dialog_alert) //set title
//                    .setTitle("Are you sure to Exit") //set message
                    .setMessage("Are you sure delete this post?") //set positive button
                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what would happen when positive button is clicked
                            deletePost(post_id, user_id)
                        }
                    }) //set negative button
                    .setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                            //set what should happen when negative button is clicked
                            dialogInterface?.dismiss()
                        }
                    })
                    .show()
            }

            true
        }

        popup.show()//showing popup menu
    }

    fun selectPoll(option: String?) {
        showDialog()
        val selectPollRequest = SelectPollRequest()
        selectPollRequest.setSelect_poll_option(option)
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call =
            apiInterface.selectPollOption(
                PreferenceFile.getInstance().getPreferenceData(
                    this,
                    WebUrls.KEY_USERID
                ), selectPollRequest
            )
        call.enqueue(object : Callback<CommonResponse?> {
            override fun onResponse(
                call: Call<CommonResponse?>,
                response: Response<CommonResponse?>
            ) {
                hideDialog()
                getHomepageData(1)
            }

            override fun onFailure(
                call: Call<CommonResponse?>,
                t: Throwable
            ) {
                hideDialog()
            }
        })
    }

    fun reportPost(reason: String?) {
//        if (!isNetworkConnected()
//            return;
        val postRequest = ReportPostRequest()
        postRequest.setReport_post_id(strPostId)
        postRequest.setReason(reason)
        postRequest.setType("Spam")


//        showProgressDialog();
        showDialog()
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.reportPost(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), postRequest
        )
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                hideDialog()
//                hideProgressDialog();
                if (response.isSuccessful) {
                    Toast.makeText(this@Home, response.body()!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<CommonResponse>,
                t: Throwable
            ) {
                hideDialog()
//                hideProgressDialog();
//                showToast(t.getMessage());
            }
        })
    }

    fun reportUser(reason: String?) {
//        if (!isNetworkConnected()
//            return;
        val postRequest = ReportPostRequest()
        postRequest.setReport_user_id(strUserId)
        postRequest.setReason(reason)
        postRequest.setType("Spam")


//        showProgressDialog();
        showDialog()
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.reportUser(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), postRequest
        )
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                hideDialog()
//                hideProgressDialog();
                if (response.isSuccessful) {
                    Toast.makeText(this@Home, response.body()!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<CommonResponse>,
                t: Throwable
            ) {
                hideDialog()
//                hideProgressDialog();
//                showToast(t.getMessage());
            }
        })
    }

    fun deletePost(id: String?, userId: String?) {
//        if (!isNetworkConnected()
//            return;
        val deletePostRequest = DeletePostRequest()
        deletePostRequest.setPost_id(id)
        deletePostRequest.setUser_id(userId)

//        showProgressDialog();
        showDialog()
        val apiInterface =
            ApiClient.getClientOld().create(ApiInterface::class.java)
        val call = apiInterface.deletePost(
            PreferenceFile.getInstance().getPreferenceData(
                this,
                WebUrls.KEY_USERID
            ), deletePostRequest
        )
        call.enqueue(object : Callback<DeletePostResponse> {
            override fun onResponse(
                call: Call<DeletePostResponse>,
                response: Response<DeletePostResponse>
            ) {
                hideDialog()
//                hideProgressDialog();
                if (response.isSuccessful) {
                    getHomepageData(1)
                    Toast.makeText(this@Home, response.body()!!.data, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(
                call: Call<DeletePostResponse>,
                t: Throwable
            ) {
                hideDialog()
//                hideProgressDialog();
//                showToast(t.getMessage());
            }
        })
    }

    fun getProfile() {
        if (!isNetworkConnected()) return
        showProgressDialog()
        val apiInterface =
            ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getProfile(
            PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID)
        )
        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                hideProgressDialog()
                Log.e("response code -->", "" + response.code())
                if (response.isSuccessful && response.body()!!.status) {
                    if (response.body()!!.selfIntroduction!=null) {
                        if (!response.body()!!.selfIntroduction.organisation.equals("")) {
                            SharePref(this@Home).about = response.body()!!.selfIntroduction
                                .designation + " at " + response.body()!!
                                .selfIntroduction
                                .organisation

                            txtNavAbout.text = response.body()!!.selfIntroduction
                                .designation + " at " + response.body()!!
                                .selfIntroduction
                                .organisation
                        } else {
                            txtNavAbout.text = "  -"
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<ProfileResponse>,
                t: Throwable
            ) {
                hideProgressDialog()
                showToast(t.message!!)
            }
        })
    }
}




