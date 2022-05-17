package com.himatifunpad.imazine.ui.screen.article.detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.databinding.FragmentArticleDetailBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.util.base.BaseFragment

class ArticleDetailFragment : BaseFragment(R.layout.fragment_article_detail) {
  private val binding by viewBinding<FragmentArticleDetailBinding>()
  private val viewModel by viewModels<ArticleDetailViewModel>()
  private val args by navArgs<ArticleDetailFragmentArgs>()



  override fun setupView() {
    TODO("Not yet implemented")
  }
}