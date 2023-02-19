package id.niteroomcreation.archcomponent.feature.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import id.niteroomcreation.archcomponent.domain.DummyData;
import id.niteroomcreation.archcomponent.domain.data.local.entity.MovieEntity;
import id.niteroomcreation.archcomponent.domain.data.local.entity.TvShowEntity;
import id.niteroomcreation.archcomponent.domain.repositories.FakeRepository;

/**
 * Created by monta on 10/05/21
 * please make sure to use credit when using people code
 **/
@RunWith(MockitoJUnitRunner.class)
public class DetailViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private Observer<MovieEntity> observerMovie;

    @Mock
    private Observer<TvShowEntity> observerTvShow;

    @Mock
    private FakeRepository repo;

    private DetailViewModel viewModel;
    private MovieEntity me = DummyData.generateRemoteDummyMovies().get(0);
    private TvShowEntity te = DummyData.generateRemoteDummyTvShows().get(0);

    private int meId = me.getId();
    private int teId = te.getId();

    @Before
    public void setUp() {
        viewModel = new DetailViewModel(repo);

    }

    @Test
    public void getMovieById() {
        MutableLiveData<MovieEntity> movies = new MutableLiveData<>();
        movies.setValue(me);

        Mockito.when(repo.getMovieById(meId)).thenReturn(movies);

        MovieEntity movieEntity = viewModel.getMovieById().getValue();
        Mockito.verify(repo).getMovieById(meId);

        Assert.assertNotNull(movieEntity);

        viewModel.getMovieById().observeForever(observerMovie);
        Mockito.verify(observerMovie).onChanged(me);
    }

    @Test
    public void getTvShowById() {
        MutableLiveData<TvShowEntity> tvShow = new MutableLiveData<>();
        tvShow.setValue(te);

        Mockito.lenient().when(repo.getTvShowById(teId)).thenReturn(tvShow);

        TvShowEntity tvShowEntity = viewModel.getTvShowById().getValue();
        Mockito.verify(repo).getTvShowById(teId);

        Assert.assertNotNull(tvShowEntity);

        viewModel.getTvShowById().observeForever(observerTvShow);
        Mockito.verify(observerTvShow).onChanged(te);
    }
}