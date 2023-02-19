package id.niteroomcreation.archcomponent.domain.repositories;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse;
import id.niteroomcreation.archcomponent.util.testing.EspressoIdlingResource;
import id.niteroomcreation.archcomponent.util.testing.JsonHelper;

/**
 * Created by Septian Adi Wijaya on 30/05/2021.
 * please be sure to add credential if you use people's code
 */
public class FakeRemoteRepoDataSource {

    private JsonHelper jsonHelper;
    private Handler handler = new Handler(Looper.getMainLooper());

    private FakeRemoteRepoDataSource(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public LiveData<ApiResponse<List<Movies>>> getMovies() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<Movies>>> mData = new MutableLiveData<>();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.setValue(ApiResponse.success(jsonHelper.loadMovies()));
                EspressoIdlingResource.decrement();
            }
        }, 1000);

        return mData;
    }

    public LiveData<ApiResponse<List<TvShows>>> getTvShows() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<TvShows>>> mData = new MutableLiveData<>();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.setValue(ApiResponse.success(jsonHelper.loadTvShows()));
                EspressoIdlingResource.decrement();
            }
        }, 1000);
        return mData;
    }

//    public interface RemoteCallback {
//        default void onMoviesCallback(List<Movies> data) {
//
//        }
//
//        default void onTvShowsCallback(List<TvShows> data) {
//
//        }
//    }
}
