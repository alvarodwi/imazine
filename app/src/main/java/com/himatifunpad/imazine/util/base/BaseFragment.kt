package com.himatifunpad.imazine.util.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
abstract class BaseFragment(@LayoutRes layoutId : Int) : Fragment(layoutId) {
  protected lateinit var eventJob: Job

  abstract fun setupView()

  override fun onStop() {
    super.onStop()
    eventJob.cancel()
  }
}