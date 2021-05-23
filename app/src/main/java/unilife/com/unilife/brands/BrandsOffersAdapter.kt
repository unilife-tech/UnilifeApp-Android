//package unilife.com.unilife.brands
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.blogs.BlogDetails
//import unilife.com.unilife.retrofit.WebUrls
//
//class BrandsOffersAdapter(
//    internal var context: Context,
//    blogArrayList: ArrayList<BrandsHome.CatBlogData>
//) :
//    RecyclerView.Adapter<BrandsOffersAdapter.NewsViewHolder>() {
//
//    var blogArrayList : ArrayList<BrandsHome.CatBlogData> = ArrayList()
//
//    init {
//        this.blogArrayList = blogArrayList
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.tranding_brand_offer, viewGroup, false)
//        return NewsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, i: Int) {
//        if(blogArrayList[i]!=null){
//            Picasso.with(context).load(WebUrls.blogImageUrl+blogArrayList[i].image)
//                .placeholder(R.drawable.no_image).into(newsViewHolder.iv_image)
//
//        }
//        else
//        {
//            newsViewHolder.iv_image.setImageResource(R.drawable.no_image)
//        }
//
//        newsViewHolder.tvOff.text = blogArrayList[i].title
//
//        newsViewHolder.iv_image.setOnClickListener {
//            context.startActivity(
//                Intent(context, BlogDetails::class.java)
//                    .putExtra("blog_id",blogArrayList[i].id))
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return if(blogArrayList.size<=6){
//            blogArrayList.size
//        } else{
//            6
//        }
//    }
//
//    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var iv_image: ImageView = itemView.findViewById(R.id.iv_image)
//        internal var tvOff: TextView = itemView.findViewById(R.id.tvOff)
//
//    }
//}
