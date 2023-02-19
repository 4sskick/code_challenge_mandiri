package id.niteroomcreation.archcomponent.feature.splash;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.repositories.Repository;

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 **/
public class SplashViewModel extends BaseViewModel {

    private final Repository repository;

    public SplashViewModel(Repository repository) {
        this.repository = repository;
    }
}
