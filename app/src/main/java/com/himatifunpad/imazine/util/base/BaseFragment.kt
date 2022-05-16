package com.himatifunpad.imazine.util.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId : Int) : Fragment(layoutId) {
  protected lateinit var eventJob: Job

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
  }

  abstract fun setupView()

  override fun onStop() {
    super.onStop()
    if(this::eventJob.isInitialized)
      eventJob.cancel()
  }
}