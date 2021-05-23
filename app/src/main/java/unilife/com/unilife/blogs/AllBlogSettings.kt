package unilife.com.unilife.blogs

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_allblog_settings.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.retrofit.WebUrls.SAVED_READ_BLOG_CODE
import unilife.com.unilife.retrofit.WebUrls.VIEW_LIKE_BLOG_CODE
import unilife.com.unilife.retrofit.WebUrls.VIEW_SAVED_BLOG_CODE
import unilife.com.unilife.retrofit.WebUrls.VIEW_SHARED_BLOG_CODE
import java.lang.Exception

class AllBlogSettings : AppCompatActivity(), RetrofitResponse {


    //    @BindView(R.id.rycBlog)
    var value = ""
    var savedBlogDataArrayList: ArrayList<BlogDataModel> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allblog_settings)

        setAdapter(savedBlogDataArrayList)

        ivNotification.visibility = View.GONE



        ivBackArrow.setOnClickListener {
            onBackPressed()
        }




        value = intent.getStringExtra("value")

        when (value) {
            "View Saved" -> {
                tvTitle.text = "View Saved Blogs"
                callViewSavedBlog()

            }
            "Saved Read" -> {
                tvTitle.text = "View Read Blogs"
                callSavedReadBlog()

            }
            "View Like" -> {
                tvTitle.text = "View Liked Blogs"
                callViewLikeBlog()

            }
            else -> {
                tvTitle.text = "View Shared Blogs"
                callViewSharedBlog()
            }
        }

    }


    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {

                VIEW_SAVED_BLOG_CODE -> {
                    Log.e("whatsyourresponse", "I'm Working")
                    savedBlogDataArrayList.clear()
                    val obj = JSONObject(response)
                    Log.e("I'm Working", obj.toString())
                    if (obj.getBoolean("response")) {
                        llBlogSettings1.visibility = View.VISIBLE
                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0){
                                Log.e("sizeget", "" + data.length())
                            for (i in 0 until data.length()) {
                                Log.e("sizegetNGVM", "" + i)
                                val dataobj = data.getJSONObject(i)
                                val blogSettingsModel = BlogDataModel()
                                blogSettingsModel.blog_id = dataobj.getString("blog_id")

                                val brandUserId = dataobj.getJSONObject("blog_user_saved")

                                blogSettingsModel.categories_id = brandUserId.getString("categories_id")
                                blogSettingsModel.blog_title = brandUserId.getString("title")
                                blogSettingsModel.blog_description = brandUserId.getString("description")
                                blogSettingsModel.blog_image = brandUserId.getString("image")
                                blogSettingsModel.blog_shared_by = brandUserId.getString("shared_by")
                                blogSettingsModel.blog_video_link = brandUserId.getString("video_link")
                                blogSettingsModel.blog_status = brandUserId.getString("status")

                                var userbloglike = brandUserId.getJSONArray("user_blog_like")

                                if (userbloglike.length() > 0) {
                                    for (j in 0 until userbloglike.length()) {
                                        var userbloglikeobj = userbloglike.getJSONObject(j)
                                        /*       var viewSavedUserLike = ViewSavedUserLike()
                                               viewSavedUserLike.blog_id = userbloglikeobj.getInt("blog_id")
                                               viewSavedUserLike.user_id = userbloglikeobj.getInt("user_id")
                                               viewSavedUserLike.id = userbloglikeobj.getInt("id")
                                               viewSavedLikelist.add(viewSavedUserLike)*/

                                    }
                                    blogSettingsModel.isLike = "make_it_dislike"
                                } else {
                                    blogSettingsModel.isLike = "make_it_like"
                                }

                                var userblogsaved = brandUserId.getJSONArray("user_blog_saved")
                                if (userblogsaved.length() > 0) {

                                    /*    for (k in 0 until userblogsaved.length()){
                                            var userblogsavedobj = userblogsaved.getJSONObject(k)
                                            var savedReadBlogData = SavedReadBlogData()
                                            savedReadBlogData.blog_id = userblogsavedobj.getInt("blog_id")
                                            savedReadBlogData.user_id =userblogsavedobj.getInt("user_id")
                                            savedReadBlogData.id =userblogsavedobj.getInt("id")
                                            savedReadBloglist.add(savedReadBlogData)

                                        }*/

                                    blogSettingsModel.isSaved = "make_it_discard"
                                } else {
                                    blogSettingsModel.isSaved = "make_it_save"

                                }
                                savedBlogDataArrayList.add(blogSettingsModel)

                            }

                                setAdapter(savedBlogDataArrayList)
                            Log.e("dataget", "" + savedBlogDataArrayList.toString())
                            Log.e("dataget", "" + savedBlogDataArrayList.size)
                        }
                    }

                    }

                }
                SAVED_READ_BLOG_CODE -> {
                    savedBlogDataArrayList.clear()
                    Log.e("whatsyourresponseRead", "I'm Working")
                    val obj = JSONObject(response)

                    if (obj.getBoolean("response")) {
                        llBlogSettings1.visibility = View.VISIBLE

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0){
                                for (i in 0 until data.length()) {

                                    val dataobj = data.getJSONObject(i)
                                    val blogSettingsModel = BlogDataModel()
                                    blogSettingsModel.blog_id = dataobj.getString("blog_id")

                                    val brandUserId = dataobj.getJSONObject("blog_user_read")
                                    if (brandUserId != null) {

                                        blogSettingsModel.categories_id = brandUserId.getString("categories_id")
                                        blogSettingsModel.blog_title = brandUserId.getString("title")
                                        blogSettingsModel.blog_description = brandUserId.getString("description")
                                        blogSettingsModel.blog_image = brandUserId.getString("image")
                                        blogSettingsModel.blog_shared_by = brandUserId.getString("shared_by")
                                        blogSettingsModel.blog_video_link = brandUserId.getString("video_link")
                                        blogSettingsModel.blog_status = brandUserId.getString("status")
                                    }
                                    else
                                    {
                                        Log.e("whatsyourresponseRead","nO data Found")
                                    }
                                    var userbloglike = brandUserId.getJSONArray("user_blog_like")

                                    if (userbloglike.length() > 0) {
                                        for (j in 0 until userbloglike.length()) {
                                            var userbloglikeobj = userbloglike.getJSONObject(j)
                                            /*       var viewSavedUserLike = ViewSavedUserLike()
                                               viewSavedUserLike.blog_id = userbloglikeobj.getInt("blog_id")
                                               viewSavedUserLike.user_id = userbloglikeobj.getInt("user_id")
                                               viewSavedUserLike.id = userbloglikeobj.getInt("id")
                                               viewSavedLikelist.add(viewSavedUserLike)*/

                                        }
                                        blogSettingsModel.isLike = "make_it_dislike"
                                    } else {
                                        blogSettingsModel.isLike = "make_it_like"
                                    }

                                    var userblogsaved = brandUserId.getJSONArray("user_blog_saved")
                                    if (userblogsaved.length() > 0) {

                                        /*    for (k in 0 until userblogsaved.length()){
                                            var userblogsavedobj = userblogsaved.getJSONObject(k)
                                            var savedReadBlogData = SavedReadBlogData()
                                            savedReadBlogData.blog_id = userblogsavedobj.getInt("blog_id")
                                            savedReadBlogData.user_id =userblogsavedobj.getInt("user_id")
                                            savedReadBlogData.id =userblogsavedobj.getInt("id")
                                            savedReadBloglist.add(savedReadBlogData)

                                        }*/

                                        blogSettingsModel.isSaved = "make_it_discard"
                                    } else {
                                        blogSettingsModel.isSaved = "make_it_save"

                                    }
                                    savedBlogDataArrayList.add(blogSettingsModel)

                                }
                                setAdapter(savedBlogDataArrayList)
                        }
                        }
                    }

                }

                VIEW_LIKE_BLOG_CODE -> {
                    savedBlogDataArrayList.clear()
                    Log.e("whatsyourresponse", "I'm Working")
                    val obj = JSONObject(response)
                    llBlogSettings1.visibility = View.VISIBLE

                    if (obj.getBoolean("response")) {


                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)
                                    val blogSettingsModel = BlogDataModel()
                                    blogSettingsModel.blog_id = dataobj.getString("blog_id")

                                    val brandUserId = dataobj.getJSONObject("blog_user_like")
                                    if (brandUserId != null){

                                        blogSettingsModel.categories_id = brandUserId.getString("categories_id")
                                    blogSettingsModel.blog_title = brandUserId.getString("title")
                                    blogSettingsModel.blog_description = brandUserId.getString("description")
                                    blogSettingsModel.blog_image = brandUserId.getString("image")
                                    blogSettingsModel.blog_shared_by = brandUserId.getString("shared_by")
                                    blogSettingsModel.blog_video_link = brandUserId.getString("video_link")
                                    blogSettingsModel.blog_status = brandUserId.getString("status")
                                }
                                    var userbloglike = brandUserId.getJSONArray("user_blog_like")

                                    if (userbloglike.length() > 0) {
                                        for (j in 0 until userbloglike.length()) {
                                            var userbloglikeobj = userbloglike.getJSONObject(j)
                                            /*       var viewSavedUserLike = ViewSavedUserLike()
                                                   viewSavedUserLike.blog_id = userbloglikeobj.getInt("blog_id")
                                                   viewSavedUserLike.user_id = userbloglikeobj.getInt("user_id")
                                                   viewSavedUserLike.id = userbloglikeobj.getInt("id")
                                                   viewSavedLikelist.add(viewSavedUserLike)*/

                                        }
                                        blogSettingsModel.isLike = "make_it_dislike"
                                    } else {
                                        blogSettingsModel.isLike = "make_it_like"
                                    }

                                    var userblogsaved = brandUserId.getJSONArray("user_blog_saved")
                                    if (userblogsaved.length() > 0) {

                                        /*    for (k in 0 until userblogsaved.length()){
                                                var userblogsavedobj = userblogsaved.getJSONObject(k)
                                                var savedReadBlogData = SavedReadBlogData()
                                                savedReadBlogData.blog_id = userblogsavedobj.getInt("blog_id")
                                                savedReadBlogData.user_id =userblogsavedobj.getInt("user_id")
                                                savedReadBlogData.id =userblogsavedobj.getInt("id")
                                                savedReadBloglist.add(savedReadBlogData)

                                            }*/

                                        blogSettingsModel.isSaved = "make_it_discard"
                                    } else {
                                        blogSettingsModel.isSaved = "make_it_save"

                                    }
                                    savedBlogDataArrayList.add(blogSettingsModel)

                                }

                                setAdapter(savedBlogDataArrayList)
                            } else {
                                //No Data
                            }
                        }

                    }
                }

                VIEW_SHARED_BLOG_CODE -> {
                    savedBlogDataArrayList.clear()
                    Log.e("whatsyourresponse", "I'm Working")
                    val obj = JSONObject(response)

                    if (obj.getBoolean("response")) {
                        llBlogSettings1.visibility = View.VISIBLE


                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)
                                    val blogSettingsModel = BlogDataModel()
                                    blogSettingsModel.blog_id = dataobj.getString("blog_id")

                                    val brandUserId = dataobj.getJSONObject("blog_user_shared")
                                    if (brandUserId != null){

                                        blogSettingsModel.categories_id = brandUserId.getString("categories_id")
                                        blogSettingsModel.blog_title = brandUserId.getString("title")
                                        blogSettingsModel.blog_description = brandUserId.getString("description")
                                        blogSettingsModel.blog_image = brandUserId.getString("image")
                                        blogSettingsModel.blog_shared_by = brandUserId.getString("shared_by")
                                        blogSettingsModel.blog_video_link = brandUserId.getString("video_link")
                                        blogSettingsModel.blog_status = brandUserId.getString("status")
                                    }
                                    var userbloglike = brandUserId.getJSONArray("user_blog_like")

                                    if (userbloglike.length() > 0) {
                                        for (j in 0 until userbloglike.length()) {
                                            var userbloglikeobj = userbloglike.getJSONObject(j)
                                            /*       var viewSavedUserLike = ViewSavedUserLike()
                                                   viewSavedUserLike.blog_id = userbloglikeobj.getInt("blog_id")
                                                   viewSavedUserLike.user_id = userbloglikeobj.getInt("user_id")
                                                   viewSavedUserLike.id = userbloglikeobj.getInt("id")
                                                   viewSavedLikelist.add(viewSavedUserLike)*/

                                        }
                                        blogSettingsModel.isLike = "make_it_dislike"
                                    } else {
                                        blogSettingsModel.isLike = "make_it_like"
                                    }

                                    var userblogsaved = brandUserId.getJSONArray("user_blog_saved")
                                    if (userblogsaved.length() > 0) {

                                        /*    for (k in 0 until userblogsaved.length()){
                                                var userblogsavedobj = userblogsaved.getJSONObject(k)
                                                var savedReadBlogData = SavedReadBlogData()
                                                savedReadBlogData.blog_id = userblogsavedobj.getInt("blog_id")
                                                savedReadBlogData.user_id =userblogsavedobj.getInt("user_id")
                                                savedReadBlogData.id =userblogsavedobj.getInt("id")
                                                savedReadBloglist.add(savedReadBlogData)

                                            }*/

                                        blogSettingsModel.isSaved = "make_it_discard"
                                    } else {
                                        blogSettingsModel.isSaved = "make_it_save"

                                    }
                                    savedBlogDataArrayList.add(blogSettingsModel)

                                }

                                setAdapter(savedBlogDataArrayList)
                            } else {
                                //No Data
                            }
                        }

                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun setAdapter(savedBlogDataArrayList: ArrayList<BlogDataModel>) {
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rycBlog?.layoutManager = staggeredGridLayoutManager
        val allBlogSettingsAdapter = AllBlogSettingsAdapter(this, value, savedBlogDataArrayList)
        rycBlog?.adapter = allBlogSettingsAdapter


        allBlogSettingsAdapter.setOnItemClickListener(object : AllBlogSettingsAdapter.onItemClickListener{
            override fun onItemClick(position: Int, blogList: ArrayList<BlogDataModel>?) {
                Log.e("asxnkzxbncx",""+blogList!!.size)
//                startActivity(Intent(this@AllBlogSettings,BlogDetails::class.java)
//                    .putExtra("blog_id", blogList!![position].blog_id)
//                )

            }
        })




    }

    override fun onBackPressed() {
        startActivity(Intent(this, BlogSettings::class.java))
    }

    fun callViewSavedBlog() {
        if(Alerts.isNetworkAvailable(this)){

        RetrofitService(
            this, this, WebUrls.VIEW_SAVED_BLOG + PreferenceFile.getInstance().getPreferenceData(
                this@AllBlogSettings, WebUrls.KEY_USERID
            ), VIEW_SAVED_BLOG_CODE, 1
        ).callService(true)

    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    fun callSavedReadBlog() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this, WebUrls.SAVED_READ_BLOG + PreferenceFile.getInstance().getPreferenceData(
                this@AllBlogSettings, WebUrls.KEY_USERID
            ), SAVED_READ_BLOG_CODE, 1
        ).callService(true)

    } else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    fun callViewLikeBlog() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this, WebUrls.VIEW_LIKE_BLOG + PreferenceFile.getInstance().getPreferenceData(
                this@AllBlogSettings, WebUrls.KEY_USERID
            ), VIEW_LIKE_BLOG_CODE, 1
        ).callService(true)

    }
    else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
}

    fun callViewSharedBlog() {
        if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this, WebUrls.VIEW_SHARED_BLOG + PreferenceFile.getInstance().getPreferenceData(
                this@AllBlogSettings, WebUrls.KEY_USERID
            ), VIEW_SHARED_BLOG_CODE, 1
        ).callService(true)

    }
else{
    Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
}
}
    /*  {
          data class ViewSavedBlogData(
              var id: Int = 0,
              var blog_id: Int = 0,
              var blogUSerSaved: JSONObject = JSONObject()
          )

          data class ViewSavedBlogUser(
              var categories_id: Int = 0,
              var title: String = "",
              var description: String = "",
              var image: String = "",
              var shared_by: String = "",
              var video_link: String = "",
              var slider: String = "",
              var status: String = "",
              var userbloglike: ArrayList<ViewSavedUserLike> = ArrayList(),
              var userblogsaved: ArrayList<SavedReadBlogData> = ArrayList(),
              var blog_categories: JSONObject = JSONObject()
          )

          data class ViewSavedUserLike(
              var id: Int = 0,
              var blog_id: Int = 0,
              var user_id: Int = 0
          )

          data class SavedReadBlogData(
              var id: Int = 0,
              var blog_id: Int = 0,
              var user_id: Int = 0

          )

          data class ViewSavedBlogCat(
              var id: String = "",
              var categories_name: String = "",
              var categories_image: String = "",
              var status: String = ""
          )

          *//*  data class ViewLikeBlogData(


    )

    data class ViewSharedBlogData(


    )*//*
    }*/
}
