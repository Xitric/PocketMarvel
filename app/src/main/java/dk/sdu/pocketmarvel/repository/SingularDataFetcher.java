package dk.sdu.pocketmarvel.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.util.Collections;
import java.util.List;

import dk.sdu.pocketmarvel.MarvelExecutors;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

/**
 * A convenience wrapper of {@link DataFetcher} for fetching a single item instead of a list.
 *
 * @param <T> The type of data to fetch.
 */
public abstract class SingularDataFetcher<T> {

    private DataFetcher<T> listFetcher;

    public SingularDataFetcher(MarvelExecutors executors) {
        listFetcher = new DataFetcher<T>(executors) {
            @Override
            protected LiveData<List<T>> fetchFromDb() {
                return Transformations.map(SingularDataFetcher.this.fetchFromDb(), Collections::singletonList);
            }

            @Override
            protected Call<MarvelDataWrapper<T>> makeApiCall() {
                return SingularDataFetcher.this.makeApiCall();
            }

            @Override
            protected void cacheResultsLocally(List<T> results) {
                if (!results.isEmpty()) {
                    cacheResultLocally(results.get(0));
                }
            }
        };
    }

    /**
     * Call this method to start fetching the data from the local DB and the remote web service, if
     * necessary. Results can be observed on the LiveData object exposed by this class.
     *
     * @return This data fetcher to allow for method chaining.
     */
    @MainThread
    public SingularDataFetcher<T> fetch() {
        listFetcher.fetch();
        return this;
    }

    @NonNull
    public LiveData<FetchResult<T>> getResult() {
        return Transformations.map(listFetcher.getResult(), result -> {
            if (result.getResult() != null && !result.getResult().isEmpty()) {
                return new FetchResult<>(result.getState(), result.getResult().get(0), result.getMessage());
            }
            return new FetchResult<>(result.getState(), null, result.getMessage());
        });
    }

    @MainThread
    protected abstract LiveData<T> fetchFromDb();

    @WorkerThread
    protected abstract Call<MarvelDataWrapper<T>> makeApiCall();

    @WorkerThread
    protected abstract void cacheResultLocally(T result);
}
