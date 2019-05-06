package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.FetchStatus;
import dk.sdu.pocketmarvel.repository.NetworkStatus;
import dk.sdu.pocketmarvel.repository.SingularDataFetcher;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.ComicSummary;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class CharacterRepository {

    private static CharacterRepository instance;
    private MarvelDatabase marvelDatabase;

    private LiveData<FetchStatus> statusLiveData;

    public static CharacterRepository getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new CharacterRepository();
            instance.marvelDatabase = MarvelDatabase.getInstance(applicationContext);
        }
        return instance;
    }

    public LiveData<FetchStatus> getStatusLiveData() {
        return statusLiveData;
    }

    public LiveData<PagedList<Character>> getCharactersPaged() {
        Executor fetchExecutor = Executors.newSingleThreadExecutor();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(60)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build();

        CharacterBoundaryCallback boundaryCallback = new CharacterBoundaryCallback(marvelDatabase, pagedConfig.pageSize);
        statusLiveData = boundaryCallback.getStatusLiveData();

        return new LivePagedListBuilder<>(marvelDatabase.characterDao().allUsers(), pagedConfig)
                .setFetchExecutor(fetchExecutor)
                .setBoundaryCallback(boundaryCallback)
                .build();
    }

    public LiveData<FetchResult<Character>> getCharacter(int id) {
        return new SingularDataFetcher<Character>() {
            @Override
            protected LiveData<Character> fetchFromDb() {
                return marvelDatabase.characterDao().load(id);
            }

            @Override
            protected Call<MarvelDataWrapper<Character>> makeApiCall() {
                return MarvelClient.getService().getCharacter(id);
            }

            @Override
            protected void cacheResultLocally(Character result) {
                marvelDatabase.characterDao().saveCharacter(result, marvelDatabase.comicDao());
            }
        }.fetch().getResult();
    }
}
