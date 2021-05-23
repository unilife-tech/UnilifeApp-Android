package unilife.com.unilife.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.chat.groupdetails.AddParticipant
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class GroupUsersListAdapter(
    val context: Context,
    var friendsList: ArrayList<AddParticipant.ShowFriendListModel>,
    var key: String
) : RecyclerView.Adapter<GroupUsersListAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener

    var tempList: ArrayList<AddParticipant.ShowFriendListModel>

    init {
        tempList = java.util.ArrayList<AddParticipant.ShowFriendListModel>()
        tempList.clear()
        tempList.addAll(friendsList)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_users_listing_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try {

            myViewHolder.tvAction.visibility = VISIBLE

            if (friendsList[i].profile_image != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + friendsList[i].profile_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivImg)

            } else {
                myViewHolder.ivImg.setImageResource(R.drawable.no_image)
            }

            myViewHolder.tvName.text = friendsList.get(i).username


            if(friendsList[i].check_req_status.equals("0")){ //adding

                myViewHolder.tvAction.text = "Add"
                myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_dark_grey)

            }else if(friendsList[i].check_req_status.equals("1")){ //selected one

                myViewHolder.tvAction.text = "Selected"
                myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_color_primary)
            }


            myViewHolder.tvAction.setOnClickListener {
                onItemClick.onPerformAction(i, friendsList[i].check_req_status, friendsList)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun getItemCount(): Int {

        return friendsList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    /* View.OnClickListener */ {


        internal var ivImg: ImageView
        internal var tvName: TextView
        internal var tvAction: TextView
        internal var rlMain: RelativeLayout

        init {

            ivImg = itemView.findViewById(R.id.ivImg)
            tvName = itemView.findViewById(R.id.tvName)
            tvAction = itemView.findViewById(R.id.tvAction)
            rlMain = itemView.findViewById(R.id.rlMain)

//            tvAction.setOnClickListener(this)

        }


        /*   override fun onClick(v: View?) {

               when (itemView?.id) {
                   R.id.tvAction -> {
                       Log.e("ONCLICK","ONCLICKED")
                       onItemClick.onPerformAction(layoutPosition,friendsList[layoutPosition].check_req_status, friendsList)
                   }
               }
           }
   */
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

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onPerformAction(
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
