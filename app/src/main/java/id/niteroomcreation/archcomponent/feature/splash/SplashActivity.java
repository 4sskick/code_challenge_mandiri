package id.niteroomcreation.archcomponent.feature.splash;

import android.os.Handler;
import android.os.Looper;

import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseActivity;
import id.niteroomcreation.archcomponent.databinding.ASplashBinding;
import id.niteroomcreation.archcomponent.util.NavUtils;

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 **/
public class SplashActivity extends BaseActivity<ASplashBinding, SplashViewModel> {

    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.a_splash;
    }

    //filled with variable name of data binding on layout
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void initUI() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                NavUtils.directToDashboard(SplashActivity.this);
            }
        }, 1500);
    }
}
