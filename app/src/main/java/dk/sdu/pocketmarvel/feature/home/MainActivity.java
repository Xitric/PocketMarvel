package dk.sdu.pocketmarvel.feature.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.character.CharacterActivity;
import dk.sdu.pocketmarvel.feature.comic.ComicActivity;

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
        Intent intent = new Intent(this, ComicActivity.class);
        startActivity(intent);
    }

}
