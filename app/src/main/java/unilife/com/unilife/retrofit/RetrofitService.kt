package unilife.com.unilife.retrofit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import unilife.com.unilife.R
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.TimeUnit


 class RetrofitService() {


    private var mContext: Context? = null
    private var mUrl:String? = null
    private var mRequestCode:Int = 0
    private var mValue:Int = 0
    private var mCall: Call<ResponseBody>? = null
    private var mResponse:RetrofitResponse? = null
    private val mPart2: MultipartBody.Part? = null
    private lateinit var mPart1: MultipartBody.Part
    private var mMap: HashMap<String, RequestBody>? = null
    private lateinit var mJsonObject: JSONObject
    private var progressDialog: Dialog? = null
    internal lateinit var retrofit: Retrofit
    private lateinit var mFiles: ArrayList<MultipartBody.Part>
    private val TAG = RetrofitService::class.java?.simpleName
       constructor(context: Context, response:RetrofitResponse, url:String, requestCode:Int, value:Int) : this() {
 //For Get Url
            mContext = context
            mResponse = response
            mUrl = url
            mRequestCode = requestCode
            mValue = value

       }




     constructor(context: Context, response:RetrofitResponse, url:String, requestCode:Int, value:Int,
                         map: HashMap<String, RequestBody>, part1: MultipartBody.Part) : this() {
           mContext = context
           mResponse = response
           mUrl = url
           mRequestCode = requestCode
           mValue = value
           mMap = map
           mPart1 = part1
           Log.e(TAG, "RetrofitService: erfgdg")

     }

     constructor(context: Context, response:RetrofitResponse, url:String, requestCode:Int, value:Int,
                         map: HashMap<String, RequestBody>/*, MultipartBody.Part part1*/) : this() {

         mContext = context
         mResponse = response
         mUrl = url
         mRequestCode = requestCode
         mValue = value
         mMap = map

 //        mPart1 = part1;
    }

     constructor(context: Context, response:RetrofitResponse, url:String, requestcode:Int, value:Int,
                         postParam: JSONObject
     ) : this()//For Post Url  --- pass 3
     {

         mContext = context
         mResponse = response
         mUrl = url
         mRequestCode = requestcode
         mValue = value
         mJsonObject = postParam

     }


    //For multipart multiple files
     constructor(context: Context, response:RetrofitResponse, url:String, requestCode:Int, value:Int,
                         map: HashMap<String, RequestBody>, files: ArrayList<MultipartBody.Part>
    ) : this() {
        mContext = context
        mResponse = response
        mUrl = url
        mRequestCode = requestCode
        mValue = value
        mMap = map
        mFiles = files

    }
     constructor(context: Context, response:RetrofitResponse, url:String, requestCode:Int, value:Int,
                  files: ArrayList<MultipartBody.Part>
     ) : this() {
         mContext = context
         mResponse = response
         mUrl = url
         mRequestCode = requestCode
         mValue = value
         mFiles = files

     }


     fun callService(ProgressDialog:Boolean) {

         try
         {

             progressDialog = Dialog(mContext!!)
             progressDialog!!.setCancelable(false)
             progressDialog!!.setCanceledOnTouchOutside(false)
             progressDialog!!.setContentView(R.layout.progress_dialog)
             progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)

             if (ProgressDialog)
             {
               progressDialog!!.show()

             }

             val okHttpClient = OkHttpClient.Builder()
                 .readTimeout(5, TimeUnit.MINUTES)
                 .connectTimeout(5, TimeUnit.MINUTES)
                 .build()

             /*            if(mUrl.contains("store/storeItemCategories?store_id=")){

                             retrofit = new Retrofit.Builder()
                                     .baseUrl(WebUrls.GROCERY_BASE_URL)
                                     .client(okHttpClient)
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .build();


                         }

                         else {*/

             retrofit = Retrofit.Builder()
                 .baseUrl(WebUrls.BASE_URL)
                 .client(okHttpClient)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()

             //            }


             val retrofitApi = retrofit.create(RetrofitApi::class.java!!)

             Log.e(TAG, mUrl)
             if (mValue == 1)
             {
                 mCall = mUrl?.let { retrofitApi.callGet(it) }
//
             }
             else if (mValue == 2)
             {
                 mCall = mUrl?.let { mMap?.let { it1 -> retrofitApi.callMultipart(it, it1, mPart1) } }
//                 Log.e(TAG, "22222" + mUrl!!)
//                 Log.e(TAG, "2222" + mMap!!.toString())
             }
             else if (mValue == 3)
             {
//                 Log.e(TAG, mUrl)
                 Log.e(TAG, mJsonObject.toString())
                 mCall = mUrl?.let {
                     retrofitApi.callPost(it, RequestBody.create(MediaType.parse("application/json"), mJsonObject.toString())) }
             }
             else if (mValue == 4)
             {
                 Log.e(TAG, mUrl)
                 Log.e(TAG, mMap!!.toString())
                 mCall = mUrl?.let { retrofitApi.callMultipartList(it, mMap!!, mFiles) }
             }
             else if (mValue == 5)
             {
                 Log.e(TAG, mUrl)
                 Log.e(TAG, mMap!!.toString())
                 mCall = mUrl?.let { retrofitApi.callMultipartOne(it, mMap!!,mPart1) }
             }
             else if (mValue == 6)
             {
                 Log.e(TAG, mUrl)
                 Log.e(TAG, mJsonObject.toString())
                 mCall = mUrl?.let {
                     retrofitApi.callPatch(
                         it, RequestBody.create(
                             MediaType.parse("application/json"),
                             mJsonObject.toString()))
                 }
             }
             else if (mValue == 7)
             {
                 Log.e(TAG, mUrl)
                 mCall = mUrl?.let { retrofitApi.callMultipartList1(it, mFiles) }
             }

             mCall!!.enqueue(object: Callback<ResponseBody> {
                 override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                     if (response.isSuccessful)
                     {
                         try
                         {
                             val res = response.body()!!.string()
                             mResponse?.onResponse(mRequestCode, res)
//                             Log.e(TAG, "response$res")
                         }
                         catch (e: IOException) {
                             e.printStackTrace()
                         }

                     }
                     else
                     {
                         Log.e(TAG, "onResponse: " + response.code())
                     }

                     try
                     {
                         if (progressDialog!!.isShowing)
                         {
                             progressDialog!!.cancel()
                         }
                     }
                     catch (e:Exception) {
                         e.printStackTrace()
                     }

                 }

                 override fun onFailure(call: Call<ResponseBody>, t:Throwable) {

                     try
                     {
                         if (progressDialog!!.isShowing)
                         {
                             progressDialog!!.cancel()
                         }
                     }
                     catch (e:Exception) {

                     }

                     //                    alertOnTimeOut(mCall, this, mContext.getResources().getString(R.string.connection_timeout));
                 }
             })
         }
         catch (e:Exception) {
             e.printStackTrace()
         }

     }


       fun callMsgService(ProgressDialog:Boolean) {
           try
           {

               progressDialog = Dialog(mContext!!)
               progressDialog?.setCancelable(false)
               progressDialog?.setCanceledOnTouchOutside(false)
               progressDialog?.setContentView(R.layout.progress_dialog)
               progressDialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)

               if (ProgressDialog)
               {
                   progressDialog!!.show()
               }

               val okHttpClient = OkHttpClient.Builder()
                   .readTimeout(5, TimeUnit.MINUTES)
                   .connectTimeout(5, TimeUnit.MINUTES)
                   .build()

               val retrofit = Retrofit.Builder()
                   .baseUrl(WebUrls.MSG_BASE_URL)
                   .client(okHttpClient)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()


               val retrofitApi = retrofit.create(RetrofitApi::class.java!!)

               if (mValue == 1)
               {
                   mCall = mUrl?.let { retrofitApi.callGet(it) }
                   Log.e("url ", mUrl)
               }
               else if (mValue == 2)
               {
                   mCall = mUrl?.let { mMap?.let { it1 -> retrofitApi.callMultipart(it, it1, mPart1) } }
                   Log.e("url ", mUrl)
                   Log.e("params>>", mMap!!.toString())
               }
               else if (mValue == 3)
               {
                   Log.e("url ", mUrl)
                   Log.e("params ", mJsonObject.toString())
                   mCall = mUrl?.let { retrofitApi.callPost(it, RequestBody.create(MediaType.parse("application/json"), mJsonObject.toString())) }
               }
               else if (mValue == 4)
               {
                   Log.e("url ", mUrl)
                   Log.e("params ", mMap!!.toString())
                   mCall = mUrl?.let { retrofitApi.callMultipartList(it, mMap!!, mFiles) }
               }
               else if (mValue == 5)
               {
                   Log.e("url>>>", mUrl)
                   Log.e("params>>>>", mMap!!.toString())
                   mCall = mUrl?.let { retrofitApi.callMultipartOne(it, mMap!!, mPart1) }
               }
               else if (mValue == 6)
               {
                   Log.e("url ", mUrl)
                   Log.e("params ", mJsonObject.toString())
                   mCall = mUrl?.let { retrofitApi.callPatch(it, RequestBody.create(MediaType.parse("application/json"), mJsonObject.toString())) }
               }
               mCall!!.enqueue(object: Callback<ResponseBody> {
                   override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                       if (response.isSuccessful)
                       {
                           try
                           {
                               val res = response.body()!!.string()
                               mResponse!!.onResponse(mRequestCode, res)
                               //                            Log.e("response", "" + res);
                           }
                           catch (e: IOException) {
                               e.printStackTrace()
                           }

                       }
                       else
                       {
                           Log.e("RetrofitService", "onResponse: " + response.code())
                       }

                       try
                       {
                           if (progressDialog!!.isShowing)
                           {
                               progressDialog!!.cancel()
                           }
                       }
                       catch (e:Exception) {
                           e.printStackTrace()
                       }

                   }

                   override fun onFailure(call: Call<ResponseBody>, t:Throwable) {

                       try
                       {
                           if (progressDialog!!.isShowing)
                           {
                               progressDialog!!.cancel()
                           }
                       }
                       catch (e:Exception) {

                       }

                       Log.e("onFailure ", "onFailure")
                       alertOnTimeOut(mCall, this, mContext!!.resources.getString(R.string.connection_timeout))
                   }
               })
           }
           catch (e:Exception) {
               e.printStackTrace()
           }

       }


       private fun alertOnTimeOut(call: Call<ResponseBody>?, callback: Callback<ResponseBody>, message:String) {

           try
           {
               val alertDialog = AlertDialog.Builder(mContext)
               alertDialog.setMessage(message)

               alertDialog.setPositiveButton(mContext!!.resources.getString(R.string.retry)
               ) { dialog, which ->
                   progressDialog!!.show()

                   call!!.clone().enqueue(callback)
               }

               alertDialog.setNegativeButton(mContext!!.resources.getString(R.string.cancel), object: DialogInterface.OnClickListener {
                   override fun onClick(dialog: DialogInterface, which:Int) {
                       dialog.dismiss()
                   }
               })

               val dialog = alertDialog.create()
               dialog.setCancelable(false)
               dialog.setCanceledOnTouchOutside(false)
               dialog.show()
           }
           catch (e:Exception) {
               e.printStackTrace()

           }


       }

   }