package dk.sdu.pocketmarvel.feature.character;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.character.CharacterRepository;
import dk.sdu.pocketmarvel.repository.comic.ComicRepository;
import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.ComicSummary;

public class CharacterViewModel extends AndroidViewModel {

    private LiveData<FetchResult<Character>> character;
    private LiveData<List<ComicSummary>> comics;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(int characterId) {
        if (character != null) {
            return;
        }
        character = CharacterRepository.getInstance(getApplication().getApplicationContext()).getCharacter(characterId);
        comics = ComicRepository.getInstance(getApplication().getApplicationContext()).getComicSummaries(characterId);
    }

    public LiveData<FetchResult<Character>> getCharacter() {
        return character;
    }

    public LiveData<List<ComicSummary>> getComics() {
        return comics;
    }
}
