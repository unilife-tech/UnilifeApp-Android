package unilife.com.unilife.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import unilife.com.unilife.R
import unilife.com.unilife.model.CustomMediaModel

class AddPostMediaAdapter(val context: Context, var customMediaList: ArrayList<CustomMediaModel>? = ArrayList())
    : RecyclerView.Adapter<AddPostMediaAdapter.MyImagesHolder>() {


    lateinit var addPostMedia: AddPostMediaInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImagesHolder {
        val view= LayoutInflater.from(parent.context).
            inflate(R.layout.add_post_adapter,parent,false)
        return MyImagesHolder(view)
    }

    override fun onBindViewHolder(holder: MyImagesHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(customMediaList!![position].path)
            .into(holder.ivMedia)

//        if(bitmapList!!.size > 0 ) {
//            holder.ivMedia.setImageBitmap(customMediaList!!.get(position).bitmap)
//            holder.ivMedia.setImageBitmap(bitmapList!!.get(position))
//        }

        holder.llMain.setOnClickListener{
            addPostMedia.AddPostMedia(position)
        }


    }



    override fun getItemCount(): Int {
        return customMediaList!!.size
    }


    class MyImagesHolder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        var ivMedia = itemview.findViewById<ImageView>(R.id.ivMedia)
        var llMain = itemview.findViewById<LinearLayout>(R.id.llMain)

    }


    public fun getAddPostImagesVideos(addPostMedia: AddPostMediaInterface)
    {
        this.addPostMedia=addPostMedia
    }


    public interface AddPostMediaInterface
    {
        public  fun AddPostMedia(position:Int)
    }



}