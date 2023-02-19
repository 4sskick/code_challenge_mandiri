package id.niteroomcreation.archcomponent.util.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import id.niteroomcreation.archcomponent.domain.di.Injector;
import id.niteroomcreation.archcomponent.domain.repositories.Repository;
import id.niteroomcreation.archcomponent.feature.dashboard.DashboardViewModel;
import id.niteroomcreation.archcomponent.feature.detail.DetailViewModel;
import id.niteroomcreation.archcomponent.feature.empty.EmptyViewModel;
import id.niteroomcreation.archcomponent.feature.favourite.FavViewModel;
import id.niteroomcreation.archcomponent.feature.movies.MoviesViewModel;
import id.niteroomcreation.archcomponent.feature.splash.SplashViewModel;
import id.niteroomcreation.archcomponent.feature.tv_shows.TvViewModel;

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
@SuppressWarnings("unchecked")
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Repository repository;

    private ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    public static ViewModelFactory getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(Injector.provideRepository(context));
            }
        }
        return INSTANCE;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            return (T) new DashboardViewModel(repository);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(repository);
        } else if (modelClass.isAssignableFrom(MoviesViewModel.class)) {
            return (T) new MoviesViewModel(repository);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel(repository);
        } else if (modelClass.isAssignableFrom(TvViewModel.class)) {
            return (T) new TvViewModel(repository);
        } else if (modelClass.isAssignableFrom(FavViewModel.class))
            return (T) new FavViewModel(repository);
        else if (modelClass.isAssignableFrom(EmptyViewModel.class))
            return (T) new EmptyViewModel(repository);

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
