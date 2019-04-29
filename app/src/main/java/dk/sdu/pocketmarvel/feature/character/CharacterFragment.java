package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dk.sdu.pocketmarvel.LogContract;
import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.DetailContract;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.GlideApp;

public class CharacterFragment extends Fragment implements OnAdapterSelectionListener {

    private ImageView characterThumbnail;
    private TextView characterName;
    private TextView characterDescription;
    private RecyclerView comicsRecycler;
    private CharacterComicsAdapter comicsAdapter;

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
                characterDescription.setText(result.getResult().getDescription().isEmpty() ? "Description missing" : result.getResult().getDescription());

                GlideApp.with(getContext())
                        .load(result.getResult().getThumbnail().getPath() + "." + result.getResult().getThumbnail().getExtension())
                        .into(characterThumbnail);
            } else if (result.getState() == FetchResult.State.Fetching) {
                characterName.setText("Loading...");
                characterDescription.setText("Loading...");
            }
        });

        characterViewModel.getComics().observe(this, result -> {
            comicsAdapter.setComics(result);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);
        characterThumbnail = view.findViewById(R.id.iv_character_thumbnail);
        characterName = view.findViewById(R.id.tv_character_name);
        characterDescription = view.findViewById(R.id.tv_character_description);
        comicsRecycler = view.findViewById(R.id.rv_character_comics);

        comicsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        comicsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        comicsRecycler.setHasFixedSize(true);
        comicsAdapter = new CharacterComicsAdapter(this);
        comicsRecycler.setAdapter(comicsAdapter);
        return view;
    }

    @Override
    public void onSelected(int id) {
        Log.i(LogContract.POCKETMARVEL_TAG, "You pressed comic with id " + id);
    }
}
