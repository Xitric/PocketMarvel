package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import dk.sdu.pocketmarvel.repository.Expireable;

@SuppressWarnings({RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED,
        RoomWarnings.INDEX_FROM_EMBEDDED_ENTITY_IS_DROPPED})
@Entity
public class Character implements Expireable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("thumbnail")
    @Expose
    @Embedded(prefix = "thumbnail")
    private Image thumbnail;
    @SerializedName("comics")
    @Expose
    @Ignore
    private ComicList comics;
    //    @SerializedName("stories")
//    @Expose
//    private StoryList stories;
//    @SerializedName("events")
//    @Expose
//    private EventList events;
//    @SerializedName("series")
//    @Expose
//    private SeriesList series;
    private Date expiration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

//    public List<Url> getUrls() {
//        return urls;
//    }
//
//    public void setUrls(List<Url> urls) {
//        this.urls = urls;
//    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ComicList getComics() {
        return comics;
    }

    public void setComics(ComicList comics) {
        this.comics = comics;
    }

    //    public StoryList getStories() {
//        return stories;
//    }
//
//    public void setStories(StoryList stories) {
//        this.stories = stories;
//    }
//
//    public EventList getEvents() {
//        return events;
//    }
//
//    public void setEvents(EventList events) {
//        this.events = events;
//    }
//
//    public SeriesList getSeries() {
//        return series;
//    }
//
//    public void setSeries(SeriesList series) {
//        this.series = series;
//    }
    @NonNull
    @Override
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(@NonNull Date expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return id == character.id &&
                Objects.equals(name, character.name) &&
                Objects.equals(description, character.description) &&
                Objects.equals(modified, character.modified) &&
                Objects.equals(resourceURI, character.resourceURI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, modified, resourceURI);
    }
}
