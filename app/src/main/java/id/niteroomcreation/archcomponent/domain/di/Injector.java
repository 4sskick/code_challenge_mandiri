package id.niteroomcreation.archcomponent.domain.di;

import android.content.Context;

import java.util.concurrent.Executor;

import id.niteroomcreation.archcomponent.domain.data.local.LocalDataSource;
import id.niteroomcreation.archcomponent.domain.data.remote.RemoteRepoDataSource;
import id.niteroomcreation.archcomponent.domain.repositories.Repository;
import id.niteroomcreation.archcomponent.util.AppExecutors;

/**
 * Created by Septian Adi Wijaya on 27/05/2021.
 * please be sure to add credential if you use people's code
 */
public class Injector {

    public static Repository provideRepository(Context context) {

        RemoteRepoDataSource remoteRepoDataSource = RemoteRepoDataSource.getInstance(provideExecutor());
        LocalDataSource localDataSource = LocalDataSource.getInstance(context);
        AppExecutors appExecutors = new AppExecutors();

        return Repository.getInstance(remoteRepoDataSource, localDataSource, appExecutors);
    }

    public static Executor provideExecutor() {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };
    }
}
