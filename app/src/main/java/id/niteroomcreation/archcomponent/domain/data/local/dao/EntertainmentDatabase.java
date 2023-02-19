package id.niteroomcreation.archcomponent.domain.data.local.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;

/**
 * Created by monta on 10/06/21
 * please make sure to use credit when using people code
 **/
@Database(entities = {MovieEntity.class, TvShowEntity.class, FavouriteEntity.class}, version = 1)
public abstract class EntertainmentDatabase extends RoomDatabase {

    /**
     * migration DB section
     * see: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
     */
    //1 to 2
    static final Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table MovieEntity add column bookmarked INTEGER default 0 not" +
                    " null");

        }
    };
    public static volatile EntertainmentDatabase INSTANCE;

    public static EntertainmentDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (EntertainmentDatabase.class) {
                INSTANCE = buildDatabase(context);
            }
        }
        return INSTANCE;
    }

    private static EntertainmentDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context
                , EntertainmentDatabase.class
                , "made-catalogue-movie.db").allowMainThreadQueries()
                /*.addMigrations(migration_1_2)*/
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract MoviesDao moviesDao();

    public abstract TvShowsDao tvShowsDao();

    public abstract FavouriteDao favouriteDao();
}
