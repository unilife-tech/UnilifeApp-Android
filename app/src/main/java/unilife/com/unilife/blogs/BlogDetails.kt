//package unilife.com.unilife.blogs
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v4.content.ContextCompat
//import android.support.v4.graphics.drawable.DrawableCompat
//import android.util.Log
//import android.view.View
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_blog_details.*
//import kotlinx.android.synthetic.main.bottom_bar.*
//import org.json.JSONObject
//import unilife.com.unilife.brands.BrandsHome
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import android.annotation.SuppressLint
//import kotlinx.android.synthetic.main.activity_login.tvTitle
//import kotlinx.android.synthetic.main.back_icon_toolbar.*
//import unilife.com.unilife.Chat.ChatListing
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.collections.ArrayList
//
//
//class BlogDetails : AppCompatActivity(), View.OnClickListener, RetrofitResponse {
//
//    var getblogdetailslist: ArrayList<BlogDetailData> = ArrayList()
//
//    var arraylist: ArrayList<Blog.CategoriesBlogData> = ArrayList()
//    var position = 0
//    var value = ""
//    var blogId: String = ""
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_blog_details)
//
//        ivBlogs.setImageDrawable(
//            ContextCompat.getDrawable(
//                applicationContext, // Context
//                R.drawable.ic_blog_blue
//            )
//        )
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivEvent.drawable),
//            ContextCompat.getColor(this, R.color.medium_grey)
//        )
//
//        tvBlog.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//
//        ivNotification.visibility = View.GONE
//
//        setOnClickListner()
//
//        getIntentData()
//
//        getBlogDetails()
//
//        readblogservice()
//
//
//    }
//
//    private fun readblogservice() {
//        if(Alerts.isNetworkAvailable(this)){
//        val postParam = JSONObject()
//
//        try {
//
//            postParam.put(
//                "user_id",
//                PreferenceFile.getInstance().getPreferenceData(this@BlogDetails, WebUrls.KEY_USERID)
//            )
//            postParam.put("blog_id", blogId)
//            RetrofitService(
//                this, this, WebUrls.READ_BLOG,
//                WebUrls.READ_BLOG_CODE, 3, postParam
//            ).callService(true)
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//    }
//    else {
//        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//    }
//}
//
//
//fun getIntentData() {
//        blogId = intent.getStringExtra("blog_id")
//    }
//
//    override fun onResponse(requestCode: Int, response: String) {
//        try {
//
//            when (requestCode) {
//
//                WebUrls.SHOW_SINGLE_BLOG_DETAIL_CODE -> {
//
//                    val obj = JSONObject(response)
//
//                    scrollView.visibility = View.VISIBLE
//                    llBlogDEtails.visibility = View.VISIBLE
//
//                    if (obj.getBoolean("response")) {
//
//                        getblogdetailslist.clear()
//
//                        if (obj.has("data")) {
//
//                            val data = obj.getJSONObject("data")
//
//                            Log.e("scksdbs", "" + data.toString())
//
//                            if (data != null) {
//
//                                var blogDetailData = BlogDetailData()
//                                blogDetailData.id = data.getInt("id")
//                                blogDetailData.categories_id = data.getInt("categories_id")
//                                blogDetailData.title = data.getString("title")
//                                blogDetailData.description = data.getString("description")
//                                blogDetailData.image = data.getString("image")
//                                blogDetailData.video_link = data.getString("video_link")
//                                blogDetailData.shared_by = data.getString("shared_by")
//                                blogDetailData.slider = data.getString("slider")
//                                blogDetailData.status = data.getString("status")
//                                blogDetailData.created_at = data.getString("created_at")
//                                blogDetailData.updated_at = data.getString("updated_at")
//
//                                var userbloglike = data.getJSONArray("user_blog_like")
//
//                                if (userbloglike.length() > 0) {
//                       /*             for (j in 0 until userbloglike.length()) {
//
//                                          var userbloglikeobj = userbloglike.getJSONObject(j)
//                                        var userBlogLike = UserBlogLike()
//                                        userBlogLike.blog_id = userbloglikeobj.getInt("blog_id")
//                                        userBlogLike.id = userbloglikeobj.getInt("id")
//                                        userBlogLike.user_id = userbloglikeobj.getInt("user_id")
//                                        userBlogLike.created_at =
//                                            userbloglikeobj.getString("created_at")
//                                        userBlogLike.updated_at =
//                                            userbloglikeobj.getString("updated_at")
//
//                                        blogDetailData.userbloglikelist.add(userBlogLike)
//
//
//                                    }*/
//                                    blogDetailData.isLike = "Like"
//                                    btnLikeBlog.text = "Unlike"
//                                    //later on check
//
//
//
//                                } else {
//                                    blogDetailData.isLike = "Discard"
//                                    btnLikeBlog.text = "Like"
//
//                                }
//
//                                var userblogsave = data.getJSONArray("user_blog_saved")
//
//                                if (userblogsave.length() > 0) {
//                   /*                 for (j in 0 until userbloglike.length()) {
//
//                                        var userblogsaveobj = userbloglike.getJSONObject(j)
//                                        var userBlogSave = UserBlogSave()
//                                        userBlogSave.blog_id = userblogsaveobj.getInt("blog_id")
//                                        userBlogSave.id = userblogsaveobj.getInt("id")
//                                        userBlogSave.user_id = userblogsaveobj.getInt("user_id")
//                                        userBlogSave.created_at =
//                                            userblogsaveobj.getString("created_at")
//                                        userBlogSave.updated_at =
//                                            userblogsaveobj.getString("updated_at")
//
//                                        blogDetailData.userblogsavelist.add(userBlogSave)
//
//                                        btnSaveBlog.text = "Discard"
//                                    }*/
//
//                                    btnSaveBlog.text = "Discard"
//                                } else {
//                                    btnSaveBlog.text = "Save"
//                                }
//
//                                getblogdetailslist.add(blogDetailData)
//
//                                tvTitle.text = blogDetailData.title
//
//                            }
//
//
///*
//                            if (getblogdetailslist[0].isLike == "make_it_like") {
//                                btnLikeBlog.text = "Like"
//                            } else {
//                                btnLikeBlog.text = "Unlike"
//
//                            }
//
//                            Log.e("whatsthedata", "" + getblogdetailslist.size)*/
//                        }
//
//                        setdata()
//
//                    } else {
//                        Common.customDialog(this@BlogDetails, "Unilife", obj.getString("data"))
//                    }
//
//                }
//
//                WebUrls.LIKE_UNLIKE_BLOG_CODE -> {
//
//                    val obj = JSONObject(response)
//
//                    if (obj.getBoolean("response")) {
//
//                        var action = obj.getString("action")
//
//                        if (action == "like") {
//
//                            btnLikeBlog.text = "Unlike"
//
//                        } else {
//                            btnLikeBlog.text = "Like"
//                        }
//
//                    }
//                }
//
//                WebUrls.SAVE_BLOG_CODE -> {
//
//                    val obj = JSONObject(response)
//
//                    if (obj.getBoolean("response")) {
//
//                        var action = obj.getString("action")
//
//                        if (action == "saved") {
//
//                            btnSaveBlog.text = "Discard"
//
//                        } else {
//                            btnSaveBlog.text = "Save"
//                        }
//                    }
//
//
//                }
//                WebUrls.READ_BLOG_CODE -> {
//
//
//                    val obj = JSONObject(response)
//
//                    if (obj.getBoolean("response")) {
//
//                        Log.e("READBLOG","YES YOU ARE READING")
//                    }
//
//
//
//                }
//            }
//
//
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    @SuppressLint("SimpleDateFormat", "SetTextI18n")
//    fun setdata() {
//
//        tvTitle.text = getblogdetailslist[0].title
//
//        val source = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        source.timeZone = TimeZone.getTimeZone("UTC")
//        val target = SimpleDateFormat("MMM dd, yyyy ")
//        target.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
//
//        var datetime = target.format(source.parse(getblogdetailslist[0].created_at))
//
//        if (getblogdetailslist[0].image != null) {
//            Picasso.with(this).load(WebUrls.blogImageUrl + getblogdetailslist[0].image)
//                .placeholder(R.drawable.no_image).into(iv_bigimage)
//        } else {
//            iv_bigimage.setImageResource(R.drawable.no_image)
//        }
//
//        tv_title2.text = getblogdetailslist[0].title
//
//        if (getblogdetailslist[0].image != null) {
//            Picasso.with(this).load(WebUrls.blogImageUrl + getblogdetailslist[0].image)
//                .placeholder(R.drawable.no_image).into(ivuser)
//        } else {
//            ivuser.setImageResource(R.drawable.no_image)
//        }
//
//        tv_date.text = getblogdetailslist[0].shared_by + " , " + datetime
//
//        tv_videolink.text = getblogdetailslist[0].video_link
//
//    //    tv_desc_BD.text = Html.fromHtml(getblogdetailslist[0].description)
//
//        tv_desc_BD.loadData(getblogdetailslist[0].description,"text/html","UTF-8")
//
//        if (getblogdetailslist[0].userbloglikelist.size > 0) {
//
//
//        }
//
//    }
//
//
//    fun getBlogDetails() {
//        if(Alerts.isNetworkAvailable(this)){
//        val postParam = JSONObject()
//
//        try {
//
//            postParam.put(
//                "user_id",
//                PreferenceFile.getInstance().getPreferenceData(this@BlogDetails, WebUrls.KEY_USERID)
//            )
//            postParam.put("blog_id", blogId)
//            RetrofitService(
//                this, this, WebUrls.SHOW_SINGLE_BLOG_DETAIL,
//                WebUrls.SHOW_SINGLE_BLOG_DETAIL_CODE, 3, postParam
//            ).callService(true)
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//    }
//        else {
//            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//        }
//    }
//
//
//
//    fun likeBlog() {
//        if(Alerts.isNetworkAvailable(this)){
//        val postParam = JSONObject()
//
//        try {
//
//            postParam.put(
//                "user_id",
//                PreferenceFile.getInstance().getPreferenceData(this@BlogDetails, WebUrls.KEY_USERID)
//            )
//            postParam.put("blog_id", blogId)
//            RetrofitService(
//                this, this, WebUrls.LIKE_UNLIKE_BLOG,
//                WebUrls.LIKE_UNLIKE_BLOG_CODE, 3, postParam
//            ).callService(true)
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//
//    } else {
//            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//        }
//    }
//
//
//    fun saveBlog() {
//        if(Alerts.isNetworkAvailable(this)){
//        val postParam = JSONObject()
//
//        try {
//
//            postParam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this@BlogDetails, WebUrls.KEY_USERID))
//            postParam.put("blog_id", blogId)
//            RetrofitService(
//                this, this, WebUrls.SAVE_BLOG,
//                WebUrls.SAVE_BLOG_CODE, 3, postParam
//            ).callService(true)
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//
//    }
//        else {
//            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//        }
//    }
//
//
//
//    fun setOnClickListner() {
//        rlEvent.setOnClickListener(this)
//        rlChat.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        ivBackArrow.setOnClickListener(this)
//        btnSaveBlog.setOnClickListener(this)
//        btnLikeBlog.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View?) {
//
//        when (v?.id) {
//
//            R.id.ivBackArrow -> {
//                onBackPressed()
//            }
//
//            R.id.rlEvent -> {
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlChat -> {
//                val intent = Intent(this, ChatListing::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBrands -> {
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//            }
//
//            R.id.btnSaveBlog -> {
//                saveBlog()
//            }
//
//            R.id.btnLikeBlog -> {
//                likeBlog()
//            }
//
//
//            R.id.ivbackArrow -> {
//                super.onBackPressed()
//            }
//
//
//        }
//    }
//
//
//    data class BlogDetailData(
//        var id: Int = 0,
//        var categories_id: Int = 0,
//        var title: String = "",
//        var description: String = "",
//        var image: String = "",
//        var shared_by: String = "",
//        var video_link: String = "",
//        var slider: String = "",
//        var status: String = "",
//        var created_at: String = "",
//        var updated_at: String = "",
//        var isLike: String = "make_it_like",
//        var userbloglikelist: ArrayList<UserBlogLike> = ArrayList(),
//        var userblogsavelist: ArrayList<UserBlogSave> = ArrayList()
//    )
//
//    data class UserBlogLike(
//        var id: Int = 0,
//        var blog_id: Int = 0,
//        var user_id: Int = 0,
//        var created_at: String = "",
//        var updated_at: String = ""
//    )
//
//    data class UserBlogSave(
//        var id: Int = 0,
//        var blog_id: Int = 0,
//        var user_id: Int = 0,
//        var created_at: String = "",
//        var updated_at: String = ""
//
//    )
//
//}
