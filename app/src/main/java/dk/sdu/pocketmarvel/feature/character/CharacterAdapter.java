package dk.sdu.pocketmarvel.feature.character;

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
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.repository.GlideApp;
import dk.sdu.pocketmarvel.vo.Character;

public class CharacterAdapter extends PagedListAdapter<Character, CharacterAdapter.CharacterViewHolder> {

    //Used by the PagedListAdapter to determine equality between versions of Character objects. For
    //instance, two Character objects might represent the same Character, but one was fetched more
    //previously than the other, and thus their contents differ.
    private static final DiffUtil.ItemCallback<Character> characterDiffCallback = new DiffUtil.ItemCallback<Character>() {
        @Override
        public boolean areItemsTheSame(@NonNull Character a, @NonNull Character b) {
            return a.getId() == b.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Character a, @NonNull Character b) {
            return a.equals(b);
        }
    };
    private final OnAdapterSelectionListener adapterSelectionListener;

    CharacterAdapter(OnAdapterSelectionListener adapterSelectionListener) {
        super(characterDiffCallback);
        this.adapterSelectionListener = adapterSelectionListener;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CharacterViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.character_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, int i) {
        Character character = getItem(i);
        if (character == null) {
            //Still fetching, show placeholder
            characterViewHolder.character.setText("Please wait...");
        } else {
            characterViewHolder.character.setText(character.getName());
            GlideApp.with(characterViewHolder.itemView.getContext())
                    .load(character.getThumbnail().getPath() + "/standard_medium." + character.getThumbnail().getExtension())
                    .placeholder(R.drawable.loader)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            characterViewHolder.image.setImageDrawable(resource);
                            characterViewHolder.spinner.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    protected class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView character;
        private ImageView image;
        private ProgressBar spinner;

        CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            character = itemView.findViewById(R.id.tv_character_list_item);
            image = itemView.findViewById(R.id.iv_character_thumbnail);
            spinner = itemView.findViewById(R.id.pb_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterSelectionListener.onSelected(getItem(getAdapterPosition()).getId());
        }
    }
}
