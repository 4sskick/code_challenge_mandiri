package id.niteroomcreation.archcomponent.domain.data.remote.network;

import java.io.IOException;

import id.niteroomcreation.archcomponent.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build();

        request = request.newBuilder()
                .addHeader("content-type", "application/json")
                .url(url)
                .method(request.method(), request.body())
                .build();

        return chain.proceed(request);
    }
}