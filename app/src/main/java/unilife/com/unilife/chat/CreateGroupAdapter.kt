package unilife.com.unilife.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import unilife.com.unilife.R

class CreateGroupAdapter : RecyclerView.Adapter<CreateGroupAdapter.myViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.chat_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

    }


    override fun getItemCount(): Int {
        return 5
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
