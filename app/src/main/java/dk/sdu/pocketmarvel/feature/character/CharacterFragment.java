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
import android.widget.ImageView;
import android.widget.TextView;

import dk.sdu.pocketmarvel.LogContract;
import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.DetailContract;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.GlideApp;

public class CharacterFragment extends Fragment {

    private ImageView characterThumbnail;
    private TextView characterName;
    private TextView characterDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments() == null ? -1 : getArguments().getInt(DetailContract.CONTENT_ID);
        CharacterViewModel characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        characterViewModel.init(id);

        characterViewModel.getCharacter().observe(this, result -> {
            if (result.getState() != FetchResult.State.Success && result.getState() != FetchResult.State.Fetching) {
                Log.i(LogContract.POCKETMARVEL_TAG, result.getMessage());
            }

            if (result.getState() == FetchResult.State.Success) {
                characterName.setText(result.getResult().getName());
                characterDescription.setText(result.getResult().getDescription());

                GlideApp.with(getContext())
                        .load(result.getResult().getThumbnail().getPath() + "." + result.getResult().getThumbnail().getExtension())
                        .into(characterThumbnail);
            }
        });

        characterViewModel.getComics().observe(this, result -> {
            System.out.println("Hello");
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);
        characterThumbnail = view.findViewById(R.id.iv_character_thumbnail);
        characterName = view.findViewById(R.id.tv_character_name);
        characterDescription = view.findViewById(R.id.tv_character_description);
        return view;
    }
}
