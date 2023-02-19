package id.niteroomcreation.archcomponent.domain.data.local.entity;

import android.content.Context;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Objects;

import id.niteroomcreation.archcomponent.R;

/**
 * Created by Septian Adi Wijaya on 18/06/2021.
 * please be sure to add credential if you use people's code
 */
@Entity(primaryKeys = BaseColumns._ID, tableName = FavouriteEntity.T_NAME)
public class FavouriteEntity {

    public static final String TAG = FavouriteEntity.class.getSimpleName();

    public static final String T_NAME = "favourite";
    @Ignore
    protected String desc;
    @Ignore
    protected String year;
    @Ignore
    protected int poster;
    @Ignore
    protected double ratePercentage;
    @ColumnInfo(index = true, name = BaseColumns._ID)
    protected int id;
    //    public static final String C_TITLE = "title";
//    public static final String C_POSTER_PATH = "posterPath";
    @Ignore
    private String name;
    @Ignore
    private String posterPath;
    /**
     * type 1: MovieEntity
     * type 2: TvEntity
     */
    private int typeObject;

    public FavouriteEntity(int id, int typeObject) {
        this.id = id;
        this.typeObject = typeObject;
    }

//    public FavouriteEntity(int id
//            , String name
//            , int typeObject
//            , String desc
//            , String year
//            , String posterPath
//            , double ratePercentage) {
//
//        this.id = id;
//        this.name = name;
//        this.typeObject = typeObject;
//        this.desc = desc;
//        this.year = year;
//        this.posterPath = posterPath;
//        this.ratePercentage = ratePercentage;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public double getRatePercentage() {
        return ratePercentage;
    }

    public void setRatePercentage(double ratePercentage) {
        this.ratePercentage = ratePercentage;
    }

    public int getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(int typeObject) {
        this.typeObject = typeObject;
    }

    public String getTypeObjectStr(Context mContext, int typeObject) {
        switch (typeObject) {
            case 1:
                return mContext.getResources().getString(R.string.nav_bot_movies);

            case 2:
                return mContext.getResources().getString(R.string.nav_bot_tv_shows);
        }

        return mContext.getResources().getString(R.string.nav_bot_saved_fav);
    }

    //https://www.sitepoint.com/implement-javas-equals-method-correctly/
    @Override
    public boolean equals(@Nullable Object o) {
        //self check
        if (this == o)
            return true;

        //null check
        if (o == null)
            return false;

        //type check & cast
        if (getClass() != o.getClass())
            return false;

        FavouriteEntity m = (FavouriteEntity) o;
        //field comparison
        return Objects.equals(id, m.id);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + id;

        return result;
    }

    @Override
    public String toString() {
        return "FavouriteEntity{" +
                "name='" + name + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", desc='" + desc + '\'' +
                ", year='" + year + '\'' +
                ", poster=" + poster +
                ", ratePercentage=" + ratePercentage +
                ", id=" + id +
                ", typeObject=" + typeObject +
                '}';
    }
}
