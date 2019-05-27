package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import dk.sdu.pocketmarvel.feature.comic.ComicActivity;
import dk.sdu.pocketmarvel.feature.shared.DetailContract;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.repository.GlideApp;
import dk.sdu.pocketmarvel.repository.NetworkStatus;

public class CharacterFragment extends Fragment implements OnAdapterSelectionListener {

    private CharacterViewModel characterViewModel;

    private ImageView characterThumbnail;
    private TextView characterName;
    private TextView characterDescription;
    private CharacterComicsAdapter comicsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments() == null ? -1 : getArguments().getInt(DetailContract.CONTENT_ID);

        //The view model is only created once for the fragment so it will retain state during
        //context switching. This might create a new view model, or it might reuse an existing one
        characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        characterViewModel.init(id);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);
        characterThumbnail = view.findViewById(R.id.iv_character_thumbnail);
        characterName = view.findViewById(R.id.tv_character_name);
        characterDescription = view.findViewById(R.id.tv_character_description);
        comicsAdapter = new CharacterComicsAdapter(this);

        RecyclerView comicsRecycler = view.findViewById(R.id.rv_character_comics);
        comicsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        comicsRecycler.setHasFixedSize(true);
        comicsRecycler.setAdapter(comicsAdapter);
        if (getContext() != null) {
            comicsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //When we are sure that the view has been properly initialized we can begin to present
        //content
        characterViewModel.getCharacter().observe(this, result -> {
            assert result != null;
            if (result.getState() != NetworkStatus.Success && result.getState() != NetworkStatus.Fetching) {
                Log.i(LogContract.POCKETMARVEL_TAG, result.getMessage());
            } else if (result.getState() == NetworkStatus.Success) {
                assert result.getResult() != null;
                characterName.setText(result.getResult().getName());
                characterDescription.setText(result.getResult().getDescription().isEmpty() ? "Description missing" : result.getResult().getDescription());

                if (getContext() != null) {
                    GlideApp.with(getContext())
                            .load(result.getResult().getThumbnail().getPath() + "." + result.getResult().getThumbnail().getExtension())
                            .into(characterThumbnail);
                }
            } else if (result.getState() == NetworkStatus.Fetching) {
                if (result.getResult() != null) {
                    characterName.setText(result.getResult().getName());
                    characterDescription.setText(result.getResult().getDescription());
                } else {
                    characterName.setText(R.string.loading);
                    characterDescription.setText(R.string.loading);
                }
            }
        });

        characterViewModel.getComics().observe(this, result -> comicsAdapter.setComics(result));
    }

    @Override
    public void onSelected(int id) {
        Intent intent = new Intent(this.getContext(), ComicActivity.class);
        intent.putExtra(DetailContract.CONTENT_ID, id);
        startActivity(intent);
    }
}
