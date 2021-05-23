package unilife.com.unilife.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_complete_profile.*
import kotlinx.android.synthetic.main.activity_complete_profile.iv_BackArrow
import kotlinx.android.synthetic.main.activity_complete_profile.iv_edit10
import kotlinx.android.synthetic.main.activity_complete_profile.rycQustion
import kotlinx.android.synthetic.main.activity_complete_profile.tvPreviousbutton
import kotlinx.android.synthetic.main.activity_complete_profile.tvSkip
import kotlinx.android.synthetic.main.activity_complete_profile.tvnextbutton
import kotlinx.android.synthetic.main.activity_complete_profile.tvsubmitbutton
import kotlinx.android.synthetic.main.back_icon_toolbar.view.*
import kotlinx.android.synthetic.main.custom_dialog.view.tvTitle
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import unilife.com.unilife.*
import unilife.com.unilife.home.CategoryModel
import unilife.com.unilife.home.Home
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.io.File

class CompleteProfile : AppCompatActivity(), View.OnClickListener, RetrofitResponse {

    var MY_PERMISSIONS_REQUEST_CAMERA = 1

    var getdataedittext: String = ""
    private var qustionData: ArrayList<CompleteProfileModel>? = ArrayList()
    var categoriesList: ArrayList<CategoriesProfileModel> = ArrayList()
    var hobbyList: ArrayList<HobbyModel> = ArrayList()
    var intrestList: ArrayList<IntrestModel> = ArrayList()
    var selectedList: ArrayList<SelectedModel> = ArrayList()
    open var answerList: ArrayList<AnswerModel> = ArrayList()
    var etgetdata: ArrayList<String> = ArrayList()
    var radioValue: String = ""
    var id: String = ""
    var totalQues: Int = 0
    //    private var categories: ArrayList<String>? = ArrayList<String>()
    lateinit var nsv: NestedScrollView

    private var uri: Uri? = null
    private var file: File? = null
    private var file1: File? = null
    private var part: MultipartBody.Part? = null
    private var completeProfileAdapter: CompleteProfileAdapter? = null
    private var count: Int = 0
    var bitmap: Bitmap? = null

    var hobbyIdList: ArrayList<Int> = ArrayList()
    var InterestIdList: ArrayList<Int> = ArrayList()
    var CatogaryIdList: ArrayList<String> = ArrayList()

    var coursesArray: ArrayList<CategoryModel> = ArrayList()
    var coursesArrayName: ArrayList<String> = ArrayList()

