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
 * Created by Septian Adi Wijaya on 29/01/2023.
 * please be sure to add credential if you use people's code
 */
class APIConfig {

    companion object {
        fun getApi(): RemoteRepo {
            val loggingInterceptor = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

            val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(NetworkInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RemoteRepo::class.java)
        }
    }
}