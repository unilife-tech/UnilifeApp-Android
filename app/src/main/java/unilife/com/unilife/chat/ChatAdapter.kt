package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import unilife.com.unilife.R
import unilife.com.unilife.profile.OtherProfileActivity
import unilife.com.unilife.retrofit.WebUrls
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatAdapter(internal var context: Context, var chatArrayList: ArrayList<Chat.ChatModel>) :
    RecyclerView.Adapter<ChatAdapter.myViewHolder>() {

    lateinit var onItemClick: onItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {

        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_chat_listing, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try {

            if (chatArrayList[i].receiverId != "") {  // receiver details

                myViewHolder.txtName.text = chatArrayList[i].receiverName

                if (chatArrayList[i].receiverProfileImage != null) {
                    Picasso.with(context)
                        .load(WebUrls.PROFILE_IMAGE_URL + chatArrayList[i].receiverProfileImage)
                        .placeholder(R.drawable.no_image).into(myViewHolder.imgProfile)

                } else {
                    myViewHolder.imgProfile.setImageResource(R.drawable.no_image)
                }

                myViewHolder.imgProfile.setOnClickListener {

                    context.startActivity(
                        Intent(context, OtherProfileActivity::class.java)
                            .putExtra("userId", chatArrayList[i].receiverId)
                    )

                }


            } else { // group details

                myViewHolder.txtName.text = chatArrayList[i].groupName
                myViewHolder.imgProfile.setOnClickListener {

                    context.startActivity(
                        Intent(context, GroupDetails::class.java)
                            .putExtra("group_id", chatArrayList[i].groupId)
                    )

                }


                if (chatArrayList[i].groupImg != null) {
                    Picasso.with(context)
                        .load(WebUrls.PROFILE_IMAGE_URL + chatArrayList[i].groupImg)
                        .placeholder(R.drawable.no_image).into(myViewHolder.imgProfile)

                } else {
                    myViewHolder.imgProfile.setImageResource(R.drawable.no_image)
                }
            }

            if (chatArrayList[i].messageType.equals("text")) {
                var decode: String? = ""
                try {
                    decode =
                        URLDecoder.decode(chatArrayList[i].message, "UTF-8")
                    Log.e("decode", chatArrayList[i].message)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }
                myViewHolder.txtLastMsg.text = decode
            } else {
                myViewHolder.txtLastMsg.text = chatArrayList[i].fileType

                if (chatArrayList[i].fileType.equals("image")) {
                    myViewHolder.txtLastMsg.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_baseline_photo_24),null,null,null)
                } else if (chatArrayList[i].fileType.equals("document")) {
                    myViewHolder.txtLastMsg.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_baseline_description_24),null,null,null)
                } else if (chatArrayList[i].fileType.equals("audio")) {
                    myViewHolder.txtLastMsg.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_baseline_mic_24),null,null,null)
                } else if (chatArrayList[i].fileType.equals("video")) {
                    myViewHolder.txtLastMsg.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_baseline_videocam_24_second),null,null,null)

                }
            }

            myViewHolder.txtTime.text = getConvertedTime(chatArrayList[i].last_message_time)
//            if (chatArrayList[i].unseen_msgs_count.isNotEmpty()) {
//                myViewHolder.llUnseenMsgs.visibility = VISIBLE
//                myViewHolder.tvUnseenMsgsCount.text = chatArrayList[i].unseen_msgs_count
//            } else {
//                myViewHolder.llUnseenMsgs.visibility = GONE
//            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getConvertedTime(raw: String): String? {
        var formattedDate: String=""
        if (!raw.equals("")) {
            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formatter = SimpleDateFormat("dd MMM hh:mm a")
            formattedDate = formatter.format(parser.parse(raw))
        }
        return formattedDate;
    }


    override fun getItemCount(): Int {
        return chatArrayList.size
    }

//    private fun getConvertedTime(raw: String): String? {
//        val dateFormat: DateFormat =
//            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//        var date: Date? =
//            null //You will get date object relative to server/client timezone wherever it is parsed
//        try {
//            date = dateFormat.parse(raw)
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        val formatter: DateFormat =
//            SimpleDateFormat("hh:mm a") //If you need time just put specific format for time like 'HH:mm:ss'
//        return formatter.format(date)
//    }


    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProfile: CircleImageView
        var txtName: TextView
        var txtLastMsg: TextView
        var txtTime: TextView
        var tvUnseenMsgsCount: TextView
        var llUnseenMsgs: LinearLayout
//        var llContent: LinearLayout

        init {

            imgProfile = itemView.findViewById(R.id.imgProfile)
            txtName = itemView.findViewById(R.id.txtName)
            txtTime = itemView.findViewById(R.id.txtTime)
            txtLastMsg = itemView.findViewById(R.id.txtLastMsg)
            tvUnseenMsgsCount = itemView.findViewById(R.id.tvUnseenMsgsCount)
            llUnseenMsgs = itemView.findViewById(R.id.llUnseenMsgs)
//            llContent = itemView.findViewById(R.id.llContent)

            itemView.setOnClickListener {
                onItemClick.onItemClick(layoutPosition, chatArrayList)
            }/*itemView.setOnClickListener {
                onItemClick.onItemClick(layoutPosition, chatArrayList)
            }*/
        }
    }

    fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    interface onItemClickListener {

        fun onItemClick(
            position: Int,
            chatArrayList: ArrayList<Chat.ChatModel>?
        )

    }
}
