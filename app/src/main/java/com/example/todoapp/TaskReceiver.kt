package com.example.todoapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat


class TaskReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val nm =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(
            context, 123, Intent(
                context,
                MainActivity::class.java
            ),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(context,"one")
            .setContentTitle(intent.getStringExtra("TASK"))
            .setContentText(intent.getStringExtra("Des"))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
        nm.notify(System.currentTimeMillis().toInt(), notification)
    }
}