package id.niteroomcreation.archcomponent

import android.app.Application
import android.content.Context

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 */
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {

        private lateinit var context: Context

        fun getContext() = context
    }
}