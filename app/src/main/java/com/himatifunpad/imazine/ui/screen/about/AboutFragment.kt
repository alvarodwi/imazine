package com.himatifunpad.imazine.ui.screen.about

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.himatifunpad.imazine.BuildConfig
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.databinding.FragmentAboutBinding
import com.himatifunpad.imazine.ui.ext.viewBinding
import com.himatifunpad.imazine.util.IG_URL
import com.himatifunpad.imazine.util.LINE_URL
import com.himatifunpad.imazine.util.LINKTREE_URL
import com.himatifunpad.imazine.util.MAIL_URL
import com.himatifunpad.imazine.util.WEBSITE_URL
import com.himatifunpad.imazine.util.base.BaseFragment

class AboutFragment : BaseFragment(R.layout.fragment_about) {
  private val binding by viewBinding<FragmentAboutBinding>()

  private val toolbar get() = binding.toolbar
  private val lblVersion get() = binding.lblVersion
  private val cardIg get() = binding.cardIg
  private val cardLine get() = binding.cardLine
  private val cardLinkTree get() = binding.cardLinktree
  private val cardWebsite get() = binding.cardWebsite
  private val cardMail get() = binding.cardMail

  override fun setupView() {
    toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    lblVersion.text = "Version " + BuildConfig.VERSION_NAME

    cardIg.setOnClickListener { startViewIntent(IG_URL) }
    cardLine.setOnClickListener { startViewIntent(LINE_URL) }
    cardLinkTree.setOnClickListener { startViewIntent(LINKTREE_URL) }
    cardWebsite.setOnClickListener { startViewIntent(WEBSITE_URL) }
    cardMail.setOnClickListener { startViewIntent(MAIL_URL) }
  }

  private fun startViewIntent(url: String) {
    Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse(url)
    }.also {
      startActivity(it)
    }
  }
}