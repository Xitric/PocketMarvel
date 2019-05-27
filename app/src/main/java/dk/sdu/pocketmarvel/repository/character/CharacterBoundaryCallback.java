package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import dk.sdu.pocketmarvel.MarvelExecutors;
import dk.sdu.pocketmarvel.repository.AbstractBoundaryCallback;
import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class CharacterBoundaryCallback extends AbstractBoundaryCallback<Character> {

    public CharacterBoundaryCallback(MarvelDatabase database, int pageSize) {
        super(database, pageSize);
    }

    @Override
    public void onZeroItemsLoaded() {
        setFetcher(new DataFetcher<Character>(MarvelExecutors.getInstance()) {
            @Override
            protected LiveData<List<Character>> fetchFromDb() {
                //Simulate an empty database
                MutableLiveData<List<Character>> result = new MutableLiveData<>();
                result.setValue(new ArrayList<>());
                return result;
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                return MarvelClient.getService().getCharacters(searchTerm, 0, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                database.characterDao().saveCharacters(results, database.comicDao());
            }
        }.fetch());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Character itemAtEnd) {
        setFetcher(new DataFetcher<Character>(MarvelExecutors.getInstance()) {
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
                return MarvelClient.getService().getCharacters(searchTerm, offset, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                database.characterDao().saveCharacters(results, database.comicDao());
            }
        }.fetch());
    }
}
