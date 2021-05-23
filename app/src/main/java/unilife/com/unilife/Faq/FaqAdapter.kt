package unilife.com.unilife.Faq

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import unilife.com.unilife.R
import java.lang.Exception

class FaqAdapter(faqList: ArrayList<Faq.FaqData>) : RecyclerView.Adapter<FaqAdapter.myViewHolder>() {

    internal var context: Context? = null
    var faqList: ArrayList<Faq.FaqData> = ArrayList()

    init {
        this.faqList=faqList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.faq_adapter, viewGroup, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try{
            myViewHolder.one.text = (i+1).toString()
            myViewHolder.txt.text = faqList[i].questions
            myViewHolder.details_txt.text = faqList[i].answer

            myViewHolder.droDownMenue.setOnClickListener {
                if(myViewHolder.details_txt.isShown){
                    myViewHolder.details_txt.visibility = View.GONE
                    myViewHolder.droDownMenue.setImageResource(R.drawable.downfaq)
                }
                else{
                    myViewHolder.details_txt.visibility=View.VISIBLE
                    myViewHolder.droDownMenue.setImageResource(R.drawable.faq_up)
                }
                }
            }

        catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var one : TextView = itemView.findViewById(R.id.one)
        var txt : TextView = itemView.findViewById(R.id.txt)
        var details_txt : TextView = itemView.findViewById(R.id.details_txt)
        var droDownMenue : ImageView = itemView.findViewById(R.id.droDownMenue)
    }
}
