package com.example.newsproject.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsproject.data.models.Doc
import com.example.newsproject.data.repository.RemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val remoteRepository: RemoteRepository):PagingSource<Int, Doc>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Doc> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = remoteRepository.getMovie(page, params.loadSize)
            if (response.isSuccessful){
                val movie = checkNotNull(response.body()?.docs)
                LoadResult.Page(
                    movie, prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (movie.isEmpty()) null else page + 1
                )
            }else LoadResult.Error(HttpException(response))

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Doc>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}