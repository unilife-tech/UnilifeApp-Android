package unilife.com.unilife.chat.groupdetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.add_participant_adapter.view.*
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class RemoveParticipantsListAdapter(
    val context: Context,
    var membersList: ArrayList<GroupDetail.GroupUserModel>?
) :
    RecyclerView.Adapter<RemoveParticipantsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.add_participant_adapter,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return membersList!!.size
    }

    override fun onBindViewHolder(myViewHolder: ViewHolder, position: Int) {
        try {

            myViewHolder.tvAction.visibility = View.VISIBLE
            if (membersList!![position].user_id == PreferenceFile.getInstance().getPreferenceData(
                    context,
                    WebUrls.KEY_USERID
                )
            ) {
                myViewHolder.ivImg.visibility = View.GONE
                myViewHolder.tvAction.visibility = View.GONE
                myViewHolder.tvName.visibility = View.GONE
                myViewHolder.vLine.visibility = View.GONE
            } else {
                myViewHolder.ivImg.visibility = View.VISIBLE
                myViewHolder.tvAction.visibility = View.VISIBLE
                myViewHolder.tvName.visibility = View.VISIBLE
                myViewHolder.vLine.visibility = View.VISIBLE
            }
            if (membersList!![position].profile_image != null) {
                Picasso.with(context)
                    .load(WebUrls.PROFILE_IMAGE_URL + membersList!![position].profile_image)
                    .placeholder(R.drawable.no_image).into(myViewHolder.ivImg)

            } else {
                myViewHolder.ivImg.setImageResource(R.drawable.no_image)
            }

            myViewHolder.tvName.text = membersList!![position].username
            myViewHolder.tvAction.visibility = View.GONE

            if (membersList!![position].check_req_status == "0") { //adding
                myViewHolder.llTick.visibility = View.GONE

                myViewHolder.tvAction.text = "Remove"
                myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_dark_grey)

            } else if (membersList!![position].check_req_status == "1") { //selected one
                myViewHolder.llTick.visibility = View.VISIBLE

                myViewHolder.tvAction.text = "Selected"
                myViewHolder.tvAction.setBackgroundResource(R.drawable.radius_color_primary)
            }


            myViewHolder.rlMain.setOnClickListener {
                selectMembers!!.onPerformAction(position)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivImg = view.ivImg
        var tvName = view.tvName
        var tvAction = view.tvAction
        var vLine = view.vLine
        var llTick = view.llTick
        var rlMain = view.rlMain
    }

    fun setOnItemClickListener(selectMembers: SelectMembers) {
        this.selectMembers = selectMembers
    }

    var selectMembers: SelectMembers? = null

    interface SelectMembers {
        fun onPerformAction(
            position: Int
        )

    }

}