package id.niteroomcreation.archcomponent.feature.favourite;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;

/**
 * Created by monta on 09/06/21
 * please make sure to use credit when using people code
 **/
public class FavViewModel extends BaseViewModel {

    public static final String TAG = FavViewModel.class.getSimpleName();

    private final RepositoryImpl repository;

    public FavViewModel(RepositoryImpl repository) {
        this.repository = repository;
    }

    public LiveData<PagedList<FavouriteEntity>> getDataFavs() {
        return repository.getFavourites();
    }

    public LiveData<MovieEntity> getMoviesFav(int id) {
        return repository.getMovieById(id);
    }

    public LiveData<TvShowEntity> getTvById(int id) {
        return repository.getTvShowById(id);
    }

    public void constructFavEntity(PagedList<FavouriteEntity> data) {
        for (int i = 0; i < data.size(); i++) {
            FavouriteEntity e = data.get(i);
            MovieEntity m = null;
            TvShowEntity t = null;

            if (e.getTypeObject() == 1)
                m = getMoviesFav(e.getId()).getValue();
            else
                t = getTvById(e.getId()).getValue();

            if (m != null || t != null) {
                e.setName((e.getTypeObject() == 1) ? m.getName() : t.getName());
                e.setDesc((e.getTypeObject() == 1) ? m.getDesc() : t.getDesc());
                e.setRatePercentage((e.getTypeObject() == 1) ? m.getRatePercentage() : t.getRatePercentage());
                e.setYear((e.getTypeObject() == 1) ? m.getYear() : t.getYear());
                e.setPosterPath((e.getTypeObject() == 1) ? m.getPosterPath() : t.getPosterPath());
            }
        }
    }
}
