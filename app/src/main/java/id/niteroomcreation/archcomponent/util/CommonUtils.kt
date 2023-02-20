package id.niteroomcreation.archcomponent.util

import android.content.Context

/**
 * Created by Septian Adi Wijaya on 20/02/2023.
 * please be sure to add credential if you use people's code
 */
object CommonUtils {
    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }
}