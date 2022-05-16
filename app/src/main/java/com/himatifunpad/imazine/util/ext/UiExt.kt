package com.himatifunpad.imazine.util.ext

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun Fragment.createSnackbar(
  message: String,
  duration: Int,
): Snackbar = Snackbar.make(requireView(), message, duration)

fun Fragment.snackbar(
  message : String,
  duration : Int = Snackbar.LENGTH_SHORT,
) {
  createSnackbar(message,duration).show()
}

fun EditText?.trimmedText(): String =
  this?.text.toString().trim()

fun TextInputLayout.trimmedText() =
  this.editText.trimmedText()