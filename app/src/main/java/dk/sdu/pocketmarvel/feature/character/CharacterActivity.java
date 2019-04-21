package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import dk.sdu.pocketmarvel.feature.shared.MasterDetailActivity;

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
        characterListViewModel.getCharactersLiveData().observe(this, characters ->
                characterAdapter.submitList(characters));

//        characterListViewModel.getErrorLiveData().observe(this, error ->
//                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show());
    }
}
