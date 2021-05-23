package unilife.com.unilife.chat.savedMultimedia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_adapter_layout.view.*
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class VideoListAdapter(
    val context: Context,
    var videoList: ArrayList<ViewSavedMultimedia.ChatMultiMedia>
) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.video_adapter_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.cbSelectVideo.isChecked = videoList[position].isSelected
            Picasso.with(context).load(WebUrls.CHAT_MEDIA_URL + videoList[position].thumb)
                .placeholder(R.drawable.no_image).into(
                    holder.ivVideoThumb
                )
            holder.cbSelectVideo.setOnCheckedChangeListener { buttonView, isChecked ->
                onClickChange!!.checkChange(position, isChecked)
            }
            holder.clVideo.setOnClickListener {
                onClickChange!!.onClickItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivVideoThumb = view.ivVideoThumb
        var cbSelectVideo = view.cbSelectVideo
        var clVideo = view.clVideo
    }

    interface OnClickChange {
        fun checkChange(position: Int, isChecked: Boolean)
        fun onClickItem(position: Int)
    }

    var onClickChange: OnClickChange? = null

    fun initOnClickChange(onClickChange: OnClickChange) {
        this.onClickChange = onClickChange
    }
}