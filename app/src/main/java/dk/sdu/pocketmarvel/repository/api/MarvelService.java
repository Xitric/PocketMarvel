package dk.sdu.pocketmarvel.repository.api;

import java.util.Date;

import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.api.model.Event;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {
    @GET("v1/public/characters")
    Call<MarvelDataWrapper<Character>> getCharacter(@Query("name") String name,
                                                    @Query("nameStartsWith") String startsWith,
                                                    @Query("modifiedSince") Date since,
                                                    @Query("comics") int[] comicIds,
                                                    @Query("series") int[] seriesIds,
                                                    @Query("events") int[] eventIds,
                                                    @Query("stories") int[] storyIds,
                                                    @Query("limit") Integer amount,
                                                    @Query("offset") Integer offset);

    @GET("v1/public/characters")
    Call<MarvelDataWrapper<Character>> getCharacter(@Query("name") String name);

    @GET("v1/public/characters")
    Call<MarvelDataWrapper<Character>> getCharacters();


    @GET("v1/public/events")
    Call<MarvelDataWrapper<Event>> getEvent(@Query("name") String name);
}
