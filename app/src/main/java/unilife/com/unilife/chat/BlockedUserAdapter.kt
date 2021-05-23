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

class BlockedUserAdapter(
   var context : Context,
   blockUserList: ArrayList<BlockedUser.BlockedUserData>
) : RecyclerView.Adapter<BlockedUserAdapter.myViewHolder>() {

    var blockUserList: ArrayList<BlockedUser.BlockedUserData> = ArrayList()
    lateinit var onItemClick: onItemClickListener
    init {
        this.blockUserList = blockUserList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.block_user_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        myViewHolder.tvname.text = blockUserList[i].username
        if (blockUserList[i].profile_image != null) {
            Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL+blockUserList[i].profile_image).placeholder(R.drawable.no_image)
                .into(myViewHolder.circleImageView)
        } else
        {
            myViewHolder.circleImageView.setImageResource(R.drawable.no_image)
        }

        myViewHolder.tvUnblock.setOnClickListener {
            onItemClick.onUnblockClick(i,blockUserList[i].blocked_id)
        }

    }

    override fun getItemCount(): Int {
        return blockUserList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var circleImageView : ImageView = itemView.findViewById(R.id.circleImageView)
        var tvname : TextView = itemView.findViewById(R.id.tvname)
        var tvUnblock : TextView = itemView.findViewById(R.id.tvUnblock)

    }
    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onUnblockClick(i: Int, blocked_id: String)

    }
}
