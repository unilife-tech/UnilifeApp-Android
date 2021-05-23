package unilife.com.unilife.chat.groupdetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.group_participants_adapter_layout.view.*
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class GroupParticipantsAdapter(
    val context: Context
) :

    RecyclerView.Adapter<GroupParticipantsAdapter.ViewHolder>() {
    private var groupUserList = ArrayList<GroupDetail.GroupUserModel>()

    fun upDateData(groupUserList1: ArrayList<GroupDetail.GroupUserModel>) {
        groupUserList = groupUserList1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.group_participants_adapter_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return groupUserList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            if (groupUserList[position].group_admin == "yes") {
                holder.llGroupAdmin.visibility = View.VISIBLE
                holder.llRemoveMember.visibility = View.GONE
//                if (GroupDetail.isAdmin) {
//                    holder.rlGroupMember.isEnabled = false
//                }
            } else {
                holder.llGroupAdmin.visibility = View.GONE
                holder.llRemoveMember.visibility = View.VISIBLE
//                holder.rlGroupMember.isEnabled = true
            }

            holder.llRemoveMember.setOnClickListener {
                if (GroupDetail.isAdmin){
                    groupClicks!!.removeGroupMember(position)
                }
            }

//            if (groupUserList[position].user_id == PreferenceFile.getInstance().getPreferenceData(
//                    context,
//                    WebUrls.KEY_USERID
//                )
//            ) {
//                holder.rlGroupMember.isEnabled = false
//            }

            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + groupUserList[position].profile_image)
                .placeholder(R.drawable.no_image).into(holder.ivuser)

            holder.tvName.text = groupUserList[position].username
            holder.tvStatus.text = groupUserList[position].status

            holder.rlGroupMember.setOnClickListener {
                groupClicks!!.clickGroupMember(position)

            }

//            holder.clContent.setOnClickListener {
//                groupClicks!!.clickGroupMember(position)
//
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val llGroupAdmin = view.llGroupAdmin
        val llRemoveMember = view.llRemoveMember
        val rlGroupMember = view.rlGroupMember
        val ivuser = view.ivuser
        val tvName = view.tvName
        val tvStatus = view.tvStatus
        val clContent = view.clContent
    }

    interface GroupClicks {
        fun clickGroupMember(position: Int)
        fun removeGroupMember(position: Int)
    }

    var groupClicks: GroupClicks? = null

    fun initGroupClicks(groupClicks: GroupClicks) {
        this.groupClicks = groupClicks
    }
}
