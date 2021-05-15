package dev.samuelmcmurray.e_wastemanagement.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.samuelmcmurray.e_wastemanagement.R

class NotificationHandler(
    var context: Context,
    private var itemName: String,
    private var companyName: String
) {

    private var CHANNEL_ID: String = "111"
    private val NOTIFICATION_ID_STRING = 99
    private lateinit var mNotifyManager: NotificationManager

    private var CHANNEL_NAME = "Bid notification"
    private var CHANNEL_DESCRIPTION = "Notification of bid on an uploaded item"

    private var textTitle = "New bid for you item $itemName"
    private var textContent = "A new bid has been made on your uploaded item by $companyName"


    init {
        createNotificationChannel()
    }

    // method to call
    fun callNotification() {
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID_STRING, builder.build())
        }
    }

    private var builder: NotificationCompat.Builder =
        NotificationCompat.Builder(context.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(textContent)).setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

}