package unilife.com.unilife.blogs

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ViewAllCategoriesAdapter(
    var context: Context,
    bloglist: ArrayList<ViewAllCategories.GetAllBlogs>
)
    : RecyclerView.Adapter<ViewAllCategoriesAdapter.MyViewHolder>() {

    var bloglist: ArrayList<ViewAllCategories.GetAllBlogs> = ArrayList()
    lateinit var onItemClick: onItemClickListener

    init {
        this.bloglist=bloglist
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.blogviewallitemlist, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bloglist.size
    }

    override fun onBindViewHolder(myViewHolder : MyViewHolder, i: Int) {
        myViewHolder.tv_Title.text = bloglist[i].categories_name
        myViewHolder.tvDesc.visibility = View.GONE
        if(bloglist[i].categories_image!=null){

            Picasso.with(context).load(WebUrls.blogImageUrl+bloglist[i].categories_image)
                .placeholder(R.drawable.no_image).into(myViewHolder.iv_imageViewAll)

        }
        else
        {
            myViewHolder.iv_imageViewAll.setImageResource(R.drawable.no_image)
        }

//        myViewHolder.btn_View.setOnClickListener {
//            onItemClick.onItemClick(i,bloglist)
//        }


    }


    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        var iv_imageViewAll: ImageView = itemView.findViewById(R.id.imgOffer)
        var tvDesc: TextView = itemView.findViewById(R.id.txtDesc)
        var tv_Title: TextView = itemView.findViewById(R.id.txtTitle)
//        var btn_View: TextView = itemView.findViewById(R.id.btn_ViewAll)
    }

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        fun onItemClick(position: Int, blogList: ArrayList<ViewAllCategories.GetAllBlogs>)
    }
}