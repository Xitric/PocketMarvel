package dk.sdu.pocketmarvel.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PagedData<T> {

    @NonNull
    private final LiveData<PagedList<T>> pagedListLiveData;
    @NonNull
    private final LiveData<FetchStatus> networkStatus;
    @NonNull
    private final AbstractBoundaryCallback<T> boundaryCallback;
    @NonNull
    private final Runnable refresher;

    public PagedData(@NonNull LiveData<PagedList<T>> pagedListLiveData,
                     @NonNull AbstractBoundaryCallback<T> boundaryCallback,
                     @NonNull Runnable refresher) {
        this.pagedListLiveData = pagedListLiveData;
        this.networkStatus = boundaryCallback.getStatusLiveData();
        this.boundaryCallback = boundaryCallback;
        this.refresher = refresher;
    }

    @NonNull
    public LiveData<PagedList<T>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    @NonNull
    public LiveData<FetchStatus> getNetworkStatus() {
        return networkStatus;
    }

    public void refresh() {
        refresher.run();
    }

    public void setSearchTerm(@Nullable String searchTerm) {
        boundaryCallback.setSearchTerm(searchTerm);
        refresh();
    }
}
