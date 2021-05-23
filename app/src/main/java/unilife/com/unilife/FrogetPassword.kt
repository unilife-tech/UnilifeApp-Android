package unilife.com.unilife

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_froget_password.*
import org.json.JSONObject
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls

class FrogetPassword : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var msg: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_froget_password)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setOnClickListner()
    }

    fun setOnClickListner() {
        ivBackArrow.setOnClickListener(this)
        tvSend.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ivBackArrow -> {
                intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

            R.id.tvSend -> {
                if (checkValidations()) {
                    forgetPassword()
                }
            }
        }
    }


    private fun checkValidations(): Boolean {
        if (etemail?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this, "Unilife", "Please enter email Id.")
            return false
        }
        return true
    }

    private fun forgetPassword() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this,
                    this,
                    WebUrls.FORGET_PASSWORD + etemail.text.toString().trim { it <= ' ' },
                    WebUrls.FORGET_PASSWORD_CODE,
                    1,
                    postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    override fun onResponse(requestCode: Int, response: String) {

        try {

            val `object` = JSONObject(response)
            val res = `object`.getString("response")

            Log.e("Register", response)


            when (requestCode) {

                WebUrls.FORGET_PASSWORD_CODE -> {
                    if (res.equals("true", true)) {

                        msg = `object`.getString("message")
                        Common.alertDialog(this, msg)

                    } else {

                        msg = `object`.getString("message")
                        Common.alertDialog(this, msg)


                    }
                }

            }

        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }
}
