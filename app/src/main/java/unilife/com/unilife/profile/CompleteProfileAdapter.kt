package unilife.com.unilife.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import unilife.com.unilife.R
import android.widget.ArrayAdapter
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner
import io.apptik.widget.multiselectspinner.MultiSelectSpinner
import java.util.*
import kotlin.collections.ArrayList


class CompleteProfileAdapter() :
    RecyclerView.Adapter<CompleteProfileAdapter.CompleteProfileViewHolder>() {

    lateinit var context: Context
    lateinit var qustionData: ArrayList<CompleteProfileModel>
    var categoryList = ArrayList<CategoriesProfileModel>()
    var answerList = ArrayList<AnswerModel>()
    var selectedList = ArrayList<SelectedModel>()
    var hobbyList = ArrayList<HobbyModel>()
    var interestList = ArrayList<IntrestModel>()
    var category = ArrayList<String>()
    var hobby = ArrayList<String>()
    var intrest = ArrayList<String>()
    var categoryItem = ArrayList<String>()
    var categoryItemId = ArrayList<Int>()
    var hobbyItem = ArrayList<String>()
    var hobbyItemId = ArrayList<Int>()
    var intrestItem = ArrayList<String>()
    var intrestItemId = ArrayList<Int>()
    var edittextvaluelist: ArrayList<CompleteProfileEditTextModel> = ArrayList()
    var clickListener: ClickListener? = null
    var value = 0
    var hobbyll = "Select Hobby"
    var interestll = "Select Interest"
    var catogary_namell = "Courses Covered"
    var profilell = "Select Category"
    var dataCourses = ""


    constructor(
        context: Context,
        qustionData: ArrayList<CompleteProfileModel>,
        categoryList: ArrayList<CategoriesProfileModel>,
        hobbyList: ArrayList<HobbyModel>,
        interestList: ArrayList<IntrestModel>,
        answerList: ArrayList<AnswerModel>,
        value: Int,
        selectedList: ArrayList<SelectedModel>, dataCourses: String

    ) : this() {

        this.context = context
        this.qustionData = qustionData
        this.categoryList = categoryList
        this.hobbyList = hobbyList
        this.interestList = interestList
        this.answerList = answerList
        this.value = value
        this.selectedList = selectedList
        this.dataCourses = dataCourses

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CompleteProfileViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.complete_profile_adpter, viewGroup, false)


        return CompleteProfileViewHolder(itemView)
    }


    var array: ArrayList<String> = ArrayList()
    var finalanswer: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor", "RecyclerView")
    override fun onBindViewHolder(myProfileViewHolder: CompleteProfileViewHolder, i: Int) {


        myProfileViewHolder.tvQues?.text = qustionData[i].qustion

//        Log.e("datataaafdsf",selectedList[0].hobby_name.toString())

        if (selectedList.size > 0) {
            if (qustionData[i].qustion == "Hobbies") {
//                Log.e("dataerhgws",selectedList[0].hobby_name.toString())
//                Log.e("dataretrg","gfhjtyjyt")
                hobbyll = selectedList[0].hobby_name.toString()
                    .substring(1, selectedList[0].hobby_name.toString().length - 1)

//                Log.e("dataata",hobbyll)

                myProfileViewHolder.etAnswer?.text =
                    Editable.Factory.getInstance().newEditable(hobbyll)

                myProfileViewHolder.etAnswer!!.setTextColor(R.color.colorPrimary)
            }

            if (qustionData[i].qustion == "Interest") {
                interestll = selectedList[0].interest_name.toString()
                    .substring(1, selectedList[0].interest_name.toString().length - 1)

                myProfileViewHolder.etAnswer?.text =
                    Editable.Factory.getInstance().newEditable(interestll)
                myProfileViewHolder.etAnswer!!.setTextColor(R.color.colorPrimary)
            }

        }
        if (qustionData[i].ques_answer.isNotEmpty()) {
            myProfileViewHolder.etAnswer?.text =
                Editable.Factory.getInstance().newEditable(qustionData[i].ques_answer)
        }

        Log.e("questionAnswer",""+qustionData[i].ques_answer)


        if (myProfileViewHolder.etAnswer?.text.toString().isNotEmpty()) {
            myProfileViewHolder.vw?.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
            myProfileViewHolder.ivdot?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.color.colorPrimary
                )
            )
        }
        else {
            myProfileViewHolder.vw?.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.medium_grey
                )
            )
            myProfileViewHolder.ivdot?.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.color.medium_grey
                )
            )
        }

        myProfileViewHolder.etAnswer?.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(p0: Editable?) {

                array.add(p0.toString())

                finalanswer = array[array.size - 1]

                clickListener!!.data(p0.toString(), i, qustionData[i].id)


                if (myProfileViewHolder.etAnswer?.text.toString().isNotEmpty()) {
                    myProfileViewHolder.vw?.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimary
                        )
                    )
                    myProfileViewHolder.ivdot?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.color.colorPrimary
                        )
                    )
                }
                else {
                    myProfileViewHolder.vw?.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.medium_grey
                        )
                    )
                    myProfileViewHolder.ivdot?.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.color.medium_grey
                        )
                    )
                }
            }


            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        setclickListener(myProfileViewHolder.ivCourses, i, qustionData[i].qustion_type)

        if (qustionData[i].qustion_type == "categories") {

            if (dataCourses.isNotEmpty()) {
                myProfileViewHolder.etAnswer!!.text =
                    Editable.Factory.getInstance().newEditable(dataCourses)
            }

            myProfileViewHolder.etAnswer!!.isEnabled = false
            myProfileViewHolder.ivCourses!!.visibility = View.VISIBLE
        } else {
            myProfileViewHolder.ivCourses!!.visibility = View.GONE
        }


        if (qustionData[i].qustion == "Hobbies") {

            myProfileViewHolder.hobbiesspinner?.visibility = View.VISIBLE
            myProfileViewHolder.etAnswer?.visibility = View.GONE

            if(selectedList[0].hobby_name.isNotEmpty()) {
                myProfileViewHolder.hobbiesspinner?.titleDividerColor = context.getColor(R.color.dark_grey)
            }

            myProfileViewHolder.vieew3?.visibility = View.VISIBLE

            for (k in 0 until hobbyList.size) {

                hobby.add(hobbyList[k].hobby)

            }

            val adapterH =
                ArrayAdapter(context, android.R.layout.simple_list_item_multiple_choice, hobby)
            myProfileViewHolder.hobbiesspinner
                ?.setListAdapter(adapterH)
                ?.setListener<BaseMultiSelectSpinner> { selected ->
                    for (l in selected!!.indices) {
                        if (selected[l]) {
                            hobbyItem.add(hobby[l])
                            if (selected[l].toString() == "true") {
                                hobbyItemId.add(l + 1)
                            }
                        }
                    }
                    clickListener!!.passdataHob(hobbyItemId)
                    if (hobbyItem.size > 0) {
                        myProfileViewHolder.vw?.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPrimary
                            )
                        )
                        myProfileViewHolder.ivdot?.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.color.colorPrimary
                            )
                        )
                    }
                }

                ?.setTitle<BaseMultiSelectSpinner>(hobbyll)
                ?.setAllUncheckedText<BaseMultiSelectSpinner>(hobbyll)
                ?.setAllCheckedText<BaseMultiSelectSpinner>("Music,Swimming,Bowling,Photography,Cycling")
                ?.setSpinnerItemLayout<BaseMultiSelectSpinner>(R.layout.custom_spinner_item)

        }
        if (qustionData[i].qustion == "Interest") {


            myProfileViewHolder.intrestspinner?.visibility = View.VISIBLE

            if(selectedList[0].interest_name.isNotEmpty()) {
                myProfileViewHolder.intrestspinner?.titleDividerColor = context.getColor(R.color.colorPrimary)
            }
            myProfileViewHolder.etAnswer?.visibility = View.GONE
            myProfileViewHolder.view2?.visibility = View.VISIBLE


            for (m in 0 until interestList.size) {

                intrest.add(interestList[m].intrest)


            }


            val adapterI =
                ArrayAdapter(context, R.layout.interst_layout, intrest)
            myProfileViewHolder.intrestspinner?.setListAdapter(adapterI)
                ?.setListener<BaseMultiSelectSpinner> { selected ->

                    for (h in selected!!.indices) {
                        if (selected[h]) {
                            intrestItem.add(intrest[h])
                            if (selected[h].toString() == "true") {
                                intrestItemId.add(h + 6)
                                Log.e("intrestItemId",intrestItemId.toString())
                            }
                        }
                    }
                    clickListener!!.passdataInt(intrestItemId)
                    if (intrest.size > 0) {
//                        Log.e("djffdaa", "dj" + intrest.size)
                        myProfileViewHolder.vw?.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorPrimary
                            )
                        )
                        myProfileViewHolder.ivdot?.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.color.colorPrimary
                            )
                        )
                    }
                }

                ?.setTitle<BaseMultiSelectSpinner>(interestll)

                ?.setAllUncheckedText<BaseMultiSelectSpinner>(interestll)
                ?.setAllCheckedText<BaseMultiSelectSpinner>("Watching TV,Music,Painting,Guitar")
                ?.setSpinnerItemLayout<BaseMultiSelectSpinner>(R.layout.custom_spinner_item)


        }


    }

    private fun setclickListener(ivAnswer: ImageView?, i: Int, qustion: String) {

        if (qustion == "categories") {

            ivAnswer?.setOnClickListener {
                clickListener?.courses(qustion, i)
            }
        } else {
            ivAnswer?.setOnClickListener {
                //                Log.e("clickedddd", "out")
                // clickListener?.courses(qustion,i)
            }
        }
    }

    fun removeArrayDuplicates(duplicates: Array<String>): Array<String> {
        return Arrays.asList(*duplicates).toSet().toTypedArray()
    }


    override fun getItemCount(): Int {
        return qustionData.size
    }

    inner class CompleteProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvQues: TextView? = null
        internal var etAnswer: EditText? = null
        internal var categoriesspinner: MultiSelectSpinner? = null
        internal var intrestspinner: MultiSelectSpinner? = null
        internal var hobbiesspinner: MultiSelectSpinner? = null
        internal var view1: View? = null
        internal var view2: View? = null
        internal var vieew3: View? = null
        internal var tvplaceholder: TextView? = null
        internal val ivdot: ImageView? = itemView.findViewById(R.id.ivdot)
        internal var vw: View? = null
        internal var rlAnswer: RelativeLayout? = null
        internal var ivCourses: ImageView? = null
        internal var radiogroup: RadioGroup? = null


        init {

            tvQues = itemView.findViewById(R.id.tvQues)
            etAnswer = itemView.findViewById(R.id.etAnswer)
            categoriesspinner = itemView.findViewById(R.id.categoriesspinner)
            intrestspinner = itemView.findViewById(R.id.intrestspinner)
            hobbiesspinner = itemView.findViewById(R.id.hobbiesspinner)
            view1 = itemView.findViewById(R.id.view1)
            view2 = itemView.findViewById(R.id.view2)
            vieew3 = itemView.findViewById(R.id.vieew3)
            vw = itemView.findViewById(R.id.vw)
            rlAnswer = itemView.findViewById(R.id.rlAnswer)
            ivCourses = itemView.findViewById(R.id.ivCourses)
            tvplaceholder = itemView.findViewById(R.id.tvplaceholder)
            //radiogroup = itemView.findViewById(R.id.radiogroup)


        }


    }

    /*private fun setclick(
        holder: CompleteProfileViewHolder, edittextvaluelist: ArrayList<CompleteProfileEditTextModel>
        , i: Int, p0: Editable? ) {
        if (p0 != null) {
            clickListener!!.passArrayList(edittextvaluelist, i, p0)
        }
        // clickListener?.passArrayList(edittextvaluelist,i,p0)

    }*/


    public fun getClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener

    }

    public interface ClickListener {
        public fun onItemClick(pos: Int, view: View, string: String, i: Int)
        public fun data(dataAnswwer: String, pos: Int, id: String)
        public fun courses(qustion: String, i: Int)

        fun passArrayList(
            edittextvaluelist: ArrayList<CompleteProfileEditTextModel>,
            i: Int,
            s: Editable
        )

        fun passdataCat(arrayListcat: ArrayList<Int>)
        fun passdataHob(arrayListHob: ArrayList<Int>)
        fun passdataInt(arrayListInt: ArrayList<Int>)

    }

    fun dataCourses(str: String){
        dataCourses = str
    }



}
