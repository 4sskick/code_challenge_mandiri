package id.niteroomcreation.archcomponent.domain.di

import android.content.Context
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepoDataSource
import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource
import id.niteroomcreation.archcomponent.domain.repositories.Repository
import id.niteroomcreation.archcomponent.util.AppExecutors
import java.util.concurrent.Executor

/**
 * Created by Septian Adi Wijaya on 27/05/2021.
 * please be sure to add credential if you use people's code
 */
object Injector {
    @JvmStatic
    fun provideRepository(context: Context?): Repository {
        val remoteRepoDataSource = RemoteRepoDataSource.getInstance(provideExecutor())
        val localDataSource = LocalDataSource.getInstance(context)
        val appExecutors = AppExecutors()
        return Repository.getInstance(remoteRepoDataSource, localDataSource, appExecutors)
    }

    fun provideExecutor(): Executor {
        return Executor { command -> Thread(command).start() }
    }
}