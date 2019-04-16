package dk.sdu.pocketmarvel.repository.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelClient {

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

            instance = new Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com:443/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createClient())
                    .build();
        }

        return instance;
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.interceptors().add(new ApiKeyInterceptor());
        return clientBuilder.build();
    }

    public static MarvelService getService() {
        return getInstance().create(MarvelService.class);
    }
}
