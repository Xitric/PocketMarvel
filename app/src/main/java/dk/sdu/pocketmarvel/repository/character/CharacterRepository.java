package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.CharacterDatabase;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class CharacterRepository {

    private static CharacterRepository instance;
    private CharacterDatabase characterDatabase;

    public static CharacterRepository getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new CharacterRepository();
            instance.characterDatabase = CharacterDatabase.getInstance(applicationContext);
        }
        return instance;
    }

    public LiveData<FetchResult<Character>> getCharacter(int id) {
        return new DataFetcher<Character>() {
            @Override
            protected LiveData<Character> fetchFromDb() {
                return characterDatabase.characterDao().load(id);
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                return MarvelClient.getService().getCharacter(id);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                if (results.size() > 0) {
                    characterDatabase.characterDao().saveCharacter(results.get(0));
                }
            }
        }.fetch().getResult();
    }
}
