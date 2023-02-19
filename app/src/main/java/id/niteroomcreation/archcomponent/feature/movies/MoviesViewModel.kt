package id.niteroomcreation.archcomponent.feature.movies

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.niteroomcreation.archcomponent.base.BaseViewModel
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl
import id.niteroomcreation.archcomponent.feature.movies.MoviesViewModel
import id.niteroomcreation.archcomponent.util.vo.Resource

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
class MoviesViewModel(private val repository: RepositoryImpl) : BaseViewModel() {
    companion object {
        val TAG = MoviesViewModel::class.java.simpleName
    }

    val movies: LiveData<Resource<PagedList<MovieEntity>>> = repository.movies
}