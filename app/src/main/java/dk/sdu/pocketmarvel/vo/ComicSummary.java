package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ComicSummary {

    @PrimaryKey
    private int id;
    private String name;
    private int year;

    public ComicSummary(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }
}
