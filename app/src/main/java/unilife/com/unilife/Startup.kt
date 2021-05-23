package unilife.com.unilife

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_startup.*

class Startup : AppCompatActivity() ,View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        setOnClickListner()
    }

    fun setOnClickListner() {
        tvSigin.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSignUp -> {
                intent = Intent(this, SignUp::class.java)
                startActivity(intent)
            }


            R.id.tvSigin -> {
                intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }


//    @OnClick(R.id.tvSignUp, R.id.tvSigin)
//    fun click(view: View) {
//        when (view.id) {
//
//            R.id.tvSigin ->
//
//                startActivity(Intent(this@Startup, Login::class.java))
//
//
//            R.id.tvSignUp ->
//
//                startActivity(Intent(this@Startup, SignUp::class.java))
//        }
//    }

    }
}


