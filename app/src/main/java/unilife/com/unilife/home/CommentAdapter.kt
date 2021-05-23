package unilife.com.unilife.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import android.text.format.DateUtils




class CommentAdapter(
   var context: Context,
    commentArrayList: ArrayList<Comment.CommentData>
) : RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    var now: Long = 0
    var time:Long = 0
    var commentArrayList: ArrayList<Comment.CommentData> = ArrayList()
    lateinit var onItemClick: onItemClickListener

    init {
        this.commentArrayList = commentArrayList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comment_adapter, viewGroup, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        now= System.currentTimeMillis()

        try {
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            time=sdf.parse(commentArrayList[i].updated_at).time
        }
        catch (e:Exception){
            e.printStackTrace()
        }

        val mail = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

        myViewHolder.tvtime.text = mail.toString()

        if(commentArrayList[i].commenter_profile_image!=null){
            Picasso.with(context).load(WebUrls.PROFILE_IMAGE_URL+commentArrayList[i].commenter_profile_image).placeholder(R.drawable.no_image)
                .into(myViewHolder.ivuser)

        }
        else{
            myViewHolder.ivuser.setImageResource(unilife.com.unilife.R.drawable.no_image)
        }

        myViewHolder.tvemail.text = "@"+commentArrayList[i].commenter_username
        myViewHolder.tvshowcomment.text = commentArrayList[i].comment
        myViewHolder.tvname.text = commentArrayList[i].commenter_username


        if(commentArrayList[i].commentlikeList!=null) {
            val totallikes = commentArrayList[i].commentlikeList.size
            if (commentArrayList[i].commentlikeList.size > 1) {
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
/*        if (homeList[i].totalPostLikesList != null) {
            val totLikes = homeList[i].totalPostLikesList!!.size
            if (homeList[i].totalPostLikesList!!.size == 1) {
                myViewHolder.tvTotLikes.text = "$totLikes Like"
            } else {
                myViewHolder.tvTotLikes.text = "$totLikes Likes"
            }
        } else {
            myViewHolder.tvTotLikes.text = "0 Like"
        }*/

        if(commentArrayList[i].userlikedcomment == "1"){
            myViewHolder.btnlikecomment.setImageResource(R.drawable.red_heart)

        }
        else
        {
            myViewHolder.btnlikecomment.setImageResource(R.drawable.ic_heart_new)
        }


        if(commentArrayList[i].replycommentList.size>0){

            myViewHolder.tvViewAllReply.visibility = View.VISIBLE

        }
        else{
            myViewHolder.tvViewAllReply.visibility = View.GONE
        }

        myViewHolder.tvViewAllReply.setOnClickListener {

            if(myViewHolder.rycreplyComment.isShown ){

                myViewHolder.rycreplyComment.visibility = View.GONE
            }
            else{
                myViewHolder.rycreplyComment.visibility = View.VISIBLE
            }


        }



        myViewHolder.tvlike.setOnClickListener {
            context.startActivity(Intent(context,Like::class.java)
                .putExtra("post_id","1")
                .putExtra("reply_id", "1")
                .putExtra("comment_id", commentArrayList[i].comment_id)
            )

        }




        myViewHolder.btnlikecomment.setOnClickListener {
            onItemClick.onLikeUnLikeClick2(i, commentArrayList[i].userlikedcomment)
        }

        myViewHolder.tvViewAllReply.setOnClickListener {
            onItemClick.onViewReply(i, myViewHolder.rycreplyComment)
        }

        myViewHolder.tvReply.setOnClickListener {
            var postValue = "reply"
            onItemClick.onReplyclick(i,postValue,commentArrayList[i].comment_id)
        }



    }


    override fun getItemCount(): Int {
        Log.e("sksdnfklsdndsl",""+commentArrayList.size)
        return commentArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var ivuser : ImageView = itemView.findViewById(R.id.ivuser)
        var btnlikecomment : ImageView = itemView.findViewById(R.id.btnlikecomment)
        var tvname : TextView = itemView.findViewById(R.id.tvname)
        var tvtime : TextView = itemView.findViewById(R.id.tvtime)
        var tvemail : TextView = itemView.findViewById(R.id.tvemail)
        var tvshowcomment : TextView = itemView.findViewById(R.id.tvshowcomment)
        var tvReply : TextView = itemView.findViewById(R.id.tvReply)
        var tvlike : TextView = itemView.findViewById(R.id.tvlike)
        var tvViewAllReply : TextView = itemView.findViewById(R.id.tvViewAllReply)
        var rycreplyComment : RecyclerView = itemView.findViewById(R.id.rycreplyComment)

    }


    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onLikeUnLikeClick2(position: Int, statusLikeUnLike : String)
        public fun onViewReply(position: Int,rView: RecyclerView)
        public fun onReplyclick(position: Int, postType: String, comment_id: String)

    }

}
