package dk.sdu.pocketmarvel.repository.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import dk.sdu.pocketmarvel.vo.ComicSummary;

class ComicSummaryDeserializer implements JsonDeserializer<ComicSummary> {

    @Override
    public ComicSummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        //Convert URI to id
        String resourceURI = jsonObject.get("resourceURI").getAsString();
        String idString = resourceURI.substring(resourceURI.lastIndexOf('/') + 1);
        int id = Integer.parseInt(idString);

        String name = jsonObject.get("name").getAsString();

        return new ComicSummary(id, name);
    }
}
