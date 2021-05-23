package unilife.com.unilife.chat.groupdetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.block_user_adapter.view.*
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class BlockedGroupUsersAdapter(
    val context: Context,
    var groupBlockUserList: ArrayList<BlockedGroupUsers.GroupBlockUser>
) : RecyclerView.Adapter<BlockedGroupUsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.block_user_adapter,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return groupBlockUserList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.tvname.text = groupBlockUserList[position].username

            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + groupBlockUserList[position].profile_image)
                .placeholder(R.drawable.no_image).into(holder.circleImageView)

            holder.tvUnblock.setOnClickListener {
                clickGroupBlock!!.clickUnblock(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvname = view.tvname
        val circleImageView = view.circleImageView
        val tvUnblock = view.tvUnblock
    }

    interface ClickGroupBlock {
        fun clickUnblock(position: Int)
    }

    var clickGroupBlock: ClickGroupBlock? = null

    fun initClickGroupBlock(clickGroupBlock: ClickGroupBlock) {
        this.clickGroupBlock = clickGroupBlock
    }
}