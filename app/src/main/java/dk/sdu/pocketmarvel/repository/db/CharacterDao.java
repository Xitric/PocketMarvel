package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import dk.sdu.pocketmarvel.vo.Character;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {

    @Insert(onConflict = REPLACE)
    void saveCharacter(Character character);

    @Query("SELECT * FROM Character WHERE id = :characterId")
    LiveData<Character> load(int characterId);
}
