package unilife.com.unilife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_uni.*
import org.json.JSONObject
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.model.UniModel
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls


class UniversitiesListing : AppCompatActivity(), RetrofitResponse {

    val universityList = ArrayList<UniModel>()
    private var uniAdapter: UniAdapter? = null

    var uniId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uni)

        getIntentData()

        callUniService()

        applyFilter()

        ivBack.setOnClickListener {
            finish()
        }

    }


    private fun applyFilter() {

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (uniAdapter != null) {
                    uniAdapter!!.filter(s.toString())

                }

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun getIntentData() {
        uniId = intent.getStringExtra("universityid")!!
        Log.e("UNIID",">>>"+uniId)
    }


    private fun callUniService() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()
            try {
                RetrofitService(
                    this@UniversitiesListing, this@UniversitiesListing, WebUrls.SEND_UNIVERSITY,
                    WebUrls.SEND_UNIVERSITY_CODE, 1, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    override fun onResponse(requestCode: Int, response: String) {

        try {

            val jsonObject = JSONObject(response)

            val res = jsonObject.getString("response")

            Log.e("SEND_UNIVERSITY_CODE", response)

            when (requestCode) {

                WebUrls.SEND_UNIVERSITY_CODE -> {

                    universityList.clear()

                    if (res.equals("true", true)) {

                        val data = jsonObject.getJSONArray("data")

                        if (data.length() > 0) {

                            tvNoData.visibility = GONE
                            rView.visibility = VISIBLE

                            for (i: Int in 0 until data.length()) {

                                val obj = data.getJSONObject(i)

                                val model = UniModel()
                                model.name = obj.getString("name")
                                model.id = obj.getString("id")

                                if(!uniId.trim().equals(model.id)) {
                                    model.isSelected = false
                                }else{
                                    model.isSelected = true
                                }

                                val domain = obj.getJSONObject("university_domain")
                                val domain_name = domain.getString("domain")
                                model.domain_name = domain_name

                                universityList.add(model)

                            }

                            uniAdapter = UniAdapter(this@UniversitiesListing, universityList)
                            rView.adapter = uniAdapter

                            uniAdapter!!.onSetItemClickListener(object :
                                UniAdapter.ClickListener {
                                override fun onItemClick(pos: Int) {

                                    for (i in 0 until universityList.size){

                                        if(i==pos){
                                            universityList[i].isSelected=true
                                        }else{
                                            universityList[i].isSelected=false
                                        }

                                    }

//                                    Log.e("POS", "onItemClick:" + universityList[pos].name)

                                    val returnIntent = Intent()
                                    returnIntent.putExtra("result", universityList[pos])
                                    setResult(Activity.RESULT_OK, returnIntent)
                                    finish()
                                }
                            })

                        } else {
                            tvNoData.visibility = VISIBLE
                            rView.visibility = GONE
                        }


                    } else {
                        tvNoData.visibility = VISIBLE
                        rView.visibility = GONE
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
