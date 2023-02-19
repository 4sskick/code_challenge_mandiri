package id.niteroomcreation.archcomponent.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.util.vo.Resource

/**
 * Created by Septian Adi Wijaya on 02/06/2021.
 * please be sure to add credential if you use people's code
 */
interface RepositoryImpl {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieById(id: Int): LiveData<MovieEntity>
}