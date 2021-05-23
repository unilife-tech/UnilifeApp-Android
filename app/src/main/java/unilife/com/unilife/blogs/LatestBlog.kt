package unilife.com.unilife.blogs

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import kotlinx.android.synthetic.main.bottom_bar.*
import unilife.com.unilife.brands.BrandsHome
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.home.Home
import unilife.com.unilife.R

class LatestBlog : AppCompatActivity(),View.OnClickListener {

//    @BindView(R.id.rycBlog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_blog)
        setAdapter()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setOnClickListner()
    }

    fun setOnClickListner(){
        rlEvent.setOnClickListener(this)
        rlChat.setOnClickListener(this)
        rlBrands.setOnClickListener(this)
       // ivbackArrow.setOnClickListener(this)

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
            }

            R.id.ivbackArrow->{
                onBackPressed()
            }
        }
    }





private fun setAdapter() {
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        rycBlog?.layoutManager = staggeredGridLayoutManager
//        val trandinBrandsAdapter = TrandinBrandsAdapter(this)
//        rycBlog?.adapter = trandinBrandsAdapter

    }

}
