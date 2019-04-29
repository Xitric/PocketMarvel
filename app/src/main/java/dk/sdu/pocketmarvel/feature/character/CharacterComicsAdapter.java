package dk.sdu.pocketmarvel.feature.character;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.OnAdapterSelectionListener;
import dk.sdu.pocketmarvel.vo.ComicSummary;

public class CharacterComicsAdapter extends RecyclerView.Adapter<CharacterComicsAdapter.ComicViewHolder> {

    private final OnAdapterSelectionListener adapterSelectionListener;
    private List<ComicSummary> comics;

    CharacterComicsAdapter(OnAdapterSelectionListener adapterSelectionListener) {
        this.adapterSelectionListener = adapterSelectionListener;
    }

    public void setComics(List<ComicSummary> comics) {
        this.comics = comics;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ComicViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.character_comic_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder comicViewHolder, int i) {
        comicViewHolder.comicName.setText(comics.get(i).getName());

        if (comics.get(i).getYear() == -1) {
            comicViewHolder.comicYear.setText("Unknown year");
        } else {
            comicViewHolder.comicYear.setText(String.valueOf(comics.get(i).getYear()));
        }
    }

    @Override
    public int getItemCount() {
        if (comics == null) {
            return 0;
        }

        return comics.size();
    }

    protected class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView comicName;
        private TextView comicYear;

        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            comicName = itemView.findViewById(R.id.tv_comic_name);
            comicYear = itemView.findViewById(R.id.tv_comic_year);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterSelectionListener.onSelected(comics.get(getAdapterPosition()).getId());
        }
    }
}
