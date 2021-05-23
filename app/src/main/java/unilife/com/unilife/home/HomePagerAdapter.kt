package unilife.com.unilife.home

import android.content.Context
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.devbrackets.android.exomedia.ui.widget.VideoView
import com.google.android.exoplayer2.*
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout
import com.squareup.picasso.Picasso
import unilife.com.unilife.R
import unilife.com.unilife.retrofit.WebUrls
import java.lang.Exception


class HomePagerAdapter(
    var context: Context,
    var postImagesArraylist: ArrayList<HomeModel.PostAttachments>
) : PagerAdapter(),Player.EventListener
{
    private lateinit var view: View
    private lateinit var viewpager_picture: ImageView
    private lateinit var video_view: VideoView
    private lateinit var btnplayvideo: ImageView
    private lateinit var llPostPlayVideo: RoundKornerLinearLayout
    private lateinit var llPostStopVideo: RoundKornerLinearLayout


    val SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode"
    val SPHERICAL_STEREO_MODE_MONO = "mono"
    val SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom"
    val SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right"

    init {
        this.context = context
        this.postImagesArraylist=postImagesArraylist
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return postImagesArraylist.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        try {

            view = LayoutInflater.from(container.context)
                .inflate(R.layout.homepageslider, container, false)

            viewpager_picture = view.findViewById(R.id.viewpager_picture)
            video_view = view.findViewById(R.id.video_view)
            llPostPlayVideo = view.findViewById(R.id.llPostPlayVideo)
            llPostStopVideo = view.findViewById(R.id.llPostStopVideo)
            btnplayvideo = view.findViewById(R.id.btnplayvideo)

            when (postImagesArraylist[position].attachment_type) {

                "video" -> {

                    viewpager_picture.visibility = View.VISIBLE
                    video_view.visibility = View.GONE
                    llPostPlayVideo.visibility = View.GONE
                    btnplayvideo.visibility = View.VISIBLE

                    Glide.with(context)
                        .load(WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment)
                        .into(viewpager_picture)


/*
                Picasso.with(context)
                    .load(WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment)
                    .placeholder(R.drawable.no_image)
                    .into(viewpager_picture)*/

                    btnplayvideo.setOnClickListener {
                        var intent = Intent(context, FullViewMedia::class.java)
                        intent.putExtra("data", postImagesArraylist[position].attachment)
                        intent.putExtra("datatype", postImagesArraylist[position].attachment_type)
                        context.startActivity(intent)
                    }

                    /*          viewpager_picture.visibility = View.GONE
                          video_view.visibility = View.VISIBLE
                          video_view.setVideoURI(Uri.parse(WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment))
                          Log.e("videolink",WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment)

                          video_view.setScaleType(ScaleType.FIT_XY)
                          video_view.setOnPreparedListener {
                              Log.e("ISPrepared", "Yes")

                              //  llPostPlayVideo.visibility=View.VISIBLE

                          }*/


                }

                "image" -> {

                    viewpager_picture.visibility = View.VISIBLE
                    video_view.visibility = View.GONE
                    llPostPlayVideo.visibility = View.GONE


//                Glide.with(context)
//                    .load(WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment)
//                    .into(viewpager_picture)

                    Picasso.with(context)
                        .load(WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment)
                        .placeholder(R.drawable.no_image)
                        .resize(400, 300)
                        .into(viewpager_picture)


                    viewpager_picture.setOnClickListener {
                        val intent = Intent(context, FullViewMedia::class.java)
                        intent.putExtra("data", postImagesArraylist[position].attachment)
                        intent.putExtra("datatype", postImagesArraylist[position].attachment_type)
                        context.startActivity(intent)

                    }
//                    Log.e(
//                        "URL",
//                        "IMAGE : " + WebUrls.POST_IMAGE_URL + postImagesArraylist[position].attachment
//                    )

                }
                else -> {
                    viewpager_picture.visibility = View.GONE
                    video_view.visibility = View.GONE
                    llPostPlayVideo.visibility = View.GONE

                }
            }

            container.addView(view)
        }catch (e : Exception){
            e.printStackTrace()
        }

        return view

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

}





/*
package unilife.com.unilife.Home

import android.content.Context
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.devbrackets.android.exomedia.ui.widget.VideoView
import com.google.android.exoplayer2.*
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout
import unilife.com.unilife.R


class HomePagerAdapter(
    var context: Context,
    var postImagesArraylist: ArrayList<HomeModel.PostAttachments>
       ) : PagerAdapter(),Player.EventListener
{
    private lateinit var view: View
    private lateinit var viewpager_picture: ImageView
    private lateinit var video_view: VideoView
    private lateinit var llPostPlayVideo: RoundKornerLinearLayout
    private lateinit var llPostStopVideo: RoundKornerLinearLayout


    val SPHERICAL_STEREO_MODE_EXTRA = "spherical_stereo_mode"
    val SPHERICAL_STEREO_MODE_MONO = "mono"
    val SPHERICAL_STEREO_MODE_TOP_BOTTOM = "top_bottom"
    val SPHERICAL_STEREO_MODE_LEFT_RIGHT = "left_right"

    init {

        this.context = context
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return postImagesArraylist.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        view = LayoutInflater.from(container.context)
            .inflate(R.layout.homepageslider, container, false)
        viewpager_picture = view.findViewById(R.id.viewpager_picture)
        video_view = view.findViewById(R.id.video_view)
        llPostPlayVideo = view.findViewById(R.id.llPostPlayVideo)
        llPostStopVideo = view.findViewById(R.id.llPostStopVideo)


//        Log.e("PostUrl",WebUrls.POST_IMAGES_URL+postImagesArraylist[position].postName)
//        Log.e("FileType",postImagesArraylist[position].file_type)

*/
/*
        if(postImagesArraylist[position].file_type == "video")
        {
            viewpager_picture.visibility=View.GONE
            video_view.visibility=View.VISIBLE
//            //todo av
//            video_view.setVideoURI(Uri.parse(WebUrls.POST_IMAGES_URL+postImagesArraylist[position].postName))

            video_view.setOnPreparedListener {
                Log.e("ISPrepared","Yes")

              //  llPostPlayVideo.visibility=View.VISIBLE

            }


*//*
*/
/*
            llPostPlayVideo.setOnClickListener {
                llPostPlayVideo.visibility=View.GONE
                llPostStopVideo.visibility=View.VISIBLE

                    video_view.start()

            }
*//*
*/
/*



*//*
*/
/*
            llPostStopVideo.setOnClickListener {
                llPostPlayVideo.visibility=View.VISIBLE
                llPostStopVideo.visibility=View.GONE
                video_view.pause()
            }
*//*
*/
/*

            video_view.setOnCompletionListener {
*//*
*/
/*
                llPostPlayVideo.visibility=View.VISIBLE
                llPostStopVideo.visibility=View.GONE
*//*
*/
/*
            }

        }
        else
        {
            viewpager_picture.visibility=View.VISIBLE
            video_view.visibility=View.GONE
            llPostPlayVideo.visibility=View.GONE


         *//*
*/
/*
         //todo av
          Glide.with(context)
                .load(WebUrls.POST_IMAGES_URL + postImagesArraylist!![position].postName)
                .placeholder(bubblebud.com.bubblebud.R.drawable.ic_profile_user)
                .into(viewpager_picture)*//*
*/
/*


        }*//*


        container.addView(view)
        return view


    }




    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }



}*/
