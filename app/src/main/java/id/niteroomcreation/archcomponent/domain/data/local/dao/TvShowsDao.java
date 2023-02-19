package id.niteroomcreation.archcomponent.domain.data.local.dao;

import android.database.Cursor;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;

/**
 * Created by monta on 10/06/21
 * please make sure to use credit when using people code
 **/
@Dao
public interface TvShowsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTvs(List<TvShowEntity> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTv(TvShowEntity movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateTv(TvShowEntity movie);

//    @Query("select * from `tvshows` where page = :page")
//    TvShowEntity getTvsByPage(Long page);

    @Query("select * from `tvshows` where _id = :id")
    TvShowEntity getTvById(Long id);

//    @Query("select * from `tvshows` where _id = :id and languageType = :lang")
//    TvShowEntity getTvByIdLang(Long id, String lang);

//    @Query("select * from `tvshows` where languageType = :lang")
//    List<TvShowEntity> getTvsByLang(String lang);

    @Query("select * from `tvshows`")
    DataSource.Factory<Integer, TvShowEntity> getTvShows();

    @Query("select * from `tvshows` where bookmarked = 1")
    DataSource.Factory<Integer, TvShowEntity> getFavsTv();

    @Query("select * from `tvshows` where title like '%'|| :query || '%'")
    List<TvShowEntity> gettvShowsOnQuery(String query);

    //content values section queries
    @Query("select * from `tvshows` where bookmarked = 1")
    Cursor cursorSelectAll();

    @Query("select * from `tvshows` where _id = :id")
    Cursor cursorSelectById(long id);
}
