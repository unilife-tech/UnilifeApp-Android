package unilife.com.unilife.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.exoplayer2.util.Log
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class HomeBlogAdapter(internal var context: Context, internal var  blogOffersList: ArrayList<HomeModel.BlogModel>?)
    : RecyclerView.Adapter<HomeBlogAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.brands_offer_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        Log.e("cjhcbjdd",""+blogOffersList!!.size)
        return if (blogOffersList!!.size <= 4) {
            blogOffersList!!.size
        } else {
            4
        }
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        try{
            myViewHolder.tv_blogName.text = blogOffersList!![i].blog_name
            myViewHolder.tv_blogName.visibility = View.VISIBLE

            if(blogOffersList!![i].blog_image.isNotEmpty()){
                Picasso.with(context).load(WebUrls.blogImageUrl+blogOffersList!![i].blog_image).placeholder(R.drawable.no_image)
                    .into(myViewHolder.iv_image)

            }
            else
            {
                myViewHolder.iv_image.setImageResource(R.drawable.no_image)
            }

            myViewHolder.iv_image.setOnClickListener {
//                context.startActivity(Intent( context,BlogDetails::class.java)
//                    .putExtra("blog_id",blogOffersList!![i].blog_id)
//                )

            }

        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_blogName : TextView = itemView.findViewById(R.id.tv_blogName)
        var iv_image: ImageView = itemView.findViewById(R.id.iv_image)
    }

}
