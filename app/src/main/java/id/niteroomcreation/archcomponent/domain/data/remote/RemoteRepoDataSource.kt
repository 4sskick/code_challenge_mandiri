package id.niteroomcreation.archcomponent.domain.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse
import id.niteroomcreation.archcomponent.domain.data.remote.response.genre.Genre
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.by_id.MoviesById
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.domain.data.remote.services.APIConfig
import id.niteroomcreation.archcomponent.domain.data.remote.services.APIService
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse
import id.niteroomcreation.archcomponent.util.LogHelper
import java.util.concurrent.Executor

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
class RemoteRepoDataSource(private val remoteRepo: RemoteRepo, private val executor: Executor) {

    companion object {
        val TAG = RemoteRepoDataSource::class.java.simpleName

        @Volatile
        private lateinit var INSTANCE: RemoteRepoDataSource

        @Synchronized
        fun getInstance(executor: Executor): RemoteRepoDataSource {
            INSTANCE = RemoteRepoDataSource(
                APIService.createService(), executor
            )
            return INSTANCE
        }
    }

    fun getMovies(lang: String?): LiveData<ApiResponse<List<Movies>>> {
        val mData = MutableLiveData<ApiResponse<List<Movies>>>()
        executor.execute(Runnable {
            try {
                val m = remoteRepo.getMovies(lang).execute()

                LogHelper.e(TAG, m.code(), m.message(), m.errorBody(), m.headers(), m.message())

                if (m.isSuccessful && m.body() != null)
                    mData.postValue(ApiResponse.success(m.body()!!.results))
                else
                    mData.postValue(ApiResponse.error("Response service not available", null))
            } catch (e: Exception) {
                LogHelper.e(TAG, "here calling me!", e.message)
                mData.postValue(ApiResponse.error(e.message, null))
                e.printStackTrace()
            }
        })
        return mData
    }

    suspend fun getMovieById(id: Int): ApiResponse<MoviesById> = try {
        val response = APIConfig.getApi().getMoviesDetail(id)
        val result = response.body()

        if (response.isSuccessful && result != null)
            ApiResponse.success(result)
        else
            ApiResponse.error("Response service not available", null)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.error(e.message, null)
    }

    suspend fun getGenre(): ApiResponse<Genre> = try {
        val response = APIConfig.getApi().getGenre()
        val result = response.body()

        if (response.isSuccessful && result != null)
            ApiResponse.success(result)
        else
            ApiResponse.error("Response service not available", null)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.error(e.message, null)
    }

    suspend fun getReviewByMovie(movieId: Int): ApiResponse<BaseResponse<MovieReviews>> = try {
        val response = APIConfig.getApi().getReviewByMovie(movieId)
        val result = response.body()

        if (response.isSuccessful && result != null)
            ApiResponse.success(result)
        else
            ApiResponse.error("Response service not available", null)
    } catch (e: Exception) {
        e.printStackTrace()
        ApiResponse.error(e.message, null)
    }
}