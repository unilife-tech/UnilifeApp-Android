package unilife.com.unilife.chat.savedMultimedia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.docs_adapter_layout.view.*
import unilife.com.unilife.chat.groupdetails.ViewSavedMultimedia
import unilife.com.unilife.R

class DocsListAdapter(
    val context: Context,
    var docsList: ArrayList<ViewSavedMultimedia.ChatMultiMedia>
) : RecyclerView.Adapter<DocsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.docs_adapter_layout,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return docsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.cbSelectDoc.isChecked = docsList[position].isSelected
            holder.ivDocName.text = docsList[position].message
            holder.cbSelectDoc.setOnCheckedChangeListener { buttonView, isChecked ->
                onClickChange!!.checkChange(position, isChecked)
            }
            holder.clDoc.setOnClickListener {
                onClickChange!!.onClickItem(position)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cbSelectDoc = view.cbSelectDoc
        var ivDocName = view.ivDocName
        var clDoc = view.clDoc
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

