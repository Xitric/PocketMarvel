package dk.sdu.pocketmarvel.repository.comic;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;

import dk.sdu.pocketmarvel.MarvelExecutors;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.PagedData;
import dk.sdu.pocketmarvel.repository.SingularDataFetcher;
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

    private void refreshComics() {
        MarvelExecutors.getInstance().getBackground().execute(() ->
                marvelDatabase.comicDao().deleteAllComics());
    }

    public PagedData<Comic> getComicsPaged() {
        Executor fetchExecutor = MarvelExecutors.getInstance().getBackground();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(12)
                .setInitialLoadSizeHint(36)
                .setPrefetchDistance(12)
                .setEnablePlaceholders(true)
                .build();

        ComicBoundaryCallback boundaryCallback = new ComicBoundaryCallback(marvelDatabase, pagedConfig.pageSize);

        LiveData<PagedList<Comic>> pagedListLiveData = new LivePagedListBuilder<>(marvelDatabase.comicDao().allComics(), pagedConfig)
                .setFetchExecutor(fetchExecutor)
                .setBoundaryCallback(boundaryCallback)
                .build();

        return new PagedData<>(pagedListLiveData, boundaryCallback, this::refreshComics);
    }

    public LiveData<FetchResult<Comic>> getComic(int comicId) {
        return new SingularDataFetcher<Comic>(MarvelExecutors.getInstance()) {
            @Override
            protected LiveData<Comic> fetchFromDb() {
                return marvelDatabase.comicDao().load(comicId);
            }

            @Override
            protected Call<MarvelDataWrapper<Comic>> makeApiCall() {
                return MarvelClient.getService().getComic(comicId);
            }

            @Override
            protected void cacheResultLocally(Comic comic) {
                marvelDatabase.comicDao().saveComic(comic);
            }
        }.fetch().getResult();
    }
}
