package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import dk.sdu.pocketmarvel.MasterDetailActivity;

public class CharacterActivity extends MasterDetailActivity {

    private CharacterListViewModel characterListViewModel;
    private CharacterAdapter characterAdapter;

    @Override
    protected RecyclerView.Adapter getMasterAdapter() {
        characterAdapter = new CharacterAdapter(this);
        return characterAdapter;
    }

    @Override
    protected Fragment getDetailFragment() {
        return new CharacterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characterListViewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        characterListViewModel.getLiveCharacters().observe(this, characters -> characterAdapter.setCharacterList(characters));
    }
}
