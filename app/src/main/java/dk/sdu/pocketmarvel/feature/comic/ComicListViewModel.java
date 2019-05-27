package dk.sdu.pocketmarvel.feature.comic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import dk.sdu.pocketmarvel.repository.FetchStatus;
import dk.sdu.pocketmarvel.repository.PagedData;
import dk.sdu.pocketmarvel.repository.comic.ComicRepository;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicListViewModel extends AndroidViewModel {

    private PagedData<Comic> pagedData;

    public ComicListViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        pagedData = ComicRepository.getInstance(getApplication().getApplicationContext())
                .getComicsPaged();
    }

    public void setSearchTerm(String searchTerm) {
        pagedData.setSearchTerm(searchTerm);
    }

    /**
     * Get a list of all {@link Comic Comics} that supports paging. This can be used to
     * avoid loading an excessively long list of Comics into memory.
     *
     * @return A list of Comics that supports paging.
     */
    public LiveData<PagedList<Comic>> getComicsLiveData() {
        return pagedData.getPagedListLiveData();
    }

    /**
     * Get a live representation of the network status for paging.
     *
     * @return A live representation of the network status for paging.
     */
    public LiveData<FetchStatus> getNetworkStatusLiveData() {
        return pagedData.getNetworkStatus();
    }
}
