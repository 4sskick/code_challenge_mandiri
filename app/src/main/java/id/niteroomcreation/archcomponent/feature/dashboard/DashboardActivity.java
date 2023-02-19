package id.niteroomcreation.archcomponent.feature.dashboard;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseActivity;
import id.niteroomcreation.archcomponent.databinding.ADashboardBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment;
import id.niteroomcreation.archcomponent.util.NavUtils;
import id.niteroomcreation.archcomponent.util.testing.EspressoIdlingResource;

/**
 * Created by Septian Adi Wijaya on 05/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DashboardActivity
        extends BaseActivity<ADashboardBinding, DashboardViewModel>
        implements MoviesFragment.MoviesListener {

    public static final String TAG = DashboardActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.a_dashboard;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void onItemSelectedDetail(Object model, List<Pair<View, String>> view) {

        if (model instanceof MovieEntity || model instanceof TvShowEntity) {

            //noinspection unchecked
            ActivityOptionsCompat opt = ActivityOptionsCompat.makeSceneTransitionAnimation(this
                    , view.get(0)
                    , view.get(1));
            NavUtils.directToDetailScreen(this, model, opt);
        } else
            throw new RuntimeException("item object model doesn't found " + model.getClass().getSimpleName());

    }

    @Override
    public void initUI() {
        setupNavigation();
    }

    void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fl_content_dashboard);
        NavigationUI.setupWithNavController(getViewDataBinding().navBottomDashboard, navHostFragment.getNavController());

        getViewDataBinding().navBottomDashboard.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                EspressoIdlingResource.increment();

                Bundle b = new Bundle();
                switch (item.getItemId()) {
                    case R.id.action_nav_movies:
                    case R.id.action_nav_tv_shows:
                    case R.id.action_nav_saved_fav:


                        navHostFragment.getNavController().navigate(item.getItemId(), b);

                        EspressoIdlingResource.decrement();
                        return true;
                }

                return false;
            }
        });

        getViewDataBinding().navBottomDashboard.setSelectedItemId(navHostFragment.getNavController().getCurrentDestination().getId());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
