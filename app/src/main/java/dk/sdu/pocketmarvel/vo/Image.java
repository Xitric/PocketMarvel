package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("path")
    @Expose
    @ColumnInfo(name = "thumbnail_path")
    private String path;
    @SerializedName("extension")
    @Expose
    @ColumnInfo(name = "thumbnail_extension")
    private String extension;

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
