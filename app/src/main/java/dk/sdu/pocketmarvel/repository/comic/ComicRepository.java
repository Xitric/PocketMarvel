package dk.sdu.pocketmarvel.repository.comic;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Comic;
import dk.sdu.pocketmarvel.vo.ComicSummary;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class ComicRepository {

    private static ComicRepository instance;
    private MarvelDatabase marvelDatabase;

    public static ComicRepository getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new ComicRepository();
            instance.marvelDatabase = MarvelDatabase.getInstance(applicationContext);
        }
        return instance;
    }

    public LiveData<List<ComicSummary>> getComicSummaries(int characterId) {
        return marvelDatabase.comicDao().getComicSummaries(characterId);
    }

    public LiveData<FetchResult<Comic>> getComic(int comicId) {
        return new DataFetcher<Comic>() {
            @Override
            protected LiveData<Comic> fetchFromDb() {
                return marvelDatabase.comicDao().load(comicId);
            }

            @Override
            protected Call<MarvelDataWrapper<Comic>> makeApiCall() {
                return MarvelClient.getService().getComic(comicId);
            }

            @Override
            protected void cacheResultsLocally(List<Comic> results) {
                if (results.size() > 0) {
                    Comic comic = results.get(0);
                    marvelDatabase.comicDao().saveComic(comic);
                }
            }
        }.fetch().getResult();
    }
}
