package id.niteroomcreation.archcomponent.feature.dashboard

import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseActivity
import id.niteroomcreation.archcomponent.databinding.ADashboardBinding
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity
import id.niteroomcreation.archcomponent.feature.dashboard.DashboardActivity
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment.Companion.newInstance
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment.MoviesListener
import id.niteroomcreation.archcomponent.util.NavUtils
import id.niteroomcreation.archcomponent.util.NavUtils.moveToFragment

/**
 * Created by Septian Adi Wijaya on 05/05/2021.
 * please be sure to add credential if you use people's code
 */
class DashboardActivity : BaseActivity<ADashboardBinding, DashboardViewModel>(), MoviesListener {

    override val layoutId: Int
        get() = R.layout.a_dashboard
    override val bindingVariable: Int
        get() = 0

    override fun onItemSelectedDetail(model: Any, view: List<Pair<View, String>>) {
        if (model is MovieEntity || model is TvShowEntity) {
            val opt = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view[0],
                view[1]
            )

            NavUtils.gotoDetail(this, model, opt)
        } else throw RuntimeException("item object model doesn't found " + model.javaClass.simpleName)
    }

    override fun initUI() {
        moveToFragment(
            supportFragmentManager,
            viewDataBinding!!.flDashboard.id,
            newInstance(),
            MoviesFragment.TAG
        )
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        val TAG = DashboardActivity::class.java.simpleName
    }
}