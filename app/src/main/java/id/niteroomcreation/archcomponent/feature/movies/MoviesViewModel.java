package id.niteroomcreation.archcomponent.feature.movies;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class MoviesViewModel extends BaseViewModel {

    public static final String TAG = MoviesViewModel.class.getSimpleName();

    private final RepositoryImpl repository;

    public MoviesViewModel(RepositoryImpl repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<MovieEntity>>> getMovies() {
        return repository.getMovies();
    }
}
