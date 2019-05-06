package dk.sdu.pocketmarvel.feature.character;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;

import dk.sdu.pocketmarvel.repository.FetchStatus;
import dk.sdu.pocketmarvel.repository.PagedData;
import dk.sdu.pocketmarvel.repository.character.CharacterRepository;
import dk.sdu.pocketmarvel.vo.Character;

public class CharacterListViewModel extends AndroidViewModel {

    private PagedData<Character> pagedData;

    public CharacterListViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        pagedData = CharacterRepository.getInstance(getApplication().getApplicationContext())
                .getCharactersPaged();
    }

    public void setSearchTerm(String searchTerm) {
        pagedData.setSearchTerm(searchTerm);
    }

    /**
     * Get a list of all {@link Character Characters} that supports paging. This can be used to
     * avoid loading an excessively long list of Characters into memory.
     *
     * @return A list of Characters that supports paging.
     */
    public LiveData<PagedList<Character>> getCharactersLiveData() {
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