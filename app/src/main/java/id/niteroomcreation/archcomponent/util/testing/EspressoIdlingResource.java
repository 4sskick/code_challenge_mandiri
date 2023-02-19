package id.niteroomcreation.archcomponent.util.testing;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

/**
 * Created by Septian Adi Wijaya on 30/05/2021.
 * please be sure to add credential if you use people's code
 */
public class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";
    private static final CountingIdlingResource idlingResource = new CountingIdlingResource(RESOURCE);

    public static void increment() {
        idlingResource.increment();
    }

    public static void decrement() {
        if (getEspressoIdlingResource().isIdleNow())
            increment();
//        else
        idlingResource.decrement();
    }

    public static IdlingResource getEspressoIdlingResource() {
        return idlingResource;
    }
}