package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import java.util.concurrent.Executor;

import dk.sdu.pocketmarvel.MarvelExecutors;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.PagedData;
import dk.sdu.pocketmarvel.repository.SingularDataFetcher;
import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.db.MarvelDatabase;
import dk.sdu.pocketmarvel.vo.Character;
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

    private void refreshCharacters() {
        MarvelExecutors.getInstance().getBackground().execute(() ->
                marvelDatabase.characterDao().deleteAllCharacters());
    }

    public PagedData<Character> getCharactersPaged() {
        Executor fetchExecutor = MarvelExecutors.getInstance().getBackground();

        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(60)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(true)
                .build();

        CharacterBoundaryCallback boundaryCallback = new CharacterBoundaryCallback(marvelDatabase, pagedConfig.pageSize);

        LiveData<PagedList<Character>> pagedListLiveData = new LivePagedListBuilder<>(marvelDatabase.characterDao().getAllUsers(), pagedConfig)
                .setFetchExecutor(fetchExecutor)
                .setBoundaryCallback(boundaryCallback)
                .build();

        return new PagedData<>(pagedListLiveData, boundaryCallback, this::refreshCharacters);
    }

    public LiveData<FetchResult<Character>> getCharacter(int id) {
        return new SingularDataFetcher<Character>(MarvelExecutors.getInstance()) {
            @Override
            protected LiveData<Character> fetchFromDb() {
                return marvelDatabase.characterDao().getUser(id);
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
