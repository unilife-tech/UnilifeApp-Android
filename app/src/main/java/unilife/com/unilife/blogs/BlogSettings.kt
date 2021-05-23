package unilife.com.unilife.blogs

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.blog_setting.*
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import java.lang.Exception

class BlogSettings : AppCompatActivity(),View.OnClickListener {

    var value = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blog_setting)



        tvTitle.text = "Blog Settings"
        ivNotification.visibility = View.GONE



      /*  ivBlogs.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                R.drawable.ic_blog_blue))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )
        tvBlog.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
      */
        setOnClickListner()
    }


    fun setOnClickListner(){

        ivBackArrow.setOnClickListener(this)
        llViewSaved.setOnClickListener(this)
        llSavedRead.setOnClickListener(this)
        llViewLike.setOnClickListener(this)
        llViewShared.setOnClickListener(this)

    }





    override fun onClick(v: View?) {
        when(v?.id){


            R.id.ivBackArrow->{
                onBackPressed()
            }

            R.id.llViewSaved->{
                val intent= Intent(this, AllBlogSettings::class.java)
                value = "View Saved"
                intent.putExtra("value",value)
                startActivity(intent)
            }
            R.id.llSavedRead->{
                val intent= Intent(this, AllBlogSettings::class.java)
                value = "Saved Read"
                intent.putExtra("value",value)
                startActivity(intent)
            }
            R.id.llViewLike->{
                val intent= Intent(this, AllBlogSettings::class.java)
                value = "View Like"
                intent.putExtra("value",value)
                startActivity(intent)
            }
            R.id.llViewShared->{
                val intent= Intent(this, AllBlogSettings::class.java)
                value = "View Shared"
                intent.putExtra("value",value)
                startActivity(intent)
            }





        }
    }

    override fun onBackPressed() {
        try {
            val intent = Intent(this, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finishAffinity()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
