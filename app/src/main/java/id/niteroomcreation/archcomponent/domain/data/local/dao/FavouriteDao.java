package id.niteroomcreation.archcomponent.domain.data.local.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;

/**
 * Created by Septian Adi Wijaya on 18/06/2021.
 * please be sure to add credential if you use people's code
 */
@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertFavourites(List<FavouriteEntity> favourites);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavourite(FavouriteEntity favourite);

    @Query("select * from `favourite`")
    DataSource.Factory<Integer, FavouriteEntity> getFavourites();

    @Delete()
    void deleteFavourite(FavouriteEntity entity);
}