    var dataCourses = ""
    private var catFlag: Boolean = false
    private var NextFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_complete_profile_signup)

        checkPermission()
        setOnClickListner()
        profileCategories()
        profileQustion()
        hobby()
        intrest()
        radio()
        tvSkip.visibility = View.VISIBLE

        coursesArray.clear()

    }

    private fun radio() {
        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            radioValue = radio.text as String
        }
    }

    val array1: ArrayList<String> = ArrayList()
    val arrayid: ArrayList<String> = ArrayList()
    var array2: ArrayList<JSONObject> = ArrayList()
    val jsonArray: JSONArray = JSONArray()

    var position = -1

    private fun setQustionAdapter() {

        nsv = findViewById(R.id.nsv)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rycQustion?.layoutManager = layoutManager

        completeProfileAdapter =
            qustionData?.let {
                CompleteProfileAdapter(
                    this, it, categoriesList, hobbyList, intrestList,
                    answerList, quesSize, selectedList, dataCourses
                )
            }
        rycQustion?.adapter = completeProfileAdapter
        //completeProfileAdapter?.notifyDataSetChanged()
        getInterface()
    }

    var finalarrayInt: ArrayList<Int> = ArrayList()
    var finalarrayCat: ArrayList<Int> = ArrayList()
    var finalarrayHob: ArrayList<Int> = ArrayList()
    var ques_id = ""

    private fun getInterface() {


        completeProfileAdapter!!.getClickListener(object : CompleteProfileAdapter.ClickListener {
            override fun courses(qustion: String, i: Int) {
                coursesPopup(i)
            }

            override fun data(dataAnswwer: String, pos: Int, id: String) {

                if (dataAnswwer.isNotEmpty()) {

                    ques_id = id
                    array1.add(dataAnswwer)
                    arrayid.add(id)

                    if (position != id.toInt()) {
                        if (array1.size > 1) {
                            val json = JSONObject()
                            json.put("answer", array1[array1.size - 2])
                            json.put("question_id", arrayid[arrayid.size - 2])

                            array2.add(json)
                            jsonArray.put(json)

//                            Log.e("datafinal", array2.toString())
                        }
//                        Log.e("dataofArray", array1.toString())
                        position = id.toInt()
                        array1.clear()
                    }
                    if (array1.isNotEmpty()) {
//                        Log.e("answerF", array1[array1.size - 1])
//                        Log.e("answerFID", arrayid[arrayid.size - 1])
                    }
                    if (count == 20) {
                        val json = JSONObject()
                        if (array1.isNotEmpty()) {
                            json.put("answer", array1[array1.size - 1])
                            json.put("question_id", arrayid[arrayid.size - 1])

                            array2.add(json)
                            jsonArray.put(json)
                            array1.clear()
                        }
                    }
                }
            }

            override fun passdataInt(arrayListInt: ArrayList<Int>) {

                finalarrayInt.clear()
                for (i in 0 until arrayListInt.size) {

                    finalarrayInt.add(arrayListInt[i])
                }
            }

            override fun passdataHob(arrayListHob: ArrayList<Int>) {

                finalarrayHob.clear()
                for (i in 0 until arrayListHob.size) {

                    finalarrayHob.add(arrayListHob[i])
                }
            }

            override fun passdataCat(arrayListcat: ArrayList<Int>) {
                finalarrayCat.clear()
                for (i in 0 until arrayListcat.size) {

                    finalarrayCat.add(arrayListcat[i])
                }
            }

            override fun passArrayList(
                edittextvaluelist: ArrayList<CompleteProfileEditTextModel>,
                i: Int,
                s: Editable
            ) {
            }

            override fun onItemClick(pos: Int, view: View, string: String, i: Int) {
                if (pos != i) {

                    etgetdata.add(string)
                }
            }
        })

    }


    @SuppressLint("InflateParams", "SetTextI18n")
    private fun coursesPopup(i: Int) {

        try {

            val dialogBuilder = AlertDialog.Builder(this, R.style.Theme_AppCompat_NoActionBar)

            //tvTitle.text = "Courses Covered"
            val dialogView = layoutInflater.inflate(R.layout.courses_list, null,false)
            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()

            val etAns1 = dialogView.findViewById<EditText>(R.id.etAns1)
            val etAns2 = dialogView.findViewById<EditText>(R.id.etAns2)
            val etAns3 = dialogView.findViewById<EditText>(R.id.etAns3)
            val etAns4 = dialogView.findViewById<EditText>(R.id.etAns4)
            val etAns5 = dialogView.findViewById<EditText>(R.id.etAns5)
            val etAns6 = dialogView.findViewById<EditText>(R.id.etAns6)
            val rlTool = dialogView.findViewById<LinearLayout>(R.id.rlTool)
            val tvSubmitCources = dialogView.findViewById<TextView>(R.id.tvSubmitCources)

            rlTool.tvTitle.text = "Courses Covered"

            rlTool.ivNotification.visibility = View.GONE

            var ans1 = ""
            var ans2 = ""
            var ans3 = ""
            var ans4 = ""
            var ans5 = ""
            var ans6 = ""

            val pos = i

            val resList: ArrayList<String> = ArrayList()
            resList.clear()
            coursesArrayName.clear()
            coursesArray.clear()

            Log.e("ffwer0",dataCourses.toString())
            val yourArray: List<String>

            yourArray = dataCourses.split(",")


            val size1: Int = yourArray.size

            for (intt in 0 until  size1) {
                if (yourArray[intt].isNotEmpty()) {
                    resList.add(yourArray[intt])
                }
            }
            Log.e("resList:", "" + resList.size + "" + "dataCourses:" + dataCourses)

            val size: Int = resList.size//                    Log.e("dataIsNotEmpty ", dataCourses)

            when (size) {
                1 -> {
                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                }
                2 -> {

                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                    etAns2.text =
                        Editable.Factory.getInstance().newEditable(yourArray[1])

                }
                3 -> {

                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                    etAns2.text =
                        Editable.Factory.getInstance().newEditable(yourArray[1])
                    etAns3.text =
                        Editable.Factory.getInstance().newEditable(yourArray[2])

                }
                4 -> {
                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                    etAns2.text =
                        Editable.Factory.getInstance().newEditable(yourArray[1])
                    etAns3.text =
                        Editable.Factory.getInstance().newEditable(yourArray[2])
                    etAns4.text =
                        Editable.Factory.getInstance().newEditable(yourArray[3])
                }
                5 -> {
                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                    etAns2.text =
                        Editable.Factory.getInstance().newEditable(yourArray[1])
                    etAns3.text =
                        Editable.Factory.getInstance().newEditable(yourArray[2])
                    etAns4.text =
                        Editable.Factory.getInstance().newEditable(yourArray[3])
                    etAns5.text =
                        Editable.Factory.getInstance().newEditable(yourArray[4])
                }
                6 -> {
                    etAns1.text =
                        Editable.Factory.getInstance().newEditable(yourArray[0])
                    etAns2.text =
                        Editable.Factory.getInstance().newEditable(yourArray[1])
                    etAns3.text =
                        Editable.Factory.getInstance().newEditable(yourArray[2])
                    etAns4.text =
                        Editable.Factory.getInstance().newEditable(yourArray[3])
                    etAns5.text =
                        Editable.Factory.getInstance().newEditable(yourArray[4])
                    etAns6.text =
                        Editable.Factory.getInstance().newEditable(yourArray[5])


                }
            }


            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            val width = WindowManager.LayoutParams.FILL_PARENT
            val height = WindowManager.LayoutParams.FILL_PARENT
            alertDialog.window!!.setLayout(width, height)

            alertDialog.show()



            tvSubmitCources?.setOnClickListener {

                catFlag = true

                ans1 = etAns1?.text.toString().trim()
                ans2 = etAns2?.text.toString().trim()
                ans3 = etAns3?.text.toString().trim()
                ans4 = etAns4?.text.toString().trim()
                ans5 = etAns5?.text.toString().trim()
                ans6 = etAns6?.text.toString().trim()


                if (ans1.isNotEmpty()) {
                    coursesArray.add(CategoryModel("1", ans1))
                    coursesArrayName.add(ans1)
                }
                if (ans2.isNotEmpty()) {
                    coursesArray.add(CategoryModel("2", ans2))
                    coursesArrayName.add(ans2)

                }
                if (ans3.isNotEmpty()) {
                    coursesArray.add(CategoryModel("3", ans3))
                    coursesArrayName.add(ans3)
                }
                if (ans4.isNotEmpty()) {
                    coursesArray.add(CategoryModel("4", ans4))
                    coursesArrayName.add(ans4)
                }
                if (ans5.isNotEmpty()) {
                    coursesArray.add(CategoryModel("5", ans5))
                    coursesArrayName.add(ans5)
                }
                if (ans6.isNotEmpty()) {
                    coursesArray.add(CategoryModel("6", ans6))
                    coursesArrayName.add(ans6)
                }


                dataCourses= ""
                 dataCourses = coursesArrayName.toString().substring(1, coursesArrayName.toString().length - 1)
//                Log.e("dataIsNotEmpty", "dataCourses:" + dataCourses)
//                Log.e("dataIsNotEmpty", "coursesArray:" + coursesArray.size)
//                Log.e("dataIsNotEmpty", "coursesArrayName:" + coursesArrayName.size)

                if (dataCourses.isNotEmpty()) {
//                    Log.e("dataIsNotEmpty ", dataCourses)
                    if (completeProfileAdapter != null) {
                        Log.e("position ", "dgsdergs$pos")
                        completeProfileAdapter?.dataCourses(dataCourses)
                        completeProfileAdapter?.notifyItemChanged(pos)
                    }
                } else {
                    if (completeProfileAdapter != null) {
                        completeProfileAdapter?.dataCourses("")
                        completeProfileAdapter?.notifyItemChanged(pos)
                    }
                }
                alertDialog.cancel()
            }

            alertDialog.setCancelable(true)
            alertDialog.setCanceledOnTouchOutside(true)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setOnClickListner() {

        iv_BackArrow.setOnClickListener(this)
        tvnextbutton.setOnClickListener(this)
        iv_edit10.setOnClickListener(this)
        tvPreviousbutton.setOnClickListener(this)
        tvSkip.setOnClickListener(this)
        tvsubmitbutton.setOnClickListener(this)

    }


    var quesSize = 1
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_BackArrow -> {
                onBackPressed()
            }

            R.id.tvnextbutton -> {

                NextFlag = true

                count += 5
                quesSize = 2
                tvPreviousbutton.visibility = View.VISIBLE
                Log.e("Count", "tvnextbutton...$count")
                Log.e("DataCourses", "tvnextbutton...$dataCourses")
                Log.e("answerList", "tvnextbutton...$answerList")
                Log.e("selectedList", "tvnextbutton...$totalQues")



                if (array1.isNotEmpty()) {
                    val json = JSONObject()
                    json.put("answer", array1[array1.size - 1])
                    json.put("question_id", arrayid[arrayid.size - 1])
                    array2.add(json)
                    jsonArray.put(json)
                    Log.e("sdfjklasdjk", array2.toString())
                    Log.e("sdfjklasdjk2", array1[array1.size - 1])
                    Log.e("sdfjklasdjk3", ques_id)
                }

                    if(count<totalQues ){
                        profileQustion()
                        tvsubmitbutton.visibility = View.INVISIBLE
                        tvnextbutton.visibility = View.VISIBLE
                        tvSkip.visibility = View.VISIBLE
                        llradio.visibility = View.INVISIBLE

                    }
                        else{
                        profileQustion()
                        tv_profileStatus.visibility = View.VISIBLE
                        llradio.visibility = View.VISIBLE
                        tvsubmitbutton.visibility = View.VISIBLE
                        tvnextbutton.visibility = View.INVISIBLE
                        tvSkip.visibility = View.INVISIBLE
                    }

/*                if (count == 10) {
                    quesSize = 3
                    profileQustion()
                    tvsubmitbutton.visibility = View.INVISIBLE
                    tvnextbutton.visibility = View.VISIBLE
                    tvSkip.visibility = View.VISIBLE
                    llradio.visibility = View.INVISIBLE
                }
                else if (count == 15) {
                    quesSize = 4
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                    tvsubmitbutton.visibility = View.INVISIBLE
                    tvnextbutton.visibility = View.VISIBLE
                    tvSkip.visibility = View.VISIBLE
                }
                else if (count == 20) {
                    quesSize = 5
                    profileQustion()
                    tv_profileStatus.visibility = View.VISIBLE
                    llradio.visibility = View.VISIBLE
                    tvsubmitbutton.visibility = View.VISIBLE
                    tvnextbutton.visibility = View.INVISIBLE
                    tvSkip.visibility = View.INVISIBLE
                } else if (count == 5) {
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                }*/

                if (file1 != null) {
                    profileanswer(array2, false)
                } else {
                    // jsonArray = array2
                    profileanswer2(jsonArray, false)
                }

            }

            R.id.tvSkip -> {

                count = totalQues
                profileQustion()
                tvPreviousbutton.visibility = View.VISIBLE
                Log.e("Count", "...$count")
                if (count == totalQues) {
                    tv_profileStatus.visibility = View.VISIBLE
                    llradio.visibility = View.VISIBLE
                    tvsubmitbutton.visibility = View.VISIBLE
                    tvnextbutton.visibility = View.INVISIBLE
                    tvSkip.visibility = View.INVISIBLE

                } else {
                    tvSkip.visibility = View.VISIBLE
                }



            }

            R.id.tvsubmitbutton -> {


                NextFlag = false

                Log.e("Count", "tvsubmitbutton...$count")
                Log.e("DataCourses", "tvsubmitbutton...$dataCourses")
                Log.e("categoriesList", "tvsubmitbutton...$categoriesList")
                Log.e("hobbyList", "tvsubmitbutton...$hobbyList")
                Log.e("intrestList", "tvsubmitbutton...$intrestList")
                Log.e("answerList", "tvsubmitbutton...$answerList")
                Log.e("quesSize", "tvsubmitbutton...$quesSize")
                Log.e("selectedList", "tvsubmitbutton...$selectedList")


                if (array1.isNotEmpty()) {
                    val json = JSONObject()
                    json.put("answer", array1[array1.size - 1])
                    json.put("question_id", arrayid[arrayid.size - 1])
                    array2.add(json)
                    jsonArray.put(json)
                    Log.e("sdfjklasdjk", array2.toString())
                    Log.e("sdfjklasdjk2", array1[array1.size - 1])
                    Log.e("sdfjklasdjk3", ques_id)
                }


                if (file1 != null) {
                    profileanswer(array2, true)
                } else {
                    // jsonArray = array2
                    profileanswer2(jsonArray, true)
                }
            }


            R.id.tvPreviousbutton -> {

                count -= 5

                Log.e("Count", "tvPreviousbutton...$count")
                Log.e("DataCourses", "...$dataCourses")


                if(count<=0){
                    count=0
                }


                if(count<1){
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                    tvPreviousbutton.visibility = View.GONE
                    ivprofile10.visibility = View.VISIBLE
                    iv_edit10.visibility = View.VISIBLE
                    tvSkip.visibility = View.INVISIBLE

                }

                else{

                    profileQustion()
                    tv_profileStatus.visibility = View.VISIBLE
                    llradio.visibility = View.VISIBLE
                    tvsubmitbutton.visibility = View.VISIBLE
                    tvnextbutton.visibility = View.INVISIBLE
                    tvSkip.visibility = View.INVISIBLE

                }

 /*               if (count == 0) {
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                    tvPreviousbutton.visibility = View.GONE
                    ivprofile10.visibility = View.VISIBLE
                    iv_edit10.visibility = View.VISIBLE
                    tvSkip.visibility = View.VISIBLE

                } else if (count == 20) {
                    profileQustion()
                    tv_profileStatus.visibility = View.VISIBLE
                    llradio.visibility = View.VISIBLE
                    tvsubmitbutton.visibility = View.VISIBLE
                    tvnextbutton.visibility = View.INVISIBLE
                    tvSkip.visibility = View.INVISIBLE

                } else if (count == 15) {
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                    tvSkip.visibility = View.VISIBLE
                    tv_profileStatus.visibility = View.GONE
                    tvsubmitbutton.visibility = View.GONE
                    tvnextbutton.visibility = View.VISIBLE
                } else if (count == 10) {
                    profileQustion()
                    llradio.visibility = View.INVISIBLE
                    tvSkip.visibility = View.VISIBLE
                    tv_profileStatus.visibility = View.GONE
                    tvsubmitbutton.visibility = View.GONE
                    tvnextbutton.visibility = View.VISIBLE
                } else if (count == 5) {
                    profileQustion()
                    tvSkip.visibility = View.VISIBLE
                    llradio.visibility = View.INVISIBLE
                }*/
            }
            R.id.iv_edit10 -> {
                selectImg()
            }
        }
    }

    var interestId = ""
    var catogaryId = ""
    var hobbyId = ""

    private fun profileanswer2(array2: JSONArray, boolean: Boolean) {

        interestId = if (finalarrayInt.isEmpty()) {
            InterestIdList.toString().substring(1, InterestIdList.toString().length - 1)
        } else {
            finalarrayInt.toString().substring(1, finalarrayInt.toString().length - 1)
        }

        hobbyId = if (finalarrayHob.isEmpty()) {
            hobbyIdList.toString().substring(1, hobbyIdList.toString().length - 1)
        } else {
            finalarrayHob.toString().substring(1, finalarrayHob.toString().length - 1)
        }

        Log.e("serviceresponse3", "" + interestId)

        val jsonn = JSONObject()
        try {

            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id")
            }
            jsonn.put("user_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))


            if (array2.length() > 1) {
                jsonn.put("edit_answer", array2)
            }
            if (radioValue.isNotEmpty()) {
                jsonn.put("profile_status", radioValue)
            } else {
                jsonn.put("profile_status", "public")
            }
            if (interestId.isNotEmpty()) {
                jsonn.put("interest_id", interestId)
            }
            if (hobbyId.isNotEmpty()) {
                jsonn.put("hobbies_id", hobbyId)
            }

            val jsonArrayCat: JSONArray = JSONArray()

            val size: Int = coursesArray.size

            Log.e("CAAA", "coursesArray:size" + size)

            for (i: Int in 0 until size) {

                val json = JSONObject()
                json.put("answer", coursesArray[i].name1)
                json.put("course_id", coursesArray[i].id1)
                Log.e("CAAA", coursesArray[i].name1 + " ID : " + coursesArray[i].id1)
                jsonArrayCat.put(json)
            }

            Log.e("CAAA", jsonArrayCat.toString())

            jsonn.put("type", "android")

            if (jsonArrayCat != null) {
                jsonn.put("categories_id", jsonArrayCat)
            }
            if (Alerts.isNetworkAvailable(this)) {
                RetrofitService(
                    this, this, WebUrls.SEND_PROFILE_ANSWER,
                    WebUrls.SEND_PROFILE_ANSWER_CODE, 3, jsonn
                ).callService(boolean)
            } else {
                Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }


    }

    @SuppressLint("DefaultLocale")
    private fun profileanswer(array2: ArrayList<JSONObject>, boolean: Boolean) {

        interestId = if (finalarrayInt.isEmpty()) {
            InterestIdList.toString().substring(1, InterestIdList.toString().length - 1)
        } else {
            finalarrayInt.toString().substring(1, finalarrayInt.toString().length - 1)
        }
        hobbyId = if (finalarrayHob.isEmpty()) {
            hobbyIdList.toString().substring(1, hobbyIdList.toString().length - 1)
        } else {
            finalarrayHob.toString().substring(1, finalarrayHob.toString().length - 1)
        }

        val mMap = HashMap<String, RequestBody>()

        try {


            if (interestId.isNotEmpty()) {
                mMap["interest_id"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), interestId)
            }
            if (hobbyId.isNotEmpty()) {
                mMap["hobbies_id"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), hobbyId)
            }

            mMap["user_id"] = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                PreferenceFile.getInstance().getPreferenceData(SignUp@ this, WebUrls.KEY_USERID)
            )


            if (array2.isNotEmpty()) {
                mMap["edit_answer"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), array2.toString())
            }

            if (radioValue.isNotEmpty()) {
                mMap["profile_status"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), radioValue.toLowerCase())
            } else {
                mMap["profile_status"] =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "public")
            }

            val jsonArrayCat: JSONArray = JSONArray()


            var size: Int = coursesArray.size

            Log.e("CAAA", "coursesArray:size" + size)

            for (i: Int in 0 until size) {

                val json = JSONObject()
                json.put("answer", coursesArray[i].name1)
                json.put("course_id", coursesArray[i].id1)
                Log.e("CAAA", coursesArray[i].name1 + " ID : " + coursesArray[i].id1)
                jsonArrayCat.put(json)
            }

            Log.e("CAAA", jsonArrayCat.toString())

            if (jsonArrayCat != null) {
                mMap["categories_id"] = RequestBody.create(
                    MediaType.parse("multipart/form-data"), jsonArrayCat.toString()
                )
            }


            mMap["type"] =
                RequestBody.create(MediaType.parse("multipart/form-data"), "android")

            Log.e("ABCDGH", "" + mMap!!.toString())
            if (Alerts.isNetworkAvailable(this)) {
                RetrofitService(
                    this, this, WebUrls.SEND_PROFILE_ANSWER,
                    WebUrls.SEND_PROFILE_ANSWER_CODE, 5, mMap, part!!
                ).callService(boolean)
            } else {
                Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
            }


        } catch (ex: Exception) {
            ex.printStackTrace()
        }

