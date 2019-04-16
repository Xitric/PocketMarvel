package dk.sdu.pocketmarvel.repository.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterRepository {

    private static CharacterRepository instance;

    public static CharacterRepository getInstance() {
        if (instance == null) {
            instance = new CharacterRepository();
        }
        return instance;
    }

    //TODO: Methods for getting specific character data
    public LiveData<Character> getCharacter(int id) {
        MutableLiveData<Character> data = new MutableLiveData<>();

        MarvelClient.getService().getCharacter(id).enqueue(new Callback<MarvelDataWrapper<Character>>() {

            @Override
            public void onResponse(Call<MarvelDataWrapper<Character>> call, Response<MarvelDataWrapper<Character>> response) {
                if (response.isSuccessful()) {
                    Character character = response.body().getData().getResults().get(0);
                    if (character != null) {
                        data.setValue(character);
                    }
                }
            }

            @Override
            public void onFailure(Call<MarvelDataWrapper<Character>> call, Throwable t) {

            }
        });
        return data;
    }
}
