package id.niteroomcreation.archcomponent.feature.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.niteroomcreation.archcomponent.base.BaseViewModel
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
class MoviesViewModel(private val repository: RepositoryImpl) : BaseViewModel() {
    companion object {
        val TAG = MoviesViewModel::class.java.simpleName
    }

    fun getMovies(): LiveData<PagingData<Movies>> {
        return repository.getMovies().cachedIn(viewModelScope)
    }
}