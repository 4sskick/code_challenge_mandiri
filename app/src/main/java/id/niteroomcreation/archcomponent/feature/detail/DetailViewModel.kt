package id.niteroomcreation.archcomponent.feature.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.niteroomcreation.archcomponent.base.BaseViewModel
import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.by_id.MoviesById
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
class DetailViewModel(private val repository: RepositoryImpl) : BaseViewModel() {

    private var respondResult_ = MutableLiveData<ApiResponse<MoviesById>>()
    val respondResult = respondResult_

    private var respondResultReview_ = MutableLiveData<ApiResponse<BaseResponse<MovieReviews>>>()
    val respondResultReview = respondResultReview_

    fun getDetail(id: Int) {
        viewModelScope.launch {
            respondResult_.postValue(repository.getMovieById(id))
        }
    }

    fun getMovieReview(id: Int) {
        viewModelScope.launch {
            respondResultReview_.postValue(repository.getReviewByMovie(id))
        }
    }

}