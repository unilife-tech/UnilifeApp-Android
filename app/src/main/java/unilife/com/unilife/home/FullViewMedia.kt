package unilife.com.unilife.home

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_view_media.*
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class FullViewMedia : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_view_media)

        val attachment: String = intent.getStringExtra("data")
        val attachmenttype: String = intent.getStringExtra("datatype")

        Log.e("fxxfg", attachment)
        var btnbackFullView: ImageView = findViewById(R.id.btnbackFullView)
        btnbackFullView.setOnClickListener {
            super.onBackPressed()
        }
        if (attachmenttype == "video") {
            fullvideoview.visibility = View.VISIBLE
            fullvideoview.setVideoURI(Uri.parse(attachment))
        } else {
            fullimageview.visibility = View.VISIBLE
            Glide.with(this).load(attachment)
                .into(fullimageview)
        }
    }
}