package id.niteroomcreation.archcomponent.domain.data.remote.network

import id.niteroomcreation.archcomponent.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val url = request.url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()

        var requestBuilder: Request.Builder =
            request.newBuilder().method(request.method, request.body)
                .header("content-type", "application/json")
                .url(url)


        return chain.proceed(requestBuilder.build())
    }
}