package unilife.com.unilife.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import unilife.com.unilife.R

class RecieveRequstAdapter(internal var context: Context) : RecyclerView.Adapter<RecieveRequstAdapter.myViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): myViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recive_request_adapter, viewGroup, false)

        return myViewHolder(itemView)
    }

    override fun onBindViewHolder(myViewHolder: myViewHolder, i: Int) {

    }


    override fun getItemCount(): Int {
        return 5
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


//        internal var ivuser: ImageView
//
//
//        init {
//            ivuser = itemView.findViewById(R.id.ivuser)
//
//
//            ivuser.setOnClickListener { context.startActivity(Intent(context, ChatInbox::class.java)) }
//        }


    }
}
