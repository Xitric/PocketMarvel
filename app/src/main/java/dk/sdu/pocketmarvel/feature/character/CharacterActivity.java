package dk.sdu.pocketmarvel.feature.character;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import dk.sdu.pocketmarvel.MasterDetailActivity;

public class CharacterActivity extends MasterDetailActivity {

    @Override
    protected RecyclerView.Adapter getMasterAdapter() {
        return new CharacterAdapter(this);
    }

    @Override
    protected Fragment getDetailFragment() {
        return new CharacterFragment();
    }

}
