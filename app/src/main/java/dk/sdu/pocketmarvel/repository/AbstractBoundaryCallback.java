package dk.sdu.pocketmarvel.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;

import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;

public abstract class AbstractBoundaryCallback<T> extends PagedList.BoundaryCallback<T> {

    protected final MarvelDatabase database;
    protected final int pageSize;
    protected String searchTerm;
    private MutableLiveData<DataFetcher<T>> fetcherLiveData = new MutableLiveData<>();
    private LiveData<FetchStatus> statusLiveData;

    public AbstractBoundaryCallback(MarvelDatabase database, int pageSize) {
        this.database = database;
        this.pageSize = pageSize;

        statusLiveData = Transformations.switchMap(fetcherLiveData, input ->
                Transformations.map(fetcherLiveData.getValue().getResult(), input1 ->
                        new FetchStatus(input1.getState(), input1.getMessage())));
    }

    public LiveData<FetchStatus> getStatusLiveData() {
        return statusLiveData;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    protected void setFetcher(DataFetcher<T> fetcher) {
        fetcherLiveData.setValue(fetcher);
    }
}
