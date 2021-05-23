//package unilife.com.unilife.home
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.Intent
//import android.support.v4.view.ViewPager
//import android.support.v7.widget.CardView
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.text.Html
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.View.GONE
//import android.view.View.VISIBLE
//import android.view.ViewGroup
//import android.widget.*
//import com.squareup.picasso.Picasso
//import me.relex.circleindicator.CircleIndicator
//import unilife.com.unilife.brands.TrendingOffers
//import unilife.com.unilife.PreferenceFile
//import unilife.com.unilife.R
//import unilife.com.unilife.blogs.ViewAllBlogs
//import unilife.com.unilife.retrofit.WebUrls
//import java.text.SimpleDateFormat
//import java.util.*
//import kotlin.collections.ArrayList
//
//class HomeAdapter(internal var context: Context,  var homeList: ArrayList<HomeModel>) :
//    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
//
//    var pos:Int=0
//    var now: Long = 0
//    var time:Long = 0
//    val TAG = HomeAdapter::class.java.simpleName
//    lateinit var onItemClick: onItemClickListener
//
//    init {
//        this.homeList = homeList
//        this.context = context
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
//        val view =
//            LayoutInflater.from(viewGroup.context).inflate(R.layout.home_adapter, viewGroup, false)
//        return MyViewHolder(view)
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) = try {
//   //     Log.e("homelistwsdcfc",""+homeList[i].blogOffersList!!.size)
//
//        when(homeList[i].type){
//            "offers" ->{
//                if (homeList[i].brandOffersList != null){
//                    myViewHolder.llTrendingOffers.visibility = VISIBLE
//                    myViewHolder.cvpost.visibility = GONE
//                    myViewHolder.llBlog.visibility = GONE
//                    setAddAdapter(myViewHolder.rycAdd, homeList[i].brandOffersList, homeList[i].categoriesBrandList)
//                    myViewHolder.tvOffersname!!.text = homeList[i].categoriesBrandList!![0].offername
//
//                    myViewHolder.tvTrandingView.setOnClickListener {
//                        context.startActivity(Intent(context, TrendingOffers::class.java)
//                            .putExtra("offer_id", homeList[i].categoriesBrandList!![0].offerid)
//                            .putExtra("name", homeList[i].categoriesBrandList!![0].offername)
//                        )
//                    }
//                }
//                else{
//
//                }
//            }
//            "blogs"->{
//                Log.e("homelistwsdcfc","3rweferf")
//                if(homeList[i].blogOffersList != null){
//                    myViewHolder.llBlog.visibility = VISIBLE
//                    myViewHolder.llTrendingOffers.visibility = GONE
//                    myViewHolder.cvpost.visibility = GONE
//                    setBlogAdapter(myViewHolder.rycBlog,homeList[i].blogOffersList)
//                    myViewHolder.tvBlogname.text = homeList[i].blogOffersList!![0].category_name
//
//                    myViewHolder.tvBlogView.setOnClickListener {
//                        context.startActivity(Intent(context,ViewAllBlogs::class.java)
//                            .putExtra("category_id", homeList[i].blogOffersList!![0].categories_id)
//                        )
//
//
//                    }
//                }
//                else{
//
//                }
//            }
//
//
//            else ->{
//                myViewHolder.llTrendingOffers.visibility = GONE
//                myViewHolder.llBlog.visibility = GONE
//                myViewHolder.cvpost.visibility = VISIBLE
//
//
//                Log.e("sbjkxcasvbce",""+homeList[i].group_image)
//
//                if(homeList[i].postUserId == PreferenceFile.getInstance().getPreferenceData(context,WebUrls.KEY_USERID)){
//                    myViewHolder.rlDeletePost.visibility = VISIBLE
//                }
//                else{
//                    myViewHolder.rlDeletePost.visibility = GONE
//
//                }
//
//                if(homeList[i].postUserId != PreferenceFile.getInstance().getPreferenceData(context,WebUrls.KEY_USERID)){
//                    myViewHolder.ivSend.visibility = VISIBLE
//                }
//                else{
//                    myViewHolder.ivSend.visibility = GONE
//
//                }
//
//
//                if (homeList[i].profile_image.isNotEmpty()) {
//                    Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL + homeList[i].profile_image)
//                        .placeholder(R.drawable.no_image).into(myViewHolder.ivProfileImg)
//                } else {
//                    myViewHolder.ivProfileImg.setImageResource(R.drawable.no_image)
//                }
//
//
//            // POST THROUGH GROUP
//                if(homeList[i].post_through_group == "yes"){
//                    myViewHolder.rlgroup.visibility = VISIBLE
//                    //   myViewHolder.rlUni.visibility = GONE
//                    Log.e("sbjkxcasvbce",""+homeList[i].group_image)
//                    myViewHolder.username.text = homeList[i].group_name
//                    myViewHolder.tvGroupmemberName.text = homeList[i].username
//                    Picasso.with(context).load(
//                        WebUrls.PROFILE_IMAGE_URL + homeList[i].group_image).placeholder(R.drawable.no_image)
//                        .into(myViewHolder.ivProfileImg)
//
//
//
//                }
//                else{
//
//                    myViewHolder.rlgroup.visibility = GONE
//                    myViewHolder.username.text = homeList[i].username
//                }
//
//
//
//                //   myViewHolder.username.text = homeList[i].username
//
//                // SETTING TIME ON POST
//               /* val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                now= System.currentTimeMillis()
//                sdf.timeZone = TimeZone.getTimeZone("GMT")
//                if (homeList[i].uploaded_at != "") {
//                    time = sdf.parse(homeList[i].uploaded_at).time
//                }
//                val mail = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
//                myViewHolder.tvtime.text = mail.toString()*/
//                calculateTimeDifference(homeList[i].uploaded_at,myViewHolder.tvtime)
//
//
//
//
//                //SET POST DATA
//                myViewHolder.tvusername.text =
//                    PreferenceFile.getInstance().getPreferenceData(context, WebUrls.KEY_USERNAME)
//                myViewHolder.tvemail.text =
//                    "@" + PreferenceFile.getInstance().getPreferenceData(context, WebUrls.KEY_USERNAME)
//
//                Picasso.with(context).load(
//                    WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance()
//                        .getPreferenceData(context, WebUrls.KEY_PROFILE_IMAGE)
//                ).placeholder(R.drawable.no_image).into(myViewHolder.ivuser)
//
//
//
//
//                if (homeList[i].caption.isNotEmpty()) {
//                    myViewHolder.caption.visibility = VISIBLE
//                        myViewHolder.caption.text = Html.fromHtml(homeList[i].caption)
//             //       myViewHolder.caption.loadData(homeList[i].caption,"text/html","UTF-8")
//                }
//                else {
//                    myViewHolder.caption.visibility = GONE
//                }
//
//                if(homeList[i].user_university_school_name != ""){
//                    myViewHolder.ivbeg.visibility =VISIBLE
//                    myViewHolder.universityname.text = "Studying at " + homeList[i].user_university_school_name
//
//                }else{
//                    myViewHolder.ivbeg.visibility =GONE
//
//                    myViewHolder.universityname.visibility = GONE
//
//                }
//
//
//
//                if (homeList[i].postAttachmentsList != null) {
//                    setPostImagesAdapter(myViewHolder.homeviewPager, homeList[i].postAttachmentsList)
//                    myViewHolder.indicator.setViewPager(myViewHolder.homeviewPager);
//                    if(homeList[i].postAttachmentsList!!.size>1){
//                        myViewHolder.indicator.visibility=VISIBLE
//                    }
//
//                } else {
//                    myViewHolder.ivImage.setImageResource(R.drawable.no_image)
//                }
//
//
////        Log.e("STATUS", homeList[i].UserPostLikesStatus+" : inside adapter")
//
//
////        if (homeList.get(i).userPostLikesList != null && homeList.get(i).userPostLikesList!!.size == 1) {
//                if (/*homeList[i].UserPostLikesStatus!=null &&*/  homeList[i].userlikedPost) {
//                    //make it red
//                    myViewHolder.ivLike.setBackgroundResource(R.drawable.red_heart)
////                    Log.e("LikeStatus","Liked")
//                } else {
//                    myViewHolder.ivLike.setBackgroundResource(R.drawable.ic_heart_new)
////                    Log.e("LikeStatus","UnLiked")
//                }
//
//                if (homeList[i].totalPostLikesList != null) {
//                    val totLikes = homeList[i].totalPostLikesList!!.size
//                    if (homeList[i].totalPostLikesList!!.size == 1) {
//                        myViewHolder.tvTotLikes.text = "$totLikes Like"
//                    } else {
//                        myViewHolder.tvTotLikes.text = "$totLikes Likes"
//                    }
//                } else {
//                    myViewHolder.tvTotLikes.text = "0 Like"
//                }
//
//                if (homeList[i].postCommentsList != null) {
//                    val totComments = homeList[i].postCommentsList!!.size
//                    if (homeList[i].postCommentsList!!.size == 1) {
//                        myViewHolder.tvTotComments.text = "View $totComments Comment"
//                    } else {
//                        myViewHolder.tvTotComments.text = "View All $totComments Comments"
//                    }
//                } else {
//                    myViewHolder.tvTotComments.text = "0 Comment"
//                }
//
//                // NAVIGATION TO COMMENT CLASS
//                myViewHolder.tvcomment.setOnClickListener {
//                    context.startActivity(Intent(context,Comment::class.java)
//                        .putExtra("post_id",homeList[i].id)
//                    )
//                }
//
//
//
//
//
//
//
//
//
//
//                myViewHolder.ivSend.setOnClickListener {
//
//                    onItemClick.onShareClick(i,homeList[i].id)
//
//
//                }
//
//                myViewHolder.ivLike.setOnClickListener {
//                    onItemClick.onLikeUnLikeClick(i, homeList[pos].UserPostLikesStatus,myViewHolder.ivLike,myViewHolder.tvTotLikes)
//                }
//
//
//                myViewHolder.ivProfileImg.setOnClickListener {
//                    Log.e("sxckldnndfsf",""+homeList[i].admin_id+"    "+homeList[i].post_user_id)
//
//                    if (homeList[i].post_user_id != PreferenceFile.getInstance().getPreferenceData(context,
//                            WebUrls.KEY_USERID)
//                    ) {
//                        if (homeList[i].post_user_id == "") {
//                            Log.e("bgbg", "dsggf")
//                            Toast.makeText(context, "Not Possible", Toast.LENGTH_SHORT).show()
//                        }
//                        else {
//                            Log.e("bgbg1", "dsggf1")
//
//                            context.startActivity(
//                                Intent(context, ShowOtherProfile::class.java)
//                                    .putExtra("post_user_id", homeList[i].post_user_id)
//                            )
//                        }
//                    }
//
//
//                    /*  else if(homeList[i].admin_id != "1321321131"){
//                          Toast.makeText(context,"Not Possible",Toast.LENGTH_SHORT).show()
//                      }*/
//
//                }
//                myViewHolder.username.setOnClickListener {
//                    context.startActivity(Intent(context,ShowOtherProfile::class.java)
//                        .putExtra("post_user_id",homeList[i].post_user_id)
//                    )
//
//                }
//
//                myViewHolder.rlDeletePost.setOnClickListener {
//                    onItemClick.onDeletePostClick(i,homeList[i].id)
//                }
//            }
//        }
//
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    override fun getItemCount(): Int {
//        return  homeList.size
//    }
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var ivbeg: ImageView
//        internal var ivImage: ImageView
//        internal var ivComment: ImageView
//        internal var ivuser: ImageView
//        internal var ivProfileImg: ImageView
//        internal var ivSend: ImageView
//        internal var homeviewPager: ViewPager
//        internal var rycAdd: RecyclerView
//        internal var rycBlog: RecyclerView
//        internal var tvTrandingView: TextView
//        internal var tvBlogname: TextView
//        internal var llTrendingOffers: LinearLayout
//        internal var llBlog: LinearLayout
//        internal var rlgroup: RelativeLayout
//        internal var rlUni: RelativeLayout
//        internal var rlDeletePost: RelativeLayout
//        internal var username: TextView
//        internal var tvBlogView: TextView
//        internal var universityname: TextView
//        internal var tvGroupmemberName: TextView
//        internal var caption: TextView
// //       internal var caption: WebView
//        internal var tvOffersname: TextView
//        internal var tvcomment: TextView
//        internal var tvtime: TextView
//        internal var tvemail: TextView
//        internal var tvusername: TextView
//        internal var cvpost: CardView
//        internal var ivLike: ImageView
//        internal var tvTotLikes: TextView
//        internal var tvTotComments: TextView
//        internal var indicator: CircleIndicator
//
//        init {
//
//            ivbeg = itemView.findViewById(R.id.ivbeg)
//            tvOffersname = itemView.findViewById(R.id.tvOffersname)
//            tvcomment = itemView.findViewById(R.id.tvcomment)
//            tvtime = itemView.findViewById(R.id.tvtime)
//            tvBlogView = itemView.findViewById(R.id.tvBlogView)
//            tvGroupmemberName = itemView.findViewById(R.id.tvGroupmemberName)
//            tvemail = itemView.findViewById(R.id.tvemail)
//            tvTotLikes = itemView.findViewById(R.id.tvTotLikes)
//            tvTotComments = itemView.findViewById(R.id.tvTotComments)
//            tvusername = itemView.findViewById(R.id.tvusername)
//            tvBlogname = itemView.findViewById(R.id.tvBlogname)
//            ivLike = itemView.findViewById(R.id.ivLike)
//            ivuser = itemView.findViewById(R.id.ivuser)
//            cvpost = itemView.findViewById(R.id.cvpost)
//
//            ivImage = itemView.findViewById(R.id.ivImage)
//            ivSend = itemView.findViewById(R.id.ivSend)
//            ivComment = itemView.findViewById(R.id.ivComment)
//            homeviewPager = itemView.findViewById(R.id.homeviewPager)
//            rycAdd = itemView.findViewById(R.id.rycAdd)
//            rycBlog = itemView.findViewById(R.id.rycBlog)
//            llTrendingOffers = itemView.findViewById(R.id.llTrendingOffers) as LinearLayout
//            llBlog = itemView.findViewById(R.id.llBlog) as LinearLayout
//            rlgroup = itemView.findViewById(R.id.rlgroup) as RelativeLayout
//            rlUni = itemView.findViewById(R.id.rlUni) as RelativeLayout
//            rlDeletePost = itemView.findViewById(R.id.rlDeletePost) as RelativeLayout
//            tvTrandingView = itemView.findViewById(R.id.tvTrandingView)
//            username = itemView.findViewById(R.id.tvname)
//            universityname = itemView.findViewById(R.id.tvUniversityname)
//            caption = itemView.findViewById(R.id.tvcaption)
//            ivProfileImg = itemView.findViewById(R.id.ivProfileImg)
//            indicator = itemView.findViewById(R.id.indicator)
//
//            tvTotComments.setOnClickListener {
//                onItemClick.onAllCommentClick(layoutPosition)
//            }
//
//            tvTotLikes.setOnClickListener {
//                onItemClick.onAllLikeClick(layoutPosition)
//            }
//
//          /*  ivLike.setOnClickListener {
//                onItemClick.onLikeUnLikeClick(layoutPosition, homeList[pos].UserPostLikesStatus,ivLike,tvTotLikes)
//            }*/
//
//            ivComment.setOnClickListener {
//                onItemClick.onAllCommentClick(layoutPosition)
//            }
//        }
//    }
//
//    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
//        this.onItemClick = onItemClick
//    }
//
//    public interface onItemClickListener {
//        public fun onLikeUnLikeClick(
//            position: Int,
//            statusLikeUnLike: String,
//            ivLike: ImageView,
//            tvTotLikes: TextView
//        )
//        public fun onAllLikeClick(position: Int)
//        public fun onAllCommentClick(position: Int)
//        public fun onShareClick(i: Int, id: String)
//        public fun onDeletePostClick(i: Int, id: String)
//    }
//
//    fun setAdapter(rycItem: RecyclerView, getdataAl: ArrayList<String>
//    ) {
//        val homePostAdapter = HomePostAdapter(context, getdataAl)
//        rycItem.adapter = homePostAdapter
//        rycItem.addItemDecoration(CirclePagerIndicatorDecoration())
//    }
//
//
//    private fun setPostImagesAdapter(
//        homeviewPager: ViewPager,
//        postImagesList: ArrayList<HomeModel.PostAttachments>?
//    ) {
//        if (postImagesList != null) {
//            val homePagerAdapter = HomePagerAdapter(context, postImagesList!!)
//            homeviewPager.adapter = homePagerAdapter
//        }
//    }
//
//    fun setAddAdapter(
//        rycAdd: RecyclerView,
//        brandOffersList: ArrayList<HomeModel.BrandOffers>?,
//        categoriesBrandList: ArrayList<HomeModel.CategoriesBrandModel>?
//    ) {
//        val linearLayoutManager = LinearLayoutManager(context)
//        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycAdd.layoutManager = linearLayoutManager
//        val homeAddAdapter = HomeAddAdapter(context,categoriesBrandList,brandOffersList)
//        rycAdd.adapter = homeAddAdapter
//    }
//
//    private fun setBlogAdapter(rycBlog: RecyclerView, blogOffersList: ArrayList<HomeModel.BlogModel>?) {
//        val linearLayoutManager = LinearLayoutManager(context)
//        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        rycBlog.layoutManager = linearLayoutManager
//        val homeAddAdapter = HomeBlogAdapter(context,blogOffersList)
//        rycBlog.adapter = homeAddAdapter
//
//    }
//    fun calculateTimeDifference(strEndDate: String, textView: TextView)
//    {
//        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//
//        inputDateFormat.timeZone = TimeZone.getTimeZone("GMT")
//        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//
//        try {
//
//            /*todo get current date time and change its format*/
//            val calendar = Calendar.getInstance()
//            val startDate = calendar.time
//
//            val mySdate = outputDateFormat.format(startDate)
//            val dateStart = outputDateFormat.parse(mySdate)
//
//            /*------------------------------------*/
//
//            /*todo change date time format of end date*/
//            if(strEndDate.isNotEmpty()) {
//                val sDate = inputDateFormat.parse(strEndDate)
//
//                inputDateFormat.timeZone = TimeZone.getDefault()
//
//                val myFormattedDate = outputDateFormat.format(sDate!!)
//
//                val dateEnd = outputDateFormat.parse(myFormattedDate)
//
//                getTimeDifference(dateEnd!!, dateStart!!, textView)
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//
//    fun getTimeDifference(startDate: Date, endDate: Date, textView : TextView) {
//
//        var different: Long = endDate.time - startDate.time
//
//        println("startDate : $startDate")
//        println("endDate : $endDate")
//        println("different : $different")
//
//        val secondsInMilli: Long  = 1000
//        val minutesInMilli: Long  = secondsInMilli * 60
//        val hoursInMilli : Long   = minutesInMilli * 60
//        val daysInMilli : Long    = hoursInMilli * 24
//        val month : Long          = daysInMilli * 30
//
//
//        val  ellapsedmonth: Long = different / month
//        different %= month
//
//        val  ellapsedDays: Long  = different / daysInMilli
//        different %= daysInMilli
//
//        val  ellapsedHours: Long  = different / hoursInMilli
//        different %= hoursInMilli
//
//        val  ellapsedMinutes: Long  = different / minutesInMilli
//        different %= minutesInMilli
//
//        val ellapsedSeconds: Long  = different / secondsInMilli;
//
//        if (ellapsedmonth > 0) {
//
//            //textView.text =(ellapsedmonth).toString() + " month" + "-" + ellapsedDays + " days ago"
//            textView.text =(ellapsedmonth).toString() + "month"
//
//        } else if (ellapsedDays > 0) {
//            if (ellapsedDays > 1) {
//                //textView.text =  (ellapsedDays).toString() + "days ago"
//                textView.text =  (ellapsedDays).toString() + "days"
//
//            } else {
//                // textView.text = (ellapsedDays).toString() + " day ago"
//                textView.text = (ellapsedDays).toString() + "day"
//            }
//        } else if (ellapsedHours > 0) {
//            if (ellapsedHours > 1) {
//                //textView.text = (ellapsedHours).toString() + " hours ago"
//                textView.text = (ellapsedHours).toString() + "hours"
//            } else {
//                //textView.text = (ellapsedHours).toString() + " hour ago"
//                textView.text = (ellapsedHours).toString() + "hour"
//            }
//
//        } else if (ellapsedMinutes > 0) {
//            if (ellapsedMinutes > 1) {
//                //textView.text = (ellapsedMinutes).toString() + " min ago"
//                textView.text = (ellapsedMinutes).toString() + "mins"
//            } else {
//                //textView.text = (ellapsedMinutes).toString() + " min ago"
//                textView.text = (ellapsedMinutes).toString() + "min"
//            }
//
//        } else if (ellapsedSeconds > 0) {
//            if (ellapsedSeconds > 1) {
//                //textView.text = (ellapsedSeconds).toString() + " sec ago"
//                textView.text = (ellapsedSeconds).toString() + "secs"
//            } else {
//                //textView.text = (ellapsedSeconds).toString() + " sec ago"
//                textView.text = (ellapsedSeconds).toString() + "sec"
//            }
//        } else {
//            textView.text = "0"
//        }
//    }
//
//}
//
//
