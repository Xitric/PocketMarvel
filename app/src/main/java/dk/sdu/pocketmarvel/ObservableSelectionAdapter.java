package dk.sdu.pocketmarvel;

import android.support.v7.widget.RecyclerView;

public abstract class ObservableSelectionAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private OnAdapterSelectionListener adapterSelectionListener;

    public ObservableSelectionAdapter(OnAdapterSelectionListener adapterSelectionListener) {
        this.adapterSelectionListener = adapterSelectionListener;
    }

    protected OnAdapterSelectionListener getAdapterSelectionListener() {
        return adapterSelectionListener;
    }

    public interface OnAdapterSelectionListener {
        void onSelected(int id);
    }
}
