package dk.sdu.pocketmarvel.feature.shared;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import dk.sdu.pocketmarvel.LogContract;
import dk.sdu.pocketmarvel.R;

public abstract class MasterFragment extends Fragment implements OnAdapterSelectionListener {

    @Nullable
    private OnMasterSelectionListener selectionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMasterSelectionListener) {
            selectionListener = (OnMasterSelectionListener) context;
        } else {
            Log.w(LogContract.POCKETMARVEL_TAG, "Master fragment attached to activity not conforming to OnMasterSelectionListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.master_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_selection_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(getAdapter());

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.sv_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                onSearch(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    onSearch(null);
                }
                return false;
            }
        });
    }

    @Override
    public void onSelected(int id) {
        if (selectionListener != null) {
            selectionListener.onSelected(id);
        }
    }

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract void onSearch(String searchTerm);
}
