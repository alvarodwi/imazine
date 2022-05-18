package com.himatifunpad.imazine.util.coil

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import logcat.logcat

// from https://github.com/Commit451/coil-imagegetter

class CoilImageGetter(
  private val textView: TextView,
  private val matchParent: Boolean = true,
  private val sourceModifier: ((source: String) -> String)? = null
) : Html.ImageGetter {
  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface CoilImageGetterEntryPoint {
    fun coilLoader(): ImageLoader
  }

  override fun getDrawable(source: String): Drawable {
    logcat { source }

    val drawablePlaceholder = DrawablePlaceHolder()
    val entryPoint =
      EntryPointAccessors.fromApplication(
        textView.context,
        CoilImageGetterEntryPoint::class.java
      )
    entryPoint.coilLoader().enqueue(
      ImageRequest.Builder(textView.context)
        .data(source)
        .apply {
          size(textView.width)
          target { drawable ->
            drawablePlaceholder.updateDrawable(drawable)
            // invalidating the drawable doesn't seem to be enough...
            textView.text = textView.text
          }
        }.build()
    )
    // Since this loads async, we return a "blank" drawable, which we update
    // later
    return drawablePlaceholder
  }

  @Suppress("DEPRECATION")
  inner class DrawablePlaceHolder : BitmapDrawable() {

    private var drawable: Drawable? = null

    override fun draw(canvas: Canvas) {
      drawable?.draw(canvas)
    }

    fun updateDrawable(drawable: Drawable) {
      this.drawable = drawable

      val width = drawable.intrinsicWidth
      val height = drawable.intrinsicHeight

      drawable.setBounds(0, 0, width, height)
      setBounds(0, 0, width, height)
    }
  }
}
