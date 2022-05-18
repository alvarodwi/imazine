package com.himatifunpad.imazine.ui.ext

import android.os.Build
import android.text.Html
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.himatifunpad.imazine.util.coil.CoilImageGetter
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Fragment.createSnackbar(
  message: String,
  duration: Int,
): Snackbar = Snackbar.make(requireView(), message, duration)

fun Fragment.snackbar(
  message: String,
  duration: Int = Snackbar.LENGTH_SHORT,
) {
  createSnackbar(message, duration).show()
}

fun EditText?.trimmedText(): String =
  this?.text.toString().trim()

fun TextInputLayout.trimmedText() =
  this.editText.trimmedText()

@Suppress("DEPRECATION")
fun TextView.parseHtmlWithImage(html: String) {
  val imageGetter = CoilImageGetter(this)
  this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT, imageGetter, null)
  } else {
    Html.fromHtml(html, imageGetter, null)
  }
}

private val indonesianLocale = Locale("id")
val imazineDateFormatter: DateTimeFormatter =
  DateTimeFormatter.ofPattern("dd MMMM yyyy", indonesianLocale)