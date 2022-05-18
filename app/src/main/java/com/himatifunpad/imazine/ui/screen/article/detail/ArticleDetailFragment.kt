package com.himatifunpad.imazine.ui.screen.article.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.request.ImageRequest
import com.himatifunpad.imazine.R
import com.himatifunpad.imazine.core.domain.model.Post
import com.himatifunpad.imazine.databinding.FragmentArticleDetailBinding
import com.himatifunpad.imazine.ext.viewBinding
import com.himatifunpad.imazine.ui.ext.imazineDateFormatter
import com.himatifunpad.imazine.ui.ext.parseHtmlWithImage
import com.himatifunpad.imazine.ui.ext.snackbar
import com.himatifunpad.imazine.ui.screen.article.detail.ArticleDetailViewModel.ArticleDetailEvent.ArticleLoaded
import com.himatifunpad.imazine.util.base.BaseEvent.ShowErrorMessage
import com.himatifunpad.imazine.util.base.BaseFragment
import com.himatifunpad.imazine.util.scrapeContent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ArticleDetailFragment : BaseFragment(R.layout.fragment_article_detail) {
  private val binding by viewBinding<FragmentArticleDetailBinding>()
  private val viewModel by viewModels<ArticleDetailViewModel>()

  private val toolbar get() = binding.toolbar
  private val img get() = binding.content.ivPostImg
  private val title get() = binding.content.tvTitle
  private val content get() = binding.content.tvContent
  private val date get() = binding.content.tvDate

  override fun onStart() {
    super.onStart()
    eventJob = viewModel.events
      .onEach { event ->
        when (event) {
          is ShowErrorMessage -> {
            snackbar("Error : ${event.message}")
          }
          is ArticleLoaded -> {
            displayPost(event.post)
          }
        }
      }.launchIn(viewLifecycleOwner.lifecycleScope)
  }

  override fun setupView() {
    toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
  }

  private fun displayPost(post: Post) {
    img.apply {
      val imgData = ImageRequest.Builder(this.context)
        .data(post.cover)
        .target(this)
        .allowHardware(true)
        .build()
      imageLoader.enqueue(imgData)
    }
    title.text = post.title
    date.text =
      imazineDateFormatter.format(LocalDate.parse(post.date, DateTimeFormatter.ISO_DATE_TIME))
    content.parseHtmlWithImage(post.scrapeContent())
  }
}