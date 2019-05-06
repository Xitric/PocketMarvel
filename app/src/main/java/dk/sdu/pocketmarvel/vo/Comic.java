package dk.sdu.pocketmarvel.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import dk.sdu.pocketmarvel.repository.Expireable;

@Entity
public class Comic implements Expireable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("resourceURI")
    @Expose
    private String resourceURI;
    @SerializedName("thumbnail")
    @Expose
    @Embedded
    private Image thumbnail;
//    @SerializedName("images")
//    @Expose
//    @Embedded
//    private List<Image> images = null;
    //    @SerializedName("digitalId")
//    @Expose
//    private String digitalId;

    //    @SerializedName("issueNumber")
//    @Expose
//    private String issueNumber;
//    @SerializedName("variantDescription")
//    @Expose
//    private String variantDescription;

    //    @SerializedName("modified")
//    @Expose
//    private String modified;
//    @SerializedName("isbn")
//    @Expose
//    private String isbn;
//    @SerializedName("upc")
//    @Expose
//    private String upc;
//    @SerializedName("diamondCode")
//    @Expose
//    private String diamondCode;
//    @SerializedName("ean")
//    @Expose
//    private String ean;
//    @SerializedName("issn")
//    @Expose
//    private String issn;
//    @SerializedName("format")
//    @Expose
//    private String format;
//    @SerializedName("pageCount")
//    @Expose
//    private String pageCount;
//    @SerializedName("textObjects")
//    @Expose
//    private List<TextObject> textObjects = null;
//    @SerializedName("urls")
//    @Expose
//    private List<Url> urls = null;
//    @SerializedName("series")
//    @Expose
//    private SeriesSummary series;
//    @SerializedName("variants")
//    @Expose
//    private List<ComicSummary> variants = null;
//    @SerializedName("collections")
//    @Expose
//    private List<ComicSummary> collections = null;
//    @SerializedName("collectedIssues")
//    @Expose
//    private List<ComicSummary> collectedIssues = null;
//    @SerializedName("dates")
//    @Expose
//    private List<ComicDate> dates = null;
//    @SerializedName("prices")
//    @Expose
//    private List<ComicPrice> prices = null;
//    @SerializedName("creators")
//    @Expose
//    private CreatorList creators;
//    @SerializedName("characters")
//    @Expose
//    private CharacterList characters;
//    @SerializedName("stories")
//    @Expose
//    private StoryList stories;
//    @SerializedName("events")
//    @Expose
//    private EventList events;
    private Date expiration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }


//    public String getDigitalId() {
//        return digitalId;
//    }
//
//    public void setDigitalId(String digitalId) {
//        this.digitalId = digitalId;
//    }
//

//
//    public String getIssueNumber() {
//        return issueNumber;
//    }
//
//    public void setIssueNumber(String issueNumber) {
//        this.issueNumber = issueNumber;
//    }
//
//    public String getVariantDescription() {
//        return variantDescription;
//    }
//
//    public void setVariantDescription(String variantDescription) {
//        this.variantDescription = variantDescription;
//    }

//    public String getModified() {
//        return modified;
//    }
//
//    public void setModified(String modified) {
//        this.modified = modified;
//    }
//
//    public String getIsbn() {
//        return isbn;
//    }
//
//    public void setIsbn(String isbn) {
//        this.isbn = isbn;
//    }
//
//    public String getUpc() {
//        return upc;
//    }
//
//    public void setUpc(String upc) {
//        this.upc = upc;
//    }
//
//    public String getDiamondCode() {
//        return diamondCode;
//    }
//
//    public void setDiamondCode(String diamondCode) {
//        this.diamondCode = diamondCode;
//    }
//
//    public String getEan() {
//        return ean;
//    }
//
//    public void setEan(String ean) {
//        this.ean = ean;
//    }
//
//    public String getIssn() {
//        return issn;
//    }
//
//    public void setIssn(String issn) {
//        this.issn = issn;
//    }
//
//    public String getFormat() {
//        return format;
//    }
//
//    public void setFormat(String format) {
//        this.format = format;
//    }
//
//    public String getPageCount() {
//        return pageCount;
//    }
//
//    public void setPageCount(String pageCount) {
//        this.pageCount = pageCount;
//    }
//
//    public List<TextObject> getTextObjects() {
//        return textObjects;
//    }
//
//    public void setTextObjects(List<TextObject> textObjects) {
//        this.textObjects = textObjects;
//    }
//
//    public List<Url> getUrls() {
//        return urls;
//    }
//
//    public void setUrls(List<Url> urls) {
//        this.urls = urls;
//    }
//
//    public SeriesSummary getSeries() {
//        return series;
//    }
//
//    public void setSeries(SeriesSummary series) {
//        this.series = series;
//    }
//
//    public List<ComicSummary> getVariants() {
//        return variants;
//    }
//
//    public void setVariants(List<ComicSummary> variants) {
//        this.variants = variants;
//    }
//
//    public List<ComicSummary> getCollections() {
//        return collections;
//    }
//
//    public void setCollections(List<ComicSummary> collections) {
//        this.collections = collections;
//    }
//
//    public List<ComicSummary> getCollectedIssues() {
//        return collectedIssues;
//    }
//
//    public void setCollectedIssues(List<ComicSummary> collectedIssues) {
//        this.collectedIssues = collectedIssues;
//    }
//
//    public List<ComicDate> getDates() {
//        return dates;
//    }
//
//    public void setDates(List<ComicDate> dates) {
//        this.dates = dates;
//    }
//
//    public List<ComicPrice> getPrices() {
//        return prices;
//    }
//
//    public void setPrices(List<ComicPrice> prices) {
//        this.prices = prices;
//    }

//    public CreatorList getCreators() {
//        return creators;
//    }
//
//    public void setCreators(CreatorList creators) {
//        this.creators = creators;
//    }
//
//    public CharacterList getCharacters() {
//        return characters;
//    }
//
//    public void setCharacters(CharacterList characters) {
//        this.characters = characters;
//    }
//
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
        Comic comic = (Comic) o;
        return id == comic.id &&
                title.equals(comic.title) &&
                Objects.equals(description, comic.description) &&
                Objects.equals(resourceURI, comic.resourceURI) &&
                Objects.equals(thumbnail, comic.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, resourceURI, thumbnail);
    }
}
