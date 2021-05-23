package unilife.com.unilife.blogs

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class AllBlogSettingsAdapter(
    var context: Context,
    value: String,
    savedBlogDataArrayList: ArrayList<BlogDataModel>
) : RecyclerView.Adapter<AllBlogSettingsAdapter.MyViewHolder>() {

    var value = ""
    var arrayList : ArrayList<BlogDataModel> = ArrayList()
    lateinit var onItemClick: onItemClickListener

    init {
        this.value=value
        this.arrayList = savedBlogDataArrayList
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.blogviewallitemlist, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        try {
            if (arrayList[i].blog_image != null) {

                Picasso.with(context).load(WebUrls.blogImageUrl + arrayList[i].blog_image)
                    .placeholder(R.drawable.no_image)
                    .into(myViewHolder.iv_image)
            } else {
                myViewHolder.iv_image.setImageResource(R.drawable.no_image)
            }

            val desc = Html.fromHtml(arrayList[i].blog_title)
            myViewHolder.tvDiscription.setText(Html.fromHtml(desc.toString()))
            myViewHolder.tv_Title.text = Html.fromHtml(arrayList[i].blog_title)

//            myViewHolder.cdBlogsAll.setOnClickListener {
//
//                onItemClick.onItemClick(i,arrayList)
//            }

        }
        catch (e : Exception){

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var iv_image : ImageView =  itemView.findViewById(R.id.imgOffer)
        var tvDiscription : TextView = itemView.findViewById(R.id.txtDesc)
        var tv_Title : TextView = itemView.findViewById(R.id.txtTitle)
//        var btnView : TextView = itemView.findViewById(R.id.btn_ViewAll)
//        var cdBlogsAll : CardView = itemView.findViewById(R.id.cdBlogsAll)


    }

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onItemClick(position: Int, blogList: ArrayList<BlogDataModel>? )
    }
}
