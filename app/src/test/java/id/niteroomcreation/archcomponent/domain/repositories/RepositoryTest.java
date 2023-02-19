package id.niteroomcreation.archcomponent.domain.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
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
import id.niteroomcreation.archcomponent.domain.data.local.entity.FavouriteEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.util.AppExecutors;
import id.niteroomcreation.archcomponent.util.testing.LiveDataTestUtil;
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
    private List<TvShowEntity> tvShowsResponse;

    private int movieId;
    private int tvShowId;

    @Before
    public void setup() {
        repo = new FakeRepository(remote, localDataSource, appExecutors);
        moviesResponse = DummyData.generateRemoteDummyMovies();
        tvShowsResponse = DummyData.generateRemoteDummyTvShows();

        movieId = moviesResponse.get(0).getId();
        tvShowId = tvShowsResponse.get(0).getId();
    }

    @Test
    public void getMovies() {

        DataSource.Factory<Integer, MovieEntity> ds = Mockito.mock(DataSource.Factory.class);
        Mockito.when(localDataSource.getMovies()).thenReturn(ds);
        repo.getMovies();
        Resource<PagedList<MovieEntity>> me = Resource.success(PagedListUtil.mockPagedList(DummyData.constructMovies()));
        Mockito.verify(localDataSource).getMovies();
        Assert.assertNotNull(me.data);
        Assert.assertEquals(moviesResponse.size(), me.data.size());
    }

    @Test
    public void getTvShows() {

        DataSource.Factory<Integer, TvShowEntity> ds = Mockito.mock(DataSource.Factory.class);
        Mockito.when(localDataSource.getTvShows()).thenReturn(ds);
        repo.getTvShows();
        Resource<PagedList<TvShowEntity>> me = Resource.success(PagedListUtil.mockPagedList(DummyData.constructTvShow()));
        Mockito.verify(localDataSource).getTvShows();
        Assert.assertNotNull(me.data);
        Assert.assertEquals(tvShowsResponse.size(), me.data.size());
    }

    @Test
    public void getFavourites() {
        DataSource.Factory<Integer, FavouriteEntity> ds = Mockito.mock(DataSource.Factory.class);
        Mockito.when(localDataSource.getFavourites()).thenReturn(ds);
        repo.getFavourites();
        PagedList<FavouriteEntity> me = PagedListUtil.mockPagedList(DummyData.constructFavourites());
        Mockito.verify(localDataSource).getFavourites();
        Assert.assertNotNull(me);
        Assert.assertEquals(6, me.size());
    }

    @Test
    public void getMovieById() {

        Mockito.when(localDataSource.getMovieById(movieId))
                .thenReturn(DummyData.getMovieById(movieId));

        MovieEntity me = LiveDataTestUtil.getValue(repo.getMovieById(movieId));
        Mockito.verify(localDataSource).getMovieById(movieId);

        Assert.assertNotNull(me);
        Assert.assertEquals(moviesResponse.get(0).getName(), me.getName());
    }

    @Test
    public void getTvShowById() {

        Mockito.when(localDataSource.getTvById(tvShowId))
                .thenReturn(DummyData.getTvShowById(tvShowId));

        TvShowEntity te = LiveDataTestUtil.getValue(repo.getTvShowById(tvShowId));
        Mockito.verify(localDataSource).getTvById(tvShowId);

        Assert.assertNotNull(te);
        Assert.assertEquals(tvShowsResponse.get(0).getName(), te.getName());
    }
}