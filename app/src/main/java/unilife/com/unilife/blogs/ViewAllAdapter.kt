package unilife.com.unilife.blogs

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class ViewAllAdapter(
    var context: Context,
    arraylist: ArrayList<BlogDataModel>
) : RecyclerView.Adapter<ViewAllAdapter.ViewAllHolder>() {

    var arraylist: ArrayList<BlogDataModel> = ArrayList()
    lateinit var onItemClick: onItemClickListener


    init {
        this.arraylist = arraylist

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewAllHolder {

        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.blogviewallitemlist, viewGroup, false)
        return ViewAllHolder(view)
    }

    override fun getItemCount(): Int {
        return arraylist.size

    }

    override fun onBindViewHolder(viewallHolder: ViewAllHolder, i: Int) {

        try {

            Log.e("ADAPTER", "arraylist[i].blog_id::"
                    + arraylist[i].blog_id+" "+arraylist[i].blog_description)

            viewallHolder.tv_Title.text = arraylist[i].blog_title
            viewallHolder.tvDesc.text = Html.fromHtml(arraylist[i].blog_description)

            if (arraylist[i].blog_image != null) {
                Picasso.with(context).load(WebUrls.blogImageUrl + arraylist[i].blog_image)
                    .placeholder(R.drawable.no_image)
                    .into(viewallHolder.iv_imageViewAll)
            } else {
                viewallHolder.iv_imageViewAll.setImageResource(R.drawable.no_image)
            }

//            viewallHolder.btn_View.setOnClickListener{
//                onItemClick.onItemClick(i,arraylist[i].blog_id)
//            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    inner class ViewAllHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_imageViewAll: ImageView = itemView.findViewById(R.id.imgOffer)
        var tvDesc: TextView = itemView.findViewById(R.id.txtDesc)
        var tv_Title: TextView = itemView.findViewById(R.id.txtTitle)
//        var btn_View: TextView = itemView.findViewById(R.id.btn_ViewAll)


    }

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onItemClick(position: Int, blogId: String)
    }

}