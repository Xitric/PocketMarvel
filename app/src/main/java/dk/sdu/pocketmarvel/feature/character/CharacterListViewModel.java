package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.sdu.pocketmarvel.repository.DataFetchError;
import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.character.CharacterDataSource;
import dk.sdu.pocketmarvel.repository.character.CharacterDataSourceFactory;

public class CharacterListViewModel extends ViewModel {

    private LiveData<PagedList<Character>> charactersLiveData;
    private LiveData<DataFetchError> errorLiveData;
    private ExecutorService fetchExecutor;

    public void init() {
        fetchExecutor = Executors.newSingleThreadExecutor();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(60)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build();

        CharacterDataSourceFactory dataSourceFactory = new CharacterDataSourceFactory();

        errorLiveData = Transformations.switchMap(dataSourceFactory.getSourceLiveData(),
                CharacterDataSource::getErrorLiveData);

        charactersLiveData = new LivePagedListBuilder<>(dataSourceFactory, pagedConfig)
                .setFetchExecutor(fetchExecutor)
                .build();
    }

    /**
     * Get a list of all {@link Character Characters} that supports paging. This can be used to
     * avoid loading an excessively long list of Characters into memory.
     *
     * @return A list of Characters that supports paging.
     */
    public LiveData<PagedList<Character>> getCharactersLiveData() {
        return charactersLiveData;
    }

    public LiveData<DataFetchError> getErrorLiveData() {
        return errorLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        fetchExecutor.shutdownNow();
    }
}