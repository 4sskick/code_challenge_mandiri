package id.niteroomcreation.archcomponent.domain.data.remote.response.genre


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    @field:SerializedName("genres")
    val result: List<GenreX>
) : Parcelable