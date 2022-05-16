package com.himatifunpad.imazine.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.databinding.FragmentAuthBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.ui.auth.AuthViewModel.AuthEvent
import com.himatifunpad.imazine.util.base.BaseEvent.ShowErrorMessage
import com.himatifunpad.imazine.util.base.BaseFragment
import com.himatifunpad.imazine.util.ext.createSnackbar
import com.himatifunpad.imazine.util.ext.snackbar
import com.himatifunpad.imazine.util.ext.trimmedText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import logcat.logcat

class AuthFragment : BaseFragment(R.layout.fragment_auth) {
  private val binding by viewBinding<FragmentAuthBinding>()
  private val viewModel: AuthViewModel by viewModels()

  private val tfUsername get() = binding.tfNpm
  private val tfPassword get() = binding.tfPassword
  private val btnLogin get() = binding.btnLogin
  private val progressBar get() = binding.progressBar

  override fun onStart() {
    super.onStart()
    eventJob = viewModel.events
      .onEach { event ->
        when (event) {
          AuthEvent.LoginSuccess -> {
            toggleLoading(false)
            snackbar("Login Success")
          }
          is ShowErrorMessage -> {
            toggleLoading(false)
            snackbar("Error : ${event.message}")
          }
        }
      }.launchIn(viewLifecycleOwner.lifecycleScope)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()
  }

  override fun setupView() {

    btnLogin.setOnClickListener { onBtnLoginClick() }
  }

  private fun toggleLoading(show: Boolean) {
    progressBar.isVisible = show
  }

  private fun onBtnLoginClick() {
    toggleLoading(true)
    val npm = tfUsername.trimmedText()
    val password = tfPassword.trimmedText()
    viewModel.doLogin(npm, password)
  }
}