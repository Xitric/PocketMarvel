package dk.sdu.pocketmarvel.repository.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import dk.sdu.pocketmarvel.vo.Character;

@android.arch.persistence.room.Database(entities = {Character.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class CharacterDatabase extends RoomDatabase {

    private static CharacterDatabase instance;

    public static CharacterDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CharacterDatabase.class, "character-database")
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance.close();
        instance = null;
    }

    public abstract CharacterDao characterDao();
}
