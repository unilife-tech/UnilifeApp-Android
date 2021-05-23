package unilife.com.unilife.blogs

import android.content.Context
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

class SearchResultAdapter(
    var context: Context,
    searchlist: ArrayList<SearchResult.SearchResultData>
) : RecyclerView.Adapter<SearchResultAdapter.MyViewHolder>() {

    var searchlist: ArrayList<SearchResult.SearchResultData> = ArrayList()

    init {
        this.searchlist = searchlist
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.blogviewallitemlist, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchlist.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.txtTitle.text = searchlist[i].title
        myViewHolder.txtDesc.text = searchlist[i].description
        val desc = Html.fromHtml(searchlist[i].description)
        myViewHolder.txtDesc.setText(Html.fromHtml(desc.toString()))

        if (searchlist[i].image != null) {
            Picasso.with(context).load(WebUrls.blogImageUrl + searchlist[i].image)
                .placeholder(R.drawable.no_image).into(myViewHolder.imgOffer)
        } else {
            myViewHolder.imgOffer.setImageResource(R.drawable.default_image)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var txtDesc: TextView = itemView.findViewById(R.id.txtDesc)
        var imgOffer: ImageView = itemView.findViewById(R.id.imgOffer)
    }
}