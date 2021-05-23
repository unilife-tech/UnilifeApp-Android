package unilife.com.unilife.chat

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
import java.lang.Exception

class OnlineFriendsAdapter (var context: Context,var friendsList : ArrayList<ChatUserDetailsModel>)
    : RecyclerView.Adapter<OnlineFriendsAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.basic_detail, viewGroup, false)
        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try{
            if (friendsList[i].friend_profile_image != null) {
                Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL + friendsList[i].friend_profile_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivuser)
            } else {
                myViewHolder.ivuser.setImageResource(R.drawable.no_image)
            }
            myViewHolder.tvName.text=friendsList.get(i).friend_username
        }catch (e : Exception){
            e.printStackTrace()
        }

    }


    override fun getItemCount(): Int {
        return friendsList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        internal var ivuser: ImageView
        internal var tvName: TextView

        init {
            ivuser = itemView.findViewById(R.id.ivuser)
            tvName = itemView.findViewById(R.id.tvName)
            itemView.setOnClickListener {
               onItemClick.onItemClick(layoutPosition, friendsList)
            }
        }

    }

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onItemClick(
            position: Int, friendsList: ArrayList<ChatUserDetailsModel>?
        )
    }
}
