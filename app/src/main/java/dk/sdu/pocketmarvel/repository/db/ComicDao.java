package dk.sdu.pocketmarvel.repository.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.Comic;
import dk.sdu.pocketmarvel.vo.ComicSummary;
import dk.sdu.pocketmarvel.vo.ComicWithImages;
import dk.sdu.pocketmarvel.vo.Image;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class ComicDao {

    @Insert(onConflict = REPLACE)
    public abstract void saveComicSummaries(List<ComicSummary> summaries);

    @Insert(onConflict = REPLACE)
    public abstract void saveCharacterComics(List<CharacterComics> characterComics);

    @Query("SELECT ComicSummary.id, ComicSummary.name, ComicSummary.year " +
            "FROM ComicSummary " +
            "INNER JOIN CharacterComics " +
            "ON CharacterComics.characterId = :characterId AND CharacterComics.comicId = ComicSummary.id")
    public abstract LiveData<List<ComicSummary>> getComicSummaries(int characterId);

    @Insert(onConflict = REPLACE)
    protected abstract void saveComicInternal(Comic comic);

    @Insert(onConflict = REPLACE)
    protected abstract void saveImages(List<Image> image);

    @Transaction
    public void saveComic(Comic comic) {
        saveComicInternal(comic);

        for (Image image : comic.getImages()) {
            image.setComicId(comic.getId());
        }

        saveImages(comic.getImages());
    }

    @Transaction
    public void saveComics(List<Comic> comics) {
        for (Comic comic : comics) {
            saveComic(comic);
        }
    }

    @Transaction
    @Query("SELECT * FROM Comic WHERE Comic.id = :comicId")
    public abstract LiveData<ComicWithImages> load(int comicId);

    @Query("SELECT * FROM Comic ORDER BY Comic.title")
    public abstract DataSource.Factory<Integer, Comic> allComics();

    @Query("SELECT COUNT(*) FROM Comic")
    public abstract int getNumberOfComics();

    @Query("DELETE FROM Comic")
    public abstract void deleteAllComics();
}
