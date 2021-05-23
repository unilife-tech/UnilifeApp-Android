package unilife.com.unilife.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.profile.adapters.ProfileAdapter_1
import unilife.com.unilife.profile.adapters.ProfileAdapter_2
import unilife.com.unilife.profile.editprofiel.*
import unilife.com.unilife.profile.model.ProfileResponse
import unilife.com.unilife.R
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.WebUrls

class MyProfile2 : AppCompatActivity(), View.OnClickListener, RetrofitResponse {
    var recycvlerView4: RecyclerView? = null
    var recycvlerView5: RecyclerView? = null
    var recycvlerView6: RecyclerView? = null
    var recycvlerView7: RecyclerView? = null
    var recycvlerView8: RecyclerView? = null
    var recycvlerView9: RecyclerView? = null
    var mContext=this

    private lateinit var adapter1: RecyclerView.Adapter<*>
    private lateinit var adapter2: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        adapter1 = ProfileAdapter_1(this)
        adapter2 = ProfileAdapter_2(this)
        setUpViews()
        getProfileData()
    }

    private fun setUpViews() {
        recycvlerView4 = findViewById<RecyclerView>(R.id.recycvlerView4).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;

            // use a linear layout manager
            layoutManager = LinearLayoutManager(mContext)

            // specify an viewAdapter (see also next example)
            adapter = adapter1
        }

        recycvlerView5 = findViewById<RecyclerView>(R.id.recycvlerView5).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;


            // use a linear layout manager
            layoutManager =  LinearLayoutManager(mContext)

            // specify an viewAdapter (see also next example)
            adapter = adapter1

        }

        recycvlerView6 = findViewById<RecyclerView>(R.id.recycvlerView6).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;


            // use a linear layout manager
            layoutManager =  GridLayoutManager(mContext,4)

            // specify an viewAdapter (see also next example)
            adapter = adapter2

        }

        recycvlerView7 = findViewById<RecyclerView>(R.id.recycvlerView7).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;


            // use a linear layout manager
            layoutManager =  GridLayoutManager(mContext,4)

            // specify an viewAdapter (see also next example)
            adapter = adapter2

        }

        recycvlerView8 = findViewById<RecyclerView>(R.id.recycvlerView8).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;
            // use a linear layout manager
            layoutManager =  LinearLayoutManager(mContext)

            // specify an viewAdapter (see also next example)
            adapter = adapter1

        }

        recycvlerView9 = findViewById<RecyclerView>(R.id.recycvlerView9).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            isNestedScrollingEnabled = false;
            // use a linear layout manager
            layoutManager =  GridLayoutManager(mContext,4)

            // specify an viewAdapter (see also next example)
            adapter = adapter2

        }
    }

    override fun onResponse(requestCode: Int, response: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun openEditIntro(view: View) { startActivity(Intent(this, EditIntroActivity::class.java)) }
    fun openEditHighlights(view: View) {startActivity(Intent(this, EditHighlightActivity::class.java))}

    fun openEditExperience(view: View) {startActivity(Intent(this, EditExperienceActivity::class.java))}
    fun openEditEducation(view: View) {startActivity(Intent(this, EditEducationActivity::class.java))}
    fun openEditSkills(view: View) {startActivity(Intent(this, EditSkillActivity::class.java))}
    fun openEditLang(view: View) {startActivity(Intent(this, EditLangActivity::class.java))}
    fun openEditAchivement(view: View) {startActivity(Intent(this, EditAchivementActivity::class.java))}
    fun openEditInterest(view: View) {startActivity(Intent(this, EditInterestActivity::class.java))}

    fun openFacebook(view: View) {startActivity(Intent(this, EditSocialActivity::class.java))}
    fun openInsta(view: View) {startActivity(Intent(this, EditSocialActivity::class.java))}
    fun openSharechat(view: View) {startActivity(Intent(this, EditSocialActivity::class.java))}
    fun openTwitter(view: View) {startActivity(Intent(this, EditSocialActivity::class.java))}

    fun getProfileData() {
//        if (!isNetworkConnected())
//            return

//        val loginRequest = LoginRequest()
//        loginRequest.setEmail(strEmail)
//        loginRequest.setPassword(strPassword)
//        loginRequest.setSource("android")
//        loginRequest.setLanguage("en")

//        showProgressDialog()
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiInterface.getProfile(
            PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))

        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {


            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                hideProgressDialog()
//                showToast(t.message)
            }
        })
    }
}