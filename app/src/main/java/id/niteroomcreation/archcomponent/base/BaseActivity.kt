package id.niteroomcreation.archcomponent.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import id.niteroomcreation.archcomponent.util.viewmodel.ViewModelFactory;

/**
 * Created by monta on 05/05/21
 * please make sure to use credit when using people code
 **/
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected VM mViewModel;
    private V mViewDataBinding;
    private Toast mToast;

    //layout xml file which gonna binds the view components
    public abstract
    @LayoutRes
    int getLayoutId();

    //this will be a variable which generated from data binding on xml view
    public abstract int getBindingVariable();

    public abstract void initUI();

    public VM obtainViewModel(ViewModelStoreOwner owner, Class<VM> vm) {
        return (VM) new ViewModelProvider(owner, ViewModelFactory.getInstance(this)).get(vm);
    }

    public V getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        performDataBinding();
        initUI();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public void showMessage(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        if (message != null && !message.isEmpty()) {
            mToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            mToast.show();
        }
    }
}
