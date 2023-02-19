package id.niteroomcreation.archcomponent.feature.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import id.niteroomcreation.archcomponent.BuildConfig;
import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.databinding.IFavBinding;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.util.BlurTransformation;
import id.niteroomcreation.archcomponent.util.LogHelper;
import id.niteroomcreation.archcomponent.util.listener.GenericItemListener;

/**
 * Created by Septian Adi Wijaya on 15/06/2021.
 * please be sure to add credential if you use people's code
 */
public class FavAdapter extends PagedListAdapter<FavouriteEntity, FavAdapter.ViewHolder> {

    public static final String TAG = FavAdapter.class.getSimpleName();
    private static final DiffUtil.ItemCallback<FavouriteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavouriteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull FavouriteEntity oldItem, @NonNull @NotNull FavouriteEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull FavouriteEntity oldItem, @NonNull @NotNull FavouriteEntity newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final GenericItemListener<FavouriteEntity, List<Pair<View, String>>> mListener;

    public FavAdapter(GenericItemListener<FavouriteEntity, List<Pair<View, String>>> mListener) {
        super(DIFF_CALLBACK);

        this.mListener = mListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        IFavBinding binding = IFavBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if (getItem(position) != null)
            holder.binds(getItem(position));
        else
            throw new RuntimeException("data is null");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final IFavBinding binding;

        public ViewHolder(@NonNull @NotNull IFavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void binds(FavouriteEntity item) {
            binding.txtItemName.setText(item.getName());
            binding.txtEntityType.setText(item.getTypeObjectStr(binding.txtEntityType.getContext(), item.getTypeObject()));
            binding.txtItemDesc.setText(item.getDesc());

            Glide.with(itemView.getContext())
                    .load(String.format("%s%sw500/%s", BuildConfig.BASE_URL_IMG, BuildConfig.BASE_PATH_IMG, item.getPosterPath()))
//                    .load(item.getPosterPath())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .transform(BlurTransformation.init(itemView.getContext()))
                    .into(binding.imgItemPhoto);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogHelper.e(TAG, item);

                    Pair<View, String> t1 = Pair.create(binding.imgItemPhoto, view.getResources().getString(R.string.transistion_img));
                    Pair<View, String> t2 = Pair.create(binding.txtItemName, view.getResources().getString(R.string.transition_name));
                    List<Pair<View, String>> a = new ArrayList<>();
                    a.add(t1);
                    a.add(t2);

                    mListener.onItemViewClicked(item, a);
                }
            });

        }
    }
}
