package dk.sdu.pocketmarvel.repository.db;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long time) {
        return time == null ? new Date() : new Date(time);
    }

    @TypeConverter
    public static Long toLong(Date time) {
        return time == null ? 0 : time.getTime();
    }
}
