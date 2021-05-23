package unilife.com.unilife.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common

class Comment : AppCompatActivity(), RetrofitResponse, ReplyAdapter.onItemClickListener,
    CommentAdapter.onItemClickListener {

    var commentArrayList: ArrayList<CommentData> = ArrayList()
    var post_id = ""
    var comment = ""
    var commentAdapter: CommentAdapter? = null
    var replyAdapter: ReplyAdapter? = null
    var postValue = ""
    var commentPos = -1
    var comment_id1 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        getIntentData()
        callViewComments()
        setData()

        btnPost.setOnClickListener {
            comment = tvMakeComment.text.toString()
            if (comment.isNotEmpty()) {

                if (postValue == "reply") {
                    callReplyComment()
                } else {
                    callPostComment()
                }
            } else {
                Toast.makeText(this, "Please write something first", Toast.LENGTH_SHORT).show()
            }
        }


        tvTitle.text = "Comments"
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
        ivNotification.visibility = View.GONE


    }


    override fun onResponse(requestCode: Int, response: String) {

        try {
            when (requestCode) {
                WebUrls.SHOW_SINGLE_POST_COMMENTS_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {

                        commentArrayList.clear()

                        if (obj.has("data")) {
                            var data = obj.getJSONArray("data")
                            Log.e("Sffsdfdfdfdsd", "  " + data.length())
                            if (data.length() > 0) {
                                for (i in 0 until data.length()) {
                                    var dataobj = data.getJSONObject(i)
                                    var commentData = CommentData()
                                    commentData.post_id = dataobj.getString("post_id")
                                    commentData.comment_id = dataobj.getString("id")
                                    commentData.comment = dataobj.getString("comment")
                                    commentData.updated_at = dataobj.getString("updated_at")

                                    if (!dataobj.isNull("comment_by_user")) {
                                        var comment_by_user =
                                            dataobj.getJSONObject("comment_by_user")
                                        Log.e("Sffsdfdfdfdsd", "  $comment_by_user")
                                        commentData.commenter_profile_image =
                                            comment_by_user.getString("profile_image")
                                        commentData.commenter_email =
                                            comment_by_user.getString("university_school_email")
                                        commentData.commenter_username =
                                            comment_by_user.getString("username")

                                    }
                                    if (dataobj.has("comment_liked")) {
                                        var comment_liked = dataobj.getJSONArray("comment_liked")
                                        if (comment_liked.length() > 0) {
                                            for (j in 0 until comment_liked.length()) {
                                                var commentlikedobj = comment_liked.getJSONObject(j)
                                                var id = commentlikedobj.getString("id")

                                                commentData.commentlikeList.add(id)
                                            }
                                        }
                                    }

                                    if (dataobj.has("user_comment_liked")) {
                                        var user_comment_liked =
                                            dataobj.getJSONArray("user_comment_liked")
                                        if (user_comment_liked.length() > 0) {
                                            commentData.userlikedcomment = "1"
                                        } else {
                                            commentData.userlikedcomment = "0"
                                        }
                                    }


                                    if (dataobj.has("comment_reply")) {
                                        var comment_reply = dataobj.getJSONArray("comment_reply")
                                        if (comment_reply.length() > 0) {
                                            for (k in 0 until comment_reply.length()) {
                                                var comment_replyobj =
                                                    comment_reply.getJSONObject(k)
                                                var replyCommentData = ReplyCommentData()
                                                replyCommentData.reply_id =
                                                    comment_replyobj.getString("id")
                                                replyCommentData.comment_id =
                                                    comment_replyobj.getString("comment_id")
                                                replyCommentData.user_id =
                                                    comment_replyobj.getString("user_id")
                                                replyCommentData.reply =
                                                    comment_replyobj.getString("reply")
                                                replyCommentData.updated_at =
                                                    comment_replyobj.getString("updated_at")

                                                if (comment_replyobj.has("reply_liked")) {
                                                    var reply_liked =
                                                        comment_replyobj.getJSONArray("reply_liked")
                                                    if (reply_liked.length() > 0) {
                                                        for (l in 0 until reply_liked.length()) {
                                                            var reply_likedobj =
                                                                reply_liked.getJSONObject(l)
                                                            var id =
                                                                reply_likedobj.getString("user_id")
                                                            replyCommentData.replylikeList.add(id)
                                                        }
                                                    }
                                                }
                                                if (comment_replyobj.has("reply_by_user")) {
                                                    var reply_by_user =
                                                        comment_replyobj.getJSONObject("reply_by_user")
                                                    if (reply_by_user != null) {
                                                        replyCommentData.username =
                                                            reply_by_user.getString("username")
                                                        replyCommentData.university_school_email =
                                                            reply_by_user.getString("university_school_email")
                                                        replyCommentData.profile_image =
                                                            reply_by_user.getString("profile_image")
                                                    }
                                                }
                                                if (comment_replyobj.has("user_reply_liked")) {
                                                    val user_reply_liked =
                                                        comment_replyobj.getJSONArray("user_reply_liked")
                                                    if (user_reply_liked.length() > 0) {

                                                        replyCommentData.userliked = "1"

                                                    } else {
                                                        replyCommentData.userliked = "0"
                                                    }
                                                }

                                                commentData.replycommentList.add(replyCommentData)
                                            }
                                        }
                                    }

                                    commentArrayList.add(commentData)
                                }


                                /*   for(i in 0 until commentArrayList.size-1)
                                   {
                                       Log.e("Dsdfsdf",""+commentArrayList[i].replycommentList.size)
                                       for(j in 0 until commentArrayList[i].replycommentList.size-1)
                                       {
                                           Log.e("ShowmeYourData",""+commentArrayList[i].replycommentList[j].toString())

                                       }
                                   }*/

                                setAdapter(commentArrayList)

                            } else {
                                Toast.makeText(this, "NO COMMENTS FOUND", Toast.LENGTH_SHORT).show()

                            }
                        }


                    }
                }
                WebUrls.POST_COMMENT_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {

                        Toast.makeText(this, "Comment posted successfully", Toast.LENGTH_SHORT)
                            .show()
                        if (commentAdapter != null) {
                            commentAdapter!!.notifyDataSetChanged()
                        }
                        callViewComments()
                        tvMakeComment.text.clear()

                    } else {
                        Toast.makeText(this, "Please try again ", Toast.LENGTH_SHORT).show()

                    }

                }

                WebUrls.LIKE_UNLIKE_COMMENT_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        Log.e("Response", obj.toString())
                        if (obj.has("data")) {
                            val data = obj.getJSONObject("data")
                            if (data != null) {
                                if (data.has("rows")) {
                                    val rows = data.getJSONArray("rows")
                                    if (rows.length() > 0) {
                                        commentAdapter!!.notifyDataSetChanged()
                                        //             callViewComments()
                                        Toast.makeText(this, "Liked", Toast.LENGTH_SHORT).show()
                                    } else {
                                        commentAdapter!!.notifyDataSetChanged()
                                        //     callViewComments()
                                        Toast.makeText(this, "Unliked", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }

                WebUrls.LIKE_UNLIKE_REPLY_CODE -> {
                    val obj = JSONObject(response)

                    Log.e("Response", obj.toString())
                    if (obj.getBoolean("response")) {

                        if (obj.has("data")) {
                            val data = obj.getJSONObject("data")
                            if (data != null) {
                                if (data.has("rows")) {
                                    val rows = data.getJSONArray("rows")
                                    if (rows.length() > 0) {
                                        replyAdapter!!.notifyDataSetChanged()
                                        commentAdapter!!.notifyDataSetChanged()
                                        callViewComments()
                                        Toast.makeText(this, "Liked", Toast.LENGTH_SHORT).show()
                                    } else {
                                        replyAdapter!!.notifyDataSetChanged()
                                        commentAdapter!!.notifyDataSetChanged()
                                        callViewComments()
                                        Toast.makeText(this, "Unliked", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }


                }
                WebUrls.REPLY_COMMENT_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        Log.e("Response", "" + obj.toString())
                        postValue = "vdgd"
                        Toast.makeText(this, "Reply posted successfully", Toast.LENGTH_SHORT).show()
                        commentAdapter!!.notifyDataSetChanged()
                        // replyAdapter!!.notifyDataSetChanged()
                        callViewComments()
                        tvMakeComment.text.clear()

                    } else {
                        Toast.makeText(this, "Please try again ", Toast.LENGTH_SHORT).show()
                        postValue = "vdgd"
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun replyLikeUnlike(reply_id: String) {

        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("reply_id", reply_id)

                RetrofitService(
                    this, this, WebUrls.LIKE_UNLIKE_REPLY,
                    WebUrls.LIKE_UNLIKE_REPLY_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callPostComment() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("post_id", post_id)
                postParam.put("comment", comment)
                RetrofitService(
                    this, this, WebUrls.POST_COMMENT,
                    WebUrls.POST_COMMENT_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callReplyComment() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("comment_id", comment_id1)
                postParam.put("reply", comment)
                RetrofitService(
                    this, this, WebUrls.REPLY_COMMENT,
                    WebUrls.REPLY_COMMENT_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callViewComments() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("post_id", post_id)
                RetrofitService(
                    this, this, WebUrls.SHOW_SINGLE_POST_COMMENTS,
                    WebUrls.SHOW_SINGLE_POST_COMMENTS_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun getIntentData() {
        post_id = intent.getStringExtra("post_id")
    }


    @SuppressLint("SetTextI18n")
    private fun setData() {

        try {
            tvusername.text =
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERNAME)
            tvUserid.text =
                "@" + PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERNAME)
            Picasso.with(this).load(
                WebUrls.PROFILE_IMAGE_URL + PreferenceFile.getInstance()
                    .getPreferenceData(this, WebUrls.KEY_PROFILE_IMAGE)
            ).placeholder(R.drawable.no_image).into(ivuser)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }


    fun setAdapter(commentArrayList: ArrayList<CommentData>) {
        val linearLayoutManager = LinearLayoutManager(this)
        rycComments?.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        commentAdapter = CommentAdapter(this, commentArrayList)
        rycComments?.adapter = commentAdapter

        commentAdapter!!.setOnItemClickListener(this)


    }

    private fun commentLikeUnlike(comment_id: String) {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("comment_id", comment_id)
                RetrofitService(
                    this, this, WebUrls.LIKE_UNLIKE_COMMENT,
                    WebUrls.LIKE_UNLIKE_COMMENT_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    override fun onLikeUnLikeClick(position: Int, statusLikeUnLike: String) {    /// REPLY LIKE
        // commentArrayList[commentPos].replycommentList[position].


        try {
            Log.e("commentpos ", "" + commentPos)
            if (statusLikeUnLike == "1") {
                commentArrayList[commentPos].replycommentList[position].userliked = "0"
            } else {
                commentArrayList[commentPos].replycommentList[position].userliked = "1"
            }

            replyLikeUnlike(commentArrayList[commentPos].replycommentList[position].reply_id)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onReplyclick(
        position: Int,
        postType: String,
        comment_id: String
    ) {  ///CommentREply
        commentPos = position
        postValue = postType
        comment_id1 = comment_id

        tvMakeComment.setText("@ " + commentArrayList[position].commenter_username)
    }


    override fun oninReplyclick(position: Int, postType: String, comment_id: String) {
        postValue = postType
        comment_id1 = comment_id

        tvMakeComment.setText("@ " + commentArrayList[commentPos].replycommentList[position].username)
    }


    override fun onViewReply(position: Int, rView: RecyclerView) {

        commentPos = position
        replyAdapter = ReplyAdapter(this, commentArrayList[position].replycommentList)
        rView.adapter = replyAdapter

        replyAdapter!!.setOnItemClickListener(this)


        rView.visibility = View.VISIBLE
    }

    override fun onLikeUnLikeClick2(position: Int, statusLikeUnLike: String) { /////COMMENT LIKE

        try {
            if (statusLikeUnLike == "1") {
                commentArrayList[position].userlikedcomment = "0"
            } else {
                commentArrayList[position].userlikedcomment = "1"
            }

            commentLikeUnlike(commentArrayList[position].comment_id)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    data class CommentData(
        var post_id: String = "",
        var comment_id: String = "",
        var comment: String = "",
        var updated_at: String = "",
        var commenter_profile_image: String = "",
        var commenter_username: String = "",
        var commenter_email: String = "",
        var userlikedcomment: String = "",
        var commentlikeList: ArrayList<String> = ArrayList(),
        var replycommentList: ArrayList<ReplyCommentData> = ArrayList()

    )

    data class ReplyCommentData(
        var reply_id: String = "",
        var comment_id: String = "",
        var user_id: String = "",
        var reply: String = "",
        var updated_at: String = "",
        var username: String = "",
        var profile_image: String = "",
        var university_school_email: String = "",
        var userliked: String = "",
        var replylikeList: ArrayList<String> = ArrayList()
    )
}
