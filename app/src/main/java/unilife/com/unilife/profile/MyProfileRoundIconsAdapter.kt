package unilife.com.unilife.profile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import unilife.com.unilife.R
import java.lang.Exception

class MyProfileRoundIconsAdapter() : RecyclerView.Adapter<MyProfileRoundIconsAdapter.RoundIconViewHolder>() {
    lateinit var context: Context
    lateinit var categoriesdata:ArrayList<CategoriesModel>
    lateinit var iconsList:ArrayList<Int>

    constructor(context : Context, categoriesdata: ArrayList<CategoriesModel>) : this(){

        this.context=context
        this.categoriesdata=categoriesdata

    }

    constructor(context : Context, categoriesdata: ArrayList<CategoriesModel>,  iconsList: ArrayList<Int>
    ) : this(){

        this.context=context
        this.categoriesdata=categoriesdata
        this.iconsList=iconsList

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RoundIconViewHolder {

        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.roundicon_itemlist, p0, false)
        return RoundIconViewHolder(itemView)
    }

    override fun getItemCount(): Int {
      return categoriesdata.size

    }

    override fun onBindViewHolder(roundIconViewHolder:RoundIconViewHolder, p1: Int) {

        try {

            roundIconViewHolder.tv_ri_title?.text = categoriesdata[p1].categoryname
            roundIconViewHolder.riv_icon!!.setImageResource(iconsList[p1])
        }catch (e : Exception ){
            e.printStackTrace()
        }

    }


    inner class RoundIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        internal var tv_ri_title : TextView?=null
        internal var riv_icon : ImageView?=null


        init {

           tv_ri_title=itemView.findViewById(R.id.tv_ri_title)
            riv_icon=itemView.findViewById(R.id.riv_icon)

        }
    }
}