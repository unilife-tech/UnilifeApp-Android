package bubblebud.com.bubblebud.view.socketConnection

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import bubblebud.com.bubblebud.view.chat.FileUploadManager
import bubblebud.com.bubblebud.view.chat.UploadFileMoreDataReqListener


import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import org.json.JSONObject
import unilife.com.unilife.PreferenceFile
import unilife.com.unilife.retrofit.WebUrls
import unilife.com.unilife.socketConnection.SocketSSL
import kotlin.collections.HashMap

class SocketConnection(val context: Context) {

    val TAG = SocketConnection::class.java.simpleName


    companion object {
        lateinit var socket: Socket
        var socketListeners: SocketUpdateListeners?=null
        var inboxSocketListeners: SocketUpdateListeners?=null
        var homeSocketListeners: SocketUpdateListeners?=null

    }


    init {
        socket = IO.socket(WebUrls.SOCKET_BASE_URL, SocketSSL().certificate())
    }


    public fun connectSocket(roomId: String) {

        if (socket.connected()) {
            socket.disconnect()
        }

        joinRoom(roomId)
        socket!!.on(WebUrls.LISTEN_ROOM_JOIN, roomJoinListener)
        socket!!.on(WebUrls.LISTEN_SEND_MESSAGE, sendMessageListener)
        socket!!.on(Socket.EVENT_RECONNECTING, reconnectingListener)
        socket!!.on(Socket.EVENT_RECONNECT_ERROR, reconnectingErrorListener)
        socket!!.connect()
    }


