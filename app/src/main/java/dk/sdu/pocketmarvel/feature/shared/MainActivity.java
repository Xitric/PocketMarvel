package dk.sdu.pocketmarvel.feature.shared;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.character.CharacterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCharactersAction(View view) {
        Intent intent = new Intent(this, CharacterActivity.class);
        startActivity(intent);
    }

    public void onComicsAction(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);
    }

    public void onCreatorsAction(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);
    }

    public void onEventsAction(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);
    }

    public void onSeriesAction(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);
    }

    public void onStoriesAction(View view) {
        Intent intent = new Intent(this, null);
        startActivity(intent);
    }

}
