package id.niteroomcreation.archcomponent.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse;
import id.niteroomcreation.archcomponent.util.AppExecutors;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 30/05/2021.
 * please be sure to add credential if you use people's code
 */
public class FakeRepository implements RepositoryImpl {

    private FakeRemoteRepoDataSource remoteRepoDataSource;
    private AppExecutors appExecutors;
    private LocalDataSource localDataSource;

    public FakeRepository(FakeRemoteRepoDataSource remoteRepoDataSource, LocalDataSource localDataSource, AppExecutors appExecutors) {
        this.remoteRepoDataSource = remoteRepoDataSource;
        this.appExecutors = appExecutors;
        this.localDataSource = localDataSource;
    }

    public LiveData<PagedList<FavouriteEntity>> getFavourites() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build();

        return new LivePagedListBuilder<>(localDataSource.getFavourites(), config).build();
    }


}
