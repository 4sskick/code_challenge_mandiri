package id.niteroomcreation.archcomponent.feature.detail;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

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
import id.niteroomcreation.archcomponent.domain.data.remote.response.Movies;
import id.niteroomcreation.archcomponent.util.LogHelper;

/**
 * Created by Septian Adi Wijaya on 09/05/2021.
 * please be sure to add credential if you use people's code
 */
public class DetailActivity extends BaseActivity<ADetailBinding, DetailViewModel> {

    public static final String TAG = DetailActivity.class.getSimpleName();
    public static final String EXTRA_MODEL_ID = "extra.model.id";
    public static final String EXTRA_MODEL = "extra.model";

    private Movies movies = null;

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

        movies = getIntent().getParcelableExtra(EXTRA_MODEL);

        supportPostponeEnterTransition();
        setupObserver();
        setupView();
    }

    void setupObserver() {
        mViewModel = obtainViewModel(this, DetailViewModel.class);
    }

    void setupView() {
        LogHelper.e(TAG, getIntent().getExtras().getString(EXTRA_MODEL_ID));

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

        getViewDataBinding().txtDetailName.setText(movies.getOriginalTitle());
        getViewDataBinding().txtDetailDesc.setText(getOverview());
        getViewDataBinding().txtDetailSaveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("SAVED");
            }
        });
    }

    private String getOverview() {
        if (movies != null) {
            if (movies.getOverview().isEmpty()) {
                return "Deskripsi Kosong";
            } else
                return movies.getOverview();
        }

        throw new RuntimeException("Model isn't carried by parcelable arguments!");
    }
}
