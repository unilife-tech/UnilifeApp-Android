//package unilife.com.unilife.brands
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v4.content.ContextCompat
//import android.support.v4.graphics.drawable.DrawableCompat
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.StaggeredGridLayoutManager
//import android.util.Log
//import android.view.View
//import kotlinx.android.synthetic.main.bottom_bar.*
//import kotlinx.android.synthetic.main.tranding_offers.*
//import org.json.JSONObject
//import unilife.com.unilife.chat.ChatListing
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.blogs.Blog
//import unilife.com.unilife.R
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import java.lang.Exception
//
//class TrendingOffers : AppCompatActivity(), RetrofitResponse, View.OnClickListener {
//
//    var name = ""
//    //    @BindView(R.id.rycCoupans)
//    var arrayOffer: ArrayList<OffersCat> = ArrayList()
//    var pos = 0
//    var offer_id = ""
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.tranding_offers)
//
//        getIntentData()
//        setOnClickListner()
//        callshowoffers()
//
//        Title.text = name
//        ivbackArrow.setOnClickListener {
//            onBackPressed()
//        }
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivBrands.drawable),
//            ContextCompat.getColor(this, R.color.colorPrimary)
//        )
//
//        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//
//    }
//
//    private fun getIntentData() {
//        offer_id = intent.getStringExtra("offer_id")
//        name = intent.getStringExtra("name")
//    }
//
//    override fun onResponse(requestCode: Int, response: String) {
//
//        try {
//            when (requestCode) {
//
//                WebUrls.SHOW_OFFERS_ACC_CAT_CODE -> {
//
//                    val obj = JSONObject(response)
//                    rlTrendingOffers.visibility = View.VISIBLE
//                    Log.e("BLOG_DATA_CODE", obj.toString())
//
//                    if (obj.getBoolean("response")) {
//
//                        val data = obj.getJSONArray("data")
//                        if (data.length() > 0) {
//                            for (i in 0 until data.length()) {
//                                val dataobj = data.getJSONObject(i)
//                                var offersCat = OffersCat()
//
//                                offersCat.brand_id = dataobj.getString("id")
//
//                                offersCat.discount_percent = dataobj.getString("discount_percent")
//                                offersCat.image = dataobj.getString("image")
//
//                                var brand_name_data = dataobj.getJSONObject("brand_name_data")
//                                offersCat.name = brand_name_data.getString("brand_name")
//
//                                arrayOffer.add(offersCat)
//
//                            }
//                            setAdapter(arrayOffer)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun setAdapter(arrayOffer: ArrayList<OffersCat>) {
//
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        rycOffers?.layoutManager = staggeredGridLayoutManager
//        val trandinBrandsAdapter = TrendinBrandsAdapter(this@TrendingOffers, arrayOffer)
//        rycOffers?.adapter = trandinBrandsAdapter
//    }
//
//    private fun callshowoffers() {
//        if (Alerts.isNetworkAvailable(this)) {
//            RetrofitService(
//                this, this, WebUrls.SHOW_OFFERS_ACC_CAT + offer_id, WebUrls.SHOW_OFFERS_ACC_CAT_CODE, 1
//            ).callService(true)
//
//        } else {
//            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
//        }
//    }
//
//    /*     try {
//
//             DrawableCompat.setTint(
//                     DrawableCompat.wrap(ivBrands.getDrawable()),
//                     ContextCompat.getColor(this, R.color.colorPrimary)
//             );
//             tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//             DrawableCompat.setTint(
//                     DrawableCompat.wrap(ivEvent.getDrawable()),
//                     ContextCompat.getColor(this, R.color.medium_grey)
//             )
//
//             val homeModel = intent.getSerializableExtra("list") as? HomeModel
//             var brandOffersList: ArrayList<HomeModel.BrandOffers>? = homeModel!!.brandOffersList
//
//             val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//             rycOffers?.layoutManager = staggeredGridLayoutManager
//             val trandinBrandsAdapter = TrendinBrandsAdapter(this@TrendingOffers, brandOffersList)
//             rycOffers?.adapter = trandinBrandsAdapter
//
//             trandinBrandsAdapter.setOnItemClickListener(object : TrendinBrandsAdapter.onItemClickListener {
//                 override fun onItemClick(position: Int, brandOffersList: ArrayList<HomeModel.BrandOffers>?) {
//                     try {
//                         startActivity(Intent(this@TrendingOffers, BrandsRedeemedCoupons::class.java)
//                                 .putExtra("list", brandOffersList!!.get(position))
//                                 .putExtra("homeModel", homeModel)
//                         )
//                     }catch (e:Exception){
//                         e.printStackTrace()
//                     }
//                 }
//             })
//
//             setOnClickListner()
//
//         }catch (e:Exception){
//             e.printStackTrace()
//         }*/
//
//
//    fun setOnClickListner() {
//        rlEvent.setOnClickListener(this)
//        rlChat.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        ivbackArrow.setOnClickListener(this)
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
//                val intent = Intent(this, ChatListing::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlBlogs -> {
//                val intent = Intent(this, Blog::class.java)
//                startActivity(intent)
//            }
//            R.id.rlBrands -> {
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//            }
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
//    data class OffersCat(
//        var name: String = "",
//        var image: String = "",
//        var brand_id: String = "",
//        var discount_percent: String = ""
//    )
//}
