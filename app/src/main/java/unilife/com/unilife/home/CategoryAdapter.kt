package unilife.com.unilife.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class CategoryAdapter(internal var context: Context, var likeList: ArrayList<LikeModel>) :
    RecyclerView.Adapter<CategoryAdapter.myViewHolder>() {


    var pos: Int = 0

    val TAG = CategoryAdapter::class.java.simpleName
    lateinit var onItemClick: onItemClickListener

    init {
        this.likeList = likeList
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.like_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        Log.e(TAG,"list:onBindViewHolder::"+likeList.size)

        myViewHolder.tvname.text = likeList[i].user_post_comment_like_username

        if (likeList[i].user_post_comment_like_profile_image != null) {
            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + likeList[i].user_post_comment_like_profile_image)
                .placeholder(R.drawable.no_image).into(myViewHolder.ivUserImg)

        } else {
            myViewHolder.ivUserImg!!.setImageResource(R.drawable.no_image)
        }

    }


    override fun getItemCount(): Int {
        return likeList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var ivUserImg: ImageView
        internal var tvname: TextView

        init {
            ivUserImg = itemView.findViewById(R.id.ivUserImg)
            tvname = itemView.findViewById(R.id.tvname)
        }

    }


    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onItemClick(position: Int)
    }
}
