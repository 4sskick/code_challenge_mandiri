package id.niteroomcreation.archcomponent.domain.data.local;

import android.content.Context;

import androidx.paging.DataSource;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.dao.EntertainmentDatabase;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.di.Injector;

/**
 * Created by monta on 10/06/21
 * please make sure to use credit when using people code
 **/
public class LocalDataSource {

    public static final String TAG = LocalDataSource.class.getSimpleName();

    private static LocalDataSource INSTANCE;
    private final EntertainmentDatabase db;

    private LocalDataSource(Context context) {
        db = EntertainmentDatabase.getInstance(context);
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                INSTANCE = new LocalDataSource(Injector.provideContext());
            }
        }
        return INSTANCE;
    }

    private EntertainmentDatabase getDb() {
        return db;
    }

    public long[] insertMovies(List<MovieEntity> movieEntities) {
        return getDb().moviesDao().insertMovies(movieEntities);
    }

    public long[] insertTvs(List<TvShowEntity> tvShowEntities) {
        return getDb().tvShowsDao().insertTvs(tvShowEntities);
    }

    public MovieEntity getMovieById(long id) {
        return getDb().moviesDao().getMovieById(id);
    }

    public TvShowEntity getTvById(long id) {
        return getDb().tvShowsDao().getTvById(id);
    }

    public int updateMovie(MovieEntity entity) {
        return getDb().moviesDao().updateMovie(entity);
    }

    public int updateTv(TvShowEntity entity) {
        return getDb().tvShowsDao().updateTv(entity);
    }

    public DataSource.Factory<Integer, MovieEntity> getMovies() {
        return getDb().moviesDao().getMovies();
    }

    public DataSource.Factory<Integer, TvShowEntity> getTvShows() {
        return getDb().tvShowsDao().getTvShows();
    }

    public DataSource.Factory<Integer, FavouriteEntity> getFavourites() {
        return getDb().favouriteDao().getFavourites();
    }

    public long insertIntoFav(FavouriteEntity entity) {
        return getDb().favouriteDao().insertFavourite(entity);
    }

    public void deleteFav(FavouriteEntity entity) {
        getDb().favouriteDao().deleteFavourite(entity);
    }

    public DataSource.Factory<Integer, MovieEntity> getMoviesBookmarked() {
        return getDb().moviesDao().getFavMovies();
    }

    public DataSource.Factory<Integer, TvShowEntity> getTvsBookmarked() {
        return getDb().tvShowsDao().getFavsTv();
    }
}
