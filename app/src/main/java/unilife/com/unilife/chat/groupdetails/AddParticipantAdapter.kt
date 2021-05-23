package unilife.com.unilife.chat.groupdetails

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class AddParticipantAdapter(
    val context: Context,
    var friendsList: ArrayList<AddParticipant.ShowFriendListModel>
) : RecyclerView.Adapter<AddParticipantAdapter.ViewHolder>() {
    lateinit var onItemClick: onItemClickListener

    var tempList: ArrayList<AddParticipant.ShowFriendListModel> = java.util.ArrayList()

    init {
        tempList.clear()
        tempList.addAll(friendsList)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.add_participant_adapter,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(myViewHolder: ViewHolder, i: Int) {
        try {

            myViewHolder.tvAction.visibility = View.VISIBLE

            if (friendsList[i].profile_image != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + friendsList[i].profile_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivImg)

            } else {
                myViewHolder.ivImg.setImageResource(R.drawable.no_image)
            }

            myViewHolder.tvName.text = friendsList[i].username

                myViewHolder.tvAction.visibility = View.GONE
                if (friendsList[i].check_req_status == "0") { //adding
                    myViewHolder.llTick.visibility = View.GONE
                    myViewHolder.tvAction.text = "Add"
                    myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_dark_grey)

                } else if (friendsList[i].check_req_status == "1") { //selected one
                    myViewHolder.llTick.visibility = View.VISIBLE
                    myViewHolder.tvAction.text = "Selected"
                    myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_color_primary)
                }

            myViewHolder.rlMain.setOnClickListener {
                onItemClick.onPerformAction(i, friendsList[i].check_req_status, friendsList)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var ivImg: ImageView
        internal var tvName: TextView
        internal var tvAction: TextView
        internal var rlMain: ConstraintLayout
        internal var llTick: RoundKornerLinearLayout

        init {
            ivImg = itemView.findViewById(R.id.ivImg)
            tvName = itemView.findViewById(R.id.tvName)
            tvAction = itemView.findViewById(R.id.tvAction)
            rlMain = itemView.findViewById(R.id.rlMain)
            llTick = itemView.findViewById(R.id.llTick)
        }
    }

    fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    fun filterResult(text: String) {

        var text = text

        friendsList.clear()

        if (text.isEmpty()) {
            friendsList.addAll(tempList)
        } else {
            text = text.toLowerCase()
            for (item in tempList) {

                if (item.username.toLowerCase().startsWith(text)) {
                    friendsList.add(item)
                }

            }
        }
        if (friendsList.size > 0) {
        } else {
            if (friendsList.size == 0) {
                //                headerHolder.tvText.setText("No Room Members");
            } else {
                //                headerHolder.tvText.setText("Room Members");
            }
        }
        notifyDataSetChanged()
    }


    interface onItemClickListener {
        fun onPerformAction(
            position: Int,
            status: String,
            friendsList: ArrayList<AddParticipant.ShowFriendListModel>?
        )

        fun onItemClick(
            position: Int,
            friendsList: ArrayList<AddParticipant.ShowFriendListModel>?
        )
    }
}