package bubblebud.com.bubblebud.view.chat

import android.util.Base64
import android.util.Log
import java.io.*

class FileUploadManager {
    private val TAG = "FileUploadManager"
    private var mFileSize: Long = 0
    private var mFile: File? = null
    private var stream: BufferedInputStream? = null
    private var mData: String? = null
    private var mBytesRead: Int = 0

    fun prepare(fullFilePath: String): Boolean {
        Log.e("qqqqq", "qqqqq")

        mFile = File(fullFilePath)
        mFileSize = mFile!!.length()


        Log.e("FileLength",mFileSize.toString())
        Log.e("FUM",mFile!!.name)

        try {
            val fileInputStream = FileInputStream(mFile)
            stream = BufferedInputStream(fileInputStream)
        } catch (e: FileNotFoundException) {
        }

        return true
    }

    fun getFileName(): String {
        return mFile!!.name
    }

    fun getFileSize(): Long {
        return mFileSize
    }

    fun getBytesRead(): Long {
        return mBytesRead.toLong()
    }

    fun getData(): String? {
        return mData
    }

    fun read(byteOffset: Int) {
        try {
            val byteBuffer = ByteArrayOutputStream()
            val buffer = ByteArray(100000)
            mBytesRead = stream!!.read(buffer)
            byteBuffer.write(buffer, 0, mBytesRead)
            Log.e(TAG, "Read :$mBytesRead")
            mData = Base64.encodeToString(byteBuffer.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    fun close() {
        if (stream != null) {
            try {
                mFile!!.deleteOnExit()
                stream!!.close()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            stream = null
        }
    }

}