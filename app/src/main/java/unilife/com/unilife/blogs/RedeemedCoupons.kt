package unilife.com.unilife.blogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.bottom_bar.*
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R

class RedeemedCoupons : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeemed_coupons)


        ivBlogs.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                R.drawable.ic_blog_blue))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )
        tvBlog.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setOnClickListner()
    }

    fun setOnClickListner(){
        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rlEvent->{
                val intent= Intent(this, Home::class.java)
                startActivity(intent)
            }
            R.id.rlChat->{
                val intent= Intent(this, Chat::class.java)
                startActivity(intent)
            }

            R.id.rlBrands->{
                val intent= Intent(this, BrandsHome::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ivBackArrow->{
               super.onBackPressed()
            }





        }
    }

}
