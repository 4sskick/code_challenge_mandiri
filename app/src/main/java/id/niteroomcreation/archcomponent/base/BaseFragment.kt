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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import id.niteroomcreation.archcomponent.base.BaseFragment
import id.niteroomcreation.archcomponent.util.viewmodel.ViewModelFactory

/**
 * Created by Septian Adi Wijaya on 06/05/2021.
 * please be sure to add credential if you use people's code
 */
abstract class BaseFragment<V : ViewDataBinding?, VM : BaseViewModel?> : Fragment(), IBaseView {

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }

    @JvmField
    protected var mViewModel: VM? = null
    var viewDataBinding: V? = null

    private lateinit var mRoot: View
    lateinit var rootActivity: BaseActivity<*, *>
    lateinit var baseFragmentManager: FragmentManager

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
        baseFragmentManager = rootActivity.supportFragmentManager
    }

    fun moveToFragment(
        viewIdFrameLayout: Int,
        fragment: BaseFragment<*, *>?,
        fragmentTag: String?
    ) {
        try {
            baseFragmentManager.beginTransaction()
                .replace(viewIdFrameLayout, fragment!!, fragmentTag)
                .commit()
        } catch (e: Exception) {
            throw IllegalStateException(
                String.format("Seems like fragmentManager isn't initialized %s", e.message)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding?.setVariable(bindingVariable, mViewModel)
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()

        mRoot = viewDataBinding!!.root
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