package unilife.com.unilife.blogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.bottom_bar.*
import org.json.JSONObject
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.brands.SearchResultBrandAdapter
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class SearchResult : AppCompatActivity() , RetrofitResponse , View.OnClickListener{

    var searchtext = ""
    var searchlist : ArrayList<SearchResultData> =  ArrayList()
    var searchlistB : ArrayList<SearchResultBrandData> =  ArrayList()
    var value = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        ivNotification.visibility = View.GONE
        getIntentData()


        setOnClickListener()
        if(value == "Blogs") {
            tvTitle.text = "Search Blogs Result"
            callSearchBlogService()
        }
        else
        {
            tvTitle.text = "Search Brands Result"
            callBrandSearchService()
        }
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }

    }



    private fun setOnClickListener() {
        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rlEvent -> {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            R.id.rlChat -> {
                val intent = Intent(this, Chat::class.java)
                startActivity(intent)
            }

            R.id.rlBrands -> {
                val intent = Intent(this, BrandsHome::class.java)
                startActivity(intent)
                finish()

            }
            R.id.rlBlogs -> {
                val intent = Intent(this, Blog::class.java)
                startActivity(intent)
                finish()

            }


        }
    }

    fun getIntentData(){
        searchtext = intent.getStringExtra("search")
        value = intent.getStringExtra("value")
    }

    private fun callSearchBlogService() {
    if(Alerts.isNetworkAvailable(this)){
        var postparam = JSONObject()
        postparam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this@SearchResult, WebUrls.KEY_USERID))
        postparam.put("search", searchtext)

        RetrofitService(
            this, this, WebUrls.BASE_URL + WebUrls.SEARCH_BLOG,
            WebUrls.SEARCH_BLOG_CODE, 3, postparam
        ).callService(true)

    }
    else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
    }

    private fun callBrandSearchService() {
        if(Alerts.isNetworkAvailable(this)){
        var postparam = JSONObject()
        postparam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this@SearchResult, WebUrls.KEY_USERID))
        postparam.put("search", searchtext)

        RetrofitService(
            this, this, WebUrls.BASE_URL + WebUrls.SEARCH_BRAND,
            WebUrls.SEARCH_BRAND_CODE, 3, postparam
        ).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    fun setAdapter(searchlist: ArrayList<SearchResultData>) {

        ryc_SearchResult.layoutManager = GridLayoutManager(this, 2)
        val searchadapter = SearchResultAdapter(this,searchlist)
        ryc_SearchResult.adapter = searchadapter


    }

    private fun setAdapter2(searchlistB: ArrayList<SearchResult.SearchResultBrandData>) {

        ryc_SearchResult.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        val searchadapter = SearchResultBrandAdapter(this,searchlistB)
        ryc_SearchResult.adapter = searchadapter

    }


    override fun onResponse(requestCode: Int, response: String) {
        try {


            when (requestCode) {
                WebUrls.SEARCH_BLOG_CODE -> {
                    val obj = JSONObject(response)


                    Log.e("adjkl", obj.toString())


                    if (obj.getBoolean("response")) {
                        Log.e("dhasjkbksc", "ARe u Working ?")
                        val data = obj.getJSONArray("data")
                        if(data.length()>0){
                            for(i in 0 until data.length()){
                                val dataobj = data.getJSONObject(i)
                                val searchDataModel = SearchResultData()
                                searchDataModel.categories_id=dataobj.getString("categories_id")
                                searchDataModel.description=dataobj.getString("description")
                                searchDataModel.id=dataobj.getString("id")
                                searchDataModel.image=dataobj.getString("image")
                                searchDataModel.title=dataobj.getString("title")

                                searchlist.add(searchDataModel)
                            }
                            setAdapter(searchlist)
                        }
                        else {
                         Toast.makeText(this,"Data Not Found",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                WebUrls.SEARCH_BRAND_CODE -> {
                    val obj = JSONObject(response)


                    Log.e("adjkl", obj.toString())


                    if (obj.getBoolean("response")) {
                        Log.e("dhasjkbksc", "ARe u Working ?")
                        val data = obj.getJSONArray("data")
                        if(data.length()>0){
                            for(i in 0 until data.length()){
                                val dataobj = data.getJSONObject(i)
                                val searchDataBrandModel = SearchResultBrandData()
                                searchDataBrandModel.brand_id=dataobj.getString("brand_id")
//                                searchDataBrandModel.brand_name=dataobj.getString("title")
//                                searchDataBrandModel.brand_desc=dataobj.getString("description")
                                searchDataBrandModel.discount_type=dataobj.getString("discount_type")
                                searchDataBrandModel.id=dataobj.getString("id")
                                searchDataBrandModel.image=dataobj.getString("image")
                                searchDataBrandModel.discount_percent=dataobj.getString("discount_percent")


                                searchlistB.add(searchDataBrandModel)
                            }
                            setAdapter2(searchlistB)
                        }
                        else {

                         Toast.makeText(this,"Data Not Found",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }



    data class SearchResultData(
        var id : String = "",
        var categories_id : String = "",
        var title : String = "",
        var description : String = "",
        var image : String = ""
    )
    data class SearchResultBrandData(
        var id : String = "",
        var brand_id : String = "",
//        var brand_name : String = "",
//        var brand_desc : String = "",
        var discount_percent : String = "",
        var discount_type : String = "",
        var image : String = ""
    )
}
