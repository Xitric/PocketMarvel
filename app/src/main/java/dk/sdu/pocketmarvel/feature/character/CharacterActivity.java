package dk.sdu.pocketmarvel.feature.character;

import android.support.v4.app.Fragment;

import dk.sdu.pocketmarvel.feature.shared.MasterDetailActivity;

public class CharacterActivity extends MasterDetailActivity {

    @Override
    protected Fragment getDetailFragment() {
        return new CharacterFragment();
    }

    @Override
    protected Fragment getMasterFragment() {
        return new CharacterListFragment();
    }
}
