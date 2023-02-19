package id.niteroomcreation.archcomponent.feature.movies

import android.content.Context
import android.view.View
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseFragment
import id.niteroomcreation.archcomponent.databinding.FMoviesBinding
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment
import id.niteroomcreation.archcomponent.util.LogHelper
import id.niteroomcreation.archcomponent.util.vo.Status

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

    override val layoutId: Int
        get() = R.layout.f_movies

    override val bindingVariable: Int
        get() = 0

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
    }

    private fun setupObserver() {
        mViewModel = obtainViewModel(this, MoviesViewModel::class.java)
        mViewModel?.movies?.observe(this, Observer { data ->

            LogHelper.e(TAG, data, data.message)

            if (data.data != null) {
                when (data.status) {
                    Status.SUCCESS -> {
//                        dismissLoading()
                        adapter.submitList(data.data)
                        adapter.notifyDataSetChanged()
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.ERROR -> {
                        dismissLoading()
                        showMessage(data.message)
                    }
                }

            } else {
                if (data.status != Status.LOADING) {
                    showMessage("Data Request is not availabled")
                    dismissLoading()
                }
            }
        })
    }

    private fun setupAdapter() {
        adapter = MoviesAdapter()

        viewDataBinding?.listMovie?.layoutManager = LinearLayoutManager(context)
        viewDataBinding?.listMovie?.adapter = adapter
    }

    interface MoviesListener {
        fun onItemSelectedDetail(item: Any, view: List<Pair<View, String>>)
    }

}