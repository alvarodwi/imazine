package com.himatifunpad.imazine.ui.home

import androidx.fragment.app.viewModels
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.databinding.FragmentHomeBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.util.base.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {
  private val binding by viewBinding<FragmentHomeBinding>()
  private val viewModel : HomeViewModel by viewModels()

  override fun setupView() {

  }
}