package unilife.com.unilife

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.provider.Settings
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_app_drawer.*
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.custom_dialog2.view.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.navigation_new.*
import org.json.JSONObject
import unilife.com.unilife.Faq.Faq
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.blogs.BlogSettings
import unilife.com.unilife.brands.BrandSettings
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.ChatSetting
import unilife.com.unilife.chat.model.ItemSearch
import unilife.com.unilife.home.Home
import unilife.com.unilife.home.adapter.NavigationAdapter
import unilife.com.unilife.login.FirstLoginActivity
import unilife.com.unilife.profile.MyAccountActivity
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.*
import java.util.*

open class AppDrawer : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var DELETE_ACC = false
    var dialog: Dialog? = null
//    var recyclerNav: RecyclerView? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        var drawerLayout =
            LayoutInflater.from(this).inflate(R.layout.activity_app_drawer, null) as DrawerLayout?
        var llContent = drawerLayout!!.findViewById<View>(R.id.llContent) as LinearLayout

        layoutInflater.inflate(layoutResID, llContent, true)
        super.setContentView(drawerLayout)


        var actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            }

        actionBarDrawerToggle.syncState()

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        drawerLayout.drawerElevation = 0f

        slide()
        setOnClickListner()
        initDrawer()

        init()
    }

    fun init() {
        val defultImg = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .error(R.drawable.default_image)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .priority(Priority.IMMEDIATE)

        Glide.with(this@AppDrawer)
            .load(
                WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
                    this@AppDrawer,
                    WebUrls.KEY_PROFILE_IMAGE
                )
            )
            .apply(defultImg)
            .into(imgNavDrawer)

        Glide.with(this@AppDrawer)
            .load(
                WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
                    this@AppDrawer,
                    WebUrls.KEY_PROFILE_IMAGE
                )
            )
            .apply(defultImg)
            .into(ivProfileImg)

        txtNavName.text = PreferenceFile.getInstance().getPreferenceData(
            this@AppDrawer,
            WebUrls.KEY_USERNAME
        )
    }

    fun initDrawer() {
        val arrayList = ArrayList<ItemSearch>()

        val arrName = arrayOf(
            "My Profile",
            "Invite Friends",
            "App and integrations",
            "",
            "Chat Setting",
            "Brand Setting",
            "Blog Setting",
            "",
            "Help",
            "FAQ",
            "Give Feedback",
            "Change Password",
            "Logout"
        )

        val arrImage = intArrayOf(
            R.drawable.ic_account_circle_24px,
            R.drawable.ic_record_voice_over_24px,
            R.drawable.ic_tablet_mac_24px,
            0,
            R.drawable.ic_chat_24px,
            R.drawable.ic_turned_in_not_24px,
            R.drawable.ic_chrome_reader_mode_24px,
            0,
            R.drawable.ic_contact_phone_24px,
            R.drawable.ic_help_24px,
            R.drawable.ic_thumbs_up_down_24px,
            R.drawable.ic_lock_outline_24px,
            R.drawable.ic_power_settings_new_24px
        )

        for (i in arrImage.indices) {
            val itemSearch1 = ItemSearch()
            itemSearch1.image = arrImage[i]
            itemSearch1.name = arrName[i]
            arrayList.add(itemSearch1)
        }

//        recyclerNav = findViewById(R.id.recyclerNav)
        recyclerNav.apply {
            // set a LinearLayoutManager to handle Android
            this?.setHasFixedSize(true)
            // RecyclerView behavior
            this?.layoutManager = LinearLayoutManager(this@AppDrawer)
            // set the custom adapter to the RecyclerView
            this?.adapter = NavigationAdapter(this@AppDrawer, arrayList)
        }

        recyclerNav.addOnItemTouchListener(object : RecyclerItemClickListener(
            this,
            recyclerNav,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    when (position) {
                        0 -> {
                            val intent = Intent(this@AppDrawer, MyAccountActivity::class.java)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(this@AppDrawer, InviteFriend::class.java)
                            startActivity(intent)
                        }
                        2 -> {
                            showToast("Coming soon...")
//                            val intent = Intent(this@AppDrawer, Feedback::class.java)
//                            startActivity(intent)
                        }
                        4 -> {
                            val intent = Intent(this@AppDrawer, ChatSetting::class.java)
                            startActivity(intent)
                        }
                        5 -> {
                            val intent = Intent(this@AppDrawer, BrandSettings::class.java)
                            startActivity(intent)
                        }
                        6 -> {
                            val intent = Intent(this@AppDrawer, BlogSettings::class.java)
                            startActivity(intent)
                        }

                        8 -> {
                            val intent = Intent(this@AppDrawer, Help::class.java)
                            startActivity(intent)
                        }
                        9 -> {
                            val intent = Intent(this@AppDrawer, Faq::class.java)
                            startActivity(intent)
                        }
                        10 -> {
                            val intent = Intent(this@AppDrawer, Feedback::class.java)
                            startActivity(intent)
                        }
                        11 -> {
//                            Log.e("llDeleteAccount", "sscfsascfaws")
//                            customDialog2(
//                                this@AppDrawer,
//                                "Unilife",
//                                "Your account and your data will be deleted permanently?"
//                            )
                            val intent = Intent(this@AppDrawer, ChangePassword::class.java)
                            startActivity(intent)
                        }
                        12 -> {
                            deleteUserDevice()
                        }
                    }
                }

                override fun onLongItemClick(view: View?, position: Int) {
                    showToast(arrayList.get(position).name)
                }
            }) {})
    }

    fun slide() {
        val actionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val slideX = drawerView.width * slideOffset
//                relativeLayout.setTranslationX(slideX)
                }
            }
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle.setToolbarNavigationClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.ivProfileImg -> {
                slide()
                drawerLayout!!.openDrawer(GravityCompat.START)
            }
            R.id.ivBack -> {
                super.onBackPressed()
            }

