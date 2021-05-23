//package unilife.com.unilife.brands
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.text.Html
//import android.view.View
//import android.widget.TextView
//import android.widget.Toast
//import com.google.android.exoplayer2.util.Log
//import com.vincent.filepicker.ToastUtil
//import kotlinx.android.synthetic.main.activity_redeemed_coupons.*
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
//class RevelRedeemedCoupons : AppCompatActivity(), View.OnClickListener, RetrofitResponse {
//    var offer_id = ""
//    lateinit var categoriesBrand2: CategoriesBrand
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_redeemed_coupons)
//
//        getIntentData()
//        setOnClickListner()
//    }
//
//    private fun getIntentData() {
//        val categoriesBrand = intent.getParcelableExtra("item") as CategoriesBrand
//        categoriesBrand2 = categoriesBrand
//        offer_id = categoriesBrand.id
//        txtHeaderReveel.text = categoriesBrand.brandName
//        couponcode.text = categoriesBrand.brandOffer[0].discountCode
//        setHtmlText(categoriesBrand.brandOffer[0].description, tvDesc)
//
//        if (categoriesBrand.brandOffer[0].link == "Save") {
//            tvSaveCode.text = "Discard"
//        } else {
//            tvSaveCode.text = "Save"
//        }
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
//    override fun onResponse(requestCode: Int, response: String) {
//        try {
//            when (requestCode) {
//                WebUrls.SAVE_OFFER_CODE -> {
//                    val obj = JSONObject(response)
//                    Log.e("SAVE_OFFER_CODE", "  " + obj.toString())
//
//                    if (obj.getBoolean("response")) {
//                        val action = obj.getString("action")
//                        ToastUtil.getInstance(this).showToast(obj.getString("message"))
//                        if (action == "saved") {
//                            tvSaveCode.text = "Discard"
//                        } else {
//                            tvSaveCode.text = "Save"
//                        }
//                    }
//                }
//
//                WebUrls.GET_SINGLE_OFFER_CODE -> {
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    fun setOnClickListner() {
////        rlEvent.setOnClickListener(this)
////        rlChat.setOnClickListener(this)
////        rlBlogs.setOnClickListener(this)
////        rlBrands.setOnClickListener(this)
//        tvSaveCode.setOnClickListener(this)
////        ivBackArrow.setOnClickListener(this)
//        tv_redeemedCoupon.setOnClickListener(this)
//
//    }
//
//
//    @SuppressLint("SetJavaScriptEnabled")
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
//                finish()
//            }
//            R.id.rlBlogs -> {
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//                finish()
//            }
//            R.id.tvSaveCode -> {
//                saveOfferService()
//            }
//            R.id.ivBackArrow -> {
//                onBackPressed()
//            }
//            R.id.tv_redeemedCoupon -> {
//               if (categoriesBrand2.brandOffer[0].link!=null) {
//                   val openURL = Intent(Intent.ACTION_VIEW)
//                   openURL.data = Uri.parse(/*"https://"*/categoriesBrand2.brandOffer[0].link)
//                   startActivity(openURL)
//               }else{
//                   ToastUtil.getInstance(this).showToast("Invalid link")
//               }
//            }
//        }
//    }
//
//    fun saveOfferService() {
//        if (Alerts.isNetworkAvailable(this)) {
//            val postParam = JSONObject()
//            try {
//                postParam.put(
//                    "user_id",
//                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
//                )
//                postParam.put("offer_id", offer_id)
//                RetrofitService(
//                    this, this, WebUrls.SAVE_OFFER,
//                    WebUrls.SAVE_OFFER_CODE, 3, postParam
//                ).callService(true)
//
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//        } else {
//            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
//        }
//    }
//
//
//    fun goBack(view: View) {
//        onBackPressed()
//    }
//
//}
