package id.niteroomcreation.archcomponent.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import id.niteroomcreation.archcomponent.util.viewmodel.ViewModelFactory

/**
 * Created by Septian Adi Wijaya on 06/05/2021.
 * please be sure to add credential if you use people's code
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), IBaseView {

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }

    protected var mViewModel: VM? = null
    lateinit var mViewBinding: V

    private lateinit var mRoot: View
    private lateinit var rootActivity: BaseActivity<*, *>

    //layout xml file which gonna binds the view components
    @get:LayoutRes
    abstract val layoutId: Int

    //this will be a variable which generated from data binding on xml view
    abstract val bindingVariable: Int
    abstract fun initUI()

    fun obtainViewModel(owner: ViewModelStoreOwner, vm: Class<VM>): VM {
        return ViewModelProvider(owner, ViewModelFactory.getInstance(context)).get(vm)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            rootActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mViewBinding.setVariable(bindingVariable, mViewModel)
        mViewBinding.lifecycleOwner = this
        mViewBinding.executePendingBindings()

        mRoot = mViewBinding.root
        return mRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    override fun showMessage(message: String?) {
        rootActivity.showMessage(message)
    }

    override fun showLoading() {
        rootActivity.showLoading()
    }

    override fun dismissLoading() {
        rootActivity.dismissLoading()
    }
}