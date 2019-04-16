package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.character.CharacterRepository;

public class CharacterViewModel extends ViewModel {

    private LiveData<Character> character;

    public void init(int characterId) {
        if (character != null) {
            return;
        }
        character = CharacterRepository.getInstance().getCharacter(characterId);
    }

    public LiveData<Character> getCharacter() {
        return character;
    }
}
