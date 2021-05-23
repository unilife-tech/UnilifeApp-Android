//package unilife.com.unilife.brands
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.text.Html
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_brands_redeemed_coupons.*
//import kotlinx.android.synthetic.main.activity_online_redeemed_coupons.*
//import kotlinx.android.synthetic.main.activity_online_redeemed_coupons.txtHeader
//import kotlinx.android.synthetic.main.back_icon_toolbar.*
//import kotlinx.android.synthetic.main.bottom_bar.*
//import org.json.JSONObject
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.R
//import unilife.com.unilife.blogs.Blog
//import unilife.com.unilife.brands.response.CategoriesBrand
//import unilife.com.unilife.chat.ChatListing
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//
//class OnlineRedeemedCoupons : AppCompatActivity(), View.OnClickListener, RetrofitResponse {
//
//    //    var singleofferList: ArrayList<SingleOfferData1> = ArrayList()
//    var offer_id = ""
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_online_redeemed_coupons)
//
////        DrawableCompat.setTint(
////            DrawableCompat.wrap(ivBrands.drawable),
////            ContextCompat.getColor(this, R.color.colorPrimary)
////        )
////        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
////        getIntentData()
//        setOnClickListner()
//        setData()
////        getOfferDetails()                            //service
////        ivNotification.visibility = View.GONE
//    }
//
//    override fun onResponse(requestCode: Int, response: String) {
//        try {
//            when (requestCode) {
////                WebUrls.GET_SINGLE_OFFER_CODE -> {
////                    val obj = JSONObject(response)
////                    llRedeemCoupons.visibility = View.VISIBLE
////
////                    if (obj.getBoolean("response")) {
////                        var singleOfferData = SingleOfferData1()
////                        if (obj.has("offers")) {
////                            val dataobj = obj.getJSONObject("offers")
////
////                            singleOfferData.brand_id = dataobj.getString("brand_id")
////                            singleOfferData.description = dataobj.getString("description")
////                            singleOfferData.discount_code = dataobj.getString("discount_code")
////                            singleOfferData.discount_percent = dataobj.getString("discount_percent")
////                            singleOfferData.id = dataobj.getString("id")
////                            singleOfferData.image = dataobj.getString("image")
////                            singleOfferData.term_condition = dataobj.getString("term_condition")
////                            singleOfferData.title = dataobj.getString("title")
////                            singleOfferData.type = dataobj.getString("type")
////
////                            // var offer_saved = dataobj.getJSONObject("offer_saved")
////
////                            if (dataobj.isNull("offer_saved")) {
////
////                                singleOfferData.isliked = "Save"
////                            } else {
////                                singleOfferData.isliked = "Discard"
////                            }
////                        }
////
////                        if (obj.has("brand")) {
////                            val brand = obj.getJSONObject("brand")
////                            if (brand != null) {
////                                singleOfferData.title = brand.getString("brand_name")
////                            }
////                        }
////                        singleofferList.add(singleOfferData)
////                        setData()
////                    }
////                }
//
//                WebUrls.SHARE_OFFER_CODE -> {
//                    val obj = JSONObject(response)
//
//
//                    if (obj.getBoolean("response")) {
//
//                        Log.e("sdjkdcssf", "I CAN FLy")
//
//                    }
//                }
//            }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
////    private fun getIntentData() {
////        //  arrayList = intent.getSerializableExtra("datalist") as ArrayList<BrandsRedeemedCoupons.SingleOfferData>
////        offer_id = intent.getStringExtra("offer_id")
////    }
//
//    @SuppressLint("SetTextI18n")
//    private fun setData() {
//        val categoriesBrand = intent.getParcelableExtra("item") as CategoriesBrand
//        offer_id = categoriesBrand.id
//        tvuser_id.text = categoriesBrand.brandOffer[0].discountPercent.toString() + "%"
//        txtHeader.text = categoriesBrand.brandName
//        offerOnline.text = categoriesBrand.brandOffer[0].discountCode
////        tvDescOnLINe.text = Html.fromHtml(categoriesBrand.brandOffer[0].description)
//        setHtmlText(categoriesBrand.brandOffer[0].description,tvDescOnLINe)
//        Picasso.with(this)
//            .load(
//                WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance().getPreferenceData(
//                    this,
//                    WebUrls.KEY_PROFILE_IMAGE
//                )
//            )
//            .placeholder(R.drawable.no_image).into(ivuser)
//
//        username.text = PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERNAME)
//        emailid.text = PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_EMAIL)
//    }
//
//    fun setHtmlText(string: String, textView: TextView) {
//        val spannedHtml = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT)
//        } else {
//            Html.fromHtml(string)
//        }
//        textView.text = spannedHtml.trim()
//    }
//
//
//    fun setOnClickListner() {
////        rlEvent.setOnClickListener(this)
////        rlChat.setOnClickListener(this)
////        rlBrands.setOnClickListener(this)
////        rlBlogs.setOnClickListener(this)
//        txtHeader.setOnClickListener(this)
//        sharebtn.setOnClickListener(this)
//        tvmanually.setOnClickListener(this)
//    }
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
//            R.id.txtHeader -> {
//                onBackPressed()
//            }
//            R.id.tvmanually -> {
//                offerOnline.visibility = View.VISIBLE
//            }
//            R.id.sharebtn -> {
//
//                callShareOfferService()
//
//                val share = Intent(Intent.ACTION_SEND)
//                share.type = "text/plain"
//                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
//
//                share.putExtra(Intent.EXTRA_SUBJECT, "Unilife")
//                share.putExtra(Intent.EXTRA_TEXT, "https://google.com/")
//
//                startActivity(Intent.createChooser(share, "Share link!"))
//            }
//
//        }
//    }
//
//    private fun callShareOfferService() {
//        if (Alerts.isNetworkAvailable(this)) {
//            val postParam = JSONObject()
//
//            try {
//
//                postParam.put(
//                    "user_id",
//                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
//                )
//                postParam.put("offer_id", offer_id)
//                RetrofitService(
//                    this, this, WebUrls.SHARE_OFFER,
//                    WebUrls.SHARE_OFFER_CODE, 3, postParam
//                ).callService(true)
//
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//
//        } else {
//            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
//        }
//    }
//
////    private fun getOfferDetails() {
////        if(Alerts.isNetworkAvailable(this)){
////        val postParam = JSONObject()
////
////        try {
////
////            postParam.put(
////                "user_id",
////                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
////            )
////            postParam.put("offer_id", offer_id)
////            RetrofitService(
////                this, this, WebUrls.GET_SINGLE_OFFER,
////                WebUrls.GET_SINGLE_OFFER_CODE, 3, postParam
////            ).callService(true)
////
////        } catch (ex: Exception) {
////            ex.printStackTrace()
////        }
////    }
////        else{
////            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
////        }
////    }
//
//    data class SingleOfferData1(
//        var id: String = "",
//        var brand_id: String = "",
//        var title: String = "",
//        var type: String = "",
//        var discount_percent: String = "",
//        var discount_code: String = "",
//        var description: String = "",
//        var term_condition: String = "",
//        var image: String = "",
//        var isliked: String = ""
//    )
//
//    fun goBack(view: View) {
//        finish()
//    }
//}
