package id.niteroomcreation.archcomponent.domain.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import id.niteroomcreation.archcomponent.domain.DummyData;
import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.util.AppExecutors;
import id.niteroomcreation.archcomponent.util.vo.Resource;
import id.niteroomcreation.archcomponent.utils.PagedListUtil;

/**
 * Created by Septian Adi Wijaya on 29/05/2021.
 * please be sure to add credential if you use people's code
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FakeRemoteRepoDataSource remote = Mockito.mock(FakeRemoteRepoDataSource.class);
    private AppExecutors appExecutors = Mockito.mock(AppExecutors.class);
    private LocalDataSource localDataSource = Mockito.mock(LocalDataSource.class);

    private FakeRepository repo;
    private List<MovieEntity> moviesResponse;

    @Before
    public void setup() {
        repo = new FakeRepository(remote, localDataSource, appExecutors);
        moviesResponse = DummyData.generateRemoteDummyMovies();
    }

    @Test
    public void getMovies() {

        DataSource.Factory<Integer, MovieEntity> ds = Mockito.mock(DataSource.Factory.class);
        Mockito.when(localDataSource.getMovies()).thenReturn(ds);
        repo.getMovies();
        Resource<PagedList<MovieEntity>> me = Resource.success(PagedListUtil.mockPagedList(DummyData.constructMovies()));
        Mockito.verify(localDataSource).getMovies();
        Assert.assertNotNull(me.getData());
        Assert.assertEquals(moviesResponse.size(), me.getData().size());
    }

}