package dk.sdu.pocketmarvel.feature.comic;

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

public class ComicFragment extends Fragment {

    private ImageView comicThumbnail;
    private TextView comicTitle;
    private TextView comicDescription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comic_fragment, container, false);
        comicThumbnail = view.findViewById(R.id.iv_comic);
        comicTitle = view.findViewById(R.id.tv_comic);
        comicDescription = view.findViewById(R.id.tv_comic_description);

        int id = getArguments() == null ? -1 : getArguments().getInt(DetailContract.CONTENT_ID);
        ComicViewModel comicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);
        comicViewModel.init(id);

        comicViewModel.getComic().observe(this, result -> {
            if (result.getState() != FetchResult.State.Success && result.getState() != FetchResult.State.Fetching) {
                Log.i(LogContract.POCKETMARVEL_TAG, result.getMessage());
            }

            if (result.getState() == FetchResult.State.Success) {
                comicTitle.setText(result.getResult().getTitle());
                String description = result.getResult().getDescription();

                if (description == null || description.isEmpty()) {
                    comicDescription.setText("Discription is missing");
                } else {
                    comicDescription.setText(description);
                }

                GlideApp.with(getContext())
                        .load(result.getResult().getThumbnail().getPath() + "/portrait_uncanny." + result.getResult().getThumbnail().getExtension())
                        .into(comicThumbnail);

            }  else if (result.getState() == FetchResult.State.Fetching) {
                comicTitle.setText("Loading...");
                comicDescription.setText("Loading...");
            }
        });

        return view;
    }

}
