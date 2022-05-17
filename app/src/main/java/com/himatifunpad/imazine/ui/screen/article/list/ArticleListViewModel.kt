package com.himatifunpad.imazine.ui.screen.article.list

import com.himatifunpad.imazine.core.domain.repository.PostRepository
import com.himatifunpad.imazine.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
  private val post: PostRepository
) : BaseViewModel(){

}