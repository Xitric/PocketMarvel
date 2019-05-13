package dk.sdu.pocketmarvel.feature.comic;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.MasterFragment;
import dk.sdu.pocketmarvel.vo.Comic;

public class ComicListFragment extends MasterFragment {

    private ComicListViewModel viewModel;
    private PagedListAdapter<Comic, ?> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ComicListViewModel.class);
        viewModel.getComicsLiveData().observe(this, comics ->
                adapter.submitList(comics));

        viewModel.getNetworkStatusLiveData().observe(this, fetchStatus ->
                Toast.makeText(this.getContext(), fetchStatus.getMessage() == null ? fetchStatus.getState().toString() : fetchStatus.getMessage(), Toast.LENGTH_LONG).show());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onSearch(String searchTerm) {
        viewModel.setSearchTerm(searchTerm);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new ComicAdapter(this, Glide.with(this));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_selection_list);

        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == 1) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        return view;
    }
}
