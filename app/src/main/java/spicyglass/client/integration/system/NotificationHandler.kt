package spicyglass.client.integration.system

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import spicyglass.client.R


/**
 * The notification handler class.
 */
object NotificationHandler {
    var notificationId = 0
    fun sendNotification(context: Context, channel: String, smallIcon: Int = R.drawable.ic_fire_black_24dp, title: String = "Spicy Glass", text: String = ""): Int {
        val mBuilder = NotificationCompat.Builder(context, channel)
        mBuilder.setSmallIcon(smallIcon)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
        //TODO attach actions
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(++notificationId, mBuilder.build())
        return notificationId
    }
}