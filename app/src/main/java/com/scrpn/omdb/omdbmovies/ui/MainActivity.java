package com.scrpn.omdb.omdbmovies.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.scrpn.omdb.omdbmovies.AppComponent;
import com.scrpn.omdb.omdbmovies.R;
import com.scrpn.omdb.omdbmovies.ui.list.MovieListFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void injectDependencies(AppComponent injector) {
        injector.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFragment(new MovieListFragment());
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
