package com.himatifunpad.imazine.ui.screen.article.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.databinding.FragmentArticleListBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.util.base.BaseFragment

class ArticleListFragment : BaseFragment(R.layout.fragment_article_list) {
  private val binding by viewBinding<FragmentArticleListBinding>()
  private val viewModel by viewModels<ArticleListViewModel>()
  private val args by navArgs<ArticleListFragmentArgs>()

  override fun setupView() {
    TODO("Not yet implemented")
  }
}