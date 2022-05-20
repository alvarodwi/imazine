package com.himatifunpad.imazine.core.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
  val NAMA = "nama"
  val NPM = "npm"
  val LATEST_POST_ID = "latest_post_id"
  val APP_THEME ="dark_mode"
  val NOTIFY_NEW_POST = "check_post_update"
}

const val APP_THEME_LIGHT = 1
const val APP_THEME_DARK = 2
const val APP_THEME_SYSTEM = 3