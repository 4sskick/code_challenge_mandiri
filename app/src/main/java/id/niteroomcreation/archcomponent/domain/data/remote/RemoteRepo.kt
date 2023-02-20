package id.niteroomcreation.archcomponent.domain.data.remote

import id.niteroomcreation.archcomponent.BuildConfig
import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse
import id.niteroomcreation.archcomponent.domain.data.remote.response.genre.Genre
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.by_id.MoviesById
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
interface RemoteRepo {
    @GET(BuildConfig.BASE_PATH + "movie")
    fun getMovies(@Query("language") lang: String?): Call<BaseResponse<Movies>>

    @GET(BuildConfig.BASE_PATH + "movie")
    suspend fun getMovies(
        @Query("page") page: Int,
//        @Query("size") size: Int,
    ): Response<BaseResponse<Movies>>

    @GET("3/movie/{movie_id}")
    suspend fun getMoviesDetail(@Path("movie_id") id: Int): Response<MoviesById>


    @GET("3/genre/movie")
    suspend fun getGenre(): Response<Genre>

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getReviewByMovie(@Path("movie_id") id: Int): Response<BaseResponse<MovieReviews>>
}