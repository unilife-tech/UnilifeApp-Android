package unilife.com.unilife.chat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_view_chat_multimedia.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import unilife.com.unilife.R

class ViewChatMultimedia : AppCompatActivity() , View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_chat_multimedia)

        tvTitle.text = "View Saved Multimedia"
        ivNotification.visibility = View.GONE
        setonClickListener()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llAudio->{
                startActivity(Intent(this,ListAllFiles::class.java)
                    .putExtra("value","audio")
                )
            }
            R.id.llVideo->{
                startActivity(Intent(this,ListAllFiles::class.java)
                    .putExtra("value","video")
                )
            }
            R.id.llImage->{
                startActivity(Intent(this,ListAllFiles::class.java)
                    .putExtra("value","image")
                )
            }
            R.id.llDocument->{
                startActivity(Intent(this,ListAllFiles::class.java)
                    .putExtra("value","document")
                )
            }
            R.id.ivBackArrow->{
              onBackPressed()
            }


        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,ChatSetting::class.java))
        finish()
    }

    private fun setonClickListener() {
        llAudio.setOnClickListener(this)
        llVideo.setOnClickListener(this)
        llImage.setOnClickListener(this)
        llDocument.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)
    }
}
