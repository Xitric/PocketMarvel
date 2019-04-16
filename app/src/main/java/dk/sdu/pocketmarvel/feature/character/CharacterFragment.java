package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.sdu.pocketmarvel.DetailContract;
import dk.sdu.pocketmarvel.R;

public class CharacterFragment extends Fragment {

    private CharacterViewModel characterViewModel;
    private TextView characterName;
    private TextView characterDescription;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);
        characterName = view.findViewById(R.id.tv_character_name);
        characterDescription = view.findViewById(R.id.tv_character_description);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        int id = bundle.getInt(DetailContract.CONTENT_ID);
        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        characterViewModel.init(id);
        characterViewModel.getCharacter().observe(this, character -> {
            Log.i("CHARACTER", character.toString());
            characterName.setText(character.getName());
            characterDescription.setText(character.getDescription());
        });
    }

}
