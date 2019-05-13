package dk.sdu.pocketmarvel.repository.comic;

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
import dk.sdu.pocketmarvel.vo.Comic;
import dk.sdu.pocketmarvel.vo.MarvelDataWrapper;
import retrofit2.Call;

public class ComicBoundaryCallback extends AbstractBoundaryCallback<Comic> {

    public ComicBoundaryCallback(MarvelDatabase database, int pageSize) {
        super(database, pageSize);
    }

    @Override
    public void onZeroItemsLoaded() {
        setFetcher(new DataFetcher<Comic>(MarvelExecutors.getInstance()) {
            @Override
            protected LiveData<List<Comic>> fetchFromDb() {
                //Simulate an empty database
                MutableLiveData<List<Comic>> result = new MutableLiveData<>();
                result.setValue(new ArrayList<>());
                return result;
            }

            @Override
            protected Call<MarvelDataWrapper<Comic>> makeApiCall() {
                return MarvelClient.getService().getComics(searchTerm, 0, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Comic> results) {
                database.comicDao().saveComics(results);
            }
        }.fetch());
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Comic itemAtEnd) {
        setFetcher(new DataFetcher<Comic>(MarvelExecutors.getInstance()) {
            @Override
            protected LiveData<List<Comic>> fetchFromDb() {
                //Simulate an empty database
                MutableLiveData<List<Comic>> result = new MutableLiveData<>();
                result.setValue(new ArrayList<>());
                return result;
            }

            @Override
            protected Call<MarvelDataWrapper<Comic>> makeApiCall() {
                int offset = database.comicDao().getNumberOfComics();
                return MarvelClient.getService().getComics(searchTerm, offset, pageSize);
            }

            @Override
            protected void cacheResultsLocally(List<Comic> results) {
                database.comicDao().saveComics(results);
            }
        }.fetch());
    }
}
