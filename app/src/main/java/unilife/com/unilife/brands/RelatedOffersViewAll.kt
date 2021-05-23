//package unilife.com.unilife.brands
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.StaggeredGridLayoutManager
//import android.view.View
//import kotlinx.android.synthetic.main.activity_related_offers__view_all.*
//import kotlinx.android.synthetic.main.back_icon_toolbar.*
//import kotlinx.android.synthetic.main.bottom_bar.*
//import org.json.JSONObject
//import unilife.com.unilife.chat.Chat
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.R
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//import unilife.com.unilife.blogs.Blog
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import java.lang.Exception
//
//class RelatedOffersViewAll : AppCompatActivity(), RetrofitResponse , View.OnClickListener {
//
//    var relatedOfferList: ArrayList<RelatedOffersData> = ArrayList()
//    var offer_id = ""
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_related_offers__view_all)
//
//
//
//        tvTitle.text = "Related Offers"
//
//        getIntentData()
//        getallOffersService()
//        setOnClickListner()
//
//    }
//
//    private fun getIntentData() {
//        offer_id = intent.getStringExtra("offer_id")
//
//    }
//
//    private fun getallOffersService() {
//        if(Alerts.isNetworkAvailable(this)){
//        try {
//
//            RetrofitService(
//                this, this, WebUrls.VIEW_ALL_RELATED + offer_id,
//                WebUrls.VIEW_ALL_RELATED_CODE, 1
//            ).callService(true)
//
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//    }
//        else{
//            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//        }
//    }
//
//    override fun onResponse(requestCode: Int, response: String) {
//        try {
//            when (requestCode) {
//                WebUrls.VIEW_ALL_RELATED_CODE -> {
//                    val obj = JSONObject(response)
//                    if (obj.getBoolean("response")) {
//                        if (obj.has("brand")) {
//                            val brand = obj.getJSONObject("brand")
//                            if (brand != null) {
//                                val brand_offer = brand.getJSONArray("brand_offer")
//                                if (brand_offer.length() > 0) {
//                                    for (i in 0 until brand_offer.length()) {
//                                        val brand_offerobj = brand_offer.getJSONObject(i)
//                                        val relatedOffersData = RelatedOffersData()
//
//                                        relatedOffersData.brand_id = brand.getString("id")
//                                        relatedOffersData.brand_name = brand.getString("brand_name")
//
//                                        relatedOffersData.offer_id = brand_offerobj.getString("id")
//                                        relatedOffersData.image = brand_offerobj.getString("image")
//                                        relatedOffersData.discount_percent =
//                                            brand_offerobj.getString("discount_percent")
//
//                                        relatedOfferList.add(relatedOffersData)
//                                    }
//
//                                    setAdapter(relatedOfferList)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun setAdapter(relatedOfferList: ArrayList<RelatedOffersViewAll.RelatedOffersData>) {
//
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        rycViewAllRelated?.layoutManager = staggeredGridLayoutManager
//        val relatedVAAdapter = RelatedViewAllAdapter(this, relatedOfferList)
//        rycViewAllRelated.adapter = relatedVAAdapter
//
//    }
//
//
//    fun setOnClickListner() {
//        rlEvent.setOnClickListener(this)
//        rlChat.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        ivBackArrow.setOnClickListener(this)
//
//
//    }
//
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.rlEvent -> {
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//            R.id.rlChat -> {
//                val intent = Intent(this, Chat::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBrands -> {
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//                finish()
//            }
//            R.id.rlBlogs -> {
//                val intent = Intent(this, Blog::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//            R.id.ivBackArrow -> {
//                super.onBackPressed()
//                finish()
//            }
//        }
//    }
//
//        data class RelatedOffersData(
//            var brand_id: String = "",
//            var brand_name: String = "",
//
//            var offer_id: String = "",
//            var image: String = "",
//            var discount_percent: String = ""
//
//        )
//
//
//}