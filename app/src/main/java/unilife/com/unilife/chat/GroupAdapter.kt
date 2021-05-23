package unilife.com.unilife.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class GroupAdapter(internal var context: Context, var groupList: ArrayList<GroupModel>) :
    RecyclerView.Adapter<GroupAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener


    var tempList: ArrayList<GroupModel>

    init {
        tempList = ArrayList<GroupModel>()
        tempList.clear()
        tempList.addAll(groupList)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {

        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.group_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try {

            myViewHolder.tvname.text = groupList[i].group_name

            if (groupList[i].group_members_count.equals("0")) {
                myViewHolder.tvTotalMembers.visibility = GONE

            } else if (groupList[i].group_members_count.equals("1")) {
                myViewHolder.tvTotalMembers.visibility = VISIBLE

                myViewHolder.tvTotalMembers.text = groupList[i].group_members_count + " User"

            } else {
                myViewHolder.tvTotalMembers.visibility = VISIBLE

                myViewHolder.tvTotalMembers.text = groupList[i].group_members_count + " Users"

            }

            if (groupList[i].group_image != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + groupList[i].group_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivuser)

            } else {
                myViewHolder.ivuser.setImageResource(R.drawable.no_image)
            }

            myViewHolder.tvUnjoin.setOnClickListener {

                onItemClick.onUnJoinClick(i, groupList)

            }

            myViewHolder.ivuser.setOnClickListener {

                onItemClick.onItemClick(i, groupList)

            }
            myViewHolder.rlMain.setOnClickListener {

                onItemClick.onGoToGroupChat(i, groupList)

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivuser: ImageView
        var tvname: TextView
        var tvTotalMembers: TextView
        var tvUnjoin: TextView
        var llUnjoin: LinearLayout
        var rlMain: RelativeLayout

        init {

            ivuser = itemView.findViewById(R.id.ivuser)
            tvname = itemView.findViewById(R.id.tvname)
            tvTotalMembers = itemView.findViewById(R.id.tvTotalMembers)
            tvUnjoin = itemView.findViewById(R.id.tvUnjoin)
            llUnjoin = itemView.findViewById(R.id.llUnjoin)
            rlMain = itemView.findViewById(R.id.rlMain)


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

        fun onGoToGroupChat(
            position: Int,
            groupList: ArrayList<GroupModel>?
        )

    }


    fun filterResult(text: String) {

        var text = text

        groupList.clear()

        if (text.isEmpty()) {
            groupList.addAll(tempList)
        } else {
            text = text.toLowerCase()
            for (item in tempList) {

                if (item.group_name.toLowerCase().startsWith(text)) {
                    groupList.add(item)
                }

            }
        }
        if (groupList.size > 0) {
        } else {
            if (groupList.size == 0) {
                //                headerHolder.tvText.setText("No Room Members");
            } else {
                //                headerHolder.tvText.setText("Room Members");
            }
        }
        notifyDataSetChanged()
    }

}
