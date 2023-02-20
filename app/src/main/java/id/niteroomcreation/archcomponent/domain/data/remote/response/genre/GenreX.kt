package id.niteroomcreation.archcomponent.domain.data.remote.response.genre


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenreX(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
) : Parcelable