package unilife.com.unilife

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import kotlinx.android.synthetic.main.activity_sign_up.etLogin
import kotlinx.android.synthetic.main.activity_sign_up.etemail
import kotlinx.android.synthetic.main.activity_sign_up.tvCreate
import unilife.com.unilife.utils.Common
import kotlin.collections.ArrayList
import android.widget.TextView
import com.mukesh.OtpView
import kotlinx.android.synthetic.main.layout_otp_dialog.view.*
import unilife.com.unilife.profile.CompleteProfile
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.AppController
import unilife.com.unilife.model.UniModel


class SignUp : AppCompatActivity(), View.OnClickListener, RetrofitResponse {


    val universityList = ArrayList<String>()
    val universityIDList = ArrayList<String>()
    val universityListID = ArrayList<String>()
    val UserList = ArrayList<String>()

    var universityid: String = ""
    var universityname: String = ""
    var getuniversityname: String = ""

    var userType: String = ""
    var msg: String = ""
    var id: String = ""
    var getuser_type = ""
    var user_label = ""
    var UniversityName: String = ""
    var UniversityId: String = ""
    /*    lateinit var spinner1index*/
    var mySpinner: Spinner? = null
    var alertDialogg : AlertDialog? = null

    companion object
    {
        val uniList = ArrayList<UniModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setOnClickListner()

        userSpinner()
        spinnerItem()
//        sendUniversity()

        ivBackArrow1.setOnClickListener {

            super.onBackPressed()
        }

    }

