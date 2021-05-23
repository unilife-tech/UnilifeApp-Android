//package unilife.com.unilife.blogs
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.res.ColorStateList
//import android.support.v4.content.ContextCompat
//import android.os.Bundle
//import android.support.v4.graphics.drawable.DrawableCompat
//import android.support.v4.view.ViewPager
//import android.support.v7.widget.LinearLayoutManager
//import android.widget.ImageView
//import kotlinx.android.synthetic.main.activity_blog.*
//import kotlinx.android.synthetic.main.bottom_bar.*
//import unilife.com.unilife.home.Home
//import kotlin.collections.ArrayList
//import android.util.Log
//import android.view.*
//import android.view.inputmethod.EditorInfo
//import android.widget.TextView
//import android.widget.TextSwitcher
//import kotlinx.android.synthetic.main.drawer_toolbar.*
//import unilife.com.unilife.R
//import org.json.JSONObject
//import unilife.com.unilife.AppDrawer
//import unilife.com.unilife.brands.TrendingOffers
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.retrofit.RetrofitResponse
//import unilife.com.unilife.retrofit.RetrofitService
//import unilife.com.unilife.retrofit.WebUrls
//import java.io.Serializable
//import java.lang.Exception
//import ZoomOutPageTransformer
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.IntentFilter
//import android.support.v4.content.LocalBroadcastManager
//import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer
//import kotlinx.android.synthetic.main.drawer_toolbar.llToolBar
//import unilife.com.unilife.notification.NotificationListing
//import unilife.com.unilife.utils.Alerts
//import unilife.com.unilife.utils.Common
//
//
//class BlogOld : AppDrawer(), View.OnClickListener, RetrofitResponse {
//
//    var arraySlider : ArrayList<SliderData> = ArrayList()
//    var arrayBlogs : ArrayList<BlogerData> = ArrayList()
//    var arrayOffer : ArrayList<OfferData> = ArrayList()
//    var arrayTeam : ArrayList<TeamData> = ArrayList()
//    var value = ""
//
//    var offerAdapter:OfferinBlogAdapter?=null
//    var blogViewPagerAdapter : BlogViewPagerAdapter?= null
//
//
//
//    private var adapter: CoverFlowAdapter? = null
//    private var coverFlowProfileAdapter: CoverFlowProfileAdapter? = null
//
//    private var item: ArrayList<Item>? = null
//    private var mData: ArrayList<BlogItem>? = ArrayList<BlogItem>(0)
//    private var mData1: ArrayList<BlogItem>? = ArrayList<BlogItem>(0)
//    private var dotscount: Int = 0
//    private var dots: Array<ImageView>? = null
//    private var mTitle: TextSwitcher? = null
//    var searchtext = ""
//
//    private var imageModelArrayList: ArrayList<ImageModel>? = null
//    lateinit var blogviewPager: ViewPager
//    private val myImageList = intArrayOf(R.drawable.sign375, R.drawable.sign375, R.drawable.sign375)
//
//    @SuppressLint("ResourceType")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_blog)
//
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
//        blogviewPager = findViewById(R.id.blogPager)
//
//        ivBlogs.setImageDrawable(
//            ContextCompat.getDrawable(
//                applicationContext, // Context
//                R.drawable.ic_blog_blue
//            )
//
//        )
//
//        DrawableCompat.setTint(
//            DrawableCompat.wrap(ivEvent.drawable),
//            ContextCompat.getColor(this, R.color.medium_grey)
//        )
//
//        tvMainTitle.text="Unilife Blogs"
//        tvMainTitle.setTextColor(resources.getColor(R.color.black))
//        llToolBar.setBackgroundColor(resources.getColor(R.color.white))
//        ivNoti.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.black))
//
//        tvBlog.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
//
//        mTitle = findViewById<View>(R.id.title) as TextSwitcher?
//
//        mTitle?.setFactory {
//            val inflater = LayoutInflater.from(this)
//            inflater.inflate(R.layout.item_title, null) as TextView
//        }
//
//
//        etSearch.setOnEditorActionListener { v, actionId, event ->
//
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                searchtext = etSearch.text.toString()
//                startActivity(Intent(this,SearchResult::class.java)
//                    .putExtra("search",searchtext)
//                    .putExtra("value","Blogs"))
//            }
//            true
//        }
//
//     //   setOfferAdapter()
//
//        callBlogData()
//
//        //         setTeamAdapter(arrayTeam)
//
//        setOnClickListener()
//
//        LocalBroadcastManager.getInstance(this@BlogOld)
//            .registerReceiver(newBlog, IntentFilter("New Blog has arrived"))
//
//
//    }
//
//    internal var newBlog: BroadcastReceiver = object : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//
//            callBlogData()
//
//        }
//    }
//
//    private fun callBlogData() {
//        if(Alerts.isNetworkAvailable(this)){
//        RetrofitService(
//            this, this, WebUrls.BLOG_DATA + PreferenceFile.getInstance().getPreferenceData(
//                this@BlogOld, WebUrls.KEY_USERID), WebUrls.BLOG_DATA_CODE, 1).callService(true)
//    }
//        else{
//            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
//        }
//    }
//
//    private fun setSliderAdapter(
//        blogviewPager: ViewPager,
//        arraySlider: ArrayList<SliderData> ){
//
//        if (arraySlider != null) {
//            blogViewPagerAdapter = BlogViewPagerAdapter(this,arraySlider)
//            blogviewPager.adapter = blogViewPagerAdapter
//            blogviewPager.setPageTransformer(true, ZoomOutPageTransformer(true))
//
//            //Necessary or the pager will only have one extra page to show
//            // make this at least however many pages you can see
////        pager.setOffscreenPageLimit(adapter.getCount());
//            blogviewPager.offscreenPageLimit = 3
//            //A little space between pages
//            //pager.setPageMargin(resources.getDimension(R.dimen.dimen_20).toInt())
//            //If hardware acceleration is enabled, you should also remove
//            // clipping on the pager for its children.
//            blogviewPager.clipChildren = false
//        }
//    }
//
//    fun setTeamAdapter(teamList : ArrayList<TeamData> ) {
//
//        carousel.transformer = FlatMerryGoRoundTransformer()
//
//        carousel.isInfinite = true
//
//
//        /*carousel.transformer = F*/
//
//
//
//
//        val dataAdapter = MyCarouselAdapter(this,teamList)
//        carousel.adapter = dataAdapter
//        //carousel.scrollToPosition(3)
//        //carousel.smoothScrollToPosition(10,)
//        /*carousel.scrollX = 2
//        carousel.scrollY = 2*/
//
//
//    }
//
//    /*  @SuppressLint("ResourceType")
//      private fun setTeamAdapter(arrayTeam: ArrayList<TeamData>) {
//
//
//          carousel.transformer = FlatMerryGoRoundTransformer()
//          carousel.setAdapter = CarouselAdapter(this)
//  *//*        val `in` = AnimationUtils.loadAnimation(this, R.xml.slide_in_top)
//        val out = AnimationUtils.loadAnimation(this, R.xml.slide_out_bottom)
//        mTitle?.inAnimation = `in`
//        mTitle?.outAnimation = out
//
//
//        coverFlowProfileAdapter = CoverFlowProfileAdapter(this)
//        coverFlowProfileAdapter?.setData(mData1,arrayTeam)
//        imgcoverflow.adapter = coverFlowProfileAdapter
//
//        imgcoverflow.onItemClickListener =
//            AdapterView.OnItemClickListener { parent, view, position, id ->
//
//            }
//
//        imgcoverflow.setOnScrollPositionListener(object :
//            FeatureCoverFlow.OnScrollPositionListener {
//            override fun onScrolledToPosition(position: Int) {
//                mTitle?.setText(mData?.get(position)?.titleResId?.let { resources.getString(it) })
//            }
//
//            override fun onScrolling() {
//                mTitle?.setText("")
//            }
//        })*//*
//
//    }*/
//
//    fun setOnClickListener() {
//
//        rlEvent.setOnClickListener(this)
//        tvCategoriesView.setOnClickListener(this)
//        tvElectronicView.setOnClickListener(this)
//        tv_ViewAll1.setOnClickListener(this)
//        tvViewAll3.setOnClickListener(this)
//        tv_ViewAll2.setOnClickListener(this)
//        btn_categories.setOnClickListener(this)
//
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
//            R.id.btn_categories -> {
//
//                if(rv_catlist.isShown){
//                    rv_catlist.visibility = View.GONE
//
//                }else{
//                    rv_catlist.visibility = View.VISIBLE
//
//                }
//
//                setCatListAdapter()
//            }
//
//            R.id.rlEvent -> {
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent)
//            }
//
//
//            R.id.tv_ViewAll1 -> {
//                if(arrayBlogs!=null){
//                    val intent = Intent(this, ViewAllBlogs::class.java)
//                    intent.putExtra("category_id", arrayBlogs[0].id)
//                    startActivity(intent)
//                }
//            }
//
//
//            R.id.tvViewAll3 -> {
//                if(arrayBlogs!=null) {
//                    val intent = Intent(this, ViewAllBlogs::class.java)
//                    intent.putExtra("category_id", arrayBlogs[2].id)
//                    startActivity(intent)
//                }
//            }
//
//            R.id.tv_ViewAll2 -> {
//                if(arrayBlogs!=null) {
//                    val intent = Intent(this, ViewAllBlogs::class.java)
//                    intent.putExtra("category_id", arrayBlogs[1].id)
//                    startActivity(intent)
//                }
//            }
//
//            R.id.tvCategoriesView -> {
//
//                val intent = Intent(this, ViewAllCategories::class.java)
//                startActivity(intent)
//            }
//
//            R.id.tvElectronicView -> { //4
//                val intent = Intent(this, TrendingOffers::class.java)
//                startActivity(intent)
//            }
//
//
//        }
//    }
//
//    private fun setCatListAdapter() {
//        val catlistAdapter = CatListAdapter(this@BlogOld, arrayBlogs)
//        rv_catlist?.adapter = catlistAdapter
//
//    }
//
//    private fun setAdapter1(arrayBlogs: ArrayList<CategoriesBlogData>, catName:String) {
//
//        tvSportsname.text = catName
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycSportsUpdates?.layoutManager = layoutManager
//        val sportsAdapter = BlogAdapter1(this@BlogOld, arrayBlogs)
//        rycSportsUpdates?.adapter = sportsAdapter
//
//    }
//
//    private fun setAdapter2(arrayBlogs: ArrayList<CategoriesBlogData>, catName:String) {
//
//        tvVideoview.text = catName
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycRecentVideo?.layoutManager = layoutManager
//        val videoAdapter = BlogAdapter2(this@BlogOld, arrayBlogs)
//        rycRecentVideo?.adapter = videoAdapter
//
//    }
//
//    private fun setAdapter3(arrayBlogs: ArrayList<CategoriesBlogData>, catName:String) {
//
//        tvNewsview.text = catName
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycLatestNews?.layoutManager = layoutManager
//        val blogNewsAdapter = BlogAdapter3(this@BlogOld, arrayBlogs)
//        rycLatestNews?.adapter = blogNewsAdapter
//
//    }
//
//    private fun setOfferAdapter(arrayOffer: ArrayList<OfferData>) {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rycElectronicOffers?.layoutManager = layoutManager
//        offerAdapter = OfferinBlogAdapter(this@BlogOld,arrayOffer)
//        rycElectronicOffers?.adapter = offerAdapter
//
//    }
//
//    private fun setCategoryAdapter(arrayBlogs: ArrayList<BlogerData>) {
//
//        val layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        rycCategories?.layoutManager = layoutManager
//        val categoriesAdapter = BlogCategoriesAdapter(this,arrayBlogs)
//        rycCategories?.adapter = categoriesAdapter
//
//    }
//
//    override fun onResponse(requestCode: Int, response: String) {
//
//        super.onResponse(requestCode, response)
//        try {
//            when(requestCode)
//            {
//
//                WebUrls.BLOG_DATA_CODE->{
//
//                    val obj = JSONObject(response)
//
//                    Log.e("BLOG_DATA_CODE",obj.toString())
//
//                    rlBlogMain.visibility = View.VISIBLE
//
//                    if(obj.getBoolean("response"))
//                    {
//
//                        arraySlider.clear()
//                        arrayBlogs.clear()
//                        arrayOffer.clear()
//                        arrayTeam.clear()
//
//                        if(obj.has("slider"))
//                        {
//
//                            val slider = obj.getJSONArray("slider")
//
//                            for(i in 0 until slider.length())
//                            {
//                                val sliderObj = slider.getJSONObject(i)
//                                val sliderData = SliderData()
//                                sliderData.image = sliderObj.getString("image")
//                                sliderData.title = sliderObj.getString("title")
//                                sliderData.description = sliderObj.getString("description")
//                                sliderData.video_link = sliderObj.getString("video_link")
//                                sliderData.id = sliderObj.getString("id")
//
//                                arraySlider.add(sliderData)
//                            }
//                            if(arraySlider.size>0) {
//                                blogPager.visibility = View.VISIBLE
//                                setSliderAdapter(blogviewPager, arraySlider)
//                                indicator.setViewPager(blogviewPager)
//                            }
//                        }
//
//                        if (obj.has("blogs")) {
//
//                            val blogs = obj.getJSONArray("blogs")
//
//                            for (j in 0 until blogs.length()) {
//
//                                val blogsObj = blogs.getJSONObject(j)
//
//                                val blogsData = BlogerData()
//
//                                blogsData.id = blogsObj.getString("id")  // categoryId
//                                blogsData.categories_image = blogsObj.getString("categories_image")
//                                blogsData.categories_name = blogsObj.getString("categories_name")
//
//                                val catblog = blogsObj.getJSONArray("categories_blog")
//
//                                if (catblog.length() > 0) {
//
//                                    for (k in 0 until catblog.length()) {
//
//                                        var catblogobj = catblog.getJSONObject(k)
//
//                                        var categoriesBlogData = CategoriesBlogData()
//
//                                        categoriesBlogData.description =
//                                            catblogobj.getString("description")
//
//                                        categoriesBlogData.id = catblogobj.getString("id")
//
//                                        categoriesBlogData.image = catblogobj.getString("image")
//
//                                        categoriesBlogData.title = catblogobj.getString("title")
//
//                                        categoriesBlogData.video_link =
//                                            catblogobj.getString("video_link")
//
//                                        categoriesBlogData.sharedby =
//                                            catblogobj.getString("shared_by")
//
//                                        blogsData.arrayCatBlogs.add(categoriesBlogData)
//
//                                    }
//
//                                    arrayBlogs.add(blogsData)
//
//                                    if(arrayBlogs.size>0) {
//                                        if (j == 0) {
//                                            tvSportsname.visibility = View.VISIBLE
//                                            tv_ViewAll1.visibility = View.VISIBLE
//                                            rycSportsUpdates.visibility = View.VISIBLE
//                                            heyview1.visibility = View.VISIBLE
//                                            iv1.visibility = View.VISIBLE
//                                            v1.visibility = View.VISIBLE
//
//                                            setAdapter1(arrayBlogs[0].arrayCatBlogs, arrayBlogs[0].categories_name)
//                                        }
//                                    }
//                                    if(arrayBlogs.size>1) {
//                                        if (j == 1) {
//                                            Log.e("czcfzxss","dczfvxfsx")
//                                            rlBlog3.visibility=View.VISIBLE
//                                            rycLatestNews.visibility = View.VISIBLE
//                                            heyview3.visibility = View.VISIBLE
//                                            iv2.visibility = View.VISIBLE
//                                            v2.visibility = View.VISIBLE
//
//                                            setAdapter2(arrayBlogs[1].arrayCatBlogs, arrayBlogs[1].categories_name)
//                                        }
//                                    }
//                                    if(arrayBlogs.size>2) {
//                                        if (j == 2) {
//                                            Log.e("czcfzx","dczfvxfsx")
//                                            rycElectronicOffers.visibility = View.VISIBLE
//                                            iv3.visibility = View.VISIBLE
//                                            v3.visibility = View.VISIBLE
//                                            rlCat.visibility = View.VISIBLE
//                                            setAdapter3(arrayBlogs[2].arrayCatBlogs, arrayBlogs[2].categories_name)
//                                        }
//                                    }
//
//                                    if(arrayBlogs.size>3) {
//                                        rycCategories.visibility=View.VISIBLE
//                                        setCategoryAdapter(arrayBlogs)
//                                    }
//                                }
//                            }
//                        }
//
//                        if(obj.has("team")) {
//                            val team = obj.getJSONArray("team")
//                            for (j in 0 until team.length()) {
//                                val teamObj = team.getJSONObject(j)
//                                val teamData = TeamData()
//
//                                teamData.image = teamObj.getString("image")
//                                teamData.name = teamObj.getString("name")
//
//                                arrayTeam.add(teamData)
//                            }
//                            if (arrayTeam.size > 0) {
//                                carousel.visibility = View.VISIBLE
//                                setTeamAdapter(arrayTeam)
//                            }
//                        }
//
//
//
//                        if (obj.has("offer"))
//                        {
//
//                            if(!obj.isNull("offer"))
//                            {
//                                val jArrayOffer = obj.getJSONArray("offer")
//
//                                if(jArrayOffer.length()>0)
//                                {
//
//                                    for (j in 0 until jArrayOffer.length()) {
//                                        val offerObj = jArrayOffer.getJSONObject(j)
//                                        val offerData = OfferData()
//                                        offerData.name= offerObj.getString("name")
//                                        offerData.id= offerObj.getString("id")
//                                        offerData.image=offerObj.getString("image")
//                                        val categories_brand = offerObj.getJSONArray("categories_brand")
//
//                                        var brandOfferList = ArrayList<BrandOfferData>()
//
//                                        for(k in 0 until categories_brand.length())
//                                        {
//                                            val categories_brandObj = categories_brand.getJSONObject(k)
//                                            val catBlogOfferData = CatBlogOfferData()
//
//                                            catBlogOfferData.brand_name=categories_brandObj.getString("brand_name")
//                                            catBlogOfferData.description=categories_brandObj.getString("description")
//                                            catBlogOfferData.image=categories_brandObj.getString("image")
//
//                                            val brand_offers = categories_brandObj.getJSONArray("brand_offer")
//
//                                            for (l in 0 until brand_offers.length())
//                                            {
//                                                val brandOffersObj = brand_offers.getJSONObject(l)
//                                                val brandOfferData = BrandOfferData()
//                                                brandOfferData.description=brandOffersObj.getString("description")
//                                                brandOfferData.discount_code=brandOffersObj.getString("discount_code")
//                                                brandOfferData.discount_percent=brandOffersObj.getString("discount_percent")
//                                                brandOfferData.discount_type=brandOffersObj.getString("discount_type")
//                                                brandOfferData.image=brandOffersObj.getString("image")
//                                                brandOfferData.term_condition=brandOffersObj.getString("term_condition")
//                                                brandOfferData.type=brandOffersObj.getString("type")
//                                                brandOfferData.id=brandOffersObj.getString("id")
//                                                //               brandOfferData.name=brandOffersObj.getString("name")
//
//                                                brandOfferList.add(brandOfferData)
//
//                                                //    brandimagelist.add(brandOfferData.image)
//                                            }
//                                            offerData.catBlogOfferList.add(catBlogOfferData)
//                                        }
//
//                                        offerData.brandOfferList.addAll(brandOfferList)
//
//                                        arrayOffer.add(offerData)
//
//                                    }
//                                }
//                            }
//                         /*   if(offerAdapter!=null)
//                            {
//
//                                //offerAdapter!!.notifyDataSetChanged()
//                            }*/
//                            if(arrayOffer.size>0){
//                                iv4.visibility = View.VISIBLE
//                                v4.visibility = View.VISIBLE
//                                rycElectronicOffers.visibility = View.VISIBLE
//
//                                setOfferAdapter(arrayOffer)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
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
//        val id = item.getItemId()
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//
//    }
//
//    data class SliderData (var id : String = "",var categories_id : String = "",
//                           var title : String = "",var description : String = "",
//                           var image : String = "",var video_link : String = "")
//
//
//    data class BlogerData (var id : String = "", var categories_name : String = "",
//                           var categories_image : String = "",
//                           var arrayCatBlogs : ArrayList<CategoriesBlogData> = ArrayList()
//    ) :Serializable
//
//    data class TeamData (var id : String = "",var name : String = "",var image : String = "",
//                         var description : String = "")
//
//    data class CategoriesBlogData(var id : String = "", var title : String = "", var image: String= "",
//                                  var sharedby : String = "" ,
//                                  var description: String = "", var video_link: String= "") : Serializable
//
//    data class OfferData (var name : String = "",
//                          var image : String = "",
//                          var id : String = "",
//                          var catBlogOfferList: ArrayList<CatBlogOfferData> = ArrayList(),
//                          var brandOfferList : ArrayList<BrandOfferData> = ArrayList()
//    ) : Serializable
//
//    data class CatBlogOfferData(var brand_name : String = "",
//                                var image : String = "",
//                                var description : String = ""
//    ) : Serializable
//
//    data class BrandOfferData( var id : String = "",
//                               var type : String = "",
//                               var name : String = "",
//                               var discount_type: String = "",
//                               var discount_percent: String = "",
//                               var discount_code: String = "",
//                               var description: String = "",
//                               var term_condition: String = "",
//                               var image: String = "" ) : Serializable
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
//
