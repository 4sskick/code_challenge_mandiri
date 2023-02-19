package id.niteroomcreation.archcomponent.feature.empty

import android.os.Bundle
import id.niteroomcreation.archcomponent.R
import id.niteroomcreation.archcomponent.base.BaseFragment
import id.niteroomcreation.archcomponent.databinding.FEmptyBinding

/**
 * Created by Septian Adi Wijaya on 21/06/2021.
 * please be sure to add credential if you use people's code
 */
class EmptyFragment : BaseFragment<FEmptyBinding, EmptyViewModel>() {

    companion object {
        val TAG = EmptyFragment::class.java.simpleName

        fun newInstance(msg: String?, mListener: EmptyListener): EmptyFragment {
            val f = EmptyFragment()
            val b = Bundle()
            b.putString("txt", msg)
            f.arguments = b
            f.setListener(mListener)
            return f
        }
    }

    private lateinit var mListener: EmptyListener
    private fun setListener(mListener: EmptyListener) {
        this.mListener = mListener
    }

    override val layoutId: Int = R.layout.f_empty
    override val bindingVariable: Int = 0

    override fun initUI() {
        if (arguments != null && arguments?.getString("txt") != null) {
            mViewBinding.sTvFooter.text = arguments?.getString("txt")
        }

        setupObserver()

        mViewBinding.sWrapFooter.setOnClickListener { mListener.onEmptyClickedView() }
    }

    private fun setupObserver() {
        mViewModel = obtainViewModel(this, EmptyViewModel::class.java)
    }

    interface EmptyListener {
        fun onEmptyClickedView()
    }
}