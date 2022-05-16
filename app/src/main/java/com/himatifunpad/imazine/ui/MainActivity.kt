package com.himatifunpad.imazine.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.himatifunpad.imazine.R.id
import com.himatifunpad.imazine.R.layout
import com.himatifunpad.imazine.R.navigation
import com.himatifunpad.imazine.R.style
import com.himatifunpad.imazine.core.data.local.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  @Inject lateinit var prefs: DataStoreManager

  override fun onCreate(savedInstanceState: Bundle?) {
    Thread.sleep(1000)
    setTheme(style.Theme_Imazine)
    // inflate
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    val navHostFragment =
      supportFragmentManager.findFragmentById(id.main_container) as NavHostFragment
    val inflater = navHostFragment.navController.navInflater
    val graph = inflater.inflate(navigation.graph_main)
    lifecycleScope.launchWhenCreated {
      if (isLoggedIn())
        graph.setStartDestination(id.homeFragment)
      else
        graph.setStartDestination(id.authFragment)

      val navController = navHostFragment.navController
      navController.setGraph(graph, intent.extras)
    }
  }

  private suspend fun isLoggedIn(): Boolean = prefs.user.first().isEmpty().not()
}