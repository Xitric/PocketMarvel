package dk.sdu.pocketmarvel.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.sdu.pocketmarvel.LogContract;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataContainer;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Class for fetching data from a remote web service while also caching the data locally, such that
 * the app can be used in offline mode. Inspired by Google's Android Architecture Components
 * samples, but also modified to a great extent, including handling of the offline state.
 *
 * @param <T>
 * @see <a href="https://github.com/googlesamples/android-architecture-components/blob/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt">Google's NetworkBoundResource</a>
 */
public abstract class DataFetcher<T> {

    /**
     * The MediatorLiveData lets us observe results coming from potentially separate sources, such
     * as a local DB and a remote web service, all through a single LiveData object.
     * <br>
     * MediatorLiveData does not actually update its content when a new result becomes available -
     * it simply manages the notifications and lets us decide how to set the new value of the
     * MediatorLiveData.
     */
    @NonNull
    private MediatorLiveData<FetchResult<T>> result;

    public DataFetcher() {
        result = new MediatorLiveData<>();
    }

    /**
     * Call this method to start fetching the data from the local DB and the remote web service, if
     * necessary. Results can be observed on the LiveData object exposed by this class.
     *
     * @return This data fetcher to allow for method chaining
     */
    public DataFetcher<T> fetch() {
        //First we try to fetch the data from the local DB. We use the MediatorLiveData to be
        //notified when a result is available
        LiveData<T> dbLiveData = fetchFromDb();
        result.addSource(dbLiveData, dbResult -> {
            result.removeSource(dbLiveData); //We just wanted to get notified once

            if (shouldFetchFromApi(dbResult)) {
                //If the local data is not good enough, we must fetch it from the API. We inform any
                //listeners that we are fetching data, but we also supply them with the current
                //data - a good temporary value
                result.setValue(new FetchResult<>(FetchResult.State.Fetching, dbResult, null));
                fetchFromApi();
            } else {
                //The local data is good enough, so whenever a new value is available in the
                //database, we just pass it along
                beginPresentingLocalContent(dbLiveData);
            }
        });

        return this;
    }

    /**
     * Call this method when the local data is not sufficient, and new data must be obtained from
     * the remote web service.
     */
    private void fetchFromApi() {
        Log.i(LogContract.POCKETMARVEL_TAG, "Begun fetching new data from remote web service.");
        makeApiCall().enqueue(new Callback<MarvelDataWrapper<T>>() {
            @Override
            public void onResponse(@NonNull Call<MarvelDataWrapper<T>> call, @NonNull Response<MarvelDataWrapper<T>> response) {
                //OnResponse is also called for erroneous error codes. OnFailure is only for when
                //the request itself could not even be performed.
                if (response.isSuccessful()) {
                    //We unwrap the result from the web service and handle any possible null
                    //pointers
                    MarvelDataContainer<T> container = response.body() != null ?
                            response.body().getData() : null;

                    if (container == null) {
                        onFailure(call, new HttpException(response));
                    } else {
                        handleSuccessfulApiResponse(container.getResults());
                    }
                } else {
                    onFailure(call, new HttpException(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MarvelDataWrapper<T>> call, @NonNull Throwable t) {
                String errorMessage = t.getMessage() == null ? "An unknown error occurred while downloading content." : t.getMessage();
                if (t instanceof UnknownHostException) {
                    //We are unable to contact the web service without an internet connection, so we
                    //inform any listeners that the data presented is old
                    beginPresentingOfflineContent(fetchFromDb());
                } else {
                    //Some other error occurred
                    beginPresentingFailedContent(fetchFromDb(), errorMessage);
                }
            }
        });
    }

    /**
     * Call this method when a successful response is received from the remote web service. This
     * will store the result locally and start presenting data to any listener.
     *
     * @param remoteResults The result received from the remote web service
     */
    private void handleSuccessfulApiResponse(List<T> remoteResults) {
        //TODO: Extremely temporary until we can inject some executors!
        Executor background = Executors.newSingleThreadExecutor();
        background.execute(() -> {
            cacheResultsLocally(remoteResults);
            Handler h = new Handler(Looper.getMainLooper());
            h.post(() -> {
                beginPresentingLocalContent(fetchFromDb());
            });
        });
    }

    /**
     * Check if the local result is sufficient for displaying in the UI, or if we should try to
     * fetch a fresh result from the web service.
     *
     * @param localResult The result from the DB
     * @return True if we should fetch a fresh result, false otherwise
     */
    private boolean shouldFetchFromApi(T localResult) {
        //If there is no data locally, we must re-fetch
        if (localResult == null) {
            Log.i(LogContract.POCKETMARVEL_TAG, "No local data found");
            return true;
        }

        //If the local data can expire, and we have passed its expiration date, we must re-fetch
        if (localResult instanceof Expireable) {
            Date expires = ((Expireable) localResult).getExpiration();
            Date now = new Date();

            boolean hasExpired = expires.before(now);

            if (hasExpired) {
                Log.i(LogContract.POCKETMARVEL_TAG, "Local data has expired");
            } else {
                Log.i(LogContract.POCKETMARVEL_TAG, "Local data is up-to-date");
            }

            return hasExpired;
        }

        //Local data exists and cannot expire
        Log.i(LogContract.POCKETMARVEL_TAG, "Local data is static and up-to-date");
        return false;
    }

    /**
     * Call this method when the data is the local DB has been verified to be good, and when data
     * must be consistently passed along to any listeners.
     *
     * @param localContent LiveData of the results stored locally, to allow for viewing subsequent
     *                     changes to the DB
     */
    private void beginPresentingLocalContent(LiveData<T> localContent) {
        Log.i(LogContract.POCKETMARVEL_TAG, "Single source of truth presenting data to listeners.");
        result.addSource(localContent, localResult -> {
            result.setValue(new FetchResult<>(FetchResult.State.Success, localResult, null));
        });
    }

    /**
     * Call this method when the local data is not sufficient, but an internet connection could not
     * be established to fetch fresh data. Thus, we must resort to presenting the local data.
     *
     * @param offlineContent LiveData of the results stored locally, to allow for viewing subsequent
     *                       changes to the DB
     */
    private void beginPresentingOfflineContent(LiveData<T> offlineContent) {
        Log.i(LogContract.POCKETMARVEL_TAG, "Device offline, resorting to offline data.");
        result.addSource(offlineContent, offlineResult -> {
            result.setValue(new FetchResult<>(FetchResult.State.Offline, offlineResult, "No internet connection available."));
        });
    }

    /**
     * Call this method when fetching fresh data from the web service failed, and we must resort to
     * local data. Listeners will know that the data they are showing is possibly outdated.
     *
     * @param failedContent LiveData of the results stored locally, to allow for viewing subsequent
     *                      changes to the DB
     * @param errorMessage  A description of what went wrong
     */
    private void beginPresentingFailedContent(LiveData<T> failedContent, String errorMessage) {
        Log.i(LogContract.POCKETMARVEL_TAG, "Error fetching from remote: " + errorMessage);
        result.addSource(failedContent, offlineResult -> {
            result.setValue(new FetchResult<>(FetchResult.State.Failure, offlineResult, errorMessage));
        });
    }

    @NonNull
    public LiveData<FetchResult<T>> getResult() {
        return result;
    }

    protected abstract LiveData<T> fetchFromDb();

    protected abstract Call<MarvelDataWrapper<T>> makeApiCall();

    protected abstract void cacheResultsLocally(List<T> results);
}
