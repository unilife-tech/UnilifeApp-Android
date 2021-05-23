//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Build
//import android.os.Bundle
//import android.support.v4.content.ContextCompat
//import android.support.v7.app.AlertDialog
//import android.support.v7.app.AppCompatActivity
//import android.text.Html
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.WindowManager
//import android.widget.TextView
//import com.bumptech.glide.Glide
//import kotlinx.android.synthetic.main.activity_brands_redeemed_coupons.*
//import kotlinx.android.synthetic.main.custom_dialog.view.*
//import org.json.JSONObject
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.R
//import unilife.com.unilife.brands.response.CategoriesBrand
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import unilife.com.unilife.retrofit.WebUrls.brandImageUrl
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//
//class BrandDetailsActivity : AppCompatActivity(), View.OnClickListener, RetrofitResponse {
//
//    lateinit var categoriesBrand2: CategoriesBrand
//    var offer_id = ""
//    var name = ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_brands_redeemed_coupons)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        getIntentData()
//        setOnClickListner()
//        callReadOfferService()
//        init()
//    }
//
//    fun init() {
//        tvInStoreRevelCode.visibility = View.GONE
//        tvRevelCode.visibility = View.VISIBLE
//    }
//
//    private fun callReadOfferService() {
//        if (Alerts.isNetworkAvailable(this)) {
//            val postParam = JSONObject()
//
//            try {
//                postParam.put(
//                    "user_id",
//                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
//                )
//                postParam.put("offer_id", offer_id)
//                RetrofitService(
//                    this, this, WebUrls.READ_OFFER,
//                    WebUrls.READ_OFFER_CODE, 3, postParam
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
//    override fun onResponse(requestCode: Int, response: String) {
//        try {
//            when (requestCode) {
//                WebUrls.READ_OFFER_CODE -> {
//                    val obj = JSONObject(response)
//                    if (obj.getBoolean("response")) {
//                        Log.e("SDFdfgrgggs", "i can fly")
//                    }
//                }
//                WebUrls.REDEEM_OFFER_CODE -> {
//                    val obj = JSONObject(response)
//                    if (obj.getBoolean("response")) {
//
//                        if (obj.has("message")) {
//                            var message = obj.getString("message")
//                            //   customDialog(this,"Unilife",message)
//                            val intent = Intent(this, RevelRedeemedCoupons::class.java)
//                            intent.putExtra("item", categoriesBrand2)
//                            startActivity(intent)
//                        }
//                    }
//                }
//            }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun getIntentData() {
//        val categoriesBrand = intent.getParcelableExtra("item") as CategoriesBrand
//        categoriesBrand2 = categoriesBrand
//        txtHeader.text = categoriesBrand.brandName
//        offer_id = categoriesBrand.id
//        name = categoriesBrand.brandName
//        Glide
//            .with(this)
//            .load(brandImageUrl + categoriesBrand.image)
//            .into(ivImage)
//
//        setHtmlText(categoriesBrand.brandOffer[0].description, tvDesc)
//        setHtmlText(categoriesBrand.brandOffer[0].termCondition, tvTermsDesc)
//        setHtmlText(categoriesBrand.brandName, txtHeader)
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
//    fun setOnClickListner() {
//        tvRevelCode.setOnClickListener(this)
//        tvInStoreRevelCode.setOnClickListener(this)
//        llInStore.setOnClickListener(this)
//        llOnline.setOnClickListener(this)
//        btnTerms.setOnClickListener(this)
//        tvRecentDiscount.setOnClickListener(this)
//
//    }
//
//    fun redeemInlineCoupon() {
//        if (Alerts.isNetworkAvailable(this)) {
//            try {
//                val postParam = JSONObject()
//                postParam.put(
//                    "user_id",
//                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
//                )
//                postParam.put("type", "instore")
//                postParam.put("coupon_id", offer_id)
//
//                RetrofitService(
//                    this, this, WebUrls.REDEEM_OFFER, WebUrls.REDEEM_OFFER_CODE,
//                    3, postParam
//                ).callService(true)
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//            }
//        } else {
//            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
//        }
//    }
//
//    fun customDialog(context: Context, title: String, msg: String) {
//        val dialogBuilder = AlertDialog.Builder(context)
//        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)
//
//        val tvTitle = dialogView.tvTitle
//        val tvOk = dialogView.tvok
//        val tvMsg = dialogView.tvMsg
//
//        dialogBuilder.setView(dialogView)
//
//        val alertDialog = dialogBuilder.create()
//        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val width = WindowManager.LayoutParams.WRAP_CONTENT
//        val height = WindowManager.LayoutParams.WRAP_CONTENT
//        alertDialog.window!!.setLayout(width, height)
//
//        tvTitle.text = title
//        tvMsg.text = msg
//
//        tvOk.setOnClickListener(View.OnClickListener {
//            alertDialog.dismiss()
//            finish()
//        })
//
//        alertDialog.setCancelable(true)
//        alertDialog.setCanceledOnTouchOutside(true)
//        alertDialog.show()
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//
//            R.id.ivbackArrow -> {
//                super.onBackPressed()
//            }
//            R.id.btnTerms -> {
//
//            }
//
//            R.id.tvInStoreRevelCode -> {
//                val intent = Intent(this, OnlineRedeemedCoupons::class.java)
//                intent.putExtra("item", categoriesBrand2)
//                startActivity(intent)
//            }
//            R.id.tvRecentDiscount -> {
//                val intent = Intent(this, RelatedOffersViewAll::class.java)
//                    .putExtra("offer_id", offer_id)
//                startActivity(intent)
//            }
//
//            R.id.llInStore -> {
//                tvInStoreRevelCode.visibility = View.VISIBLE
//                tvRevelCode.visibility = View.GONE
//                llOnline.setBackgroundColor(Color.WHITE)
//                tvOnline.setTextColor(ContextCompat.getColor(this, R.color.medium_black))
//                llInStore.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                tvInStore.setTextColor(ContextCompat.getColor(this, R.color.white))
//            }
//
//            R.id.llOnline -> {
//                tvInStoreRevelCode.visibility = View.GONE
//                tvRevelCode.visibility = View.VISIBLE
//                llOnline.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                tvOnline.setTextColor(ContextCompat.getColor(this, R.color.white))
//                llInStore.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//
//                tvInStore.setTextColor(ContextCompat.getColor(this, R.color.medium_black))
//            }
//
//            R.id.tvRevelCode -> {
//                redeemInlineCoupon()
//            }
//        }
//    }
//
//    data class BrandOffers(
//        var brand_name: String = "",
//        var brand_id: String = "",
//        var categories_id: String = "",
//        var id: String = "",
//        var image: String = "",
//        var discount_percent: String = ""
//    )
//
//    fun goBack(view: View) {
//        onBackPressed()
//    }
//}
