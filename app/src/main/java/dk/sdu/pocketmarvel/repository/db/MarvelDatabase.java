package dk.sdu.pocketmarvel.repository.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import dk.sdu.pocketmarvel.vo.Character;
import dk.sdu.pocketmarvel.vo.CharacterComics;
import dk.sdu.pocketmarvel.vo.ComicSummary;

@android.arch.persistence.room.Database(
        entities = {
                Character.class,
                ComicSummary.class,
                CharacterComics.class
        },
        version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MarvelDatabase extends RoomDatabase {

    private static MarvelDatabase instance;

    public static MarvelDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MarvelDatabase.class, "marvel-database")
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance.close();
        instance = null;
    }

    public abstract CharacterDao characterDao();

    public abstract ComicDao comicDao();
}
