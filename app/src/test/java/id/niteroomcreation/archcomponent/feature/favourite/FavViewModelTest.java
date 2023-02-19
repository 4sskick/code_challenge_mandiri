package id.niteroomcreation.archcomponent.feature.favourite;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.DummyData;
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.repositories.FakeRepository;
import id.niteroomcreation.archcomponent.utils.PagedListUtil;

/**
 * Created by Septian Adi Wijaya on 22/06/2021.
 * please be sure to add credential if you use people's code
 */
@RunWith(MockitoJUnitRunner.class)
public class FavViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<PagedList<FavouriteEntity>> observer;

    @Mock
    private FakeRepository repo;

    private FavViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new FavViewModel(repo);
    }

    @Test
    public void getDataFavs() {
        //whenever `dummyMovies.data.size` called would be returning empty data with size `432`
        PagedList<FavouriteEntity> dummyFavourites = PagedListUtil.mockPagedList(DummyData.constructFavourites());
        Mockito.when(dummyFavourites.size()).thenReturn(6);

        //make it wrapped on mutable live data
        MutableLiveData<PagedList<FavouriteEntity>> favourite = new MutableLiveData<>();
        favourite.setValue(dummyFavourites);

        //set return data from repo with mutable live data already set before
        //when `repo.getMovies()` called then returning `movies`
        Mockito.when(repo.getFavourites()).thenReturn(favourite);

        //this `movieEntities` called the `viewModel.getMovies()` which called the `repo.getMovies()`
        //that already set the return value as `movies` as MutableLiveData
        List<FavouriteEntity> favouriteEntities = viewModel.getDataFavs().getValue();
        //telling to mockito, method `repo.getMovies()` is called with this interaction
        // without any arguments
        Mockito.verify(repo).getFavourites();

        Assert.assertNotNull(favouriteEntities);
        Assert.assertEquals(6, favouriteEntities.size());

        viewModel.getDataFavs().observeForever(observer);
        Mockito.verify(observer).onChanged(dummyFavourites);
    }
}