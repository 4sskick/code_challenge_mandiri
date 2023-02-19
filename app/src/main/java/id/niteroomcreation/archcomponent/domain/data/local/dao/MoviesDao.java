package id.niteroomcreation.archcomponent.domain.data.local.dao;


import android.database.Cursor;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;

/**
 * Created by monta on 10/06/21
 * please make sure to use credit when using people code
 **/
@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertMovies(List<MovieEntity> movies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMovie(MovieEntity movie);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int updateMovie(MovieEntity movie);

//    @Query("select * from `movies` where page = :page")
//    MovieEntity getMoviesByPage(Long page);

    @Query("select * from `movies` where _id = :id")
    MovieEntity getMovieById(Long id);

//    @Query("select * from `movies` where _id = :id and languageType = :lang")
//    MovieEntity getMovieByIdLang(Long id, String lang);
//
//    @Query("select * from `movies` where languageType = :lang")
//    List<MovieEntity> getMoviesByLang(String lang);

    @Query("select * from `movies`")
    DataSource.Factory<Integer, MovieEntity> getMovies();

    @Query("select * from `movies` where bookmarked = 1")
    DataSource.Factory<Integer, MovieEntity> getFavMovies();
//    List<MovieEntity> getFavMovies();

    @Query("select * from `movies` where title like '%' || :query || '%'")
    List<MovieEntity> getMoviesOnQuery(String query);

    //content values section queries
//    @Query("select * from `MovieEntity` where isFavorite = 1")
    @Query("select _id, title, posterPath, `desc` from `movies` where bookmarked = 1 union select _id, title, posterPath, `desc` from `tvshows` where bookmarked  = 1")
    Cursor cursorSelectAll();
}
