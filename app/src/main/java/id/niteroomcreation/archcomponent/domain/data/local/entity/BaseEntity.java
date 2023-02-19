package id.niteroomcreation.archcomponent.domain.data.local.entity;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;

/**
 * Created by Septian Adi Wijaya on 19/06/2021.
 * please be sure to add credential if you use people's code
 */
public abstract class BaseEntity {

    public static final String C_TITLE = "title";
    public static final String C_POSTER_PATH = "posterPath";

    @ColumnInfo(index = true, name = BaseColumns._ID)
    protected int id;

    @ColumnInfo(name = C_TITLE)
    protected String name;
    @ColumnInfo(name = C_POSTER_PATH)

    protected String posterPath;
    protected String desc;
    protected String year;
    protected int poster;
    protected double ratePercentage;
}
