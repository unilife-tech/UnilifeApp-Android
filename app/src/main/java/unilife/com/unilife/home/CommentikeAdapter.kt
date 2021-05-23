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

class CommentikeAdapter(
    var context: Context,
    commentlikeList: ArrayList<LikeModel>
) : RecyclerView.Adapter<CommentikeAdapter.MyViewHolder>() {

var commentlikeList: ArrayList<LikeModel> = ArrayList()

    init {
        this.commentlikeList=commentlikeList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.like_adapter, viewGroup, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
            return commentlikeList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {



        myViewHolder.tvname.text = commentlikeList[i].user_post_comment_like_username

        if (commentlikeList[i].user_post_comment_like_profile_image.isNotEmpty()) {
            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + commentlikeList[i].user_post_comment_like_profile_image)
                .placeholder(R.drawable.no_image).into(myViewHolder.ivUserImg)

        } else {
            myViewHolder.ivUserImg.setImageResource(R.drawable.no_image)
        }

    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
      var  ivUserImg : ImageView = itemView.findViewById(R.id.ivUserImg)
      var  tvname : TextView= itemView.findViewById(R.id.tvname)
    }

}
