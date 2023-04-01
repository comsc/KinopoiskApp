package com.example.newsproject.presentation.first.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MoviePagingSource(
    private val remoteRepository: RemoteRepository,
    val type:String
    ): PagingSource<Int, Doc>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Doc> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = withContext(Dispatchers.IO) {
                remoteRepository.getMovie(page, params.loadSize, type = type)
            }
            val movie = checkNotNull(response.body()?.docs)
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
            val nextKey = if (movie.isEmpty()) null else page.plus(1)
            if (response.isSuccessful) {
                LoadResult.Page(
                    data = movie,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                    nextKey = if (movie.isEmpty()) null else page.plus(1)
                )
            } else {
                LoadResult.Page(emptyList(), prevKey, nextKey)
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (e:Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Doc>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}