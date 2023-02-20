package id.niteroomcreation.archcomponent.feature.movies

import android.content.Context
import android.view.View
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseFragment
import id.niteroomcreation.archcomponent.databinding.FMoviesBinding
import id.niteroomcreation.archcomponent.util.LogHelper
import kotlinx.coroutines.launch

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
class MoviesFragment : BaseFragment<FMoviesBinding, MoviesViewModel>() {

    companion object {
        val TAG = MoviesFragment::class.java.simpleName

        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

    private lateinit var adapter: MoviesAdapter
    private var mListener: MoviesListener? = null

    override val layoutId: Int = R.layout.f_movies
    override val bindingVariable: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is MoviesListener) {
            context
        } else throw RuntimeException("Listener need to implemented")
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    override fun initUI() {
        setupObserver()
        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel!!.getMovies().observe(viewLifecycleOwner, Observer {
                LogHelper.j(TAG, it)

                lifecycleScope.launch {
                    adapter.submitData(it)
                }
            })
        }
    }

    private fun setupObserver() {
        mViewModel = obtainViewModel(this, MoviesViewModel::class.java)
    }

    private fun setupAdapter() {
        adapter = MoviesAdapter()

        mViewBinding.listMovie.layoutManager = LinearLayoutManager(context)
        mViewBinding.listMovie.adapter = adapter
    }

    interface MoviesListener {
        fun onItemSelectedDetail(item: Any, view: List<Pair<View, String>>)
    }

}