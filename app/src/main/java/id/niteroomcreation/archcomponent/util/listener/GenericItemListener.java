package id.niteroomcreation.archcomponent.util.listener;

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 **/
public interface GenericItemListener<M, V> {
    default void onItemViewClicked(M item, V view) {
    }
}