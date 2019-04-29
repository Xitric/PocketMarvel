package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.ComicSummary;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ComicDao {

    @Insert(onConflict = REPLACE)
    void saveComicSummaries(List<ComicSummary> summaries);

    @Insert(onConflict = REPLACE)
    void saveCharacterComics(List<CharacterComics> characterComics);

    @Query("SELECT ComicSummary.id, ComicSummary.name " +
            "FROM ComicSummary " +
            "INNER JOIN CharacterComics " +
            "ON CharacterComics.characterId = :characterId AND CharacterComics.comicId = ComicSummary.id")
    LiveData<List<ComicSummary>> getComicSummaries(int characterId);
}
