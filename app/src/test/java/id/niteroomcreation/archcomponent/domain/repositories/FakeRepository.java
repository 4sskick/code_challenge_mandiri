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
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.ApiResponse;
import id.niteroomcreation.archcomponent.domain.data.remote.utils.NetworkBoundResource;
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

    public LiveData<Resource<PagedList<MovieEntity>>> getMovies() {

        return new NetworkBoundResource<PagedList<MovieEntity>, List<Movies>>(appExecutors) {

            @Override
            protected LiveData<PagedList<MovieEntity>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();

                return new LivePagedListBuilder<>(localDataSource.getMovies(), config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<MovieEntity> data) {
                return (data == null || data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<Movies>>> createCall() {
                return remoteRepoDataSource.getMovies();
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
            }

        }.asLiveData();
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> getTvShows() {

        return new NetworkBoundResource<PagedList<TvShowEntity>, List<TvShows>>(appExecutors) {

            @Override
            protected LiveData<PagedList<TvShowEntity>> loadFromDB() {
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build();

                return new LivePagedListBuilder<>(localDataSource.getTvShows(), config).build();
            }

            @Override
            protected Boolean shouldFetch(PagedList<TvShowEntity> data) {
                return (data == null || data.size() == 0);
            }

            @Override
            protected LiveData<ApiResponse<List<TvShows>>> createCall() {
                return remoteRepoDataSource.getTvShows();
            }

            @Override
            protected void saveCallResult(List<TvShows> data) {
                List<TvShowEntity> tl = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    TvShowEntity t = new TvShowEntity();
                    t.setId(data.get(i).getId());
                    t.setName(data.get(i).getName());
                    t.setDesc(data.get(i).getOverview());
                    t.setPosterPath(data.get(i).getPosterPath());
                    t.setRatePercentage(data.get(i).getVoteAverage());
                    t.setYear(data.get(i).getFirstAirDate());

                    tl.add(t);
                }

                localDataSource.insertTvs(tl);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<PagedList<FavouriteEntity>> getFavourites() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build();

        return new LivePagedListBuilder<>(localDataSource.getFavourites(), config).build();
    }

    @Override
    public LiveData<MovieEntity> getMovieById(int id) {
        return new MutableLiveData<>(localDataSource.getMovieById((long) id));
    }

    @Override
    public LiveData<TvShowEntity> getTvShowById(int id) {
        return new MutableLiveData<>(localDataSource.getTvById((long) id));
    }

    @Override
    public void updateTvShow(TvShowEntity entity) {
        localDataSource.updateTv(entity);
    }

    @Override
    public void updateMovie(MovieEntity entity) {
        localDataSource.updateMovie(entity);
    }

    @Override
    public void insertIntoFav(FavouriteEntity entity) {
        localDataSource.insertIntoFav(entity);
    }

    @Override
    public void deleteFav(FavouriteEntity entity) {
        localDataSource.deleteFav(entity);
    }
}
