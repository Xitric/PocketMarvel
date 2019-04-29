package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ComicSummary {

    @PrimaryKey
    private int id;
    private String name;

    public ComicSummary(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
