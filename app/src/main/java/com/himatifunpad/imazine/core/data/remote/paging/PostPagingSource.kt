package com.himatifunpad.imazine.core.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.himatifunpad.imazine.core.data.remote.api.WpApiService
import com.himatifunpad.imazine.core.data.remote.json.PostJson
import com.himatifunpad.imazine.core.data.toModel
import com.himatifunpad.imazine.core.domain.model.Post
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource(
  private val api: WpApiService,
  private val categoryId: Int? = null
) : PagingSource<Int, Post>() {
  override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
    return try {
      val nextPage = params.key ?: 1
      val response = api.getPosts(
        page = nextPage,
        category = categoryId
      )
      val pagedResponse = response.body()
      val data = pagedResponse?.map(PostJson::toModel)

      LoadResult.Page(
        data = data.orEmpty(),
        prevKey = if (nextPage == 1) null else nextPage - 1,
        nextKey = if (data.isNullOrEmpty()) null else nextPage + 1
      )
    } catch (ex: HttpException) {
      return LoadResult.Error(ex)
    } catch (ex: IOException) {
      return LoadResult.Error(ex)
    }
  }
}