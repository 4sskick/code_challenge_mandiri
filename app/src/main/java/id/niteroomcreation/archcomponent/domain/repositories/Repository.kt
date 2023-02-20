package id.niteroomcreation.archcomponent.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.data.paging.MoviesPagingSource
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepoDataSource
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies
import id.niteroomcreation.archcomponent.util.AppExecutors

/**
 * Created by Septian Adi Wijaya on 27/05/2021.
 * please be sure to add credential if you use people's code
 */
class Repository (
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

    override suspend fun getMovieById(id: Int): LiveData<MovieEntity> {
//        return MutableLiveData(localDataSource.getMovieById(id.toLong()))
        return MutableLiveData()
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