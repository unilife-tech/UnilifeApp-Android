package unilife.com.unilife

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.activity_help.etUni
import kotlinx.android.synthetic.main.activity_help.tvCreate
import kotlinx.android.synthetic.main.activity_help.userSpinner
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.back_icon_toolbar.tvTitle
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.home.Home
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.model.UniModel
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.util.*

class Help : AppCompatActivity(), RetrofitResponse {

    val universityList = ArrayList<String>()
    val universityListID = ArrayList<String>()
    var queryType = ""
    var query_type = arrayOf("Select", "query", "report")
    var description = ""
    var subject = ""
    var post_id = ""
    var UniversityName = ""
    var mailDomain = ""

    var universityid: String = ""
    var universityname: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

//        sendUnivesity()
        setSpinner1()
//        spinnerItem()


        tvCreate.setOnClickListener {
            if(checkValidations()) {
                callHelpService()
            }
        }


        etUni.setOnClickListener {
            intent = Intent(this, UniversitiesListing::class.java)
            intent.putExtra("universityid",universityid)
            startActivityForResult(intent, 101)
        }

        ivNotification.visibility = View.GONE

        tvTitle.text = "Help"

        ivBackArrow.setOnClickListener {
            onBackPressed()
        }
    }


    override fun onResponse(requestCode: Int, response: String) {
        try {
            val `object` = JSONObject(response)
            val res = `object`.getString("response")
            when (requestCode) {
                WebUrls.SEND_UNIVERSITY_CODE -> {
                    if (res.equals("true", true)) {

                        val data = `object`.getJSONArray("data")
                        for (i: Int in 0 until data.length()) {

                            val obj = data.getJSONObject(i)
                            UniversityName = obj.getString("name")

                            val domain = obj.getJSONObject("university_domain")

                            val domain_name = domain.getString("domain")
//                            id = obj.getString("id")

                            universityList.add(UniversityName)
                            universityListID.add(domain_name)
//                            universityList.addAll(universityList)

                        }
//                        universityList.add(0," select university/school")


                        universitySpinner.adapter =
                            ArrayAdapter<String>(this, R.layout.spinner_item, universityList)

                    }

                }
                WebUrls.HELP_CODE -> {
                    val obj = JSONObject(response)
                    if (obj.getBoolean("response")) {
                        Log.e("response", "" + obj.toString())
                        // Toast.makeText(this,"Thanks for contacting us we will contact you shortly",Toast.LENGTH_SHORT).show()
                        customDialog(
                            this,
                            "Unilife",
                            "Thanks for contacting us we will contact you shortly"
                        )
                    }
                }
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when(requestCode){

            101->{

                if(resultCode== Activity.RESULT_OK){

                    val uniModel = data!!.getSerializableExtra("result") as UniModel
                    etEmail.text = Editable.Factory.getInstance().newEditable(uniModel.domain_name)
                    etUni.text = Editable.Factory.getInstance().newEditable(uniModel.name)
                    mailDomain = uniModel.domain_name
                    universityid = uniModel.id
                    universityname = uniModel.name

                }

            }

        }

    }


    private fun setSpinner1() {
        var useradapter = ArrayAdapter(this, R.layout.spinner_item, query_type)
        userSpinner!!.adapter = useradapter
        userSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val spinner1index = parent?.selectedItemPosition
                if (spinner1index != null) {
                    if (spinner1index > 0) {
                        queryType = query_type[position]
                    }
                }
            }


        }
    }

    private fun sendUnivesity() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this, this, WebUrls.SEND_UNIVERSITY,
                    WebUrls.SEND_UNIVERSITY_CODE, 1, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun callHelpService() {

        if (Alerts.isNetworkAvailable(this)) {
            val postparam = JSONObject()
            description = etDesc.text.toString()
            subject = etsubject.text.toString()


            postparam.put("type", queryType)
            postparam.put("description", description)
            postparam.put(
                "user_id",
                PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
            )
            postparam.put("subject", subject)
            //   postparam.put("post_id",post_id)

            RetrofitService(
                this, this, WebUrls.HELP, WebUrls.HELP_CODE,
                3, postparam
            ).callService(true)
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun spinnerItem() {

        universityList.add(0, " Select university/school")
        universityListID.add(0, "0")
        universitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val index = adapterView.selectedItemPosition
                if (index != null) {
                    if (index > 0) {
                        etEmail.text =
                            Editable.Factory.getInstance().newEditable(universityListID[index])

                        mailDomain = universityListID[index]
                    }
                }

                universityid = universitySpinner.getItemIdAtPosition(i).toString()
                universityname = universitySpinner.selectedItem.toString()
                Log.e("universityvalue", universityid)
                //       Toast.makeText(applicationContext, country, Toast.LENGTH_LONG).show()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // DO Nothing here
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun checkValidations(): Boolean {
        if (userSpinner!!.selectedItem == "Select") {
            Common.customDialog(this, "Unilife", "Please select concern type.")
            return false
        } else if (etDesc?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this, "Unilife", "Please enter text or description")
            return false
        } else if (etUni.text.trim().length == 0) {
            Common.customDialog(this, "Unilife", "Please select University or school.")

            return false
        } else if (etEmail?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this, "Unilife", "Please enter university or school email.")

            return false
        } else if (etEmail?.text.toString().trim().substring(
                0,
                etEmail.text.toString().lastIndexOf("@")
            ).isEmpty()
        ) {
            Common.customDialog(this, "Unilife", "Please enter university or school email.")

            return false
        } else if (!etEmail?.text.toString().trim().contains(mailDomain)) {
            Common.customDialog(
                this,
                "Unilife",
                "Please join university or school email domain."
            )
            return false
        } else if (etEmail?.text.toString().trim().startsWith("@")) {
            Common.customDialog(
                this,
                "Unilife",
                "Please enter valid email address"
            )
            return false
        } else if (etDesc?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this, "Unilife", "Please enter text or description")
            return false
        } else if (etsubject?.text.toString().trim { it <= ' ' }.isEmpty()) {
            Common.customDialog(this, "Unilife", "Please enter Subject")
            return false
        }



        return true
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

        tvOk.setOnClickListener(View.OnClickListener {
            alertDialog.dismiss()
            startActivity(Intent(this, Home::class.java))
        })

        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }
}
