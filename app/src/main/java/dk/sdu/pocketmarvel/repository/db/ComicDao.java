package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.Comic;
import dk.sdu.pocketmarvel.vo.ComicSummary;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ComicDao {

    @Insert(onConflict = REPLACE)
    void saveComicSummaries(List<ComicSummary> summaries);

    @Insert(onConflict = REPLACE)
    void saveCharacterComics(List<CharacterComics> characterComics);

    @Query("SELECT ComicSummary.id, ComicSummary.name, ComicSummary.year " +
            "FROM ComicSummary " +
            "INNER JOIN CharacterComics " +
            "ON CharacterComics.characterId = :characterId AND CharacterComics.comicId = ComicSummary.id")
    LiveData<List<ComicSummary>> getComicSummaries(int characterId);

    @Insert(onConflict = REPLACE)
    void saveComic(Comic comic);

    @Insert(onConflict = REPLACE)
    void saveComics(List<Comic> comics);

    @Query("SELECT * FROM Comic WHERE Comic.id = :comicId")
    LiveData<Comic> load(int comicId);

    @Query("SELECT * FROM Comic ORDER BY Comic.title")
    DataSource.Factory<Integer, Comic> allComics();

    @Query("SELECT COUNT(*) FROM Comic")
    int getNumberOfComics();

    @Query("DELETE FROM Comic")
    void deleteAllComics();
}
