package unilife.com.unilife.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class HomePostAdapter(
    private val context: Context,
    getdataAl: ArrayList<String>
) : RecyclerView.Adapter<HomePostAdapter.NewsViewHolder>() {
    var imagelist: ArrayList<String> = ArrayList()

    init {
        this.imagelist = getdataAl
        Log.e("array", imagelist.toString())
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.home_item_adapter, viewGroup, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(newsViewHolder: NewsViewHolder, i: Int) {
        Picasso.with(context).load(WebUrls.IMAGE_BASE_URL + imagelist[i])
            .placeholder(R.drawable.no_image).into(newsViewHolder.images)
        Log.e("WEB", WebUrls.IMAGE_BASE_URL + imagelist[i])
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var images: ImageView = itemView.findViewById(R.id.image)

    }
}
