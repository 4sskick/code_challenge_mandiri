package id.niteroomcreation.archcomponent.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun String.dateFormatWithTime(): String {
        if (this.isBlank())
            return ""

        val instant = Instant.parse(this)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(TimeZone.getDefault().id))
        return formatter.format(instant)
    }

    fun String.dateFormatted(): String {

        if (this.isBlank())
            return ""

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        //`this` represent the value which String uses
        val date = formatter.parse(this) as Date
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        dateFormatter.timeZone = TimeZone.getDefault()

        return dateFormatter.format(date)
    }
}