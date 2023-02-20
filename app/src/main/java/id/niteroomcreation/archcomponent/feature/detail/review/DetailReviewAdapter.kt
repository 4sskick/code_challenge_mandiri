package id.niteroomcreation.archcomponent.feature.detail.review

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.databinding.IReviewsBinding
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.reviews.MovieReviews
import id.niteroomcreation.archcomponent.util.CommonUtils.dateFormatted
import id.niteroomcreation.archcomponent.util.LogHelper


/**
 * Created by Septian Adi Wijaya on 20/02/2023.
 * please be sure to add credential if you use people's code
 */
class DetailReviewAdapter(private val dataReviews: List<MovieReviews>) :
    RecyclerView.Adapter<DetailReviewAdapter.ViewHolder>() {

    companion object {
        val TAG = DetailReviewAdapter::class.java.simpleName
    }

    class ViewHolder(private val binding: IReviewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun binds(data: MovieReviews) {

            binding.iReviewDate.text = data.updatedAt.dateFormatted()
            binding.iReviewContent.text = data.content
            binding.iReviewAuthor.text = data.author

            LogHelper.j(TAG, data)

            Glide.with(itemView.context)
                .load(
                    String.format(
                        "https://www.gravatar.com/avatar/%s",
                        data.authorDetails.avatarPath
                    )
                )
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.iReviewImage)

            binding.iReviewParent.setOnClickListener {
                val url = data.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                itemView.context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: IReviewsBinding =
            IReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binds(dataReviews[position])
    }

    override fun getItemCount(): Int {
        return dataReviews.size
    }
}