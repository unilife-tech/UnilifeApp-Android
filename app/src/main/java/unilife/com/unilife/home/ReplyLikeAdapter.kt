package unilife.com.unilife.home

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

class ReplyLikeAdapter(var context: Context, replylikeList: ArrayList<LikeModel>) : RecyclerView.Adapter<ReplyLikeAdapter.MyViewHolder>() {

    var replylikeList: ArrayList<LikeModel> = ArrayList()
    init {
        this.replylikeList=replylikeList
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.like_adapter, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return replylikeList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {



        myViewHolder.tvname.text = replylikeList[i].user_post_comment_like_username

        if (replylikeList[i].user_post_comment_like_profile_image != null) {
            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + replylikeList[i].user_post_comment_like_profile_image)
                .placeholder(R.drawable.no_image).into(myViewHolder.ivUserImg)

        } else {
            myViewHolder.ivUserImg!!.setImageResource(R.drawable.no_image)
        }

    }



    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var  ivUserImg : ImageView = itemView.findViewById(R.id.ivUserImg)
        var  tvname : TextView = itemView.findViewById(R.id.tvname)
    }

}
