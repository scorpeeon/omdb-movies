package com.scrpn.omdb.omdbmovies.ui.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.scrpn.omdb.omdbmovies.AppComponent;
import com.scrpn.omdb.omdbmovies.R;
import com.scrpn.omdb.omdbmovies.network.model.Movie;
import com.scrpn.omdb.omdbmovies.ui.BaseFragment;
import com.scrpn.omdb.omdbmovies.ui.details.MovieDetailFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MovieListFragment extends BaseFragment implements MovieListScreen, OnMovieSelectedListener {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    @Inject
    MovieListPresenter presenter;

    @BindView(R.id.movie_list)
    RecyclerView recyclerView;

    @BindView(R.id.search_string)
    EditText searchString;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity!= null) {
            toolbar.setTitle(activity.getTitle());
        }

        searchString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.refreshItems(s.toString());
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_movie_list;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachScreen(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.detachScreen();
    }



    @Override
    public void onMovieSelected(String imdbId) {

        hideKeyboard();
        navigateToFragment(MovieDetailFragment.newInstance(imdbId));
    }

    @Override
    public void onMoviesLoaded(List<Movie> movies) {
        recyclerView.setAdapter(new MovieRecyclerViewAdapter(movies, MovieListFragment.this));
    }

    @Override
    public void onLoadFailed() {
        Log.e(TAG, getString(R.string.load_fail_message));
    }

    @Override
    protected void injectDependencies(AppComponent injector) {
        injector.inject(this);
    }
}
