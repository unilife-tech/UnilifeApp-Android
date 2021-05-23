package unilife.com.unilife

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.mukesh.OtpView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_otp_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.login.SchoolNameActivity
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.AppController
import unilife.com.unilife.utils.Common

class Login : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var msg: String = ""
    var user_id: String = ""
    var getuser_type = ""
    var user_label = ""
    var alertDialogg: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setOnclicklistner()
    }

    fun setOnclicklistner() {
        txtSignIn.setOnClickListener(this)
        txtSignUp.setOnClickListener(this)
        txtForgotPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.txtSignIn -> {

                // startActivity(Intent(this@Login, Home::class.java))

                if (checkValidations()) {
                    callLoginService()
                }
            }

            R.id.txtSignUp -> {
                startActivity(Intent(this@Login, SchoolNameActivity::class.java))
            }

//            R.id.ivBackarrow->{
//                intent= Intent(this,Startup::class.java)
//                startActivity(intent)
//            }
            R.id.txtForgotPassword -> {
                intent = Intent(this, FrogetPassword::class.java)
                startActivity(intent)

            }
        }
    }

    private fun checkValidations(): Boolean {
        if (edtUsername?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@Login, "Unilife", "Please enter username or id.")
            return false

        } else if (edtPassword?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@Login, "Unilife", "Please enter password.")
            return false
        }
        return true
    }

    private fun callLoginService() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()
            try {
                postParam.put("username", edtUsername.text.toString().trim { it <= ' ' })
                postParam.put("password", edtPassword.text.toString().trim { it <= ' ' })

                Log.d("request login", postParam.toString())
                Log.d("url", WebUrls.BASE_URL + "" + WebUrls.LOGIN)
                RetrofitService(
                    this, this, WebUrls.LOGIN,
                    WebUrls.LOGIN_CODE, 3, postParam
                ).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun addDevice() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()
            try {
                val sharedPreferences = AppController.getIdPrefs()
                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this@Login, WebUrls.KEY_USERID)
                )
                postParam.put("device_token", sharedPreferences.getString("token", null))
                postParam.put(
                    "device_id",
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                )
                postParam.put("type", "android")

                Log.e("PARAMS", postParam.toString())

                RetrofitService(
                    this, this, WebUrls.USER_DEVICE, WebUrls.USER_DEVICE_CODE,
                    3, postParam
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

            Log.e("onResponse", response)

            when (requestCode) {

                WebUrls.USER_DEVICE_CODE -> {

                    if (res.equals("true", true)) {

                        val intent = Intent(this@Login, Home::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finishAffinity()

                    } else {
                        Common.customDialog(this, "Unilife", `object`.getString("message"))
                    }

                }

                WebUrls.LOGIN_CODE -> {

                    if (res.equals("true", true)) {

                        val data = `object`.getJSONObject("data")

                        Log.e("DATAAAAAA", data.toString())

//                        if (`object`.has("university")){
//                        val university = `object`.getJSONObject("university")}

                        getuser_type = data.getString("user_type")

                        if (getuser_type == "0") {
                            user_label = "Student"
                        } else if (getuser_type == "1") {
                            user_label = "Faculty Staff"
                        } else if (getuser_type == "2") {
                            user_label = "Teacher"
                        }


                        PreferenceFile.getInstance().saveData(
                            this@Login,
                            WebUrls.KEY_USERTYPE,
                            user_label
                        )


                        PreferenceFile.getInstance().saveData(
                            this@Login, WebUrls.UNIVERSITY_SCHOOL_ID,
                            data.getString("university_school_id")
                        )

                        PreferenceFile.getInstance().saveData(
                            this@Login, WebUrls.KEY_PROFILE_STATUS,
                            data.getString("profile_status")
                        )

                        PreferenceFile.getInstance().saveData(
                            this@Login, WebUrls.KEY_STATUS,
                            data.getString("status")
                        )


                        if (data.has("username")) {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USERNAME,
                                data.getString("username")
                            )
                        } else {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USERNAME,
                                ""
                            )
                        }


                        if (data.has("first_name")) {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USER_FIRSTNAME,
                                data.getString("first_name")
                            )
                        } else {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USER_FIRSTNAME,
                                ""
                            )
                        }


                        if (data.has("last_name")) {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USER_LASTNAME,
                                data.getString("last_name")
                            )
                        } else {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USER_LASTNAME,
                                ""
                            )
                        }

                        if (data.has("university_school_email")) {
                            if (data.getString("university_school_email") != null) {
                                PreferenceFile.getInstance().saveData(
                                    this@Login, WebUrls.KEY_EMAIL,
                                    data.getString("university_school_email")
                                )
                            } else {
                                PreferenceFile.getInstance().saveData(
                                    this@Login, WebUrls.KEY_EMAIL,
                                    ""
                                )
                            }
                        } else {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_EMAIL,
                                ""
                            )
                        }

                        if (data.has("profile_image")) {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_PROFILE_IMAGE,
                                data.getString("profile_image")
                            )
                        } else {
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_PROFILE_IMAGE,
                                ""
                            )
                        }

