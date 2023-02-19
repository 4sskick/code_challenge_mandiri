package id.niteroomcreation.archcomponent.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepoDataSource;
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.NetworkBoundResource;
import id.niteroomcreation.archcomponent.util.AppExecutors;
import id.niteroomcreation.archcomponent.util.testing.EspressoIdlingResource;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 27/05/2021.
 * please be sure to add credential if you use people's code
 */
public class Repository implements RepositoryImpl {

    public static final String TAG = Repository.class.getSimpleName();

    private static Repository INSTANCE;

    private RemoteRepoDataSource remoteRepoDataSource;
    private LocalDataSource localDataSource;
    private AppExecutors appExecutors;

    private Repository() {

    }

    private Repository(RemoteRepoDataSource remoteRepoDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.localDataSource = localDataSource;
        this.remoteRepoDataSource = remoteRepoDataSource;
        this.appExecutors = appExecutors;
    }

    //would be add local resource data as param
    public static Repository getInstance(RemoteRepoDataSource remoteRepoDataSource
            , LocalDataSource localDataSource
            , AppExecutors appExecutors) {

        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository(remoteRepoDataSource, localDataSource, appExecutors);
            }
        }
        return INSTANCE;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getMovies() {
        EspressoIdlingResource.increment();

        return new NetworkBoundResource<PagedList<MovieEntity>, List<Movies>>(appExecutors) {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {


                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();

                LivePagedListBuilder builder = new LivePagedListBuilder<>(localDataSource.getMovies(), config);

                EspressoIdlingResource.decrement();

                return builder.build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return (data == null || data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<Movies>>> createCall() {
                EspressoIdlingResource.increment();

                return remoteRepoDataSource.getMovies(BuildConfig.LANG_VER_ID);
            }

            @Override
            protected void saveCallResult(List<Movies> data) {
                List<MovieEntity> ml = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    MovieEntity m = new MovieEntity();
                    m.setId(data.get(i).getId());
                    m.setName(data.get(i).getTitle());
                    m.setDesc(data.get(i).getOverview());
                    m.setPosterPath(data.get(i).getPosterPath());
                    m.setRatePercentage(data.get(i).getVoteAverage());
                    m.setYear(data.get(i).getReleaseDate());

                    ml.add(m);
                }

                localDataSource.insertMovies(ml);

                EspressoIdlingResource.decrement();
            }

        }.asLiveData();
    }


    @Override
    public LiveData<MovieEntity> getMovieById(int id) {
//        EspressoIdlingResource.increment();
//        LiveData<MovieEntity> m = localDataSource.getMovieById((long) id);
//        EspressoIdlingResource.decrement();
//        return m;

        return new MutableLiveData<>(localDataSource.getMovieById((long) id));
    }
}
