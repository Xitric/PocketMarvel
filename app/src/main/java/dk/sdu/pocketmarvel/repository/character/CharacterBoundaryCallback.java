package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.FetchStatus;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class CharacterBoundaryCallback extends PagedList.BoundaryCallback<Character> {

    private final MarvelDatabase database;
    private final int pageSize;

    private MutableLiveData<DataFetcher<Character>> fetcher = new MutableLiveData<>();
    private LiveData<FetchStatus> statusLiveData;

    public CharacterBoundaryCallback(MarvelDatabase database, int pageSize) {
        this.database = database;
        this.pageSize = pageSize;

        statusLiveData = Transformations.switchMap(fetcher, input ->
                Transformations.map(fetcher.getValue().getResult(), input1 ->
                        new FetchStatus(input1.getState(), input1.getMessage())));
    }

    public LiveData<FetchStatus> getStatusLiveData() {
        return statusLiveData;
    }

    @Override
    public void onZeroItemsLoaded() {
        fetcher.setValue(new DataFetcher<Character>() {
            @Override
            protected LiveData<List<Character>> fetchFromDb() {
                //Simulate an empty database
                MutableLiveData<List<Character>> result = new MutableLiveData<>();
                result.setValue(new ArrayList<>());
                return result;
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                return MarvelClient.getService().getCharacters(null, 0, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                database.characterDao().saveCharacters(results, database.comicDao());
            }
        }.fetch());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Character itemAtEnd) {
        fetcher.setValue(new DataFetcher<Character>() {
            @Override
            protected LiveData<List<Character>> fetchFromDb() {
                //Simulate an empty database
                MutableLiveData<List<Character>> result = new MutableLiveData<>();
                result.setValue(new ArrayList<>());
                return result;
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                int offset = database.characterDao().getNumberOfCharacters();
                return MarvelClient.getService().getCharacters(null, offset, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                database.characterDao().saveCharacters(results, database.comicDao());
            }
        }.fetch());
    }
}
