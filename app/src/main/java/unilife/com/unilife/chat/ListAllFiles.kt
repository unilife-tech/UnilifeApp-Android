package unilife.com.unilife.chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_list_all_files.*
import kotlinx.android.synthetic.main.back_icon_toolbar.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.R
import unilife.com.unilife.utils.Alerts
import unilife.com.unilife.utils.Common
import unilife.com.unilife.retrofit.RetrofitResponse
import unilife.com.unilife.retrofit.RetrofitService
import unilife.com.unilife.retrofit.WebUrls
import java.io.File
import java.lang.Exception

class ListAllFiles : AppCompatActivity(),RetrofitResponse {
    var mediaList : ArrayList<ViewMediaData> = ArrayList()
    var value = ""
    var listfilesgridAdapter : ListFilesGridAdapter? = null
    var listFilesAdapter : ListFilesAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all_files)

        getIntentData()


        ivBackArrow.setOnClickListener {
            onBackPressed()
        }
        ivNotification.visibility = View.GONE



        if (value == "audio") {
            tvTitle.text = "Audio"
            ViewMultimedialist()
        }
        if (value == "video") {
            tvTitle.text = "Video"
            ViewMultimedialist()
        }
        if (value == "image") {
            tvTitle.text = "Image"
            ViewMultimedialist()
        }
        if (value == "document") {
            tvTitle.text = "Document"
            ViewMultimedialist()
        }


    }

    override fun onBackPressed() {
        startActivity(Intent(this,ViewChatMultimedia::class.java))
        finish()
    }

    override fun onResponse(requestCode: Int, response: String) {
            try{
                when(requestCode){
                    WebUrls.VIEW_MULTIMEDIA_CODE->{
                        val obj = JSONObject(response)
                        mediaList.clear()
                        if(obj.getBoolean("response")){
                            var data = obj.getJSONArray("data")
                            if(data.length()>0){
                                for(i in 0 until data.length()){
                                    var dataobj = data.getJSONObject(i)
                                    var viewMediaData = ViewMediaData()
                                    viewMediaData.message=dataobj.getString("message")
                                    viewMediaData.id=dataobj.getString("id")
                                    viewMediaData.thumb=dataobj.getString("thumb")
                                    viewMediaData.filepath=dataobj.getString("filepath")
                                    viewMediaData.message_type=dataobj.getString("message_type")

                                    mediaList.add(viewMediaData)
                                }
                            }
                            else{
                                Common.customDialog(this,"Unilife","No data found")
                            }

                            if(mediaList[0].message_type == "audio" || mediaList[0].message_type == "document" ) {
                                Log.e("dgvjbgv",""+mediaList.size)
                                setAdapter(mediaList)
                            }
                            else{
                                setGridAdapter(mediaList)
                            }

                        }
                        else{
                            Common.customDialog(this,"Unilife","No data found")
                            setAdapter(mediaList)
                            setGridAdapter(mediaList)
                        }
                    }
                    WebUrls.DELETE_MULTIMEDIA_CODE->{
                            var obj = JSONObject(response)
                            if(obj.getBoolean("response")){
                            Common.customDialog(this,"Unilife","Media File Deleted.")
                            ViewMultimedialist()

                              listfilesgridAdapter!!.notifyDataSetChanged()
                              listFilesAdapter!!.notifyDataSetChanged()
                            }
                    }
                }
            }
            catch (e:Exception){

            }
    }

    private fun ViewMultimedialist() {
        if(Alerts.isNetworkAvailable(this)){
        try{
        var postparam = JSONObject()
        postparam.put("user_id",PreferenceFile.getInstance().getPreferenceData(this,WebUrls.KEY_USERID))
        postparam.put("message_type",value)

        RetrofitService(this,this,WebUrls.VIEW_MULTIMEDIA,WebUrls.VIEW_MULTIMEDIA_CODE,
            3,postparam).callService(true)
    }
        catch(e:Exception)
        {
            e.printStackTrace()
        }

    }
    else{
        Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
    }
}

    private fun getIntentData() {
        value = intent.getStringExtra("value")
    }

    fun setAdapter(mediaList: ArrayList<ViewMediaData>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_listfiles?.layoutManager = layoutManager
        listFilesAdapter = ListFilesAdapter(this,mediaList)
        rv_listfiles?.adapter = listFilesAdapter

        listFilesAdapter!!.setOnItemClickListener(object : ListFilesAdapter.onItemClickListener{
            override fun onCheckBoxClick(
                i: Int,
                idList: ArrayList<String>,
                filepath: ArrayList<String>,
                fileName: ArrayList<String>,
                message_type: String
            ) {

                if(idList.size>0){
                    lloptions.visibility=View.VISIBLE
                }
                else
                {
                    lloptions.visibility=View.GONE
                }
                Log.e("listsizebjbjk",""+idList.size)
                Log.e("listsizebjbjk",""+idList)

                llDelete.setOnClickListener {
                    callDeleteItemService(idList)
                }

                llMove.setOnClickListener {
                    if(idList.size<6) {
                        for (j in 0 until filepath.size) {
                            Log.e("sjksbbfsdfsfsdb", "" + filepath)
                            DownloadFile(this@ListAllFiles,message_type).execute(filepath[j], fileName[j])
                        }
                    }
                    else
                    {
                        Common.customDialog(this@ListAllFiles,"Unilife","You can only move max 5 files")
                    }


                }


            }
        })
    }

    fun setGridAdapter(mediaList: ArrayList<ViewMediaData>)
    {

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        rv_listfiles?.layoutManager = staggeredGridLayoutManager
        listfilesgridAdapter = ListFilesGridAdapter(this,mediaList)
        rv_listfiles?.adapter = listfilesgridAdapter

        listfilesgridAdapter!!.setOnItemClickListener(object : ListFilesGridAdapter.onItemClickListener{
            override fun onCheckBoxClick(
                i: Int,
                idList: ArrayList<String>,
                filepath: ArrayList<String>,
                fileName: ArrayList<String>,
                message_type: String
            ) {

                if(idList.size>0){
                    lloptions.visibility=View.VISIBLE
                }
                else
                {
                    lloptions.visibility=View.GONE
                }


                Log.e("listsizebjbjk",""+idList.size)
                Log.e("listsizebjbjk",""+idList)

                llDelete.setOnClickListener {
                    callDeleteItemService(idList)
                }

                llMove.setOnClickListener {
                    if(idList.size<6) {
                        for (j in 0 until filepath.size) {
                            Log.e("sjksbbfsdfsfsdb", "" + filepath)
                            DownloadFile(this@ListAllFiles, message_type).execute(filepath[j], fileName[j])
                        }
                    }
                    else
                    {
                        Common.customDialog(this@ListAllFiles,"Unilife","You can only move max 5 files")
                    }
                }


            }
        })
    }


    private fun callDeleteItemService(idList: ArrayList<String>) {
        if(Alerts.isNetworkAvailable(this)){
        try {
            Log.e("listsizebjbjk", "" + idList.size)
            if (idList.size < 6) {
                var postParam = JSONObject()
                postParam.put("sender_id", PreferenceFile.getInstance().getPreferenceData(this, WebUrls.KEY_USERID))
                postParam.put("media", idList.toString())

                RetrofitService(
                    this, this, WebUrls.DELETE_MULTIMEDIA, WebUrls.DELETE_MULTIMEDIA_CODE,
                    3, postParam
                ).callService(true)
            }
        else
        {
                Common.customDialog(this,"Unilife","You can only select max 5")
        }
    }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
        else{
            Common.customDialog(this, "Unilife",resources.getString(R.string.internet_offline))
        }
    }

    data class ViewMediaData(
        var id : String = "",
        var message : String = "",
        var thumb : String = "",
        var filepath : String = "",
        var message_type : String = ""
    )

    class DownloadFile(context: Context, message_type: String) : AsyncTask<String, Void, Void>() {
        var context:Context=context
        var value : String = message_type
        private var kProgressHUD: KProgressHUD?=null
        override fun doInBackground(vararg strings: String?): Void? {

            var fileUrl: String = strings[0]!!
            var fileName = strings[1]
            var extStorageDirectory: String = Environment.getExternalStorageDirectory().toString()

            var folder: File = File(extStorageDirectory, "Unilife files")
            folder.mkdir()

            var pdfFile: File = File(folder, fileName)
            try {
                pdfFile.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.e("FragmentURl","uhhj"+fileUrl)
            Log.e("pdfLOCATIONNNpath","ytyyd"+pdfFile)

            Common.download(fileUrl,pdfFile)
            return null



        }

        override fun onPreExecute() {
            super.onPreExecute()
            kProgressHUD= KProgressHUD(context)
            kProgressHUD!!.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            kProgressHUD!!.setCancellable(false)
            kProgressHUD!!.setAnimationSpeed(1)
            kProgressHUD!!.setLabel("Downloading")
            kProgressHUD!!.setDimAmount(0.2f)
            kProgressHUD!!.show()
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if(kProgressHUD!!.isShowing)
            {
                kProgressHUD!!.dismiss()

            }

            customDialog(context,"Unilife","File downloaded successfully",value)



        }

        fun customDialog(
            context: Context,
            title: String,
            msg: String,
            value: String
        ) {

            val dialogBuilder = AlertDialog.Builder(context)

            val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

            val tvTitle = dialogView.tvTitle
            val tvOk = dialogView.tvok
            val tvMsg = dialogView.tvMsg

            dialogBuilder.setView(dialogView)

            val alertDialog = dialogBuilder.create()
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = WindowManager.LayoutParams.WRAP_CONTENT
            val height = WindowManager.LayoutParams.WRAP_CONTENT
            alertDialog.window!!.setLayout(width, height)

            tvTitle.text = title
            tvMsg.text = msg

            tvOk.setOnClickListener(View.OnClickListener {
                context.startActivity(Intent(context,ListAllFiles::class.java)
                    .putExtra("value",value)
                )

                 alertDialog.dismiss()
            })

            alertDialog.setCancelable(true)
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.show()
        }
    }

}
