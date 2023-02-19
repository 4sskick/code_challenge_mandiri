package id.niteroomcreation.archcomponent.feature.dashboard;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import id.niteroomcreation.archcomponent.DisableAnimationsRule;
import id.niteroomcreation.archcomponent.R;
import id.niteroomcreation.archcomponent.util.testing.EspressoIdlingResource;

/**
 * Created by Septian Adi Wijaya on 11/05/2021.
 * please be sure to add credential if you use people's code
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DashboardActivityTest {

    @ClassRule
    public static DisableAnimationsRule disableAnimationsRule = new DisableAnimationsRule();

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(DashboardActivity.class);

    @Before
    public void setup() {
        ActivityScenario.launch(DashboardActivity.class);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
    }

    //custom methods - begin
    private void gotoDetail(boolean isMovie) {
        if (isMovie)
            aLoadMovies();

        Espresso.onView(ViewMatchers.withId(isMovie ? R.id.list_movie : R.id.list_tv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, forceClick()));

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_name))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_desc))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    public static ViewAction forceClick() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(ViewMatchers.isClickable(), ViewMatchers.isEnabled(), ViewMatchers.isDisplayed());
            }

            @Override
            public String getDescription() {
                return "force click";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick(); // perform click without checking view coordinates.
                uiController.loopMainThreadUntilIdle();
            }
        };
    }
    //custom method - end

    //begin - movies
    @Test
    public void aLoadMovies() {
        Espresso.onView(ViewMatchers.withId(R.id.list_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.list_movie))
                .perform(RecyclerViewActions.scrollToPosition(6));
    }

    @Test
    public void aLoadMoviesAGoToDetail() {
        gotoDetail(true);
    }

    @Test
    public void aLoadMoviesGoBackFromDetail() {
        gotoDetail(true);
        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.s_progress))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void aLoadMoviesGoBackFromDetailBookmarked() {
        aLoadMoviesAGoToDetail();

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_save_fav))
                .perform(forceClick());

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.pressBack();
    }

    //tv shows - begin
    @Test
    public void aLoadTvShows() {
        Espresso.onView(ViewMatchers.withId(R.id.list_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.action_nav_tv_shows))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.list_tv))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.list_tv))
                .perform(RecyclerViewActions.scrollToPosition(6));
    }

    @Test
    public void aLoadTvShowsAGoToDetail() {
        aLoadTvShows();
        gotoDetail(false);
    }

    @Test
    public void aLoadTvShowsGoBackFromDetail() {
        aLoadTvShowsAGoToDetail();

        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.s_progress))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void aLoadTvShowsGoBackFromDetailBookmarked() {
        aLoadTvShowsAGoToDetail();

        Espresso.onView(ViewMatchers.withId(R.id.txt_detail_save_fav))
                .perform(forceClick());

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.pressBack();

    }

    //go to nav favourite
    @Test
    public void aLoadZ() {
        Espresso.onView(ViewMatchers.withId(R.id.list_movie))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.action_nav_saved_fav))
                .perform(forceClick());
        Espresso.onView(ViewMatchers.withId(R.id.list_favs))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}