//                        if (university.has("name")) {
//                            PreferenceFile.getInstance().saveData(
//                                this@Login, WebUrls.UNIVERSITY_NAME,
//                                university.getString("name")
//                            )
//                        } else {
//                            PreferenceFile.getInstance().saveData(
//                                this@Login, WebUrls.KEY_PROFILE_IMAGE,
//                                ""
//                            )
//                        }

                        user_id = data.getString("id")


                        if (!data.isNull("otp_verify") && data.getString("otp_verify")
                                .toLowerCase() == "no"
                        ) {

                            showAlert(this, "")

                        } else {

                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USERID, user_id
                            )
                            if (!data.isNull("otp_verify") && data.getString("otp_verify")
                                    .toLowerCase() == "yes"
                            ) {
                                PreferenceFile.getInstance().saveData(
                                    this,
                                    WebUrls.OTP_VERIFY,
                                    data.getString("otp_verify")
                                )
                            }

                            addDevice()
                        }
                    } else {

                        msg = `object`.getString("message")
                        Common.customDialog(this, "Unilife", msg)

                    }
                }

                WebUrls.RESEND_OTP_CODE -> {
                    val obj = JSONObject(response)
                    val msg = obj.getString("message")
                    val res = obj.getBoolean("response")
                    Log.e("objResponse", "" + obj.toString())
                    if (res) {
                        Log.e("objResponse", "obj...!!")
                    }
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }

                WebUrls.SUBMIT_OTP_CODE -> {

                    val obj = JSONObject(response)

                    val msg = obj.getString("message")
                    val res = obj.getBoolean("response")

                    Log.e("objResponse", "" + obj.toString())

                    if (res) {

                        Log.e("objResponse", "SUBMIT_OTP_CODE")

                        alertDialogg!!.dismiss()

                        if (!obj.isNull("data")) {

                            val data = obj.getJSONObject("data")

                            val otpVerifyStatus = data.getString("otp_verify")

                            PreferenceFile.getInstance()
                                .saveData(this, WebUrls.OTP_VERIFY, otpVerifyStatus)
                            PreferenceFile.getInstance().saveData(
                                this@Login, WebUrls.KEY_USERID, user_id

                            )
                            addDevice()

                            Log.e(
                                "verify",
                                "" + PreferenceFile.getInstance()
                                    .getPreferenceData(this, WebUrls.OTP_VERIFY)
                            )
                        }
                    }

                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    private fun showAlert(context: Context, msg11: String) {

        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.layout_otp_dialog, null)

        val tvOk = dialogView.otpSubmit
        val otpView = dialogView.findViewById<OtpView>(R.id.otpView)
        val tvResend = dialogView.findViewById<TextView>(R.id.tvResend)

        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialogg = alertDialog
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        alertDialog.window!!.setLayout(width, height)

        otpView.setOnClickListener {

            InputMethodManager.SHOW_FORCED
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
            showKeyboard(context, dialogView)
        }

        tvResend.setOnClickListener {
            callResendOtp()
        }

        tvOk.setOnClickListener {
            val otp = otpView.text
            if (otp!!.isEmpty()) {
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show()

            } else {
                callSubmitOtp(otp)
            }
        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    fun showKeyboard(context: Context, view: View?) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputManager.showSoftInputFromInputMethod(
                view.windowToken,
                InputMethodManager.RESULT_SHOWN
            )
        }
    }

    private fun callSubmitOtp(otp: Editable?) {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                val postParam = JSONObject()
                postParam.put(
                    "user_id", user_id
                )
                postParam.put("otp", otp)

                RetrofitService(
                    this,
                    this,
                    WebUrls.SUBMIT_OTP,
                    WebUrls.SUBMIT_OTP_CODE,
                    3,
                    postParam
                )
                    .callService(true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun callResendOtp() {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                RetrofitService(
                    this, this, WebUrls.RESEND_OTP +
                            PreferenceFile.getInstance().getPreferenceData(
                                this,
                                WebUrls.KEY_USERID
                            ),
                    WebUrls.RESEND_OTP_CODE, 1
                ).callService(true)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}
