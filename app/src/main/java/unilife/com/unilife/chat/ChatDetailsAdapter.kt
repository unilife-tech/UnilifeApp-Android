package unilife.com.unilife.chat

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.WebUrls
import java.text.SimpleDateFormat


//class ChatDetailsAdapter(internal var context: Context, var chatList: ArrayList<Chat.ChatModel>) :
//    RecyclerView.Adapter<ChatDetailsAdapter.MyChatHolder>() {
//
//    //    private var chatList = ArrayList<Chat.ChatModel>()
//    val source = SimpleDateFormat("yyyy-MM-dd")
//    val target = SimpleDateFormat("dd MMM, yyyy")
//
//
//    var chatInterface: ChatInterface? = null
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): MyChatHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.chat_adapter_layout, parent, false)
//        return MyChatHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyChatHolder, position: Int) {
//
//        try {
//
//            holder.tvHeaderDate.text = target.format(source.parse(chatList[position].only_date))
//
//            if (position > 0) {
//
//                if (chatList[position].only_date == chatList.get(position - 1).only_date) {
//                    holder.tvHeaderDate!!.visibility = View.GONE
//                } else {
//                    holder.tvHeaderDate!!.visibility = View.VISIBLE
//                }
//            } else {
//                holder.tvHeaderDate!!.visibility = View.VISIBLE
//            }
//
//            if (chatList[position].senderId == PreferenceFile.getInstance().getPreferenceData(
//                    context,
//                    WebUrls.KEY_USERID
//                )
//            ) {
//
//                holder.clMyMessageLayout.visibility = View.VISIBLE
//                holder.clSecondUser.visibility = View.GONE
//
//                Log.e("MessageType", chatList[position].messageType)
//
//                if (chatList[position].replied_text.isNotEmpty()) {
//
//                    holder.llOnRepliedMessageSender.visibility = View.VISIBLE
//
//                    Log.e("replied_text", chatList[position].replied_text)
//                    Log.e("replied_message_type", chatList[position].replied_message_type)
//
//
//                    if (chatList[position].replied_message_type == "media") {
//
//                        holder.llOnRepliedSenderMedia.visibility = View.VISIBLE
//
//                        Log.e("ADAPTER", "fileType: " + chatList[position].replied_file_type)
//
//                        when {
//
//                            chatList[position].replied_file_type == "document" -> {
//
//                                Log.e(
//                                    "ADAPTER",
//                                    "document: " + WebUrls.CHAT_MEDIA_URL + chatList[position].replied_text
//                                )
//                                holder.tvOnRepliedMessageSender.text = "File"
//                                Picasso.with(context)
//                                    .load(R.drawable.doc_sender)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedSenderMedia)
//                            }
//
//                            chatList[position].replied_file_type == "image" || chatList[position].replied_file_type == "gif" -> {
//
//                                Log.e(
//                                    "ADAPTER",
//                                    "image: " + WebUrls.CHAT_MEDIA_URL + chatList[position].replied_text
//                                )
//                                holder.tvOnRepliedMessageSender.text = "Image"
//
//                                Picasso.with(context)
//                                    .load(WebUrls.CHAT_MEDIA_URL + chatList[position].replied_text)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedSenderMedia)
//                            }
//
//                            chatList[position].replied_file_type == "video" -> {
//
//                                Log.e(
//                                    "ADAPTER",
//                                    "VIDEO: " + WebUrls.CHAT_MEDIA_URL + chatList[position].replied_thumb
//                                )
//                                holder.tvOnRepliedMessageSender.text = "Video"
//                                Picasso.with(context)
//                                    .load(WebUrls.CHAT_MEDIA_URL + chatList[position].replied_thumb)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedSenderMedia)
//                            }
//
//                            chatList[position].replied_file_type == "audio" -> {
//
//                                Log.e("ADAPTER", "AUDIO: ")
//
//                                holder.tvOnRepliedMessageSender.text = "Voice Message"
//
//                                Picasso.with(context)
//                                    .load(R.drawable.audio_icon_white)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedSenderMedia)
//                            }
//
//                        }
//
//                    } else {
//                        holder.llOnRepliedSenderMedia.visibility = View.GONE
//                        holder.tvOnRepliedMessageSender.text = chatList[position].replied_text
//
//                    }
//
//                } else {
//                    holder.tvOnRepliedMessageSender.text = chatList[position].replied_text
//
//                    holder.llOnRepliedMessageSender.visibility = View.GONE
//
//                }
//
//                holder.tvChatMyTime.text = Common.changeChatDateFormat(chatList[position].createdAt)
//
//                Log.e(
//                    "TimeSet",
//                    "  " + holder.tvChatMyTime.text.toString() + "   " + chatList[position].createdAt
//                )
//
//                if (chatList[position].messageType == "media") {
//
//                    holder.tvMyMessage.visibility = View.GONE
//
//                    when {
//
//                        chatList[position].fileType == "document" -> {
//
//                            holder.llMyImage.visibility = View.VISIBLE
//
//                            Log.e("document", WebUrls.CHAT_MEDIA_URL + chatList[position].message)
//
//                            Picasso.with(context).load(R.drawable.doc_sender)
//                                .placeholder(R.drawable.no_image)
//                                .resize(150, 200)
//                                .into(holder.ivMyChatImage)
//
//                            holder.llPlayChatVideo.visibility = View.GONE
//                            holder.clMyAudio.visibility = View.GONE
//
//                        }
//
//                        chatList[position].fileType == "image" || chatList[position].fileType == "gif" -> {
//
//                            holder.llMyImage.visibility = View.VISIBLE
//
//
////                        Log.e("MediaImage", WebUrls.CHAT_MEDIA_URL + chatList[position].message)
//
//                            Picasso.with(context).load(
//                                WebUrls.CHAT_MEDIA_URL
//                                        + chatList[position].message
//                            ).placeholder(R.drawable.no_image)
//                                .resize(200, 200)
//                                .into(holder.ivMyChatImage)
//
//
//                            holder.llPlayChatVideo.visibility = View.GONE
//                            holder.clMyAudio.visibility = View.GONE
//
//                        }
//                        chatList[position].fileType == "video" -> {
//
//                            holder.llMyImage.visibility = View.VISIBLE
//
//
////                        Log.e("MediaImage", WebUrls.CHAT_MEDIA_URL + chatList[position].thumb)
//
//                            //decode base64 string to image
////                        var  imageBytes = Base64.decode(chatList[position].thumb, Base64.DEFAULT)
////                        val decodedImage =
////                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
////                        holder.ivChatSecondMedia.setImageBitmap(decodedImage)
//
//
//                            Picasso.with(context).load(
//                                WebUrls.CHAT_MEDIA_URL
//                                        + chatList[position].thumb
//                            ).placeholder(R.drawable.no_image)
//                                .resize(200, 200)
//                                .into(holder.ivMyChatImage)
//
//                            holder.llPlayChatVideo.visibility = View.VISIBLE
//                            holder.clMyAudio.visibility = View.GONE
//
//                        }
//                        chatList[position].fileType == "audio" -> {
//
//                            holder.setMediaPlayer(
//                                holder.seekbarMyAudio,
//                                holder.ivPlayMyAudio,
//                                holder.ivStopMyAudio,
//                                WebUrls.CHAT_MEDIA_URL + chatList[position].message,
//                                holder.tvRSideAudioTimer,
//                                position
//                            )
//
//                            holder.llMyImage.visibility = View.GONE
//                            holder.clMyAudio.visibility = View.VISIBLE
//                            holder.llPlayChatVideo.visibility = View.GONE
//
//
//                            holder.llPlayMyAudio.setOnClickListener {
//
//                                holder.playSound(
//                                    WebUrls.CHAT_MEDIA_URL + chatList[position].message,
//                                    holder.ivPlayMyAudio,
//                                    holder.ivStopMyAudio,
//                                    holder.seekbarMyAudio
//                                )
//
//                            }
//
//
//                        }
//                    }
//
//
//                } else {
//                    holder.tvMyMessage.visibility = View.VISIBLE
//                    holder.llMyImage.visibility = View.GONE
//                    holder.tvMyMessage.text = chatList[position].message
//
//                }
//
//                Picasso.with(context).load(
//                    WebUrls.PROFILE_IMAGE_URL
//                            + chatList[0].senderProfile
//                ).placeholder(R.drawable.no_image)
//                    .resize(50, 50)
//                    .into(holder.civMyImage)
//
//                holder.tvMainName.text = chatList[0].senderName
//
//            } else {
//
//                holder.clMyMessageLayout.visibility = View.GONE
//                holder.clSecondUser.visibility = View.VISIBLE
//
//
//                if (chatList[position].replied_text.isNotEmpty()) {
//
//                    holder.llOnRepliedMessageReceiver.visibility = View.VISIBLE
//
//
//                    if (chatList[position].replied_message_type.equals("media")) {
//
//                        holder.llOnRepliedReceiverMedia.visibility = View.VISIBLE
//
//                        when {
//
//                            chatList[position].replied_file_type == "document" -> {
//
//                                holder.tvOnRepliedMessageReceiver.text = "File"
//
//                                Picasso.with(context)
//                                    .load(R.drawable.doc_sender)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedReceiverMedia)
//                            }
//
//                            chatList[position].replied_file_type == "image" || chatList[position].replied_file_type == "gif" -> {
//
//                                holder.tvOnRepliedMessageReceiver.text = "Image"
//
//                                Picasso.with(context)
//                                    .load(WebUrls.CHAT_MEDIA_URL + chatList[position].replied_text)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedReceiverMedia)
//                            }
//
//                            chatList[position].replied_file_type == "video" -> {
//
//                                holder.tvOnRepliedMessageReceiver.text = "Video"
//
//
//                                Picasso.with(context)
//                                    .load(WebUrls.CHAT_MEDIA_URL + chatList[position].replied_thumb)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedReceiverMedia)
//                            }
//
//                            chatList[position].replied_file_type == "audio" -> {
//
//                                holder.tvOnRepliedMessageReceiver.text = "Voice Message"
//
//
//                                Picasso.with(context)
//                                    .load(R.drawable.audio_icon_white)
//                                    .placeholder(R.drawable.no_image)
//                                    .resize(40, 40).into(holder.ivOnRepliedReceiverMedia)
//                            }
//
//                        }
//
//                    } else {
//                        holder.tvOnRepliedMessageReceiver.text = chatList[position].replied_text
//
//                        holder.llOnRepliedReceiverMedia.visibility = View.GONE
//                    }
//
//                } else {
//                    holder.llOnRepliedMessageReceiver.visibility = View.GONE
//
//                }
//
//
//                holder.tvChatSecondPersonTime.text =
//                    Common.changeChatDateFormat(chatList[position].createdAt)
//
//
//                if (chatList[position].messageType == "media") {
//
//                    holder.tvSecondPersonMessage.visibility = View.GONE
//
//                    when {
//
//                        chatList[position].fileType == "document" -> {
//
//                            holder.cvSecondPersonAudio.visibility = View.GONE
//                            holder.llSecondPersonImage.visibility = View.VISIBLE
//
//                            Log.e("document", WebUrls.CHAT_MEDIA_URL + chatList[position].message)
//
//
//                            Picasso.with(context).load(R.drawable.doc_receiver)
//                                .placeholder(R.drawable.no_image)
//                                .resize(150, 200)
//                                .into(holder.ivChatSecondMedia)
//
//                            holder.llPlaySecondChatVideo.visibility = View.GONE
//
//                        }
//
//                        chatList[position].fileType == "image" || chatList[position].fileType == "gif" -> {
//                            holder.cvSecondPersonAudio.visibility = View.GONE
//                            holder.llSecondPersonImage.visibility = View.VISIBLE
//
////                        Log.e("MediaImage", WebUrls.CHAT_MEDIA_URL + chatList[position].message)
//
//                            Picasso.with(context).load(
//                                WebUrls.CHAT_MEDIA_URL
//                                        + chatList[position].message
//                            ).placeholder(R.drawable.no_image)
//                                .resize(200, 200)
//                                .into(holder.ivChatSecondMedia)
//
//                            holder.llPlaySecondChatVideo.visibility = View.GONE
//                        }
//
//                        chatList[position].fileType == "video" -> {
//
//                            holder.cvSecondPersonAudio.visibility = View.GONE
//                            holder.llPlaySecondChatVideo.visibility = View.VISIBLE
//                            holder.llSecondPersonImage.visibility = View.VISIBLE
//
////                        Log.e("MediaImage", "ABCD:"+WebUrls.CHAT_MEDIA_URL + chatList[position].thumb+" "+chatList[position].message)
//
//                            //decode base64 string to image
////                        var  imageBytes = Base64.decode(chatList[position].thumb, Base64.DEFAULT)
////                        val decodedImage =
////                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
////                        holder.ivChatSecondMedia.setImageBitmap(decodedImage)
//
//                            Picasso.with(context).load(
//                                WebUrls.CHAT_MEDIA_URL
//                                        + chatList[position].thumb
//                            ).placeholder(R.drawable.no_image)
//                                .resize(200, 200)
//                                .into(holder.ivChatSecondMedia)
//
//                        }
//                        chatList[position].fileType == "audio" -> {
//
//                            holder.setMediaPlayer(
//                                holder.seekbarYourAudio,
//                                holder.ivLSidePlayAudio,
//                                holder.ivStopLSideAudio,
//                                WebUrls.CHAT_MEDIA_URL + chatList[position].message,
//                                holder.tvLSideAudioTimer, position
//                            )
//
//                            holder.llSecondPersonImage.visibility = View.GONE
//                            holder.llPlaySecondChatVideo.visibility = View.GONE
//                            holder.cvSecondPersonAudio.visibility = View.VISIBLE
//                            holder.seekbarMyAudio.max = 99
//
//                            holder.rlPlayLSideAudio.setOnClickListener {
//
//                                Log.e("ADAPTER_CLICK", "CLICKED")
//
//                                holder.playSound(
//                                    WebUrls.CHAT_MEDIA_URL + chatList[position].message,
//                                    holder.ivLSidePlayAudio,
//                                    holder.ivStopLSideAudio,
//                                    holder.seekbarYourAudio
//                                )
//                            }
//                        }
//                    }
//
//                } else {
//                    holder.tvSecondPersonMessage.text = chatList[position].message
//                    holder.tvSecondPersonMessage.visibility = View.VISIBLE
//                    holder.llSecondPersonImage.visibility = View.GONE
//                }
//
////                Log.e(
////                    "RECEIVER:", "groupId:" + chatList[position].groupId + " " +
////                            "receiverId:" + chatList!![position].receiverProfileImage + " name:" + chatList!![position].receiverName
////                )
//
//                Log.e("GROUPID", "" + chatList[position].groupId)
//                if (chatList[position].groupId.isNotEmpty()) {
//                    Picasso.with(context).load(
//                        WebUrls.PROFILE_IMAGE_URL
//                                + chatList[position].receiverProfileImage
//                    ).placeholder(R.drawable.no_image)
//                        .resize(50, 50)
//                        .into(holder.civSecondPerson)
//                    holder.tvRecMainName.text = chatList[position].receiverName
//                    holder.tvMainName.visibility = View.VISIBLE
//                    holder.civMyImage.visibility = View.VISIBLE
//                    holder.tvRecMainName.visibility = View.VISIBLE
//                    holder.civSecondPerson.visibility = View.VISIBLE
//
//                } else {
//                    Picasso.with(context).load(
//                        WebUrls.PROFILE_IMAGE_URL
//                                + chatList[0].receiverProfileImage
//                    ).placeholder(R.drawable.no_image)
//                        .resize(50, 50)
//                        .into(holder.civSecondPerson)
//                    holder.tvRecMainName.text = chatList[0].receiverName
//
//                    holder.tvRecMainName.visibility = View.GONE
//                    holder.civSecondPerson.visibility = View.VISIBLE
//                    holder.tvMainName.visibility = View.VISIBLE
//                    holder.civMyImage.visibility = View.VISIBLE
//                }
//
//            }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return chatList.size
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }
//
//    inner class MyChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnTouchListener, ChatAdapterHandlerInterface {
//
//        init {
//
//            if (Chat.chatAHInterface == null) {
//                Chat.chatAHInterface = this
//            }
//
//        }
//
//        var mediaPlayer: MediaPlayer? = null
//        var mediaFileLengthInMilliseconds: Int = 0
//
//        override fun stopMediaPlayer() {
//
//            Log.e("ADAPTER_CLICK", "stopMediaPlayer")
//
//
//            if (mediaPlayer != null) {
//                if (mediaPlayer!!.isPlaying) {
//                    mediaPlayer!!.stop()
//
//                }
//                mediaPlayer!!.release()
//                mediaPlayer = null
////                Log.e("InMediaPlayer","NotNull")
//
//            } else {
////                Log.e("InMediaPlayer","Null")
//            }
//        }
//
//        var llOnRepliedMessageReceiver =
//            itemView.findViewById<LinearLayout>(R.id.llOnRepliedMessageReceiver)
//        var tvOnRepliedMessageReceiver =
//            itemView.findViewById<TextView>(R.id.tvOnRepliedMessageReceiver)
//        var llOnRepliedReceiverMedia =
//            itemView.findViewById<LinearLayout>(R.id.llOnRepliedReceiverMedia)
//        var ivOnRepliedReceiverMedia =
//            itemView.findViewById<ImageView>(R.id.ivOnRepliedReceiverMedia)
//
//        var llOnRepliedMessageSender =
//            itemView.findViewById<LinearLayout>(R.id.llOnRepliedMessageSender)
//        var tvOnRepliedMessageSender =
//            itemView.findViewById<TextView>(R.id.tvOnRepliedMessageSender)
//        var llOnRepliedSenderMedia =
//            itemView.findViewById<LinearLayout>(R.id.llOnRepliedSenderMedia)
//        var ivOnRepliedSenderMedia = itemView.findViewById<ImageView>(R.id.ivOnRepliedSenderMedia)
//
//        var clMain = itemView.findViewById<ConstraintLayout>(R.id.clMain)
//        var tvMainName = itemView.findViewById<TextView>(R.id.tvMainName)
//        var tvRecMainName = itemView.findViewById<TextView>(R.id.tvRecMainName)
//        var tvHeaderDate = itemView.findViewById<TextView>(R.id.tvHeaderDate)
//        var tvMyMessage = itemView.findViewById<TextView>(R.id.tvMyMessage)
//        var tvSecondPersonMessage = itemView.findViewById<TextView>(R.id.tvSecondPersonMessage)
//        var clSender = itemView.findViewById<ConstraintLayout>(R.id.clSender)
//        var clReceiver = itemView.findViewById<ConstraintLayout>(R.id.clReceiver)
//        var clSecondUser = itemView.findViewById<ConstraintLayout>(R.id.clSecondUser)
//        var clMyMessageLayout = itemView.findViewById<ConstraintLayout>(R.id.clMyMessageLayout)
//        var civSecondPerson = itemView.findViewById<CircleImageView>(R.id.civSecondPerson)
//        var civMyImage = itemView.findViewById<CircleImageView>(R.id.civMyImage)
//        var tvChatMyTime = itemView.findViewById<TextView>(R.id.tvChatMyTime)
//        var tvChatSecondPersonTime = itemView.findViewById<TextView>(R.id.tvChatSecondPersonTime)
//        var ivMyChatImage = itemView.findViewById<RoundedImageView>(R.id.ivMyChatImage)
//        var ivChatSecondMedia = itemView.findViewById<RoundedImageView>(R.id.ivChatSecondMedia)
//        var llMyImage = itemView.findViewById<LinearLayout>(R.id.llMyImage)
//        var llSecondPersonImage = itemView.findViewById<LinearLayout>(R.id.llSecondPersonImage)
//        var llPlayChatVideo = itemView.findViewById<RoundKornerLinearLayout>(R.id.llPlayChatVideo)
//        var llPlaySecondChatVideo =
//            itemView.findViewById<RoundKornerLinearLayout>(R.id.llPlaySecondChatVideo)
//        var clMyAudio = itemView.findViewById<RelativeLayout>(R.id.clMyAudio)
//        var cvSecondPersonAudio = itemView.findViewById<ConstraintLayout>(R.id.cvSecondPersonAudio)
//        var seekbarMyAudio = itemView.findViewById<SeekBar>(R.id.seekbarMyAudio)
//        var seekbarYourAudio = itemView.findViewById<SeekBar>(R.id.seekbarYourAudio)
//        var llPlayMyAudio = itemView.findViewById<RelativeLayout>(R.id.llPlayMyAudio)
//        var ivPlayMyAudio = itemView.findViewById<ImageView>(R.id.ivPlayMyAudio)
//        var ivStopMyAudio = itemView.findViewById<ImageView>(R.id.ivStopMyAudio)
//        var tvRSideAudioTimer = itemView.findViewById<TextView>(R.id.tvRSideAudioTimer)
//        var tvLSideAudioTimer = itemView.findViewById<TextView>(R.id.tvLSideAudioTimer)
//        var ivLSidePlayAudio = itemView.findViewById<ImageView>(R.id.ivLSidePlayAudio)
//        var ivStopLSideAudio = itemView.findViewById<ImageView>(R.id.ivStopLSideAudio)
//        var rlPlayLSideAudio = itemView.findViewById<RelativeLayout>(R.id.rlPlayLSideAudio)
//
//        init {
//
//            seekbarMyAudio.setOnTouchListener(this)
//            seekbarYourAudio.setOnTouchListener(this)
//
//            itemView.setOnClickListener {
//                if (chatList[layoutPosition].messageType == "media") {
//
//                    when {
//                        chatList[layoutPosition].fileType == "gif" -> {
//                            chatInterface!!.itemClick(layoutPosition, chatList)
//                        }
//                        chatList[layoutPosition].fileType == "image" -> {
//                            chatInterface!!.itemClick(layoutPosition, chatList)
//                        }
//                        chatList[layoutPosition].fileType == "document" -> {
//                            chatInterface!!.itemClick(layoutPosition, chatList)
//                        }
//                        chatList[layoutPosition].fileType == "video" -> {
//                            chatInterface!!.itemClick(layoutPosition, chatList)
////                            chatInterface!!.openEnlargeFragment(chatList[layoutPosition])
//                        }
//
//                    }
//                }
//            }
//        }
//
//        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//
//            when (v!!.id) {
//
//                R.id.seekbarMyAudio -> {
//
//                    Log.e("ADAPTER_CLICK", "seekbarMyAudio")
//
//                    if (mediaPlayer != null) {
//                        if (mediaPlayer!!.isPlaying) {
//                            val sb = v as SeekBar
//                            val playPositionInMillisecconds =
//                                mediaFileLengthInMilliseconds / 100 * sb.progress
//                            mediaPlayer!!.seekTo(playPositionInMillisecconds)
//                        }
//                    }
//                }
//
//                R.id.seekbarYourAudio -> {
//
//                    Log.e("ADAPTER_CLICK", "seekbarYourAudio")
//
//                    if (mediaPlayer != null) {
//                        if (mediaPlayer!!.isPlaying) {
//                            val sb = v as SeekBar
//                            val playPositionInMillisecconds =
//                                mediaFileLengthInMilliseconds / 100 * sb.progress
//                            mediaPlayer!!.seekTo(playPositionInMillisecconds)
//                        }
//                    }
//                }
//            }
//
//            return false
//
//        }
//
//        fun setMediaPlayer(
//            seekBarProgress: SeekBar,
//            playImage: ImageView,
//            stopImage: ImageView,
//            source: String,
//            textView: TextView,
//            position: Int
//        ) {
//
//
//            mediaPlayer = MediaPlayer()
//
//            mediaPlayer!!.setOnBufferingUpdateListener { mp, percent ->
//                seekBarProgress.secondaryProgress = percent
//            }
//
//
//            mediaPlayer!!.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
//                override fun onPrepared(mp: MediaPlayer?) {
//                    var d = mp!!.duration
////                    Log.e("DuratrionDD", d.toString())
//                }
//            })
//
//            mediaPlayer!!.setOnCompletionListener {
//
//                seekBarProgress.progress = 0
//                playImage.visibility = View.VISIBLE
//                stopImage.visibility = View.GONE
//            }
//
//
//            mediaPlayer!!.setOnPreparedListener { mp ->
//                var output = Common.getTimeFromMillis(mp!!.duration.toLong())
//                chatList[position].audioFileDuration = output
//                textView.text = output
//            }
//
//            try {
//                mediaPlayer!!.setDataSource(source) // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
//                mediaPlayer!!.prepareAsync() // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//
//        private fun primarySeekBarProgressUpdater(seekBarProgress: SeekBar) {
//            if (mediaPlayer != null) {
////                Log.e("MediaPlayerosition", (mediaPlayer!!.currentPosition.toString()))
//
////                Log.e("MediaFileLength", mediaFileLengthInMilliseconds.toString())
//                var cp = mediaPlayer!!.currentPosition
//
////                Log.e("CurrentPOs", cp.toString())
//
//                var total = mediaPlayer!!.duration
//                if (mediaPlayer!!.isPlaying && cp < total) {
//
//                    //seekBarProgress.progress = cp
//
//                    if (total > 0) {
//                        var progress =
//                            ((mediaPlayer!!.currentPosition) * 100) / total
//                        if (progress != 100) {
//                            seekBarProgress.progress = progress
//                        }
//                    }
//                }
//
////                Log.e("PrimarytProgress", seekBarProgress.progress.toString())
//
//                if (mediaPlayer!!.isPlaying) {
//                    val notification = Runnable { primarySeekBarProgressUpdater(seekBarProgress) }
//                    Handler().postDelayed(notification, 1000)
//                }
//            }
//        }
//
//        fun playSound(
//            source: String,
//            playSoundImage: ImageView,
//            stopImage: ImageView,
//            seekBar: SeekBar
//        ) {
//
////            Log.e("AudioDuration",mediaPlayer!!.duration.toString())
//
//            if (!mediaPlayer!!.isPlaying) {
//
//                playSoundImage.visibility = View.INVISIBLE
//                stopImage.visibility = View.VISIBLE
//                mediaPlayer!!.start()
//            } else {
//                mediaPlayer!!.pause()
//                playSoundImage.visibility = View.VISIBLE
//                stopImage.visibility = View.GONE
//
//            }
//
//            primarySeekBarProgressUpdater(seekBar)
//        }
//
//    }
//
//
//    fun add(chatModel: Chat.ChatModel) {
//        chatList.add(chatModel)
//        notifyItemInserted(chatList.size - 1)
//    }
//
//
//    fun addData(list: ArrayList<Chat.ChatModel>) {
//        chatList.clear()
//        for (i in 0 until list.size) {
//            add(list[i])
//        }
//
//    }
//
//
//    fun addChatModel(chatModel: Chat.ChatModel, recyclerView: RecyclerView) {
//        chatList.add(chatModel)
//
//        notifyItemInserted(chatList.size - 1)
//        Handler().postDelayed({
//            recyclerView.smoothScrollToPosition(chatList.size - 1)
//        }, 1000)
//
//    }
//
//
//    fun getChatInterface(chatInterface: ChatInterface) {
//        this.chatInterface = chatInterface
//    }
//
//
//    interface ChatInterface {
//        fun openEnlargeFragment(chatModel: Chat.ChatModel)
//        fun itemClick(pos: Int, chatList: ArrayList<Chat.ChatModel>)
//    }
//
//
//    inner class GetDuration(var path: String) : AsyncTask<Void, Void, String>() {
//        override fun doInBackground(vararg params: Void?): String {
//            var mp = MediaPlayer.create(context as Activity, Uri.parse(path))
//            return mp.duration.toString()
//
//        }
//
//    }
//
//
//}