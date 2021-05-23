package unilife.com.unilife.chat.savedMultimedia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.audio_adapter_layout.view.*
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
import unilife.com.unilife.R

class AudioListAdapter(
    val context: Context,
    var audioList: ArrayList<ViewSavedMultimedia.ChatMultiMedia>
) : RecyclerView.Adapter<AudioListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.audio_adapter_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.cbSelectAudio.isChecked = audioList[position].isSelected
            holder.tvAudioName.text = audioList[position].message

            holder.cbSelectAudio.setOnCheckedChangeListener { buttonView, isChecked ->
                onClickChange!!.checkChange(position, isChecked)
            }
            holder.clAudio.setOnClickListener {
                onClickChange!!.onClickItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvAudioName = view.tvAudioName
        var cbSelectAudio = view.cbSelectAudio
        var clAudio = view.clAudio
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