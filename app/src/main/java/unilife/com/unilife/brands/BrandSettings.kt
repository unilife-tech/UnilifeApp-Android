package unilife.com.unilife.brands

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_brand_settings.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.bottom_bar.*
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R
import java.lang.Exception

class BrandSettings : AppCompatActivity(),View.OnClickListener {

    var value = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_settings)
        setOnClickListner()

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivBrands.drawable),
            ContextCompat.getColor(this, R.color.colorPrimary)
        )

        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.drawable),
            ContextCompat.getColor(this, R.color.medium_grey)
        )
        setOnClickListner()

        tvTitle.text = "Brand Settings"
        ivNotification.visibility=View.GONE
    }


    fun setOnClickListner(){
        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)
        ivBackArrow.setOnClickListener(this)
    //    tvblockeduser.setOnClickListener(this)
        viewBrands.setOnClickListener(this)
        tvSavedCoupons.setOnClickListener(this)
        RedeemBrand.setOnClickListener(this)
        sharedBrand.setOnClickListener(this)
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

            R.id.rlBlogs->{
                val intent= Intent(this, Blog::class.java)
                startActivity(intent)
            }
            R.id.rlBrands->{
                val intent= Intent(this, BrandsHome::class.java)
                startActivity(intent)
            }


            R.id.ivBackArrow->{
                onBackPressed()
            }

            R.id.viewBrands->{
                value = "View Brands"
                val intent= Intent(this, AllBrandSettings::class.java)
                intent.putExtra("value",value)
                startActivity(intent)

            }

            R.id.tvSavedCoupons->{
                value = "View Saved"
                val intent= Intent(this, AllBrandSettings::class.java)
                intent.putExtra("value",value)
                startActivity(intent)

            }
            R.id.RedeemBrand->{
                value = "View Redeemed"
                val intent= Intent(this, AllBrandSettings::class.java)
                intent.putExtra("value",value)
                startActivity(intent)

            }
            R.id.sharedBrand->{
                value = "View Shared"
                val intent= Intent(this, AllBrandSettings::class.java)
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
