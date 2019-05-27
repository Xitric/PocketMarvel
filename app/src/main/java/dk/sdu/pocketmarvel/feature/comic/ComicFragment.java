package dk.sdu.pocketmarvel.feature.comic;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import dk.sdu.pocketmarvel.LogContract;
import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.DetailContract;
import dk.sdu.pocketmarvel.repository.FetchResult;
import dk.sdu.pocketmarvel.repository.GlideApp;
import dk.sdu.pocketmarvel.repository.GlideRequests;
import dk.sdu.pocketmarvel.repository.NetworkStatus;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicFragment extends Fragment {

    private ComicViewModel comicViewModel;
    private CarouselView carouselView;
    private TextView comicTitle;
    private TextView comicDescription;
    private TextView comicIssue;
    private TextView comicPages;
    private GlideRequests glide;

    private ImageListener carouselListener = (position, imageView) -> {
        FetchResult<Comic> result = comicViewModel.getComic().getValue();
        if (result == null) return;
        Comic comic = result.getResult();
        if (comic == null) return;

        glide.clear(imageView);
        if (position == 0) {
            glide.load(comic.getThumbnail().getPath() + "/portrait_uncanny." + comic.getThumbnail().getExtension())
                    .into(imageView);
        } else {
            glide.load(comic.getImages().get(position).getPath() + "/portrait_uncanny." + comic.getImages().get(position).getExtension())
                    .into(imageView);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        glide = GlideApp.with(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getArguments() == null ? -1 : getArguments().getInt(DetailContract.CONTENT_ID);
        comicViewModel = ViewModelProviders.of(this).get(ComicViewModel.class);
        comicViewModel.init(id);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comic_fragment, container, false);
        carouselView = view.findViewById(R.id.cv_comic);
        comicTitle = view.findViewById(R.id.tv_comic);
        comicDescription = view.findViewById(R.id.tv_comic_description);
        comicIssue = view.findViewById(R.id.tv_comic_issue);
        comicPages = view.findViewById(R.id.tv_comic_pageCount);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //When we are sure that the view has been properly initialized we can begin to present
        //content
        carouselView.setImageListener(carouselListener);

        comicViewModel.getComic().observe(this, result -> {
            assert result != null;
            if (result.getState() != NetworkStatus.Success && result.getState() != NetworkStatus.Fetching) {
                Log.i(LogContract.POCKETMARVEL_TAG, result.getMessage());
            } else if (result.getState() == NetworkStatus.Success) {
                assert result.getResult() != null;
                comicTitle.setText(result.getResult().getTitle());
                String description = result.getResult().getDescription();

                if (description == null || description.isEmpty()) {
                    comicDescription.setText(R.string.missing_description);
                } else {
                    comicDescription.setText(description);
                }

                comicIssue.setText(String.valueOf(result.getResult().getIssueNumber()));
                comicPages.setText(String.valueOf(result.getResult().getPageCount()));

                carouselView.setPageCount(result.getResult().getImages().size());

            } else if (result.getState() == NetworkStatus.Fetching) {
                if (result.getResult() != null) {
                    comicTitle.setText(result.getResult().getTitle());
                    comicDescription.setText(result.getResult().getDescription());
                    comicIssue.setText(result.getResult().getIssueNumber());
                    comicPages.setText(result.getResult().getPageCount());
                } else {
                    comicTitle.setText(R.string.loading);
                    comicDescription.setText(R.string.loading);
                    comicIssue.setText(R.string.loading);
                    comicPages.setText(R.string.loading);
                }
            }
        });
    }
}
