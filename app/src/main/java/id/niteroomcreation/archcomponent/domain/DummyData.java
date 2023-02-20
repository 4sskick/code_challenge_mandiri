package id.niteroomcreation.archcomponent.domain;

import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies;
import id.niteroomcreation.archcomponent.domain.data.remote.response.TvShows;
import id.niteroomcreation.archcomponent.util.testing.JsonHelper;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DummyData {

    public static List<MovieEntity> constructMovies() {
        return generateRemoteDummyMovies();
    }

    public static List<TvShowEntity> constructTvShow() {
        return generateRemoteDummyTvShows();
    }

    public static List<FavouriteEntity> constructFavourites() {
        List<MovieEntity> me = generateRemoteDummyMovies();
        List<TvShowEntity> te = generateRemoteDummyTvShows();

        List<FavouriteEntity> fe = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            fe.add(new FavouriteEntity(me.get(i).getId(), 1));
            fe.add(new FavouriteEntity(te.get(i).getId(), 2));
        }

        return fe;
    }

    public static TvShowEntity getTvShowById(int id) {
        List<TvShowEntity> t = constructTvShow();

        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).getId() == id)
                return t.get(i);
        }

        return null;
    }

    public static MovieEntity getMovieById(int id) {
        List<MovieEntity> m = constructMovies();

        for (int i = 0; i < m.size(); i++) {
            if (m.get(i).getId() == id)
                return m.get(i);
        }

        return null;
    }

    public static List<MovieEntity> generateRemoteDummyMovies() {
        List<MovieEntity> movies = new ArrayList<>();
        JsonHelper jsonHelper = new JsonHelper();
        List<Movies> m = jsonHelper.loadMovies();

        for (int i = 0; i < m.size(); i++) {
            MovieEntity me = new MovieEntity(m.get(i).getTitle()
                    , m.get(i).getOverview()
                    , 0
                    , m.get(i).getReleaseDate()
                    , m.get(i).getVoteAverage()
                    , m.get(i).getPosterPath());

            movies.add(me);
        }

        return movies;
    }

    public static List<TvShowEntity> generateRemoteDummyTvShows() {
        List<TvShowEntity> tvshows = new ArrayList<>();
        JsonHelper jsonHelper = new JsonHelper();
        List<TvShows> m = jsonHelper.loadTvShows();

        for (int i = 0; i < m.size(); i++) {
            TvShowEntity me = new TvShowEntity(m.get(i).getName()
                    , m.get(i).getOverview()
                    , 0
                    , m.get(i).getFirstAirDate()
                    , m.get(i).getVoteAverage()
                    , m.get(i).getPosterPath());

            tvshows.add(me);
        }

        return tvshows;
    }

}
