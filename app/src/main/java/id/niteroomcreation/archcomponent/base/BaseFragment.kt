package id.niteroomcreation.archcomponent.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import id.niteroomcreation.archcomponent.util.viewmodel.ViewModelFactory;

/**
 * Created by Septian Adi Wijaya on 06/05/2021.
 * please be sure to add credential if you use people's code
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();
    protected VM mViewModel;
    private BaseActivity mActivity;
    private View mRoot;
    private V mViewDataBinding;
    private FragmentManager fragmentManager;

    //layout xml file which gonna binds the view components
    public abstract
    @LayoutRes
    int getLayoutId();

    //this will be a variable which generated from data binding on xml view
    public abstract int getBindingVariable();

    public abstract void initUI();

    public VM obtainViewModel(ViewModelStoreOwner owner, Class<VM> vm) {
        return (VM) new ViewModelProvider(owner, ViewModelFactory.getInstance(getContext())).get(vm);
    }

    public BaseActivity getRootActivity() {
        return mActivity;
    }

    public V getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }

    }

    @Override
    public void onDetach() {
        mActivity = null;

        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

        fragmentManager = (mActivity).getSupportFragmentManager();
    }

    public void moveToFragment(int viewIdFrameLayout
            , BaseFragment fragment
            , String fragmentTag) {
        try {
            fragmentManager.beginTransaction()
                    .replace(viewIdFrameLayout, fragment, fragmentTag)
                    .commit();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Seems like fragmentManager isn't " +
                    "initialized %s", e.getMessage()));
        }
    }

    public FragmentManager getBaseFragmentManager() {
        return fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRoot = mViewDataBinding.getRoot();
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();

        initUI();
    }
}
