package id.niteroomcreation.archcomponent;

import android.app.Application;
import android.content.Context;

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 **/
public class CustomApplication extends Application {

    private static Context mContext;

    public static Context getAppContext() {
        return CustomApplication.mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CustomApplication.mContext = getApplicationContext();
    }
}
