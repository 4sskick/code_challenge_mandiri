package id.niteroomcreation.archcomponent.base

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import id.niteroomcreation.archcomponent.base.BaseActivity
import id.niteroomcreation.archcomponent.feature.custom.CLoadingDialog
import id.niteroomcreation.archcomponent.util.viewmodel.ViewModelFactory

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(),
    IBaseView {

    companion object {
        val TAG = BaseActivity::class.java.simpleName
    }

    @JvmField
    protected var mViewModel: VM? = null
    var viewDataBinding: V? = null

    private lateinit var mToast: Toast
    private lateinit var mLoading: Dialog

    //layout xml file which gonna binds the view components
    @get:LayoutRes
    abstract val layoutId: Int

    //this will be a variable which generated from data binding on xml view
    abstract val bindingVariable: Int
    abstract fun initUI()

    fun obtainViewModel(owner: ViewModelStoreOwner, vm: Class<VM>): VM {
        return ViewModelProvider(owner, ViewModelFactory.getInstance(this)).get(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLoading = CLoadingDialog.progressDialog(context = this)
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        performDataBinding()
        initUI()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }


    override fun showMessage(message: String?) {
        mToast.cancel()

        if (!message!!.isNullOrEmpty()) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_LONG).also { it.show() }
        }
    }

    override fun showLoading() {
        mLoading.show()
    }

    override fun dismissLoading() {
        mLoading.dismiss()
    }
}