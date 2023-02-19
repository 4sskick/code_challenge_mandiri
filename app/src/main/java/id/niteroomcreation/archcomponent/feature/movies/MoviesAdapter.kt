package id.niteroomcreation.archcomponent.feature.movies;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.databinding.IMoviesBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.feature.detail.DetailActivity;
import id.niteroomcreation.archcomponent.util.BlurTransformation;

/**
 * Created by Septian Adi Wijaya on 07/05/2021.
 * please be sure to add credential if you use people's code
 */
public class MoviesAdapter extends PagedListAdapter<MovieEntity, MoviesAdapter.ViewHolder> {

    public static final String TAG = MoviesAdapter.class.getSimpleName();
    private static final DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieEntity>() {
        @Override
        public boolean areItemsTheSame(MovieEntity oldItem, MovieEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(MovieEntity oldItem, MovieEntity newItem) {
            return oldItem.equals(newItem);
        }
    };


    public MoviesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IMoviesBinding binding = IMoviesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItem(position) != null)
            holder.binds(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final IMoviesBinding binding;

        public ViewHolder(@NonNull IMoviesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void binds(MovieEntity item) {
            binding.txtItemName.setText(item.getName());
            binding.txtItemDesc.setText(item.getDesc());

            Glide.with(itemView.getContext())
                    .load(String.format("%s%sw500/%s", BuildConfig.BASE_URL_IMG, BuildConfig.BASE_PATH_IMG, item.getPosterPath()))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transform(BlurTransformation.init(itemView.getContext()))
                    .into(binding.imgItemPhoto);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Pair<View, String> t1 = Pair.create(binding.imgItemPhoto, view.getResources().getString(R.string.transistion_img));
                    Pair<View, String> t2 = Pair.create(binding.txtItemName, view.getResources().getString(R.string.transition_name));
                    List<Pair<View, String>> a = new ArrayList<>();
                    a.add(t1);
                    a.add(t2);

//                    mListener.onItemViewClicked(item, a);

                    ActivityOptionsCompat opt = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) binding.getRoot().getContext()
                            , t1
                            , t2
                    );

                    binding.getRoot().getContext().startActivity(
                            new Intent(binding.getRoot().getContext(), DetailActivity.class)
                                    .putExtra(DetailActivity.EXTRA_MODEL_ID, item.getId() + "_" + MovieEntity.TAG),
                            opt.toBundle()
                    );
                }
            });
        }
    }
}
