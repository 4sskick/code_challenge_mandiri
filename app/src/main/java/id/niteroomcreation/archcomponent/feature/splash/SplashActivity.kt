package id.niteroomcreation.archcomponent.feature.splash

import android.os.Handler
import android.os.Looper
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseActivity
import id.niteroomcreation.archcomponent.databinding.ASplashBinding
import id.niteroomcreation.archcomponent.feature.splash.SplashActivity
import id.niteroomcreation.archcomponent.util.NavUtils

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 */
class SplashActivity : BaseActivity<ASplashBinding, SplashViewModel>() {

    override val layoutId: Int = R.layout.a_splash

    //filled with variable name of data binding on layout
    override val bindingVariable: Int = 0

    override fun initUI() {
        Handler(Looper.getMainLooper()).postDelayed(
            { NavUtils.gotoDashboard(this@SplashActivity) },
            1500
        )
    }

    companion object {
        val TAG = SplashActivity::class.java.simpleName
    }
}