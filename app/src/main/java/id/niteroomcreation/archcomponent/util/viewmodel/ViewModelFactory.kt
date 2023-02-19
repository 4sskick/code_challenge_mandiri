package id.niteroomcreation.archcomponent.util.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.niteroomcreation.archcomponent.domain.di.Injector.provideRepository
import id.niteroomcreation.archcomponent.domain.repositories.Repository
import id.niteroomcreation.archcomponent.feature.dashboard.DashboardViewModel
import id.niteroomcreation.archcomponent.feature.detail.DetailViewModel
import id.niteroomcreation.archcomponent.feature.empty.EmptyViewModel
import id.niteroomcreation.archcomponent.feature.movies.MoviesViewModel
import id.niteroomcreation.archcomponent.feature.splash.SplashViewModel

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {

        @Volatile
        private lateinit var INSTANCE: ViewModelFactory

        @Synchronized
        fun getInstance(context: Context?): ViewModelFactory {
            INSTANCE = ViewModelFactory(
                provideRepository(context)
            )
            return INSTANCE
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EmptyViewModel::class.java))
            return EmptyViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}