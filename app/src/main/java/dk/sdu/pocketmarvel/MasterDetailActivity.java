package dk.sdu.pocketmarvel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public abstract class MasterDetailActivity extends AppCompatActivity implements OnAdapterSelectionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        MasterFragment masterFragment = new MasterFragment();
        masterFragment.setMasterAdapter(getMasterAdapter());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vg_primary_fragment, masterFragment)
                .commit();
    }

    @Override
    public void onSelected(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(DetailContract.CONTENT_ID, id);
        Fragment fragment = getDetailFragment();
        fragment.setArguments(bundle);
        if (findViewById(R.id.vg_detail_fragment) == null) {
            pushDetailFragment(fragment);
        } else {
            showDetailFragment(fragment);
        }
    }

    /**
     * Replace the master fragment with a detail fragment.
     */
    private void pushDetailFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vg_primary_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Show a detail fragment besides the master fragment.
     */
    private void showDetailFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vg_detail_fragment, fragment)
                .commit();
    }

    protected abstract RecyclerView.Adapter getMasterAdapter();

    protected abstract Fragment getDetailFragment();
}
