package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class ComicWithImages {

    @Embedded
    public Comic comic;

    @Relation(parentColumn = "id", entityColumn = "comicId")
    public List<Image> images;
}
