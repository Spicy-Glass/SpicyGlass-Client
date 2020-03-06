package spicyglass.client.integration.system

import android.content.Context
import androidx.core.app.NotificationCompat
import spicyglass.client.R

/**
 * The notification handler class.
 */
object NotificationHandler {
    fun sendNotification(context: Context, channel: String, smallIcon: Int = R.drawable.ic_fire_black_24dp, title: String = "Spicy Glass", text: String = "") {
        val mBuilder = NotificationCompat.Builder(context, channel)
        mBuilder.setSmallIcon(smallIcon)
        mBuilder.setContentTitle(title)
        mBuilder.setContentText(text)
    }
}