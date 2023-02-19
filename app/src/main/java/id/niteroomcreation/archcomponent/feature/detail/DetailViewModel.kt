package id.niteroomcreation.archcomponent.feature.detail

import androidx.lifecycle.LiveData
import id.niteroomcreation.archcomponent.base.BaseViewModel
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
class DetailViewModel(private val repository: RepositoryImpl) : BaseViewModel() {

    private var objId = 0

    fun setSelectedEntity(objId: Int) {
        this.objId = objId
    }

    val movieById: LiveData<MovieEntity> = repository.getMovieById(objId)
}