package com.himatifunpad.imazine.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.himatifunpad.imazine.BuildConfig
import com.himatifunpad.imazine.R.id
import com.himatifunpad.imazine.R.layout
import com.himatifunpad.imazine.R.navigation
import com.himatifunpad.imazine.R.style
import com.himatifunpad.imazine.core.data.local.DataStoreManager
import com.himatifunpad.imazine.ui.ext.toggleAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  @Inject lateinit var prefs: DataStoreManager
  private lateinit var firebaseAnalytics: FirebaseAnalytics
  // private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    Thread.sleep(1000)
    setTheme(style.Theme_Imazine)
    // inflate
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    // setting up nav component
    val navHostFragment =
      supportFragmentManager.findFragmentById(id.main_container) as NavHostFragment
    val inflater = navHostFragment.navController.navInflater
    val graph = inflater.inflate(navigation.graph_main)
    // run with lifecylce scope
    lifecycleScope.launchWhenCreated {
      toggleAppTheme(prefs.appTheme.first())
      // set start destination
      if (isLoggedIn())
        graph.setStartDestination(id.homeScreen)
      else
        graph.setStartDestination(id.authScreen)
      // bind navGraph to fragment
      val navController = navHostFragment.navController
      navController.setGraph(graph, intent.extras)
    }
    // init analytics
    firebaseAnalytics = Firebase.analytics

    // Android 13 notifications permission, check askNotificationPermission() below for more info
    // // Sets up permissions request launcher.
    // requestPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted->
    //   if (isGranted) {
    //     // FCM can now post notifications
    //   } else {
    //     Snackbar.make(
    //       findViewById<View>(android.R.id.content).rootView,
    //       "Please grant Notification permission from App Settings",
    //       Snackbar.LENGTH_LONG
    //     ).show()
    //   }
    // }
    //
    // askNotificationPermission()
  }

  private suspend fun isLoggedIn(): Boolean = prefs.user.first().isEmpty().not()
  private suspend fun isPostNotificationOn(): Boolean = prefs.notifyNewPost.first()

  // still unused (for now) refer to https://developer.android.com/guide/topics/ui/notifiers/notification-permission
  // @RequiresApi(33)
  // private fun askNotificationPermission() {
  //   if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
  //     PackageManager.PERMISSION_GRANTED
  //   ) {
  //     // FCM SDK (and your app) can post notifications.
  //   } else {
  //     // Directly ask for the permission
  //     requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
  //   }
  // }

  override fun onResume() {
    super.onResume()
    if (BuildConfig.DEBUG) {
      window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
  }
}