package id.niteroomcreation.archcomponent.feature.dashboard;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.repositories.Repository;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;

/**
 * Created by Septian Adi Wijaya on 05/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DashboardViewModel extends BaseViewModel {

    public static final String TAG = DashboardViewModel.class.getSimpleName();

    private final RepositoryImpl repository;

    public DashboardViewModel(Repository repository) {
        this.repository = repository;
    }
}
