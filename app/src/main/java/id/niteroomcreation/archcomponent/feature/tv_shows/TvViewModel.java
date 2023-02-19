package id.niteroomcreation.archcomponent.feature.tv_shows;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class TvViewModel extends BaseViewModel {

    public static final String TAG = TvViewModel.class.getSimpleName();
    private final RepositoryImpl repository;

    public TvViewModel(RepositoryImpl repository) {
        this.repository = repository;
    }

    public LiveData<Resource<PagedList<TvShowEntity>>> getTvShow() {
        return repository.getTvShows();
    }
}