    public fun joinRoom(roomId: String) {
        try {

            Log.e(TAG,"JOIN_ROOM"+ roomId)
            socket!!.emit(WebUrls.JOIN_ROOM, roomId)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var roomJoinListener = Emitter.Listener { args ->
        Log.e(TAG,"RoomJoined"+ "SuccessFully")
    }

    var reconnectingListener = Emitter.Listener { args ->
        Log.e(TAG,"Reconnected" +"SuccessFully" + args[0])
    }

    var reconnectingErrorListener = Emitter.Listener { args ->
        Log.e(TAG,"ReconnectedError"+ "SuccessFully" + args[0])
    }


    public fun sendMessage(
        message: String,
        roomId: String,
        receiverId: String,
        senderId: String,
        messageType: String,
        fileType: String
    ) {
        try {
            var jsonObject = JSONObject()
//            jsonObject.put(WebUrls.ROOM_ID, roomId)
            jsonObject.put(WebUrls.SENDER_ID, senderId)
            jsonObject.put(WebUrls.RECEIVER_ID, receiverId)
            jsonObject.put(WebUrls.MESSAGE, message)
//            jsonObject.put(WebUrls.MESSAGE_TYPE, messageType)
//            jsonObject.put(WebUrls.FILE_TYPE, fileType)

            Log.e(TAG,"SEND_MESSAGE"+ jsonObject.toString())

            socket!!.emit(WebUrls.SEND_MESSAGE, jsonObject)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    var sendMessageListener = Emitter.Listener { args ->

        Log.e(TAG,"onMessageReceived"+ args[0].toString())

        if(socketListeners!=null)
        {
            socketListeners!!.onMessageReceived(args[0].toString())
        }


    }


    public fun leaveRoom(roomId: String) {
        try {

            Log.e(TAG,"leaveRoom"+ roomId)
            socket!!.emit(WebUrls.LEAVE_ROOM, roomId)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    public fun disconnectSocket() {
        socket!!.off()
        socket!!.disconnect()
    }




    var connectionListener = Emitter.Listener { args ->
        Log.e(TAG,"Connected"+ "Successfully")
    }
    var connectionErrorListener = Emitter.Listener { args ->
        Log.e(TAG,"ConnectedError"+ "Error")
    }

    var connectionTimeOutListener = Emitter.Listener { args ->
        Log.e(TAG,"ConnectedTimeOUt"+ "TimeOUt")

    }




    @SuppressLint("StaticFieldLeak")
    public inner class FileUploadTask(
        var file_path: String?, var uni_code: String, var room_id: String, var receiver_id: String,
        var media_path: HashMap<String, String>,var type:String) :
        AsyncTask<String, Int, String>() {
        //   private String receiver_id = "";
        private var attachment_type = ""

        var myFlag ="new"


        lateinit var res1:JSONObject

        private var callback: UploadFileMoreDataReqListener? = null
        private var mFileUploadManager: FileUploadManager? = null

        override fun onPreExecute() {
            mFileUploadManager = FileUploadManager()
        }


        override fun doInBackground(vararg params: String): String? {
            val isSuccess = socket.connected()

            Log.e("isSuccesssss", isSuccess.toString())

            if (isSuccess) {
                mFileUploadManager!!.prepare(file_path!!)

                Log.e("file_path", "" + file_path)
                // This function gets callback when server requests more data
                setUploadFileMoreDataReqListener(mUploadFileMoreDataReqListener)
                // This function will get a call back when upload completes
                setUploadFileCompleteListener()
                // Tell server we are ready to start uploading ..
                if (socket.connected()) {
                    val res = JSONObject()
                    try {
                        res.put("name", mFileUploadManager!!.getFileName())
                        res.put("size", mFileUploadManager!!.getFileSize())
                        Log.e("ObjectStart", "" + res.toString())
                        socket.emit("uploadFileStart", res)

                    } catch (e: JSONException) {
                        //TODO: Log errors some where..
                    }

                }
            }
            return null
        }

        override fun onPostExecute(s: String?) {
            if (s == null) {
                return
            }
            if (s.equals("OK", ignoreCase = true)) {
                Log.e("OnPost", "onPostExecute: " + "aaaaaaaaaa")
                media_path.remove(uni_code)
                mFileUploadManager!!.close()
                socket.off("uploadFileMoreDataReq", uploadFileMoreDataReq)
                socket.off("uploadFileCompleteRes", onCompletedddd)
                uploadFileOnServer(media_path)
            }
        }


        @SuppressLint("ObsoleteSdkInt")
        private fun uploadFileOnServer(map: java.util.HashMap<String, String>) {
            Log.e("ChatClass", "uploadFileOnServer: " + map.keys)
            if (map.size > 0) {
                for (entry in map.keys) {
                    val value = map[entry]

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        FileUploadTask(
                            value,
                            entry, room_id, receiver_id, media_path,type
                        ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "")
                    break
                }
            }
        }


        private val mUploadFileMoreDataReqListener = object : UploadFileMoreDataReqListener {
            override fun uploadChunck(offset: Int, percent: Int) {
                // Read the next chunk

                Log.e("PlaceIS",offset.toString())
                Log.e("PercentIS",percent.toString())

                mFileUploadManager!!.read(offset)
                if (socket.connected()) {
                    var res = JSONObject()
                    res1=JSONObject()
                    try {


                        Log.e("MyFlagu",myFlag)


                        res.put("name", mFileUploadManager!!.getFileName())
                        res.put("size", mFileUploadManager!!.getFileSize())
                        res.put("basedata", mFileUploadManager!!.getData())
                        res.put("flag", myFlag)

                        res1.put(
                            "auth_image",
                            PreferenceFile.getInstance().getPreferenceData(context, WebUrls.KEY_PROFILE_IMAGE)
                        )
                        res1.put("file_type", type)

                        if(type=="image")
                        {
                            res1.put("file_type_text", "sent an image")

                        }
                        else if(type=="video")
                        {
                            res1.put("file_type_text", "sent an video")

                        }
                        else if(type=="file")
                        {
                            res1.put("file_type_text","sent a file")
                        }

                        res1.put("message_type", "media")
                        res1.put("receiver_id", receiver_id)
                        res1.put("room_id", room_id)
                        res1.put(WebUrls.MESSAGE,mFileUploadManager!!.getFileName())
                        res1.put("sender_id", PreferenceFile.getInstance().getPreferenceData(context!!, WebUrls.KEY_USERID))


                        res.put("data",res1)


                        socket.emit("uploadFileChuncks", res)
                        Log.e("ChatClassUploadMore", "uploadChunck:Array $res")

                    } catch (e: JSONException) {
                        e.message
                        //TODO: Log errors some where..
                    }

                }
            }

            override fun err(e: JSONException) {
                // TODO Auto-generated method stub
            }
        }

        internal var uploadFileMoreDataReq: Emitter.Listener =
            Emitter.Listener { args ->
                Log.e("ChatClass", "call: " + "uploadFileMoreDataReq")
                    Log.e("eDataReqListener", "setUploadFileMoreDataReqListener-- " + args[0].toString())


                try {
                    val json_data = args[0] as JSONObject
                    val place = json_data.getInt("Place")
                    val percent = json_data.getInt("Percent")
                    publishProgress(json_data.getInt("Percent"))

                    myFlag="old"
                    callback!!.uploadChunck(place, percent)

                } catch (e: JSONException) {
                    callback!!.err(e)
                }
            }


        internal var onCompletedddd: Emitter.Listener = object : Emitter.Listener {
            override fun call(vararg args: Any) {
                val json_data = args[0] as JSONObject

                Log.e("OnCompleteData",json_data.toString())

                Log.e("Complete", "call:json_data $json_data")
                if (json_data.has("IsSuccess")) {
//                    sendCompleteMessage(res1)

                    publishProgress(110)
                    return
                }
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            if (values[0]!! > 107) {
                if (media_path.containsKey(uni_code)) {
                    onPostExecute("OK")
                }
            }


        }



        private fun setUploadFileMoreDataReqListener(callbackk: UploadFileMoreDataReqListener) {
            Log.e("ChatClass", "setUploadFileMoreDataReqListener: ")
            callback = callbackk
            socket.on("uploadFileMoreDataReq", uploadFileMoreDataReq)
        }

        private fun setUploadFileCompleteListener() {
            socket.on("uploadFileCompleteRes", onCompletedddd)
        }
    }


}