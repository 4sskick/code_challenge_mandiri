package id.niteroomcreation.archcomponent.domain.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import id.niteroomcreation.archcomponent.domain.data.remote.response.BaseResponse;
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;
import id.niteroomcreation.archcomponent.domain.data.remote.services.APIService;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse;
import id.niteroomcreation.archcomponent.util.LogHelper;
import retrofit2.Response;

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
public class RemoteRepoDataSource {

    public static final String TAG = RemoteRepoDataSource.class.getSimpleName();

    private volatile static RemoteRepoDataSource INSTANCE;
    private RemoteRepo remoteRepo;
    private Executor executor;

    private RemoteRepoDataSource() {
    }

    private RemoteRepoDataSource(RemoteRepo remoteRepo, Executor executor) {
        this.remoteRepo = remoteRepo;
        this.executor = executor;
    }

    public static RemoteRepoDataSource getInstance(Executor executor) {
        if (INSTANCE == null) {
            synchronized (RemoteRepoDataSource.class) {
                INSTANCE = new RemoteRepoDataSource(APIService.createService(), executor);
            }
        }

        return INSTANCE;
    }

    public LiveData<ApiResponse<List<Movies>>> getMovies(String lang) {
        MutableLiveData<ApiResponse<List<Movies>>> mData = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<BaseResponse<Movies>> m = remoteRepo.getMovies(lang).execute();

                    LogHelper.e(TAG, m.code(), m.message(), m.errorBody(), m.headers(), m.message());
                    if (m.isSuccessful() && m.body() != null)

                        mData.postValue(ApiResponse.success(m.body().getResults()));
                    else
                        mData.postValue(ApiResponse.error("Response service not available", null));
                } catch (Exception e) {

                    LogHelper.e(TAG, "here calling me!", e.getMessage());

                    mData.postValue(ApiResponse.error(e.getMessage(), null));

                    e.printStackTrace();
                }
            }
        });
        return mData;
    }


    public interface RemoteCallback {
        default void onMoviesCallback(List<Movies> data) {
        }

        default void onTvShowsCallback(List<TvShows> data) {

        }
    }
}
