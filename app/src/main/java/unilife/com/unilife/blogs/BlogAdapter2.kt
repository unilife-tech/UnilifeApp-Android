//package unilife.com.unilife.blogs
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.makeramen.roundedimageview.RoundedImageView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//import java.util.*
//
//class BlogAdapter2(
//    internal var context: Context,
//    arrayCatBlogs: ArrayList<Blog.CategoriesBlogData>
//) :
//    RecyclerView.Adapter<BlogAdapter2.VideoViewHolder>() {
//
//    var arrayCatBlogs = ArrayList<Blog.CategoriesBlogData>()
//    init {
//        this.context = context
//        this.arrayCatBlogs = arrayCatBlogs
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.blog_video_adapter, viewGroup, false)
//        return VideoViewHolder(view)
//    }
//
//    override fun onBindViewHolder(videoViewHolder: VideoViewHolder, i: Int) {
//
//        try {
//
//            if (arrayCatBlogs[i].image.isNotEmpty()) {
//                Log.e("smczsldccsx", "" + WebUrls.blogImageUrl + arrayCatBlogs[i].image)
//                Picasso.with(context).load(WebUrls.blogImageUrl + arrayCatBlogs[i].image)
//                    .placeholder(R.drawable.no_image).into(videoViewHolder.iv_image)
//
//
//            } else {
//                videoViewHolder.iv_image.setImageResource(R.drawable.no_image)
//            }
//
//
//            videoViewHolder.iv_image.setOnClickListener {
//                context.startActivity(
//                    Intent(context, BlogDetails::class.java)
//                        .putExtra("blog_id", arrayCatBlogs[i].id)
//                )
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        var size = 0
//        size = if (arrayCatBlogs.size <= 6) {
//            arrayCatBlogs.size
//        } else {
//            6
//        }
//        return size
//    }
//
//    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        internal var iv_image: RoundedImageView
//
//        init {
//
//            iv_image = itemView.findViewById(R.id.iv_image)
//
//            iv_image.setOnClickListener {
//                context.startActivity(
//                    Intent(
//                        context,
//                        BlogDetails::class.java
//                    )
//                )
//            }
//        }
//    }
//}
