package unilife.com.unilife.chat

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.enlarge_layout.*
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class FullMediaScreen : AppCompatActivity(), View.OnClickListener {
    var message_type: String = ""
    var message: String = ""
    val TAG = FullMediaScreen::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enlarge_layout)
        llEnlargeBack.setOnClickListener(this)
        llRestart.setOnClickListener(this)
        getIntentData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun getIntentData() {
        try {
            message = intent.getStringExtra("message")
            message_type = intent.getStringExtra("file_type")
            Log.e(TAG, message_type + " file_type: " + message_type + "message : " + message)
            when {
                message_type == "image" || message_type == "gif" -> {

                    webView1.visibility = View.GONE
                    ivEnlarge.visibility = View.VISIBLE
                    vvEnlarge.visibility = View.GONE

//                    Picasso.with(this).load(
//                        WebUrls.CHAT_MEDIA_URL
//                                + message
//                    ).placeholder(R.drawable.default_image)
//                        .into(ivEnlarge)

                    Glide.with(this)
                        .load(WebUrls.CHAT_MEDIA_URL + message)
                        .into(ivEnlarge)

                }
                message_type == "video" || message_type == "audio" -> {
                    webView1.visibility = View.GONE
                    ivEnlarge.visibility = View.GONE
                    vvEnlarge.visibility = View.VISIBLE
                    vvEnlarge.setVideoURI(Uri.parse(WebUrls.CHAT_MEDIA_URL + message))
                    vvEnlarge.setOnCompletionListener {
                        llRestart.visibility = View.VISIBLE
                    }
                }

                message_type == "document" -> {
                    ivEnlarge.visibility = View.GONE
                    vvEnlarge.visibility = View.GONE
                    webView1.visibility = View.VISIBLE

                    Log.e(TAG, "inside : " + message)

                    webView1.settings.javaScriptEnabled = true
                    webView1.settings.allowFileAccess = true
                    webView1.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + WebUrls.CHAT_MEDIA_URL + message)
                }

                message_type == "profile_image" -> {

                    webView1.visibility = View.GONE
                    ivEnlarge.visibility = View.VISIBLE
                    vvEnlarge.visibility = View.GONE

                    Picasso.with(this).load(
                        WebUrls.PROFILE_IMAGE_URL
                                + message
                    ).placeholder(R.drawable.default_image)
                        .into(ivEnlarge)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llEnlargeBack -> {
                finish()
            }

            R.id.llRestart -> {
                llRestart.visibility = View.GONE
                vvEnlarge.restart()
            }
        }
    }
}
