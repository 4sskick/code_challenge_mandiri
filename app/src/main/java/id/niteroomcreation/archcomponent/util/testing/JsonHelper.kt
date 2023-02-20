package id.niteroomcreation.archcomponent.util.testing

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/**
 * Created by Septian Adi Wijaya on 30/05/2021.
 * please be sure to add credential if you use people's code
 */
class JsonHelper {
    private var context: Context? = null

    constructor() {}
    constructor(context: Context?) {
        this.context = context
    }

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context!!.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private fun readFile(path: String): String {
        val sb = StringBuilder()
        var strLine: String?
        try {
            BufferedReader(
                InputStreamReader(
                    FileInputStream(path),
                    StandardCharsets.UTF_8
                )
            ).use { reader ->
                while (reader.readLine().also { strLine = it } != null) {
                    sb.append(strLine)
                }
            }
        } catch (ignore: IOException) {
            //ignore
        }
        return sb.toString()
    }

    //    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun loadMovies(): List<Movies> {
        val movies: MutableList<Movies> = ArrayList()
        try {
            val json = readFile("src/main/assets/Movies_dummy.json")
            //            String json = parsingFileToString("Movies_dummy.json");
            val responseJo = JSONObject(json)
            val resultJa = responseJo.getJSONArray("results")
            for (i in 0 until resultJa.length()) {
                val mJo = resultJa.getJSONObject(i)
                val m = Movies()
                m.title = mJo.getString("title")
                m.overview = mJo.getString("overview")
                m.posterPath = mJo.getString("poster_path")
                m.voteAverage = mJo.getDouble("vote_average")
                m.releaseDate = mJo.getString("release_date")
                movies.add(m)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return movies
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun loadTvShows(): List<TvShows> {
        val tvShows: MutableList<TvShows> = ArrayList()
        try {
            val json = readFile("src/main/assets/TvShows_dummy.json")
            if (json != null) {
                val responseJo = JSONObject(json)
                val resultJa = responseJo.getJSONArray("results")
                for (i in 0 until resultJa.length()) {
                    val mJo = resultJa.getJSONObject(i)
                    val m = TvShows()
                    m.name = mJo.getString("name")
                    m.overview = mJo.getString("overview")
                    m.posterPath = mJo.getString("poster_path")
                    m.voteAverage = mJo.getDouble("vote_average")
                    m.firstAirDate = mJo.getString("first_air_date")
                    tvShows.add(m)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tvShows
    }
}