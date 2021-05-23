package unilife.com.unilife.profile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class InterestAdapter() : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {

    lateinit var context: Context
    lateinit var interestdata:ArrayList<InterestModel>

    constructor(context :Context,interestdata: ArrayList<InterestModel>) : this(){

        this.context=context
        this.interestdata=interestdata

    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): InterestViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.hobbies_itemlist, p0, false)
        return InterestViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return interestdata.size
    }

    override fun onBindViewHolder(interestViewHolder: InterestViewHolder, p1: Int) {
        /*interestViewHolder.interest_title?.visibility=View.INVISIBLE*/
        Picasso.with(context).load(WebUrls.MYPROFILE_ICON+interestdata[p1].interesticon)
            .placeholder(R.drawable.no_image).into(interestViewHolder.interest_icon)
        interestViewHolder.interest_title?.text = interestdata[p1].interestname

    }


    inner class InterestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var interest_icon: ImageView? = null
        internal var interest_title: TextView? = null
        init {
            interest_icon=itemView.findViewById(R.id.hobby_icon)
            interest_title=itemView.findViewById(R.id.tvhobbies_itemlist)
        }
    }

}
