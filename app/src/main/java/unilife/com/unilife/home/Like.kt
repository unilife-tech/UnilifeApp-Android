package unilife.com.unilife.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import org.json.JSONObject
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class Like : AppCompatActivity(), RetrofitResponse {


    val TAG = Like::class.java.simpleName
    var likeList: ArrayList<LikeModel> = ArrayList()
    var commentlikeList: ArrayList<LikeModel> = ArrayList()
    var replylikeList: ArrayList<LikeModel> = ArrayList()

    var post_id: String = ""
    var comment_id: String = ""
    var reply_id: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        tvTitle.text = "Likes"
        llmakecoments.visibility = View.GONE
        getIntentData()

        when {
            post_id!="1" -> viewAllLikes()
            comment_id!="1" -> {
                viewAllCommentlikes()
            }
            reply_id!="1" -> {
                viewReplyLikes()
            }
        }

        ivNotification.visibility = View.GONE

        ivBackArrow.setOnClickListener {
            super.onBackPressed()
            finish()
        }



    }

    private fun viewReplyLikes() {
        if(Alerts.isNetworkAvailable(this)){

        RetrofitService(
            this, this, WebUrls.VIEW_REPLY_LIKES + reply_id, WebUrls.VIEW_REPLY_LIKES_CODE,
            1).callService(true)
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    private fun viewAllCommentlikes() {
    if(Alerts.isNetworkAvailable(this)){
        RetrofitService(
            this, this, WebUrls.VIEW_COMMENT_LIKES + comment_id, WebUrls.VIEW_COMMENT_LIKES_CODE,
            1).callService(true)
    }
    else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
    }

    private fun getIntentData() {
        post_id = intent.getStringExtra("post_id")
        comment_id = intent.getStringExtra("comment_id")
        reply_id = intent.getStringExtra("reply_id")

        Log.e("post_id","      "+post_id)

    }


    fun setAdapterPostLikes() {
        val linearLayoutManager = LinearLayoutManager(this)
        rycComments?.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val likeadapter = LikeAdapter(this@Like, likeList)
        rycComments?.adapter = likeadapter
    }

    fun setAdapterCommentLikes() {
        val linearLayoutManager = LinearLayoutManager(this)
        rycComments?.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val likeadapter = CommentikeAdapter(this@Like,commentlikeList)
        rycComments?.adapter = likeadapter
    }
    fun setAdapterReplyLikes() {
        val linearLayoutManager = LinearLayoutManager(this)
        rycComments?.layoutManager = linearLayoutManager
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val likeadapter = ReplyLikeAdapter(this@Like, replylikeList)
        rycComments?.adapter = likeadapter
    }






    fun viewAllLikes() {
        if(Alerts.isNetworkAvailable(this)){

        RetrofitService(
            this,
            this,
            WebUrls.VIEW_ALL_POST_LIKE + post_id,
            WebUrls.VIEW_ALL_POST_LIKE_CODE,
            1
        ).callService(true)

        Log.e(
            TAG,
            "post_id:" + post_id
        )

    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    override fun onResponse(requestCode: Int, response: String) {

        when (requestCode) {

            WebUrls.VIEW_ALL_POST_LIKE_CODE -> {

                try {

                    var jsonObject = JSONObject(response)

                    Log.e(TAG, "SHOW_POST_CODE:" + jsonObject.toString())

                    var res = jsonObject.getString("response")

                    if ((res == "true")) {

                        likeList.clear()

                        var data = jsonObject.getJSONArray("data")

                        Log.e(TAG, "SHOW_POST_CODE:data:$data")

                        if (data != null && data.length() > 0) {

                            for (i in 0 until data.length()) {

                                var likeModel = LikeModel()
                                var dataObj = data.getJSONObject(i)

                                var user_post_comment_likeObj =
                                    dataObj.getJSONObject("user_post_comment_like")


                                likeModel.id = dataObj.getString("id")
                                likeModel.post_comment_id = dataObj.getString("post_comment_id")
                                likeModel.user_id = dataObj.getString("user_id")
                                likeModel.id = dataObj.getString("type")


                                likeModel.user_post_comment_like_id =
                                    user_post_comment_likeObj.getString("id")
                                likeModel.user_post_comment_like_username =
                                    user_post_comment_likeObj.getString("username")
                                likeModel.user_post_comment_like_profile_image =
                                    user_post_comment_likeObj.getString("profile_image")

                                likeList.add(likeModel)

                            }

                            Log.e(TAG,"list:"+likeList.size)

                            setAdapterPostLikes()

                        }
                    } else {
                        Common.customDialog(this@Like, "Unilife", jsonObject.getString("message"))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            WebUrls.VIEW_COMMENT_LIKES_CODE->{
             try{

                 var obj = JSONObject(response)
                 if(obj.getBoolean("response")){
                     commentlikeList.clear()
                     if(obj.has("data")){
                         var data = obj.getJSONArray("data")
                         if(data.length()>0){
                             for(i in 0 until data.length()){
                                var dataobj = data.getJSONObject(i)
                                 var likeModel = LikeModel()
                                 if(dataobj.has("user_post_comment_like")){
                                  var comment_like = dataobj.getJSONObject("user_post_comment_like")
                                     if(comment_like!=null){
                                         likeModel.user_post_comment_like_id = comment_like.getString("id")
                                         likeModel.user_post_comment_like_profile_image = comment_like.getString("profile_image")
                                         likeModel.user_post_comment_like_username = comment_like.getString("username")

                                         commentlikeList.add(likeModel)
                                     }

                                 }
                             }
                             setAdapterCommentLikes()
                         }

                     }

                 }
             }
             catch (e:Exception)
             {
                 e.printStackTrace()
             }

            }
            WebUrls.VIEW_REPLY_LIKES_CODE->{
                try{

                    var obj = JSONObject(response)
                    if(obj.getBoolean("response")){
                        replylikeList.clear()
                        if(obj.has("data")){
                            var data = obj.getJSONArray("data")
                            if(data.length()>0){
                                for(i in 0 until data.length()){
                                    var dataobj = data.getJSONObject(i)
                                    var likeModel = LikeModel()
                                    if(dataobj.has("user_post_comment_like")){
                                        var comment_like = dataobj.getJSONObject("user_post_comment_like")
                                        if(comment_like!=null){
                                            likeModel.user_post_comment_like_id = comment_like.getString("id")
                                            likeModel.user_post_comment_like_profile_image = comment_like.getString("profile_image")
                                            likeModel.user_post_comment_like_username = comment_like.getString("username")

                                            replylikeList.add(likeModel)
                                        }

                                    }
                                }
                                setAdapterReplyLikes()
                            }

                        }

                    }
                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }

            }



        }
    }
}
