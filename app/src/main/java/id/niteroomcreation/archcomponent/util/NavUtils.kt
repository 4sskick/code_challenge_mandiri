package id.niteroomcreation.archcomponent.util;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.feature.dashboard.DashboardActivity;
import id.niteroomcreation.archcomponent.feature.detail.DetailActivity;

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
public class NavUtils {

    public static void directToDashboard(Activity act) {
        Intent i = new Intent(act, DashboardActivity.class);
        act.finish();
        act.startActivity(i);
    }

    public static void directToDetailScreen(Activity act
            , Object obj
            , ActivityOptionsCompat options) {

        Intent intent = new Intent(act, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MODEL_ID, obj instanceof MovieEntity ?
                (((MovieEntity) obj).getId()) + "_" + MovieEntity.TAG : obj instanceof TvShowEntity ?
                ((TvShowEntity) obj).getId() + "_" + TvShowEntity.TAG : null);
        ActivityCompat.startActivity(act, intent, options.toBundle());
    }
}
