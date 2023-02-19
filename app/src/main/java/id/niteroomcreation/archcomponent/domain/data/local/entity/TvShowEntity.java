package id.niteroomcreation.archcomponent.domain.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Created by Septian Adi Wijaya on 08/05/2021.
 * please be sure to add credential if you use people's code
 */
@Entity(primaryKeys = (BaseColumns._ID), tableName = TvShowEntity.T_NAME)
public class TvShowEntity extends BaseEntity implements Parcelable {

    public static final String TAG = TvShowEntity.class.getSimpleName();

    public static final String T_NAME = "tvshows";
    public static final Creator<TvShowEntity> CREATOR = new Creator<TvShowEntity>() {
        @Override
        public TvShowEntity createFromParcel(Parcel in) {
            return new TvShowEntity(in);
        }

        @Override
        public TvShowEntity[] newArray(int size) {
            return new TvShowEntity[size];
        }
    };
    private boolean bookmarked;

    public TvShowEntity() {
        name = "";
        desc = "";
        posterPath = "";
        year = "";
        poster = 0;
        ratePercentage = 0;
    }

    public TvShowEntity(String name
            , String desc
            , int poster
            , String year
            , double ratePercentage
            , String posterPath) {

        this.name = name;
        this.desc = desc;
        this.posterPath = posterPath;
        this.year = year;
        this.poster = poster;
        this.ratePercentage = ratePercentage;
    }

    protected TvShowEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        desc = in.readString();
        posterPath = in.readString();
        year = in.readString();
        poster = in.readInt();
        ratePercentage = in.readDouble();
        bookmarked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(posterPath);
        dest.writeString(year);
        dest.writeInt(poster);
        dest.writeDouble(ratePercentage);
        dest.writeByte((byte) (bookmarked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
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

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return "TvShowEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", year='" + year + '\'' +
                ", poster=" + poster +
                ", ratePercentage=" + ratePercentage +
                ", bookmarked=" + bookmarked +
                '}';
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

        TvShowEntity m = (TvShowEntity) o;
        //field comparison
        return Objects.equals(id, m.id) && Objects.equals(name, m.name);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : name.hashCode());
        result = prime * result + ((name == null) ? 0 : id);

        return result;
    }

    @NonNull
    @NotNull
    public FavouriteEntity cloned() {
        return new FavouriteEntity(getId(), 2);
    }
}
