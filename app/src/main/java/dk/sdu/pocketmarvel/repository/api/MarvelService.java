package dk.sdu.pocketmarvel.repository.api;

import dk.sdu.pocketmarvel.repository.api.model.Character;
import dk.sdu.pocketmarvel.repository.api.model.Event;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelService {

    @GET("v1/public/characters")
    Call<MarvelDataWrapper<Character>> getCharacters(@Query("nameStartsWith") String namePrefix,
                                                     @Query("offset") Integer offset,
                                                     @Query("limit") Integer amount);

    @GET("v1/public/characters/{id}")
    Call<MarvelDataWrapper<Character>> getCharacter(@Path("id") int id);

    @GET("v1/public/events")
    Call<MarvelDataWrapper<Event>> getEvent(@Query("name") String name);


}
