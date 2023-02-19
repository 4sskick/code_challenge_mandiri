package id.niteroomcreation.archcomponent.feature.detail;

import androidx.lifecycle.LiveData;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DetailViewModel extends BaseViewModel {

    private int objId;
    private final RepositoryImpl repository;

    public DetailViewModel(RepositoryImpl repository) {
        this.repository = repository;
    }

    public void setSelectedEntity(int objId) {
        this.objId = objId;
    }

    public LiveData<MovieEntity> getMovieById() {
        return repository.getMovieById(objId);
    }

    public LiveData<TvShowEntity> getTvShowById() {
        return repository.getTvShowById(objId);
    }

    public void updateMoviesOnFav(MovieEntity entity) {
        repository.updateMovie(entity);
    }

    public void updateTvShowOnFav(TvShowEntity entity) {
        repository.updateTvShow(entity);
    }

    public void insertIntoFav(FavouriteEntity entity) {
        repository.insertIntoFav(entity);
    }

    public void deleteFav(FavouriteEntity entity) {
        repository.deleteFav(entity);
    }
}
