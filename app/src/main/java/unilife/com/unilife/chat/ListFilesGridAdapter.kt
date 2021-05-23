package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls

class ListFilesGridAdapter(var context: Context, mediaList: ArrayList<ListAllFiles.ViewMediaData>) :
    RecyclerView.Adapter<ListFilesGridAdapter.MyViewHolder>() {

    var mediaList: ArrayList<ListAllFiles.ViewMediaData> = ArrayList()
    lateinit var onItemClick: onItemClickListener
    var idList : ArrayList<String> = ArrayList(5)
    var filepath : ArrayList<String> = ArrayList(5)
    var fileName : ArrayList<String> = ArrayList(5)



    init {
        this.mediaList = mediaList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_files_gridadapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.tvGrid.text = mediaList[i].message

        if (mediaList[i].message_type == "image") {
            Picasso.with(context).load(WebUrls.CHAT_MEDIA_URL + mediaList[i].message)
                .placeholder(R.drawable.no_image).into(myViewHolder.ivGrid)
        } else {
            Picasso.with(context).load(WebUrls.CHAT_MEDIA_URL + mediaList[i].thumb)
                .placeholder(R.drawable.no_image).into(myViewHolder.ivGrid)
        }

        myViewHolder.ivGrid.setOnClickListener {
            context.startActivity(
                Intent(context, FullMediaScreen::class.java)
                    .putExtra("message", mediaList[i].message)
                    .putExtra("message_type", mediaList[i].message_type)
                    .putExtra("file_type", mediaList[i].message_type)
            )
        }



        myViewHolder.chkbox.setOnClickListener {
            if (myViewHolder.chkbox.isChecked) {
                idList.add(mediaList[i].id)
                myViewHolder.layoutchk.visibility = View.VISIBLE


                filepath.add(WebUrls.CHAT_MEDIA_URL + mediaList[i].message)
                fileName.add(mediaList[i].message)



                onItemClick.onCheckBoxClick(i,idList,filepath,fileName,mediaList[i].message_type)


            } else {
                myViewHolder.layoutchk.visibility = View.GONE
                idList.remove(mediaList[i].id)

                filepath.remove(WebUrls.CHAT_MEDIA_URL + mediaList[i].message)
                fileName.remove(mediaList[i].message)

                onItemClick.onCheckBoxClick(i, idList, filepath, fileName, mediaList[i].message_type)
            }


        }


/*        myViewHolder.chkbox.setOnCheckedChangeListener { buttonView, isChecked ->

            if(myViewHolder.layoutchk.isShown){
            myViewHolder.layoutchk.visibility = View.GONE
        }
            else{
                myViewHolder.layoutchk.visibility = View.VISIBLE
            }


        }*/

    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivclose: ImageView = itemView.findViewById(R.id.ivclose)
        var ivGrid: ImageView = itemView.findViewById(R.id.ivGrid)
        var tvGrid: TextView = itemView.findViewById(R.id.tvGrid)
        var chkbox: CheckBox = itemView.findViewById(R.id.chkbox)
        var layoutchk: ConstraintLayout = itemView.findViewById(R.id.layoutchk)
    }

    public fun setOnItemClickListener(onItemClick: onItemClickListener) {
        this.onItemClick = onItemClick
    }

    public interface onItemClickListener {
        public fun onCheckBoxClick(
            i: Int,
            idList: ArrayList<String>,
            filepath: ArrayList<String>,
            filename: ArrayList<String>,
            message_type: String
        )
    }

}
