//package unilife.com.unilife.brands
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
//class BrandViewPagerAdapter(var context: Context, var arraySlider: ArrayList<BrandsHome.SliderBrands>) : PagerAdapter() {
//    override fun isViewFromObject(p0: View, p1: Any): Boolean {
//        return p0 == p1
//    }
//
//    override fun getCount(): Int {
//        return arraySlider.size
//    }
//
//    private lateinit var view: View
//    private lateinit var viewpager_picture: ImageView
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        view = LayoutInflater.from(container.context)
//            .inflate(R.layout.homepageslider, container, false)
//        viewpager_picture = view.findViewById(R.id.viewpager_picture)
//
//
//        Glide.with(context)
//            .load(WebUrls.offerImageUrl + arraySlider[position].image)
//            .into(viewpager_picture)
//
//
//        viewpager_picture.setOnClickListener {
//            context.startActivity(Intent(context,BrandsRedeemedCoupons::class.java)
//                .putExtra("offer_id",arraySlider[position].brand_id)
//                .putExtra("name",arraySlider[position].name)
//            )
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
