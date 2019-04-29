package dk.sdu.pocketmarvel.feature.comic;

import android.support.v4.app.Fragment;

import dk.sdu.pocketmarvel.feature.shared.MasterDetailActivity;

public class ComicActivity extends MasterDetailActivity {

    @Override
    protected Fragment getMasterFragment() {
        return new ComicListFragment();
    }

    @Override
    protected Fragment getDetailFragment() {
        return new ComicFragment();
    }
}
