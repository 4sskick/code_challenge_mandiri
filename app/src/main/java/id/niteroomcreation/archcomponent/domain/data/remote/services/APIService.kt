package id.niteroomcreation.archcomponent.domain.data.remote.services

import id.niteroomcreation.archcomponent.BuildConfig
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepo
import id.niteroomcreation.archcomponent.domain.data.remote.network.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
object APIService {
    private lateinit var api: RemoteRepo

    @JvmStatic
    fun createService(): RemoteRepo {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //set logging level to NONE
        //so there is no log information while request
        //see: https://futurestud.io/blog/retrofit-2-log-requests-and-responses
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(NetworkInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .build()
        getApi(okHttpClient)
        return api
    }

    private fun getApi(okHttpClient: OkHttpClient) {
        api = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RemoteRepo::class.java)
    }
}