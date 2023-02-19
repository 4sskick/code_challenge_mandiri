package id.niteroomcreation.archcomponent.domain.data.remote;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse;
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
public interface RemoteRepo {

    @GET(BuildConfig.BASE_PATH + "movie")
    Call<BaseResponse<Movies>> getMovies(@Query("language") String lang);

}
