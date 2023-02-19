package com.example.vechatooo

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.vechatooo.R.layout.notification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channel_id= "notification channel"
const val channel_name ="com.example.vechatooo"
class MyFIrebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if(message.notification !=null){
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String):RemoteViews{
        val remoteView= RemoteViews("com.example.vechatooo", notification)
        remoteView.setTextViewText(R.id.titleTv, title)
        remoteView.setTextViewText(R.id.titleDescription,message)
        remoteView.setImageViewResource(R.id.titleImage,R.drawable.send)
        return remoteView
    }
    fun generateNotification(title:String, message:String){
        val intent = Intent(this, HomePage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder = NotificationCompat.Builder(this, channel_id)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.send)
            .setVibrate(longArrayOf(1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, message))
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager

        val notificationChannel = NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(0,builder)
    }
}