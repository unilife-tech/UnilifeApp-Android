package unilife.com.unilife.profile

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import unilife.com.unilife.retrofit.WebUrls
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.SpannableStringBuilder
import unilife.com.unilife.R
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MyProfileAdapter() : RecyclerView.Adapter<MyProfileAdapter.MyProfileViewHolder>() {

    lateinit var context: Context
    lateinit var qustionData: ArrayList<MyProfileModel>
    var clickListener: ClickListener? = null


    constructor(context: Context, qustionData: ArrayList<MyProfileModel>) : this() {

        this.context = context
        this.qustionData = qustionData

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyProfileViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.my_profile_adapter, viewGroup, false)
        return MyProfileViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(myProfileViewHolder: MyProfileViewHolder, i: Int) {

        if (qustionData[i].questionType !="simple")
        {

            if(qustionData[i].qustion !="Name" && qustionData[i].qustion !="Bio"
                && qustionData[i].qustion !="Current studies"
                && qustionData[i].qustion !="Courses Covered"
                && qustionData[i].qustion !="Hobbies"
                && qustionData[i].qustion !="Interest"

            )

            {
                Log.e("asdfgfgfd", "" + qustionData[i].answer)

                myProfileViewHolder.llprofileadapter?.visibility = View.VISIBLE
                myProfileViewHolder.tvQues?.visibility = View.GONE
                myProfileViewHolder.ivQustionImg?.visibility = View.GONE

                myProfileViewHolder.tvanswer?.visibility = View.VISIBLE



                if (qustionData[i].answer == "") {

                    myProfileViewHolder.llprofileadapter?.visibility = View.GONE
                    /*myProfileViewHolder.ivanswer_img?.visibility = View.GONE
                    myProfileViewHolder.tvanswer?.visibility = View.GONE*/

                } else {
                    Picasso.with(context).load(WebUrls.MYPROFILE_ICON + qustionData[i].qustionImage)
                        .placeholder(R.drawable.no_image).resize(25, 25)
                        .into(myProfileViewHolder.ivanswer_img)
                    myProfileViewHolder.tvanswer?.text = qustionData[i].answer

                }
            }
        }
        else {

            /*if (i in 7..12) {*/

            myProfileViewHolder.llprofileadapter?.visibility = View.GONE
            myProfileViewHolder.llprofileadapter1?.visibility = View.VISIBLE
            myProfileViewHolder.tvQues1?.text = qustionData[i].qustion


            myProfileViewHolder.tvQues1?.visibility = View.VISIBLE

            myProfileViewHolder.tvanswer1?.visibility = View.VISIBLE


            Log.e("asklbnxdvdv", "" + qustionData[i].answer)

            if (qustionData[i].answer == "") {

                myProfileViewHolder.llprofileadapter1?.visibility = View.GONE

            } else {
                myProfileViewHolder.tvQues1?.visibility = View.VISIBLE
                myProfileViewHolder.rv_answers?.visibility = View.VISIBLE
                var answer = qustionData[i].answer
                val substring  = answer.split("\n")
                val list: ArrayList<String>  = ArrayList()
                list.addAll(substring)
                Log.e("TTTTTT", "=======$list")

                Log.e("evdfgrg", "" + list.size)

                myProfileViewHolder.rv_answers!!.adapter = AnswersAdapter(context, list )
//                Log.e("S_VALUE","dfd::"+ qustionData[i].answer)

//                if(qustionData[i].answer.contains("\\r\\n")){
    //                myProfileViewHolder.tvanswer1?.text = qustionData[i].answer
//                    myProfileViewHolder.tvanswer1?.text = getSmiledText(context,qustionData[i].answer)
//                }else {
//                    myProfileViewHolder.tvanswer1?.text = qustionData[i].answer
//                }



                Picasso.with(context).load(WebUrls.MYPROFILE_ICON + qustionData[i].qustionImage)
                    .placeholder(unilife.com.unilife.R.drawable.no_image).resize(25, 25)
                    .into(myProfileViewHolder.ivQustionImg1)

            }
            // }
            /*if (i > 14) {*/
            /*myProfileViewHolder.llprofileadapter1?.visibility = View.VISIBLE
            myProfileViewHolder.llprofileadapter?.visibility = View.GONE
            myProfileViewHolder.tvQues1?.text = qustionData[i].qustion
            myProfileViewHolder.tvanswer1?.visibility = View.VISIBLE

            myProfileViewHolder.tvQues1?.visibility = View.VISIBLE


            Log.e("asklbnxdvdv", "" + qustionData[i].answer)
            Picasso.with(context).load(WebUrls.MYPROFILE_ICON + qustionData[i].qustionImage)
                .placeholder(R.drawable.no_image).resize(25, 25)
                .into(myProfileViewHolder.ivQustionImg1)

            if (qustionData[i].answer == "") {

                myProfileViewHolder.tvanswer1?.text = "N/A"

            } else {
                myProfileViewHolder.tvanswer1?.text = qustionData[i].answer

            }*/
        }
        // }
        //  myProfileViewHolder.ivQustionImg!!.backgroundTintList = context.getColorStateList(R.color.colorPrimary)

    }




    fun getSmiledText(context: Context, s: String): Spannable {

        var emoticons:HashMap<String, Int> = HashMap<String, Int>()
        emoticons["\n"] = R.drawable.answer_icon_eight
        emoticons["\r\n"] = R.drawable.answer_icon_eight

        Log.e("S_VALUE", "dfddf:$s")

        var index: Int = 0
        val builder = SpannableStringBuilder()
        builder.append(" $s   ")

       /* builder.setSpan(
            ImageSpan(context,R.drawable.answer_icon_eight),
            index, index + s.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
*/
        index = 0

        while (index < builder.length) {

            for (entry in emoticons.entries) {
                val length = entry.key.length
                if (index + length > builder.length)
                    continue

                Log.e("CHECK",builder.subSequence(index, index + length).toString()+"" +
                        "entry.key:"+entry.key+" "+entry.value+" s: "+s)
                if (builder.subSequence(index, index + length).toString() == entry.key) {
                    builder.setSpan(
                        ImageSpan(context, entry.value),
                        index, index + length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    index += length - 1
                    break
                }
            }

            index++

        }

        return builder
    }

    override fun getItemCount(): Int {
        return qustionData.size
    }

    inner class MyProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var tvQues: TextView? = null
        internal var tvanswer: TextView? = null
        internal var ivQustionImg: ImageView? = null
        internal var ivanswer_img: ImageView? = null
        internal var tvQues1: TextView? = null
        internal var tvanswer1: TextView? = null
        internal var ivQustionImg1: ImageView? = null
        internal var ivanswer_img1: ImageView? = null
        internal var llprofileadapter: LinearLayout? = null
        internal var llprofileadapter1: LinearLayout? = null
        internal var rv_answers: RecyclerView? = null





        init {

            tvQues = itemView.findViewById(R.id.tvQues)
            tvanswer = itemView.findViewById(R.id.tvanswer)
            ivQustionImg = itemView.findViewById(R.id.ivQustionImg)
            ivanswer_img = itemView.findViewById(R.id.ivanswer_img)
            tvQues1 = itemView.findViewById(R.id.tvQues1)
      //     tvanswer1 = itemView.findViewById(R.id.tvanswer1)
            ivQustionImg1 = itemView.findViewById(R.id.ivQustionImg1)
 //           ivanswer_img1 = itemView.findViewById(R.id.ivanswer_img1)
            rv_answers = itemView.findViewById(R.id.rv_answers)


            llprofileadapter = itemView.findViewById(R.id.llprofileadapter)
            llprofileadapter1 = itemView.findViewById(R.id.llprofileadapter1)
            /*   rycCategories = itemView.findViewById(R.id.rycCategories)
               rycIntrest = itemView.findViewById(R.id.rycIntrest)
               rycHobbies = itemView.findViewById(R.id.rycHobbies)*/

        }


    }


    fun getClickListener(clickListener: ClickListener?) {
        if (null != clickListener) {
            this.clickListener = clickListener
        }
    }

    interface ClickListener {
        fun onItemClick(pos: Int, spinner: Spinner)


    }

}


