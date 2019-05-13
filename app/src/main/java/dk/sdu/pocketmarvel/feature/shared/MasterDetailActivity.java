package dk.sdu.pocketmarvel.feature.shared;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import dk.sdu.pocketmarvel.R;

public abstract class MasterDetailActivity extends AppCompatActivity implements OnMasterSelectionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                int id = bundle.getInt(DetailContract.CONTENT_ID, -1);
                if (id != -1) {
                    onSelected(id);

                    if (findViewById(R.id.vg_detail_fragment) == null) {
                        return;
                    }
                }
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.vg_primary_fragment, getMasterFragment())
                    .commit();
        }
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.vg_primary_fragment, fragment);

        if (getSupportFragmentManager().findFragmentById(R.id.vg_primary_fragment) != null) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    /**
     * Show a detail fragment besides the master fragment.
     */
    private void showDetailFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vg_detail_fragment, fragment)
                .commit();
    }

    protected abstract Fragment getMasterFragment();

    protected abstract Fragment getDetailFragment();
}
