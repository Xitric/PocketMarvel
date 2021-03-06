package dk.sdu.pocketmarvel.feature.comic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.comic.ComicRepository;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicViewModel extends AndroidViewModel {

    private LiveData<FetchResult<Comic>> comic;

    public ComicViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(int comicId) {
        if (comic != null) {
            return;
        }
        comic = ComicRepository.getInstance(getApplication().getApplicationContext()).getComic(comicId);
    }

    public LiveData<FetchResult<Comic>> getComic() {
        return comic;
    }
}
