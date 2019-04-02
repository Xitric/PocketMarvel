package dk.sdu.pocketmarvel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import dk.sdu.pocketmarvel.repository.api.MarvelClient;
import dk.sdu.pocketmarvel.repository.api.MarvelService;
import dk.sdu.pocketmarvel.repository.api.model.Event;
import dk.sdu.pocketmarvel.repository.api.model.MarvelDataWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            MarvelService service = MarvelClient.getService();
            try {
                MarvelDataWrapper<Event> wrapper = service.getEvent("Spider-Island")
                        .execute().body();
                Event event = wrapper.getData().getResults().get(0);

                Log.i("MARVELRESULTS", String.valueOf(event.getId()));
                Log.i("MARVELRESULTS", event.getTitle());
                Log.i("MARVELRESULTS", event.getDescription());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
