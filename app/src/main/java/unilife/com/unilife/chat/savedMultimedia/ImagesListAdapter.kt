package unilife.com.unilife.chat.savedMultimedia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.images_adapter_layout.view.*
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ImagesListAdapter(
    val context: Context,
    var imagesList: ArrayList<ViewSavedMultimedia.ChatMultiMedia>
) : RecyclerView.Adapter<ImagesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.images_adapter_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.cbSelectImage.isChecked = imagesList[position].isSelected
            Picasso.with(context).load(WebUrls.CHAT_MEDIA_URL + imagesList[position].message)
                .placeholder(R.drawable.no_image).into(
                    holder.ivImageMultiMedia
                )
            holder.cbSelectImage.setOnCheckedChangeListener { buttonView, isChecked ->
                onClickChange!!.checkChange(position, isChecked)
            }
            holder.clImage.setOnClickListener {
                onClickChange!!.onClickItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivImageMultiMedia = view.ivImageMultiMedia
        var cbSelectImage = view.cbSelectImage
        var clImage = view.clImage
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