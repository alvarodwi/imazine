package com.himatifunpad.imazine.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.himatifunpad.imazine.core.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("prefs")

class DataStoreManager @Inject constructor(@ApplicationContext appContext : Context) {
 private val prefsDataStore = appContext.dataStore

  suspend fun setUser(user : User){
    prefsDataStore.edit { prefs->
     prefs[Keys.NAMA] = user.nama
     prefs[Keys.NPM] = user.npm
    }
  }

 suspend fun clearUser() {
  prefsDataStore.edit { prefs->
   prefs[Keys.NAMA] = ""
   prefs[Keys.NPM] = ""
  }
 }

 val user : Flow<User> = prefsDataStore.data.map { prefs ->
  User(
   nama = prefs[Keys.NAMA] ?: "",
   npm = prefs[Keys.NPM] ?: "",
  )
 }
}