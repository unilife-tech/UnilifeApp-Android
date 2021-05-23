package unilife.com.unilife.blogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_view_all_blogs.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.back_icon_toolbar.ivNotification
import kotlinx.android.synthetic.main.bottom_bar.*
import org.json.JSONObject
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class ViewAllBlogs : AppCompatActivity(), RetrofitResponse ,View.OnClickListener{


    var blogsList: ArrayList<BlogDataModel> = ArrayList()
    var value = ""
    var catId = ""

    val TAG = ViewAllBlogs::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_all_blogs)

        ivNotification.visibility = View.GONE

        getIntentData()

        viewAllBlogsAccToCat()
        setOnClickListener()

        ivBackArrow.setOnClickListener {
            startActivity(Intent(this, Blog::class.java))
        }

    }

    fun setOnClickListener() {

        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rlEvent->{
                val intent= Intent(this, Home::class.java)
                startActivity(intent)
            }
            R.id.rlChat->{
                val intent= Intent(this, ChatListing::class.java)
                startActivity(intent)
            }

            R.id.rlBlogs->{
                val intent= Intent(this,Blog::class.java)
                startActivity(intent)
            }

            R.id.rlBrands ->{
                val intent= Intent(this, BrandsHome::class.java)
                startActivity(intent)
            }
        }
    }
    fun getIntentData() {
        catId = intent.getStringExtra("category_id")
        Log.e(TAG, "CATID:" + catId)
    }

    fun setAdapter() {

        rycViewAll.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        val viewAllAdapter = ViewAllAdapter(this, blogsList)
        rycViewAll.adapter = viewAllAdapter

        viewAllAdapter.setOnItemClickListener(object : ViewAllAdapter.onItemClickListener {
            override fun onItemClick(position: Int, blogId: String) {

                Log.e(TAG, "BLOG_ID:"+blogId)

//                startActivity(Intent(this@ViewAllBlogs,BlogDetails::class.java)
//                    .putExtra("blog_id", blogId))

            }

        })

    }


    private fun viewAllBlogsAccToCat() {
    if(Alerts.isNetworkAvailable(this)){
        try {

            RetrofitService(
                this, this, WebUrls.VIEW_ALL_BLOGS_ACC_TO_CAT + catId,
                WebUrls.VIEW_ALL_BLOGS_ACC_TO_CAT_CODE, 1
            ).callService(true)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
}

    override fun onResponse(requestCode: Int, response: String) {

        try {

            when (requestCode) {

                WebUrls.VIEW_ALL_BLOGS_ACC_TO_CAT_CODE -> {

                    val obj = JSONObject(response)

                    Log.e(TAG, obj.toString())

                    if (obj.getBoolean("response")) {
                        clViewAllBlogs.visibility = View.VISIBLE
                        blogsList.clear()

                        val data = obj.getJSONArray("data")

                        if (data.length() > 0) {

                            for (j in 0 until data.length()) {

                                val blogsObj = data.getJSONObject(j)

                                val catblog = blogsObj.getJSONArray("categories_blog")

                                if (catblog.length() > 0) {

                                    for (k in 0 until catblog.length()) {

                                        var catblogobj = catblog.getJSONObject(k)

                                        val blogsData = BlogDataModel()

                                        blogsData.categories_id = blogsObj.getString("id")  // categoryId
                                        blogsData.categories_image = blogsObj.getString("categories_image")
                                        blogsData.categories_name = blogsObj.getString("categories_name")
                                        blogsData.blog_description = catblogobj.getString("description")
                                        blogsData.blog_id = catblogobj.getString("id")
                                        blogsData.blog_image = catblogobj.getString("image")
                                        blogsData.blog_title = catblogobj.getString("title")
                                        blogsData.blog_video_link = catblogobj.getString("video_link")
                                        blogsData.blog_shared_by = catblogobj.getString("shared_by")

                                        if (k == 0) {
                                            tvTitle.text = blogsData.categories_name
                                        }

                                        blogsList.add(blogsData)

                                        Log.e(TAG, "blogsList:inside:" + blogsList[k].blog_id)

                                    }
                                }
                            }

                            setAdapter()
                        }

                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}