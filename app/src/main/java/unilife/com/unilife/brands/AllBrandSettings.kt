package unilife.com.unilife.brands

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_all_brand_settings.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class AllBrandSettings : AppCompatActivity(), RetrofitResponse {


    var value = ""
    var brandDataArrayList: ArrayList<BrandSettingsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_brand_settings)

        //     setAdapter(brandDataArrayList)

        ivNotification.visibility = View.GONE



        ivBackArrow.setOnClickListener {
            onBackPressed()
        }


        value = intent.getStringExtra("value")

        when (value) {
            "View Brands" -> {
                tvTitle.text = "View Brand Coupons"
                callViewBrand()

            }
            "View Saved" -> {
                tvTitle.text = "Saved Brand Coupons"
                callViewSavedBrand()

            }
            "View Redeemed" -> {
                Log.e("VDgsdgdgv", "sddgvsdgge")
                tvTitle.text = "Redeemed Brand Coupons"
                callRedeemedBrand()

            }
            else -> {
                tvTitle.text = "Shared Brand Coupons"
                sharedViewBrand()
            }
        }


    }

    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {
                WebUrls.LIST_VIEW_BRANDS_CODE -> {

                    val obj = JSONObject(response)

                    llAllbrand.visibility = View.VISIBLE
                    Log.e("LIST_VIEW_BRANDS_CODE", obj.toString())
                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)

                                    val offer_user_view = dataobj.getJSONObject("offer_user_view")
                                    if (offer_user_view != null) {
                                        val brandSettingsData = BrandSettingsData()
                                        brandSettingsData.brand_id = offer_user_view.getString("brand_id")
                                        brandSettingsData.id = offer_user_view.getString("id")
                                        brandSettingsData.type = offer_user_view.getString("type")
                                        brandSettingsData.image = offer_user_view.getString("image")
                                        brandSettingsData.discount_code = offer_user_view.getString("discount_code")

                                        val brand_name_data = offer_user_view.getJSONObject("brand_name_data")
                                        if (brand_name_data != null) {
                                            brandSettingsData.title = brand_name_data.getString("brand_name")

                                        }
                                        brandDataArrayList.add(brandSettingsData)
                                    }
                                    setAdapter(brandDataArrayList, value)
                                }
                            } else {
                                Common.customDialog(this, "Unilife", "No data found")

                            }
                        }
                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }


                WebUrls.LIST_SHARED_OFFER_CODE -> {

                    val obj = JSONObject(response)
                    llAllbrand.visibility = View.VISIBLE

                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)

                                    val offer_user_view = dataobj.getJSONObject("offer_user_shared")
                                    if (offer_user_view != null) {
                                        val brandSettingsData = BrandSettingsData()
                                        brandSettingsData.brand_id = offer_user_view.getString("brand_id")
                                        brandSettingsData.id = offer_user_view.getString("id")
                                        brandSettingsData.type = offer_user_view.getString("type")
                                        brandSettingsData.image = offer_user_view.getString("image")
                                        brandSettingsData.discount_code = offer_user_view.getString("discount_code")

                                        val brand_name_data = offer_user_view.getJSONObject("brand_name_data")
                                        if (brand_name_data != null) {
                                            brandSettingsData.title = brand_name_data.getString("brand_name")

                                        }
                                        brandDataArrayList.add(brandSettingsData)
                                    }
                                    setAdapter(brandDataArrayList, value)
                                }
                            } else {
                                Common.customDialog(this, "Unilife", "No data found")

                            }
                        }
                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }


                WebUrls.LIST_SAVED_OFFER_CODE -> {

                    val obj = JSONObject(response)
                    llAllbrand.visibility = View.VISIBLE

                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)

                                    val offer_user_view = dataobj.getJSONObject("offer_user_saved")
                                    if (offer_user_view != null) {
                                        val brandSettingsData = BrandSettingsData()
                                        brandSettingsData.brand_id = offer_user_view.getString("brand_id")
                                        brandSettingsData.id = offer_user_view.getString("id")
                                        brandSettingsData.type = offer_user_view.getString("type")
                                        brandSettingsData.image = offer_user_view.getString("image")
                                        brandSettingsData.discount_code = offer_user_view.getString("discount_code")

                                        val brand_name_data = offer_user_view.getJSONObject("brand_name_data")
                                        if (brand_name_data != null) {
                                            brandSettingsData.title = brand_name_data.getString("brand_name")

                                        }
                                        brandDataArrayList.add(brandSettingsData)
                                    }
                                    setAdapter(brandDataArrayList, value)
                                }
                            } else {
                                Common.customDialog(this, "Unilife", "No data found")

                            }
                        }
                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }
                }
                WebUrls.SHOW_REDEEM_OFFER_CODE -> {
                    val obj = JSONObject(response)
                    llAllbrand.visibility = View.VISIBLE

                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {

                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    val dataobj = data.getJSONObject(i)

                                    val offer_user_view = dataobj.getJSONObject("offer_redeem")
                                    if (offer_user_view != null) {
                                        val brandSettingsData = BrandSettingsData()
                                        brandSettingsData.brand_id = offer_user_view.getString("brand_id")
                                        brandSettingsData.id = offer_user_view.getString("id")
                                        brandSettingsData.type = offer_user_view.getString("type")
                                        brandSettingsData.image = offer_user_view.getString("image")
                                        brandSettingsData.discount_code = offer_user_view.getString("discount_code")

                                        val brand_name_data = offer_user_view.getJSONObject("brand_name_data")
                                        if (brand_name_data != null) {
                                            brandSettingsData.title = brand_name_data.getString("brand_name")

                                        }
                                        brandDataArrayList.add(brandSettingsData)
                                    }
                                    setAdapter(brandDataArrayList, value)
                                }
                            } else {
                                Common.customDialog(this, "Unilife", "No data found")

                            }
                        }
                    } else {
                        Common.customDialog(this, "Unilife", obj.getString("message"))
                    }


                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    private fun callRedeemedBrand() {
        if (Alerts.isNetworkAvailable(this)) {
            RetrofitService(
                this, this, WebUrls.SHOW_REDEEM_OFFER + PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                ), WebUrls.SHOW_REDEEM_OFFER_CODE, 1
            ).callService(true)

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun callViewSavedBrand() {
        if (Alerts.isNetworkAvailable(this)) {
            RetrofitService(
                this, this, WebUrls.LIST_SAVED_OFFER + PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                ), WebUrls.LIST_SAVED_OFFER_CODE, 1
            ).callService(true)

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callViewBrand() {
        if (Alerts.isNetworkAvailable(this)) {

            RetrofitService(
                this, this, WebUrls.LIST_VIEW_BRANDS + PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                ), WebUrls.LIST_VIEW_BRANDS_CODE, 1
            ).callService(true)
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun sharedViewBrand() {
        if (Alerts.isNetworkAvailable(this)) {
            RetrofitService(
                this, this, WebUrls.LIST_SHARED_OFFER + PreferenceFile.getInstance().getPreferenceData(
                    this, WebUrls.KEY_USERID
                ), WebUrls.LIST_SHARED_OFFER_CODE, 1
            ).callService(true)


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun setAdapter(
        brandDataArrayList: ArrayList<BrandSettingsData>,
        value: String
    ) {

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rycBrand?.layoutManager = staggeredGridLayoutManager
        val allBlogSettingsAdapter = AllBrandSettingsAdapter(this, value, brandDataArrayList)
        rycBrand?.adapter = allBlogSettingsAdapter

    }

    data class BrandSettingsData(
        var id: String = "",
        var brand_id: String = "",
        var title: String = "",
        var type: String = "",
        var discount_code: String = "",
        var image: String = ""
    )
}
