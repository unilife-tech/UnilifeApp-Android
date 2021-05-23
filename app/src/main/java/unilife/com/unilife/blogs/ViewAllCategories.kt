package unilife.com.unilife.blogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_view_all_categories.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.bottom_bar.*
import org.json.JSONObject
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class ViewAllCategories : AppCompatActivity() , RetrofitResponse , View.OnClickListener{


    var bloglist : ArrayList<GetAllBlogs> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_categories)

        ivNotification.visibility = View.GONE
        tvTitle.text = "Categories"
        setonClick()
        setOnClickListner()
        callShowAllBlogsService()

    }

    fun setOnClickListner() {
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
                val intent= Intent(this, Chat::class.java)
                startActivity(intent)
            }

            R.id.rlBlogs->{
                val intent= Intent(this,Blog::class.java)
                startActivity(intent)
            }

            R.id.rlBrands ->{
                val intent= Intent(this,BrandsHome::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setonClick() {
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
    }


    override fun onResponse(requestCode: Int, response: String) {

        when(requestCode){

            WebUrls.SHOW_ALL_BLOGS_CODE->{

                val obj = JSONObject(response)
                Log.e("I'm Working", obj.toString())
                clAllCat.visibility=View.VISIBLE
                if (obj.getBoolean("response")) {

                    if (obj.has("data")) {

                        val data = obj.getJSONArray("data")
                        if(data.length()>0){

                            for(i in 0 until data.length() ){
                                var dataobj = data.getJSONObject(i)
                                var getAllBlogs = GetAllBlogs()
                                getAllBlogs.id=dataobj.getString("id")
                                getAllBlogs.categories_image=dataobj.getString("categories_image")
                                getAllBlogs.categories_name = dataobj.getString("categories_name")

                                bloglist.add(getAllBlogs)
                            }
                            setAdapter(bloglist)
                        }
                    }
                }

            }
        }
    }


    private fun setAdapter(bloglist: ArrayList<GetAllBlogs>) {

        rycAllBlogs.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        val viewAllAdapter = ViewAllCategoriesAdapter(this,bloglist)
        rycAllBlogs.adapter = viewAllAdapter

        viewAllAdapter.setOnItemClickListener(object : ViewAllCategoriesAdapter.onItemClickListener{
            override fun onItemClick(position: Int, blogList: ArrayList<GetAllBlogs>) {
                Log.e("asxnkzxbncx",""+blogList!!.size)
                startActivity(Intent(this@ViewAllCategories,ViewAllBlogs::class.java)
                    .putExtra("category_id",blogList[position].id)
                )

            }
        })
    }


    fun callShowAllBlogsService(){

        RetrofitService(this, this, WebUrls.SHOW_ALL_BLOGS ,WebUrls.SHOW_ALL_BLOGS_CODE,1)
            .callService(true)

    }



    data class GetAllBlogs(
        var id : String= "",
        var categories_name : String= "",
        var categories_image : String= ""
    )




}
