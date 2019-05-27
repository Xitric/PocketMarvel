package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"characterId", "comicId"},
        foreignKeys = {
                @ForeignKey(entity = Character.class,
                        parentColumns = "id",
                        childColumns = "characterId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE),
                @ForeignKey(entity = ComicSummary.class,
                        parentColumns = "id",
                        childColumns = "comicId",
                        onDelete = CASCADE,
                        onUpdate = CASCADE)
        },
        indices = {
                @Index("characterId"),
                @Index("comicId")
        })
public class CharacterComics {

    private int characterId;
    private int comicId;

    public CharacterComics(int characterId, int comicId) {
        this.characterId = characterId;
        this.comicId = comicId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getComicId() {
        return comicId;
    }
}
