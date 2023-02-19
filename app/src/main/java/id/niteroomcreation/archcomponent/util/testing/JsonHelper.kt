package id.niteroomcreation.archcomponent.util.testing;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;

/**
 * Created by Septian Adi Wijaya on 30/05/2021.
 * please be sure to add credential if you use people's code
 */
public class JsonHelper {

    private Context context;

    public JsonHelper() {

    }

    public JsonHelper(Context context) {
        this.context = context;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String parsingFileToString(String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String readFile(String path) {
        final StringBuilder sb = new StringBuilder();
        String strLine;
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            while ((strLine = reader.readLine()) != null) {
                sb.append(strLine);
            }
        } catch (final IOException ignore) {
            //ignore
        }
        return sb.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<Movies> loadMovies() {
        List<Movies> movies = new ArrayList<>();
        try {
            String json = readFile("src/main/assets/Movies_dummy.json");
//            String json = parsingFileToString("Movies_dummy.json");
            JSONObject responseJo = new JSONObject(json);
            JSONArray resultJa = responseJo.getJSONArray("results");

            for (int i = 0; i < resultJa.length(); i++) {
                JSONObject mJo = resultJa.getJSONObject(i);

                Movies m = new Movies();
                m.setTitle(mJo.getString("title"));
                m.setOverview(mJo.getString("overview"));
                m.setPosterPath(mJo.getString("poster_path"));
                m.setVoteAverage(mJo.getDouble("vote_average"));
                m.setReleaseDate(mJo.getString("release_date"));

                movies.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public List<TvShows> loadTvShows() {
        List<TvShows> tvShows = new ArrayList<>();
        try {
            String json = readFile("src/main/assets/TvShows_dummy.json");

            if (json != null) {
                JSONObject responseJo = new JSONObject(json);
                JSONArray resultJa = responseJo.getJSONArray("results");

                for (int i = 0; i < resultJa.length(); i++) {
                    JSONObject mJo = resultJa.getJSONObject(i);

                    TvShows m = new TvShows();
                    m.setName(mJo.getString("name"));
                    m.setOverview(mJo.getString("overview"));
                    m.setPosterPath(mJo.getString("poster_path"));
                    m.setVoteAverage(mJo.getDouble("vote_average"));
                    m.setFirstAirDate(mJo.getString("first_air_date"));

                    tvShows.add(m);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tvShows;
    }
}
