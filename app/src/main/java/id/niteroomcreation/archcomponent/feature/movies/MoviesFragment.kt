package id.niteroomcreation.archcomponent.feature.movies;

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
import id.niteroomcreation.archcomponent.databinding.FMoviesBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.util.LogHelper;
import id.niteroomcreation.archcomponent.util.listener.GenericItemListener;
import id.niteroomcreation.archcomponent.util.vo.Resource;
import id.niteroomcreation.archcomponent.util.vo.Status;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class MoviesFragment extends BaseFragment<FMoviesBinding, MoviesViewModel> {

    public static final String TAG = MoviesFragment.class.getSimpleName();

    private MoviesAdapter adapter;
    private MoviesListener mListener;

    @Override
    public int getLayoutId() {
        return R.layout.f_movies;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MoviesListener) {
            mListener = (MoviesListener) context;
        } else
            throw new RuntimeException("Listener need to implemented");
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void initUI() {
        setupObserver();
        setupAdapter();
    }

    void setupObserver() {
        mViewModel = obtainViewModel(this, MoviesViewModel.class);
        mViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<Resource<PagedList<MovieEntity>>>() {
            @Override
            public void onChanged(Resource<PagedList<MovieEntity>> data) {
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
                        String msg = "Data Request is not availabled";
                        LogHelper.e(TAG, msg, data);
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        getViewDataBinding().sProgress.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    void setupAdapter() {

        adapter = new MoviesAdapter(new GenericItemListener<MovieEntity, List<Pair<View, String>>>() {
            @Override
            public void onItemViewClicked(MovieEntity model, List<Pair<View, String>> view) {
                mListener.onItemSelectedDetail(model, view);
            }
        });

        getViewDataBinding().sProgress.setVisibility(View.VISIBLE);
        getViewDataBinding().listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().listMovie.setAdapter(adapter);
    }

    public interface MoviesListener {
        void onItemSelectedDetail(Object item, List<Pair<View, String>> view);
    }
}
