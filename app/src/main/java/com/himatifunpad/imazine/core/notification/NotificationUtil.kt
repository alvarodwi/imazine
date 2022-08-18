package com.himatifunpad.imazine.core.notification

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationManagerCompat
import com.himatifunpad.imazine.R
import logcat.logcat

object NotificationUtil {
  object ChannelName {
    const val NEW_POST = "New post"
  }

  data class NotificationData(
    val id: Int = 0,
    val title: String = "",
    val message: String = "",
    val channel: String,
  )

  fun createNotificationChannel(
    context: Context,
    @StringRes id: Int,
    name: String,
    description: String,
    importance: Int,
    showBadge: Boolean,
  ) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channelId = context.getString(id)
      val channel = NotificationChannel(channelId, name, importance)
      channel.description = description
      channel.setShowBadge(showBadge)

      NotificationManagerCompat.from(context)
        .createNotificationChannel(channel)
    }
  }

  fun showNotification(context: Context, id: Int, builder: Notification) {
    NotificationManagerCompat.from(context)
      .notify(id, builder)
  }
}