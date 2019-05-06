package dk.sdu.pocketmarvel.feature.comic;

import android.arch.paging.PagedListAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

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
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }


}
