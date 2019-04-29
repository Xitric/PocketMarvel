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

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.repository.GlideApp;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicAdapter extends PagedListAdapter<Comic, ComicAdapter.ComicViewHolder> {

    private static final DiffUtil.ItemCallback<Comic> comicDiffCallBack = new DiffUtil.ItemCallback<Comic>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comic a, @NonNull Comic b) {
            return a.getId().equals(b.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comic a, @NonNull Comic b) {
            return a.equals(b);
        }
    };

    private final OnAdapterSelectionListener adapterSelectionListener;

    protected ComicAdapter(OnAdapterSelectionListener adapterSelectionListener) {
        super(comicDiffCallBack);
        this.adapterSelectionListener = adapterSelectionListener;
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
         if (comic == null){
            // Set placeholder
        } else {
            comicViewHolder.image.setImageResource(R.drawable.loader);
             GlideApp.with(comicViewHolder.itemView.getContext())
                     .load(comic.getThumbnail().getPath() + "/landscape_incredible." + comic.getThumbnail().getExtension())
                     .placeholder(R.drawable.loader)
                     .into(new SimpleTarget<Drawable>() {
                         @Override
                         public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                             comicViewHolder.image.setImageDrawable(resource);

                         }
                     });
        }
    }

    protected class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView image;

        ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_comic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }
}
