package id.niteroomcreation.archcomponent.feature.movies

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.niteroomcreation.archcomponent.BuildConfig
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.databinding.IMoviesBinding
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.feature.detail.DetailActivity
import id.niteroomcreation.archcomponent.util.BlurTransformation

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
class MoviesAdapter : PagingDataAdapter<Movies, MoviesAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val TAG = MoviesAdapter::class.java.simpleName

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movies,
                newItem: Movies
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binds(getItem(position)!!)
    }

    class ViewHolder(private val binding: IMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binds(item: Movies) {
            binding.txtItemName.text = item.originalTitle
            binding.txtItemDesc.text = item.overview

            Glide.with(itemView.context)
                .load(
                    String.format(
                        "%s%sw500/%s",
                        BuildConfig.BASE_URL_IMG,
                        BuildConfig.BASE_PATH_IMG,
                        item.posterPath
                    )
                )
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transform(BlurTransformation.init(itemView.context))
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener { view ->
                val opt = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (binding.root.context as Activity),
                    Pair.create(
                        binding.imgItemPhoto,
                        view.resources.getString(R.string.transistion_img)
                    ),
                    Pair.create(
                        binding.txtItemName,
                        view.resources.getString(R.string.transition_name)
                    )
                )
                binding.root.context.startActivity(
                    Intent(binding.root.context, DetailActivity::class.java)
                        .also {
                            it.putExtra(
                                DetailActivity.EXTRA_MODEL_ID,
                                item.id.toString() + "_" + MovieEntity.TAG
                            )
                            it.putExtra(DetailActivity.EXTRA_MODEL, item)
                        },
                    opt.toBundle()
                )
            }
        }
    }
}