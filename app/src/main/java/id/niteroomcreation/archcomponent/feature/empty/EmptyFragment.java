package id.niteroomcreation.archcomponent.feature.empty;

import android.os.Bundle;
import android.view.View;

import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseFragment;
import id.niteroomcreation.archcomponent.databinding.FEmptyBinding;

/**
 * Created by Septian Adi Wijaya on 21/06/2021.
 * please be sure to add credential if you use people's code
 */
public class EmptyFragment extends BaseFragment<FEmptyBinding, EmptyViewModel> {

    public static final String TAG = EmptyFragment.class.getSimpleName();
    private EmptyListener mListener;

    public static EmptyFragment newInstance(String msg, EmptyListener mListener) {
        EmptyFragment f = new EmptyFragment();

        Bundle b = new Bundle();
        b.putString("txt", msg);

        f.setArguments(b);
        f.setListener(mListener);
        return f;
    }

    private void setListener(EmptyListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.f_empty;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void initUI() {
        if (getArguments() != null && getArguments().getString("txt") != null) {
            getViewDataBinding().sTvFooter.setText(getArguments().getString("txt"));
        }

        setupObserver();
        getViewDataBinding().sWrapFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEmptyClickedView();
            }
        });
    }

    private void setupObserver() {
        mViewModel = obtainViewModel(this, EmptyViewModel.class);

    }

    public interface EmptyListener {
        void onEmptyClickedView();
    }
}
