package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = Comic.class,
                parentColumns = "id",
                childColumns = "comicId",
                onDelete = CASCADE,
                onUpdate = CASCADE)
})
public class Image {

    @PrimaryKey
    private int id;
    private int comicId;
    @SerializedName("path")
    @Expose
    @ColumnInfo(name = "thumbnail_path")
    private String path;
    @SerializedName("extension")
    @Expose
    @ColumnInfo(name = "thumbnail_extension")
    private String extension;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}
