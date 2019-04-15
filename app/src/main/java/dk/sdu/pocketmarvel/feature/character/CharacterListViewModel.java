package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.api.MarvelService;
import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;

public class CharacterListViewModel extends ViewModel {
    private MutableLiveData<List<Character>> liveCharacters;

    public LiveData<List<Character>> getLiveCharacters() {
        return liveCharacters;
    }

    public CharacterListViewModel() {
        liveCharacters = new MutableLiveData<>();
        CharacterTask characterTask = new CharacterTask(liveCharacters);
        characterTask.execute();
    }

    private static class CharacterTask extends AsyncTask<Void, Void, List<Character>> {

        private WeakReference<MutableLiveData<List<Character>>> liveCharacters;

        public CharacterTask(MutableLiveData<List<Character>> liveCharacters) {
            this.liveCharacters = new WeakReference<>(liveCharacters);
        }

        @Override
        protected List<Character> doInBackground(Void... voids) {
            MarvelService service = MarvelClient.getService();
            try {
                MarvelDataWrapper<Character> wrapper = service.getCharacters()
                        .execute().body();

                return wrapper.getData().getResults();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Character> characters) {
            MutableLiveData<List<Character>> characterList = liveCharacters.get();
            if (characterList != null) {
                characterList.setValue(characters);
            }
        }
    }
}
