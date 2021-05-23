package unilife.com.unilife

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import unilife.com.unilife.chat.Chat
import unilife.com.unilife.chat.ChatListing
import unilife.com.unilife.chat.RequestReceivedList
import unilife.com.unilife.home.Home
import unilife.com.unilife.blogs.Blog
import unilife.com.unilife.brands.BrandsHome


class MyFirebaseMessagingService : FirebaseMessagingService() {


    var message = ""
    var type = ""
    var title = ""

    var pendingIntent: PendingIntent? = null
    var mBuilder: NotificationCompat.Builder? = null
    var intent: Intent? = null
    var notificationManager: NotificationManager? = null
    var notification: Notification? = null

    var showNotificationFlag: Boolean = true


    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.e(TAG, "From: ${remoteMessage.from}")
        Log.e("newNotification", "received")
        remoteMessage.data.isNotEmpty().let {

            Log.e(TAG, "Message data payload: " + remoteMessage.data)

            val messagebody = remoteMessage.data

            title = messagebody["title"].toString()
            message = messagebody["message"].toString()
            type = messagebody["type"].toString()

            sendNotification(this, message, type)

        }


    }

    override fun onNewToken(token: String) {
//        Log.e(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }


    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
//        Log.e(TAG, "sendRegistrationTokenToServer($token)")
    }


    private fun sendNotification(
        context: Context,
        message: String?,
        type: String?
    ) {
        try {

            Log.e(TAG, "" + type.toString() + "" + message.toString())

            if (checkActivation(applicationContext)) {

                Log.e(TAG, "FG...." + Chat.isChatRunning)

                when (type) {

                    "Chat" -> {

                        if (Chat.isChatRunning) {
                            showNotificationFlag = false

                        } else {
                            showNotificationFlag = true
                            val intent = Intent("New message")  // title : New message
                            LocalBroadcastManager.getInstance(applicationContext)
                                .sendBroadcast(intent)
                        }
                    }

                    "Post" -> {
                        val intent = Intent("New Post has arrived")  // title : New Post has arrived
                        intent.putExtra("notify", "notify")
                        LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(intent)
                    }

                    "Blog" -> {
                        val intent = Intent("New Blog has arrived")  // title : New Blog has arrived
                        intent.putExtra("notify", "notify")
                        LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(intent)
                    }

                    "Friend Request" -> {
                        val intent = Intent("Friend Request")  // title : Friend Request
                        intent.putExtra("notify", "notify")
                        LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(intent)
                    }

                    "Accept Request", "Group" -> {
                        val intent = Intent("New message")  // title : Accept Request
                        intent.putExtra("notify", "notify")
                        LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(intent)
                    }

                    "Online", "Offline" -> {
                        showNotificationFlag = false

                        val intent = Intent("New message")  // title : Accept Request
                        intent.putExtra("notify", "notify")
                        LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent)
                    }


                    "Reject Request" -> {
//                        val intent = Intent("Reject Request")  // title : Reject Request
//                        intent.putExtra("notify", "notify")
//                        LocalBroadcastManager.getInstance(getApplicationContext())
//                            .sendBroadcast(intent)
                    }

                    "Offer" -> {

                    }

                }

            } else {

                Log.e(TAG, "BG....")

                when (type) {

                    "Chat", "Group", "Accept Request", "Reject Request" -> {
                        intent = Intent(applicationContext, ChatListing::class.java)
                        intent!!.putExtra("notify", "notify")
                    }

                    "Post" -> {
                        intent = Intent(applicationContext, Home::class.java)
                        intent!!.putExtra("notify", "notify")
                    }

                    "Blog" -> {
                        intent = Intent(applicationContext, Blog::class.java)
                        intent!!.putExtra("notify", "notify")
                    }


                    "Friend Request" -> {
                        intent = Intent(applicationContext, RequestReceivedList::class.java)
                        intent!!.putExtra("notify", "notify")
                    }

                    "Offer" -> {
                        intent = Intent(applicationContext, BrandsHome::class.java)
                        intent!!.putExtra("notify", "notify")
                    }

                }

                intent!!.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
                pendingIntent =
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

            }

            mBuilder = NotificationCompat.Builder(this)

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val uri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.ringtwo)

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION).build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                var mChannel: NotificationChannel? = null

                if (notificationManager != null) {
                    val channelList = notificationManager!!.notificationChannels
                    var i = 0
                    while (channelList != null && i < channelList.size) {
                        notificationManager!!.deleteNotificationChannel(channelList[i].id)
                        i++
                    }
                }
                mChannel =
                    NotificationChannel("Unilife", message, NotificationManager.IMPORTANCE_HIGH)
                mChannel.setSound(uri, attributes)
                notificationManager!!.createNotificationChannel(mChannel)

                notification = mBuilder!!
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("Unilife")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(getNotificationIcon(mBuilder!!, context))
                    .setPriority(Notification.PRIORITY_MAX)
                    .setSound(uri)
                    .setContentText(message)
                    .setChannelId("Unilife")
                    .build()

                notification!!.defaults = 0
                notification!!.sound = uri
                if (showNotificationFlag) {
                    notificationManager!!.notify(1, notification)
                }


            } else {
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                @SuppressLint("ResourceAsColor") val notificationBuilder =

                    mBuilder!!
                        .setWhen(0)
                        .setAutoCancel(true)
                        .setContentTitle("Unilife")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(getNotificationIcon(mBuilder!!, context))
                        .setPriority(Notification.PRIORITY_MAX)
                        .setSound(uri)
                        .setContentText(message)
                        .build()
                notificationManager.notify(1, notificationBuilder)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }

    fun checkActivation(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningTasks(Integer.MAX_VALUE)
        var isActivityFound = false
        if (services[0].topActivity.packageName.toString()
                .equals(context.packageName.toString(), ignoreCase = true)
        ) {
            isActivityFound = true
        }
        Log.e(" FCM ", "Activity open: $isActivityFound")  // true  foreground n false background
        return isActivityFound
    }

    private fun getNotificationIcon(
        notificationBuilder: NotificationCompat.Builder,
        context: Context
    ): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color = ContextCompat.getColor(context, R.color.white)
            notificationBuilder.color = color
            return R.drawable.app_logo
        } else {
            return R.drawable.app_logo
        }
    }


}
