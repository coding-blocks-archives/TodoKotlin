package com.codingblocks.todo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "Todo",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        val taskRowId = intent.extras?.getString(TASK_ID)
        val taskTitle = intent.extras?.getString(TASK_TITLE)
        val taskTask = intent.extras?.getString(TASK_TASK)
        val rowId = taskRowId.toString()
        val notificationIntent = Intent(context, MainActivity::class.java)
//        notificationIntent.putExtra(Constant.ROW_ID, rowId)
//        notificationIntent.putExtra(Constant.NOTIFICATION, true)
        notificationIntent.action = "EDIT"
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationFinishIntent = Intent(context, MainActivity::class.java)
//        notificationFinishIntent.putExtra(Constant.ROW_ID, rowId)
//        notificationFinishIntent.putExtra(Constant.NOTIFICATION, true)
        notificationFinishIntent.action = "FINISH"
        val pendingFinishIntent = PendingIntent.getActivity(
            context,
            0,
            notificationFinishIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val title = taskTitle//taskTitle//task title
        val message = taskTask//taskTask//task
        val icon = R.mipmap.ic_launcher
        val time = System.currentTimeMillis()

        val notification = NotificationCompat.Builder(context, "default")
            .setContentTitle(title)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setColor(Color.argb(225, 225, 87, 34))
            .setSmallIcon(icon)
            .setWhen(time)
            .addAction(R.drawable.ic_edit_black_24dp, "Edit", pendingIntent)
            .addAction(R.drawable.ic_check_black_24dp, "Finish", pendingFinishIntent)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .build()
        mNotificationManager.notify(Integer.parseInt(rowId/*rowId*/), notification)

    }
}
