package unilife.com.unilife.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.provider.MediaStore
import android.text.format.Formatter
import android.util.Log


import android.content.Context.WIFI_SERVICE
import android.database.Cursor
import android.database.DatabaseUtils
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.shockwave.pdfium.PdfiumCore
import kotlinx.android.synthetic.main.custom_dialog.view.*
import unilife.com.unilife.R
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.ceil


object Common {


    private var density = 1f

    fun dp(value: Float, context: Context): Int {
        if (density == 1f) {
            checkDisplaySize(context)
        }
        return if (value == 0f) {
            0
        } else ceil((density * value).toDouble()).toInt()
    }


    private fun checkDisplaySize(context: Context) {
        try {
            density = context.resources.displayMetrics.density
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGoogleDocsUri(uri: Uri): Boolean {
        return "com.google.android.apps.docs.storage" == uri.authority
    }

    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    //PdfiumAndroid (https://github.com/barteksc/PdfiumAndroid)
    //https://github.com/barteksc/AndroidPdfViewer/issues/49

    fun generateImageFromPdf(context: Context, pdfUri: Uri): Bitmap? {

        val pageNumber = 0
        val pdfiumCore = PdfiumCore(context)
        val bmp: Bitmap
        try {
            Log.e("generateImageFromPdf", "generateImageFromPdf: $pdfUri")
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            val fd = context.contentResolver.openFileDescriptor(pdfUri, "r")
            val pdfDocument = pdfiumCore.newDocument(fd)
            pdfiumCore.openPage(pdfDocument, pageNumber)
            val width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber)
            val height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber)
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height)
//            saveImage(bmp);
            pdfiumCore.closeDocument(pdfDocument) // important!
            return bmp
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
    fun download(fileUrl:String,directory:File) = try {


        val url = URL(fileUrl)
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.connect()

        var inputStream: InputStream = urlConnection.inputStream;
        var fileOutputStream: FileOutputStream = FileOutputStream(directory)
        var totalSize: Int = urlConnection.contentLength

        var buffer = ByteArray(1024)
        var bufferLength: Int = 0


        while(inputStream.read(buffer).also{bufferLength=it}>0)
        {
            fileOutputStream.write(buffer,0,bufferLength)
        }

        fileOutputStream.close()
        fileOutputStream.flush()
        inputStream.close()
    }
    catch (e:FileNotFoundException) {
        e.printStackTrace()
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    } catch (e:IOException) {
        e.printStackTrace()
    }

    fun getPath(context: Context, uri: Uri): String? {
        val needToCheckUri = Build.VERSION.SDK_INT >= 19
        val selection: String
        val selectionArgs: Array<String>
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                return if ("primary".equals(type, ignoreCase = true)) {
                    Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                } else {
                    Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                Toast.makeText(context, "contentUri$contentUri", Toast.LENGTH_SHORT).show()
                Log.e("contentUri", "getPath: $contentUri")
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                selection = "_id=?"
                selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
                //                final String docId = DocumentsContract.getDocumentId(uri);
                //                final String[] split = docId.split(":");
                //                final String type = split[0];
                //                if ("image".equals(type)) {
                //                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                //                } else if ("video".equals(type)) {
                //                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                //                } else if ("audio".equals(type)) {
                //                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                //                }
                //                selection = "_id=?";
                //                selectionArgs = new String[]{ split[1] };
            } else if (isGoogleDocsUri(uri)) {
                Log.e("contentUri", "getPath: " + "GOOGLE DRIVE")
                var filename = ""
                var filePath = ""
                val mimeType = context.contentResolver.getType(uri)
                if (mimeType == null) {
                    val path = getPath(context, uri)
                    if (path == null) {
                        filename = FilenameUtils.getName(uri.toString())
                    } else {
                        val file = File(path)
                        filename = file.name
                    }
                } else {
                    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
                    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    returnCursor.moveToFirst()
                    filename = returnCursor.getString(nameIndex)
                }
                val path = makeDirectory(context)
                val fileSave = File(path)
                filePath = fileSave.absolutePath + "/" + filename
                Log.e("contentUri", "getPath:filePath $filePath")
                try {
                    copyFileStream(File(filePath), uri, context)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return filePath
            }/*
             * check whether the document is selected from google drive
             * then get the filename and path from the uri
             *
             * @Params uri
             * */
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)
        return null
        /*if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;*/
    }


    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor =
                context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                DatabaseUtils.dumpCursor(cursor)
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    @Throws(IOException::class)
    private fun copyFileStream(dest: File, uri: Uri, context: Context) {

        var iss: InputStream? = null
        var os: OutputStream? = null

        try {

            iss = context.contentResolver.openInputStream(uri)
            os = FileOutputStream(dest)

            val buffer = ByteArray(1024)
            var length: Int = 0
            var booleanFlag: Boolean = false


            while ((length == iss!!.read(buffer))) {
                os.write(buffer, 0, length)
            }

            /*while ((length == iss!!.read(buffer)) > 0) {
                os.write(buffer, 0, length)
            }*/

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            iss!!.close()
            os!!.close()
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun changeChatDateFormat(stringDate: String): String {

        var string = ""

        try {

            val simpleInputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

            simpleInputDateFormat.timeZone= TimeZone.getTimeZone("IST")

            val simpleOutputDateFormat = SimpleDateFormat("hh:mm a")

            simpleOutputDateFormat.timeZone = TimeZone.getDefault()

            val dateInput = simpleInputDateFormat.parse(stringDate)
            Log.e("TimeSet2",""+dateInput)
            string = simpleOutputDateFormat.format(dateInput)

            Log.e("TimeSet3",""+string)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return string

    }


    fun getTimeFromMillis(millis:Long):String
    {

        var output=""

        try
        {

            var hour= TimeUnit.MILLISECONDS.toHours(millis)
            var minutes= TimeUnit.MILLISECONDS.toMinutes(millis)
            var seconds= TimeUnit.MILLISECONDS.toSeconds(millis)

//            Log.e("Hours", hour.toString())
//            Log.e("Minute", minutes.toString())
//            Log.e("Seconds", seconds.toString())

            output="$hour:$minutes:$seconds"

//            Log.e("OutPutString",output)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

        return output
    }


    fun hideSoftKeyboard(activity: Activity) {

        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    /* Alert Dialog with one button*/
    fun alertDialog(context: Context, msg: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)

            alertDialogBuilder.setPositiveButton("OK") { arg0, arg -> arg0.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


     fun customDialog(context: Context, title: String, msg: String) {

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

             alertDialog.dismiss()
         })

         alertDialog.setCancelable(true)
         alertDialog.setCanceledOnTouchOutside(true)
         alertDialog.show()
    }

    fun alertDialogBack(context: Context, msg: String,type: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)

        when {
            type.equals("clickBack") -> alertDialogBuilder.setPositiveButton("OK") { arg0, arg ->

               /* context.startActivity(Intent(context,Login::class.java))
                (context as Activity).finishAffinity()*/
            }
            type.equals("normalBack") -> alertDialogBuilder.setPositiveButton("OK") { arg0, arg ->

                (context as Activity).finish()
            }
            type.equals("fragBack")-> alertDialogBuilder.setPositiveButton("OK"){arg0,arg->
                val fm = (context as FragmentActivity).supportFragmentManager
                fm.popBackStackImmediate()
            }
            else -> alertDialogBuilder.setPositiveButton("OK") { arg0, arg -> arg0.dismiss() }
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun alertDialogWithIntent(context: Context, msg: String, aClass: Class<*>) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)

            alertDialogBuilder.setPositiveButton("OK") { arg0, arg ->
                arg0.dismiss()
                val intent = Intent(context, aClass)
                context.startActivity(intent)
                (context as Activity).finishAffinity()
            }



        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun alertDialogWithIntentWithExtra(context: Context, msg: String, aClass: Class<*>, unload: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)
        alertDialogBuilder.setPositiveButton("OK") { arg0, arg ->
            arg0.dismiss()
            val intent = Intent(context, aClass)
            intent.putExtra("unload", unload)
            context.startActivity(intent)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun alertDialogWithIntentFinish(context: Context, msg: String, aClass: Class<*>) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)
        alertDialogBuilder.setPositiveButton("OK") { arg0, arg ->
            arg0.dismiss()
            val intent = Intent(context, aClass)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    /* Alert Dialog with one button*/
    fun alertDialogTwoButtons(context: Context, msg: String, firstButton: String, secButton: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)
        alertDialogBuilder.setPositiveButton(firstButton) { arg0, arg -> arg0.dismiss() }
        alertDialogBuilder.setPositiveButton(secButton) { arg0, arg -> arg0.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    /* Alert dialog for intent*/
    fun alertDialogIntent(context: Context, msg: String, to: Class<*>,string: String,bundle: Bundle?) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(context.getString(R.string.app_name))
        alertDialogBuilder.setMessage(msg)
        if(string.equals("data"))
        {
            alertDialogBuilder.setPositiveButton("OK") { arg0, arg1 ->
                arg0.dismiss()
                val it = Intent(context, to)
                it.putExtras(bundle)
                context.startActivity(it)
            }

        }
        else
        {
            alertDialogBuilder.setPositiveButton("OK") { arg0, arg1 ->
                arg0.dismiss()
                intent(context, to)
            }

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    /* Start Activity*/
    private fun intent(context: Context, to: Class<*>) {
        val it = Intent(context, to)
        context.startActivity(it)
    }


    /* Check whether the app is in foreground or in background*/
    fun checkActivation(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningTasks(Integer.MAX_VALUE)
        var isActivityFound = false
        if (services[0].topActivity.packageName.toString().equals(context.packageName.toString(), ignoreCase = true)) {
            isActivityFound = true
        }
        Log.e(" FCM ", "Activity open: $isActivityFound")  // true  foreground n false background
        return isActivityFound
    }

    /* Check Network Availability*/
    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wm = context.getSystemService(WIFI_SERVICE) as WifiManager
        val ip = Formatter.formatIpAddress(wm.connectionInfo.ipAddress)

        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifi != null && wifi.isConnected) {
            return true
        }

        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mobile != null && mobile.isConnected) {
            return true
        }

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    fun getFileExt(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)
    }


    fun getRealPathFromURI(contentURI: Uri, activity: AppCompatActivity): String? {
        val result: String?
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }


    // TODO: 3/8/17 get path  for video
    fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String? {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }


    fun makeDirectory(context: Context): String {
        var directory: File? = null
        var profile_image_path = ""
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            directory = File(Environment.getExternalStorageDirectory().toString() + "/unilife")
            if (!directory.exists())
                directory.mkdirs()
        } else {
            directory = context.getDir("unilife", 0)  // private mode
            if (!directory!!.exists())
                directory.mkdirs()
        }

        if (directory != null) {
            val profile_image = File(directory.toString() + File.separator + "unilife")

            if (!profile_image.exists())
                profile_image.mkdirs()

            profile_image_path = directory.toString() + ""

        }
        return profile_image_path
    }

    fun makeFile(bitmap: Bitmap, name: String): File {

        var file: File
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        file = File(dir, name)

        if (file.exists()) {
            file.delete()
        }
        file = File(dir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file.path)
            if(bitmap!=null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return file
    }

    fun makeFileNew( name: String): File {

        var file: File
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        file = File(dir, name)

        if (file.exists()) {
            file.delete()
        }
        file = File(dir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file.path)

            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return file
    }

    fun hideKeyboard(context: Context, view: View?) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}
