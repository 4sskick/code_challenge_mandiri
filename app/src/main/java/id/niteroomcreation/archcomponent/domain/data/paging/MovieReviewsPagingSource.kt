package id.niteroomcreation.archcomponent.domain.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.domain.data.remote.services.APIConfig

/**
 * Created by Septian Adi Wijaya on 20/02/2023.
 * please be sure to add credential if you use people's code
 */
class MovieReviewsPagingSource(private val movieId: Int) : PagingSource<Int, MovieReviews>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, MovieReviews>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReviews> = try {
        val page = params.key ?: INITIAL_PAGE_INDEX
        val response = APIConfig.getApi().getReviewByMovie(id = movieId, page = page)

        if (response.isSuccessful) {
            LoadResult.Page(
                data = response.body()?.results ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.body()?.results.isNullOrEmpty()) null else page.plus(1)
            )
        } else
            LoadResult.Error(Exception("Failed load reviews"))
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}