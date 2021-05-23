package unilife.com.unilife

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.google.android.exoplayer2.util.Log
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls


class Feedback : AppCompatActivity(), RetrofitResponse, View.OnClickListener {



    var count = ""
    var feedback = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)


        setOnClick()
        getratingsvalue()


    }

    override fun onResponse(requestCode: Int, response: String) {
        try {
            when (requestCode) {
                WebUrls.FEEDBACK_CODE -> {
                    val obj = JSONObject(response)
                    Log.e("asnklcsdnlsdf",""+obj.toString())
                    if (obj.getBoolean("response")) {
                        Log.e("sfjhs",""+obj.toString())
                        customDialog(this, "Unilife","Thanks for your feedback")
                        etfeedback.text.clear()
                        feedratings.rating = 1.0F
                    }
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun getratingsvalue() {
        feedratings.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            count = feedratings.rating.toString()

            Log.e("saff", "  " + count)
        }
    }

    private fun setOnClick() {
        yyy.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        tvTitle.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        Log.e("saff", "mjkljkj  " + count)

        when(v) {

            yyy -> {
                Log.e("saff", "ivBackarrow3  " + count)

                onBackPressed()
            }

            btnSubmit->{
                checkValidation()
                callFeedbackService()
            }
        }
    }

    private fun checkValidation() : Boolean {
        if (count.isEmpty() ) {
            Common.customDialog(this,"Unilife","Please select stars.")
            return false
        }
        else{
            return true
        }
    }

    private fun callFeedbackService() {
        try {
            if (Alerts.isNetworkAvailable(this)) {
                var postparam = JSONObject()
                feedback = etfeedback.text.toString()

                postparam.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
                postparam.put("rating", count)
                postparam.put("feedback", feedback)

                RetrofitService(
                    this, this, WebUrls.FEEDBACK, WebUrls.FEEDBACK_CODE,
                    3, postparam
                ).callService(true)
            }
            else{
                Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, Home::class.java))
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
            startActivity(Intent(this, Home::class.java))

        }

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}
