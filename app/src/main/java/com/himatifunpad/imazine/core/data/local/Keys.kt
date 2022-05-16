package com.himatifunpad.imazine.core.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Keys {
  val NAMA = stringPreferencesKey("nama")
  val NPM = stringPreferencesKey("npm")
  val LATEST_POST_ID = longPreferencesKey("latest_post_id")
  val DARK_MODE = intPreferencesKey("dark_mode")
  val CHECK_POST_UPDATE = booleanPreferencesKey("check_post_update")
}