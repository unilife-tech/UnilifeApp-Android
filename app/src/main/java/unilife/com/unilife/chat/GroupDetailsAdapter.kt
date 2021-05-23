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

class GroupDetailsAdapter(
    internal var context: Context,
    var membersList: ArrayList<GroupModel.MembersModel>
) : RecyclerView.Adapter<GroupDetailsAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {

        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_group_info, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try {

            myViewHolder.tvname.text = membersList[i].memName

            if (membersList[i].memImg != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + membersList[i].memImg)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivuser)

            } else {
                myViewHolder.ivuser.setImageResource(R.drawable.no_image)
            }

//            myViewHolder.llUnjoin.visibility=GONE

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivuser: ImageView
        var tvname: TextView
//         var tvTotalMembers: TextView
//         var tvUnjoin: TextView
//         var llUnjoin: LinearLayout
//         var rlMain: RelativeLayout

        init {

            ivuser = itemView.findViewById(R.id.ivuser)
            tvname = itemView.findViewById(R.id.tvname)
//            tvTotalMembers = itemView.findViewById(R.id.tvTotalMembers)
//            tvUnjoin = itemView.findViewById(R.id.tvUnjoin)
//            llUnjoin = itemView.findViewById(R.id.llUnjoin)
//            rlMain = itemView.findViewById(R.id.rlMain)

        }
    }

    fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    interface onItemClickListener {

        fun onItemClick(
            position: Int,
            groupList: ArrayList<GroupModel>?
        )

        fun onUnJoinClick(
            position: Int,
            groupList: ArrayList<GroupModel>?
        )

    }
}
