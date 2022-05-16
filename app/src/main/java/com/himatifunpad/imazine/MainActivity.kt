package com.himatifunpad.imazine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    Thread.sleep(1000)
    setTheme(R.style.Theme_Imazine)
    // inflate
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}