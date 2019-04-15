package dk.sdu.pocketmarvel.repository.character;

public class CharacterRepository {

    private static CharacterRepository instance;

    public static CharacterRepository getInstance() {
        if (instance == null) {
            instance = new CharacterRepository();
        }
        return instance;
    }

    //TODO: Methods for getting specific character data
}
