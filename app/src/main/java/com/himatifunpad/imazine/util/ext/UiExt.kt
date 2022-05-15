package com.himatifunpad.imazine.util.ext

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

// shorter snackbar creation
fun Fragment.createSnackbar(
  message: String,
  duration: Int = Snackbar.LENGTH_SHORT,
): Snackbar = Snackbar.make(requireView(), message, duration)