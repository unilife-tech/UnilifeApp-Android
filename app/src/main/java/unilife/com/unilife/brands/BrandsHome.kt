package unilife.com.unilife.brands

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.TextSwitcher
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_brands_home2.*
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
import unilife.com.unilife.blogs.SearchResult
import unilife.com.unilife.brands.adapter.BottomCategoriesAdapter
import unilife.com.unilife.brands.adapter.BrandMainAdapter
import unilife.com.unilife.brands.adapter.CustomPagerAdapter
import unilife.com.unilife.brands.newbrandresponse.BrandResponse2
import unilife.com.unilife.notification.NotificationListing
import unilife.com.unilife.retro.ApiClient
import unilife.com.unilife.retro.ApiInterface
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.WebUrls.KEY_USERID

class BrandsHome : AppDrawer(), View.OnClickListener, RetrofitResponse {

    private var mTitle: TextSwitcher? = null
    private var recyclerView: RecyclerView? = null
    private val offer: ArrayList<BrandResponse2.Offer>? = ArrayList()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands_home2)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivBrands.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )

        tvMainTitle.text = "Unilife Brands"
        tvMainTitle.setTextColor(resources.getColor(R.color.colorAccent))
        llToolBar.setBackgroundColor(resources.getColor(R.color.white))
        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.word_grey))

        ivNoti.setOnClickListener {
            val intent = Intent(this, NotificationListing::class.java)
            startActivity(intent)
        }

        doCall()

        etSearchBrand.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val intent = Intent(this@BrandsHome, SearchResult::class.java)
                    intent.putExtra("search", etSearchBrand.text.toString().trim())
                    intent.putExtra("value", "Brands")
                    startActivity(intent)
                    return true
                }
                return false
            }
        })
    }

    internal fun setupPager(slider: ArrayList<BrandResponse2.Banner>) {
        val viewPager = findViewById<ViewPager>(R.id.brandPager)
        val adapter = CustomPagerAdapter(this, slider)
        viewPager.adapter = adapter
        viewPager.currentItem = 0

        val pageIndicatorView = findViewById<CircleIndicator>(R.id.indicatorbrand)
        pageIndicatorView.setViewPager(viewPager) // specify total count of indicators
    }

    internal fun setUpData(arrayList: ArrayList<BrandResponse2.Offer>) {
//        card1.setOnClickListener {
//            if (offer != null) {
//                val intent = Intent(this@BrandsHome, BrandViewAllActivity::class.java)
//                intent.putExtra("item", offer.get(3))
//                startActivity(intent)
//            }
//        }
//        card2.setOnClickListener {
//            if (offer != null) {
//                val intent = Intent(this@BrandsHome, BrandViewAllActivity::class.java)
//                intent.putExtra("item", offer.get(4))
//                startActivity(intent)
//            }
//        }
//        card3.setOnClickListener {
//            if (offer != null) {
//                val intent = Intent(this@BrandsHome, BrandViewAllActivity::class.java)
//                intent.putExtra("item", offer.get(5))
//                startActivity(intent)
//            }
//        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycvlerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BrandMainAdapter(this, arrayList)
    }

    fun doCall() {
        if (!isNetworkConnected())
            return

        showProgressDialog()
        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call =
            apiInterface.getBrands(PreferenceFile.getInstance().getPreferenceData(this, KEY_USERID))

        call.enqueue(object : Callback<BrandResponse2> {
            override fun onResponse(
                call: Call<BrandResponse2>,
                response: Response<BrandResponse2>
            ) {
                hideProgressDialog()
                Log.e("response code -->", "" + response.code())
                if (response.isSuccessful) {
                    if (response.body()!!.offer != null && response.body()!!.offer.size > 0) {
                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility = View.GONE
                        offer!!.addAll(response.body()!!.offer)
                        setupPager(response.body()!!.banner)
                        setUpData(response.body()!!.offer)
                        setUpBottom(response.body()!!.categories)

                    } else {
                        findViewById<RelativeLayout>(R.id.layoutEmpty).visibility = View.VISIBLE
                    }
                } else {
                    findViewById<RelativeLayout>(R.id.layoutEmpty).visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<BrandResponse2>, t: Throwable) {
                hideProgressDialog()
                showToast(t.message.toString())
            }
        })
    }

    private fun setUpBottom(categories: ArrayList<BrandResponse2.Category>) {
        recyclerBottomCategories.setHasFixedSize(true)
        recyclerBottomCategories.layoutManager = LinearLayoutManager(this)
        recyclerBottomCategories.adapter = BottomCategoriesAdapter(this, categories)

    }

//    fun getBanner() {
//        if (!isNetworkConnected())
//            return
//
//        val getBannerRequest = GetBannerRequest()
//        getBannerRequest.setType("brand")
//
//        showProgressDialog()
//        val apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
//        val call =
//            apiInterface.getBanner(
//                PreferenceFile.getInstance().getPreferenceData(
//                    this,
//                    WebUrls.KEY_USERID
//                ), getBannerRequest
//            )
//
//        call.enqueue(object : Callback<BannerResponse> {
//            override fun onResponse(
//                call: Call<BannerResponse>,
//                response: Response<BannerResponse>
//            ) {
//                hideProgressDialog()
//                Log.e("response code -->", "" + response.code())
//
//                if (response.isSuccessful) {
//                    setupPager(response.body()!!.data)
////                    setUpData(response.body()!!.blogs)
////                    setUpTeam(response.body()!!.team)
////                    blogs?.addAll(response.body()!!.blogs)
//                }
//            }
//
//            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
//                hideProgressDialog()
//                showToast(t.message.toString())
//            }
//        })
//    }


    private fun showDialog() {
        // setup the alert builder

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Categories:-")

        val list: java.util.ArrayList<String>? = ArrayList()

        // add a list
        // add a list


        if (offer != null) {
            for (Offer in offer) {
                // body of loop
                if (list != null) {
                    list.add(Offer.category)
                }
            }
        }
        var listArr: Array<String?>? = arrayOfNulls(list!!.size)
        listArr = list.toArray(listArr)

        builder.setItems(listArr) { dialog, which ->
            val intent = Intent(this@BrandsHome, BrandViewAllActivity::class.java)
            if (offer != null) {
                intent.putExtra("item", offer.get(which))
                startActivity(intent)
            }
        }

        // create and show the alert dialog
        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

//    private fun showDialog() {
//        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this@BrandsHome)
//        builderSingle.setIcon(R.drawable.ic_launcher)
//        builderSingle.setTitle("Categories:-")
//
//        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
//            this@BrandsHome,
//            android.R.layout.select_dialog_singlechoice
//        )
//
//        if (offer != null) {
//            for (Offer in offer) {
//                // body of loop
//                arrayAdapter.add(Offer.name)
//            }
//        }
//
//        builderSingle.setNegativeButton("close", object : DialogInterface.OnClickListener {
//            override fun onClick(dialog: DialogInterface, which: Int) {
//                dialog.dismiss()
//            }
//        })
//
//        builderSingle.setAdapter(arrayAdapter, object : DialogInterface.OnClickListener {
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//
//                val intent = Intent(this@BrandsHome, BrandViewAllActivity::class.java)
//                if (offer != null) {
//                    intent.putExtra("item", offer.get(which))
//                }
//                startActivity(intent)
////                val strName: String = arrayAdapter.getItem(which)
////                val builderInner: AlertDialog.Builder = AlertDialog.Builder(this@BrandsHome)
////                builderInner.setMessage(strName)
////                builderInner.setTitle("Your Selected Item is")
////                builderInner.setPositiveButton("Ok", object : DialogInterface.OnClickListener {
////                    override fun onClick(dialog: DialogInterface, which: Int) {
////                        dialog.dismiss()
////                    }
////                })
////                builderInner.show()
//            }
//        })
//        builderSingle.show()
//    }

    fun showDialog(view: View) {
        showDialog()
    }

    fun openSettings(view: View) {
        val intent = Intent(this@BrandsHome, BrandSettings::class.java)
        startActivity(intent)
    }

//    override fun onClick(v: View?) {
//
//        when (v?.id) {
//            R.id.rlChat -> {
//                finish()
//                val intent = Intent(this, ChatListing::class.java)
//                startActivity(intent)
//
//            }
//
//            R.id.rlBlogs -> {
//                finish()
//                val intent = Intent(this, Blog::class.java)
//                startActivity(intent)
//
//            }
//
////            R.id.rlBrands -> {
////                finish()
////                val intent = Intent(this, BrandsHome::class.java)
////                startActivity(intent)
////
////            }
//            R.id.rlEvent -> {
//                finish()
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//
//            }
//        }
//    }

}
