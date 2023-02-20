package id.niteroomcreation.archcomponent.domain.data.remote.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import id.niteroomcreation.archcomponent.util.AppExecutors;
import id.niteroomcreation.archcomponent.util.vo.Resource;

/**
 * Created by Septian Adi Wijaya on 11/06/2021.
 * please be sure to add credential if you use people's code
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

//    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
//
//    private final AppExecutors mExecutors;
//
//    public NetworkBoundResource(AppExecutors appExecutors) {
//
//        this.mExecutors = appExecutors;
//        result.setValue(Resource.loading(null));
//
//        LiveData<ResultType> dbSource = loadFromDB();
//
//        result.addSource(dbSource, new Observer<ResultType>() {
//            @Override
//            public void onChanged(ResultType data) {
//                result.removeSource(dbSource);
//                if (shouldFetch(data)) {
//                    fetchFromNetwork(dbSource);
//                } else {
//                    result.addSource(dbSource, new Observer<ResultType>() {
//                        @Override
//                        public void onChanged(ResultType newData) {
//                            result.setValue(Resource.success(newData));
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    protected void onFetchFailed() {
//    }
//
//    protected abstract LiveData<ResultType> loadFromDB();
//
//    protected abstract Boolean shouldFetch(ResultType data);
//
//    protected abstract LiveData<ApiResponse<RequestType>> createCall();
//
//    protected abstract void saveCallResult(RequestType data);
//
//    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
//
//        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
//
//        result.addSource(dbSource, new Observer<ResultType>() {
//            @Override
//            public void onChanged(ResultType newData) {
//                result.setValue(Resource.loading(newData));
//            }
//        });
//
//        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
//            @Override
//            public void onChanged(ApiResponse<RequestType> response) {
//                result.removeSource(apiResponse);
//                result.removeSource(dbSource);
//
//                switch (response.status) {
//                    case SUCCESS:
//                        mExecutors.diskIO().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                saveCallResult(response.body);
//
//                                mExecutors.mainThread().execute(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        result.addSource(loadFromDB(), new Observer<ResultType>() {
//                                            @Override
//                                            public void onChanged(ResultType newData) {
//                                                result.setValue(Resource.success(newData));
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        });
//                        break;
//                    case EMPTY:
//
//                        mExecutors.mainThread().execute(new Runnable() {
//                            @Override
//                            public void run() {
//                                mExecutors.mainThread().execute(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        result.addSource(loadFromDB(), new Observer<ResultType>() {
//                                            @Override
//                                            public void onChanged(ResultType newData) {
//                                                result.setValue(Resource.success(newData));
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        });
//                        break;
//                    case ERROR:
//                        onFetchFailed();
//                        result.addSource(dbSource, new Observer<ResultType>() {
//                            @Override
//                            public void onChanged(ResultType newData) {
//                                result.setValue(Resource.error(response.message, newData));
//                            }
//                        });
//                        break;
//                }
//            }
//        });
//
//    }
//
//    public LiveData<Resource<ResultType>> asLiveData() {
//        return result;
//    }
}