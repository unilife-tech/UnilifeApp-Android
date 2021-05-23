package unilife.com.unilife.home

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReplyAdapter(var context: Context, replycommentList: ArrayList<Comment.ReplyCommentData>) :
    RecyclerView.Adapter<ReplyAdapter.MyViewHolder>() {
    var now: Long = 0
    var time:Long = 0
    var replycommentList: ArrayList<Comment.ReplyCommentData> = ArrayList()
    lateinit var onItemClick1: onItemClickListener
    init {

        this.replycommentList = replycommentList
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comment_adapter, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return replycommentList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        now= System.currentTimeMillis()

        try {
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            time=sdf.parse(replycommentList[i].updated_at).time
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        val mail = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

        myViewHolder.tvtime.text = mail.toString()


        myViewHolder.tvViewAllReply.visibility = View.GONE

        if(replycommentList[i].profile_image!=null){
            Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL+replycommentList[i].profile_image).placeholder(R.drawable.no_image)
                .into(myViewHolder.ivuser)

        }
        else{
            myViewHolder.ivuser.setImageResource(R.drawable.no_image)
        }

        myViewHolder.tvname.text = replycommentList[i].username
        myViewHolder.tvemail.text = "@"+replycommentList[i].username
        myViewHolder.tvshowcomment.text = replycommentList[i].reply

        if(replycommentList[i].replylikeList!=null) {
            val totallikes = replycommentList[i].replylikeList.size
            if (replycommentList[i].replylikeList.size > 1 ) {
                myViewHolder.tvlike.text = "$totallikes Likes"
            }
            else
            {
                myViewHolder.tvlike.text = "$totallikes Like"
            }
        }
        else{
            myViewHolder.tvlike.text = "0 Like"
        }



        if(replycommentList[i].userliked == "1"){
            myViewHolder.btnlikecomment.setImageResource(R.drawable.red_heart)

        }
        else
        {
            myViewHolder.btnlikecomment.setImageResource(R.drawable.ic_heart_new)
        }





        myViewHolder.btnlikecomment.setOnClickListener {
            onItemClick1.onLikeUnLikeClick(i, replycommentList[i].userliked)
        }

        myViewHolder.tvReply.setOnClickListener {
            var postValue = "reply"
            onItemClick1.oninReplyclick(i,postValue,replycommentList[i].comment_id)
        }

        myViewHolder.tvlike.setOnClickListener {
            context.startActivity(
                Intent(context,Like::class.java)
                    .putExtra("post_id","1")
                    .putExtra("reply_id", replycommentList[i].reply_id)
                    .putExtra("comment_id", "1")
            )

        }


    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivuser: ImageView = itemView.findViewById(R.id.ivuser)
        var btnlikecomment: ImageView = itemView.findViewById(R.id.btnlikecomment)
        var tvname: TextView = itemView.findViewById(R.id.tvname)
        var tvtime : TextView = itemView.findViewById(R.id.tvtime)
        var tvemail: TextView = itemView.findViewById(R.id.tvemail)
        var tvshowcomment: TextView = itemView.findViewById(R.id.tvshowcomment)
        var tvReply: TextView = itemView.findViewById(R.id.tvReply)
        var tvlike: TextView = itemView.findViewById(R.id.tvlike)
        var tvViewAllReply: TextView = itemView.findViewById(R.id.tvViewAllReply)
        var rycreplyComment: RecyclerView = itemView.findViewById(R.id.rycreplyComment)
    }
    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick1 = onItemClick
    }

    public interface onItemClickListener {
        public fun onLikeUnLikeClick(position: Int, statusLikeUnLike : String)
        public fun oninReplyclick(position: Int, postType: String, comment_id: String)

    }
}
