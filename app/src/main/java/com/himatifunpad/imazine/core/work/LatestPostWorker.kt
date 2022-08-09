package com.himatifunpad.imazine.core.work

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.core.domain.repository.PostRepository
import com.himatifunpad.imazine.core.notification.NotificationUtil
import com.himatifunpad.imazine.core.notification.NotificationUtil.NotificationData
import com.himatifunpad.imazine.core.notification.NotificationUtil.asChannelId
import com.himatifunpad.imazine.ui.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import logcat.logcat
import java.util.concurrent.TimeUnit

@HiltWorker
class LatestPostWorker @AssistedInject constructor(
  @Assisted appContext: Context,
  @Assisted workerParams: WorkerParameters,
  val post: PostRepository,
) : CoroutineWorker(appContext, workerParams) {
  companion object {
    const val TAG = "core.work.LatestPostWorker"
    const val NEW_POST_NOTIF_ID = 256

    fun scheduleWork(context: Context) {
      logcat { "Work scheduled!" }
      val repeatingRequest =
        PeriodicWorkRequestBuilder<LatestPostWorker>(7, TimeUnit.DAYS)
          .build()

      WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
          TAG,
          ExistingPeriodicWorkPolicy.KEEP,
          repeatingRequest,
        )
    }

    fun unScheduleWork(context: Context) {
      logcat { "Work unscheduled!" }
      WorkManager.getInstance(context)
        .cancelUniqueWork(TAG)
    }
  }

  override suspend fun doWork(): Result {
    try {
      val idBefore = post.getLatestPostId().first()
      post.getLatestPost()
      val idAfter = post.getLatestPostId().first()

      if (idBefore != idAfter) {
        logcat { "New post detected..." }
        NotificationData(
          title = "New article posted!",
          message = "Check this new article on Imazine!!",
          channel = NotificationUtil.ChannelName.NEW_POST
        ).apply {
          NotificationUtil.showNotification(
            applicationContext,
            NEW_POST_NOTIF_ID,
            buildNewPostNotification(applicationContext, this)
          )
        }
      } else {
        logcat { "No new post detected..." }
        NotificationData(
          title = "Nothing new posted!",
          message = "Stay tuned on Imazine!!",
          channel = NotificationUtil.ChannelName.NEW_POST
        ).apply {
          NotificationUtil.showNotification(
            applicationContext,
            NEW_POST_NOTIF_ID,
            buildNewPostNotification(applicationContext, this)
          )
        }
      }
    } catch (e: Exception) {
      return Result.retry()
    }
    return Result.success()
  }

  private fun buildNewPostNotification(
    context: Context,
    data: NotificationData
  ): Notification =
    NotificationCompat.Builder(context, data.channel.asChannelId(context))
      .apply {
        val intent = Intent(context, MainActivity::class.java).apply {
          flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent =
          PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        setContentIntent(pendingIntent)
        setSmallIcon(R.drawable.ic_logo_notif)
        setContentTitle(data.title)
        setContentText(data.message)
        setOngoing(false)
        setAutoCancel(true)
      }
      .build()
}