package com.himatifunpad.imazine

import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.himatifunpad.imazine.core.notification.NotificationUtil
import com.himatifunpad.imazine.core.work.LatestPostWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import logcat.AndroidLogcatLogger
import logcat.LogPriority.VERBOSE
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
  @Inject lateinit var workerFactory: HiltWorkerFactory

  override fun getWorkManagerConfiguration(): Configuration =
    Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .setMinimumLoggingLevel(android.util.Log.DEBUG)
      .build()

  override fun onCreate() {
    super.onCreate()
    // init component
    AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = VERBOSE)
    // notification channel
    NotificationUtil.createNotificationChannel(
      context = this,
      importance = NotificationManagerCompat.IMPORTANCE_HIGH,
      name = NotificationUtil.ChannelName.NEW_POST,
      description = "Notification when a new article is posted",
      showBadge = true
    )
  }
}