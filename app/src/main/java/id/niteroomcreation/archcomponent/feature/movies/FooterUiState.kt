package id.niteroomcreation.archcomponent.feature.movies

import androidx.paging.LoadState
import id.niteroomcreation.archcomponent.base.BaseUiState

/**
 * Created by Septian Adi Wijaya on 20/02/2023.
 * please be sure to add credential if you use people's code
 */
data class FooterUiState(private val loadState: LoadState) : BaseUiState() {
    fun getLoadingVisibility() = getViewVisibility(isVisible = loadState is LoadState.Loading)
}