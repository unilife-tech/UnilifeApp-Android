package unilife.com.unilife.chat.groupdetails

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.selected_participant_layout.view.*
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class SelectedParticipantAdapter(
    val context: Context,
    var selectedFriendsList: ArrayList<AddParticipant.ShowFriendListModel>
) : RecyclerView.Adapter<SelectedParticipantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.selected_participant_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return selectedFriendsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        try {
            Picasso.with(context)
                .load(WebUrls.PROFILE_IMAGE_URL + selectedFriendsList[i].profile_image)
                .placeholder(R.drawable.no_image).into(holder.ivImg)

            holder.tvName.text = selectedFriendsList[i].username

            holder.llCross.setOnClickListener {
                clickSelected!!.removeSelected(i)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivImg = view.ivImg
        var llCross = view.llCross
        var tvName = view.tvName
    }

    interface ClickSelected {
        fun removeSelected(position: Int)
    }

    var clickSelected: ClickSelected? = null

    fun initClickSelected(clickSelected: ClickSelected) {
        this.clickSelected = clickSelected
    }

}