package dk.sdu.pocketmarvel.repository.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.sdu.pocketmarvel.vo.ComicSummary;

class ComicSummaryDeserializer implements JsonDeserializer<ComicSummary> {

    private final Pattern yearExtractor;

    ComicSummaryDeserializer() {
        yearExtractor = Pattern.compile("\\s\\((\\d+)\\)");
    }

    @Override
    public ComicSummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        //Convert URI to id
        String resourceURI = jsonObject.get("resourceURI").getAsString();
        String idString = resourceURI.substring(resourceURI.lastIndexOf('/') + 1);
        int id = Integer.parseInt(idString);

        //Extract year from name
        String rawName = jsonObject.get("name").getAsString();
        String name = getNameWithoutYear(rawName);
        int year = getYear(rawName);

        return new ComicSummary(id, name, year);
    }

    private String getNameWithoutYear(String rawName) {
        return rawName.replaceAll(yearExtractor.pattern(), "");
    }

    private int getYear(String name) {
        Matcher matcher = yearExtractor.matcher(name);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}
