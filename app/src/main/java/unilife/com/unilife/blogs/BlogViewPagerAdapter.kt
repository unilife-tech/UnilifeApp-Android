//package unilife.com.unilife.blogs
//
//import android.content.Context
//import android.content.Intent
//import android.support.v4.view.PagerAdapter
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import com.bumptech.glide.Glide
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class BlogViewPagerAdapter(
//    internal var context: Context,
//    arraySlider: ArrayList<Blog.SliderData>
//) : PagerAdapter (){
//
//    private lateinit var view: View
//    private lateinit var viewpager_picture: ImageView
//    val Image = arrayOf(R.drawable.no_image, R.drawable.no_image , R.drawable.no_image)
//
//    var arraySlider : ArrayList<Blog.SliderData> = ArrayList()
//    init {
//        this.arraySlider=arraySlider
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }
//
//    override fun getCount(): Int {
//        return arraySlider.size
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//
//        view = LayoutInflater.from(container.context)
//            .inflate(R.layout.homepageslider, container, false)
//        viewpager_picture = view.findViewById(R.id.viewpager_picture)
//
//
//
//
//        Glide.with(context)
//            .load(WebUrls.blogImageUrl + arraySlider[position].image)
//            .into(viewpager_picture)
//
//        viewpager_picture.setOnClickListener {
//            context.startActivity(
//                Intent(context,BlogDetails::class.java)
//                    .putExtra("blog_id",arraySlider[position].id)
//            )
//
//        }
//
//        container.addView(view)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        val view = `object` as View
//        container.removeView(view)
//    }
//
//}