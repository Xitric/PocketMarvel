package dk.sdu.pocketmarvel.feature.comic;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dk.sdu.pocketmarvel.R;
import dk.sdu.pocketmarvel.feature.shared.MasterFragment;
import dk.sdu.pocketmarvel.vo.Comic;


public class ComicListFragment extends MasterFragment {

    private PagedListAdapter<Comic, ?> adapter;

    public ComicListFragment() {
        adapter = new ComicAdapter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComicListViewModel viewModel = ViewModelProviders.of(this).get(ComicListViewModel.class);
        viewModel.getComicsLiveData().observe(this, comics -> adapter.submitList(comics));
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_selection_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(getAdapter());
        return view;
    }
}
