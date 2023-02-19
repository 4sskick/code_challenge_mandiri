package id.niteroomcreation.archcomponent.feature.tv_shows;

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

import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.repositories.FakeRepository;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 **/
@RunWith(MockitoJUnitRunner.class)
public class TvViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Resource<PagedList<TvShowEntity>>> observer;

    @Mock
    private FakeRepository repo;

    @Mock
    private PagedList<TvShowEntity> pagedList;

    private TvViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new TvViewModel(repo);
    }

    @Test
    public void getTvShow() {

        //whenever `dummyTvShow.data.size` called would be returning empty data with size `21`
        Resource<PagedList<TvShowEntity>> dummyTvShow = Resource.success(pagedList);
        Mockito.when(dummyTvShow.data.size()).thenReturn(21);

        //make it wrapped on mutable live data
        MutableLiveData<Resource<PagedList<TvShowEntity>>> tvSHows = new MutableLiveData<>();
        tvSHows.setValue(dummyTvShow);

        //set return data from repo with mutable live data already set before
        //when `repo.getTvShows()` called then returning `tvSHows`
        Mockito.when(repo.getTvShows()).thenReturn(tvSHows);

        //this `tvShowEntities` called the `viewModel.getTvShows()` which called the `repo.getTvShows()`
        //that already set the return value as `movies` as MutableLiveData
        List<TvShowEntity> tvShowEntities = viewModel.getTvShow().getValue().data;
        //telling to mockito, method `repo.getMovies()` is called with this interaction
        // without any arguments
        Mockito.verify(repo).getTvShows();

        Assert.assertNotNull(tvShowEntities);
        Assert.assertEquals(21, tvShowEntities.size());

        //verifying whenever viewModel.getTvShow() called onChanged returning data that already set
        viewModel.getTvShow().observeForever(observer);
        Mockito.verify(observer).onChanged(dummyTvShow);
    }
}