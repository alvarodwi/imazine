package com.himatifunpad.imazine.core.data

import com.himatifunpad.imazine.core.data.local.DataStoreManager
import com.himatifunpad.imazine.core.data.remote.SafeApiRequest
import com.himatifunpad.imazine.core.data.remote.api.HdaApiService
import com.himatifunpad.imazine.core.domain.model.User
import com.himatifunpad.imazine.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val prefs: DataStoreManager,
  private val api: HdaApiService,
) : AuthRepository, SafeApiRequest() {
  override suspend fun login(username: String, password: String): Flow<User> = flow {
    val user = apiRequest { api.login(username, password) }.user.asModel()
    saveUser(user)
    emit(user)
  }

  override suspend fun logout() {
    saveUser(User(nama = "", npm = ""))
  }

  private suspend fun saveUser(user: User) {
    prefs.setUser(user)
  }

  override fun getUser(): Flow<User> = prefs.user
}