package unilife.com.unilife

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import unilife.com.unilife.model.UniModel
import java.util.*

class UniAdapter(var context: Context,var uniList: ArrayList<UniModel>) : RecyclerView.Adapter<UniAdapter.myViewHolder>() {

    var tempList: ArrayList<UniModel>
    lateinit var clickListener: ClickListener
    private var selectedPos = -1

    init {
        tempList = ArrayList<UniModel>()
        tempList.clear()
        tempList.addAll(uniList)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.uni_adapter, viewGroup, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

        try{
            myViewHolder.tvTitle.text = uniList.get(i).name
            if (uniList.get(i).isSelected) {
                selectedPos = i
                myViewHolder.ivSelected.visibility = VISIBLE
            }else{
                myViewHolder.ivSelected.visibility = GONE
            }
            myViewHolder.clMain.setOnClickListener {
//                if (selectedPos != -1) {
//                    uniList.get(selectedPos).isSelected=false
//                }
//                uniList.get(i).isSelected=true
                clickListener.onItemClick(i)
                notifyDataSetChanged()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return uniList.size
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        var ivSelected : ImageView = itemView.findViewById(R.id.ivSelected)
        var clMain : ConstraintLayout = itemView.findViewById(R.id.clMain)
    }

    fun filter(text: String) {
        var text = text
        uniList.clear()
        if (text.isEmpty()) {
            uniList.addAll(tempList)
        } else {
            text = text.toLowerCase(Locale.US)
            for (item in tempList) {
                if (item.name.toLowerCase(Locale.US).startsWith(text)) {
                    uniList.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }


    interface ClickListener {
        fun onItemClick(pos: Int)
    }

    fun onSetItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }
}
