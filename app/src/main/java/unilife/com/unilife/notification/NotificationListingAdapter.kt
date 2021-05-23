package unilife.com.unilife.notification

import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.RequestReceivedList
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.retrofit.WebUrls
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationListingAdapter(
    var context: Context,
    notificationList: ArrayList<NotificationListing.NotificationListData>
) : RecyclerView.Adapter<NotificationListingAdapter.MyViewHolder>() {


    var notificationList: ArrayList<NotificationListing.NotificationListData> = ArrayList()
    var now: Long = 0
    var time: Long = 0

    init {
        this.notificationList = notificationList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.notification_listing_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        if (notificationList[i].profile_image != null) {
            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + notificationList[i].profile_image)
                .placeholder(R.drawable.no_image)
                .into(myViewHolder.ivProfileImg)

        } else {
            myViewHolder.ivProfileImg.setImageResource(R.drawable.no_image)
        }

        myViewHolder.tv_username.text = notificationList[i].type
        myViewHolder.tvNotificationtext.text = notificationList[i].message

        var type = notificationList[i].type

        if (type == "Post") {
            myViewHolder.clnotification.setOnClickListener {
                context.startActivity(Intent(context, Home::class.java))
            }
        } else if (type == "Blog") {
            myViewHolder.clnotification.setOnClickListener {
                context.startActivity(Intent(context, Blog::class.java))
            }
        } else if (type == "Chat" || type == "Group"|| type == "Accept Request"|| type == "Reject Request") {
            myViewHolder.clnotification.setOnClickListener {
                context.startActivity(Intent(context, ChatListing::class.java))
            }
        } else if (type == "Offer") {
            myViewHolder.clnotification.setOnClickListener {
                context.startActivity(Intent(context, BrandsHome::class.java))
            }
        } else if (type == "Friend Request") {
            myViewHolder.clnotification.setOnClickListener {
                context.startActivity(Intent(context, RequestReceivedList::class.java))
            }
        }


        //setting time
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        now = System.currentTimeMillis()
        try {
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            if (notificationList[i].created_at != "") {
                time = sdf.parse(notificationList[i].created_at).time
            }
        } catch (e: Exception) {

        }
        val mail = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
        myViewHolder.tvDate.text = mail.toString()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivProfileImg: ImageView = itemView.findViewById(R.id.ivProfileImg)
        var tv_username: TextView = itemView.findViewById(R.id.tv_username)
        var tvNotificationtext: TextView = itemView.findViewById(R.id.tvNotificationtext)
        var tvDate: TextView = itemView.findViewById(R.id.tvDate)
        var clnotification: ConstraintLayout = itemView.findViewById(R.id.clnotification)

    }

}