//        Log.e(
//                "serviceresponse1",
//                "" + PreferenceFile.getInstance().getPreferenceData(this@CompleteProfile, WebUrls.KEY_USERID)
//        )
        /*Log.e("serviceresponse2", "" + radioValue)
        Log.e("serviceresponse3", "" + finalarrayInt.toString())
        Log.e("serviceresponse4", "" + finalarrayCat.toString())
        Log.e("serviceresponse5", "" + finalarrayHob.toString())*/


    }

    private fun profileQustion() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                postParam.put(
                    "user_id",
                    PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID)
                )
                postParam.put("offset", count)

                RetrofitService(
                    this, this, WebUrls.VIEW_PROFILE_QUES_ANSWER,
                    WebUrls.VIEW_PROFILE_QUES_ANSWER_CODE, 3, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            /*   val postParam = JSONObject()

               try {

                   RetrofitService(
                       this, this, WebUrls.SEND_PROFILE_QUSTION + count,
                       WebUrls.SEND_PROFILE_QUSTION_CODE, 1, postParam
                   ).callService(true)
               } catch (ex: Exception) {
                   ex.printStackTrace()
               }*/


        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }

    private fun profileCategories() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this, this, WebUrls.SEND_CATEGORIES,
                    WebUrls.SEND_CATEGORIES_CODE, 1, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun hobby() {
        if (Alerts.isNetworkAvailable(this)) {
            val postParam = JSONObject()

            try {

                RetrofitService(
                    this, this, WebUrls.SEND_HOBBY,
                    WebUrls.SEND_HOBBY_CODE, 1, postParam
                ).callService(true)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            Common.customDialog(this, "Unilife", resources.getString(R.string.internet_offline))
        }
    }


    private fun intrest() {
        if(Alerts.isNetworkAvailable(this)){
        val postParam = JSONObject()
        try {
            RetrofitService(
                this,
                this,
                WebUrls.SEND_INTREST,
                WebUrls.SEND_INTREST_CODE,
                1,
                postParam
            ).callService(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }


    private fun checkPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            val hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA)
            val hasReadExtPermission =
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            val hasWriteExtPermission =
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)

            val permissionList = java.util.ArrayList<String>()

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.CAMERA)
            }

            if (hasReadExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (hasWriteExtPermission != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if (permissionList.isNotEmpty()) {
                requestPermissions(permissionList.toTypedArray(), 2)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {

        when (requestCode) {

            2 ->

                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        checkPermission()
                    }
                }

            MY_PERMISSIONS_REQUEST_CAMERA -> {
                launchCamera()
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }
        }
    }


    fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        if (cameraIntent.resolveActivity(packageManager) != null) {
            try {
                val dir = File(Alerts.makeDirectory(this))
                file = File(dir, System.currentTimeMillis().toString() + "unilife.png")
                uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider", file!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(cameraIntent, 501)
                Log.e("sbsjcb", "$uri dnifnfoefe")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun selectImg() {

        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")

        builder.setItems(options) { dialogInterface, i ->
            if (options[i] == "Take Photo") {
                launchCamera()

            }

            if (options[i] == "Choose from Gallery") {

                val GalIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(
                    Intent.createChooser(GalIntent, "Select Image from Gallery"),
                    111
                )

            } else if (options[i] == "Cancel") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            501 -> if (resultCode == Activity.RESULT_OK) { //camera
                Log.e("is crop working ?", "hdsdgjmgmjgojg")
                cropImage(uri)
            }


            111 -> if (resultCode == Activity.RESULT_OK) {      // gallery
                val uri = data!!.data
                try {
                    val filePath = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = contentResolver.query(uri!!, filePath, null, null, null)
                    if (cursor == null) {
                        val path = uri.path
                        file = File(path)
                    } else {
                        cursor.moveToFirst()
                        val colIndex = cursor.getColumnIndex(filePath[0])
                        val path = cursor.getString(colIndex)
                        file = File(path)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                cropImage(data.data)

            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri = result.uri
                    setImageafterCrop(resultUri.path)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    error.printStackTrace()
                }
            }
        }


    }

    /*crop image square*/
    private fun cropImage(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1).start(this)
    }


    private fun setImageafterCrop(path: String?) {

        file1 = File(path)
        bitmap = BitmapFactory.decodeFile(file1!!.path)
        ivprofile10.setImageBitmap(bitmap)
        val reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file1)
        part = MultipartBody.Part.createFormData("profile_image", file1!!.name, reqFile)
    }


    override fun onResponse(requestCode: Int, response: String) {

        try {

            val `object` = JSONObject(response)
            val res = `object`.getString("response")

            Log.e("Register1", response)


            when (requestCode) {

                WebUrls.SEND_PROFILE_QUSTION_CODE -> {
                    if (res.equals("true", true)) {
                        qustionData?.clear()

                        Log.e("asdgklhdf", `object`.toString())

                        val data = `object`.getJSONObject("data")
                        if (`object`.getString("data") != " ") {
                            val row = data.getJSONArray("rows")
                            if (!row.equals(" ")) {
                                for (i: Int in 0 until row.length()) {
                                    val completeProfile = CompleteProfileModel()
                                    val rowObj = row.getJSONObject(i)
                                    completeProfile.qustion = rowObj.getString("question")
                                    completeProfile.id = rowObj.getString("id")
                                    completeProfile.qustion_type = rowObj.getString("question_type")
                                    qustionData?.add(completeProfile)
                                }
                                setQustionAdapter()
                            } else {


                            }
                        }
                    } else {
                        quesSize = 0
                        Alerts.alertDialog(this, `object`.getString("message"))
                    }
                }

                WebUrls.VIEW_PROFILE_QUES_ANSWER_CODE -> {

                    if (res.equals("true", true)) {

                        qustionData?.clear()
                        selectedList.clear()
                        hobbyIdList.clear()
                        InterestIdList.clear()
                        CatogaryIdList.clear()

                        Log.e("VW_PRF_QUES_ANS_CODE", `object`.toString())

                        val data = `object`.getJSONObject("data")

                        if (`object`.getString("data") != " ") {

                            val user_ques_ans = data.getJSONObject("user_ques_ans")

                             totalQues = user_ques_ans.getInt("count")


                            val row = user_ques_ans.getJSONArray("rows")
                            if (row.length()>0) {
                                for (i: Int in 0 until row.length()) {

                                    val rowObj = row.getJSONObject(i)

                                    val completeProfile = CompleteProfileModel()
                                    completeProfile.qustion = rowObj.getString("question")
                                    completeProfile.id = rowObj.getString("id")
                                    completeProfile.qustion_type = rowObj.getString("question_type")

                                    val ques_answers = rowObj.getJSONArray("ques_answers")
                                    if (ques_answers.length() != 0) {
                                        val ansObj =
                                            ques_answers.getJSONObject(ques_answers.length() - 1)
                                        if (rowObj.getString("id") != "3") {
                                            completeProfile.ques_answer = ansObj.getString("answer")
                                        }
                                    }

                                    qustionData?.add(completeProfile)

                                }

                                if(qustionData!!.size<5){

                                    tv_profileStatus.visibility = View.VISIBLE
                                    llradio.visibility = View.VISIBLE
                                    tvsubmitbutton.visibility = View.VISIBLE
                                    tvnextbutton.visibility = View.INVISIBLE
                                    tvSkip.visibility = View.INVISIBLE
                                }
                                else{
                                    tv_profileStatus.visibility = View.GONE
                                    llradio.visibility = View.GONE
                                    tvsubmitbutton.visibility = View.GONE
                                    tvnextbutton.visibility = View.VISIBLE
                                    tvSkip.visibility = View.VISIBLE

                                }

                            }

                            Log.e("whkedchssd",""+qustionData!!.size)

                            val user_detail = data.getJSONObject("user_detail")

                            val select = SelectedModel()
                            // Image of user
                            val profImage = user_detail.getString("profile_image")

                            if (profImage != null) {
                                if (bitmap != null) {
                                    ivprofile10.setImageBitmap(bitmap)
                                } else {
                                    Picasso.with(applicationContext)
                                        .load(WebUrls.PROFILE_IMAGE + profImage)
                                        .placeholder(R.drawable.user).into(ivprofile10)
                                }
                            } else {
                                if (bitmap != null) {
                                    ivprofile10.setImageBitmap(bitmap)
                                }
                            }

                            val user_hobbies = user_detail.getJSONArray("user_hobbies")
                            val user_hobbies_interests =
                                user_detail.getJSONArray("user_hobbies_interests")
                            val user_course_covered =
                                user_detail.getJSONArray("user_course_covered")


                            if (user_hobbies.length() > 0) {
                                for (j: Int in 0 until user_hobbies.length()) {

                                    val user_hobbiesObj = user_hobbies.getJSONObject(j)

                                    Log.e("j value ", j.toString())

                                    val hobbies =
                                        user_hobbiesObj.getJSONObject("hobbies")
                                    select.hobby_name.add(hobbies.getString("name"))
                                    select.hobbyId = hobbies.getInt("id")
                                }

                            }
                            if (user_hobbies_interests.length() > 0) {
                                for (j: Int in 0 until user_hobbies_interests.length()) {

                                    val user_hobbies_interestsObj =
                                        user_hobbies_interests.getJSONObject(j)
                                    val hobbies =
                                        user_hobbies_interestsObj.getJSONObject("hobbies_interests")
                                    select.interest_name.add(hobbies.getString("name"))
                                    select.interestId = hobbies.getInt("id")
                                }

                            }

                            Log.e(
                                "COURSES_COVERED", " coursesArray.size: " + coursesArray.size
                                        + " coursesArrayName.size: " + coursesArrayName.size
                                        + " catFlag : " + catFlag
                            )

                            if (!catFlag) {

                                if (user_course_covered.length() > 0) {

                                    if (coursesArray.size == 0) {

                                        for (j: Int in 0 until user_course_covered.length()) {
                                            val user_course_coveredObj =
                                                user_course_covered.getJSONObject(j)

                                            select.catogary_name.add(
                                                user_course_coveredObj.getString(
                                                    "answer"
                                                )
                                            )
                                            select.catogaryId =
                                                user_course_coveredObj.getInt("course_id")
                                            coursesArray.add(
                                                CategoryModel(
                                                    user_course_coveredObj.getString("course_id"),
                                                    user_course_coveredObj.getString("answer")
                                                )
                                            )
                                            coursesArrayName.add(user_course_coveredObj.getString("answer"))
                                        }
                                        dataCourses =
                                            select.catogary_name.toString().replace("[", "")
                                                .replace("]", "")

                                    }
                                } else {
                                    dataCourses = ""
                                }
                            }
                            Log.e(
                                "dataCourses:",
                                dataCourses.toString() + "catFlag: " + catFlag
                            )


                            hobbyIdList.add(select.hobbyId)
                            InterestIdList.add(select.interestId)
                            CatogaryIdList.add(select.catogaryId.toString())
                            selectedList.add(select)
                        }

                              setQustionAdapter()
                         //       completeProfileAdapter!!.notifyDataSetChanged()
                    } else {
                        quesSize = 0
                        Alerts.alertDialog(this, `object`.getString("message"))

                    }
                }


                WebUrls.SEND_CATEGORIES_CODE -> {

                    if (res.equals("true", true)) {
                        val completeProfileModel = CompleteProfileModel()
                        //init

                        val data = `object`.getJSONArray("data")
                        for (i: Int in 0 until data.length()) {

                            //  categoriesList.add(0,"Select Category")

                            val categoriesProfileModel = CategoriesProfileModel()
                            val obj = data.getJSONObject(i)
                            var categories_name = obj.getString("categories_name")
                            categoriesProfileModel.categories_name =
                                obj.getString("categories_name")
                            categoriesProfileModel.categories_id = obj.getString("id")
                            categoriesList?.add(categoriesProfileModel)
//                            Log.e("gdsf", "hgd" + categoriesProfileModel.categories_name)

                        }


                    } else {
                        Alerts.alertDialog(this, `object`.getString("message"))
                    }
                }


                WebUrls.SEND_HOBBY_CODE -> {

                    if (res.equals("true", true)) {

                        val data = `object`.getJSONArray("data")
                        for (i: Int in 0 until data.length()) {


                            val hobbyModel = HobbyModel()
                            val obj = data.getJSONObject(i)
                            hobbyModel.hobby = obj.getString("name")
                            hobbyModel.hobbyId = obj.getString("id")
                            hobbyList?.add(hobbyModel)
//                            Log.e("gdsf", "hgd" + hobbyModel.hobby)


                        }


                    } else {

                        Alerts.alertDialog(this, `object`.getString("message"))


                    }
                }


                WebUrls.SEND_INTREST_CODE -> {

                    if (res.equals("true", true)) {
                        val completeProfileModel = CompleteProfileModel()
                        //init

                        val data = `object`.getJSONArray("data")
                        for (i: Int in 0 until data.length()) {


                            val intrestModel = IntrestModel()
                            val obj = data.getJSONObject(i)
                            intrestModel.intrest = obj.getString("name")
                            intrestModel.intrestId = obj.getString("id")

                            intrestList?.add(intrestModel)
//                            Log.e("gdsf", "hgd" + intrestModel.intrest)


                        }


                    } else {

                        Alerts.alertDialog(this, `object`.getString("message"))


                    }
                }

                WebUrls.SEND_PROFILE_ANSWER_CODE -> {

                    try {

                        if (res.equals("true", true)) {

                            Log.e("servicehit", `object`.toString())

                            val data = `object`.getJSONObject("data")

                            PreferenceFile.getInstance().saveData(
                                this@CompleteProfile,
                                WebUrls.KEY_USERID,
                                data.getString("id")
                            )

                            PreferenceFile.getInstance().saveData(
                                this@CompleteProfile,
                                WebUrls.KEY_USERNAME,
                                data.getString("username")
                            )
                            PreferenceFile.getInstance().saveData(
                                this@CompleteProfile,
                                WebUrls.KEY_PROFILE_IMAGE,
                                data.getString("profile_image")
                            )
                            if (!NextFlag) {

                                val intent = Intent(this, Home::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Alerts.alertDialog(this, `object`.getString("message"))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }


            }


        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }

}


