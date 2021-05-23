package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ListFilesAdapter(
    var context: Context,
    mediaList: ArrayList<ListAllFiles.ViewMediaData>
) : RecyclerView.Adapter<ListFilesAdapter.MyViewHolder>(){


    lateinit var onItemClick: onItemClickListener
    var idList : ArrayList<String> = ArrayList(5)
    var mediaList: ArrayList<ListAllFiles.ViewMediaData> = ArrayList()
    var filepath : ArrayList<String> = ArrayList(5)
    var fileName : ArrayList<String> = ArrayList(5)
    init {
        this.mediaList=mediaList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_files_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.e("sdxfjkbfjd",""+mediaList.size)
        return mediaList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {

        when {
            mediaList[i].message_type == "audio" -> myViewHolder.imgView.setImageResource(R.drawable.audio)
            mediaList[i].message_type == "video" -> myViewHolder.imgView.setImageResource(R.drawable.video)
            mediaList[i].message_type == "image" -> myViewHolder.imgView.setImageResource(R.drawable.img)
            else -> myViewHolder.imgView.setImageResource(R.drawable.doc)
        }

            if(mediaList[i].message!=null) {
                myViewHolder.txtView.text = mediaList[i].message
                Log.e("checkwhen", "" + mediaList[i].message)
            }
        else
            {
                myViewHolder.txtView.text = "dvkldfjgbvfgbfkn "
            }
        myViewHolder.txtView.setOnClickListener {
            context.startActivity(Intent(context,FullMediaScreen::class.java)
                .putExtra("message",mediaList[i].message)
                .putExtra("message_type",mediaList[i].message_type)
                .putExtra("file_type",mediaList[i].message_type)
            )
        }


        myViewHolder.chk.setOnClickListener {
            if (myViewHolder.chk.isChecked) {
                idList.add(mediaList[i].id)
                myViewHolder.layoutchk2.visibility = View.VISIBLE


                filepath.add(WebUrls.CHAT_MEDIA_URL + mediaList[i].message)
                fileName.add(mediaList[i].message)

                onItemClick.onCheckBoxClick(i,idList,filepath,fileName,mediaList[i].message_type)


            } else {
                myViewHolder.layoutchk2.visibility = View.GONE
                idList.remove(mediaList[i].id)

                filepath.remove(WebUrls.CHAT_MEDIA_URL + mediaList[i].message)
                fileName.remove(mediaList[i].message)

                onItemClick.onCheckBoxClick(i, idList, filepath, fileName, mediaList[i].message_type)
            }


        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var chk : CheckBox = itemView.findViewById(R.id.chk)
        var imgView : ImageView = itemView.findViewById(R.id.imgView)
        var txtView : TextView = itemView.findViewById(R.id.txtView)
        var layoutchk2 : ConstraintLayout = itemView.findViewById(R.id.layoutchk2)

    }
    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onCheckBoxClick(
            i: Int,
            idList: ArrayList<String>,
            filepath: ArrayList<String>,
            fileName: ArrayList<String>,
            message_type: String
        )
    }
}