    fun setOnClickListner() {
        tvSigin.setOnClickListener(this)
        ivBackArrow1.setOnClickListener(this)
        tvCreate.setOnClickListener(this)
        etUni.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.etUni -> {
                intent = Intent(this, UniversitiesListing::class.java)
                intent.putExtra("universityid",universityid)
                startActivityForResult(intent, 101)
            }

            R.id.tvSigin -> {
                intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }

            R.id.ivBackArrow1 -> {
                /* intent = Intent(this, Startup::class.java)
                 startActivity(intent)*/
                super.onBackPressed()
                Log.e("DSAdfsdfs", "ds")
                finish()
            }

            R.id.tvCreate -> {

                /*  intent = Intent(this, CompleteProfile::class.java)
                  startActivity(intent)
                  finish()*/

                if (checkValidations()) {
                    callService()
                }


            }

        }

    }

    private fun userSpinner() {

        mySpinner = findViewById(R.id.userSpinner)
        var myStrings = arrayOf("Select User Type", "Student", "Staff", "Teacher")
        var adapter = ArrayAdapter(this, R.layout.spinner_item, myStrings)
        mySpinner!!.adapter = adapter
        mySpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //(parent?.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.colorPrimary))
                val spinner1index = parent?.selectedItemPosition
                if (spinner1index != null) {
                    if (spinner1index > 0) {
                        (mySpinner!!.selectedView as TextView).setTextColor(resources.getColor(R.color.colorPrimary))

                        if (spinner1index == 1) {
                            userType = "0"
                        } else if (spinner1index == 2) {
                            userType = "1"
                        } else if (spinner1index == 3) {
                            userType = "2"
                        }
                        /* //  userType=myStrings[position]
                           Log.e("mklsf", jasdbkdb.toString())*/
                    } else {
                        userType = ""
                    }
                    /*   userType=parent.selectedItem.toString()*/

                }
            }
        }

    }


    var mailDomain = ""
    private fun spinnerItem() {

        universityList.add(0, " Select University/School")
        universityListID.add(0, "0")
        universityIDList.add(0, "0")
        unversitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val index = adapterView.selectedItemPosition
                if (index != null) {
                    if (index > 0) {
                        (unversitySpinner.selectedView as TextView).setTextColor(
                            resources.getColor(
                                R.color.colorPrimary
                            )
                        )


                        etemail.text = Editable.Factory.getInstance().newEditable(universityListID[index])

                        mailDomain = universityListID[index]
                    }
                }

                //   universityid = unversitySpinner.getItemIdAtPosition(i).toString()
                universityid = universityIDList[i]
                universityname = unversitySpinner.selectedItem.toString()
                Log.e("universityvalue", universityid)
                //       Toast.makeText(applicationContext, country, Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // DO Nothing here
            }
        }
    }

    private fun callService() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {


                postParam.put("user_type", userType)
                postParam.put("username", etLogin.text.toString().trim { it <= ' ' })
                postParam.put("university_school_id", universityid)
                postParam.put("university_school_email", etemail.text.toString().trim { it <= ' ' })
                postParam.put("password", etPassword.text.toString().trim { it <= ' ' })

                Log.e("postParam", postParam.toString())


                /*postParam.put("country_id", spinner.getSelectedItem().tetUnioString());*/
                RetrofitService(
                    this@SignUp, this@SignUp, WebUrls.SIGN_UP,
                    WebUrls.SIGN_UP_CODE, 3, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun sendUniversity() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this@SignUp, this@SignUp, WebUrls.SEND_UNIVERSITY,
                    WebUrls.SEND_UNIVERSITY_CODE, 1, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun checkValidations(): Boolean {

        if (userType == "") {
            Common.customDialog(this@SignUp, "Unilife", "Please select user type.")
            return false
        } else if (etLogin?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@SignUp, "Unilife", "Please enter username or ID.")
            return false
        }
//        else if (universityid == "0") {
        else if (etUni.text.trim().length == 0) {
            Common.customDialog(this@SignUp, "Unilife", "Please select university or school.")

            return false
        } else if (etemail?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@SignUp, "Unilife", "Please enter valid email address.")

            return false
        } else if (!etemail?.text.toString().trim().contains(mailDomain)) {
            Common.customDialog(
                this@SignUp,
                "Unilife",
                "Please join university or school email domain."
            )
            return false
        } else if (etemail?.text.toString().trim().startsWith("@")) {
            Common.customDialog(
                this@SignUp,
                "Unilife",
                "Please enter valid email address"
            )
            return false
        } else if (etPassword?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@SignUp, "Unilife", "Please enter password.")
            return false

        } else if (etCnfrPassword?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this@SignUp, "Unilife", "Please confirm password.")
            return false

        } else if (etPassword.text.toString().trim { it <= ' ' }.length < 6) {
            Common.customDialog(this@SignUp, "Unilife", "Please enter atleast 6 digit password.")
            return false

        } else if (etCnfrPassword.text.toString() != etPassword.text.toString()) {
            Common.customDialog(
                this@SignUp,
                "Unilife",
                "Password and Confirm Password should be same"
            )
            return false

        }
        return true
    }

    private fun addDevice() {

        if (Alerts.isNetworkAvailable(this)) {

            val postParam = JSONObject()

            try {

                val sharedPreferences = AppController.getIdPrefs()

                postParam.put("user_id",PreferenceFile.getInstance().getPreferenceData(this@SignUp, WebUrls.KEY_USERID))
                postParam.put("device_token", sharedPreferences.getString("token", null))
                postParam.put("device_id",Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
                postParam.put("type", "android")

                Log.e("PARAMS", postParam.toString())

                RetrofitService(this, this, WebUrls.USER_DEVICE, WebUrls.USER_DEVICE_CODE,3, postParam).callService(true)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when(requestCode){

            101->{

                if(resultCode== Activity.RESULT_OK){

                    val uniModel = data!!.getSerializableExtra("result") as UniModel
                    etemail.text = Editable.Factory.getInstance().newEditable(uniModel.domain_name)
                    etUni.text = Editable.Factory.getInstance().newEditable(uniModel.name)
                    mailDomain = uniModel.domain_name
                    universityid = uniModel.id
                    universityname = uniModel.name

                }

            }

        }

    }

    override fun onResponse(requestCode: Int, response: String) {

        try {

            val `object` = JSONObject(response)

            val res = `object`.getString("response")

            Log.e("Register", response)


            when (requestCode) {

                WebUrls.SIGN_UP_CODE -> {


                    if (res.equals("true", true)) {

                        Log.e("fkas", `object`.toString())

                        val msg = `object`.getString("message")

                        val data = `object`.getJSONObject("data")

                        id = data.getString("id")


                        getuser_type = data.getString("user_type")

                        when (getuser_type) {
                            "0" -> {
                                user_label = "Student"
                            }
                            "1" -> {
                                user_label = "Faculty Staff"
                            }
                            "2" -> {
                                user_label = "Teacher"
                            }


                            //                       showAlert(msg)
                        }

                        var university = `object`.getJSONObject("university")


                        getuniversityname = university.getString("name")
                        PreferenceFile.getInstance().saveData(
                            SignUp@ this, WebUrls.UNIVERSITY_NAME,
                            getuniversityname
                        )

                        PreferenceFile.getInstance().saveData(SignUp@ this, WebUrls.KEY_USERID, id)

                        PreferenceFile.getInstance().saveData(
                            SignUp@ this,
                            WebUrls.KEY_USERNAME,
                            data.getString("username")
                        )

                        PreferenceFile.getInstance().saveData(
                            SignUp@ this,
                            WebUrls.KEY_USERTYPE,
                            user_label
                        )

                        addDevice()

//                       showAlert(msg)

                    } else {
                        msg = `object`.getString("message")

                        Common.customDialog(this, "Unilife", msg)


                    }
                }

                WebUrls.USER_DEVICE_CODE -> {
                    if (res.equals("true", true)) {
                        Log.e("DEVICEADDED", "ovondfvbdbcvbvjbvbv,")

                        showAlert(this, "Signup successfully")


                    } else {

                    }
                }

//                WebUrls.SEND_UNIVERSITY_CODE -> {
//
//                    uniList.clear()
//
//                    if (res.equals("true", true)) {
//
//                        val data = `object`.getJSONArray("data")
//
//                        for (i: Int in 0 until data.length()) {
//
//                            val obj = data.getJSONObject(i)
//
//                            val model = UniModel()
//                            model.name = obj.getString("name")
//                            model.id = obj.getString("id")
//                            model.isSelected = false
//
//                            val domain = obj.getJSONObject("university_domain")
//                            val domain_name = domain.getString("domain")
//                            model.domain_name = domain_name
//
//                            uniList.add(model)
//
//                        }
//
//
//                    }
//                }

//                WebUrls.SEND_UNIVERSITY_CODE -> {
//                    if (res.equals("true", true)) {
//
//                        val data = `object`.getJSONArray("data")
//                        for (i: Int in 0 until data.length()) {
//
//                            val obj = data.getJSONObject(i)
//                            UniversityName = obj.getString("name")
//                            UniversityId = obj.getString("id")
//
//
//                            val domain = obj.getJSONObject("university_domain")
//
//                            val domain_name = domain.getString("domain")
////                            id = obj.getString("id")
//
//                            universityList.add(UniversityName)
//                            universityListID.add(domain_name)
//                            universityIDList.add(UniversityId)
////                            universityList.addAll(universityList)
//
//                        }
////                        universityList.add(0," select university/school")
//
//
//                        unversitySpinner.adapter =
//                            ArrayAdapter<String>(this, R.layout.spinner_item, universityList)
//
//                    }
//                }
                WebUrls.SUBMIT_OTP_CODE -> {
                    val obj = JSONObject(response)
                    val msg = obj.getString("message")
                    val res = obj.getBoolean("response")
                    Log.e("objResponse", "" + obj.toString())
                    if (res) {
                        Log.e("objResponse", "fsdfs")

                        alertDialogg!!.dismiss()
                        if (!obj.isNull("data")) {
                            val data = obj.getJSONObject("data")
                            val otpVerifyStatus = data.getString("otp_verify")
                            PreferenceFile.getInstance()
                                .saveData(this, WebUrls.OTP_VERIFY, otpVerifyStatus)
                            finish()
                            intent = Intent(this, CompleteProfile::class.java)
                            intent.putExtra("key", "signup")
                            intent.putExtra("id", id)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                            Log.e("verify",""+PreferenceFile.getInstance().getPreferenceData(this,WebUrls.OTP_VERIFY))
                        }
                    }

                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

                }

                WebUrls.RESEND_OTP_CODE -> {
                    val obj = JSONObject(response)
                    val msg = obj.getString("message")
                    val res = obj.getBoolean("response")
                    Log.e("objResponse", "" + obj.toString())
                    if (res) {
                        Log.e("objResponse", "fsdfs")

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

            //            intent = Intent(this, EditProfile::class.java)


            val otp = otpView.text
            if(otp!!.isEmpty()){
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show()

            }else {
                callSubmitOtp(otp)
            }

            //  alertDialog.dismiss()
        }



        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
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

    private fun callSubmitOtp(otp: Editable?) {
        if (Alerts.isNetworkAvailable(this)) {
            try {
                val postParam = JSONObject()
                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
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


    data class UniversityData(
        var name: String = "",
        var id: String = ""
    )

    override fun onBackPressed() {
        finish()
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

}

