package dk.sdu.pocketmarvel.repository.comic;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.ComicSummary;

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
}
