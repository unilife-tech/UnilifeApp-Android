package unilife.com.unilife.profile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import unilife.com.unilife.R

class AnswersAdapter(var context: Context, var substring: ArrayList<String>) : RecyclerView.Adapter<AnswersAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.answer_adapter, viewGroup, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return substring.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        if(substring[i].isNotEmpty()) {
            myViewHolder.tvanswer1.text = substring[i]
            myViewHolder.ivanswer_img1.visibility = View.VISIBLE
            myViewHolder.tvanswer1.visibility = View.VISIBLE
        }
    }


    class MyViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView){
            var ivanswer_img1 : ImageView = itemView.findViewById(R.id.ivanswer_img1)
            var tvanswer1 : TextView = itemView.findViewById(R.id.tvanswer1)

    }

}
