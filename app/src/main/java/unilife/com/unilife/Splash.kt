package unilife.com.unilife

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import unilife.com.unilife.home.Home
import unilife.com.unilife.login.FirstLoginActivity
import unilife.com.unilife.retrofit.WebUrls


class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(unilife.com.unilife.R.layout.activity_splash)

        Handler().postDelayed({
            if (PreferenceFile.getInstance().getPreferenceData(
                    this@Splash,
                    WebUrls.KEY_USERID
                ) != null
            ) {
                if (PreferenceFile.getInstance()
                        .getPreferenceData(this, WebUrls.OTP_VERIFY) != "yes"
                ) {

                    val intent = Intent(this@Splash, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

                } else {
                    val intent = Intent(this@Splash, Home::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            } else {

                val intent = Intent(this@Splash, FirstLoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()

            }
        }, 2000)
    }

}