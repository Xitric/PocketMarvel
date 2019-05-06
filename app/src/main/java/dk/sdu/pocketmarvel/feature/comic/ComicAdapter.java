package dk.sdu.pocketmarvel.feature.comic;

import android.arch.paging.PagedListAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicAdapter extends PagedListAdapter<Comic, ComicAdapter.ComicViewHolder> {

    private static final DiffUtil.ItemCallback<Comic> comicDiffCallBack = new DiffUtil.ItemCallback<Comic>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comic a, @NonNull Comic b) {
            return a.getId() == (b.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comic a, @NonNull Comic b) {
            return a.equals(b);
        }
    };
    private final OnAdapterSelectionListener adapterSelectionListener;
    private RequestManager glide;

    ComicAdapter(OnAdapterSelectionListener adapterSelectionListener, RequestManager glide) {
        super(comicDiffCallBack);
        this.adapterSelectionListener = adapterSelectionListener;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ComicViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comic_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder comicViewHolder, int i) {
        Comic comic = getItem(i);

        if (comic != null) {
            glide.clear(comicViewHolder.target);
            comicViewHolder.image.setImageDrawable(null);
            comicViewHolder.target = glide
                    .load(comic.getThumbnail().getPath() + "/portrait_xlarge." + comic.getThumbnail().getExtension())
                    .into(new SimpleTarget<Drawable>() {
                              @Override
                              public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                  comicViewHolder.image.setImageDrawable(resource);
                                  comicViewHolder.progressBar.setVisibility(View.INVISIBLE);
                              }
                          }
                    );
        }
    }

    protected class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;
        private ProgressBar progressBar;
        private SimpleTarget target;

        ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_item_comic);
            progressBar = itemView.findViewById(R.id.pb_comic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterSelectionListener.onSelected(getItem(getAdapterPosition()).getId());
        }
    }
}
