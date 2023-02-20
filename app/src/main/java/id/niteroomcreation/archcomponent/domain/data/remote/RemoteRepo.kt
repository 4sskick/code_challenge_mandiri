package id.niteroomcreation.archcomponent.domain.data.remote

import id.niteroomcreation.archcomponent.BuildConfig
import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
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
}