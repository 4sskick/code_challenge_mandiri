package id.niteroomcreation.archcomponent.feature.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.databinding.IFooterLoadBinding
import id.niteroomcreation.archcomponent.util.vo.executeWithAction

/**
 * Created by Septian Adi Wijaya on 20/02/2023.
 * please be sure to add credential if you use people's code
 */
class MovieAdapterFooter : LoadStateAdapter<MovieAdapterFooter.FooterViewHolder>() {

    class FooterViewHolder(private val binding: IFooterLoadBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(loadState: LoadState) {
            binding.executeWithAction {
                //from <variable> inside <data> on layout
                footerUiState = FooterUiState(loadState)
            }
        }
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.binds(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        val itemBinding: IFooterLoadBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.i_footer_load,
            parent,
            false
        )

        return FooterViewHolder(itemBinding)

    }
}