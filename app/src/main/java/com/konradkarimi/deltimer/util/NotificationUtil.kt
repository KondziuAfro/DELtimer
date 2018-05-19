package com.konradkarimi.deltimer.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.konradkarimi.deltimer.AppConstants
import com.konradkarimi.deltimer.R
import com.konradkarimi.deltimer.TimerActivity
import com.konradkarimi.deltimer.TimerNotificationActionReceiver
import java.text.SimpleDateFormat
import java.util.*

class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "menu_timer"
        private const val CHANNEL_ID_NAME = "Timer App Timer"
        private const val TIMER_ID = 0

        fun showTimerExpiried(context: Context) {
            val startInternt = Intent(context, TimerNotificationActionReceiver::class.java)
            startInternt.action = AppConstants.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(context, 0, startInternt, PendingIntent.FLAG_UPDATE_CURRENT)

            val nbuilder = getBasicNofificationBuilder(context, CHANNEL_ID_TIMER, true)
            nbuilder.setContentTitle("Time Expiried!").setContentText("Start Again?")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .addAction(R.drawable.ic_play, "Start", startPendingIntent)

            val nmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nmanager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_ID_NAME, true)
            nmanager.notify(TIMER_ID, nbuilder.build())

        }

        fun showTimerRunning(context: Context, wakeUpTime: Long) {
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = AppConstants.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(context, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val dateform = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nbuilder = getBasicNofificationBuilder(context, CHANNEL_ID_TIMER, true)
            nbuilder.setContentTitle("Time is Running!").setContentText("End: ${dateform.format(Date(wakeUpTime))}")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
                    .addAction(R.drawable.ic_pause, "Pause", pausePendingIntent)

            val nmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nmanager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_ID_NAME, true)
            nmanager.notify(TIMER_ID, nbuilder.build())

        }

        fun showTimerPaused(context: Context) {
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = AppConstants.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(context, 0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nbuilder = getBasicNofificationBuilder(context, CHANNEL_ID_TIMER, true)
            nbuilder.setContentTitle("Time is paused!").setContentText("Resume?")
                    .setContentIntent(getPendingIntentWithStack(context, TimerActivity::class.java))
                    .setOngoing(true)
                    .addAction(R.drawable.ic_play, "Resume", resumePendingIntent)

            val nmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nmanager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_ID_NAME, true)
            nmanager.notify(TIMER_ID, nbuilder.build())

        }

        fun hideTimerNotification(context: Context){
            val nmanager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nmanager.cancel(TIMER_ID)
        }

        private fun getBasicNofificationBuilder(context: Context, channelId: String, playSound: Boolean): NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val nBuilder = NotificationCompat.Builder(context, channelId).setSmallIcon(R.drawable.ic_timer).setAutoCancel(true).setDefaults(0)
            if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)
            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun NotificationManager.createNotificationChannel(channelId: String, channelName: String, playSound: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelImportance = if (playSound) NotificationManager.IMPORTANCE_DEFAULT
                else NotificationManager.IMPORTANCE_LOW
                val nchannel = NotificationChannel(channelId, channelName, channelImportance)
                nchannel.enableLights(true)
                nchannel.lightColor = Color.BLUE
                this.createNotificationChannel(nchannel)
            }
        }
    }
}