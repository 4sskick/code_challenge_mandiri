package id.niteroomcreation.archcomponent.feature.detail

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import id.niteroomcreation.archcomponent.BuildConfig
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseActivity
import id.niteroomcreation.archcomponent.databinding.ADetailBinding
import id.niteroomcreation.archcomponent.domain.data.remote.response.movies.Movies
import id.niteroomcreation.archcomponent.domain.data.remote.utils.StatusResponse.*
import id.niteroomcreation.archcomponent.feature.detail.review.DetailReviewAdapter
import id.niteroomcreation.archcomponent.feature.movies.MovieAdapterFooter
import id.niteroomcreation.archcomponent.util.LogHelper
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
class DetailActivity : BaseActivity<ADetailBinding, DetailViewModel>() {

    companion object {
        val TAG = DetailActivity::class.java.simpleName

        const val EXTRA_MODEL_ID = "extra.model.id"
        const val EXTRA_MODEL = "extra.model"
    }

    private val movies: Movies? by lazy {
        intent.getParcelableExtra<Movies>(EXTRA_MODEL)
    }

    override val layoutId: Int = R.layout.a_detail
    override val bindingVariable: Int = 0

    private lateinit var adapter: DetailReviewAdapter

    override fun initUI() {

        supportPostponeEnterTransition()
        setupAdapter()
        setupObserver()
        setupView()
    }

    private fun setupAdapter() {

        adapter = DetailReviewAdapter()

        viewDataBinding.rvReviews.layoutManager = LinearLayoutManager(this)
        viewDataBinding.rvReviews.adapter = adapter.withLoadStateFooter(MovieAdapterFooter())
    }

    private fun setupObserver() {
        mViewModel = obtainViewModel(this, DetailViewModel::class.java)

        mViewModel!!.getMovieReview(movies!!.id!!).observe(this, Observer {
            if (it != null)
                lifecycleScope.launch {
                    adapter.submitData(it)
                }
        })

        mViewModel!!.respondResult.observe(this, Observer {

            LogHelper.e(TAG, it)

            showLoading()
            when (it.status) {
                SUCCESS -> {
                    dismissLoading()

                    var genre = mutableListOf<String>()
                    it.body!!.genres.forEach {
                        genre.add(it.name)
                    }

                    viewDataBinding.tagGenreLayout.setItems(genre)
                }
                EMPTY -> {
                    dismissLoading()
                }
                ERROR -> {
                    dismissLoading()
                    showMessage(it.message)
                }
            }
        })
    }

    private fun setupView() {

        Glide.with(this)
            .load(
                String.format(
                    "%s%sw500/%s",
                    BuildConfig.BASE_URL_IMG,
                    BuildConfig.BASE_PATH_IMG,
                    if (movies != null) movies!!.posterPath else R.drawable.ic_placeholder
                )
            )
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }
            })
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_placeholder)
            .into(viewDataBinding.imgDetailMovie)

        viewDataBinding.txtDetailName.text = movies!!.originalTitle
        viewDataBinding.txtDetailDesc.text = overview
        viewDataBinding.txtDetailSaveFav.setOnClickListener { showMessage("SAVED") }
        viewDataBinding.txtDetailReview.setOnClickListener {
            if (adapter.itemCount > 0) {
                viewDataBinding.layoutBottomDetailContent.visibility =
                    if (viewDataBinding.layoutBottomDetailContent.isVisible)
                        View.GONE
                    else View.VISIBLE
            } else {
                viewDataBinding.layoutBottomDetailContent.visibility = View.GONE
                showMessage("Review film tidak ditemukan")
            }


        }
        viewDataBinding.txtDetailVideo.setOnClickListener {
            //due to lack of link to video from API, just hardcode the message here!
            showMessage("Trailer film tidak ditemukan")
        }

        mViewModel!!.getDetail(movies!!.id!!)
    }

    private val overview: String?
        get() {
            if (movies != null) {
                return if (movies!!.overview!!.isEmpty()) {
                    "Deskripsi Kosong"
                } else movies!!.overview
            }
            throw RuntimeException("Model isn't carried by parcelable arguments!")
        }
}