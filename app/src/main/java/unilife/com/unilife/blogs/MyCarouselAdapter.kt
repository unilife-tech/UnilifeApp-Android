//package unilife.com.unilife.blogs
//
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class MyCarouselAdapter(blog: Blog,var teamList: ArrayList<Blog.TeamData>) : RecyclerView.Adapter<MyCarouselAdapter.ViewHolder>() {
//
//    var viewcount = false
//
//    var context = blog
//
//    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
//
//        val view = LayoutInflater.from(p0.context).inflate(R.layout.my_tream,null)
//        return ViewHolder(view)
//
//
//    }
//
//    override fun getItemCount(): Int {
//
//        return teamList.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(2)
//    }
//
//
//    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
//
//        Log.e("onBindViewHolder",p1.toString())
//        Log.e("onBindData ===",teamList[p1].toString())
//
//        p0.name.text = teamList[p1].name
//        Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL+teamList[p1].image).placeholder(R.drawable.no_image).into(p0.ivImageView)
//
//
//    }
//
//
//
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//
//        internal  var ivImageView: ImageView = itemView.findViewById(R.id.ivMyteam) as ImageView
//        internal  var name: TextView = itemView.findViewById(R.id.name) as TextView
//
//
//
//    }
//}