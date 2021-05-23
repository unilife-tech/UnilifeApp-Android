package unilife.com.unilife.brands

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import kotlinx.android.synthetic.main.bottom_bar.*
import kotlinx.android.synthetic.main.tranding_offers.*
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R

class NewlyAddedOffers : AppCompatActivity(),View.OnClickListener {

    //    @BindView(R.id.rycCoupans)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newly_added_offers)

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivBrands.getDrawable()),
            ContextCompat.getColor(this, R.color.colorPrimary)
        );
        tvBrands.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));

        DrawableCompat.setTint(
            DrawableCompat.wrap(ivEvent.getDrawable()),
            ContextCompat.getColor(this, R.color.medium_grey)
        );

        setAdapter()
        setOnClickListner()
    }


    fun setOnClickListner(){
        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBlogs.setOnClickListener(this)
        ivbackArrow.setOnClickListener(this)
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

            R.id.ivbackArrow->{
               onBackPressed()
            }


        }
    }

    override fun onBackPressed() {
        val intent= Intent(this, BrandsHome::class.java)
        startActivity(intent)
    }

    private fun setAdapter() {


//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        rycOffers?.layoutManager = staggeredGridLayoutManager
//        val trandinBrandsAdapter = TrendinBrandsAdapter(this)
//        rycOffers?.adapter = trandinBrandsAdapter

    }
}
