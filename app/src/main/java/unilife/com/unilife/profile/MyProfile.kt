package unilife.com.unilife.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_my_profile.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class MyProfile : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    private var qustionanswerData: ArrayList<MyProfileModel>? = ArrayList<MyProfileModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        setOnClickListner()
        ViewProfileService()
    }


    fun setOnClickListner() {
        ivback_arrow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivback_arrow -> {
                var i = Intent(this, Home::class.java)
                startActivity(i)
            }

            R.id.iv_edit -> {
                val intent = Intent(this, EditAccount::class.java)
                startActivity(intent)
            }
        }
    }


    private fun ViewProfileService() {
        val postParam = JSONObject()
        try {
            postParam.put(
                "user_id",
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            )
            RetrofitService(
                this,
                this,
                WebUrls.VIEW_PROFILE,
                WebUrls.VIEW_PROFILE_CODE,
                3,
                postParam
            ).callService(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    override fun onResponse(requestCode: Int, response: String) {
        try {
            val `object` = JSONObject(response)
            val res = `object`.getString("response")
            Log.e("Register", response)

            when (requestCode) {

                WebUrls.VIEW_PROFILE_CODE -> {
                    if (res.equals("true", true)) {

                        val data = `object`.getJSONObject("data")
                        val quesAnswerObj = data.getJSONObject("user_ques_ans")
                        if (!`data`.getString("user_ques_ans").equals(" ")) {

                            val row = quesAnswerObj.getJSONArray("rows")

                            if (!row.equals(" ")) {
                                for (i: Int in 0 until row.length()) {

                                    val myProfileModel = MyProfileModel()
                                    val rowObj = row.getJSONObject(i)

                                    myProfileModel.qustion = rowObj.getString("question")
                                    myProfileModel.qustionImage = rowObj.getString("image")

                                    val answerArray = rowObj.getJSONArray("ques_answers")

                                    if (!answerArray.equals(" ")) {

                                        for (j: Int in 0 until answerArray.length()) {

                                            val answerObj = answerArray.getJSONObject(j)

                                            myProfileModel.answer = answerObj.getString("answer")
                                            qustionanswerData?.add(myProfileModel)
                                        }
                                    }

                                }
                            }
                        }

                        val userDetailsObj = data.getJSONObject("user_detail")

                        /*       val layoutManager = LinearLayoutManager(this)
                               ryc_qustion_answer?.layoutManager = layoutManager
                               val myProfileAdapter = qustionanswerData?.let { MyProfileAdapter(this, it) }
                               ryc_qustion_answer?.adapter = myProfileAdapter*/

                    } else {
                        Common.alertDialog(this, `object`.getString("message"))
                    }
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }
}



