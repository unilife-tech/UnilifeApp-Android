//package unilife.com.unilife.brands
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.res.ColorStateList
//import android.os.Bundle
//import android.support.v4.content.ContextCompat
//import android.support.v4.graphics.drawable.DrawableCompat
//import android.support.v4.view.ViewPager
//import android.support.v7.widget.GridLayoutManager
//import android.support.v7.widget.LinearLayoutManager
//import android.util.Log
//import android.view.*
//import android.view.animation.AnimationUtils
//import android.view.inputmethod.EditorInfo
//import android.widget.*
//import kotlinx.android.synthetic.main.activity_brands_home.*
//import kotlinx.android.synthetic.main.activity_brands_home2.*
//import kotlinx.android.synthetic.main.activity_brands_home2.OfferAdapter2
//import kotlinx.android.synthetic.main.activity_brands_home2.brandPager
//import kotlinx.android.synthetic.main.activity_brands_home2.etSearchBrand
//import kotlinx.android.synthetic.main.activity_brands_home2.indicatorbrand
//import kotlinx.android.synthetic.main.activity_brands_home2.rycOffers3
//import kotlinx.android.synthetic.main.activity_brands_home2.ryc_offer1
//import kotlinx.android.synthetic.main.activity_brands_home2.tvcategories
//import kotlinx.android.synthetic.main.activity_brands_home2.tvofferVA1
//import kotlinx.android.synthetic.main.activity_brands_home2.tvofferVA3
//import kotlinx.android.synthetic.main.activity_brands_home2.tvoffers1
//import kotlinx.android.synthetic.main.activity_brands_home2.tvoffers2
//import kotlinx.android.synthetic.main.activity_brands_home2.tvoffers3
//import kotlinx.android.synthetic.main.bottom_bar.*
//import kotlinx.android.synthetic.main.drawer_toolbar.*
//import kotlinx.android.synthetic.main.drawer_toolbar.llToolBar
//import kotlinx.android.synthetic.main.navigation_bar.*
//import org.json.JSONObject
//import unilife.com.unilife.AppDrawer
//import unilife.com.unilife.Chat.ChatListing
//import unilife.com.unilife.blogs.*
//import unilife.com.unilife.Chat.ChatSetting
//import unilife.com.unilife.Faq.Faq
//import unilife.com.unilife.Help
//import unilife.com.unilife.home.Home
//import unilife.com.unilife.notification.NotificationListing
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.profile.MyProfile
//import unilife.com.unilife.R
//import unilife.com.unilife.brands.OfferAdapter2
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import java.lang.Exception
//
//class BrandsHome : AppDrawer(), View.OnClickListener, RetrofitResponse {
//
//    private var adapter: CoverFlowAdapter? = null
//    private var mTitle: TextSwitcher? = null
//    private var mData: ArrayList<BlogItem>? = ArrayList(0)
//    var sliderArraylist: ArrayList<SliderBrands> = ArrayList()
//    var mainOfferArrayList: ArrayList<OfferData> = ArrayList()
//    var blogArrayList: ArrayList<BlogsinBrandData> = ArrayList()
//    var searchtext = ""
//
//
//    @SuppressLint("ResourceType")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_brands_home2)
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivBrands.drawable),
//            ContextCompat.getColor(this, R.color.colorPrimary)
//        )
//
//        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivEvent.drawable),
//            ContextCompat.getColor(this, R.color.medium_grey)
//        )
//
//        tvMainTitle.text = "Unilife Brands"
//        tvMainTitle.setTextColor(resources.getColor(R.color.white))
//        llToolBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
//
//     /*   tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//        ivBrands.setImageDrawable(
//            ContextCompat.getDrawable(
//                applicationContext, // Context
//                R.drawable.ic_brands
//            )
//        )*/
//
//
//
//        etSearchBrand.setOnEditorActionListener { v, actionId, event ->
//
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                searchtext = etSearchBrand.text.toString()
//                startActivity(Intent(this,SearchResult::class.java)
//                    .putExtra("search",searchtext)
//                    .putExtra("value","Brands"))
//            }
//            true
//        }
//
//        callService()
//
//
//
//
//        setOnClickListener()
//        //   setCategoryAdapter()
//        //   setBrandsAdapter(blogArrayList)
//
//
//        mData = ArrayList()
//        mData?.add(BlogItem(R.drawable.no_image, R.string.title4))
//        mData?.add(BlogItem(R.drawable.no_image, R.string.title4))
//        mData?.add(BlogItem(R.drawable.no_image, R.string.title4))
//
//
//        mTitle = findViewById<View>(R.id.title) as TextSwitcher?
//        mTitle?.setFactory {
//            val inflater = LayoutInflater.from(this)
//            inflater.inflate(R.layout.item_title, null) as TextView
//        }
//
//
//        val `in` = AnimationUtils.loadAnimation(this, R.xml.slide_in_top)
//        val out = AnimationUtils.loadAnimation(this, R.xml.slide_out_bottom)
//        mTitle?.inAnimation = `in`
//        mTitle?.outAnimation = out
//
//        val arraySlider: ArrayList<Blog.SliderData> = ArrayList()
//
//        /* adapter = CoverFlowAdapter(this)
//         adapter?.setData(mData, arraySlider)
//         coverflowbrands.adapter = adapter
//         coverflowbrands.onItemClickListener =
//             AdapterView.OnItemClickListener { parent, view, position, id ->
//
//             }
//
//         coverflowbrands.setOnScrollPositionListener(object :
//             FeatureCoverFlow.OnScrollPositionListener {
//             override fun onScrolledToPosition(position: Int) {
//                 mTitle?.setText(mData?.get(position)?.titleResId?.let { resources.getString(it) })
//             }
//
//             override fun onScrolling() {
//                 mTitle?.setText("")
//             }
//         })*/
//
//        Log.e("Brands", "Brands_HOME")
//
//
//    }
//
//
//    override fun onResponse(requestCode: Int, response: String) {
//        super.onResponse(requestCode, response)
//
//        try {
//
//            when (requestCode) {
//
//                WebUrls.SHOW_OFFERS_CODE -> {
//
//                    val obj = JSONObject(response)
//                    Log.e("obj", obj.toString())
////                    rlBrandsMain.visibility=View.VISIBLE
//                    val res = obj.getString("response")
//                    if (res.equals("true", true)) {
//                        sliderArraylist.clear()
//                        mainOfferArrayList.clear()
//
//
//                        if (obj.has("slider")) {
//                            val slider = obj.getJSONArray("slider")
//                            if (slider.length() > 0) {
//                                for (i in 0 until slider.length()) {
//                                    val sliderr = SliderBrands()
//                                    val sliderObj = slider.getJSONObject(i)
//                                    sliderr.image = sliderObj.getString("image")
//                                    sliderr.brand_id = sliderObj.getString("id")
//                                    sliderr.name= sliderObj . getString ("title")
//                                    sliderArraylist.add(sliderr)
//                                }
//                            }
//                            if(sliderArraylist.size>0){
//                                brandPager.visibility=View.VISIBLE
//                            }
//                            setSliderAdapter(brandPager, sliderArraylist)
//                            indicatorbrand.setViewPager(brandPager)
//                        }
//
//                        if (obj.has("offer")) {
//                            val offer = obj.getJSONArray("offer")
//                            if (offer.length() > 0) {
//                                for (i in 0 until offer.length()) {
//                                    val offerObj = offer.getJSONObject(i)
//                                    var offerData = OfferData()
//                                    offerData.id = offerObj.getString("id")
//                                    offerData.image = offerObj.getString("image")
//                                    offerData.name = offerObj.getString("name")
//
//
//                                    val categories_brand = offerObj.getJSONArray("categories_brand")
//
//                                    var brandList: ArrayList<BrandOffersBrands> = ArrayList()
//
//                                    if (categories_brand.length() > 0) {
//
//                                        for (j in 0 until categories_brand.length()) {
//
//                                            Log.e("categories_brand", j.toString())
//
//                                            val categories_brandObj =
//                                                categories_brand.getJSONObject(j)
//
//                                            val brand_offer =
//                                                categories_brandObj.getJSONArray("brand_offer")
//
//                                            if (brand_offer.length() > 0) {
//
//                                                for (k in 0 until brand_offer.length()) {
//
//                                                    val offerr = BrandOffersBrands()
//                                                    val brand_offerObj =
//                                                        brand_offer.getJSONObject(k)
//
//                                                    offerr.image = brand_offerObj.getString("image")
//                                                    offerr.discount_percent =
//                                                        brand_offerObj.getString("discount_percent")
//                                                    offerr.offer_id = brand_offerObj.getString("id")
//
//                                                    var brandname =
//                                                        brand_offerObj.getJSONObject("brand_name_data")
//
//                                                    var brandName = BrandName()
//                                                    brandName.name =
//                                                        brandname.getString("brand_name")
//                                                    offerr.brandnameList.add(brandName)
//
//
//                                                    brandList.add(offerr)
//                                                    Log.e("whatsyoursize", "" + brandList.size)
//
//                                                }
//                                            }
//
//
//                                        }
//                                    }
//                                    offerData.offerArraylist.addAll(brandList)
//                                    mainOfferArrayList.add(offerData)
//
//                                    if (i == 0) {
//                                        rlOffer1.visibility = View.VISIBLE
//
//                                        setOfferAdapter1(
//                                            mainOfferArrayList[0].offerArraylist,
//                                            mainOfferArrayList[0].name
//                                        )
//                                    }
//                                    if (i == 1) {
//                                        rlOffer2.visibility = View.VISIBLE
//                                        setOfferAdapter2(
//                                            mainOfferArrayList[1].offerArraylist,
//                                            mainOfferArrayList[1].name
//                                        )
//                                    }
//                                    if (i == 2) {
//                                        rlOffer3.visibility = View.VISIBLE
//                                        setOfferAdapter3(
//                                            mainOfferArrayList[2].offerArraylist,
//                                            mainOfferArrayList[2].name
//                                        )
//
//                                    }
//
//
//                                    if(mainOfferArrayList.size>3) {
//                                        rlCategoriesBrand.visibility = View.VISIBLE
//                                        setCategoryAdapter(mainOfferArrayList)
//                                    }
//                                    setCategoryListAdapter(mainOfferArrayList)
//
//                                    /*  if(i==1) {
//                                        setAdapter2(arrayBlogs[1].arrayCatBlogs, arrayBlogs[1].categories_name)
//                                    }
//
//                                    if(i==2) {
//                                        setAdapter3(arrayBlogs[2].arrayCatBlogs, arrayBlogs[2].categories_name)*/
//                                }
//                            }
//
//           //                 Log.e("djkvdsvndd", "" + mainOfferArrayList[0].name)
//                        }
//
//                        if (obj.has("blogs")) {
//
//                            var blogs = obj.getJSONArray("blogs")
//                            if (blogs.length() > 0) {
//                                for (i in 0 until blogs.length()) {
//                                    var blogsobj = blogs.getJSONObject(i)
//                                    var blogdata = BlogsinBrandData()
//                                    blogdata.id = blogsobj.getString("id")
//                                    blogdata.categories_name = blogsobj.getString("categories_name")
//
//                                    var categories_blog = blogsobj.getJSONArray("categories_blog")
//                                    if (categories_blog.length() > 0) {
//                                        for (j in 0 until categories_blog.length()) {
//                                            var catblogobj = categories_blog.getJSONObject(j)
//                                            var catBlogData = CatBlogData()
//                                            catBlogData.image = catblogobj.getString("image")
//                                            catBlogData.title = catblogobj.getString("title")
//                                            catBlogData.id = catblogobj.getString("id")
//                                            blogdata.catBlogList.add(catBlogData)
//                                        }
//                                    }
//                                    blogArrayList.add(blogdata)
//                                }
//                            }
//                            Log.e("dhiwefwef",""+blogArrayList.size)
//                            if(blogArrayList.size>0) {
//                                rlBlogsinoffer.visibility=View.VISIBLE
//                                setBrandsAdapter(blogArrayList[0].catBlogList,blogArrayList[0].categories_name)
//                            }
//                        }
//                    }
//                }
//            }
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    private fun setOfferAdapter3(offerArraylist: ArrayList<BrandOffersBrands>, name: String) {
//        tvoffers3.text = name
//        Log.e("whatsyoursize3", "" + offerArraylist.size)
//        val staggeredGridLayoutManager = GridLayoutManager(this,2)
//        rycOffers3?.layoutManager = staggeredGridLayoutManager
//        val allBlogSettingsAdapter = OfferAdapter3(this, offerArraylist)
//        rycOffers3?.adapter = allBlogSettingsAdapter
//    }
//
//    private fun setOfferAdapter1(
//        offerArraylist: ArrayList<BrandOffersBrands>,
//        name: String
//    ) {
//        Log.e("whatsyoursize1", "" + offerArraylist.size)
//        tvoffers1.text = name
//
//        var offeradapter1 = OfferAdapter1(this, offerArraylist)
//        ryc_offer1.adapter = offeradapter1
//    }
//
//    private fun setOfferAdapter2(
//        offerArraylist: ArrayList<BrandOffersBrands>,
//        name: String
//    ) {
//        Log.e("whatsyoursize2", "" + offerArraylist.size)
//
//        tvoffers2.text = name
//        var offerAdapter2 = OfferAdapter2(this, offerArraylist)
//        OfferAdapter2.adapter = offerAdapter2
//
//    }
//
//    private fun setSliderAdapter(
//        blogviewPager: ViewPager,
//        arraySlider: ArrayList<SliderBrands>
//    ) {
//        Log.e("vsdvddgdvvvv", "" + arraySlider.size)
//        val blogViewPagerAdapter = BrandViewPagerAdapter(this, arraySlider)
//        brandPager.adapter = blogViewPagerAdapter
//
//    }
//
//
//    private fun callService() {
//            if(Alerts.isNetworkAvailable(this)){
//        try {
//
//            RetrofitService(
//                this,
//                this@BrandsHome,
//                WebUrls.SHOW_OFFERS + PreferenceFile.getInstance().getPreferenceData(
//                    this,
//                    WebUrls.KEY_USERID
//                ),
//                WebUrls.SHOW_OFFERS_CODE,
//                1
//            ).callService(true)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//            else{
//                Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//            }
//    }
//
//
//    fun setOnClickListener() {
//        rlEvent.setOnClickListener(this)
//        rlChat.setOnClickListener(this)
//        rlBrands.setOnClickListener(this)
//        rlBlogs.setOnClickListener(this)
//        llFaq.setOnClickListener(this)
//        llMyAccount.setOnClickListener(this)
//        llBlogSettings.setOnClickListener(this)
////        tvBrandsVA.setOnClickListener(this)
////        tvofferVA2.setOnClickListener(this)
////        tvlatestBlogView.setOnClickListener(this)
//        tvofferVA1.setOnClickListener(this)
//        tvofferVA3.setOnClickListener(this)
//        llHelp.setOnClickListener(this)
//        llChatSettings.setOnClickListener(this)
//        tvcategories.setOnClickListener(this)
//        llBrandsSettings.setOnClickListener(this)
////        tvCategoriesView.setOnClickListener(this)
////        llBlogSettings.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View?) {
//
//        super.onClick(v)
//
//        when (v?.id) {
//
//            R.id.ivNoti -> {
//                startActivity(Intent(this, NotificationListing::class.java))
//            }
//
//            R.id.rlEvent -> {
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//
//            R.id.rlChat -> {
//                val intent = Intent(this, ChatListing::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//            R.id.rlBlogs -> {
//                val intent = Intent(this, Blog::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llFaq -> {
//                val intent = Intent(this, Faq::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llMyAccount -> {
//                val intent = Intent(this, MyProfile::class.java)
//                startActivity(intent)
//            }
//
//            R.id.tvBrandsVA -> {
//                val intent = Intent(this, ViewAllBlogs::class.java)
//                intent.putExtra("category_id", blogArrayList[0].id)
//                startActivity(intent)
//            }
//
//            R.id.tvCategoriesView -> {
//                val intent = Intent(this, ViewAllBrands::class.java)
//                startActivity(intent)
//            }
//
//            R.id.tvofferVA1 -> {
//                val intent = Intent(this, TrendingOffers::class.java)
//                intent.putExtra("offer_id", mainOfferArrayList[0].id)
//                intent.putExtra("name", mainOfferArrayList[0].name)
//                startActivity(intent)
//            }
//            R.id.tvofferVA2 -> {
//                val intent = Intent(this, TrendingOffers::class.java)
//                intent.putExtra("offer_id", mainOfferArrayList[1].id)
//                intent.putExtra("name", mainOfferArrayList[1].name)
//                startActivity(intent)
//            }
//
//
//            R.id.tvofferVA3 -> {
//                val intent = Intent(this, TrendingOffers::class.java)
//                intent.putExtra("offer_id", mainOfferArrayList[2].id)
//                intent.putExtra("name", mainOfferArrayList[2].name)
//                startActivity(intent)
//            }
//
//            R.id.tvlatestBlogView -> {
//                val intent = Intent(this, LatestBlog::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llHelp -> {
//                val intent = Intent(this, Help::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llChatSettings -> {
//                val intent = Intent(this, ChatSetting::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llBrandsSettings -> {
//                val intent = Intent(this, BrandSettings::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llBrandsSettings -> {
//                val intent = Intent(this, BlogSettings::class.java)
//                startActivity(intent)
//            }
//
//            R.id.llBlogSettings -> {
//                val intent = Intent(this, BlogSettings::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//
//            R.id.tvcategories -> {
//              //    popupMenu()
//
//                if (rv_catlistBrand.isShown) {
//                    rv_catlistBrand.visibility = View.GONE
//
//                } else {
//                    rv_catlistBrand.visibility = View.VISIBLE
//
//                }
//
//
//            }
//
//
//        }
//    }
//
//    private fun setCategoryListAdapter(mainOfferArrayList: ArrayList<OfferData>) {
//        val catlistAdapter = CatListBrandAdapter(this, mainOfferArrayList)
//
//        rv_catlistBrand?.adapter = catlistAdapter
//    }
//
//    /* fun popupMenu() {
//
//
//         val popupMenu = PopupMenu(this, tvcategories)
//         popupMenu.menuInflater.inflate(R.menu.item_menu, popupMenu.menu)
//         popupMenu.setOnMenuItemClickListener { item ->
//             when (item.itemId) {
//                 R.id.home -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//                 R.id.food -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//                 R.id.fashion -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//                 R.id.hair_beauty -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//                 R.id.music -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//                 R.id.travel -> {
//                     val intent = Intent(this, TrendingOffers::class.java)
//                     startActivity(intent)
//                 }
//             }
//             true
//         }
//         popupMenu.show()
//     }*/
//
//
//    private fun setCategoryAdapter(mainOfferArrayList: ArrayList<OfferData>) {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rycCategories?.layoutManager = layoutManager
//        val categoriesAdapter = BrandCategoriesAdapter(this, mainOfferArrayList)
//        rycCategories?.adapter = categoriesAdapter
//
//    }
//
//
//    private fun setBrandsAdapter(
//        blogArrayList: ArrayList<CatBlogData>,
//        categoriesName: String
//    ) {
//        tvoffers4.text = categoriesName
//        val brandsOffersAdapter = BrandsOffersAdapter(this, blogArrayList)
//        rycBrandOffers?.adapter = brandsOffersAdapter
//
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.cover_folow_blog_activity, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//
//    }
//
//
//    data class SliderBrands(
//        var image: String = "",
//        var brand_id: String = "",
//        var name: String = ""
//    )
//
//    data class OfferData(
//        var image: String = "",
//        var offer_image: String = "",
//        var id: String = "",
//        var categories_id: String = "",
//        var name: String = "",
//        var offerArraylist: ArrayList<BrandOffersBrands> = ArrayList()
//    )
//
//    data class BrandOffersBrands(
//        var image: String = "",
//        var discount_percent: String = "",
//        var offer_id: String = "",
//        var brandnameList: ArrayList<BrandName> = java.util.ArrayList()
//    )
//
//    data class BlogsinBrandData(
//        var id: String = "",
//        var categories_name: String = "",
//        var catBlogList: ArrayList<CatBlogData> = ArrayList()
//    )
//
//    data class CatBlogData(
//        var id: String = "",
//        var image: String = "",
//        var title: String = ""
//    )
//
//    data class BrandName(
//        var name: String = ""
//    )
//
//
//    override fun onBackPressed() {
//        try {
//            val intent = Intent(this, Home::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//            finishAffinity()
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//}
