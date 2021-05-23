package unilife.com.unilife.brands

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.blogs.SearchResult
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.retrofit.WebUrls.brandImageUrl
import unilife.com.unilife.retrofit.WebUrls.offerImageUrl

class SearchResultBrandAdapter(
    var context: Context,
    searchlistB: ArrayList<SearchResult.SearchResultBrandData> )
    : RecyclerView.Adapter<SearchResultBrandAdapter.MyViewHolder>() {
    var searchlist : ArrayList<SearchResult.SearchResultBrandData> = ArrayList()
    init {
        this.searchlist=searchlistB
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
        myViewHolder.txtTitle.text = searchlist[i].discount_percent+"% OFF"
//        myViewHolder.txtDesc.text = searchlist[i].brand_desc
//        val desc = Html.fromHtml(searchlist[i].brand_desc)
//        myViewHolder.txtDesc.setText(Html.fromHtml(desc.toString()))

        if (searchlist[i].image != null) {


//            RequestOptions defultImg = new RequestOptions()
//                    .placeholder(R.drawable.default_image)
//                    .error(R.drawable.default_image)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .priority(Priority.HIGH);
            Glide
                .with(context)
                .load(
                    offerImageUrl + searchlist[i].image) //                    .apply(defultImg)
                .into(myViewHolder.imgOffer)

//            Picasso.with(context).load(WebUrls.blogImageUrl + searchlist[i].image)
//                .placeholder(R.drawable.no_image).into(myViewHolder.imgOffer)
        } else {
            myViewHolder.imgOffer.setImageResource(R.drawable.default_image)
        }

        myViewHolder.txtDesc.visibility=View.GONE

//        myViewHolder.imgOffer.setOnClickListener {
//
//            context.startActivity(
//                Intent(context, BrandDetailsActivity::class.java)
//                    .putExtra("offer_id",searchlist[i].id)
//                    .putExtra("name",searchlist[i].id))
//        }
    }


    class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var txtDesc: TextView = itemView.findViewById(R.id.txtDesc)
        var imgOffer: ImageView = itemView.findViewById(R.id.imgOffer)


    }

}
