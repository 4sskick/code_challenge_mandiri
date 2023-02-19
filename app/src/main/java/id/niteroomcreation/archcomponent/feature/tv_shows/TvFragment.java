package id.niteroomcreation.archcomponent.feature.tv_shows;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseFragment;
import id.niteroomcreation.archcomponent.databinding.FTvsBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment;
import id.niteroomcreation.archcomponent.util.LogHelper;
import id.niteroomcreation.archcomponent.util.listener.GenericItemListener;
import id.niteroomcreation.archcomponent.util.vo.Resource;
import id.niteroomcreation.archcomponent.util.vo.Status;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class TvFragment extends BaseFragment<FTvsBinding, TvViewModel> {

    public static final String TAG = TvFragment.class.getSimpleName();

    private TvAdapter adapter;
    private MoviesFragment.MoviesListener mListener;

    @Override
    public int getLayoutId() {
        return R.layout.f_tvs;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MoviesFragment.MoviesListener) {
            mListener = (MoviesFragment.MoviesListener) context;
        } else
            throw new RuntimeException("Listener need to implemented");
    }

    @Override
    public void initUI() {
        setupObserver();
        setupAdapter();
    }

    void setupObserver() {
        mViewModel = obtainViewModel(this, TvViewModel.class);
        mViewModel.getTvShow().observe(getViewLifecycleOwner(), new Observer<Resource<PagedList<TvShowEntity>>>() {
            @Override
            public void onChanged(Resource<PagedList<TvShowEntity>> data) {
                if (data.data != null) {
                    switch (data.status) {
                        case SUCCESS:
                            getViewDataBinding().sProgress.setVisibility(View.GONE);

                            adapter.submitList(data.data);

                            break;
                        case LOADING:
                            getViewDataBinding().sProgress.setVisibility(View.VISIBLE);

                            break;
                        case ERROR:
                            getViewDataBinding().sProgress.setVisibility(View.GONE);

                            LogHelper.e(TAG, data, data.message);
                            Toast.makeText(getContext(), data.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    if (data.status != Status.LOADING) {
                        String msg = "Data Request is not available";
                        LogHelper.e(TAG, msg);
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        getViewDataBinding().sProgress.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    void setupAdapter() {
        adapter = new TvAdapter(new GenericItemListener<TvShowEntity, List<Pair<View, String>>>() {
            @Override
            public void onItemViewClicked(TvShowEntity item, List<Pair<View, String>> view) {
                mListener.onItemSelectedDetail(item, view);
            }
        });

        getViewDataBinding().sProgress.setVisibility(View.VISIBLE);
        getViewDataBinding().listTv.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().listTv.setAdapter(adapter);
    }
}
