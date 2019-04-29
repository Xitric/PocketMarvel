package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.ComicSummary;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class CharacterRepository {

    private static CharacterRepository instance;
    private MarvelDatabase marvelDatabase;

    public static CharacterRepository getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new CharacterRepository();
            instance.marvelDatabase = MarvelDatabase.getInstance(applicationContext);
        }
        return instance;
    }

    public LiveData<FetchResult<Character>> getCharacter(int id) {
        return new DataFetcher<Character>() {
            @Override
            protected LiveData<Character> fetchFromDb() {
                return marvelDatabase.characterDao().load(id);
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                return MarvelClient.getService().getCharacter(id);
            }

            @Override
            protected void cacheResultsLocally(List<Character> results) {
                if (results.size() > 0) {
                    Character character = results.get(0);
                    marvelDatabase.characterDao().saveCharacter(character);

                    //When we receive a new character, we also get some information about the comics
                    //of this character that we need to save
                    marvelDatabase.comicDao().saveComicSummaries(character.getComics().getItems());

                    for (ComicSummary comicSummary: character.getComics().getItems()) {
                        //TODO: Save join table
                    }
                }
            }
        }.fetch().getResult();
    }
}
