package unilife.com.unilife.home

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_profile.ivback_arrow
import kotlinx.android.synthetic.main.activity_my_profile2.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.profile.*
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class ShowOtherProfile : AppCompatActivity(),View.OnClickListener, RetrofitResponse {

    var post_user_id= ""
    var friend = ""
    var profile_status =""

    private var qustionanswerData: ArrayList<MyProfileModel>?=  ArrayList()
    private var hobbiesData: ArrayList<MyProfileModel2>?=  ArrayList()
    private var interestData: ArrayList<InterestModel>?=  ArrayList()
    private var categoriesData: ArrayList<CategoriesModel>?=  ArrayList()
    var iconsList: ArrayList<Int> = ArrayList()
    var answersCategory: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile2)
        iv_edit.visibility=View.GONE
        setOnClickListener()
        getIntentData()
        ViewProfileService()
        initRecycler()

        tv_myaccount.text = "Account"
        tv_LoggedInAs.visibility = View.GONE
    }

    private fun getIntentData() {
        post_user_id = intent.getStringExtra("post_user_id")
    }

    private fun initRecycler() {

        val myProfileAdapter = MyProfileRoundIconsAdapter(this,categoriesData!!,iconsList)
        rv_roundicons?.adapter = myProfileAdapter

        val adapter12 = MyProfileAdapter(this,qustionanswerData!!)
        rv_profileques?.adapter = adapter12

        val myProfileAdapter2 = HobbiesAdapter(this,hobbiesData!!)
        rv_Hobbies?.adapter = myProfileAdapter2

        val myProfileAdapter3 = InterestAdapter(this,interestData!!)
        rv_Interest?.adapter = myProfileAdapter3

    }


    fun setOnClickListener(){

        ivback_arrow.setOnClickListener(this)
        iv_edit.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivback_arrow-> {
                val i = Intent(this, Home::class.java)
                startActivity(i)
            }

            R.id.iv_edit-> {
                val intent= Intent(this, EditProfile::class.java)
                startActivity(intent)
            }
        }
    }


    private fun ViewProfileService() {
        if(Alerts.isNetworkAvailable(this)){
        val postParam = JSONObject()
        try {
            postParam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
            postParam.put("post_user_id", post_user_id)
            RetrofitService(
                this, this, WebUrls.SHOW_OTHER_PROFILE,
                WebUrls.SHOW_OTHER_PROFILE_CODE, 3, postParam
            ).callService(true)
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }



    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("SetTextI18n")
    override fun onResponse(requestCode: Int, response: String) {

        try {

            val `object` = JSONObject(response)

            val res = `object`.getString("response")

            Log.e("Register", response)

            when (requestCode) {

                WebUrls.SHOW_OTHER_PROFILE_CODE -> {


                    scrollView.visibility = View.VISIBLE

                    if (res.equals("true", true)) {

                        val data = `object`.getJSONObject("data")

                        friend = `object`.getString("friend")


                        val quesAnswerObj = data.getJSONObject("user_ques_ans")

                        if (quesAnswerObj != null) {

                            val row = quesAnswerObj.getJSONArray("rows")

                            if (row != null) {

                                //not simple   one
                                for (i: Int in 0 until row.length()) {

                                    val rowObj = row.getJSONObject(i)

                                    val myProfileModel = MyProfileModel()

                                    myProfileModel.questionType = rowObj.getString("question_type")
                                    myProfileModel.qustion = rowObj.getString("question")
                                    myProfileModel.qustionImage = rowObj.getString("image")

                                    val answerArray = rowObj.getJSONArray("ques_answers")

                                    if (answerArray != null) {

                                        for (j: Int in 0 until answerArray.length()) {

                                            val answerObj = answerArray.getJSONObject(j)

                                            myProfileModel.answer = answerObj.getString("answer")


                                        }
                                    }

                                    if (myProfileModel.questionType != "simple")
                                        qustionanswerData?.add(myProfileModel)
                                }
                                // simple   one
                                for (i: Int in 0 until row.length()) {

                                    val rowObj = row.getJSONObject(i)

                                    val myProfileModel = MyProfileModel()

                                    myProfileModel.questionType = rowObj.getString("question_type")
                                    myProfileModel.qustion = rowObj.getString("question")
                                    myProfileModel.qustionImage = rowObj.getString("image")

                                    val answerArray = rowObj.getJSONArray("ques_answers")

                                    if (answerArray != null) {

                                        for (j: Int in 0 until answerArray.length()) {

                                            val answerObj = answerArray.getJSONObject(j)

                                            myProfileModel.answer = answerObj.getString("answer")


                                        }
                                    }

                                    if (myProfileModel.questionType == "simple")
                                        qustionanswerData?.add(myProfileModel)
                                }
                            }
                        }

                        val userDetailsObj = data.getJSONObject("user_detail")

                        if (userDetailsObj != null) {

                            profile_status = userDetailsObj.getString("profile_status")

                            Log.e("whatsthere",""+profile_status)
                            var dataprofileimage = userDetailsObj.getString("profile_image")

                            Picasso.with(applicationContext).load(WebUrls.PROFILE_IMAGE + dataprofileimage)
                                .placeholder(R.drawable.user).into(ivProfileImg)

                            Log.e("image_profile", WebUrls.PROFILE_IMAGE + dataprofileimage)

                            val datauserhobbies = userDetailsObj.getJSONArray("user_hobbies")

                            if (datauserhobbies != null) {
                                for (i in 0 until datauserhobbies.length()) {
                                    val hobbiesobj = datauserhobbies.getJSONObject(i)
                                    var myProfileModel2 = MyProfileModel2()
                                    var hobbieslist = hobbiesobj.getJSONObject("hobbies")
                                    myProfileModel2.hobbiesname = hobbieslist.getString("name")
                                    myProfileModel2.hobbiesicon = hobbieslist.getString("icon")
                                    hobbiesData?.add(myProfileModel2)
                                }

                            }

                            val datauserinterest = userDetailsObj.getJSONArray("user_hobbies_interests")

                            if (datauserinterest != null) {

                                for (j in 0 until datauserinterest.length()) {
                                    val interestrobj = datauserinterest.getJSONObject(j)
                                    var interestModel = InterestModel()
                                    var interestlist = interestrobj.getJSONObject("hobbies_interests")
                                    interestModel.interesticon = interestlist.getString("icon")
                                    interestModel.interestname = interestlist.getString("name")
                                    interestData?.add(interestModel)
                                }
                            }

                            val user_course_covered = userDetailsObj.getJSONArray("user_course_covered")
                            var catogary_name: ArrayList<String> = ArrayList()
                            var catogary_id: ArrayList<String> = ArrayList()

                            if (user_course_covered.length() > 0) {
                                for (j: Int in 0 until user_course_covered.length()) {

                                    val user_course_coveredObj = user_course_covered.getJSONObject(j)
                                    Log.e("J value::", user_course_coveredObj.getString("answer"))
                                    catogary_name.add(user_course_coveredObj.getString("answer"))
                                    catogary_id.add(user_course_coveredObj.getString("course_id"))
                                }
                            }

                            answersCategory = catogary_name.toString().replace("[", "").replace("]", "")


                            Log.e("AnswersCategory", "answersCategory:" + answersCategory)

                            var yourArray: List<String> = answersCategory.split(",")
                            iconsList.clear()
                            iconsList.add(R.drawable.caa)
                            iconsList.add(R.drawable.cone)
                            iconsList.add(R.drawable.cbook)
                            iconsList.add(R.drawable.cman)
                            iconsList.add(R.drawable.cwheel)
                            iconsList.add(R.drawable.ccircle)
                            var size: Int = yourArray.size

                            Log.e("AnswersCategory", "yourArray:" + yourArray.size)
                            Log.e("AnswersCategory", "yourArray:" + yourArray.toString())

                            for (i in 0 until size) {
                                if (yourArray[i].isNotEmpty()) {
                                    val categoriesModel = CategoriesModel()

                                    categoriesModel.categoryname = yourArray.get(i)
                                    categoriesData?.add(categoriesModel)
                                }
                            }
                            Log.e("ListData:", "" + categoriesData?.size)


                        }
                        tvname_mp.text = qustionanswerData!![0].answer
                        tv_showbio.text = qustionanswerData!![1].answer
                        tvstudy2.text = "Studying at " + PreferenceFile.getInstance().getPreferenceData(this,WebUrls.UNIVERSITY_NAME)



                        initRecycler()
                        if (profile_status == "public"){

                                clselectView.visibility = View.VISIBLE
                    }
                        else if (profile_status == "private"){

                            if(friend == "friend")
                            {
                                clselectView.visibility = View.VISIBLE
                            }
                            else
                            {
                                clselectView.visibility = View.GONE
                            }

                        }
                        else{

                            clselectView.visibility = View.GONE

                        }

                    } else {
                        Alerts.alertDialog(this, `object`.getString("message"))
                    }
                }
            }
        }
        catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }


    override fun onBackPressed() {
//        startActivity(Intent(this, Home::class.java))
        finish()
    }

}