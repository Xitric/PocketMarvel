package dk.sdu.pocketmarvel.repository.comic;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dk.sdu.pocketmarvel.repository.DataFetcher;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
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

    public LiveData<FetchResult<ComicSummary>> getComicSummaries(int characterId) {
        return new DataFetcher<ComicSummary>() {
            @Override
            protected LiveData<ComicSummary> fetchFromDb() {
                return null;
            }

            @Override
            protected Call<MarvelDataWrapper<ComicSummary>> makeApiCall() {
                return null;
            }

            @Override
            protected void cacheResultsLocally(List<ComicSummary> results) {

            }
        }.fetch().getResult();
    }
}
