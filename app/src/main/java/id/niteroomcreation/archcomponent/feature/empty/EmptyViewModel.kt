package id.niteroomcreation.archcomponent.feature.empty;

import id.niteroomcreation.archcomponent.base.BaseViewModel;
import id.niteroomcreation.archcomponent.domain.repositories.RepositoryImpl;

/**
 * Created by Septian Adi Wijaya on 21/06/2021.
 * please be sure to add credential if you use people's code
 */
public class EmptyViewModel extends BaseViewModel {

    private final RepositoryImpl repository;

    public EmptyViewModel(RepositoryImpl repository) {
        this.repository = repository;
    }
}