//            R.id.llLogout -> {
//                deleteUserDevice()
//            }

//            R.id.llChangePassword -> {
//                val intent = Intent(this, ChangePassword::class.java)
//                startActivity(intent)
//            }

//            R.id.llFaq -> {
//                val intent = Intent(this, Faq::class.java)
//                startActivity(intent)
//            }

//            R.id.llFeedback -> {
//                val intent = Intent(this, Feedback::class.java)
//                startActivity(intent)
//
//            }

//            R.id.llMyAccount -> {
//                val intent = Intent(this, MyAccountActivity::class.java)
//                startActivity(intent)
//
//            }

            R.id.imgNavDrawer -> {
                val intent = Intent(this, MyAccountActivity::class.java)
                startActivity(intent)
            }

//            R.id.llHelp -> {
//                val intent = Intent(this, Help::class.java)
//                startActivity(intent)
//
//            }


//            R.id.llChatSettings -> {
//                val intent = Intent(this, ChatSetting::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llBrandsSettings -> {
//                val intent = Intent(this, BrandSettings::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llBlogSettings -> {
//                val intent = Intent(this, BlogSettings::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llDeleteAccount -> {
//                Log.e("llDeleteAccount", "sscfsascfaws")
//                customDialog2(
//                    this,
//                    "Unilife",
//                    "Your account and your data will be deleted permanently?"
//                )
//            }
//
//            R.id.llInviteFriend -> {
//                Log.e("llInviteFriend", "sscfsascfaws")
//                val intent = Intent(this, InviteFriend::class.java)
//                startActivity(intent)
//            }

            //======================================================================================
            R.id.rlChat -> {
                val intent = Intent(this, ChatListing::class.java)
                startActivity(intent)
                finish()
            }

            R.id.rlBlogs -> {
                val intent = Intent(this, Blog::class.java)
                startActivity(intent)
                finish()
            }

            R.id.rlBrands -> {
                val intent = Intent(this, BrandsHome::class.java)
                startActivity(intent)
                finish()
            }
            R.id.rlEvent -> {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    fun setOnClickListner() {
        rlChat.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)
        rlEvent.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        imgNavDrawer.setOnClickListener(this)
        ivProfileImg.setOnClickListener(this)

//        llFaq.setOnClickListener(this)
//        llMyAccount.setOnClickListener(this)
//        llHelp.setOnClickListener(this)
//        llFeedback.setOnClickListener(this)
//        llChatSettings.setOnClickListener(this)
//        llBrandsSettings.setOnClickListener(this)
//        llBlogSettings.setOnClickListener(this)
//        llLogout.setOnClickListener(this)
//        llInviteFriend.setOnClickListener(this)
//        llChangePassword.setOnClickListener(this)
//        llDeleteAccount.setOnClickListener(this)

        ivNoti.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        try {
            if (PreferenceFile.getInstance().getPreferenceData(
                    this@AppDrawer,
                    WebUrls.KEY_USER_FIRSTNAME
                ) != null
            ) {
//                tvUsername!!.text =
//                    PreferenceFile.getInstance()
//                        .getPreferenceData(this@AppDrawer, WebUrls.KEY_USER_FIRSTNAME)
//            } else {
//                tvUsername!!.text = ""
            }

            if (PreferenceFile.getInstance().getPreferenceData(
                    this@AppDrawer,
                    WebUrls.KEY_PROFILE_IMAGE
                ) != null
            ) {


//                Picasso.with(this@AppDrawer)
//                    .load(
//                        WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
//                            this@AppDrawer,
//                            WebUrls.KEY_PROFILE_IMAGE
//                        )
//                    )
//                    .placeholder(R.drawable.no_image).into(imgNavDrawer)

//                Picasso.with(this@AppDrawer)
//                    .load(
//                        WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
//                            this@AppDrawer,
//                            WebUrls.KEY_PROFILE_IMAGE
//                        )
//                    )
//                    .placeholder(R.drawable.no_image).into(imgNavDrawer)

            } else {
                imgNavDrawer!!.setImageResource(R.drawable.default_image)
                imgNavDrawer!!.setImageResource(R.drawable.default_image)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @SuppressLint("HardwareIds")
    private fun deleteUserDevice() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                val sharedPreferences = AppController.getIdPrefs()
                var postParam = JSONObject()

                postParam.put(
                    "user_id",
                    "" + PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("type", "android")
                postParam.put("device_token", sharedPreferences.getString("token", null))
                postParam.put(
                    "device_id",
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                )

                RetrofitService(
                    this, this, WebUrls.DELETE_USER, WebUrls.DELETE_USER_CODE, 3, postParam
                ).callService(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun logout() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                val postParam = JSONObject()
                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("status", "offline")
                Log.e("cbjksdsf", "" + postParam.toString())

                RetrofitService(
                    this, this, WebUrls.OFFLINE_ONLINE, WebUrls.OFFLINE_ONLINE_CODE,
                    3, postParam
                ).callService(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {
                WebUrls.OFFLINE_ONLINE_CODE -> {

                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        PreferenceFile.getInstance().logout()

                        if (DELETE_ACC) {
                            callDeleteAccount()
                        } else {
                            SharePref(this@AppDrawer).clearPref()
                            val intent = Intent(this, FirstLoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finishAffinity()
                        }
                    }
                }

                WebUrls.DELETE_USER_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        logout()
                    } else {
                        Log.e("sjmnvbdsvd", "wsklcnsdnvdfv")
                        logout()
                    }
                }

                WebUrls.DELETE_ACCOUNT_CODE -> {
                    val obj = JSONObject(response)
                    val message = obj.getString("data")
                    if (obj.getBoolean("response")) {

                        val intent = Intent(this, Startup::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finishAffinity()
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun customDialog2(context: Context, title: String, msg: String) {
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
            //  callDeleteAccount()
            deleteUserDevice()
            alertDialog.dismiss()
        }

        tvcancel.setOnClickListener {

            alertDialog.dismiss()
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    private fun callDeleteAccount() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                DELETE_ACC = true
                RetrofitService(
                    this, this, WebUrls.DELETE_ACCOUNT +
                            PreferenceFile.getInstance().getPreferenceData(
                                this,
                                WebUrls.KEY_USERID
                            ), WebUrls.DELETE_ACCOUNT_CODE, 1
                )
                    .callService(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }

    }

    //==================================================================================

    fun showProgressDialog() {
        if (dialog == null) {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
            dialog!!.setContentView(R.layout.layout_progress)
            dialog!!.show()
        } else if (!dialog!!.isShowing) {
            dialog!!.show()
        }
    }

    fun hideProgressDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    protected fun showToast(mToastMsg: String) {
        Toast.makeText(this, mToastMsg, Toast.LENGTH_LONG).show()
    }

//    protected void checkInvalidAuth(String mToastMsg) {
//        if (mToastMsg.equalsIgnoreCase("Invalid authentication.")) {
//            Intent intent = new Intent(mContext, LoginActivity.class);
//            // set the new task and clear flags
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }


    protected fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val d = cm.activeNetworkInfo != null
        if (!d) {
            showToast("Check your internet connection")
        }
        return cm.activeNetworkInfo != null
    }
}
