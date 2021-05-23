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

class HobbiesAdapter() : RecyclerView.Adapter<HobbiesAdapter.HobbiesViewHolder>() {
    lateinit var context: Context
    lateinit var hobbiesdata:ArrayList<MyProfileModel2>

    constructor(context :Context,hobbiesdata: ArrayList<MyProfileModel2>) : this(){

        this.context=context
        this.hobbiesdata=hobbiesdata

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HobbiesViewHolder {
        val itemView = LayoutInflater.from(p0.context).inflate(R.layout.hobbies_itemlist, p0, false)
        return HobbiesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return hobbiesdata.size
    }

    override fun onBindViewHolder(hobbiesViewHolder: HobbiesViewHolder, p1: Int) {
        hobbiesViewHolder.tv_hobbies_title?.text=hobbiesdata[p1].hobbiesname
        hobbiesViewHolder.tv_hobbies_title
        Picasso.with(context).load(WebUrls.MYPROFILE_ICON+hobbiesdata[p1].hobbiesicon).
            placeholder(R.drawable.no_image).into(hobbiesViewHolder.hobbies_icon)

    }


    inner class HobbiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv_hobbies_title: TextView? = null
       var hobbies_icon: ImageView

        init {
            tv_hobbies_title = itemView.findViewById(R.id.tvhobbies_itemlist)
            hobbies_icon= itemView.findViewById(R.id.hobby_icon)
        }
    }
}