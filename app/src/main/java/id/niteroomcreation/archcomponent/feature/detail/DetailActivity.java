package id.niteroomcreation.archcomponent.feature.detail;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.base.BaseActivity;
import id.niteroomcreation.archcomponent.databinding.ADetailBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.util.LogHelper;

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DetailActivity extends BaseActivity<ADetailBinding, DetailViewModel> {

    public static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_MODEL_ID = "extra.model.id";

    private MovieEntity movies = null;

    @Override
    public int getLayoutId() {
        return R.layout.a_detail;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void initUI() {
        supportPostponeEnterTransition();
        setupObserver();
    }

    void setupObserver() {
        mViewModel = obtainViewModel(this, DetailViewModel.class);

        if (getIntent() != null && getIntent().getExtras() != null) {

            //should be in format: "82332_class.TAG"
            //String[] objName = new String[]{"82332","class.TAG"}
            String[] objName = getIntent().getExtras().getString(EXTRA_MODEL_ID).split("_");
            LogHelper.e(TAG, objName);

            //82332
            if (objName[0] != null) {
                mViewModel.setSelectedEntity(Integer.parseInt(objName[0]));

                if (objName[1].equals(MovieEntity.TAG)) {
                    mViewModel.getMovieById().observe(this, new Observer<MovieEntity>() {
                        @Override
                        public void onChanged(MovieEntity movieEntity) {

                            LogHelper.e(TAG, movieEntity);

                            if (movieEntity != null)
                                setupView(movieEntity);
                            else
                                throw new RuntimeException("Data doesn't found");
                        }
                    });
                } else
                    throw new RuntimeException("Obj model doesn't found!");
            } else
                throw new RuntimeException("Model isn't carried by parcelable arguments!");
        } else
            throw new RuntimeException("Model isn't carried by parcelable arguments!");
    }

    void setupView(Object obj) {
        LogHelper.e(TAG, getIntent().getExtras().getString(EXTRA_MODEL_ID));


        if (obj instanceof MovieEntity)
            movies = (MovieEntity) obj;

        Glide.with(this)
                .load(String.format("%s%sw500/%s"
                        , BuildConfig.BASE_URL_IMG
                        , BuildConfig.BASE_PATH_IMG
                        , movies != null ?
                                movies.getPosterPath() : R.drawable.ic_placeholder))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e
                            , Object model
                            , Target<Drawable> target
                            , boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource
                            , Object model
                            , Target<Drawable> target
                            , DataSource dataSource
                            , boolean isFirstResource) {
                        DetailActivity.this.supportStartPostponedEnterTransition();

                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.ic_placeholder)
                .into(getViewDataBinding().imgDetailMovie);

        getViewDataBinding().txtDetailName.setText(movies.getName());
        getViewDataBinding().txtDetailDesc.setText(getOverview(movies, null));
        getViewDataBinding().txtDetailSaveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setupSavedFav(true, movies.isBookmarked());
            }
        });

        setupSavedFav(false, movies.isBookmarked());
    }

    private void setupSavedFav(boolean showMsg, boolean makeItFav) {
        getViewDataBinding().txtDetailSaveFav.setTextColor(makeItFav ?
                getResources().getColor(R.color.colorPrimary) :
                getResources().getColor(R.color.textColorSecondary));

        if (showMsg)
            showMessage(makeItFav ? "Disimpan pada favorit" : "Dihapus dari favorit");

    }

    private String getOverview(MovieEntity m, TvShowEntity t) {
        if (m != null) {
            if (m.getDesc().isEmpty()) {
                return "Deskripsi Kosong";
            } else
                return m.getDesc();
        }

        if (t != null) {
            if (t.getDesc().isEmpty()) {
                return "Deskripsi Kosong";
            } else
                return t.getDesc();
        }

        throw new RuntimeException("Model isn't carried by parcelable arguments!");
    }
}
