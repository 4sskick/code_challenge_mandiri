package id.niteroomcreation.archcomponent.feature.movies;

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

import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.repositories.FakeRepository;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 **/
@RunWith(MockitoJUnitRunner.class)
public class MoviesViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Resource<PagedList<MovieEntity>>> observer;

    @Mock
    private FakeRepository repo;

    @Mock
    private PagedList<MovieEntity> pagedList;

    private MoviesViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new MoviesViewModel(repo);
    }

    @Test
    public void getMovies() {

        //whenever `dummyMovies.data.size` called would be returning empty data with size `432`
        Resource<PagedList<MovieEntity>> dummyMovies = Resource.success(pagedList);
        Mockito.when(dummyMovies.data.size()).thenReturn(432);

        //make it wrapped on mutable live data
        MutableLiveData<Resource<PagedList<MovieEntity>>> movies = new MutableLiveData<>();
        movies.setValue(dummyMovies);

        //set return data from repo with mutable live data already set before
        //when `repo.getMovies()` called then returning `movies`
        Mockito.when(repo.getMovies()).thenReturn(movies);

        //this `movieEntities` called the `viewModel.getMovies()` which called the `repo.getMovies()`
        //that already set the return value as `movies` as MutableLiveData
        List<MovieEntity> movieEntities = viewModel.getMovies().getValue().data;
        //telling to mockito, method `repo.getMovies()` is called with this interaction
        // without any arguments
        Mockito.verify(repo).getMovies();

        Assert.assertNotNull(movieEntities);
        Assert.assertEquals(432, movieEntities.size());

        viewModel.getMovies().observeForever(observer);
        Mockito.verify(observer).onChanged(dummyMovies);
    }
}