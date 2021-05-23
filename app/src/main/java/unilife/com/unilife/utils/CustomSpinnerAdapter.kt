package unilife.com.unilife.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import unilife.com.unilife.R
import unilife.com.unilife.model.InfoModel


class CustomSpinnerAdapter(private val context: Context, modelArrayList: ArrayList<InfoModel>) : BaseAdapter() {

    private var modelArrayList: ArrayList<InfoModel>

    init {
        this.modelArrayList = modelArrayList
    }

    override fun getViewTypeCount(): Int {
//        return count
        return 1
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return modelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return modelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {

            holder = ViewHolder()

            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.custom_spitem, null, true)

            holder.checkBox = convertView!!.findViewById(R.id.checkbox1) as CheckBox

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.checkBox!!.text = modelArrayList[position].name
//        holder.checkBox!!.text = "Checkbox $position"

        holder.checkBox!!.isChecked = modelArrayList[position].getSelecteds()

        holder.checkBox!!.setTag(R.integer.btnplusview, convertView)
        holder.checkBox!!.tag = position

        holder.checkBox!!.setOnClickListener {
            val tempview = holder.checkBox!!.getTag(R.integer.btnplusview) as View

            val pos = holder.checkBox!!.tag as Int

            Toast.makeText(context, "Checkbox $pos clicked!IDS:>>"+modelArrayList[pos].id, Toast.LENGTH_SHORT).show()

            if (modelArrayList[pos].getSelecteds()) {
                modelArrayList[pos].setSelecteds(false)
                public_modelArrayList = modelArrayList
            } else {
                modelArrayList[pos].setSelecteds(true)
                public_modelArrayList = modelArrayList
            }
        }

        return convertView
    }

    private inner class ViewHolder {

        var checkBox: CheckBox? = null

    }

    companion object {
        lateinit var public_modelArrayList: ArrayList<InfoModel>
    }

}