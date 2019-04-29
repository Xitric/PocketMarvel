package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dk.sdu.pocketmarvel.vo.Character;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {

    @Insert(onConflict = REPLACE)
    void saveCharacter(Character character);

    @Insert(onConflict = REPLACE)
    void saveCharacters(List<Character> characters);

    @Query("SELECT * FROM Character WHERE id = :characterId")
    LiveData<Character> load(int characterId);

    @Query("SELECT * FROM Character ORDER BY Character.name")
    DataSource.Factory<Integer, Character> allUsers();

    @Query("SELECT COUNT(*) FROM Character")
    int getNumberOfCharacters();
}
