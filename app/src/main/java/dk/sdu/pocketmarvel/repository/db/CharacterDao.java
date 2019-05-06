package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.ComicSummary;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class CharacterDao {

    @Insert(onConflict = REPLACE)
    protected abstract void saveCharacterInternal(Character character);

    @Transaction
    public void saveCharacter(Character character, ComicDao comicDao) {
        saveCharacterInternal(character);

        //When we receive a new character, we also get some information about the comics
        //of this character that we need to save
        comicDao.saveComicSummaries(character.getComics().getItems());

        List<CharacterComics> characterComics = new ArrayList<>();
        for (ComicSummary comicSummary : character.getComics().getItems()) {
            characterComics.add(new CharacterComics(character.getId(), comicSummary.getId()));
        }
        comicDao.saveCharacterComics(characterComics);
    }

    @Transaction
    public void saveCharacters(List<Character> characters, ComicDao comicDao) {
        for (Character character : characters) {
            saveCharacter(character, comicDao);
        }
    }

    @Query("SELECT * FROM Character WHERE id = :characterId")
    public abstract LiveData<Character> load(int characterId);

    @Query("SELECT * FROM Character ORDER BY Character.name")
    public abstract DataSource.Factory<Integer, Character> allUsers();

    @Query("SELECT COUNT(*) FROM Character")
    public abstract int getNumberOfCharacters();

    @Query("DELETE FROM Character")
    public abstract void deleteAllCharacters();
}
