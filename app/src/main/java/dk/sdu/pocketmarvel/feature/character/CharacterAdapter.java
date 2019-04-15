package dk.sdu.pocketmarvel.feature.character;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.sdu.pocketmarvel.ObservableSelectionAdapter;
import dk.sdu.pocketmarvel.R;

public class CharacterAdapter extends ObservableSelectionAdapter<CharacterAdapter.CharacterViewHolder> {

    private String[] characters = {"1", "2", "hej"};

    public CharacterAdapter(OnAdapterSelectionListener adapterSelectionListener) {
        super(adapterSelectionListener);
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CharacterViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.character_list_item, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return characters.length;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder characterViewHolder, int i) {
        characterViewHolder.character.setText(characters[i]);
    }

    protected class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView character;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            character = itemView.findViewById(R.id.tv_character_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            getAdapterSelectionListener().onSelected(getAdapterPosition());
        }
    }
}
