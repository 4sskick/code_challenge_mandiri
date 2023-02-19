package id.niteroomcreation.archcomponent.domain.repositories;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 02/06/2021.
 * please be sure to add credential if you use people's code
 */
public interface RepositoryImpl {

    LiveData<Resource<PagedList<MovieEntity>>> getMovies();

    LiveData<Resource<PagedList<TvShowEntity>>> getTvShows();

    LiveData<PagedList<FavouriteEntity>> getFavourites();

    LiveData<TvShowEntity> getTvShowById(int id);

    LiveData<MovieEntity> getMovieById(int id);

    void updateTvShow(TvShowEntity entity);

    void updateMovie(MovieEntity entity);

    void insertIntoFav(FavouriteEntity entity);

    void deleteFav(FavouriteEntity entity);

}
