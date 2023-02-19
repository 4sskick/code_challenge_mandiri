package id.niteroomcreation.archcomponent.domain.data.remote.services;

import java.util.concurrent.TimeUnit;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepo;
import id.niteroomcreation.archcomponent.domain.data.remote.network.NetworkInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
public class APIService {
    private static RemoteRepo api;

    public static RemoteRepo createService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //set logging level to NONE
        //so there is no log information while request
        //see: https://futurestud.io/blog/retrofit-2-log-requests-and-responses
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new NetworkInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .build();

        getApi(okHttpClient);

        return api;
    }

    private static void getApi(OkHttpClient okHttpClient) {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                    .create(RemoteRepo.class);
        }

    }
}
