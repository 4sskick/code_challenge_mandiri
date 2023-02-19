package id.niteroomcreation.archcomponent.util

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity
import id.niteroomcreation.archcomponent.feature.dashboard.DashboardActivity
import id.niteroomcreation.archcomponent.feature.detail.DetailActivity

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
object NavUtils {
    @JvmStatic
    fun gotoDashboard(act: Activity) {
        val i = Intent(act, DashboardActivity::class.java)
        act.finish()
        act.startActivity(i)
    }

    @JvmStatic
    fun gotoDetail(
        act: Activity, obj: Any?, options: ActivityOptionsCompat
    ) {
        val intent = Intent(act, DetailActivity::class.java)
        intent.putExtra(
            DetailActivity.EXTRA_MODEL_ID,
            when (obj) {
                is MovieEntity -> obj.id.toString() + "_" + MovieEntity.TAG
                is TvShowEntity -> obj.id.toString() + "_" + TvShowEntity.TAG
                else -> null
            }
        )
        ActivityCompat.startActivity(act, intent, options.toBundle())
    }

    @JvmStatic
    fun moveToFragment(
        fragmentManager: FragmentManager,
        viewIdFrameLayout: Int,
        fragment: Fragment?,
        fragmentTag: String?,
    ) {
        try {
            fragmentManager.beginTransaction()
                .replace(viewIdFrameLayout, fragment!!, fragmentTag)
                .commit()
        } catch (e: Exception) {
            throw IllegalStateException(
                String.format(
                    "Seems like fragmentManager isn't " +
                            "initialized %s", e.message
                )
            )
        }
    }
}