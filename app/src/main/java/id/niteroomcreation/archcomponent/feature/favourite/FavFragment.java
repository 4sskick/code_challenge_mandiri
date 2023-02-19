package id.niteroomcreation.archcomponent.feature.favourite;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseFragment;
import id.niteroomcreation.archcomponent.databinding.FFavouriteBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.feature.empty.EmptyFragment;
import id.niteroomcreation.archcomponent.feature.movies.MoviesFragment;
import id.niteroomcreation.archcomponent.util.LogHelper;
import id.niteroomcreation.archcomponent.util.listener.GenericItemListener;
import id.niteroomcreation.archcomponent.util.testing.EspressoIdlingResource;

/**
 * Created by monta on 09/06/21
 * please make sure to use credit when using people code
 **/
public class FavFragment extends BaseFragment<FFavouriteBinding, FavViewModel> {

    public static final String TAG = FavFragment.class.getSimpleName();

    private FavAdapter adapter;
    private MoviesFragment.MoviesListener mListener;

    @Override
    public int getLayoutId() {
        return R.layout.f_favourite;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void initUI() {
        setupAdapter();
        setupObserver();

    }

    private void setupObserver() {
        mViewModel = obtainViewModel(this, FavViewModel.class);
        mViewModel.getDataFavs().observe(getViewLifecycleOwner(), new Observer<PagedList<FavouriteEntity>>() {
            @Override
            public void onChanged(PagedList<FavouriteEntity> data) {

                EspressoIdlingResource.increment();
//                mViewModel.constructFavEntity(data);

                for (int i = 0; i < data.size(); i++) {
                    FavouriteEntity e = data.get(i);
                    MovieEntity m = null;
                    TvShowEntity t = null;

                    if (e.getTypeObject() == 1) {
                        m = mViewModel.getMoviesFav(e.getId()).getValue();

                        LogHelper.e(TAG, m, e);

                    } else if (e.getTypeObject() == 2) {
                        t = mViewModel.getTvById(e.getId()).getValue();

                        LogHelper.e(TAG, t, e);
                    }

                    if (m != null || t != null) {
                        e.setName((e.getTypeObject() == 1) ? m.getName() : t.getName());
                        e.setDesc((e.getTypeObject() == 1) ? m.getDesc() : t.getDesc());
                        e.setRatePercentage((e.getTypeObject() == 1) ? m.getRatePercentage() : t.getRatePercentage());
                        e.setYear((e.getTypeObject() == 1) ? m.getYear() : t.getYear());
                        e.setPosterPath((e.getTypeObject() == 1) ? m.getPosterPath() : t.getPosterPath());
                    }
                }

                LogHelper.e(TAG, data);

                getViewDataBinding().sProgress.setVisibility(View.GONE);
                adapter.submitList(data);

                if (data.size() == 0) {
                    showEmptyState();
                }
                EspressoIdlingResource.decrement();
            }
        });
    }

    private void showEmptyState() {
        moveToFragment(getViewDataBinding().flFav.getId()
                , EmptyFragment.newInstance("Belum ada item yang di favoritkan", new EmptyFragment.EmptyListener() {
                    @Override
                    public void onEmptyClickedView() {
                        LogHelper.e(TAG, "here clicking me");
                    }
                }), TAG);
    }

    private void setupAdapter() {
        adapter = new FavAdapter(new GenericItemListener<FavouriteEntity, List<Pair<View, String>>>() {
            @Override
            public void onItemViewClicked(FavouriteEntity item, List<Pair<View, String>> view) {

                MovieEntity me = null;
                TvShowEntity te = null;

                if (item.getTypeObject() == 1) {
                    me = new MovieEntity();
                    me.setId(item.getId());
                    me.setName(item.getName());
                    me.setDesc(item.getDesc());
                    me.setPoster(item.getPoster());
                    me.setPosterPath(item.getPosterPath());
                    me.setRatePercentage(item.getRatePercentage());
                    me.setYear(item.getYear());
                } else if (item.getTypeObject() == 2) {
                    te = new TvShowEntity();
                    te.setId(item.getId());
                    te.setName(item.getName());
                    te.setDesc(item.getDesc());
                    te.setPoster(item.getPoster());
                    te.setPosterPath(item.getPosterPath());
                    te.setRatePercentage(item.getRatePercentage());
                    te.setYear(item.getYear());
                } else
                    throw new RuntimeException("Model entity doesn't found!, " + item);

                mListener.onItemSelectedDetail(me != null ? me : te, view);
            }
        });
        getViewDataBinding().sProgress.setVisibility(View.VISIBLE);
        getViewDataBinding().listFavs.setLayoutManager(new LinearLayoutManager(getContext()));
        getViewDataBinding().listFavs.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);

        if (context instanceof MoviesFragment.MoviesListener)
            mListener = (MoviesFragment.MoviesListener) context;
        else
            throw new RuntimeException("Listener need to be implemented");
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
