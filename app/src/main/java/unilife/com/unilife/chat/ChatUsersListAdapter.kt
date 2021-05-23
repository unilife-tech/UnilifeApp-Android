package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.home.ShowOtherProfile
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ChatUsersListAdapter(
    val context: Context,
    var friendsList: ArrayList<ChatUserDetailsModel>,
    var key: String
) : RecyclerView.Adapter<ChatUsersListAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener

    var tempList: ArrayList<ChatUserDetailsModel>

    init {
        tempList = java.util.ArrayList<ChatUserDetailsModel>()
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

            if (friendsList[i].friend_profile_image != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + friendsList[i].friend_profile_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivImg)

            } else {
                myViewHolder.ivImg.setImageResource(R.drawable.no_image)
            }


            if (key == "view_friends") {
                myViewHolder.tvAction.visibility = GONE
                myViewHolder.tvName.text = friendsList.get(i).friend_username
                myViewHolder.rlMain.setOnClickListener {
                    onItemClick.onItemClick(i, friendsList)
                }

                myViewHolder.ivImg.setOnClickListener {
                    context.startActivity(
                        Intent(context, ShowOtherProfile::class.java)
                            .putExtra("post_user_id", friendsList[i].friend_id)
                    )
                }

            } else if (key == "view_more_suggestions") {
                myViewHolder.tvAction.visibility = VISIBLE
                if (friendsList[i].check_req_status == "1") {  // cancel Req
                    myViewHolder.tvAction.text = "Cancel Request"
                    myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_dark_grey)

                } else if (friendsList[i].check_req_status == "0") {  // send Req
                    myViewHolder.tvAction.text = "Send Request"
                    myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_color_primary)
                }
                myViewHolder.tvName.text = friendsList[i].username

                myViewHolder.rlMain.setOnClickListener {
                    onItemClick.onItemClick(i, friendsList)
                }

                myViewHolder.ivImg.setOnClickListener {

                    context.startActivity(
                        Intent(context, ShowOtherProfile::class.java)
                            .putExtra("post_user_id", friendsList[i].friend_id)
                    )
                }


            } else if (key == "Request Received") {
                myViewHolder.tvAction.visibility = VISIBLE
                myViewHolder.tvAction.text = "Respond"
                myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_color_primary)
                myViewHolder.tvName.text = friendsList[i].friend_username
                myViewHolder.ivImg.setOnClickListener {
                    onItemClick!!.onProfileImageClick(i)
                }
            } else {
                myViewHolder.tvAction.visibility = GONE
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
                if (key.equals("view_more_suggestions")) {
                    if (item.username.toLowerCase().startsWith(text)) {
                        friendsList.add(item)
                    }
                } else if (key.equals("view_friends")) {
                    if (item.friend_username.toLowerCase().startsWith(text)) {
                        friendsList.add(item)
                    }
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
            friendsList: ArrayList<ChatUserDetailsModel>?
        )

        fun onItemClick(
            position: Int,
            friendsList: ArrayList<ChatUserDetailsModel>?
        )

        fun onProfileImageClick(
            position: Int
        ) {
        }
    }
}
