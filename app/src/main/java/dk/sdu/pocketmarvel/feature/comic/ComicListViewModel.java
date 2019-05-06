package dk.sdu.pocketmarvel.feature.comic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.sdu.pocketmarvel.repository.comic.ComicDataSourceFactory;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicListViewModel extends ViewModel {

    private LiveData<PagedList<Comic>> comicsLiveData;
    private ExecutorService fetchExecutor;

    public ComicListViewModel() {
        init();
    }

    private void init() {
        fetchExecutor = Executors.newSingleThreadExecutor();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(60)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build();

        ComicDataSourceFactory dataSourceFactory = new ComicDataSourceFactory();

        comicsLiveData = new LivePagedListBuilder<>(dataSourceFactory, pagedConfig)
                .setFetchExecutor(fetchExecutor)
                .build();
    }

    public LiveData<PagedList<Comic>> getComicsLiveData() {
        return comicsLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        fetchExecutor.shutdownNow();
    }
}
