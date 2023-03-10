package id.niteroomcreation.archcomponent.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource
import id.niteroomcreation.archcomponent.domain.data.paging.MovieReviewsPagingSource
import id.niteroomcreation.archcomponent.domain.data.paging.MoviesPagingSource
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepoDataSource
import id.niteroomcreation.archcomponent.domain.data.remote.response.genre.Genre
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.by_id.MoviesById
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse
import id.niteroomcreation.archcomponent.util.AppExecutors

/**
 * Created by Septian Adi Wijaya on 27/05/2021.
 * please be sure to add credential if you use people's code
 */
class Repository(
    private val remoteRepoDataSource: RemoteRepoDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : RepositoryImpl {

    override fun getMovies(): LiveData<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { MoviesPagingSource() }
        ).liveData
    }

    override suspend fun getMovieById(id: Int): ApiResponse<MoviesById> {
        return remoteRepoDataSource.getMovieById(id)
    }

    override suspend fun getGenre(): ApiResponse<Genre> {
        return remoteRepoDataSource.getGenre()
    }

    override fun getReviewByMovie(id: Int): LiveData<PagingData<MovieReviews>> {
//        return remoteRepoDataSource.getReviewByMovie(id)
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {MovieReviewsPagingSource(id)}
        ).liveData
    }

    companion object {
        val TAG = Repository::class.java.simpleName
        private lateinit var INSTANCE: Repository

        //would be add local resource data as param
        @Synchronized
        fun getInstance(
            remoteRepoDataSource: RemoteRepoDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository {
            INSTANCE = Repository(remoteRepoDataSource, localDataSource, appExecutors)
            return INSTANCE
        }
    }
}