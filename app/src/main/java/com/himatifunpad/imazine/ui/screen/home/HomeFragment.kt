package com.himatifunpad.imazine.ui.screen.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.request.ImageRequest
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.core.domain.model.Post
import com.himatifunpad.imazine.databinding.FragmentHomeBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.ui.adapter.CategoryAdapter
import com.himatifunpad.imazine.ui.adapter.PostAdapter
import com.himatifunpad.imazine.ui.adapter.PostLoadStateAdapter
import com.himatifunpad.imazine.ui.ext.snackbar
import com.himatifunpad.imazine.ui.screen.home.HomeViewModel.HomeEvent
import com.himatifunpad.imazine.util.base.BaseEvent.ShowErrorMessage
import com.himatifunpad.imazine.util.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(R.layout.fragment_home) {
  private val binding by viewBinding<FragmentHomeBinding>()
  private val viewModel: HomeViewModel by viewModels()

  private val rvCategory get() = binding.content.rvCategory
  private val rvAllPosts get() = binding.content.allPosts.rvPosts
  private val latestPost get() = binding.content.latestPost
  private val swipeRefresh get() = binding.swipeRefresh

  private lateinit var postAdapter: PostAdapter
  private lateinit var categoryAdapter: CategoryAdapter

  override fun onStart() {
    super.onStart()
    eventJob = viewModel.events
      .onEach { event ->
        when (event) {
          is HomeEvent.UpdateLatestPost -> {
            toggleLoading(false)
            updateLatestPost(event.post)
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
    refresh()

    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.categories.collectLatest { list ->
        categoryAdapter.submitList(list)
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      viewModel.allPosts.collectLatest(postAdapter::submitData)
    }
  }

  override fun setupView() {
    swipeRefresh.setOnRefreshListener { refresh() }

    categoryAdapter = CategoryAdapter(
      onClick = {
        // do nothing
      }
    )
    rvCategory.adapter = categoryAdapter

    postAdapter = PostAdapter(
      imageLoader = imageLoader,
      onClick = {
        // do nothing
      }
    )
    rvAllPosts.adapter = postAdapter.withLoadStateHeaderAndFooter(
      header = PostLoadStateAdapter(postAdapter::retry),
      footer = PostLoadStateAdapter(postAdapter::retry)
    )
  }

  private fun refresh() {
    toggleLoading(true)
    viewModel.onRefresh()
    postAdapter.refresh()
  }

  private fun toggleLoading(show: Boolean) {
    swipeRefresh.isRefreshing = show
  }

  private fun updateLatestPost(post: Post) {
    with(latestPost) {
      ivFeaturedImg.apply {
        val imgData = ImageRequest.Builder(this.context)
          .data(post.cover)
          .target(this)
          .allowHardware(true)
          .build()
        imageLoader.enqueue(imgData)
      }
      tvCategory.text = post.category
      tvTitle.text = post.title
    }
  }
}