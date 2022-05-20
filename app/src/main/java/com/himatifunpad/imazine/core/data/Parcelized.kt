package com.himatifunpad.imazine.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelizedPost(
  val id: Long,
  val date: String,
  val slug: String,
  val title: String,
  val content: String,
  val cover: String,
  val category: String
) : Parcelable