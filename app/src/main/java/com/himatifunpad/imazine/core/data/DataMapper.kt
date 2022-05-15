package com.himatifunpad.imazine.core.data

import com.himatifunpad.imazine.core.data.remote.json.UserJson
import com.himatifunpad.imazine.core.domain.model.User

fun UserJson.asModel() =
  User(
    nama = this.nama,
    npm = this.npm
  )