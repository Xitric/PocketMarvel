package dk.sdu.pocketmarvel.feature.character;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dk.sdu.pocketmarvel.feature.shared.MasterFragment;
import dk.sdu.pocketmarvel.vo.Character;

public class CharacterListFragment extends MasterFragment {

    private CharacterListViewModel viewModel;
    private PagedListAdapter<Character, ?> adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new CharacterAdapter(this, Glide.with(context));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(CharacterListViewModel.class);
        viewModel.getCharactersLiveData().observe(this, characters ->
                adapter.submitList(characters));

        viewModel.getNetworkStatusLiveData().observe(this, fetchStatus -> {
            assert fetchStatus != null;
            Toast.makeText(this.getContext(), fetchStatus.getMessage() == null ? fetchStatus.getState().toString() : fetchStatus.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onSearch(String searchTerm) {
        viewModel.setSearchTerm(searchTerm);
    }
}
