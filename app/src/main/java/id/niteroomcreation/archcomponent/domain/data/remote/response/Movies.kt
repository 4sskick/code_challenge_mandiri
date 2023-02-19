package id.niteroomcreation.archcomponent.domain.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Septian Adi Wijaya on 26/05/2021.
 * please be sure to add credential if you use people's code
 */
@Parcelize
data class Movies(
    @field:SerializedName("overview")
    var overview: String? = null,

    @field:SerializedName("original_language")
    var originalLanguage: String? = null,

    @field:SerializedName("original_title")
    var originalTitle: String? = null,

    @field:SerializedName("video")
    var isVideo: Boolean? = false,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("genre_ids")
    var genreIds: List<Int>? = emptyList(),

    @field:SerializedName("poster_path")
    var posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @field:SerializedName("release_date")
    var releaseDate: String? = null,

    @field:SerializedName("popularity")
    var popularity: Double? = 0.0,

    @field:SerializedName("vote_average")
    var voteAverage: Double? = 0.0,

    @field:SerializedName("id")
    var id: Int? = 0,

    @field:SerializedName("adult")
    var isAdult: Boolean? = false,

    @field:SerializedName("vote_count")
    var voteCount: Double? = 0.0
) : Parcelable