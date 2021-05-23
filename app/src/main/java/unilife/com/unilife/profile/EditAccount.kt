package unilife.com.unilife.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_my_profile.*
import unilife.com.unilife.R

class EditAccount : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        setOnClickListner()
    }


    fun setOnClickListner(){

        ivback_arrow.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivback_arrow-> {
                onBackPressed()
            }


        }
    }
}
