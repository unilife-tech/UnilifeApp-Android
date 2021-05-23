//package unilife.com.unilife.blogs
//
//import android.content.Context
//import android.content.Intent
//import android.support.v7.widget.CardView
//import android.support.v7.widget.RecyclerView
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import com.squareup.picasso.Picasso
//import unilife.com.unilife.R
//import unilife.com.unilife.retrofit.WebUrls
//
//class BlogCategoriesAdapter(
//    private val context: Context,
//    arrayBlogs: ArrayList<Blog.BlogerData>
//) : RecyclerView.Adapter<BlogCategoriesAdapter.CategoriesViewHolder>() {
//
//    var blogdatalist = ArrayList<Blog.BlogerData>()
//
//    init {
//        this.blogdatalist=arrayBlogs
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CategoriesViewHolder {
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.blog_category_adapter, viewGroup, false)
//        return CategoriesViewHolder(view)
//    }
//
//    override fun onBindViewHolder(categoriesViewHolder: CategoriesViewHolder, i: Int) {
//
//            if(i>2)
//            {
//                categoriesViewHolder.tv_categories.text = blogdatalist[i].categories_name
//                Picasso.with(context).load(WebUrls.blogImageUrl+blogdatalist[i].categories_image)
//                    .placeholder(R.drawable.no_image).into(categoriesViewHolder.iv_image)
//             //   Log.e("Sfsafsffegd",""+WebUrls.blogImageUrl+blogdatalist[i].categories_image)
//
//                categoriesViewHolder.cvblogsCat.visibility = View.VISIBLE
//
//            }
//        else
//            {
//                categoriesViewHolder.cvblogsCat.visibility = View.GONE
//            }
//
//        categoriesViewHolder.iv_image.setOnClickListener {
//            context.startActivity(Intent(context,ViewAllBlogs::class.java)
//                .putExtra("category_id",blogdatalist[i].id)
//            )
//        }
//    }
//
//    override fun getItemCount(): Int {
//        if(blogdatalist.size<=6){
//            return blogdatalist.size
//        }
//        else{
//            return 6
//        }
//    }
//
//    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//    {
//        var iv_image : ImageView = itemView.findViewById(R.id.iv_image)
//        var tv_categories : TextView = itemView.findViewById(R.id.tv_categories)
//        var cvblogsCat:CardView= itemView.findViewById(R.id.cvblogsCat)
//    }
//}
