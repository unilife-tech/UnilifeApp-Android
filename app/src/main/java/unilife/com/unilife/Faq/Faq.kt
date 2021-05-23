package unilife.com.unilife.Faq

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class Faq : AppCompatActivity(),RetrofitResponse {


    var faqList: ArrayList<FaqData> = ArrayList()

    internal var faqAdapter: FaqAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        tvTitle.text = "FAQ's"
        ivNotification.visibility = View.GONE
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
        getFaqdata()


    }

    override fun onResponse(requestCode: Int, response: String) {

        when (requestCode) {
            WebUrls.FAQ_CODE -> {
                val obj = JSONObject(response)
                if (obj.getBoolean("response")) {
                    if (obj.has("data")) {
                        var data = obj.getJSONArray("data")
                        if (data.length() > 0) {
                            for (i in 0 until data.length()) {
                                var dataobj = data.getJSONObject(i)
                                var faq = FaqData()
                                faq.questions = dataobj.getString("questions")
                                faq.answer = dataobj.getString("answer")
                                faqList.add(faq)
                            }
                        }
                        setFaqAdapter(faqList)
                    }
                }
            }
        }
    }

    private fun getFaqdata() {
        if (Alerts.isNetworkAvailable(this)){
            RetrofitService(this, this, WebUrls.FAQ, WebUrls.FAQ_CODE, 1).callService(true)
    }
    else
    {
        Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
    }
}


    private fun setFaqAdapter(faqList: ArrayList<FaqData>) {

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        faqrecyclerview?.layoutManager = layoutManager
        faqAdapter = FaqAdapter(faqList)
        faqrecyclerview?.adapter = faqAdapter

    }

    data class FaqData(
        var questions : String = "",
        var answer : String = ""

    )
}
