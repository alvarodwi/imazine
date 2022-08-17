package com.himatifunpad.imazine.ui.screen.article.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.core.data.parcelize
import com.himatifunpad.imazine.core.domain.model.Post
import com.himatifunpad.imazine.databinding.FragmentArticleListBinding
import com.himatifunpad.imazine.ui.ext.viewBinding
import com.himatifunpad.imazine.ui.adapter.PostAdapter
import com.himatifunpad.imazine.util.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleListFragment : BaseFragment(R.layout.fragment_article_list) {
  private val binding by viewBinding<FragmentArticleListBinding>()
  private val viewModel by viewModels<ArticleListViewModel>()
  private val args by navArgs<ArticleListFragmentArgs>()

  private val toolbar get() = binding.toolbar
  private val rvPosts get() = binding.content.rvPosts
  private val swipeRefresh get() = binding.swipeRefresh

  private lateinit var postAdapter: PostAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupView()

    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.allPosts.collectLatest(postAdapter::submitData)
    }

    viewLifecycleOwner.lifecycleScope.launch {
      postAdapter.loadStateFlow.collectLatest { loadState ->
        swipeRefresh.isRefreshing = loadState.refresh is LoadState.Loading
      }
    }
  }

  override fun setupView() {
    toolbar.title = args.categoryName
    toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    postAdapter = PostAdapter(
      imageLoader = imageLoader,
      onClick = { post ->
        moveToArticleDetail(post)
      }
    )
    rvPosts.adapter = postAdapter
    swipeRefresh.setOnRefreshListener { refresh() }
  }

  private fun refresh() {
    postAdapter.refresh()
  }

  private fun toggleLoading(show: Boolean) {
    swipeRefresh.isRefreshing = show
  }

  private fun moveToArticleDetail(post : Post) {
    findNavController().navigate(
      ArticleListFragmentDirections.actionArticleListToArticleDetail(post.parcelize())
    )
  }
}