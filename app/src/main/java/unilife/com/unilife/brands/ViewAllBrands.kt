package unilife.com.unilife.brands

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_view_all_brands.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.bottom_bar.*
import org.json.JSONObject
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class ViewAllBrands : AppCompatActivity() , RetrofitResponse , View.OnClickListener {
    var catId = ""
    var brandCatList : ArrayList<BrandsCatData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_brands)

            ivNotification.visibility = View.GONE

            tvTitle.text = "Categories"

        setOnClickListener()
        DrawableCompat.setTint(
            DrawableCompat.wrap(ivBrands.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )
   //     getIntentData()
        viewAllBrandsAccToCat()
    }




    override fun onResponse(requestCode: Int, response: String) {
        try {
            when(requestCode){
                WebUrls.GET_OFFER_CAT_CODE ->{
                    val obj = JSONObject(response)
                    clViewAllBrands.visibility=View.VISIBLE
                    Log.e("I'm Working", obj.toString())
                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if(data.length()>0){

                                for(i in 0 until data.length() ){
                                    var dataobj = data.getJSONObject(i)
                                    var brandsCatData = BrandsCatData()
                                    brandsCatData.id=dataobj.getString("id")
                                    brandsCatData.image=dataobj.getString("image")
                                    brandsCatData.name = dataobj.getString("name")

                                    brandCatList.add(brandsCatData)
                                }
                                setAdapter(brandCatList)
                            }
                        }
                    }


                }
            }

        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }


    fun setOnClickListener() {

        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)

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
                val intent= Intent(this, Blog::class.java)
                startActivity(intent)
            }

            R.id.rlBrands ->{
                val intent= Intent(this, BrandsHome::class.java)
                startActivity(intent)
            }
            R.id.ivBackArrow ->{
                super.onBackPressed()
            }
        }
    }
    fun getIntentData() {
        catId = intent.getStringExtra("category_id")
    }


    fun setAdapter(brandCatList: ArrayList<BrandsCatData>) {

        rycViewAllBrands.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
        val viewAllAdapter = ViewAllBrandsAdapter(this, brandCatList)
        rycViewAllBrands.adapter = viewAllAdapter
    }

    private fun viewAllBrandsAccToCat() {
        if(Alerts.isNetworkAvailable(this)){
        try {

            RetrofitService(
                this, this, WebUrls.GET_OFFER_CAT,
                WebUrls.GET_OFFER_CAT_CODE, 1
            ).callService(true)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    data class BrandsCatData(
        var id : String = "",
        var image : String = "",
        var name : String = ""

    )

}
