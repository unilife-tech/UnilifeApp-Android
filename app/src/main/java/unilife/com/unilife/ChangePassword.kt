package unilife.com.unilife

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception

class ChangePassword : AppCompatActivity(), RetrofitResponse {

    var oldpw = ""
    var newpw = ""
    var cnfrmpw = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        tvTitle.text = "Change Password"
        ivNotification.visibility = View.GONE
        ivBackArrow.setOnClickListener {
            super.onBackPressed()
        }
        btnChange.setOnClickListener {
            oldpw = etOldpassword.text.toString()
            newpw = etNewPassword.text.toString()
            cnfrmpw = etCnfrmpassword.text.toString()

            if( checkvalidations()) {

                callChangePassword()
            }
        }
    }

    private fun callChangePassword() {




        try {

            var postParam = JSONObject()

            postParam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
            postParam.put("current_password", oldpw)
            postParam.put("change_password", newpw)

            RetrofitService(this, this, WebUrls.CHANGE_PASSWORD, WebUrls.CHANGE_PASSWORD_CODE, 3, postParam)
                .callService(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkvalidations(): Boolean {
        if (oldpw.isEmpty()) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "Please enter your Old Password")
            return false
        } else if (newpw.isEmpty()) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "Please enter your New Password")
            return false

        } else if (cnfrmpw.isEmpty()) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "Please enter your Confirm Password")
            return false

        } else if (oldpw == newpw) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "Old and New Password are same")
            return false
        } else if (newpw.length < 6) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "Please enter atleast 6 digit password.")
            return false
        } else if (newpw != cnfrmpw) {
            unilife.com.unilife.utils.Common.customDialog(this, "Unilife", "New and Confirm Password doesnt match")
            return false
        }

        return true

    }

    override fun onResponse(requestCode: Int, response: String) {
        when (requestCode) {
            WebUrls.CHANGE_PASSWORD_CODE -> {
                var message = ""
                    val obj = JSONObject(response)
                    if(obj.getBoolean("response")){
                         message = obj.getString("message")
                            customDialog(this,"Unilife",message)
                    }
                else
                    {
                         message = obj.getString("message")
                            unilife.com.unilife.utils.Common.customDialog(this,"Unilife",message)
                    }

            }

        }


    }

    fun customDialog(context: Context, title: String, msg: String) {

        val dialogBuilder = AlertDialog.Builder(context)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        val tvTitle = dialogView.tvTitle
        val tvOk = dialogView.tvok
        val tvMsg = dialogView.tvMsg

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        tvTitle.text = title
        tvMsg.text = msg

        tvOk.setOnClickListener {

            alertDialog.dismiss()
            startActivity(Intent(this,Home::class.java))
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}
