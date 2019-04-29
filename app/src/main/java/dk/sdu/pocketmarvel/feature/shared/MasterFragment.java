package dk.sdu.pocketmarvel.feature.shared;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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

        RecyclerView.Adapter adapter = getAdapter();
        recyclerView.setAdapter(getAdapter());
        return view;
    }

    @Override
    public void onSelected(int id) {
        if (selectionListener != null) {
            selectionListener.onSelected(id);
        }
    }

    public abstract RecyclerView.Adapter getAdapter();
}
