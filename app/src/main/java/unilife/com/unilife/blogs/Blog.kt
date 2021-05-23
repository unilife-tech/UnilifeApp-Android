package unilife.com.unilife.blogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextSwitcher
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_blog2.*
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.drawer_toolbar.*
import kotlinx.android.synthetic.main.layout_brand_bottom.*
import me.relex.circleindicator.CircleIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import unilife.com.unilife.AppDrawer
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.blogs.adapter.BlogMainAdapter
import unilife.com.unilife.blogs.adapter.BlogPagerAdapter
import unilife.com.unilife.blogs.adapter.BlogTeamAdapter
import unilife.com.unilife.blogs.request.GetBannerRequest
import unilife.com.unilife.blogs.response.BannerResponse
import unilife.com.unilife.blogs.response.Blog
import unilife.com.unilife.blogs.response.BlogResponse
import unilife.com.unilife.blogs.response.Team
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.home.Home
import unilife.com.unilife.notification.NotificationListing
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.WebUrls

class Blog : AppDrawer(), View.OnClickListener, RetrofitResponse {

    private var mTitle: TextSwitcher? = null
    private val blogs: ArrayList<Blog>? = ArrayList()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog2)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        ivBlogs.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                R.drawable.ic_blog_blue
            )
        )

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )

        tvMainTitle.text = "Unilife Blogs"
        tvMainTitle.setTextColor(resources.getColor(R.color.black))
        llToolBar.setBackgroundColor(resources.getColor(R.color.white))
        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.word_grey))
        ivNoti.setOnClickListener {
            val intent = Intent(this, NotificationListing::class.java)
            startActivity(intent)
        }

        tvBlog.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        mTitle = findViewById<View>(R.id.title) as TextSwitcher?

        mTitle?.setFactory {
            val inflater = LayoutInflater.from(this)
            inflater.inflate(R.layout.item_title, null) as TextView
        }
        doCall()
        getBanner()

        etSearchBlog.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val intent = Intent(this@Blog, SearchResult::class.java)
                    intent.putExtra("search", etSearchBlog.text.toString().trim())
                    intent.putExtra("value", "Blogs")
                    startActivity(intent)
                    return true
                }
                return false
            }
        })
    }

    fun showDialog(view: View) {
        // setup the alert builder

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Categories:-")

        val list: java.util.ArrayList<String>? = ArrayList()

        // add a list
        // add a list


        if (blogs != null) {
            for (Offer in blogs) {
                // body of loop
                if (list != null) {
                    list.add(Offer.categoriesName)
                }
            }
        }
        var listArr: Array<String?>? = arrayOfNulls(list!!.size)
        listArr = list.toArray(listArr)

        builder.setItems(listArr) { dialog, which ->
            val intent = Intent(this@Blog, BlogViewAllActivity::class.java)
            if (blogs != null) {
                intent.putExtra("item", blogs.get(which))
                startActivity(intent)
            }
        }

        // create and show the alert dialog
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }


    internal fun setupPager(slider: ArrayList<BannerResponse.Datum>) {
        val viewPager = findViewById<ViewPager>(R.id.brandPager)
        val adapter = BlogPagerAdapter(this, slider)
        viewPager.adapter = adapter
        viewPager.currentItem = 0

        val pageIndicatorView = findViewById<CircleIndicator>(R.id.indicatorbrand)
        pageIndicatorView.setViewPager(viewPager) // specify total count of indicators
    }

    internal fun setUpData(arrayList: ArrayList<Blog>) {
//        textName1.setText("Health and Fitness");
//        textName2.setText("Career");

        val recyclerView = findViewById<RecyclerView>(R.id.recycvlerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BlogMainAdapter(this, arrayList)

        card1.setOnClickListener {
            if (blogs != null) {
                val intent = Intent(this@Blog, BlogViewAllActivity::class.java)
                intent.putExtra("item", blogs.get(1))
                startActivity(intent)
            }
        }
        card2.setOnClickListener {
            if (blogs != null) {
                val intent = Intent(this@Blog, BlogViewAllActivity::class.java)
                intent.putExtra("item", blogs.get(2))
                startActivity(intent)
            }
        }
        card3.setOnClickListener {
            if (blogs != null) {
                val intent = Intent(this@Blog, BlogViewAllActivity::class.java)
                intent.putExtra("item", blogs.get(0))
                startActivity(intent)
            }
        }

    }

    internal fun setUpTeam(arrayList: ArrayList<Team>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycvlerTeam)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = BlogTeamAdapter(this, arrayList)
    }

    fun doCall() {
        if (!isNetworkConnected())
            return

        showProgressDialog()
        val apiInterface = ApiClient.getClientOld().create(ApiInterface::class.java)
        val call =
            apiInterface.getBlogs(
                PreferenceFile.getInstance().getPreferenceData(
                    this,
                    WebUrls.KEY_USERID
                )
            )

        call.enqueue(object : Callback<BlogResponse> {
            override fun onResponse(call: Call<BlogResponse>, response: Response<BlogResponse>) {
                hideProgressDialog()
                Log.e("response code -->", "" + response.code())

                if (response.isSuccessful) {
//                    setupPager(response.body()!!.slider)
                    blogs?.addAll(response.body()!!.blogs)
                    setUpData(response.body()!!.blogs)
                    setUpTeam(response.body()!!.team)
                }
            }

            override fun onFailure(call: Call<BlogResponse>, t: Throwable) {
                hideProgressDialog()
                showToast(t.message.toString())
            }
        })
    }

    fun getBanner() {
        if (!isNetworkConnected())
            return

        val getBannerRequest = GetBannerRequest()
        getBannerRequest.setType("blog")

        showProgressDialog()
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call =
            apiInterface.getBanner(
                PreferenceFile.getInstance().getPreferenceData(
                    this,
                    WebUrls.KEY_USERID
                ), getBannerRequest
            )

        call.enqueue(object : Callback<BannerResponse> {
            override fun onResponse(
                call: Call<BannerResponse>,
                response: Response<BannerResponse>
            ) {
                hideProgressDialog()
                Log.e("response code -->", "" + response.code())

                if (response.isSuccessful) {
                    setupPager(response.body()!!.data)
//                    setUpData(response.body()!!.blogs)
//                    setUpTeam(response.body()!!.team)
//                    blogs?.addAll(response.body()!!.blogs)
                }
            }

            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                hideProgressDialog()
                showToast(t.message.toString())
            }
        })
    }

    fun openSettings(view: View) {
        val intent = Intent(this@Blog, BlogSettings::class.java)
        startActivity(intent)
    }

//    override fun onClick(v: View?) {
//
//        when (v?.id) {
//            R.id.rlChat -> {
//                finish()
//                val intent = Intent(this, ChatListing::class.java)
//                startActivity(intent)
//            }
//
////            R.id.rlBlogs -> {
////                finish()
////                val intent = Intent(this, Blog::class.java)
////                startActivity(intent)
////
////            }
//
//            R.id.rlBrands -> {
//                finish()
//                val intent = Intent(this, BrandsHome::class.java)
//                startActivity(intent)
//
//            }
//            R.id.rlEvent -> {
//                finish()
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//
//            }
//        }
//    }

}

