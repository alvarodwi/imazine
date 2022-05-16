package com.himatifunpad.imazine.ui.auth

import androidx.lifecycle.viewModelScope
import com.himatifunpad.imazine.core.domain.repository.AuthRepository
import com.himatifunpad.imazine.ui.auth.AuthViewModel.AuthEvent.LoginSuccess
import com.himatifunpad.imazine.util.base.BaseEvent
import com.himatifunpad.imazine.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
  private val auth: AuthRepository
) : BaseViewModel() {
  sealed class AuthEvent : BaseEvent() {
    object LoginSuccess : AuthEvent()
  }

  fun doLogin(username: String, password: String) =
    viewModelScope.launch {
      if(username.isEmpty() || password.isEmpty()){
        setErrorMessage("NPM / password tidak boleh kosong")
        return@launch
      }
      auth.login(username, password)
        .catch {
          setErrorMessage(it.message)
        }
        .collect { result ->
          if (result.isSuccess) sendNewEvent(LoginSuccess)
          if (result.isFailure) setErrorMessage(result.exceptionOrNull()?.message)
        }
    }
}