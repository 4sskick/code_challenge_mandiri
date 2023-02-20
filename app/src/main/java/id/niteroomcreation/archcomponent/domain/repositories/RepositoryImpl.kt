package id.niteroomcreation.archcomponent.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import id.niteroomcreation.archcomponent.domain.data.remote.response.genre.Genre
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.by_id.MoviesById
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse

/**
 * Created by Septian Adi Wijaya on 02/06/2021.
 * please be sure to add credential if you use people's code
 */
interface RepositoryImpl {

    fun getMovies(): LiveData<PagingData<Movies>>

    suspend fun getMovieById(id: Int): ApiResponse<MoviesById>

    suspend fun getGenre(): ApiResponse<Genre>

    fun getReviewByMovie(id: Int): LiveData<PagingData<MovieReviews>>
}